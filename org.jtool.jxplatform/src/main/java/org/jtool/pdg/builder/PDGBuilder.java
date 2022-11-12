/*
 *  Copyright 2022
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.pdg.builder;

import org.jtool.pdg.PDG;
import org.jtool.pdg.ClDG;
import org.jtool.pdg.Dependence;
import org.jtool.pdg.ClDGEntry;
import org.jtool.pdg.PDGEntry;
import org.jtool.pdg.PDGNode;
import org.jtool.pdg.PDGStatement;
import org.jtool.cfg.CCFG;
import org.jtool.cfg.CFG;
import org.jtool.cfg.CFGEntry;
import org.jtool.cfg.CFGNode;
import org.jtool.cfg.CFGStatement;
import java.util.Map;

/**
 * Builds a PDG for a class member (a method, constructor, initializer, or field).
 * 
 * @author Katsuhisa Maruyama
 */
public class PDGBuilder {
    
    static ClDG buildClDG(CCFG ccfg, Map<String, BarePDG> bpdgMap) {
        ClDG cldg = new ClDG();
        ClDGEntry entry = new ClDGEntry(ccfg.getEntryNode());
        cldg.setEntryNode(entry);
        cldg.add(entry);
        
        for (CFG cfg : ccfg.getCFGs()) {
            PDG pdg = buildPDG(cfg, bpdgMap);
            cldg.add(pdg);
            
            Dependence edge = new Dependence(entry, pdg.getEntryNode());
            edge.setClassMember();
            cldg.addInterEdge(edge);
        }
        return cldg;
    }
    
    private static PDG buildPDG(CFG cfg, Map<String, BarePDG> bpdgMap) {
        BarePDG bpdg = bpdgMap.get(cfg.getQualifiedName().fqn()); 
        if (bpdg == null) {
            bpdg = new BarePDG(cfg);
            createNodes(bpdg, cfg);
            
            CDFinder.find(bpdg, cfg);
            DDFinder.find(bpdg, cfg);
            bpdgMap.put(cfg.getQualifiedName().fqn(), bpdg);
        }
        
        PDG pdg = new PDG();
        pdg.setBarePDG(bpdg);
        PDGEntry entry = (PDGEntry)pdg.getNodes().stream()
                                      .filter(node -> node.isEntry()).findFirst().orElse(null);
        pdg.setEntryNode(entry);
        return pdg;
    }
    
    private static void createNodes(BarePDG bpdg, CFG cfg) {
        cfg.getNodes().stream().map(cfgnode -> createNode(bpdg, cfgnode))
                      .filter(pdgnode -> pdgnode != null)
                      .forEach(pdgnode -> bpdg.add(pdgnode));
    }
    
    private static PDGNode createNode(BarePDG bpdg, CFGNode node) {
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
