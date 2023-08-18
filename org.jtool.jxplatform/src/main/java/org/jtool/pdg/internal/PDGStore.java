/*
 *  Copyright 2022-2023
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.pdg.internal;

import org.jtool.pdg.PDG;
import org.jtool.pdg.ClDG;
import org.jtool.pdg.SDG;
import org.jtool.pdg.PDG;
import org.jtool.pdg.DependencyGraph;
import org.jtool.pdg.CD;
import org.jtool.pdg.DD;
import org.jtool.pdg.Dependence;
import org.jtool.pdg.InterPDGEdge;
import org.jtool.pdg.InterPDGCD;
import org.jtool.pdg.InterPDGDD;
import org.jtool.pdg.PDGNode;
import org.jtool.pdg.PDGStatement;
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
import org.jtool.cfg.internal.CFGStore;
import org.jtool.cfg.internal.CallGraphBuilder;
import org.jtool.cfg.JFieldReference;
import org.jtool.cfg.JUncoveredFieldReference;
import org.jtool.cfg.CFGEntry;
import org.jtool.cfg.CallGraph;
import org.jtool.srcmodel.JavaClass;
import org.jtool.srcmodel.JavaField;
import org.jtool.srcmodel.JavaMethod;
import org.jtool.srcmodel.JavaProject;
import org.jtool.srcmodel.QualifiedName;
import org.eclipse.jdt.core.dom.ITypeBinding;
import java.util.Map;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
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
@SuppressWarnings("unused")
public class PDGStore {
    
    private CFGStore cfgStore;
    
    private Map<String, PDG> pdgs = new HashMap<>();
    private Map<String, ClDG> cldgs = new HashMap<>();
    
    public PDGStore(CFGStore cfgStore) {
        this.cfgStore = cfgStore;
    }
    
    public void clear() {
        pdgs.clear();
        cldgs.clear();
    }
    
    public void destroy() {
        pdgs.clear();
        cldgs.clear();
        cfgStore.destroy();
    }
    
    public JavaProject getJavaProject() {
        return cfgStore.getJavaProject();
    }
    
    public Collection<ClDG> getClDGs() {
        return cldgs.values();
    }
    
    public Collection<PDG> getPDGs() {
        return pdgs.values();
    }
    
    public PDG findPDG(String fqn) {
        return pdgs.get(fqn);
    }
    
    public ClDG findClDG(String fqn) {
        return cldgs.get(fqn);
    }
    
    private void removePDGs(JavaClass jclass) {
        removePDGs(new HashSet<>(Arrays.asList(jclass)));
    }
    
    private void removePDGs(Set<JavaClass> classes) {
        for (JavaClass jclass : classes) {
            cldgs.remove(jclass.getQualifiedName().fqn());
            for (JavaClass jc : jclass.getObsoleteClasses()) {
                jc.getMethods().forEach(jmethod -> pdgs.remove(jmethod.getQualifiedName().fqn()));
                jc.getFields().forEach(jfield -> pdgs.remove(jfield.getQualifiedName().fqn()));
            }
        }
    }
    
    public ClDG getClDG(CCFG ccfg, boolean force, boolean whole) {
        assert ccfg != null;
        
        return getClDG(ccfg.getEntryNode().getJavaClass(), force, whole);
    }
    
    public ClDG getClDG(JavaClass jclass, boolean force, boolean whole) {
        assert jclass != null;
        
        if (force) {
            cfgStore.removeCFGs(jclass);
            removePDGs(jclass);
        }
        return getClDG(jclass, whole);
    }
    
    public ClDG getClDG(JavaClass jclass, boolean whole) {
        assert jclass != null;
        
        String fqn = jclass.getQualifiedName().fqn();
        if (whole) {
            DependencyGraph graph = new DependencyGraph(jclass.getQualifiedName().fqn());
            Set<JavaClass> classes = getColleagues(jclass);
            createDependencyGraph(graph, classes, whole);
            return graph.getClDG(fqn);
        }
        
        CCFG ccfg = cfgStore.getCCFG(jclass);
        if (ccfg == null) {
            return null;
        }
        return PDGBuilder.buildClDG(jclass, ccfg);
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
    
    public PDG getPDG(JavaField jfield, boolean force, boolean whole) {
        return getPDG(jfield.getQualifiedName().fqn(), jfield.getDeclaringClass(), force, whole);
    }
    
    public PDG getPDG(JavaMethod jmethod, boolean force, boolean whole) {
        return getPDG(jmethod.getQualifiedName().fqn(), jmethod.getDeclaringClass(), force, whole);
    }
    
    private PDG getPDG(String fqn, JavaClass jclass, boolean force, boolean whole) {
        if (force) {
            cfgStore.removeCFGs(jclass);
            removePDGs(jclass);
        }
        
        ClDG cldg = getClDG(jclass, whole);
        if (cldg == null) {
            return null;
        }
        return cldg.getPDG(fqn);
    }
    
    public SDG getSDG(boolean force) {
        if (force) {
            cfgStore.clear();
            clear();
        }
        
        Set<JavaClass> classes = new HashSet<>(cfgStore.getJavaProject().getClasses());
        SDG sdg = new SDG();
        
        createDependencyGraph(sdg, classes, true);
        return sdg;
    }
    
    public DependencyGraph getDependencyGraph(JavaClass jclass, boolean force, boolean whole) {
        return getDependencyGraph(new HashSet<>(Arrays.asList(jclass)), force, whole);
    }
    
    public DependencyGraph getDependencyGraph(Set<JavaClass> classes, boolean force, boolean whole) {
        assert classes.size() > 0;
        
        if (force) {
            cfgStore.removeCFGs(classes);
            removePDGs(classes);
        }
        
        DependencyGraph graph = new DependencyGraph(classes.iterator().next().getQualifiedName().fqn());
        if (whole) {
            Set<JavaClass> wholeClasses = getColleague(classes);
            createDependencyGraph(graph, wholeClasses, whole);
        } else {
            createDependencyGraph(graph, classes, whole);
        }
        return graph;
    }
    
    private void createDependencyGraph(DependencyGraph graph, Set<JavaClass> classes, boolean whole) {
        for (JavaClass jclass : classes) {
            ClDG cldg = cldgs.get(jclass.getQualifiedName().fqn());
            if (cldg != null) {
                graph.add(cldg);
            } else {
                CCFG ccfg = cfgStore.getCCFG(jclass);
                if (ccfg != null) {
                    cldg = PDGBuilder.buildClDG(jclass, ccfg);
                    if (cldg != null) {
                        graph.add(cldg);
                    }
                }
            }
        }
        
        if (whole) {
            collectInterPDGEdges(graph);
            
            graph.getClDGs().forEach(cldg -> {
                cldgs.put(cldg.getQualifiedName().fqn(), cldg);
                cldg.getPDGs().forEach(pdg -> pdgs.put(pdg.getQualifiedName().fqn(), pdg));
            });
        }
    }
    
    public static Set<JavaClass> getColleague(Set<JavaClass> classes) {
        Set<JavaClass> allClasses = new HashSet<>();
        for (JavaClass jclass : classes) {
            allClasses.addAll(getColleagues(jclass));
        }
        return allClasses;
    }
    
    public static Set<JavaClass> getColleagues(JavaClass jclass) {
        assert jclass != null;
        
        Set<JavaClass> classes = new HashSet<>();
        
        Stack<JavaClass> classStack = new Stack<>();
        classStack.push(jclass);
        
        while (!classStack.isEmpty()) {
            JavaClass jc = classStack.pop();
            
            if (classes.contains(jc)) {
                continue;
            }
            classes.add(jc);
            
            jc.getEfferentClassesInProject().forEach(c -> classStack.push(c));
        }
        return classes;
    }
    
    public void collectInterPDGEdges(DependencyGraph graph) {
        Set<CFGMethodCall> callers = new HashSet<>();
        Set<CFGStatement> stnodes = new HashSet<>();
        graph.getNodes().stream().map(node -> node.getCFGNode()).forEach(node -> {
            if (node.isMethodCall()) {
                callers.add((CFGMethodCall)node);
            }
            if (node.isStatement()) {
                stnodes.add((CFGStatement)node);
            }
        });
        
        connectMethodCalls(graph, callers);
        connectFieldAccesses(graph, stnodes);
    }
    
    private void connectMethodCalls(DependencyGraph graph, Set<CFGMethodCall> callers) {
        checkCalls(graph, callers);
        collectSummaryEdges(graph, callers);
    }
    
    private void checkCalls(DependencyGraph graph, Set<CFGMethodCall> callers) {
        for (CFGMethodCall caller : callers) {
            for (String className : caller.getApproximatedTypeNames()) {
                QualifiedName qname = new QualifiedName(className, caller.getSignature());
                PDG callee = graph.getPDG(qname.fqn());
                
                if (callee != null) {
                    InterPDGCD edge = new InterPDGCD(caller.getPDGNode(), callee.getEntryNode());
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
            for (int ordinal = 0; ordinal < caller.getActualIns().size() - 1; ordinal++) {
                CFGParameter actualIn = caller.getActualIn(ordinal);
                if (ordinal < callee.getFormalIns().size()) {
                    formalIn = callee.getFormalIn(ordinal);
                } else {
                    formalIn = callee.getFormalIn(callee.getFormalIns().size() - 1);
                }
                
                JVariableReference jvar = formalIn.getDefFirst();
                InterPDGDD edge = new InterPDGDD(actualIn.getPDGNode(), formalIn.getPDGNode(), jvar);
                edge.setParameterIn();
                graph.add(edge);
            }
            
        } else {
            for (int ordinal = 0; ordinal < caller.getActualIns().size(); ordinal++) {
                CFGParameter actualIn = caller.getActualIn(ordinal);
                CFGParameter formalIn = callee.getFormalIn(ordinal);
                
                JVariableReference jvar = formalIn.getDefFirst();
                InterPDGDD edge = new InterPDGDD(actualIn.getPDGNode(), formalIn.getPDGNode(), jvar);
                edge.setParameterIn();
                graph.add(edge);
            }
        }
        
        CFGParameter actualOut = caller.getActualOut();
        CFGParameter formalOut = callee.getFormalOut();
        
        if (formalOut != null) {
            JVariableReference jvar = formalOut.getUseFirst();
            InterPDGDD edge = new InterPDGDD(formalOut.getPDGNode(), actualOut.getPDGNode(), jvar);
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
                        InterPDGCD edge = new InterPDGCD(exceptionNode.getPDGNode(), catchNode.getPDGNode());
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
    
    private void collectSummaryEdges(DependencyGraph graph, Set<CFGMethodCall> callers) {
        for (CFGMethodCall caller : callers) {
            for (String className : caller.getApproximatedTypeNames()) {
                QualifiedName qname = new QualifiedName(className, caller.getSignature());
                PDG callee = graph.getPDG(qname.fqn());
                if (callee == null) {
                    collectConservativelySummaryEdges(graph, caller);
                }
            }
        }
    }
    
    private void collectConservativelySummaryEdges(DependencyGraph graph,
            CFGMethodCall caller) {
        PDGNode aoutForReturn = caller.getActualOut().getPDGNode();
        for (CFGStatement ain : caller.getActualIns()) {
            InterPDGDD edge = new InterPDGDD(ain.getPDGNode(), aoutForReturn);
            edge.setSummary();
            graph.add(edge);
        }
    }
    
    private void connectFieldAccesses(DependencyGraph graph, Set<CFGStatement> stnodes) {
        Map<String, CFGFieldEntry> fieldEntryMap = graph.getPDGs().stream()
                .map(pdg -> pdg.getCFG()).filter(cfg -> cfg.isField())
                .collect(Collectors.toMap(cfg -> cfg.getQualifiedName().fqn(), cfg -> (CFGFieldEntry)cfg.getEntryNode()));
        
        stnodes.stream().forEach(node -> {
            node.getDefVariables().stream()
                    .filter(var -> var.isFieldAccess())
                    .forEach(var -> connectDefFieldAccesses(graph, node, (JFieldReference)var));
            node.getUseVariables().stream()
                    .filter(var -> var.isFieldAccess())
                    .forEach(var -> connectUseFieldAccesses(graph, node, (JFieldReference)var, fieldEntryMap));
        });
    }
    
    private void connectDefFieldAccesses(DependencyGraph graph, CFGNode node, JFieldReference fvar) {
        if (fvar.isUncoveredFieldReference()) {
            JUncoveredFieldReference jvar = (JUncoveredFieldReference)fvar;
            jvar.getHoldingNodes().stream()
                    .map(n -> n.getPDGNode())
                    .filter(n -> n != null)
                    .forEach(n -> addUncoveredFieldAccessEdge(graph, n, node.getPDGNode(), jvar));
        }
    }
    
    private void addUncoveredFieldAccessEdge(DependencyGraph graph, PDGNode src, PDGNode dst,
            JUncoveredFieldReference jvar) {
        if (!graph.existsUncoveredFieldAccessEdge(src, dst)) {
            InterPDGDD edge = new InterPDGDD(src, dst, jvar);
            edge.setUncoveredFieldAccess();
            graph.add(edge);
            graph.addUncoveredFieldAccessEdge(edge);
        }
    }
    
    private void connectUseFieldAccesses(DependencyGraph graph, CFGNode node, JFieldReference jvar,
            Map<String, CFGFieldEntry> fieldEntryMap) {
        CFGFieldEntry fieldEntry = fieldEntryMap.get(jvar.getQualifiedName().fqn());
        if (fieldEntry != null) {
            InterPDGDD edge = new InterPDGDD(fieldEntry.getDeclarationNode().getPDGNode(), node.getPDGNode(), jvar);
            edge.setFieldAccess();
            graph.add(edge);
        }
    }
    
    public ClDG generateUnregisteredClDG(JavaClass jclass, CCFG ccfg) {
        return PDGBuilder.buildClDG(jclass, ccfg);
    }
    
    public PDG generateUnregisteredPDG(JavaClass jclass, CFG cfg) {
        return PDGBuilder.buildPDG(jclass, cfg);
    }
}
