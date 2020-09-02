/*
 *  Copyright 2020
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.pdg.builder;

import org.jtool.pdg.CD;
import org.jtool.pdg.CallEdge;
import org.jtool.pdg.ClDG;
import org.jtool.pdg.ClassMemberEdge;
import org.jtool.pdg.DD;
import org.jtool.pdg.Dependence;
import org.jtool.pdg.PDG;
import org.jtool.pdg.PDGClassEntry;
import org.jtool.pdg.PDGEntry;
import org.jtool.pdg.PDGNode;
import org.jtool.pdg.PDGStatement;
import org.jtool.pdg.SDG;
import org.jtool.cfg.CCFG;
import org.jtool.cfg.CFG;
import org.jtool.cfg.CFGClassEntry;
import org.jtool.cfg.CFGEntry;
import org.jtool.cfg.CFGException;
import org.jtool.cfg.CFGFieldEntry;
import org.jtool.cfg.CFGMethodCall;
import org.jtool.cfg.CFGMethodEntry;
import org.jtool.cfg.CFGNode;
import org.jtool.cfg.CFGParameter;
import org.jtool.cfg.CFGStatement;
import org.jtool.cfg.ControlFlow;
import org.jtool.cfg.JFieldReference;
import org.jtool.cfg.JReference;
import org.jtool.srcmodel.JavaClass;
import org.jtool.srcmodel.QualifiedName;
import org.eclipse.jdt.core.dom.ITypeBinding;
import java.util.Set;
import java.util.HashSet;
import java.util.stream.Collectors;

/**
 * Builds a PDG for a class member (a method, constructor, initializer, or field).
 * 
 * @author Katsuhisa Maruyama
 */
public class PDGBuilder {
    
    public static PDG buildPDG(CFG cfg) {
        PDG pdg = new PDG();
        createNodes(pdg, cfg);
        CDFinder.find(pdg, cfg);
        DDFinder.find(pdg, cfg);
        return pdg;
    }
    
    private static void createNodes(PDG pdg, CFG cfg) {
        cfg.getNodes().stream()
           .map(cfgnode -> createNode(pdg, cfgnode))
           .filter(pdgnode -> pdgnode != null)
           .forEach(pdgnode -> pdg.add(pdgnode));
    }
    
    private static PDGNode createNode(PDG pdg, CFGNode node) {
        if (node.isInterfaceEntry() || node.isClassEntry() || node.isEnumEntry()) {
            PDGClassEntry pdgnode = new PDGClassEntry((CFGClassEntry)node);
            pdg.setEntryNode(pdgnode);
            return pdgnode;
            
        } else if (node.isMethodEntry() || node.isConstructorEntry() || node.isInitializerEntry() ||
                   node.isFieldEntry() || node.isEnumConstantEntry()) {
            PDGEntry pdgnode = new PDGEntry((CFGEntry)node);
            pdg.setEntryNode(pdgnode);
            return pdgnode;
            
        } else if (node.isStatement()) {
            PDGStatement pdgnode = new PDGStatement((CFGStatement)node);
            return pdgnode;
        }
        return null;
    }
    
    public static ClDG buildClDG(CCFG ccfg) {
        ClDG cldg = new ClDG();
        PDGClassEntry entry = new PDGClassEntry(ccfg.getEntryNode());
        cldg.setEntryNode(entry);
        cldg.add(entry);
        
        for (CFG cfg : ccfg.getCFGs()) {
            PDG pdg = buildPDG(cfg);
            cldg.add(pdg);
            
            ClassMemberEdge edge = new ClassMemberEdge(entry, pdg.getEntryNode());
            edge.setKind(Dependence.Kind.classMember);
            cldg.add(edge);
        }
        return cldg;
    }
    
    public static void connectFieldAccesses(SDG sdg) {
        Set<CFGFieldEntry> fieldEntries = sdg.getPDGs()
                .stream()
                .map(pdg -> pdg.getCFG())
                .filter(cfg -> cfg.isField())
                .map(cfg -> (CFGFieldEntry)cfg.getEntryNode())
                .collect(Collectors.toSet());
        
        for (PDG pdg : sdg.getPDGs()) {
            CFG cfg = pdg.getCFG();
            for (CFGNode node : cfg.getNodes()) {
                if (node.isStatement()) {
                    CFGStatement stNode = (CFGStatement)node;
                    
                    for (JReference var : stNode.getDefVariables()) {
                        if (var.isFieldAccess()) {
                            JFieldReference fvar = (JFieldReference)var;
                            
                            for (CFGFieldEntry fieldEntry : fieldEntries) {
                                if (fieldEntry.getQualifiedName().equals(fvar.getQualifiedName())) {
                                    DD edge = new DD(node.getPDGNode(), fieldEntry.getDeclarationNode().getPDGNode(), fvar);
                                    edge.setFieldAccess();
                                    pdg.add(edge);
                                    
                                    if (cfg.isMethod()) {
                                        CFGMethodEntry cfgEntry = (CFGMethodEntry)cfg.getEntryNode();
                                        if (cfgEntry.isConstructorEntry()) {
                                            CFGParameter foutForInstance = cfgEntry.getFormalOutForReturn();
                                            
                                            DD instanceCreationEdge = new DD(fieldEntry.getDeclarationNode().getPDGNode(), foutForInstance.getPDGNode(), fvar);
                                            instanceCreationEdge.setFieldAccess();
                                            pdg.add(instanceCreationEdge);
                                        }
                                    }
                                }
                            }
                        }
                    }
                    
                    for (JReference var : stNode.getUseVariables()) {
                        if (var.isFieldAccess()) {
                            JFieldReference fvar = (JFieldReference)var;
                            
                            for (CFGFieldEntry fieldEntry : fieldEntries) {
                                if (fieldEntry.getQualifiedName().equals(fvar.getQualifiedName())) {
                                    DD edge = new DD(fieldEntry.getDeclarationNode().getPDGNode(), node.getPDGNode(), fvar);
                                    edge.setFieldAccess();
                                    pdg.add(edge);
                                }
                            }
                        }
                    }
                }
            }
        }
    }
    
    public static void connectMethodCalls(ClDG cldg) {
        for (PDG pdg : cldg.getPDGs()) {
            CFG cfg = pdg.getCFG();
            for (CFGNode node : cfg.getNodes()) {
                if (node.isMethodCall()) {
                    CFGMethodCall callNode = (CFGMethodCall)node;
                    PDG callee = cldg.getPDG(callNode.getQualifiedName().fqn());
                    if (callee != null) {
                        CallEdge edge = new CallEdge(callNode.getPDGNode(), callee.getEntryNode());
                        edge.setCall();
                        pdg.add(edge);
                        
                        connectParameters(pdg, callNode, (CFGMethodEntry)callee.getCFG().getEntryNode());
                        connectExceptionCatch(pdg, callNode, (CFGMethodEntry)callee.getCFG().getEntryNode());
                    }
                }
            }
        }
        for (PDG pdg : cldg.getPDGs()) {
            SummaryEdgeFinder.find(pdg);
        }
    }
    
    public static void connectMethodCalls(Set<JavaClass> classes, SDG sdg) {
        for (PDG pdg : sdg.getPDGs()) {
            CFG cfg = pdg.getCFG();
            for (CFGNode node : cfg.getNodes()) {
                if (node.isMethodCall()) {
                    CFGMethodCall callnode = (CFGMethodCall)node;
                    for (String className : callnode.getApproximatedTypes()) {
                        QualifiedName qname = new QualifiedName(className, callnode.getSignature());
                        PDG callee = sdg.getPDG(qname.fqn());
                        if (callee != null) {
                            CallEdge edge = new CallEdge(callnode.getPDGNode(), callee.getEntryNode());
                            edge.setCall();
                            pdg.add(edge);
                            
                            connectParameters(pdg, callnode, (CFGMethodEntry)callee.getCFG().getEntryNode());
                            connectExceptionCatch(pdg, callnode, (CFGMethodEntry)callee.getCFG().getEntryNode());
                        }
                    }
                }
            }
        }
        for (PDG pdg : sdg.getPDGs()) {
            SummaryEdgeFinder.find(pdg);
        }
    }
    
    private static void connectParameters(PDG pdg, CFGMethodCall caller, CFGMethodEntry callee) {
        if (caller.getMethodCall().isVarargs()) {
            CFGParameter formalIn;
            for (int index = 0; index < caller.getActualIns().size() - 1; index++) {
                CFGParameter actualIn = caller.getActualIn(index);
                if (index < callee.getFormalIns().size()) {
                    formalIn = callee.getFormalIn(index);
                } else {
                    formalIn = callee.getFormalIn(callee.getFormalIns().size() - 1);
                }
                
                JReference jvar = formalIn.getUseVariables().get(0);
                DD edge = new DD(actualIn.getPDGNode(), formalIn.getPDGNode(), jvar);
                edge.setParameterIn();
                pdg.add(edge);
            }
        } else {
            for (int ordinal = 0; ordinal < caller.getActualIns().size(); ordinal++) {
                CFGParameter actualIn = caller.getActualIn(ordinal);
                CFGParameter formalIn = callee.getFormalIn(ordinal);
                
                JReference jvar = formalIn.getUseVariables().get(0);
                DD edge = new DD(actualIn.getPDGNode(), formalIn.getPDGNode(), jvar);
                edge.setParameterIn();
                pdg.add(edge);
            }
        }
        
        CFGParameter actualOut = caller.getActualOutForReturn();
        CFGParameter formalOut = callee.getFormalOutForReturn();
        
        JReference jvar = formalOut.getUseVariables().get(0);
        DD edge = new DD(formalOut.getPDGNode(), actualOut.getPDGNode(), jvar);
        edge.setParameterOut();
        pdg.add(edge);
    }
    
    public static void connectMethodCallsConservatively(PDG pdg) {
        for (PDGNode pdgnode : pdg.getNodes()) {
            CFGNode cfgnode = pdgnode.getCFGNode();
            if (cfgnode.isMethodCall()) {
                CFGMethodCall callNode = (CFGMethodCall)cfgnode;
                PDGNode aoutForReturn = callNode.getActualOutForReturn().getPDGNode();
                for (CFGStatement ain : callNode.getActualIns()) {
                    JReference jvar = ain.getDefVariables().get(0);
                    DD edge = new DD(ain.getPDGNode(), aoutForReturn, jvar);
                    edge.setSummary();
                    pdg.add(edge);
                }
            }
        }
    }
    
    private static void connectExceptionCatch(PDG pdg, CFGMethodCall caller, CFGMethodEntry callee) {
        for (ControlFlow flow : caller.getOutgoingFlows()) {
            if (flow.isExceptionCatch()) {
                CFGException catchNode = (CFGException)flow.getDstNode();
                for (CFGException exceptionNode : callee.getExceptionNodes()) {
                    if (getCatchTypes(exceptionNode.getType()).contains(catchNode.getTypeName())) {
                        CD edge = new CD(exceptionNode.getPDGNode(), catchNode.getPDGNode());
                        edge.setExceptionCatch();
                        pdg.add(edge);
                    }
                }
            }
        }
    }
    
    private static Set<String> getCatchTypes(ITypeBinding type) {
        Set<String> types = new HashSet<>();
        while (type != null) {
            types.add(type.getQualifiedName());
            type = type.getSuperclass();
        }
        return types;
    }
}
