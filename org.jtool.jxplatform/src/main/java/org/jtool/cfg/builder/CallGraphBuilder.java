/*
 *  Copyright 2022
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.cfg.builder;

import org.jtool.cfg.CFG;
import org.jtool.cfg.CFGMethodCall;
import org.jtool.cfg.CFGNode;
import org.jtool.cfg.CallGraph;
import org.jtool.srcmodel.JavaClass;
import org.jtool.srcmodel.JavaMethod;
import org.jtool.srcmodel.JavaProject;

/**
 * Builds a call graph that corresponds to a method, a class, or a project.
 * 
 * @author Katsuhisa Maruyama
 */
public class CallGraphBuilder {
    
    public static CallGraph getCallGraph(JavaProject jproject) {
        CallGraph callGraph = new CallGraph(jproject.getName());
        jproject.getClasses().forEach(c -> callGraph.append(getCallGraph(c)));
        return callGraph;
    }
    
    private static CallGraph getCallGraph(JavaClass jclass) {
        CallGraph callGraph = new CallGraph(jclass.getQualifiedName().fqn());
        jclass.getMethods().forEach(m -> callGraph.append(getCallGraph(m)));
        return callGraph;
    }
    
    private static CallGraph getCallGraph(JavaMethod jmethod) {
        CFGStore cfgStore = jmethod.getJavaProject().getCFGStore();
        CallGraph callGraph = new CallGraph(jmethod.getQualifiedName().fqn());
        CFG cfg = cfgStore.findCFG(jmethod.getQualifiedName().fqn());
        if (cfg == null) {
            return null;
        }
        
        for (CFGNode cfgnode : cfg.getNodes()) {
            if (cfgnode.isMethodCall()) {
                CFGMethodCall call = (CFGMethodCall)cfgnode;
                CFG calledCFG = cfgStore.findCFG(call.getQualifiedName().fqn());
                if (calledCFG != null) {
                    callGraph.setCall(cfg.getEntryNode(), calledCFG.getEntryNode());
                }
            }
        }
        return callGraph;
    }
}
