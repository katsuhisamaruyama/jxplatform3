/*
 *  Copyright 2022
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.jxplatform.util;

import org.jtool.srcmodel.JavaClass;
import org.jtool.srcmodel.JavaMethod;
import org.jtool.srcmodel.JavaField;
import org.jtool.srcmodel.JavaProject;
import org.jtool.pdg.SDG;
import org.jtool.pdg.ClDG;
import org.jtool.pdg.PDG;
import org.jtool.pdg.DependencyGraph;
import org.jtool.pdg.DependencyGraphEdge;
import org.jtool.pdg.DependencyGraphEdgeKind;
import org.jtool.pdg.Dependence;
import org.jtool.pdg.DD;
import org.jtool.pdg.PDGNode;
import org.jtool.pdg.PDGEntry;
import org.jtool.cfg.CFGNode;
import org.jtool.cfg.CFGStatement;
import org.jtool.cfg.CFGMethodCall;
import org.jtool.cfg.CFG;
import org.jtool.cfg.JReference;
import org.jtool.cfg.JVariableReference;
import org.jtool.graph.GraphElement;
import org.jtool.cfg.JMethodReference;
import org.jtool.cfg.JFieldReference;
import org.jtool.cfg.JLocalVarReference;
import org.jtool.cfg.JVersatileReference;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Set;
import java.util.HashSet;
import java.util.Comparator;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PDGTestUtil {
    
    public static PDG createPDG(JavaProject jproject, String cname, String member) {
        return createPDG(jproject, cname, member, true);
    }
    
    public static PDG createPDG(JavaProject jproject, String cname, String member, boolean whole) {
        if (member.indexOf("(") != -1) {
            JavaMethod jmethod = jproject.getClass(cname).getMethod(member);
            return jproject.getPDGStore().getPDG(jmethod, false, whole);
        } else {
            JavaField jfield = jproject.getClass(cname).getField(member);
            return jproject.getPDGStore().getPDG(jfield, false, whole);
        }
    }
    
    public static ClDG createClDG(JavaProject jproject, String cname) {
        return createClDG(jproject, cname, true);
    }
    
    public static ClDG createClDG(JavaProject jproject, String cname, boolean whole) {
        JavaClass jclass = jproject.getClass(cname);
        return jproject.getPDGStore().getClDG(jclass, false, whole);
    }
    
    public static DependencyGraph createDependencyGraph(JavaProject jproject, String cname) {
        JavaClass jclass = jproject.getClass(cname);
        return jproject.getPDGStore().getDependencyGraph(jclass, false);
    }
    
    public static DependencyGraph createDependencyGraph(JavaProject jproject, String cname, boolean whole) {
        JavaClass jclass = jproject.getClass(cname);
        return jproject.getPDGStore().getDependencyGraph(jclass, false, whole);
    }
    
    public static SDG createSDG(JavaProject jproject) {
        return jproject.getPDGStore().getSDG(false);
    }
    
    public static DependencyGraph createDependencyGraph(JavaProject jproject, Set<JavaClass> classes) {
        return jproject.getPDGStore().getDependencyGraph(classes, false, false);
    }
    
    public static PDGNode getNode(PDG pdg, int index) {
        long entry = pdg.getEntryNode().getId();
        return pdg.getNodes().stream().filter(n -> n.getId() == entry + index).findFirst().orElse(null);
    }
    
    public static Set<PDGNode> getNodes(PDG pdg, int[] indices) {
        long entry = pdg.getEntryNode().getId();
        Set<PDGNode> nodes = new HashSet<>();
        for (int index : indices) {
            PDGNode node = pdg.getNodes().stream().filter(n -> n.getId() == entry + index).findFirst().orElse(null);
            nodes.add(node);
        }
        return nodes;
    }
    
    public static Set<PDGNode> getNodes(ClDG cldg, int[] indices) {
        long entry = cldg.getEntryNode().getId();
        Set<PDGNode> nodes = new HashSet<>();
        for (int index : indices) {
            PDGNode node = cldg.getNodes().stream().filter(n -> n.getId() == entry + index).findFirst().orElse(null);
            nodes.add(node);
        }
        return nodes;
    }
    
    public static List<Dependence> getDependence(PDG pdg, long srcId, long dstId) {
        PDGNode srcNode = pdg.getNode(srcId);
        PDGNode dstNode = pdg.getNode(dstId);
        return getDependence(pdg, srcNode, dstNode);
    }
    
    public static List<Dependence> getDependence(PDG pdg, PDGNode src, PDGNode dst) {
        List<Dependence> edges = pdg.getOutgoingEdges(src).stream()
                .filter(e -> e.getDstNode().equals(dst)).collect(Collectors.toList());
        return Dependence.sortEdges(edges);
    }
    
    public static List<DependencyGraphEdge> getDependence(ClDG cldg, PDGNode src, PDGNode dst) {
        List<DependencyGraphEdge> edges = cldg.getEdges().stream()
                .filter(e -> e.getSrcNode().equals(src) && e.getDstNode().equals(dst)).collect(Collectors.toList());
        return DependencyGraphEdge.sortEdges(edges);
    }
    
    public static List<DependencyGraphEdge> getDependence(DependencyGraph graph, PDGNode src, PDGNode dst) {
        List<DependencyGraphEdge> edges = graph.getOutgoingEdges(src).stream()
                .filter(e -> e.getDstNode().equals(dst)).collect(Collectors.toList());
        return DependencyGraphEdge.sortEdges(edges);
    }
    
    public static List<PDGNode> getNodes(PDG pdg, String kind) {
        return PDGNode.sortNodes(pdg.getNodes()).stream()
            .filter(n -> !n.getCFGNode().isMerge())
            .filter(n -> n.getCFGNode().getASTNode().getClass().getName().endsWith("." + kind))
            .collect(Collectors.toList());
    }
    
    public static List<PDGNode> getNodes(PDG pdg, CFGNode.Kind kind) {
        return PDGNode.sortNodes(pdg.getNodes()).stream()
            .filter(n -> n.getCFGNode().getKind() == kind).collect(Collectors.toList());
    }
    
    public static List<Dependence> getEdges(PDG pdg, DependencyGraphEdgeKind kind) {
        return DependencyGraphEdge.sortEdges(pdg.getEdges()).stream().map(e -> (Dependence)e)
            .filter(e -> e.getKind() == kind).collect(Collectors.toList());
    }
    
    public static String getIdStr(ClDG cldg, PDGNode node) {
        return String.valueOf(node.getId() - cldg.getEntryNode().getId());
    }
    
    public static String getIdStr(PDG pdg, PDGNode node) {
        return String.valueOf(node.getId() - pdg.getEntryNode().getId());
    }
    
    public static List<String> getIdList(PDG pdg, Set<? extends PDGNode> set) {
        return set.stream().map(e -> String.valueOf(e.getId() - pdg.getEntryNode().getId())).sorted()
            .collect(Collectors.toList());
    }
    
    public static List<String> getIdList(PDG pdg, List<? extends PDGNode> list) {
        return list.stream().map(e -> String.valueOf(e.getId() - pdg.getEntryNode().getId()))
            .collect(Collectors.toList());
    }
    
    public static List<String> getIdList(ClDG cldg, Set<? extends PDGNode> set) {
        long min = cldg.getNodes().stream().map(n -> n.getId()).min(Comparator.naturalOrder()).get();
        return set.stream().map(e -> String.valueOf(e.getId() - min)).sorted()
            .collect(Collectors.toList());
    }
    
    public static List<String> getIdList(DependencyGraph graph, Set<? extends PDGNode> set) {
        long min = graph.getNodes().stream().map(n -> n.getId()).min(Comparator.naturalOrder()).get();
        return set.stream().map(e -> (e.getId() - min)).sorted().map(id -> String.valueOf(id))
            .collect(Collectors.toList());
    }
    
    public static List<String> getIdListOfSrc(DependencyGraph graph, List<? extends DependencyGraphEdge> set) {
        Set<PDGNode> srcs = set.stream().map(e -> (PDGNode)e.getSrcNode()).collect(Collectors.toSet());
        return getIdList(graph, srcs);
    }
    
    public static List<String> getIdListOfSrc(ClDG cldg, List<? extends Dependence> list) {
        Set<PDGNode> srcs = list.stream().map(e -> (PDGNode)e.getSrcNode()).collect(Collectors.toSet());
        return getIdList(cldg, srcs);
    }
    
    public static List<String> getIdListOfSrc(PDG pdg, List<? extends Dependence> list) {
        Set<PDGNode> srcs = list.stream().map(e -> (PDGNode)e.getSrcNode()).collect(Collectors.toSet());
        return getIdList(pdg, srcs);
    }
    
    public static List<String> getIdListOfDst(DependencyGraph graph, List<? extends DependencyGraphEdge> set) {
        Set<PDGNode> dsts = set.stream().map(e -> (PDGNode)e.getDstNode()).collect(Collectors.toSet());
        return getIdList(graph, dsts);
    }
    
    public static List<String> getIdListOfDst(ClDG cldg, List<? extends Dependence> list) {
        Set<PDGNode> dsts = list.stream().map(e -> (PDGNode)e.getDstNode()).collect(Collectors.toSet());
        return getIdList(cldg, dsts);
    }
    
    public static List<String> getIdListOfDst(PDG pdg, List<? extends Dependence> list) {
        Set<PDGNode> dsts = list.stream().map(e -> (PDGNode)e.getDstNode()).collect(Collectors.toSet());
        return getIdList(pdg, dsts);
    }
    
    public static String asStrOfPDGNode1(Set<? extends PDGNode> set) {
        return set.stream().map(e -> String.valueOf(e.getId())).collect(Collectors.joining(";"));
    }
    
    public static String asStrOfPDGNode1(List<? extends PDGNode> list) {
        return list.stream().map(e -> String.valueOf(e.getId())).collect(Collectors.joining(";"));
    }
    
    public static String asSortedStrOfPDGEntry(Set<? extends PDGEntry> set) {
        return set.stream().map(e -> e.getQualifiedName().fqn()).sorted().collect(Collectors.joining(";"));
    }
    
    public static String asStrOfNode(PDG pdg, PDGNode node) {
        return String.valueOf(node.getId() - pdg.getEntryNode().getId());
    }
    
    public static String asStrOfNode(ClDG cldg, PDGNode node) {
        for (PDG pdg : cldg.getPDGs()) {
            if (pdg.getNodes().contains(node)) {
                return String.valueOf(node.getId() - pdg.getEntryNode().getId());
            }
        }
        return "";
    }
    
    public static String asStrOfNode(DependencyGraph graph, PDGNode node) {
        for (PDG pdg : graph.getPDGs()) {
            if (pdg.getNodes().contains(node)) {
                return String.valueOf(node.getId() - pdg.getEntryNode().getId());
            }
        }
        return "";
    }
    
    public static String asStrOfEdge(PDG pdg, Dependence edge) {
        long srcId = edge.getSrcNode().getId() - pdg.getEntryNode().getId();
        long dstId = edge.getDstNode().getId() - pdg.getEntryNode().getId();
        return String.valueOf(srcId) + "->" + String.valueOf(dstId);
    }
    
    public static void print(PDG pdg, CFGNode.Kind kind) {
        List<PDGNode> nodes = getNodes(pdg, kind);
        nodes.forEach(e -> System.err.println(e.getId() + " " +
                e.getCFGNode().getKind().toString() + " " + e.getClass().toString()));
    }
    
    public static List<JVersatileReference> getDefExpedientialReference(PDG pdg) {
        return getDefReference(pdg, "JExpedientialReference")
                .map(n -> (JVersatileReference)n).collect(Collectors.toList());
    }
    
    public static List<JVersatileReference> getUseExpedientialReference(PDG pdg) {
        return getUseReference(pdg, "JExpedientialReference")
                .map(n -> (JVersatileReference)n).collect(Collectors.toList());
    }
    
    public static List<JLocalVarReference> getDefLocalReference(PDG pdg) {
        return getDefReference(pdg, "JLocalVarReference")
                .map(n -> (JLocalVarReference)n).collect(Collectors.toList());
    }
    
    public static List<JLocalVarReference> getUseLocalReference(PDG pdg) {
        return getUseReference(pdg, "JLocalVarReference")
                .map(n -> (JLocalVarReference)n).collect(Collectors.toList());
    }
    
    public static List<JFieldReference> getDefFieldReference(PDG pdg) {
        return getDefReference(pdg, "JFieldReference")
                .map(n -> (JFieldReference)n).collect(Collectors.toList());
    }
    
    public static List<JFieldReference> getUseFieldReference(PDG pdg) {
        return getUseReference(pdg, "JFieldReference")
                .map(n -> (JFieldReference)n).collect(Collectors.toList());
    }
    
    public static List<JMethodReference> getMethodReference(PDG pdg) {
        Stream<CFGMethodCall> nstream = PDGNode.sortNodes(pdg.getNodes()).stream()
                .filter(n -> n.getCFGNode().isMethodCall()).map(n -> (CFGMethodCall)n.getCFGNode());
        return nstream.map(n -> n.getMethodCall()).collect(Collectors.toList());
    }
    
    private static Stream<JVariableReference> getDefReference(PDG pdg, String refType) {
        Stream<CFGStatement> nstream = PDGNode.sortNodes(pdg.getNodes()).stream()
                .filter(n -> n.isStatement()).map(n -> (CFGStatement)n.getCFGNode());
        return nstream.flatMap(n -> n.getDefVariables().stream())
                .filter(r -> r.getClass().getName().endsWith("." + refType));
    }
    
    private static Stream<JVariableReference> getUseReference(PDG pdg, String refType) {
        Stream<CFGStatement> nstream = PDGNode.sortNodes(pdg.getNodes()).stream()
                .filter(n -> n.isStatement()).map(n -> (CFGStatement)n.getCFGNode());
        return nstream.flatMap(n -> n.getUseVariables().stream())
                .filter(r -> r.getClass().getName().endsWith("." + refType));
    }
    
    public static void printReference(PDG pdg, String refType) {
        List<JReference> defs = getDefReference(pdg, refType).collect(Collectors.toList());
        defs.forEach(e -> System.err.println("DEF = " + e.toString()));
        List<JReference> uses = getUseReference(pdg, refType).collect(Collectors.toList());
        uses.forEach(e -> System.err.println("USE = " + e.toString()));
        List<JMethodReference> calls = getMethodReference(pdg);
        calls.forEach(e -> System.err.println("CALL = " + e.toString()));
    }
    
    public static void writePDGs(JavaProject jproject) {
        writePDGs(jproject, true);
    }
    
    public static void writePDGs(JavaProject jproject, boolean force) {
        Path pdgPath = Paths.get(jproject.getPath(), "pdg");
        if (Files.exists(pdgPath) && !force) {
            System.err.println("Already exists the directory: " + pdgPath);
            return;
        }
        
        System.out.println("Create PDG data in the directory: " + pdgPath);
        try {
            Files.createDirectories(pdgPath);
            
            DependencyGraph sdg = jproject.getPDGStore().getSDG(false);
            for (JavaClass jclass : jproject.getClasses()) {
                Path path = pdgPath.resolve(jclass.getQualifiedName().fqn() + ".pdg");
                ClDG cldg = sdg.getClDG(jclass.getQualifiedName().fqn());
                writePDG(path, getClDGData(cldg));
            }
        } catch (IOException e) {
            System.err.println("Cannot create the directory: " + pdgPath);
        }
    }
    
    public static void writePDG(Path path, String content) {
        try (BufferedWriter writer = Files.newBufferedWriter(path)) {
            writer.write(content);
            writer.newLine();
        } catch (IOException e) {
            System.err.println("Cannot write PDG data in the file: " + path);
        }
    }
    
    public static String getClDGData(ClDG cldg) {
        StringBuilder buf = new StringBuilder();
        buf.append("----- ClDG (from here) -----\n");
        buf.append("Class Name = " + cldg.getQualifiedName());
        buf.append("\n");
        cldg.getPDGs().forEach(pdg -> buf.append(toStringForNodes(pdg) + "--\n"));
        cldg.getPDGs().forEach(pdg -> buf.append(toStringForEdges(pdg) + "--\n"));
        buf.append("----- ClDG (to here) -----\n");
        return buf.toString();
    }
    
    static String toStringForNodes(PDG pdg) {
        CFG cfg = pdg.getEntryNode().getCFGEntry().getCFG();
        StringBuilder buf = new StringBuilder();
        PDGNode.sortNodes(pdg.getNodes()).forEach(node -> {
            buf.append(CFGTestUtil.toString(cfg, node.getCFGNode()));
            buf.append("\n");
        });
        return buf.toString();
    }
    
    static String toStringForEdges(PDG pdg) {
        CFG cfg = pdg.getEntryNode().getCFGEntry().getCFG();
        StringBuilder buf = new StringBuilder();
        buf.append(GraphElement.getIdString(0));
        buf.append(": ");
        buf.append(pdg.getEntryNode().getSignature());
        buf.append("\n");
        
        List<String> edgesInfo = Dependence.sortEdges(pdg.getEdges()).stream()
                .map(e -> toString(cfg, e)).sorted().collect(Collectors.toList());
        long index = 1;
        for (String edgeInfo : edgesInfo) {
            buf.append(GraphElement.getIdString(index));
            buf.append(": ");
            buf.append(edgeInfo);
            buf.append("\n");
            index++;
        }
        return buf.toString();
    }
    
    private static String toString(CFG cfg, Dependence edge) {
        StringBuilder buf = new StringBuilder();
        buf.append(CFGTestUtil.getIdString(cfg, edge.getSrcNode().getCFGNode())
                   + " -> " + 
                   CFGTestUtil.getIdString(cfg, edge.getDstNode().getCFGNode()));
        if (edge.getKind() != null) {
            buf.append(" " + edge.getKind().toString());
        }
        if (edge.isDD()) {
            DD dd = (DD)edge;
            if (dd.getVariable() != null) {
                buf.append(" " + dd.getVariable().getReferenceForm());
            }
            if (dd.isLoopCarried()) {
                buf.append(" (LC = " + String.valueOf(CFGTestUtil.getId(cfg, dd.getLoopCarriedNode().getCFGNode())) + ")");
            }
        }
        return buf.toString();
    }
}
