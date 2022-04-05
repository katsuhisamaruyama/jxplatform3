/*
 *  Copyright 2022
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.cfg.builder;

import org.jtool.cfg.CFG;
import org.jtool.cfg.CFGMethodEntry;
import org.jtool.cfg.CFGNode;
import org.jtool.cfg.CFGStatement;
import org.jtool.cfg.ControlFlow;
import org.jtool.cfg.JVariableReference;
import org.jtool.cfg.JFieldReference;
import org.jtool.cfg.JExpedientialReference;
import org.jtool.srcmodel.JavaField;
import org.jtool.srcmodel.JavaMethod;
import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;
import java.util.Collection;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import org.eclipse.jdt.core.dom.Assignment;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;

/**
 * Resolves the alias relations in local variables in the method
 * and fields in a class enclosing the method.
 * 
 * @author Katsuhisa Maruyama
 */
class LocalAliasResolver {
    
    private Multimap<CFGNode, Alias> aliasMap = HashMultimap.create();
    
    void resolve(CFG cfg) {
        if (!cfg.isMethod()) {
            return;
        }
        
        collectAliasStaticFields(cfg);
        
        for (CFGNode node : cfg.getNodes()) {
            List<Alias> aliases = null;
            if (node.getASTNode() instanceof Assignment) {
                Assignment assignment = (Assignment)node.getASTNode();
                if (assignment.getOperator() == Assignment.Operator.ASSIGN) {
                    aliases = getAliasRelations((CFGStatement)node, assignment.getRightHandSide());
                }
            } else if (node.getASTNode() instanceof VariableDeclarationFragment) {
                VariableDeclarationFragment decl = (VariableDeclarationFragment)node.getASTNode();
                aliases = getAliasRelations((CFGStatement)node, decl.getInitializer());
            }
            
            if (aliases != null) {
                aliases.forEach(a -> createAliasMap(node, a, new HashSet<>()));
            }
        }
        
        if (aliasMap.size() != 0) {
            cfg.getNodes().stream()
                          .filter(n -> n.isStatement() && !n.isFormal())
                          .forEach(n -> addAliasVariables((CFGStatement)n));
        }
    }
    
    private void collectAliasStaticFields(CFG cfg) {
        CFGMethodEntry entry = (CFGMethodEntry)cfg.getEntryNode();
        JavaMethod jmethod = entry.getJavaMethod();
        
        for (JavaField jfield : jmethod.getDeclaringClass().getFields()) {
            if (jfield.isField()) {
                VariableDeclarationFragment decl = (VariableDeclarationFragment)jfield.getASTNode();
                CFGStatement stNode = new CFGStatement(decl, CFGNode.Kind.fieldDeclaration);
                ExpressionVisitor expressionVisitor = new ExpressionVisitor(new CFG(), stNode);
                decl.accept(expressionVisitor);
                
                for (Alias alias : getAliasRelations(stNode, decl.getInitializer())) {
                    for (CFGNode node : cfg.getNodes()) {
                        if (node.isStatement() && !node.isFormal()) {
                            registerAlias((CFGStatement)node, alias);
                        }
                    }
                }
            }
        }
    }
    
    private void registerAlias(CFGNode node, Alias newAlias) {
        String name = newAlias.righthand.getReferenceForm();
        for (Alias alias : new ArrayList<>(aliasMap.get(node))) {
            if (alias.lefthand.getReferenceForm().equals(name)) {
                aliasMap.put(node, new Alias(newAlias.lefthand, alias.righthand));
            } else if (alias.righthand.getReferenceForm().equals(name)) {
                aliasMap.put(node, new Alias(newAlias.lefthand, alias.lefthand));
            }
        }
        aliasMap.put(node, newAlias);
    }
    
    private void createAliasMap(CFGNode node, Alias alias, Set<CFGNode> track) {
        track.add(node);
        
        for (ControlFlow edge : node.getOutgoingFlows()) {
            CFGNode succ = edge.getDstNode();
            if (succ.isStatement()) {
                CFGStatement stNode = (CFGStatement)succ;
                if (stNode.isActualOut()) {
                    registerAlias((CFGStatement)succ, alias);
                } else if (define(stNode, alias.lefthand) || define(stNode, alias.righthand)) {
                    return;
                } else {
                    registerAlias((CFGStatement)succ, alias);
                }
            } 
            if (!track.contains(succ)) {
                createAliasMap(succ, alias, track);
            }
        }
    }
    
    private boolean define(CFGStatement node, JVariableReference var) {
        while (var != null) {
            if (node.defineVariable(var)) {
                return true;
            }
            var = var.getPrefix();
        }
        return false;
    }
    
    private List<Alias> getAliasRelations(CFGStatement node, Expression expr) {
        List<Alias> aliases = new ArrayList<>();
        
        if (node.getDefVariables().size() != 1) {
            return aliases;
        }
        
        JVariableReference def = node.getDefFirst();
        if (def.isPrimitiveType() || !def.isTouchable()) {
            return aliases;
        }
        
        for (JVariableReference use : node.findPrimaryUseVariables(expr)) {
            if (use.isPrimitiveType()) {
                continue;
            }
            if (use.isFieldAccess() && ((JFieldReference)use).isEnumConstant()) {
                continue;
            }
            aliases.add(new Alias(def, use));
        }
        return aliases;
    }
    
    private void addAliasVariables(CFGStatement node) {
        Collection<Alias> aliases = aliasMap.get(node);
        for (JVariableReference var : new ArrayList<>(node.getUseVariables())) {
            String name = var.getReferenceForm();
            
            for (Alias alias : aliases) {
                String lname = alias.lefthand.getReferenceForm();
                String rname = alias.righthand.getReferenceForm();
                
                String aliasName = getAliasName(name, lname, rname);
                if (aliasName != null) {
                    JVariableReference avar = new JExpedientialReference(alias.righthand.getASTNode(),
                            aliasName, alias.righthand.getType(), alias.righthand.isPrimitiveType());
                    node.addUseVariable(avar);
                  
                }
                
                aliasName = getAliasName(name, rname, lname);
                if (aliasName != null) {
                    JVariableReference avar = new JExpedientialReference(alias.lefthand.getASTNode(),
                            aliasName, alias.lefthand.getType(), alias.lefthand.isPrimitiveType());
                    node.addUseVariable(avar);
                }
            }
        }
    }
    
    private String getAliasName(String name, String name1, String name2) {
        if (name.equals(name1)) {
            return name.replace(name1, name2);
        } else if (name.indexOf("." + name1 + ".") != -1) {
            return name.replaceAll("." + name1 + ".", "." + name2 + ".");
        } else if (name.startsWith(name1 + ".")) {
            return name.replace(name1 + ".", name2 + ".");
        } else if (name.endsWith("." + name1)) {
            return name.replace("." + name1, "." + name2);
        }
        return null;
    }
    
    private class Alias {
        
        JVariableReference lefthand;
        JVariableReference righthand;
        
        Alias(JVariableReference lefthand, JVariableReference righthand) {
            this.lefthand = lefthand;
            this.righthand = righthand;
        }
        
        @Override
        public String toString() {
            return lefthand.getReferenceForm() + " == " + righthand.getReferenceForm();
        }
    }
}
