/*
 *  Copyright 2022
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.pdg.builder;

import org.jtool.pdg.PDG;
import org.jtool.pdg.ClDG;
import org.jtool.pdg.SDG;
import org.jtool.pdg.PDGNode;
import org.jtool.pdg.PDGStatement;
import org.jtool.pdg.CD;
import org.jtool.pdg.DD;
import org.jtool.pdg.Dependence;
import org.jtool.pdg.DependencyGraph;
import org.jtool.cfg.CFG;
import org.jtool.cfg.CCFG;
import org.jtool.cfg.CFGException;
import org.jtool.cfg.CFGNode;
import org.jtool.cfg.CFGParameter;
import org.jtool.cfg.CFGStatement;
import org.jtool.cfg.ControlFlow;
import org.jtool.cfg.CFGMethodEntry;
import org.jtool.cfg.CFGFieldEntry;
import org.jtool.cfg.CFGMethodCall;
import org.jtool.cfg.JVariableReference;
import org.jtool.cfg.JFieldReference;
import org.jtool.cfg.JUncoveredFieldReference;
import org.jtool.cfg.builder.CFGStore;
import org.jtool.srcmodel.JavaClass;
import org.jtool.srcmodel.JavaField;
import org.jtool.srcmodel.JavaMethod;
import org.jtool.srcmodel.QualifiedName;
import org.eclipse.jdt.core.dom.ITypeBinding;
import java.util.Map;
import java.util.HashMap;
import java.util.Set;
import java.util.HashSet;
import java.util.Stack;
import java.util.List;
import java.util.stream.Collectors;

/**
 * An object that stores information on PDGs in the project.
 * 
 * @author Katsuhisa Maruyama
 */
public class PDGStore {
    
    private CFGStore cfgStore;
    
    private Map<String, BarePDG> bpdgMap = new HashMap<>();
    
    private Map<String, ClDG> cldgMap = new HashMap<>();
    private Map<String, PDG> pdgMap = new HashMap<>();
    
    public PDGStore(CFGStore cfgStore) {
        this.cfgStore = cfgStore;
    }
    
    public void destroy() {
        bpdgMap.clear();
        pdgMap.clear();
        cldgMap.clear();
        cfgStore.destroy();
    }
    
    public BarePDG findBarePDG(String fqn) {
        return bpdgMap.get(fqn);
    }
    
    public ClDG findClDG(String fqn) {
        return cldgMap.get(fqn);
    }
    
    public PDG findPDG(String fqn) {
        return pdgMap.get(fqn);
    }
    
    public PDG getPDG(JavaField jfield, boolean force, boolean whole) {
        return getPDG(jfield.getQualifiedName().fqn(), jfield.getDeclaringClass(), force, whole);
    }
    
    public PDG getPDG(JavaMethod jmethod, boolean force, boolean whole) {
        return getPDG(jmethod.getQualifiedName().fqn(), jmethod.getDeclaringClass(), force, whole);
    }
    
    public PDG getPDG(CFG cfg, boolean force, boolean whole) {
        if (cfg.isMethod()) {
            JavaMethod jmethod = ((CFGMethodEntry)cfg.getEntryNode()).getJavaMethod();
            return getPDG(jmethod, force, whole);
        } else {
            JavaField jfield = ((CFGFieldEntry)cfg.getEntryNode()).getJavaField();
            return getPDG(jfield, force, whole);
        }
    }
    
    private PDG getPDG(String fqn, JavaClass jclass, boolean force, boolean whole) {
        if (!force) {
            PDG pdg = pdgMap.get(fqn);
            if (pdg != null) {
                return pdg;
            }
        }
        
        ClDG cldg = getClDG(jclass.getQualifiedName().fqn(), jclass, force, whole);
        if (cldg == null) {
            return null;
        }
        return cldg.findPDG(fqn);
    }
    
    public ClDG getClDG(JavaClass jclass, boolean force, boolean whole) {
        return getClDG(jclass.getQualifiedName().fqn(), jclass, force, whole);
    }
    
    public ClDG getClDG(CCFG ccfg, boolean force, boolean whole) {
        JavaClass jclass = ccfg.getEntryNode().getJavaClass();
        return getClDG(jclass, force, whole);
    }
    
    private ClDG getClDG(String fqn, JavaClass jclass, boolean force, boolean whole) {
        assert jclass != null;
        
        if (!force) {
            ClDG cldg = cldgMap.get(fqn);
            if (cldg != null) {
                return cldg;
            }
        } else {
            cldgMap.remove(jclass.getQualifiedName().fqn());
            jclass.getMethods().forEach(jmethod -> bpdgMap.remove(jmethod.getQualifiedName().fqn()));
            jclass.getFields().forEach(jfield -> bpdgMap.remove(jfield.getQualifiedName().fqn()));
        }
        
        CCFG ccfg = cfgStore.getCCFG(jclass, force);
        if (ccfg == null) {
            return null;
        }
        
        if (whole) {
            SDG sdg = getSDG(getColleagues(jclass), force, true);
            ClDG cldg = sdg.findClDG(fqn);
            if (cldg != null) {
                cldgMap.put(cldg.getQualifiedName().fqn(), cldg);
                cldg.getPDGs().forEach(pdg -> pdgMap.put(pdg.getQualifiedName().fqn(), pdg));
            }
            return cldg;
        } else {
            ClDG cldg = PDGBuilder.buildClDG(ccfg, bpdgMap);
            findConnection(cldg);
            return cldg;
        }
    }
    
    public SDG getSDG(boolean force) {
        Set<JavaClass> classes = new HashSet<>(cfgStore.getJavaProject().getClasses());
        return getSDG(classes, force, true);
    }
    
    public SDG getSDG(JavaClass jclass, boolean force) {
        return getSDG(jclass, force, true);
    }
    
    public SDG getSDG(JavaClass jclass, boolean force, boolean whole) {
        assert jclass != null;
        
        if (whole) {
            return getSDG(getColleagues(jclass), force, true);
        } else {
            Set<JavaClass> classes = new HashSet<>();
            classes.add(jclass);
            return getSDG(classes, force, false);
        }
    }
    
    public SDG getSDG(Set<JavaClass> classes, boolean force, boolean whole) {
        SDG sdg = new SDG();
        for (JavaClass jclass : classes) {
            if (!force) {
                ClDG cldg = cldgMap.get(jclass.getQualifiedName().fqn());
                if (cldg != null) {
                    sdg.add(cldg);
                    continue;
                } else {
                    cldgMap.remove(jclass.getQualifiedName().fqn());
                    jclass.getMethods().forEach(jmethod -> bpdgMap.remove(jmethod.getQualifiedName().fqn()));
                    jclass.getFields().forEach(jfield -> bpdgMap.remove(jfield.getQualifiedName().fqn()));
                }
            }
            
            CCFG ccfg = cfgStore.getCCFG(jclass, force);
            if (ccfg != null) {
                ClDG cldg = PDGBuilder.buildClDG(ccfg, bpdgMap);
                if (cldg != null) {
                    sdg.add(cldg);
                    if (whole) {
                        cldgMap.put(cldg.getQualifiedName().fqn(), cldg);
                        cldg.getPDGs().forEach(pdg -> pdgMap.put(pdg.getQualifiedName().fqn(), pdg));
                    }
                }
            }
        }
        
        findConnection(sdg);
        return sdg;
    }
    
    public ClDG generateUnregisteredClDG(CCFG ccfg) {
        return PDGBuilder.buildClDG(ccfg, bpdgMap);
    }
    
    Set<JavaClass> getColleagues(JavaClass jclass) {
        Set<JavaClass> allClasses = new HashSet<>();
        collectColleagues(jclass, allClasses);
        return allClasses;
    }
    
    Set<JavaClass> getColleague(Set<JavaClass> classes) {
        Set<JavaClass> allClasses = new HashSet<>();
        for (JavaClass jclass : classes) {
            collectColleagues(jclass, allClasses);
        }
        return allClasses;
    }
    
    private void collectColleagues(JavaClass jclass, Set<JavaClass> classes) {
        assert jclass != null;
        
        if (classes.contains(jclass)) {
            return;
        }
        classes.add(jclass);
        jclass.getDescendants().stream()
                .filter(descendant -> descendant.isInProject())
                .forEach(descendant -> classes.add(descendant));
        
        for (JavaClass jc : jclass.getEfferentClassesInProject()) {
            collectColleagues(jc, classes);
        }
    }
    
    public void findConnection(DependencyGraph graph) {
        List<CFGNode> nodes = graph.getNodes().stream()
                .map(node -> node.getCFGNode())
                .collect(Collectors.toList());
        List<CFGMethodCall> callers = nodes.stream()
                .filter(node -> node.isMethodCall())
                .map(node -> (CFGMethodCall)node)
                .collect(Collectors.toList());
        
        connectMethodCalls(graph, callers);
        connectFieldAccesses(graph, nodes);
    }
    
    private void connectMethodCalls(DependencyGraph graph, List<CFGMethodCall> callers) {
        findCalls(graph, callers);
        findSummaryEdges(graph, callers);
    }
    
    private void findCalls(DependencyGraph graph, List<CFGMethodCall> callers) {
        for (CFGMethodCall caller : callers) {
            for (String className : caller.getApproximatedTypeNames()) {
                QualifiedName qname = new QualifiedName(className, caller.getSignature());
                PDG callee = graph.findPDG(qname.fqn());
                if (callee != null) {
                    Dependence edge = new Dependence(caller.getPDGNode(), callee.getEntryNode());
                    edge.setCall();
                    graph.add(edge);
                    
                    connectParameters(graph, caller, (CFGMethodEntry)callee.getCFG().getEntryNode());
                    connectExceptionCatch(graph, caller, (CFGMethodEntry)callee.getCFG().getEntryNode());
                }
            }
        }
    }
    
    private void connectParameters(DependencyGraph graph, CFGMethodCall caller, CFGMethodEntry callee) {
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
    
    private void connectExceptionCatch(DependencyGraph graph, CFGMethodCall caller, CFGMethodEntry callee) {
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
    
    private Set<String> getCatchTypes(ITypeBinding type) {
        Set<String> types = new HashSet<>();
        while (type != null) {
            types.add(type.getQualifiedName());
            type = type.getSuperclass();
        }
        return types;
    }
    
    private void findSummaryEdges(DependencyGraph graph, List<CFGMethodCall> callers) {
        Map<CFG, Set<PDGStatement>> finsMap = new HashMap<>();
        for (CFGMethodCall caller : callers) {
            for (String className : caller.getApproximatedTypeNames()) {
                QualifiedName qname = new QualifiedName(className, caller.getSignature());
                PDG callee = graph.findPDG(qname.fqn());
                if (callee != null) {
                    findSummaryEdges(graph, caller, callee.getCFG(), finsMap);
                } else {
                    findConservativelySummaryEdges(graph, caller);
                }
            }
        }
    }
    
    private void findSummaryEdges(DependencyGraph graph,
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
                graph.add(edge);
            }
        }
    }
    
    private void findConservativelySummaryEdges(DependencyGraph graph,
            CFGMethodCall caller) {
        PDGNode aoutForReturn = caller.getActualOut().getPDGNode();
        for (CFGStatement ain : caller.getActualIns()) {
            DD edge = new DD(ain.getPDGNode(), aoutForReturn);
            edge.setSummary();
            graph.add(edge);
        }
    }
    
    private void traverseBackward(DependencyGraph graph, PDGStatement startnode,
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
    
    private void connectFieldAccesses(DependencyGraph graph, List<CFGNode> nodes) {
        Set<CFGFieldEntry> fieldEntries = graph.getPDGs().stream()
                .map(pdg -> pdg.getCFG())
                .filter(cfg -> cfg.isField())
                .map(cfg -> (CFGFieldEntry)cfg.getEntryNode())
                .collect(Collectors.toSet());
        
        List<CFGStatement> stnodes = nodes.stream()
                .filter(node -> node.isStatement())
                .map(node -> (CFGStatement)node)
                .collect(Collectors.toList());
        
        for (CFGStatement node : stnodes) {
            node.getDefVariables().stream()
                    .filter(var -> var.isFieldAccess())
                    .forEach(var -> connectDefFieldAccesses(graph, node, (JFieldReference)var));
            node.getUseVariables().stream()
                    .filter(var -> var.isFieldAccess())
                    .forEach(var -> connectUseFieldAccesses(fieldEntries, graph, node, (JFieldReference)var));
        }
    }
    
    private void connectDefFieldAccesses(DependencyGraph graph, CFGNode node, JFieldReference fvar) {
        if (fvar.isUncoveredFieldReference()) {
            JUncoveredFieldReference cvar = (JUncoveredFieldReference)fvar;
            cvar.getHoldingNodes().forEach(n -> {
                addUncoveredFieldAccessEdge(graph, n.getPDGNode(), node.getPDGNode());
            });
        }
    }
    
    private void addUncoveredFieldAccessEdge(DependencyGraph graph, PDGNode src, PDGNode dst) {
        List<Dependence> edges = graph.getDependence(src, dst);
        DD edge = (DD)edges.stream()
                           .filter(e -> e.isUncoveredFieldAccess())
                           .findFirst().orElse(null);
        if (edge == null) {
            edge = new DD(src, dst);
            edge.setUncoveredFieldAccess();
            graph.add(edge);
        }
    }
    
    private void connectUseFieldAccesses(Set<CFGFieldEntry> fieldEntries,
            DependencyGraph graph, CFGNode node, JFieldReference fvar) {
        for (CFGFieldEntry fieldEntry : fieldEntries) {
            if (fieldEntry.getQualifiedName().equals(fvar.getQualifiedName())) {
                DD edge = new DD(fieldEntry.getDeclarationNode().getPDGNode(), node.getPDGNode(), fvar);
                edge.setFieldAccess();
                graph.add(edge);
            }
        }
    }
}
