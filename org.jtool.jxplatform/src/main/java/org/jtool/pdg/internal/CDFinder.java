/*
 *  Copyright 2022-2023
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.pdg.internal;

import org.jtool.srcmodel.JavaProject;
import org.jtool.pdg.PDG;
import org.jtool.pdg.Dependence;
import org.jtool.pdg.CD;
import org.jtool.pdg.PDGNode;
import org.jtool.cfg.CFG;
import org.jtool.cfg.CFGNode;
import org.jtool.cfg.CFGStatement;
import org.jtool.cfg.ControlFlow;
import org.jtool.cfg.JVariableReference;
import org.jtool.cfg.StopConditionOnReachablePath;
import org.jtool.cfg.internal.CFGTry;
import org.jtool.cfg.internal.DominantStatement;
import java.util.Set;
import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;
import java.util.Stack;
import java.util.Collections;
import java.util.stream.Collectors;
import java.util.concurrent.TimeoutException;

/**
 * Finds control dependences in a bare PDG.
 * 
 * @author Katsuhisa Maruyama
 */
public class CDFinder {
    
    private static final int TIMEOUT_SEC = 5;
    private static final int SHRORT_TIMEOUT_SEC = 2;
    
    public void find(final JavaProject jproject, final PDG pdg, final CFG cfg) {
        Runnable task = new Runnable() {
            
            @Override
            public void run() {
                find0(pdg, cfg);
            }
        };
        
        try {
            int timeoutSec = getTimeoutPeriod(cfg);
            jproject.getModelBuilderImpl().performTaskWithTimeout(task, timeoutSec);
        } catch (TimeoutException e) {
            jproject.getModelBuilderImpl().printErrorOnMonitor(
                    "**Timeout occurred in control dependency analysis: " + cfg.getQualifiedName().fqn());
            jproject.getModelBuilderImpl().getLogger().recordTimeoutError(cfg.getQualifiedName().fqn());
        }
    }
    
    private int getTimeoutPeriod(final CFG cfg) {
        GetNestingDepth nesting = new GetNestingDepth();
        int maxDepth = nesting.getMaximumNuberOfNestingDepth(cfg.getEntryNode());
        if (maxDepth > 30) {
            return SHRORT_TIMEOUT_SEC;
        } else {
            return TIMEOUT_SEC;
        }
    }
    
    private void find0(PDG pdg, CFG cfg) {
        try {
            if (hasNestedStructure(cfg)) {
                findCDsOnNestStructure(pdg, cfg);
            } else {
                findCDs(pdg, cfg);
            }
            
            findCDsOnTryCatch(pdg, cfg);
            findCDsFromEntry(pdg, cfg);
            findCDsOnDeclarations(pdg, cfg);
            
            removeTransitiveCDs(pdg);
        } catch (InterruptedException e) {
            new ArrayList<CD>(pdg.getCDEdges()).forEach(cd -> pdg.remove(cd));
            simplyFindCDsOnTimeout(pdg, cfg);
        }
    }
    
    private void simplyFindCDsOnTimeout(PDG pdg, CFG cfg) {
        for (CFGNode node : cfg.getNodes()) {
            if (node.isBranch()) {
                for (ControlFlow flow : node.getOutgoingFlows()) {
                    DominantStatement statement = cfg.getDominantStatement(flow);
                    if (statement != null) {
                        for (CFGNode postDominator : statement.getImmediatePostDominators()) {
                            CD cd = createCD(node, flow, postDominator);
                            pdg.add(cd);
                        }
                    }
                }
            }
        }
    }
    
    private boolean hasNestedStructure(CFG cfg) {
        DominantStatement statement = cfg.getDominantStatement(cfg.getEntryNode().getOutgoingTrueFlow());
        return statement != null && !statement.breakNestedStructure();
    }
    
    private void findCDsOnNestStructure(PDG pdg, CFG cfg) throws InterruptedException {
        for (CFGNode node : cfg.getNodes()) {
            if (Thread.currentThread().isInterrupted()) {
                throw new InterruptedException();
            }
            
            if (node.isBranch()) {
                boolean hasNestedStructure = node.getOutgoingFlows().stream()
                        .map(flow -> cfg.getDominantStatement(flow))
                        .allMatch(st -> (st != null && !st.breakNestedStructure()));
                if (hasNestedStructure) {
                    simplyFindCDs(pdg, cfg, node);
                } else {
                    findCDs(pdg, cfg, node);
                }
            }
        }
    }
    
    private void simplyFindCDs(PDG pdg, CFG cfg, CFGNode node) throws InterruptedException {
        for (ControlFlow flow : node.getOutgoingFlows()) {
            if (Thread.currentThread().isInterrupted()) {
                throw new InterruptedException();
            }
            
            DominantStatement statement = cfg.getDominantStatement(flow);
            for (CFGNode postDominator : statement.getImmediatePostDominators()) {
                CD cd = createCD(node, flow, postDominator);
                pdg.add(cd);
            }
        }
    }
    
    private void findCDs(PDG pdg, CFG cfg) throws InterruptedException {
        for (CFGNode node :cfg.getNodes()) {
            if (Thread.currentThread().isInterrupted()) {
                throw new InterruptedException();
            }
            
            if (node.isBranch()) {
                findCDs(pdg, cfg, node);
            }
        }
    }
    
    private void findCDs(PDG pdg, CFG cfg, CFGNode branchNode) throws InterruptedException {
        Set<CFGNode> postDominator = cfg.postDominator(branchNode);
        
        for (ControlFlow flow : branchNode.getOutgoingFlows()) {
            if (Thread.currentThread().isInterrupted()) {
                throw new InterruptedException();
            }
            
            CFGNode branchDstNode = flow.getDstNode();
            Set<CFGNode> postDominatorsForDstNode = cfg.postDominator(branchDstNode);
            postDominatorsForDstNode.add(branchDstNode);
            
            for (CFGNode node : postDominatorsForDstNode) {
                if (node.isStatement() && !branchNode.equals(node) && !postDominator.contains(node)) {
                    CD cd = createCD(branchNode, flow, node);
                    pdg.add(cd);
                }
            }
        }
    }
    
    private CD createCD(CFGNode branchNode, ControlFlow flow, CFGNode node) {
        CD edge = new CD(branchNode.getPDGNode(), node.getPDGNode());
        if (flow.isTrue()) {
            edge.setTrue();
        } else if (flow.isFalse()) {
            edge.setFalse();
        } else if (flow.isFallThrough()) {
            edge.setFallThrough();
        } else if (flow.isExceptionCatch()) {
            edge.setExceptionCatch();
        }
        return edge;
    }
    
    private void findCDsOnTryCatch(PDG bpdg, CFG cfg) {
        for (CFGNode cfgnode : cfg.getNodes()) {
            if (cfgnode.isTry()) {
                CFGTry trynode = (CFGTry)cfgnode;
                trynode.getCatchNodes().forEach(catchnode -> {
                    CD edge = new CD(trynode.getPDGNode(), catchnode.getPDGNode());
                    edge.setTrue();
                    bpdg.add(edge);
                });
                if (trynode.getFinallyNode() != null) {
                    CD edge = new CD(trynode.getPDGNode(), trynode.getFinallyNode().getPDGNode());
                    edge.setTrue();
                    bpdg.add(edge);
                }
            }
        }
    }
    
    private void findCDsFromEntry(PDG pdg, CFG cfg) {
        CFGNode entryNode = cfg.getEntryNode();
        for (PDGNode pdgnode : pdg.getNodes()) {
            if (!pdgnode.isEntry() && pdg.getIncomingCDEdges(pdgnode).size() == 0) {
                CD edge = new CD(entryNode.getPDGNode(), pdgnode);
                edge.setTrue();
                pdg.add(edge);
            }
        }
    }
    
    private void findCDsOnDeclarations(PDG pdg, CFG cfg) {
        for (CFGNode cfgnode : cfg.getNodes()) {
            if (cfgnode.isStatement()) {
                findCDsOnDeclarations(pdg, cfg, (CFGStatement)cfgnode);
            }
        }
    }
    
    private void findCDsOnDeclarations(PDG pdg, CFG cfg, CFGStatement cfgnode) {
        Set<JVariableReference> vars = new HashSet<>();
        vars.addAll(cfgnode.getDefVariables());
        vars.addAll(cfgnode.getUseVariables());
        
        for (JVariableReference jv : vars) {
            cfg.backwardReachableNodes(cfgnode, true, true, new StopConditionOnReachablePath() {
                
                @Override
                public boolean isStop(CFGNode node) {
                    if (node.isLocalDeclaration()) {
                        CFGStatement decnode = (CFGStatement)node;
                        if (decnode.defineVariable(jv)) {
                            if (!decnode.equals(cfgnode)) {
                                CD edge = new CD(decnode.getPDGNode(), cfgnode.getPDGNode());
                                edge.setDeclaration();
                                pdg.add(edge);
                            }
                            return true;
                        }
                    }
                    return false;
                }
            });
        }
    }
    
    private void removeTransitiveCDs(PDG pdg) {
        List<PDGNode> nodes = pdg.getNodes().stream().sorted().collect(Collectors.toList());
        Collections.reverse(nodes);
        for (PDGNode node: nodes) {
            Set<CD> edges = pdg.getIncomingCDEdges(node).stream()
                    .filter(e -> e.isTrue() || e.isFalse()).collect(Collectors.toSet());
            Set<CD> removed = new HashSet<>();
            for (CD edge1 : edges) {
                if (!removed.contains(edge1)) {
                    PDGNode node1 = edge1.getSrcNode();
                    for (CD edge2 : edges) {
                        PDGNode node2 = edge2.getSrcNode();
                        if (!node1.equals(node2) && !removed.contains(edge2)) {
                            if (CDFinder.getCDAncestors(pdg, node2).contains(node1)) {
                                removed.add(edge1);
                                break;
                            }
                        }
                    }
                }
            }
            for (CD edge : removed) {
                pdg.remove(edge);
            }
        }
    }
    
    public static List<PDGNode> getCDAncestors(PDG pdg, PDGNode anchor) {
        List<PDGNode> track = new ArrayList<PDGNode>();
        
        Stack<PDGNode> nodeStack = new Stack<>();
        nodeStack.push(anchor);
        
        while (!nodeStack.isEmpty()) {
            PDGNode node = nodeStack.pop();
            
            if (track.contains(node)) {
                continue;
            }
            track.add(node);
            
            pdg.getIncomingEdges(node).stream().map(edge -> (Dependence)edge)
                .filter(edge -> (edge.isTrue() || edge.isFalse()))
                .forEach(edge -> nodeStack.push(edge.getSrcNode()));
        }
        return track;
    }
}
