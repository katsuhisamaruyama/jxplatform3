/*
 *  Copyright 2020
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.jxplatform.builder.cfg;

import org.jtool.cfg.BasicBlock;
import org.jtool.cfg.CFG;
import org.jtool.cfg.CFGNode;

/**
 * Calculates and stores basic blocks of a CFG.
 * 
 * @author Katsuhisa Maruyama
 */
public class BasicBlockBuilder {
    
    public static void create(CFG cfg) {
        CFGNode start = cfg.getEntryNode();
        CFGNode first = start.getSuccessors().iterator().next();
        for (CFGNode node : cfg.getNodes()) {
            if (node.equals(first) || node.isJoin() || (node.isNextToBranch() && !node.equals(start))) {
                BasicBlock block = new BasicBlock(node);
                cfg.add(block);
                block.add(node);
            }
        }
        
        for (BasicBlock block : cfg.getBasicBlocks()) {
            collectNodesInBlock(block, cfg);
        }
    }
    
    private static void collectNodesInBlock(BasicBlock block, CFG cfg) {
        CFGNode node = getTrueSucc(block.getLeader());
        while (node != null && !node.isLeader() && !node.equals(cfg.getExitNode())) {
            block.add(node);   
            node = getTrueSucc(node);
        }
    }
    
    private static CFGNode getTrueSucc(CFGNode node) {
        return node.getOutgoingFlows().stream().filter(edge -> edge.isTrue()).map(edge -> edge.getDstNode()).findFirst().orElse(null);
    }
}
