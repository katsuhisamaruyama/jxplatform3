/*
 *  Copyright 2022
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.cfg.builder;

import org.jtool.cfg.BasicBlock;
import org.jtool.cfg.CFG;
import org.jtool.cfg.CFGNode;

/**
 * Calculates and stores basic blocks of a CFG.
 * 
 * @author Katsuhisa Maruyama
 */
public class BasicBlockBuilder {
    
    public static void create(CFG cfg, boolean force) {
        if (force) {
            cfg.clearBasicBlocks();
        }
        
        if (cfg.getBasicBlocks().size() > 0) {
            return;
        }
        
        collectLeaders(cfg, cfg.getEntryNode());
        
        for (BasicBlock block : cfg.getBasicBlocks()) {
            collectNodesInBlock(block, cfg);
        }
    }
    
    private static void collectLeaders(CFG cfg, CFGNode start) {
        CFGNode first = start.getSuccessors().iterator().next();
        for (CFGNode node : cfg.getNodes()) {
            if (node.equals(first) || (node.isJoin() && !node.isWhile()) ||
                    (node.isNextToBranch() && !node.equals(start))) {
                BasicBlock block = new BasicBlock(node);
                cfg.add(block);
                block.add(node);
            }
        }
    }
    
    private static void collectNodesInBlock(BasicBlock block, CFG cfg) {
        CFGNode node = getTrueSucc(block.getLeader());
        while (node != null && !isLeader(cfg, node) && !node.equals(cfg.getExitNode())) {
            block.add(node);
            node = getTrueSucc(node);
        }
    }
    
    private static boolean isLeader(CFG cfg, final CFGNode node) {
        return cfg.getBasicBlocks().stream().anyMatch(b -> node.equals(b.getLeader()));
    }
    
    private static CFGNode getTrueSucc(CFGNode node) {
        return node.getOutgoingFlows().stream()
                                      .filter(e -> e.isTrue() && !e.isLoopBack())
                                      .map(edge -> edge.getDstNode())
                                      .findFirst().orElse(null);
    }
}
