/*
 *  Copyright 2022
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.pdg.builder;

import org.jtool.pdg.PDG;
import org.jtool.pdg.ClDG;
import org.jtool.pdg.SDG;
import org.jtool.pdg.DependencyGraph;
import org.jtool.pdg.Dependence;
import org.jtool.pdg.CD;
import org.jtool.pdg.DD;
import org.jtool.pdg.ClDGEntry;
import org.jtool.pdg.PDGEntry;
import org.jtool.pdg.PDGNode;
import org.jtool.pdg.PDGStatement;
import org.jtool.cfg.CCFG;
import org.jtool.cfg.CFG;
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
import org.jtool.cfg.JComplementaryFieldReference;
import org.jtool.cfg.JVariableReference;
import org.jtool.srcmodel.QualifiedName;
import java.util.List;
import java.util.Set;
import java.util.Stack;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.stream.Collectors;
import org.eclipse.jdt.core.dom.ITypeBinding;

/**
 * Builds a PDG for a class member (a method, constructor, initializer, or field).
 * 
 * @author Katsuhisa Maruyama
 */
public class PDGBuilder {
    
    static ClDG buildClDG(CCFG ccfg, Map<String, BarePDG> bpdgMap) {
        ClDG cldg = new ClDG();
        ClDGEntry entry = new ClDGEntry(ccfg.getEntryNode());
        cldg.setEntryNode(entry);
        cldg.add(entry);
        
        for (CFG cfg : ccfg.getCFGs()) {
            PDG pdg = buildPDG(cfg, bpdgMap);
            cldg.add(pdg);
            
            Dependence edge = new Dependence(entry, pdg.getEntryNode());
            edge.setClassMember();
            cldg.add(edge);
        }
        return cldg;
    }
    
    private static PDG buildPDG(CFG cfg, Map<String, BarePDG> bpdgMap) {
        BarePDG bpdg = bpdgMap.get(cfg.getQualifiedName().fqn()); 
        if (bpdg == null) {
            bpdg = new BarePDG(cfg);
            createNodes(bpdg, cfg);
            CDFinder.find(bpdg, cfg);
            DDFinder.find(bpdg, cfg);
            bpdgMap.put(cfg.getQualifiedName().fqn(), bpdg);
        }
        
        PDG pdg = new PDG();
        pdg.setBarePDG(bpdg);
        PDGEntry entry = (PDGEntry)pdg.getNodes().stream()
                                      .filter(node -> node.isEntry()).findFirst().orElse(null);
        pdg.setEntryNode(entry);
        return pdg;
    }
    
    private static void createNodes(BarePDG bpdg, CFG cfg) {
        cfg.getNodes().stream()
                      .map(cfgnode -> createNode(bpdg, cfgnode))
                      .filter(pdgnode -> pdgnode != null)
                      .forEach(pdgnode -> bpdg.add(pdgnode));
    }
    
    private static PDGNode createNode(BarePDG bpdg, CFGNode node) {
        if (node.isMethodEntry() || node.isConstructorEntry() || node.isInitializerEntry() ||
                   node.isFieldEntry() || node.isEnumConstantEntry()) {
            PDGEntry pdgnode = new PDGEntry((CFGEntry)node);
            return pdgnode;
        } else if (node.isStatement()) {
            PDGStatement pdgnode = new PDGStatement((CFGStatement)node);
            return pdgnode;
        }
        return null;
    }
    
    public static void connectMethodCalls(DependencyGraph graph) {
        findCalls(graph);
        findSummaryEdges(graph);
    }
    
    private static void findCalls(DependencyGraph graph) {
        for (PDGNode node : graph.getNodes()) {
            CFGNode cfgnode = node.getCFGNode();
            if (cfgnode.isMethodCall()) {
                CFGMethodCall caller = (CFGMethodCall)cfgnode;
                for (String className : caller.getApproximatedTypeNames()) {
                    QualifiedName qname = new QualifiedName(className, caller.getSignature());
                    PDG callee = graph.findPDG(qname.fqn());
                    if (callee != null) {
                        Dependence edge = new Dependence(node, callee.getEntryNode());
                        edge.setCall();
                        graph.add(edge);
                        
                        connectParameters(graph, caller, (CFGMethodEntry)callee.getCFG().getEntryNode());
                        connectExceptionCatch(graph, caller, (CFGMethodEntry)callee.getCFG().getEntryNode());
                    }
                }
            }
        }
    }
    
    private static void connectParameters(DependencyGraph graph, CFGMethodCall caller, CFGMethodEntry callee) {
        if (caller.getMethodCall().isVarargs()) {
            CFGParameter formalIn;
            for (int index = 0; index < caller.getActualIns().size() - 1; index++) {
                CFGParameter actualIn = caller.getActualIn(index);
                if (index < callee.getFormalIns().size()) {
                    formalIn = callee.getFormalIn(index);
                } else {
                    formalIn = callee.getFormalIn(callee.getFormalIns().size() - 1);
                }
                
                JVariableReference jvar = formalIn.getDefFirst();
                DD edge = new DD(actualIn.getPDGNode(), formalIn.getPDGNode(), jvar);
                edge.setParameterIn();
                graph.add(edge);
            }
            
        } else {
            for (int ordinal = 0; ordinal < caller.getActualIns().size(); ordinal++) {
                CFGParameter actualIn = caller.getActualIn(ordinal);
                CFGParameter formalIn = callee.getFormalIn(ordinal);
                
                JVariableReference jvar = formalIn.getDefFirst();
                DD edge = new DD(actualIn.getPDGNode(), formalIn.getPDGNode(), jvar);
                edge.setParameterIn();
                graph.add(edge);
            }
        }
        
        CFGParameter actualOut = caller.getActualOut();
        CFGParameter formalOut = callee.getFormalOut();
        
        if (formalOut != null) {
            JVariableReference jvar = formalOut.getUseFirst();
            DD edge = new DD(formalOut.getPDGNode(), actualOut.getPDGNode(), jvar);
            edge.setParameterOut();
            graph.add(edge);
        }
    }
    
    private static void connectExceptionCatch(DependencyGraph graph, CFGMethodCall caller, CFGMethodEntry callee) {
        for (ControlFlow flow : caller.getOutgoingFlows()) {
            if (flow.isExceptionCatch()) {
                CFGException catchNode = (CFGException)flow.getDstNode();
                for (CFGException exceptionNode : callee.getExceptionNodes()) {
                    if (getCatchTypes(exceptionNode.getTypeBinding()).contains(catchNode.getTypeName())) {
                        CD edge = new CD(exceptionNode.getPDGNode(), catchNode.getPDGNode());
                        edge.setExceptionCatch();
                        graph.add(edge);
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
    
    public static void connectFieldAccesses(DependencyGraph graph) {
        Set<CFGFieldEntry> fieldEntries = graph.getPDGs()
                .stream()
                .map(pdg -> pdg.getCFG())
                .filter(cfg -> cfg.isField())
                .map(cfg -> (CFGFieldEntry)cfg.getEntryNode())
                .collect(Collectors.toSet());
        
        for (PDG pdg : graph.getPDGs()) {
            CFG cfg = pdg.getCFG();
            for (CFGNode node : cfg.getNodes()) {
                if (node.isStatement()) {
                    CFGStatement stNode = (CFGStatement)node;
                    stNode.getDefVariables().stream()
                        .filter(var -> var.isFieldAccess())
                        .forEach(var -> connectDefFieldAccesses(graph, node, (JFieldReference)var));
                    stNode.getUseVariables().stream()
                        .filter(var -> var.isFieldAccess())
                        .forEach(var -> connectUseFieldAccesses(fieldEntries, graph, node, (JFieldReference)var));
                }
            }
        }
    }
    
    private static void connectDefFieldAccesses(DependencyGraph graph, CFGNode node, JFieldReference fvar) {
        if (fvar.isComplementary()) {
            JComplementaryFieldReference cvar = (JComplementaryFieldReference)fvar;
            cvar.getHoldingNodes().forEach(n -> {
                addComplementaryFieldAccessEdge(graph, n.getPDGNode(), node.getPDGNode());
            });
        }
    }
    
    private static void addComplementaryFieldAccessEdge(DependencyGraph graph, PDGNode src, PDGNode dst) {
        List<Dependence> edges = graph.getDependence(src, dst);
        DD edge = (DD)edges.stream()
                           .filter(e -> e.isComplementaryFieldAccess())
                           .findFirst().orElse(null);
        if (edge == null) {
            edge = new DD(src, dst);
            edge.setComplementaryFieldAccess();
            graph.add(edge);
        }
    }
    
    private static void connectUseFieldAccesses(Set<CFGFieldEntry> fieldEntries,
            DependencyGraph graph, CFGNode node, JFieldReference fvar) {
        for (CFGFieldEntry fieldEntry : fieldEntries) {
            if (fieldEntry.getQualifiedName().equals(fvar.getQualifiedName())) {
                DD edge = new DD(fieldEntry.getDeclarationNode().getPDGNode(), node.getPDGNode(), fvar);
                edge.setFieldAccess();
                graph.add(edge);
            }
        }
    }
    
    private static void findSummaryEdges(DependencyGraph graph) {
        Map<CFG, Set<PDGStatement>> finsMap = new HashMap<>();
        for (PDG pdg : graph.getPDGs()) {
            for (PDGNode node : pdg.getNodes()) {
                CFGNode cfgnode = node.getCFGNode();
                if (cfgnode.isMethodCall()) {
                    CFGMethodCall caller = (CFGMethodCall)cfgnode;
                    for (String className : caller.getApproximatedTypeNames()) {
                        QualifiedName qname = new QualifiedName(className, caller.getSignature());
                        PDG callee = graph.findPDG(qname.fqn());
                        if (callee != null) {
                            findSummaryEdges(graph, pdg, caller, callee.getCFG(), finsMap);
                        } else {
                            findConservativelySummaryEdges(graph, pdg, caller);
                        }
                    }
                }
            }
        }
    }
    
    private static void findSummaryEdges(DependencyGraph graph, PDG pdg,
            CFGMethodCall caller, CFG callee, Map<CFG, Set<PDGStatement>> finsMap) {
        Set<PDGStatement> actualIns = caller.getActualIns().stream()
                                            .map(node -> (PDGStatement)node.getPDGNode())
                                            .collect(Collectors.toSet());
        PDGStatement actualOut = (PDGStatement)caller.getActualOut().getPDGNode();
        CFGMethodEntry entry = (CFGMethodEntry)callee.getEntryNode();
        Set<PDGStatement> formalIns = entry.getFormalIns().stream()
                                           .map(node -> (PDGStatement)node.getPDGNode())
                                           .collect(Collectors.toSet());
        
        PDGStatement formalOut = (PDGStatement)entry.getFormalOut().getPDGNode();
        
        Set<PDGStatement> nodes = new HashSet<>();
        Set<PDGStatement> fins = new HashSet<>();
        traverseBackward(graph, formalOut, formalIns, fins, nodes);
        
        for (PDGStatement fin : fins) {
            PDGStatement ain = graph.getIncomingDDEdges(fin).stream()
                                    .map(edge -> (PDGStatement)edge.getSrcNode())
                                    .filter(node -> actualIns.contains(node))
                                    .findFirst().orElse(null);
            if (ain != null) {
                DD edge = new DD(ain, actualOut);
                edge.setSummary();
                pdg.add(edge);
                graph.add(edge);
                
                if (graph.isSDG()) {
                    SDG sdg = (SDG)graph;
                    ClDG cldg = sdg.findClDG(pdg.getQualifiedName().getClassName());
                    cldg.add(edge);
                }
            }
        }
    }
    
    private static void findConservativelySummaryEdges(DependencyGraph graph, PDG pdg,
            CFGMethodCall caller) {
        PDGNode aoutForReturn = caller.getActualOut().getPDGNode();
        for (CFGStatement ain : caller.getActualIns()) {
            DD edge = new DD(ain.getPDGNode(), aoutForReturn);
            edge.setSummary();
            pdg.add(edge);
        }
    }
    
    private static void traverseBackward(DependencyGraph graph, PDGStatement startnode,
            Set<PDGStatement> formalIns, Set<PDGStatement> fins, Set<PDGStatement> nodes) {
        Stack<PDGStatement> nodeStack = new Stack<>();
        nodeStack.push(startnode);
        
        while (!nodeStack.isEmpty()) {
            PDGStatement node = nodeStack.pop();
            
            if (nodes.contains(node)) {
                continue;
            }
            nodes.add(node);
            
            for (Dependence edge : graph.getIncomingDDEdges(node)) {
                PDGStatement src = (PDGStatement)edge.getSrcNode();
                if (formalIns.contains(src)) {
                    fins.add(src);
                    nodes.add(src);
                } else if (!nodes.contains(src)) {
                    nodeStack.push(src);
                }
            }
        }
    }
}
