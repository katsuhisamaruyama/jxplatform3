/*
 *  Copyright 2022
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.pdg.builder;

import org.jtool.pdg.DD;
import org.jtool.pdg.PDG;
import org.jtool.pdg.PDGNode;
import org.jtool.pdg.PDGStatement;
import org.jtool.cfg.CFGMethodCall;
import org.jtool.cfg.CFGNode;
import org.jtool.cfg.JVariableReference;
import java.util.Set;
import java.util.Stack;
import java.util.HashSet;
import java.util.Map;
import java.util.HashMap;
import java.util.stream.Collectors;

/**
 * Finds summary edges for data dependences between actual-in nodes and actual-out nodes in a PDG.
 * 
 * @author Katsuhisa Maruyama
 */
public class SummaryEdgeFinder {
    
    public static void find(PDG pdg) {
        Map<PDGStatement, Set<PDGStatement>> nodeMap = new HashMap<>();
        
        for (PDGNode pdgnode : pdg.getNodes()) {
            CFGNode cfgnode = pdgnode.getCFGNode();
            if (cfgnode.isMethodCall()) {
                CFGMethodCall callNode = (CFGMethodCall)cfgnode;
                Set<PDGStatement> ains = findActualIns(callNode);
                PDGStatement aoutForReturn = (PDGStatement)callNode.getActualOutForReturn().getPDGNode();
                PDGStatement formalOutForReturn = getFormalOutForReturn(aoutForReturn);
                
                if (formalOutForReturn != null) {
                    Set<PDGStatement> nodes = nodeMap.get(formalOutForReturn);
                    if (nodes == null) {
                        nodes = new HashSet<>();
                        traverseBackward(formalOutForReturn, ains, nodes);
                        nodeMap.put(formalOutForReturn, nodes);
                    }
                    
                    for (PDGStatement ain : ains) {
                        if (nodes.contains(ain)) {
                            JVariableReference jvar = ain.getDefVariables().get(0);
                            DD edge = new DD(ain, aoutForReturn, jvar);
                            edge.setSummary();
                            pdg.add(edge);
                        }
                    }
                }
            }
        }
    }
    
    private static PDGStatement getFormalOutForReturn(PDGStatement aoutForReturn) {
        for (DD dd : aoutForReturn.getIncomingDDEdges()) {
            if (dd.isParameterOut()) {
                return (PDGStatement)dd.getSrcNode();
            }
        }
        return null;
    }
    
    private static Set<PDGStatement> findActualIns(CFGMethodCall callNode) {
        return callNode.getActualIns().stream()
                       .map(node -> (PDGStatement)node.getPDGNode())
                       .collect(Collectors.toSet());
    }
    
    private static void traverseBackward(PDGStatement startnode, Set<PDGStatement> ains, Set<PDGStatement> nodes) {
        Stack<PDGStatement> nodeStack = new Stack<>();
        nodeStack.push(startnode);
        
        while (!nodeStack.isEmpty()) {
            PDGStatement node = nodeStack.pop();
            
            if (nodes.contains(node)) {
                continue;
            }
            nodes.add(node);
            
            node.getIncomingDDEdges().stream()
                .map(edge -> (PDGStatement)edge.getSrcNode())
                .forEach(pred -> {
                    if (ains.contains(pred)) {
                        nodes.add(pred);
                    } else if (!nodes.contains(node)) {
                        nodeStack.push(pred);
                    }
                });
        }
    }
}
