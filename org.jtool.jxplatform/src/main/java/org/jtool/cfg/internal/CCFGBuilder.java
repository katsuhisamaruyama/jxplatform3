/*
 *  Copyright 2022-2023
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.cfg.internal;

import org.jtool.cfg.CCFG;
import org.jtool.cfg.CFG;
import org.jtool.cfg.CCFGEntry;
import org.jtool.cfg.CFGNode;
import org.jtool.srcmodel.JavaClass;
import org.jtool.srcmodel.JavaField;
import org.jtool.srcmodel.JavaMethod;
import org.jtool.srcmodel.JavaProject;
import java.util.Set;
import java.util.HashSet;

/**
 * Builds a CCFG that corresponds to a class.
 * 
 * @author Katsuhisa Maruyama
 */
class CCFGBuilder {
    
    static CCFG build(JavaProject jproject) {
        CCFG ccfg = new CCFG();
        jproject.getClasses().forEach(jclass -> build(ccfg, jclass, new HashSet<>()));
        return ccfg;
    }
    
    static CCFG build(JavaClass jclass) {
        return build(jclass, new HashSet<>());
    }
    
    static CCFG build(JavaClass jclass, Set<CFG> cfgs) {
        CCFG ccfg = new CCFG();
        build(ccfg, jclass, cfgs);
        return ccfg;
    }
    
    private static CFG getExistingCFG(String fqn, Set<CFG> cfgs) {
        return cfgs.stream().filter(cfg -> cfg.getQualifiedName().fqn().equals(fqn)).findFirst().orElse(null);
    }
    
    private static void build(CCFG ccfg, JavaClass jclass, Set<CFG> cfgs) {
        CCFGEntry entry;
        if (jclass.isEnum()) {
            entry = new CCFGEntry(jclass, CFGNode.Kind.enumEntry);
        } else if (jclass.isInterface()) {
            entry = new CCFGEntry(jclass, CFGNode.Kind.interfaceEntry);
        } else {
            entry = new CCFGEntry(jclass, CFGNode.Kind.classEntry);
        }
        ccfg.setEntryNode(entry);
        
        CFGStore cfgStore = jclass.getJavaProject().getCFGStore();
        for (JavaMethod jmethod : jclass.getMethods()) {
            CFG cfg = getExistingCFG(jmethod.getQualifiedName().fqn(), cfgs);
            if (cfg == null) {
                cfg = cfgStore.getCFG(jmethod);
            }
            
            if (cfg != null) {
                ccfg.add(cfg);
                entry.addMethod(cfg);
            }
        }
        
        for (JavaField jfield : jclass.getFields()) {
            CFG cfg = getExistingCFG(jfield.getQualifiedName().fqn(), cfgs);
            if (cfg == null) {
                cfg = cfgStore.getCFG(jfield);
            }
            
            if (cfg != null) {
                ccfg.add(cfg);
                entry.addField(cfg);
            }
        }
        
        for (JavaClass jc : jclass.getInnerClasses()) {
            CCFG ccfg2 = build(jc);
            entry.addClass(ccfg2);
        }
    }
}
