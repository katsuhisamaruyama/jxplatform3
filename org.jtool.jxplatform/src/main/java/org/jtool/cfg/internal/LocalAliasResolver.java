/*
 *  Copyright 2022
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.cfg.internal;

import org.jtool.cfg.CFG;
import org.jtool.cfg.CFGMethodEntry;
import org.jtool.cfg.CFGNode;
import org.jtool.cfg.CFGStatement;
import org.jtool.cfg.CFGMethodCall;
import org.jtool.cfg.CFGReceiver;
import org.jtool.cfg.JVariableReference;
import org.jtool.cfg.JFieldReference;
import org.jtool.cfg.ControlFlow;
import org.jtool.srcmodel.JavaClass;
import org.jtool.srcmodel.JavaField;
import org.jtool.srcmodel.JavaMethod;
import org.jtool.srcmodel.JavaProject;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.AssertStatement;
import org.eclipse.jdt.core.dom.Assignment;
import org.eclipse.jdt.core.dom.DoStatement;
import org.eclipse.jdt.core.dom.EnhancedForStatement;
import org.eclipse.jdt.core.dom.ForStatement;
import org.eclipse.jdt.core.dom.IfStatement;
import org.eclipse.jdt.core.dom.ReturnStatement;
import org.eclipse.jdt.core.dom.SwitchStatement;
import org.eclipse.jdt.core.dom.ThrowStatement;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;
import org.eclipse.jdt.core.dom.WhileStatement;
import org.eclipse.jdt.core.dom.Expression;
import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;
import java.util.Stack;
import java.util.Collection;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;

/**
 * Resolves the alias relations in local variables in the method
 * and fields in a class enclosing the method.
 * 
 * @author Katsuhisa Maruyama
 */
class LocalAliasResolver {
    
    private Multimap<CFGNode, Alias> aliasMap = HashMultimap.create();
    
    private JavaProject jproject;
    
    LocalAliasResolver(JavaProject jproject) {
        this.jproject = jproject;
    }
    
    void resolve(CFG cfg) {
        if (!cfg.isMethod()) {
            return;
        }
        
        collectAliasesByFieldDeclations(cfg);
        collectAliases(cfg);
        
        if (aliasMap.size() != 0) {
            cfg.getNodes().stream().filter(node -> node.isStatement() && !node.isFormal())
                    .forEach(node -> addAliasVariables((CFGStatement)node));
        }
    }
    
    private void collectAliasesByFieldDeclations(CFG cfg) {
        CFGMethodEntry entry = (CFGMethodEntry)cfg.getEntryNode();
        JavaMethod jmethod = entry.getJavaMethod();
        
        for (JavaField jfield : jmethod.getDeclaringClass().getFields()) {
            if (jfield.isField() && jfield.isFinal()) {
                VariableDeclarationFragment decllaration = (VariableDeclarationFragment)jfield.getASTNode();
                CFGStatement declNode = new CFGStatement(decllaration, CFGNode.Kind.dummy);
                ExpressionVisitor expressionVisitor = new ExpressionVisitor(jproject, declNode);
                decllaration.accept(expressionVisitor);
                
                Alias alias = getAliasRelation(declNode, decllaration.getInitializer());
                if (alias != null && isFinalField(alias.righthand)) {
                    cfg.getNodes().stream()
                            .filter(node -> node.isStatement() && !node.isFormal())
                            .forEach(node -> registerAlias((CFGStatement)node, alias));
                }
            }
        }
    }
    
    private boolean isFinalField(JVariableReference var) {
        if (var.isFieldAccess()) {
            String className = var.getQualifiedName().getClassName();
            JavaClass jclass = jproject.getClass(className);
            if (jclass != null) {
                JavaField jfield = jclass.getField(var.getQualifiedName().getMemberSignature());
                if (jfield.isFinal()) {
                    return true;
                }
            }
        }
        return false;
    }
    
    private Alias getAliasRelation(CFGStatement node, Expression expr) {
        List<JVariableReference> defs = node.getDefVariables();
        if (defs.size() != 1) {
            return null;
        }
        JVariableReference def = defs.get(0);
        if (def.isPrimitiveType() || !def.isAvailable()) {
            return null;
        }
        
        List<JVariableReference> uses = node.findPrimaryUseVariables(expr);
        if (uses.size() != 1) {
            return null;
        }
        JVariableReference use = uses.get(0);
        if (use.isPrimitiveType() || (!use.isAvailable() && !use.isReturnValueReference())) {
            return null;
        }
        if (use.isFieldAccess() && ((JFieldReference)use).isEnumConstant()) {
            return null;
        }
        
        return new Alias(def, use);
    }
    
    private void registerAlias(CFGNode node, Alias alias) {
        String name = alias.righthand.getReferenceForm();
        for (Alias as : new ArrayList<>(aliasMap.get(node))) {
            if (as.lefthand.getReferenceForm().equals(name)) {
                aliasMap.put(node, new Alias(alias.lefthand, as.righthand));
            } else if (alias.bidirectional && as.righthand.getReferenceForm().equals(name)) {
                aliasMap.put(node, new Alias(alias.lefthand, as.lefthand));
            }
        }
        aliasMap.put(node, alias);
    }
    
    private void collectAliases(CFG cfg) {
        for (CFGNode node : CFGNode.sortNodes(cfg.getNodes())) {
            List<Alias> aliases = new ArrayList<>();
            collectAliases(node, aliases);
            aliases.forEach(a -> createAliasMap(node, a, new HashSet<>()));
        }
    }
    
    @SuppressWarnings("unchecked")
    private void collectAliases(CFGNode node, List<Alias> aliases) {
        List<Expression> exprs = new ArrayList<>();
        if (node.isAssignment()) {
            collectAliasesInAssignment(node.getASTNode(), node, aliases);
            return;
        } else if (node.isLocalDeclaration()) {
            collectAliasesInDeclaration(node.getASTNode(), node, aliases);
            return;
        }
        
        if (node.isAssert()) {
            exprs.add(((AssertStatement)node.getASTNode()).getExpression());
        } else if (node.isDo()) {
            exprs.add(((DoStatement)node.getASTNode()).getExpression());
        } else if (node.isFor()) {
            exprs.addAll(((ForStatement)node.getASTNode()).initializers());
            exprs.add(((ForStatement)node.getASTNode()).getExpression());
            exprs.addAll(((ForStatement)node.getASTNode()).updaters());
        } else if (node.isEnhancedFor()) {
            exprs.add(((EnhancedForStatement)node.getASTNode()).getExpression());
        } else if (node.isIf()) {
            exprs.add(((IfStatement)node.getASTNode()).getExpression());
        } else if (node.isReturn()) {
            exprs.add(((ReturnStatement)node.getASTNode()).getExpression());
        } else if (node.isSwitch()) {
            exprs.add(((SwitchStatement)node.getASTNode()).getExpression());
        } else if (node.isWhile()) {
            exprs.add(((WhileStatement)node.getASTNode()).getExpression());
        } else if (node.isThrow()) {
            exprs.add(((ThrowStatement)node.getASTNode()).getExpression());
        }
        
        for (Expression expr : exprs) {
            if (expr != null) {
                CFGStatement exprNode = new CFGStatement(expr, CFGNode.Kind.dummy);
                ExpressionVisitor expressionVisitor = new ExpressionVisitor(jproject, exprNode);
                expr.accept(expressionVisitor);
                
                collectAliasesInAssignment(expr, exprNode, aliases);
            }
        }
    }
    
    private void collectAliasesInAssignment(ASTNode astNode, CFGNode node, List<Alias> aliases) {
        if (astNode instanceof Assignment) {
            Assignment assignment = (Assignment)astNode;
            if (assignment.getOperator() == Assignment.Operator.ASSIGN) {
                if (assignment.getRightHandSide() instanceof Assignment) {
                    Assignment righthand = (Assignment)assignment.getRightHandSide();
                    Alias alias = getAliasRelation((CFGStatement)node, righthand.getLeftHandSide());
                    if (alias != null) {
                        aliases.add(alias);
                    }
                    
                    CFGStatement righthandNode = new CFGStatement(righthand, CFGNode.Kind.dummy);
                    ExpressionVisitor expressionVisitor = new ExpressionVisitor(jproject, righthandNode);
                    righthand.accept(expressionVisitor);
                    
                    collectAliasesInAssignment(righthand, righthandNode, aliases);
                    
                } else {
                    Alias alias = getAliasRelation((CFGStatement)node, assignment.getRightHandSide());
                    if (alias != null) {
                        aliases.add(alias);
                    }
                }
            }
        }
    }
    
    private void collectAliasesInDeclaration(ASTNode astNode, CFGNode node, List<Alias> aliases) {
        if (astNode instanceof VariableDeclarationFragment) {
            VariableDeclarationFragment declaration = (VariableDeclarationFragment)astNode;
            if (declaration.getInitializer() instanceof Assignment) {
                Assignment initializer = (Assignment)declaration.getInitializer();
                Alias alias = getAliasRelation((CFGStatement)node, initializer.getLeftHandSide());
                if (alias != null) {
                    aliases.add(alias);
                }
                
                CFGStatement initializerNode = new CFGStatement(initializer, CFGNode.Kind.dummy);
                ExpressionVisitor expressionVisitor = new ExpressionVisitor(jproject, initializerNode);
                initializer.accept(expressionVisitor);
                
                collectAliasesInAssignment(initializer, initializerNode, aliases);
            } else {
                Alias alias = getAliasRelation((CFGStatement)node, declaration.getInitializer());
                if (alias != null) {
                    aliases.add(alias);
                }
            }
        }
    }
    
    private void createAliasMap(CFGNode node, Alias alias, Set<CFGNode> track) {
        Stack<CFGNode> stack = new Stack<>();
        stack.push(node);
        
        while (!stack.isEmpty()) {
            CFGNode top = stack.pop();
            
            track.add(top);
            
            for (ControlFlow edge : top.getOutgoingFlows()) {
                CFGNode succ = edge.getDstNode();
                if (succ.isStatement()) {
                    CFGStatement stNode = (CFGStatement)succ;
                    if (stNode.isActualOut()) {
                        registerAlias(stNode, alias);
                    } else if (define(stNode, alias.lefthand) || define(stNode, alias.righthand)) {
                        return;
                    } else {
                        registerAlias(stNode, alias);
                    }
                }
                if (!track.contains(succ)) {
                    stack.push(succ);
                }
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
    
    private void addAliasVariables(CFGStatement node) {
        Collection<Alias> aliases = aliasMap.get(node);
        
        for (JVariableReference var : new ArrayList<>(node.getUseVariables())) {
            String name = var.getReferenceForm();
            
            for (Alias alias : aliases) {
                String lname = alias.lefthand.getReferenceForm();
                String rname = alias.righthand.getReferenceForm();
                
                if (!node.isReceiver() || (node.isReceiver() && hasDefInCall((CFGReceiver)node))) {
                    String aliasName = getAliasName(name, lname, rname);
                    if (aliasName != null) {
                        JVariableReference avar = new JAliasReference(alias.righthand, aliasName, var);
                        if (!avar.getReferenceForm().endsWith(")")) {
                            node.addUseVariable(avar);
                        }
                    }
                }
                
                if (alias.bidirectional) {
                    if (!node.isReceiver() || (node.isReceiver() && hasDefInCall((CFGReceiver)node))) {
                        String aliasName = getAliasName(name, rname, lname);
                        if (aliasName != null) {
                            JVariableReference avar = new JAliasReference(alias.lefthand, aliasName, var);
                            if (!avar.getReferenceForm().endsWith(")")) {
                                node.addUseVariable(avar);
                            }
                        }
                    }
                }
            }
        }
    }
    
    private String getAliasName(String name, String name1, String name2) {
        if (name.equals(name1)) {
            return name.replace(name1, name2);
        } else if (name.indexOf("." + name1 + ".") != -1) {
            return name.replace("." + name1 + ".", "." + name2 + ".");
        } else if (name.startsWith(name1 + ".")) {
            return name.replace(name1 + ".", name2 + ".");
        } else if (name.endsWith("." + name1)) {
            return name.replace("." + name1, "." + name2);
        }
        return null;
    }
    
    private boolean hasDefInCall(CFGReceiver node) {
        CFGMethodCall callNode = node.getMethodCall();
        return callNode.getActualOut().getDefVariables().size() > 1;
    }
    
    private class Alias {
        
        JVariableReference lefthand;
        JVariableReference righthand;
        boolean bidirectional;
        
        Alias(JVariableReference lefthand, JVariableReference righthand) {
            this.lefthand = lefthand;
            this.righthand = righthand;
            this.bidirectional = !righthand.isReturnValueReference();
        }
        
        @Override
        public String toString() {
            return lefthand.getReferenceForm() + " == " + righthand.getReferenceForm();
        }
    }
}
