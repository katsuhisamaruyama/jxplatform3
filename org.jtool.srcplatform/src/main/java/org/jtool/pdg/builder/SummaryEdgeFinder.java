/*
 *  Copyright 2020
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.pdg.builder;

import org.jtool.pdg.DD;
import org.jtool.pdg.PDG;
import org.jtool.pdg.PDGNode;
import org.jtool.pdg.PDGStatement;
import org.jtool.cfg.CFGMethodCall;
import org.jtool.cfg.CFGNode;
import org.jtool.cfg.JReference;
import java.util.Set;
import java.util.HashSet;
import java.util.stream.Collectors;

/**
 * Finds summary edges for data dependences between actual-in nodes and actual-out nodes in a PDG.
 * All methods of this class are not intended to be directly called by clients.
 * 
 * @author Katsuhisa Maruyama
 */
public class SummaryEdgeFinder {
    
    public static void find(PDG pdg) {
        for (PDGNode pdgnode : pdg.getNodes()) {
            CFGNode cfgnode = pdgnode.getCFGNode();
            if (cfgnode.isMethodCall()) {
                CFGMethodCall callNode = (CFGMethodCall)cfgnode;
                Set<PDGStatement> ains = findActualIns(callNode);
                PDGStatement aoutForReturn = (PDGStatement)callNode.getActualOutForReturn().getPDGNode();
                
                Set<PDGStatement> nodes = new HashSet<>();
                traverseBackward(nodes, aoutForReturn, ains);
                
                for (PDGStatement ain : ains) {
                    if (nodes.contains(ain)) {
                        JReference jvar = ain.getDefVariables().get(0);
                        DD edge = new DD(ain, aoutForReturn, jvar);
                        edge.setSummary();
                        pdg.add(edge);
                    }
                }
            }
        }
    }
    
    private static Set<PDGStatement> findActualIns(CFGMethodCall callNode) {
        return callNode.getActualIns().stream()
                       .map(node -> (PDGStatement)node.getPDGNode())
                       .collect(Collectors.toSet());
    }
    
    private static void traverseBackward(Set<PDGStatement> nodes, PDGStatement anchor, Set<PDGStatement> ains) {
        nodes.add(anchor);
        anchor.getIncomingDDEdges().stream()
              .map(edge -> (PDGStatement)edge.getSrcNode())
              .forEach(node -> {
                  if (ains.contains(node)) {
                      nodes.add(node);
                  } else if (!nodes.contains(node)) {
                      traverseBackward(nodes, node, ains);
                  }
              });
    }
}
