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
            PDG pdg = pdgs.get(fqn);
            if (pdg != null) {
                return pdg;
            }
        }
        
        ClDG cldg = getClDG(jclass.getQualifiedName().fqn(), jclass, force, whole);
        if (cldg == null) {
            return null;
        }
        return cldg.getPDG(fqn);
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
        
        if (force) {
            cldgs.remove(jclass.getQualifiedName().fqn());
            jclass.getMethods().forEach(jmethod -> pdgs.remove(jmethod.getQualifiedName().fqn()));
            jclass.getFields().forEach(jfield -> pdgs.remove(jfield.getQualifiedName().fqn()));
        } else {
            ClDG cldg = cldgs.get(fqn);
            if (cldg != null) {
                return cldg;
            }
        }
        
        CCFG ccfg = cfgStore.getCCFG(jclass, force);
        if (ccfg == null) {
            return null;
        }
        
        if (whole) {
            DependencyGraph graph = new DependencyGraph(jclass.getQualifiedName().fqn());
            Set<JavaClass> classes = CFGStore.getColleagues(jclass);
            createDependencyGraph(graph, classes, force, whole);
            ClDG cldg = graph.getClDG(fqn);
            if (cldg != null) {
                cldgs.put(cldg.getQualifiedName().fqn(), cldg);
                cldg.getPDGs().forEach(pdg -> pdgs.put(pdg.getQualifiedName().fqn(), pdg));
            }
            return cldg;
        } else {
            ClDG cldg = PDGBuilder.buildClDG(ccfg);
            return cldg;
        }
    }
    
    public SDG getSDG(boolean force) {
        Set<JavaClass> classes = new HashSet<>(cfgStore.getJavaProject().getClasses());
        SDG sdg = new SDG();
        createDependencyGraph(sdg, classes, force, true);
        return sdg;
    }
    
    public DependencyGraph getDependencyGraph(JavaClass jclass, boolean force) {
        return getDependencyGraph(jclass, force, true);
    }
    
    public DependencyGraph getDependencyGraph(JavaClass jclass, boolean force, boolean whole) {
        assert jclass != null;
        
        DependencyGraph graph = new DependencyGraph(jclass.getQualifiedName().fqn());
        if (whole) {
            Set<JavaClass> classes = CFGStore.getColleagues(jclass);
            createDependencyGraph(graph, classes, force, whole);
            return graph;
        } else {
            Set<JavaClass> classes = new HashSet<>();
            classes.add(jclass);
            createDependencyGraph(graph, classes, force, whole);
        }
        return graph;
    }
    
    public DependencyGraph getDependencyGraph(Set<JavaClass> classes, boolean force, boolean whole) {
        assert classes.size() > 0;
        
        DependencyGraph graph = new DependencyGraph(classes.iterator().next().getQualifiedName().fqn());
        if (whole) {
            for (JavaClass jclass : classes) {
                classes.addAll(CFGStore.getColleagues(jclass));
            }
        }
        createDependencyGraph(graph, classes, force, whole);
        return graph;
    }
    
    private void createDependencyGraph(DependencyGraph graph,
            Set<JavaClass> classes, boolean force, boolean whole) {
        if (force) {
            removePDGs(classes);
        }
        
        for (JavaClass jclass : classes) {
            ClDG cldg = cldgs.get(jclass.getQualifiedName().fqn());
            if (cldg != null) {
                graph.add(cldg);
                continue;
            }
            
            CCFG ccfg = cfgStore.getCCFG(jclass, force);
            if (ccfg != null) {
                cldg = PDGBuilder.buildClDG(ccfg);
                if (cldg != null) {
                    graph.add(cldg);
                    if (whole) {
                        cldgs.put(cldg.getQualifiedName().fqn(), cldg);
                        cldg.getPDGs().forEach(pdg -> pdgs.put(pdg.getQualifiedName().fqn(), pdg));
                    }
                }
            }
        }
        
        collectInterPDGEdges(graph);
    }
    
    private void removePDGs(Set<JavaClass> classes) {
        for (JavaClass jc : classes) {
            cldgs.remove(jc.getQualifiedName().fqn());
            for (JavaClass jc2 : jc.getObsoleteClasses()) {
                jc2.getMethods().forEach(jmethod -> pdgs.remove(jmethod.getQualifiedName().fqn()));
                jc2.getFields().forEach(jfield -> pdgs.remove(jfield.getQualifiedName().fqn()));
            }
        }
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
            jvar.getHoldingNodes().forEach(n -> {
                addUncoveredFieldAccessEdge(graph, n.getPDGNode(), node.getPDGNode(), jvar);
            });
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
    
    public ClDG generateUnregisteredClDG(CCFG ccfg) {
        return PDGBuilder.buildClDG(ccfg);
    }
    
    public PDG generateUnregisteredPDG(CFG cfg) {
        return PDGBuilder.buildPDG(cfg);
    }
}
