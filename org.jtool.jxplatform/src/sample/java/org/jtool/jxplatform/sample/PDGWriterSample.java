/*
 *  Copyright 2023
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.jxplatform.sample;

import static org.jtool.jxplatform.builder.ModelBuilder.br;
import org.jtool.jxplatform.builder.ModelBuilderBatch;
import org.jtool.srcmodel.JavaClass;
import org.jtool.srcmodel.JavaProject;
import org.jtool.pdg.DependencyGraph;
import org.jtool.pdg.DependencyGraphEdge;
import org.jtool.pdg.DD;
import org.jtool.pdg.Dependence;
import org.jtool.pdg.PDG;
import org.jtool.pdg.PDGNode;
import org.jtool.cfg.CCFG;
import org.jtool.cfg.CCFGEntry;
import org.jtool.cfg.CFG;
import org.jtool.cfg.CFGFieldEntry;
import org.jtool.cfg.CFGMerge;
import org.jtool.cfg.CFGMethodCall;
import org.jtool.cfg.CFGMethodEntry;
import org.jtool.cfg.CFGNode;
import org.jtool.cfg.CFGParameter;
import org.jtool.cfg.CFGStatement;
import org.jtool.cfg.ControlFlow;
import org.jtool.graph.GraphElement;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Test to build PDGs from open-source Java programs.
 * 
 * @author Katsuhisa Maruyama
 */
public class PDGWriterSample {
    
    public static void main(String[] args) {
        PDGWriterSample writer = new PDGWriterSample();
        writer.run("sample", SrcModelGeneratorSample.SAMPLE_PROJECT_DIR);
    }
    
    private void run(String name, String target) {
        ModelBuilderBatch builder = new ModelBuilderBatch(true, false);
        List<JavaProject> jprojects = builder.build(name, target);
        jprojects.forEach(jproject -> buildPDGs(builder, jproject));
        builder.unbuild();
    }
    
    private void buildPDGs(ModelBuilderBatch builder, JavaProject jproject) {
        int size = jproject.getClasses().size();
        System.out.println();
        System.out.println("** Building ClDGs of " + size + " classes in " + jproject.getName());
        
        Path cfgPath = Paths.get(jproject.getPath(), "cfg");
        Path pdgPath = Paths.get(jproject.getPath(), "pdg");
        
        for (JavaClass jclass : jproject.getClasses()) {
            CCFG ccfg = jproject.getCFGStore().getCCFG(jclass);
            writeCFG(cfgPath, jclass, ccfg);
            ccfg.print();
            
            DependencyGraph graph = jproject.getPDGStore().getDependencyGraph(jclass, false, true);
            writePDG(pdgPath, jclass, graph);
            graph.print();
        }
    }
    
    private void writeCFG(Path path, JavaClass jclass, CCFG ccfg) {
        Path filepath = path.resolve(jclass.getQualifiedName().fqn() + ".ccfg");
        writeCFG(filepath, getCCFGData(jclass, ccfg));
    }
    
    private void writeCFG(Path path, String content) {
        try (BufferedWriter writer = Files.newBufferedWriter(path)) {
            writer.write(content);
            writer.newLine();
        } catch (IOException e) {
            System.err.println("Cannot write CFG data in the file: " + path);
        }
    }
    
    private String getCCFGData(JavaClass jclass, CCFG ccfg) {
        StringBuilder buf = new StringBuilder();
        buf.append("Class Name = " + jclass.getQualifiedName().fqn());
        buf.append(br);
        ccfg.getCFGs().forEach(cfg -> buf.append(toStringForNodes(cfg) + "--" + br));
        ccfg.getCFGs().forEach(cfg -> buf.append(toStringForEdges(cfg) + "--" + br));
        return buf.toString();
    }
    
    private String toStringForNodes(CFG cfg) {
        StringBuilder buf = new StringBuilder();
        CFGNode.sortNodes(cfg.getNodes()).forEach(node -> {
            buf.append(toString(cfg, node));
            buf.append(br);
        });
        return buf.toString();
    }
    
    private String toStringForEdges(CFG cfg) {
        StringBuilder buf = new StringBuilder();
        buf.append(GraphElement.getIdString(0));
        buf.append(": ");
        buf.append(cfg.getEntryNode().getSignature());
        buf.append(br);
        
        long index = 1;
        for (ControlFlow edge : ControlFlow.sortEdges(cfg.getEdges())) {
            buf.append(GraphElement.getIdString(index));
            buf.append(": ");
            buf.append(toString(cfg, edge));
            buf.append(br);
            index++;
        }
        return buf.toString();
    }
    
    private String toString(CFG cfg, CFGNode node) {
        if (node.getKind() != null) {
            return getIdString(cfg, node) + " " + node.getKind().toString() + getDetails(cfg, node);
        } else {
            return getIdString(cfg, node);
        }
    }
    
    private String toString(CFG cfg, ControlFlow edge) {
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
    
    private String getIdString(CFG cfg, CFGNode node) {
        if (node.getKind() == CFGNode.Kind.actualOutByFieldAccess) {
            CFGParameter actualOut = (CFGParameter)node;
            long id = getId(cfg, actualOut.getParent());
            return GraphElement.getIdString(id) + "+" + String.valueOf(node.getId());
        } else {
            long id = getId(cfg, node);
            return GraphElement.getIdString(id);
        }
    }
    
    private void writePDG(Path path, JavaClass jclass, DependencyGraph graph) {
        Path filepath = path.resolve(jclass.getQualifiedName().getClassName() + ".cldg");
        writePDG(filepath, getDGData(jclass, graph));
    }
    
    private void writePDG(Path path, String content) {
        try (BufferedWriter writer = Files.newBufferedWriter(path)) {
            writer.write(content);
            writer.newLine();
        } catch (IOException e) {
            System.err.println("Cannot write PDG data in the file: " + path);
        }
    }
    
    private String getDGData(JavaClass jclass, DependencyGraph graph) {
        StringBuilder buf = new StringBuilder();
        buf.append("Class Name = " + jclass.getQualifiedName().fqn());
        buf.append(br);
        graph.getPDGs().forEach(pdg -> buf.append(toStringForNodes(pdg) + "--" + br));
        graph.getPDGs().forEach(pdg -> buf.append(toStringForEdges(pdg) + "--" + br));
        buf.append(toStringForEdges(graph.getInterPDGEdges()) + "--" + br);
        return buf.toString();
    }
    
    private String toStringForNodes(PDG pdg) {
        CFG cfg = pdg.getEntryNode().getCFGEntry().getCFG();
        StringBuilder buf = new StringBuilder();
        PDGNode.sortNodes(pdg.getNodes()).forEach(node -> {
            buf.append(toString(cfg, node.getCFGNode()));
            buf.append(br);
        });
        return buf.toString();
    }
    
    private String toStringForEdges(PDG pdg) {
        CFG cfg = pdg.getEntryNode().getCFGEntry().getCFG();
        StringBuilder buf = new StringBuilder();
        buf.append(GraphElement.getIdString(0));
        buf.append(": ");
        buf.append(pdg.getEntryNode().getSignature());
        buf.append(br);
        
        List<String> edgesInfo = Dependence.sortEdges(pdg.getEdges()).stream()
                .map(e -> toString(cfg, e)).sorted().collect(Collectors.toList());
        long index = 1;
        for (String edgeInfo : edgesInfo) {
            buf.append(GraphElement.getIdString(index));
            buf.append(": ");
            buf.append(edgeInfo);
            buf.append(br);
            index++;
        }
        return buf.toString();
    }
    
    private String toStringForEdges(List<DependencyGraphEdge> edges) {
        StringBuilder buf = new StringBuilder();
        List<String> edgesInfo = edges.stream().map(e -> e.toString()).sorted().collect(Collectors.toList());
        long index = 1;
        for (String edgeInfo : edgesInfo) {
            buf.append(GraphElement.getIdString(index));
            buf.append(": ");
            buf.append(edgeInfo);
            buf.append(br);
            index++;
        }
        return buf.toString();
    }
    
    private String toString(CFG cfg, Dependence edge) {
        StringBuilder buf = new StringBuilder();
        buf.append(getIdString(cfg, edge.getSrcNode().getCFGNode())
                   + " -> " + 
                   getIdString(cfg, edge.getDstNode().getCFGNode()));
        if (edge.getKind() != null) {
            buf.append(" " + edge.getKind().toString());
        }
        if (edge.isDD()) {
            DD dd = (DD)edge;
            if (dd.getVariable() != null) {
                buf.append(" " + dd.getVariable().getReferenceForm());
            }
            if (dd.isLoopCarried()) {
                buf.append(" (LC = " + String.valueOf(getId(cfg, dd.getLoopCarriedNode().getCFGNode())) + ")");
            }
        }
        return buf.toString();
    }
    
    private long getId(CFG cfg, CFGNode node) {
        return node.getId() - cfg.getEntryNode().getId();
    }
    
    private String getDetails(CFG cfg, CFGNode node) {
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