/*
 *  Copyright 2022
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.cfg.internal;

import org.jtool.cfg.CFG;
import org.jtool.cfg.CFGMethodCall;
import org.jtool.cfg.CFGNode;
import org.jtool.cfg.CallGraph;
import org.jtool.srcmodel.JavaMethod;
import org.jtool.srcmodel.JavaProject;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Builds a call graph that corresponds to a method, a class, or a project.
 * 
 * @author Katsuhisa Maruyama
 */
public class CallGraphBuilder {
    
    public static CallGraph getCallGraph(JavaProject jproject) {
        CallGraph graph = new CallGraph(jproject.getName());
        Set<JavaMethod> methods = jproject.getClasses().stream()
                                          .flatMap(jclass -> jclass.getMethods().stream())
                                          .collect(Collectors.toSet());
        methods.forEach(jmethod -> jproject.getCFGStore().getCFG(jmethod, false));
        methods.forEach(jmethod -> createCallGraph(jmethod, graph));
        return graph;
    }
    
    private static void createCallGraph(JavaMethod jmethod, CallGraph graph) {
        CFGStore cfgStore = jmethod.getJavaProject().getCFGStore();
        CFG cfg = cfgStore.findCFG(jmethod.getQualifiedName().fqn());
        if (cfg == null) {
            return;
        }
        
        for (CFGNode node : cfg.getNodes()) {
            if (node.isMethodCall()) {
                CFG calledCFG = cfgStore.findCFG(((CFGMethodCall)node).getQualifiedName().fqn());
                if (calledCFG != null) {
                    graph.setCall(cfg.getEntryNode(), calledCFG.getEntryNode());
                }
            }
        }
    }
}
