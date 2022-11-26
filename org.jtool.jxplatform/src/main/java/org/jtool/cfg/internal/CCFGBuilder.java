/*
 *  Copyright 2022
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

/**
 * Builds a CCFG that corresponds to a class.
 * 
 * @author Katsuhisa Maruyama
 */
class CCFGBuilder {
    
    static CCFG build(JavaProject jproject, boolean force) {
        CCFG ccfg = new CCFG();
        jproject.getClasses().forEach(jclass -> build(ccfg, jclass, force));
        return ccfg;
    }
    
    static CCFG build(JavaClass jclass, boolean force) {
        CCFG ccfg = new CCFG();
        build(ccfg, jclass, force);
        return ccfg;
    }
    
    private static void build(CCFG ccfg, JavaClass jclass, boolean force) {
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
            CFG cfg = cfgStore.getCFG(jmethod, force);
            if (cfg != null) {
                ccfg.add(cfg);
                entry.addMethod(cfg);
            }
        }
        
        for (JavaField jfield : jclass.getFields()) {
            CFG cfg = cfgStore.getCFG(jfield, force);
            if (cfg != null) {
                ccfg.add(cfg);
                entry.addField(cfg);
            }
        }
        
        for (JavaClass jc : jclass.getInnerClasses()) {
            CCFG ccfg2 = build(jc, force);
            entry.addClass(ccfg2);
        }
    }
}
