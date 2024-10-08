/*
 *  Copyright 2022-2023
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.pdg.internal;

import org.jtool.srcmodel.JavaClass;
import org.jtool.pdg.PDG;
import org.jtool.pdg.ClDG;
import org.jtool.pdg.InterPDGCD;
import org.jtool.pdg.ClDGEntry;
import org.jtool.pdg.PDGEntry;
import org.jtool.pdg.PDGNode;
import org.jtool.pdg.PDGStatement;
import org.jtool.cfg.CCFG;
import org.jtool.cfg.CFG;
import org.jtool.cfg.CFGEntry;
import org.jtool.cfg.CFGNode;
import org.jtool.cfg.CFGStatement;

/**
 * Builds a PDG for a class member (a method, constructor, initializer, or field).
 * 
 * @author Katsuhisa Maruyama
 */
public class PDGBuilder {
    
    static ClDG buildClDG(JavaClass jclass, CCFG ccfg) {
        ClDG cldg = new ClDG();
        ClDGEntry entry = new ClDGEntry(ccfg.getEntryNode());
        cldg.setEntryNode(entry);
        
        for (CFG cfg : ccfg.getCFGs()) {
            PDG pdg = buildPDG(jclass, cfg);
            cldg.add(pdg);
            
            InterPDGCD edge = new InterPDGCD(entry, pdg.getEntryNode());
            edge.setClassMember();
            cldg.addClassMemberEdge(edge);
        }
        return cldg;
    }
    
    static PDG buildPDG(JavaClass jclass, CFG cfg) {
        PDG pdg = new PDG(cfg);
        createNodes(pdg, cfg);
        
        PDGEntry entry = (PDGEntry)pdg.getNodes().stream()
                .filter(node -> node.isEntry()).findFirst().orElse(null);
        pdg.setEntryNode(entry);
        
        CDFinder cdfinder = new CDFinder();
        cdfinder.find(jclass.getJavaProject(), pdg, cfg);
        
        DDFinder ddfinder = new DDFinder();
        ddfinder.find(jclass.getJavaProject(), pdg, cfg);
        return pdg;
    }
    
    private static void createNodes(PDG pdg, CFG cfg) {
        cfg.getNodes().stream().map(cfgnode -> createNode(cfgnode))
                      .filter(pdgnode -> pdgnode != null)
                      .forEach(pdgnode -> pdg.add(pdgnode));
    }
    
    private static PDGNode createNode(CFGNode node) {
        if (node.isMethodEntry() || node.isConstructorEntry() || node.isInitializerEntry() ||
                   node.isFieldEntry() || node.isEnumConstantEntry()) {
            PDGEntry pdgnode = new PDGEntry((CFGEntry)node);
            return pdgnode;
        } else if (node.isStatement()) {
            PDGStatement pdgnode = new PDGStatement((CFGStatement)node);
            return pdgnode;
        }
        return null;
    }
}
