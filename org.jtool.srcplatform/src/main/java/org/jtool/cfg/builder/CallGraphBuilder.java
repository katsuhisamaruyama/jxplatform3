/*
 *  Copyright 2020
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.cfg.builder;

import org.jtool.cfg.CFG;
import org.jtool.cfg.CFGMethodCall;
import org.jtool.cfg.CFGNode;
import org.jtool.cfg.CFGStatement;
import org.jtool.cfg.CallGraph;
import org.jtool.cfg.ControlFlow;
import org.jtool.cfg.JReference;
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
        jproject.getClasses().forEach(jclass -> callGraph.append(getCallGraph(jclass)));
        return callGraph;
    }
    
    public static CallGraph getCallGraph(JavaClass jclass) {
        CallGraph callGraph = new CallGraph(jclass.getQualifiedName().fqn());
        jclass.getMethods().forEach(jmethod -> callGraph.append(getCallGraph(jmethod)));
        return callGraph;
    }
    
    public static CallGraph getCallGraph(JavaMethod jmethod) {
        CFGStore cfgStore = jmethod.getJavaProject().getCFGStore();
        CallGraph callGraph = new CallGraph(jmethod.getQualifiedName().fqn());
        CFG cfg = cfgStore.findCFG(jmethod.getQualifiedName().fqn());
        if (cfg == null) {
            return null;
        }
        
        for (CFGNode cfgnode : cfg.getNodes()) {
            if (cfgnode.isMethodCall()) {
                CFGMethodCall call = (CFGMethodCall)cfgnode;
                CFG methodCFG = cfgStore.findCFG(call.getQualifiedName().fqn());
                if (methodCFG != null) {
                    ControlFlow flow = new ControlFlow(cfg.getEntryNode(), methodCFG.getEntryNode());
                    callGraph.add(flow);
                }
            } else if (cfgnode.isStatement()) {
                CFGStatement statement = (CFGStatement)cfgnode;
                for (JReference def : statement.getDefVariables()) {
                    if (def.isFieldAccess()) {
                        CFG fieldCFG = cfgStore.findCFG(def.getQualifiedName().fqn());
                        if (fieldCFG != null) {
                            ControlFlow flow = new ControlFlow(cfg.getEntryNode(), fieldCFG.getEntryNode());
                            callGraph.add(flow);
                        }
                    }
                }
                for (JReference use : statement.getUseVariables()) {
                    if (use.isFieldAccess()) {
                        CFG fieldCFG = cfgStore.findCFG(use.getQualifiedName().fqn());
                        if (fieldCFG != null) {
                            ControlFlow flow = new ControlFlow(cfg.getEntryNode(), fieldCFG.getEntryNode());
                            callGraph.add(flow);
                        }
                    }
                }
            }
        }
        return callGraph;
    }
}
