/*
 *  Copyright 2022
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.pdg.builder;

import org.jtool.pdg.CD;
import org.jtool.pdg.PDGNode;
import org.jtool.cfg.CFG;
import org.jtool.cfg.CFGNode;
import org.jtool.cfg.CFGStatement;
import org.jtool.cfg.CFGTry;
import org.jtool.cfg.ControlFlow;
import org.jtool.cfg.JVariableReference;
import org.jtool.cfg.StopConditionOnReachablePath;
import java.util.Set;
import java.util.HashSet;

/**
 * Finds control dependences in a bare PDG.
 * 
 * @author Katsuhisa Maruyama
 */
public class CDFinder {
    
    public static void find(BarePDG bpdg , CFG cfg) {
        findCDs(bpdg, cfg);
        findCDsOnTryCatch(bpdg, cfg);
        findCDsFromEntry(bpdg, cfg);
        findCDsOnDeclarations(bpdg, cfg);
    }
    
    private static void findCDs(BarePDG bpdg, CFG cfg) {
        for (CFGNode cfgnode : cfg.getNodes()) {
            if (cfgnode.isBranch()) {
                findCDs(bpdg, cfg, cfgnode);
            }
        }
    }
    
    private static void findCDs(BarePDG bpdg, CFG cfg, CFGNode branchNode) {
        Set<CFGNode> postDominator = cfg.postDominator(branchNode);
        
        for (ControlFlow branch : branchNode.getOutgoingFlows()) {
            CFGNode branchDstNode = branch.getDstNode();
            Set<CFGNode> postDominatorForBranch = cfg.postDominator(branchDstNode);
            postDominatorForBranch.add(branchDstNode);
            
            for (CFGNode cfgnode : postDominatorForBranch) {
                if (cfgnode.isStatement() && 
                  !branchNode.equals(cfgnode) && !postDominator.contains(cfgnode)) {
                    CD edge = new CD(branchNode.getPDGNode(), cfgnode.getPDGNode());
                    if (branch.isTrue()) {
                        edge.setTrue();
                    } else if (branch.isFalse()) {
                        edge.setFalse();
                    } else if (branch.isFallThrough()) {
                        edge.setFallThrough();
                    } else if (branch.isExceptionCatch()) {
                        edge.setExceptionCatch();
                    }
                    bpdg.add(edge);
                }
            }
        }
    }
    
    private static void findCDsFromEntry(BarePDG bpdg, CFG cfg) {
        CFGNode entryNode = cfg.getEntryNode();
        Set<CFGNode> postDominator = cfg.postDominator(entryNode);
        for (CFGNode cfgnode : postDominator) {
            if ((cfgnode.isStatementNotParameter() && !cfgnode.isMethodCall()) || cfgnode.isFormal()) {
                CD edge = new CD(entryNode.getPDGNode(), cfgnode.getPDGNode());
                edge.setTrue();
                bpdg.add(edge);
            }
        }
        
        for (PDGNode pdgnode : bpdg.getNodes()) {
            if (!pdgnode.isEntry() && pdgnode.getIncomingCDEdges().size() == 0) {
                CD edge = new CD(entryNode.getPDGNode(), pdgnode);
                edge.setTrue();
                bpdg.add(edge);
            }
        }
    }
    
    private static void findCDsOnDeclarations(BarePDG bpdg, CFG cfg) {
        for (CFGNode cfgnode : cfg.getNodes()) {
            if (cfgnode.isStatement()) {
                findCDsOnDeclarations(bpdg, cfg, (CFGStatement)cfgnode);
            }
        }
    }
    
    private static void findCDsOnDeclarations(BarePDG bpdg, CFG cfg, CFGStatement cfgnode) {
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
                                bpdg.add(edge);
                            }
                            return true;
                        }
                    }
                    return false;
                }
            });
        }
    }
    
    private static void findCDsOnTryCatch(BarePDG bpdg, CFG cfg) {
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
}
