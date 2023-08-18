/*
 *  Copyright 2022-2023
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.pdg.internal;

import org.jtool.srcmodel.JavaProject;
import org.jtool.pdg.PDG;
import org.jtool.pdg.Dependence;
import org.jtool.pdg.CD;
import org.jtool.pdg.DD;
import org.jtool.pdg.PDGNode;
import org.jtool.cfg.CFG;
import org.jtool.cfg.CFGNode;
import org.jtool.cfg.CFGStatement;
import org.jtool.cfg.ControlFlow;
import org.jtool.cfg.JVariableReference;
import java.util.List;
import java.util.Set;
import java.util.HashSet;
import java.util.Stack;
import java.util.ArrayList;
import java.util.Collections;
import java.util.stream.Collectors;
import java.util.concurrent.TimeoutException;

/**
 * Finds data dependences of a PDG from its CFG.
 * This class does not guarantee a loop carried node to be correct if a given PDG contains no control dependence.
 * 
 * @author Katsuhisa Maruyama
 */
public class DDFinder {
    
    private static final int TIMEOUT_SEC = 60;
    
    public void find(final JavaProject jproject, final PDG pdg, final CFG cfg) {
        Runnable task = new Runnable() {
            
            @Override
            public void run() {
                find0(pdg, cfg);
            }
        };
        
        try {
            jproject.getModelBuilderImpl().performTaskWithTimeout(task, TIMEOUT_SEC);
        } catch (TimeoutException e) {
            jproject.getModelBuilderImpl().printErrorOnMonitor(
                    "**Timeout occurred in data dependency analysis: " + cfg.getQualifiedName().fqn());
            jproject.getModelBuilderImpl().getLogger().recordTimeoutError(cfg.getQualifiedName().fqn());
        }
    }
    
    public void find0(PDG pdg, CFG cfg) {
        try {
            findDDs(pdg, cfg);
            findDefOrderDDs(pdg, cfg);
        } catch (InterruptedException e) {
            // executes workaround analysis if needed
        }
    }
    
    private void findDDs(PDG pdg, CFG cfg) throws InterruptedException {
        for (CFGNode node : cfg.getNodes()) {
            if (Thread.currentThread().isInterrupted()) {
                throw new InterruptedException();
            }
            
            if (node.isStatement() && node.hasDefVariable()) {
                CFGStatement anchor = (CFGStatement)node;
                List<CFGNode> reachableNodes = cfg.forwardReachableNodes(anchor, true, false);
                
                for (JVariableReference jvar : anchor.getDefVariables()) {
                    if (needTraverse(jvar, reachableNodes)) {
                        for (ControlFlow flow : anchor.getOutgoingFlows()) {
                            if (!flow.isFallThrough()) {
                                findDD(pdg, cfg, anchor, flow.getDstNode(), jvar);
                            }
                        }
                    }
                }
            }
        }
    }
    
    private boolean needTraverse(JVariableReference jvar, List<CFGNode> nodes) throws InterruptedException {
        for (CFGNode node : nodes) {
            if (Thread.currentThread().isInterrupted()) {
                throw new InterruptedException();
            }
            
            if (node.isStatement()) {
                CFGStatement candidate = (CFGStatement)node;
                if (candidate.defineVariable(jvar) || candidate.useVariable(jvar)) {
                    return true;
                }
            }
        }
        return false;
    }
    
    private void findDD(PDG pdg, CFG cfg,
            CFGNode anchor, CFGNode startnode, JVariableReference jvar) throws InterruptedException {
        Set<CFGNode> track = new HashSet<>();
        Stack<CFGNode> nodeStack = new Stack<>();
        nodeStack.push(startnode);
        
        while (!nodeStack.isEmpty()) {
            if (Thread.currentThread().isInterrupted()) {
                throw new InterruptedException();
            }
            
            CFGNode node = nodeStack.pop();
            
            if (track.contains(node)) {
                continue;
            }
            track.add(node);
            
            if (node.hasUseVariable()) {
                CFGStatement candidate = (CFGStatement)node;
                if (candidate.useVariable(jvar)) {
                    DD edge;
                    if (anchor.isFormalIn()) {
                        edge = new DD(anchor.getPDGNode(), candidate.getPDGNode(), jvar);
                        edge.setLIDD();
                    
                    } else if (candidate.isFormalOut()) {
                        edge = new DD(anchor.getPDGNode(), candidate.getPDGNode(), jvar);
                        edge.setLIDD();
                    
                    } else {
                        edge = new DD(anchor.getPDGNode(), candidate.getPDGNode(), jvar);
                        PDGNode lc = getLoopCarried(pdg, cfg, anchor, candidate);
                        
                        if (lc != null) {
                            edge.setLCDD();
                            edge.setLoopCarriedNode(lc);
                        } else {
                            edge.setLIDD();
                        }
                    }
                    pdg.add(edge);
                }
            }
            
            if (node.hasDefVariable()) {
                CFGStatement candidate = (CFGStatement)node;
                if (candidate.defineVariable(jvar)) {
                    if (!candidate.useVariable(jvar)) {
                        DD edge = new DD(anchor.getPDGNode(), candidate.getPDGNode(), jvar);
                        edge.setOutput();
                        pdg.add(edge);
                    }
                    continue;
                }
            }
            
            List<ControlFlow> edges = ControlFlow.sortEdges(node.getOutgoingFlows());
            Collections.reverse(edges);
            edges.stream().filter(flow -> !flow.isFallThrough())
                    .forEach(flow -> nodeStack.push(flow.getDstNode()));
        }
    }
    
    private PDGNode getLoopCarried(PDG pdg, CFG cfg,
            CFGNode defnode, CFGNode usenode) throws InterruptedException {
        List<PDGNode> defancestors = CDFinder.getCDAncestors(pdg, defnode.getPDGNode()).stream()
                .filter(node -> node.isLoop()).collect(Collectors.toList());
        List<PDGNode> useancestors = CDFinder.getCDAncestors(pdg, usenode.getPDGNode()).stream()
                .filter(node -> node.isLoop()).collect(Collectors.toList());
        
        Set<CFGNode> nodesBetween = null;
        for (PDGNode defancestor : defancestors) {
            for (PDGNode useancestor : useancestors) {
                if (Thread.currentThread().isInterrupted()) {
                    throw new InterruptedException();
                }
                
                if (defancestor.equals(useancestor)) {
                    if (nodesBetween == null) {
                        nodesBetween = getNodesBetween(cfg, defnode, usenode);
                    }
                    if (nodesBetween.contains(defancestor.getCFGNode())) {
                        return defancestor;
                    }
                }
            }
        }
        return null;
    }
    
    private Set<CFGNode> getNodesBetween(CFG cfg, CFGNode defnode, CFGNode usenode) {
        Set<CFGNode> nodes = new HashSet<>();
        for (CFGNode succ : defnode.getSuccessors()) {
            nodes.addAll(cfg.reachableNodes(succ, usenode, true, false));
        }
        return nodes;
    }
    
    private void findDefOrderDDs(PDG pdg, CFG cfg) throws InterruptedException {
        for (Dependence edge : pdg.getEdges()) {
            if (Thread.currentThread().isInterrupted()) {
                throw new InterruptedException();
            }
            
            if (edge.isDD()) {
                DD dd = (DD)edge;
                if (dd.isOutput() && isDefOrder(pdg, dd)) {
                    dd.setDefOrder();
                }
            }
        }
    }
    
    private boolean isDefOrder(PDG pdg, DD dd) throws InterruptedException {
        PDGNode src = dd.getSrcNode();
        PDGNode dst = dd.getDstNode();
        
        CD srcCD = pdg.getIncomingCDEdges(src).iterator().next();
        CD dstCD = pdg.getIncomingCDEdges(dst).iterator().next();
        if (!srcCD.getSrcNode().equals(dstCD.getSrcNode())) {
            return false;
        }
        
        for (DD srcDD : pdg.getOutgoingDDEdges(src)) {
            if (srcDD.isLCDD() || srcDD.isLIDD()) {
                for (DD dstDD : pdg.getOutgoingDDEdges(dst)) {
                    if (dstDD.isLCDD() || dstDD.isLIDD()) {
                        if (Thread.currentThread().isInterrupted()) {
                            throw new InterruptedException();
                        }
                        
                        if (srcDD.getDstNode().equals(dstDD.getDstNode())) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }
}
