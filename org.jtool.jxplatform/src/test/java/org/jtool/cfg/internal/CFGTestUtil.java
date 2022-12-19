/*
 *  Copyright 2022
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.cfg.internal;

import org.jtool.srcmodel.JavaClass;
import org.jtool.srcmodel.JavaMethod;
import org.jtool.srcmodel.JavaField;
import org.jtool.srcmodel.JavaProject;
import org.jtool.graph.GraphElement;
import org.jtool.cfg.CFG;
import org.jtool.cfg.CCFG;
import org.jtool.cfg.ControlFlow;
import org.jtool.cfg.CFGNode;
import org.jtool.cfg.CFGEntry;
import org.jtool.cfg.BasicBlock;
import org.jtool.cfg.CFGStatement;
import org.jtool.cfg.CFGParameter;
import org.jtool.cfg.CFGMethodCall;
import org.jtool.cfg.CCFGEntry;
import org.jtool.cfg.CFGMethodEntry;
import org.jtool.cfg.CFGFieldEntry;
import org.jtool.cfg.CFGMerge;
import org.jtool.cfg.JReference;
import org.jtool.cfg.JUncoveredFieldReference;
import org.jtool.cfg.JMethodReference;
import org.jtool.cfg.JFieldReference;
import org.jtool.cfg.JLocalVarReference;
import org.jtool.cfg.JVariableReference;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.Comparator;

public class CFGTestUtil {
    
    public static CCFG createCCFG(JavaProject jproject, String cname) {
        JavaClass jclass = jproject.getClass(cname);
        return jproject.getModelBuilder().getCCFG(jclass, false);
    }
    
    public static CFG createCFG(JavaProject jproject, String cname, String member) {
        if (member.indexOf("(") != -1) {
            JavaMethod jmethod = jproject.getClass(cname).getMethod(member);
            return jproject.getModelBuilder().getCFG(jmethod, false);
        } else {
            JavaField jfield = jproject.getClass(cname).getField(member);
            return jproject.getModelBuilder().getCFG(jfield, false);
        }
    }
    
    public static CFGNode getNode(CFG cfg, int index) {
        return cfg.getNodes().stream()
                .filter(n -> n.getId() == index + cfg.getEntryNode().getId())
                .findFirst().orElse(null);
    }
    
    public static List<CFGNode> getNodes(CFG cfg, String kind) {
        return CFGNode.sortNodes(cfg.getNodes()).stream()
            .filter(n -> !n.isMerge())
            .filter(n -> n.getASTNode().getClass().getName().endsWith("." + kind))
            .collect(Collectors.toList());
    }
    
    public static List<CFGNode> getNodes(CFG cfg, CFGNode.Kind kind) {
        return CFGNode.sortNodes(cfg.getNodes()).stream()
            .filter(n -> n.getKind() == kind).collect(Collectors.toList());
    }
    
    public static List<ControlFlow> getEdges(CFG cfg, ControlFlow.Kind kind) {
        return ControlFlow.sortEdges(cfg.getEdges()).stream()
            .filter(e -> e.getKind() == kind).collect(Collectors.toList());
    }
    
    public static List<ControlFlow> getLCFlow(CFG cfg) {
        return ControlFlow.sortEdges(cfg.getEdges()).stream()
            .filter(e -> e.getLoopBack() != null).collect(Collectors.toList());
    }
    
    public static List<BasicBlock> getBasicBlocks(CFG cfg) {
        BasicBlockBuilder.create(cfg, false);
        return cfg.getBasicBlocks().stream()
                .sorted(Comparator.comparingLong(BasicBlock::getId))
                .collect(Collectors.toList());
    }
    
    public static List<String> getIdList(CFG cfg, Set<? extends CFGNode> set) {
        return set.stream().map(e -> String.valueOf(e.getId() - cfg.getEntryNode().getId())).sorted()
            .collect(Collectors.toList());
    }
    
    public static List<String> getIdList(CFG cfg, List<? extends CFGNode> list) {
        return list.stream().map(e -> String.valueOf(e.getId() - cfg.getEntryNode().getId()))
            .collect(Collectors.toList());
    }
    
    public static List<String> getIdListOfSrc(CFG cfg, Set<? extends ControlFlow> set) {
        Set<CFGNode> srcs = set.stream().map(e -> (CFGNode)e.getSrcNode()).collect(Collectors.toSet());
        return getIdList(cfg, srcs);
    }
    
    public static List<String> getIdListOfSrc(CFG cfg, List<? extends ControlFlow> set) {
        Set<CFGNode> srcs = set.stream().map(e -> (CFGNode)e.getSrcNode()).collect(Collectors.toSet());
        return getIdList(cfg, srcs);
    }
    
    public static List<String> getIdListOfDst(CFG cfg, Set<? extends ControlFlow> set) {
        Set<CFGNode> srcs = set.stream().map(e -> (CFGNode)e.getDstNode()).collect(Collectors.toSet());
        return getIdList(cfg, srcs);
    }
    
    public static List<String> getIdListOfDst(CFG cfg, List<? extends ControlFlow> set) {
        Set<CFGNode> srcs = set.stream().map(e -> (CFGNode)e.getDstNode()).collect(Collectors.toSet());
        return getIdList(cfg, srcs);
    }
    
    public static String asStrOfCFGNode(List<? extends CFGNode> set) {
        return set.stream().map(e -> String.valueOf(e.getId())).collect(Collectors.joining(";"));
    }
    
    public static String asSortedStrOfCFGEntry(Set<? extends CFGEntry> set) {
        return set.stream().map(e -> e.getQualifiedName().fqn()).sorted().collect(Collectors.joining(";"));
    }
    
    public static String asStrOfNode(CFG cfg, CFGNode node) {
        return String.valueOf(node.getId() - cfg.getEntryNode().getId());
    }
    
    public static String asStrOfEdge(CFG cfg, ControlFlow edge) {
        long srcId = edge.getSrcNode().getId() - cfg.getEntryNode().getId();
        long dstId = edge.getDstNode().getId() - cfg.getEntryNode().getId();
        return String.valueOf(srcId) + "->" + String.valueOf(dstId);
    }
    
    public static void print(CFG cfg, CFGNode.Kind kind) {
        List<CFGNode> nodes = getNodes(cfg, kind);
        nodes.forEach(e -> System.err.println(e.getId() + " " +
                e.getKind().toString() + " " + e.getClass().toString()));
    }
    
    public static List<JVersatileReference> getDefVersatileReference(CFG cfg) {
        return getDefReference(cfg, "JVersatileReference")
                .map(n -> (JVersatileReference)n).collect(Collectors.toList());
    }
    
    public static List<JVersatileReference> getUseVersatileReference(CFG cfg) {
        return getUseReference(cfg, "JVersatileReference")
                .map(n -> (JVersatileReference)n).collect(Collectors.toList());
    }
    
    public static List<JUncoveredFieldReference> getDefUncoveredFieldReference(CFG cfg) {
        return getDefReference(cfg, "JUncoveredFieldReference")
                .map(n -> (JUncoveredFieldReference)n).collect(Collectors.toList());
    }
    
    public static List<JUncoveredFieldReference> getUseUncoveredFieldReference(CFG cfg) {
        return getUseReference(cfg, "JUncoveredFieldReference")
                .map(n -> (JUncoveredFieldReference)n).collect(Collectors.toList());
    }
    
    public static List<JAliasReference> getDefAliasReference(CFG cfg) {
        return getDefReference(cfg, "JAliasReference")
                .map(n -> (JAliasReference)n).collect(Collectors.toList());
    }
    
    public static List<JAliasReference> getUseAliasReference(CFG cfg) {
        return getUseReference(cfg, "JAliasReference")
                .map(n -> (JAliasReference)n).collect(Collectors.toList());
    }
    
    public static List<JReturnValueReference> getDefReturnValueReference(CFG cfg) {
        return getDefReference(cfg, "JReturnValueReference")
                .map(n -> (JReturnValueReference)n).collect(Collectors.toList());
    }
    
    public static List<JReturnValueReference> getUseReturnValueReference(CFG cfg) {
        return getUseReference(cfg, "JReturnValueReference")
                .map(n -> (JReturnValueReference)n).collect(Collectors.toList());
    }
    
    public static List<JLocalVarReference> getDefLocalReference(CFG cfg) {
        return getDefReference(cfg, "JLocalVarReference")
                .map(n -> (JLocalVarReference)n).collect(Collectors.toList());
    }
    
    public static List<JLocalVarReference> getUseLocalReference(CFG cfg) {
        return getUseReference(cfg, "JLocalVarReference")
                .map(n -> (JLocalVarReference)n).collect(Collectors.toList());
    }
    
    public static List<JFieldReference> getDefFieldReference(CFG cfg) {
        return getDefReference(cfg, "JFieldReference")
                .map(n -> (JFieldReference)n).collect(Collectors.toList());
    }
    
    public static List<JFieldReference> getUseFieldReference(CFG cfg) {
        return getUseReference(cfg, "JFieldReference")
                .map(n -> (JFieldReference)n).collect(Collectors.toList());
    }
    
    public static List<JMethodReference> getMethodReference(CFG cfg) {
        return CFGNode.sortNodes(cfg.getNodes()).stream()
                .filter(n -> n.isMethodCall()).map(n -> (CFGMethodCall)n)
                .map(n -> n.getMethodCall()).collect(Collectors.toList());
    }
    
    private static Stream<JVariableReference> getDefReference(CFG cfg, String refType) {
        return CFGNode.sortNodes(cfg.getNodes()).stream()
                .filter(n -> n instanceof CFGStatement).map(n -> (CFGStatement)n)
                .flatMap(n -> n.getDefVariables().stream())
                .filter(r -> r.getClass().getName().endsWith("." + refType));
    }
    
    private static Stream<JVariableReference> getUseReference(CFG cfg, String refType) {
        return CFGNode.sortNodes(cfg.getNodes()).stream()
                .filter(n -> n instanceof CFGStatement).map(n -> (CFGStatement)n)
                .flatMap(n -> n.getUseVariables().stream())
                .filter(r -> r.getClass().getName().endsWith("." + refType));
    }
    
    public static void printReference(CFG cfg, String refType) {
        List<JReference> defs = getDefReference(cfg, refType).collect(Collectors.toList());
        defs.forEach(e -> System.err.println("DEF = " + e.toString()));
        List<JReference> uses = getUseReference(cfg, refType).collect(Collectors.toList());
        uses.forEach(e -> System.err.println("USE = " + e.toString()));
        List<JMethodReference> calls = getMethodReference(cfg);
        calls.forEach(e -> System.err.println("CALL = " + e.toString()));
    }
    
    public static void writeCFGs(JavaProject jproject) {
        writeCFGs(jproject, true);
    }
    
    public static void writeCFGs(JavaProject jproject, boolean force) {
        Path cfgPath = Paths.get(jproject.getPath(), "cfg");
        if (Files.exists(cfgPath) && !force) {
            System.err.println("Already exists the directory: " + cfgPath);
            return;
        }
        
        try {
            Files.createDirectories(cfgPath);
            
            for (JavaClass jclass : jproject.getClasses()) {
                Path path = cfgPath.resolve(jclass.getQualifiedName().fqn() + ".cfg");
                CCFG ccfg = jproject.getCFGStore().getCCFG(jclass, false);
                writeCFG(path, getCCFGData(ccfg));
            }
        } catch (IOException e) {
            System.err.println("Cannot create the directory: " + cfgPath);
        }
        System.out.println("Wrote CFG data in the directory: " + cfgPath);
    }
    
    public static void writeCFG(Path path, String content) {
        try (BufferedWriter writer = Files.newBufferedWriter(path)) {
            writer.write(content);
            writer.newLine();
        } catch (IOException e) {
            System.err.println("Cannot write CFG data in the file: " + path);
        }
    }
    
    public static String getCCFGData(CCFG ccfg) {
        StringBuilder buf = new StringBuilder();
        buf.append("----- CCFG (from here) -----\n");
        buf.append("Class Name = " + ccfg.getQualifiedName());
        buf.append("\n");
        ccfg.getCFGs().forEach(cfg -> buf.append(toStringForNodes(cfg) + "--\n"));
        ccfg.getCFGs().forEach(cfg -> buf.append(toStringForEdges(cfg) + "--\n"));
        buf.append("----- CCFG (to here) -----\n");
        return buf.toString();
    }
    
    static String toStringForNodes(CFG cfg) {
        StringBuilder buf = new StringBuilder();
        CFGNode.sortNodes(cfg.getNodes()).forEach(node -> {
            buf.append(toString(cfg, node));
            buf.append("\n");
        });
        return buf.toString();
    }
    
    static String toStringForEdges(CFG cfg) {
        StringBuilder buf = new StringBuilder();
        buf.append(GraphElement.getIdString(0));
        buf.append(": ");
        buf.append(cfg.getEntryNode().getSignature());
        buf.append("\n");
        
        long index = 1;
        for (ControlFlow edge : ControlFlow.sortEdges(cfg.getEdges())) {
            buf.append(GraphElement.getIdString(index));
            buf.append(": ");
            buf.append(toString(cfg, edge));
            buf.append("\n");
            index++;
        }
        return buf.toString();
    }
    
    public static String toString(CFG cfg, CFGNode node) {
        if (node.getKind() != null) {
            return getIdString(cfg, node) + " " + node.getKind().toString() + getDetails(cfg, node);
        } else {
            return getIdString(cfg, node);
        }
    }
    
    static String toString(CFG cfg, ControlFlow edge) {
        StringBuilder buf = new StringBuilder();
        buf.append(getIdString(cfg, edge.getSrcNode()) + " -> " + getIdString(cfg, edge.getDstNode()));
        if (edge.getKind() != null) {
            buf.append(" " + edge.getKind().toString());
        }
        if (edge.getLoopBack() != null) {
            buf.append(" (L = " + String.valueOf(getId(cfg,edge.getLoopBack())) + ")");
        }
        return buf.toString();
    }
    
    public static String getIdString(CFG cfg, CFGNode node) {
        if (node.getKind() == CFGNode.Kind.actualOutByFieldAccess) {
            CFGParameter actualOut = (CFGParameter)node;
            long id = getId(cfg, actualOut.getParent());
            return GraphElement.getIdString(id) + "+";
        } else {
            long id = getId(cfg, node);
            return GraphElement.getIdString(id);
        }
    }
    
    public static long getId(CFG cfg, CFGNode node) {
        return node.getId() - cfg.getEntryNode().getId();
    }
    
    static String getDetails(CFG cfg, CFGNode node) {
        if (node instanceof CCFGEntry) {
            return " [ " + ((CCFGEntry)node).getQualifiedName().getClassName() + " ]";
        } else if (node instanceof CFGMethodEntry) {
            return " [ " + ((CFGMethodEntry)node).getQualifiedName().fqn() + " ]";
        } else if (node instanceof CFGFieldEntry) {
            return " [ " + ((CFGFieldEntry)node).getQualifiedName().fqn() + " ]";
        } else if (node instanceof CFGMethodCall) {
            return getVars((CFGStatement)node) + " TO = " + ((CFGMethodCall)node).getQualifiedName();
        } else if (node instanceof CFGStatement) {
            return getVars((CFGStatement)node);
        } else if (node instanceof CFGMerge) {
            CFGMerge merge = (CFGMerge)node;
            if (node.getKind() != null) {
                return " " + merge.getBranch().getKind().toString() + "(" +
                        String.valueOf(getId(cfg, merge.getBranch())) + ")";
            }
        }
        return "";
    }
    
    static String getVars(CFGStatement st) {
        String defStr = st.getDefVariables().stream().map(e -> e.getReferenceForm()).sorted().collect(Collectors.joining(", "));
        String useStr = st.getUseVariables().stream().map(e -> e.getReferenceForm()).sorted().collect(Collectors.joining(", "));
        return " D = { " + defStr + " } U = { " + useStr + " }";
    }
}
