/*
 *  Copyright 2020
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.jxplatform.builder.pdg;

import org.jtool.pdg.CD;
import org.jtool.pdg.PDG;
import org.jtool.pdg.PDGNode;
import org.jtool.cfg.CFG;
import org.jtool.cfg.CFGMethodCall;
import org.jtool.cfg.CFGNode;
import org.jtool.cfg.CFGParameter;
import org.jtool.cfg.CFGStatement;
import org.jtool.cfg.ControlFlow;
import org.jtool.cfg.JReference;
import org.jtool.cfg.StopConditionOnReachablePath;
import java.util.Set;
import java.util.HashSet;

/**
 * Finds control dependences in a PDG.
 * All methods of this class are not intended to be directly called by clients.
 * 
 * @author Katsuhisa Maruyama
 */
public class CDFinder {
    
    public static void find(PDG pdg, CFG cfg) {
        findCDs(pdg, cfg);
        findCDsFromEntry(pdg, cfg);
        addCDsFromEntry(pdg);
        findCDsOnDeclarations(pdg, cfg);
    }
    
    private static void findCDs(PDG pdg, CFG cfg) {
        for (CFGNode cfgnode : cfg.getNodes()) {
            if (cfgnode.isBranch()) {
                findCDs(pdg, cfg, cfgnode);
            }
            if (cfgnode.isMethodCall()) {
                findCDsOnParameters(pdg, (CFGMethodCall)cfgnode);
            }
        }
    }
    
    private static void findCDs(PDG pdg, CFG cfg, CFGNode branchNode) {
        Set<CFGNode> postDominator = cfg.postDominator(branchNode);
        for (ControlFlow branch : branchNode.getOutgoingFlows()) {
            CFGNode branchDstNode = branch.getDstNode();
            Set<CFGNode> postDominatorForBranch = cfg.postDominator(branchDstNode);
            postDominatorForBranch.add(branchDstNode);
            
            for (CFGNode cfgnode : postDominatorForBranch) {
                if (cfgnode.isStatementNotParameter() && !cfgnode.isReceiver() &&
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
                    pdg.add(edge);
                }
            }
        }
    }
    
    private static void findCDsOnParameters(PDG pdg, CFGMethodCall callNode) {
        for (CFGParameter cfgnode : callNode.getActualIns()) {
            CD edge = new CD(callNode.getPDGNode(), cfgnode.getPDGNode());
            edge.setTrue();
            pdg.add(edge);
        }
        
        CFGParameter returnNode = callNode.getActualOutForReturn();
        if (returnNode != null) {
            CD edge = new CD(callNode.getPDGNode(), returnNode.getPDGNode());
            edge.setTrue();
            pdg.add(edge);
        }
        
        CFGNode receiverNode = callNode.getReceiver();
        if (receiverNode != null) {
            CD edge = new CD(receiverNode.getPDGNode(), callNode.getPDGNode());
            edge.setTrue();
            pdg.add(edge);
        }
    }
    
    private static void findCDsFromEntry(PDG pdg, CFG cfg) {
        CFGNode entryNode = cfg.getEntryNode();
        Set<CFGNode> postDominator = cfg.postDominator(entryNode);
        for (CFGNode cfgnode : postDominator) {
            if ((cfgnode.isStatementNotParameter() && !cfgnode.isMethodCall()) || cfgnode.isFormal()) {
                CD edge = new CD(entryNode.getPDGNode(), cfgnode.getPDGNode());
                edge.setTrue();
                pdg.add(edge);
            }
        }
    }
    
    private static void addCDsFromEntry(PDG pdg) {
        for (PDGNode pdgnode : pdg.getNodes()) {
            if (!pdgnode.equals(pdg.getEntryNode()) && pdgnode.getNumOfIncomingTrueFalseCDs() == 0) {
                CD edge = new CD(pdg.getEntryNode(), pdgnode);
                edge.setTrue();
                pdg.add(edge);
            }
        }
    }
    
    private static void findCDsOnDeclarations(PDG pdg, CFG cfg) {
        for (CFGNode cfgnode : cfg.getNodes()) {
            if (cfgnode.isStatement()) {
                findCDsOnDeclarations(pdg,cfg, (CFGStatement)cfgnode);
            }
        }
    }
    
    private static void findCDsOnDeclarations(PDG pdg, CFG cfg, CFGStatement cfgnode) {
        Set<JReference> vars = new HashSet<>();
        vars.addAll(cfgnode.getDefVariables());
        vars.addAll(cfgnode.getUseVariables());
        for (JReference jv : vars) {
            cfg.backwardReachableNodes(cfgnode, true, new StopConditionOnReachablePath() {
                
                @Override
                public boolean isStop(CFGNode node) {
                    if (node.isLocalDeclaration()) {
                        CFGStatement decnode = (CFGStatement)node;
                        if (decnode.defineVariable(jv)) {
                            CD edge = new CD(decnode.getPDGNode(), cfgnode.getPDGNode());
                            edge.setDeclaration();
                            pdg.add(edge);
                            return true;
                        }
                    }
                    return false;
                }
            });
        }
    }
}
