/*
 *  Copyright 2020
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.cfg.builder;

import org.jtool.cfg.CCFG;
import org.jtool.cfg.CFG;
import org.jtool.cfg.CFGNode;
import org.jtool.srcmodel.JavaClass;
import org.jtool.srcmodel.JavaField;
import org.jtool.srcmodel.JavaMethod;
import org.jtool.srcmodel.JavaProject;
import org.jtool.jxplatform.refmodel.BytecodeClassStore;
import java.util.Map;
import java.util.HashMap;
import java.util.Set;
import java.util.HashSet;

/**
 * A repository that stores information on CFGs in the project.
 * 
 * @author Katsuhisa Maruyama
 */
public class CFGStore {
    
    private JavaProject jproject;
    private BytecodeClassStore bcStore;
    
    private int analysisLevel;
    
    private Map<String, CFG> cfgs = new HashMap<>();
    private Map<String, CCFG> ccfgs = new HashMap<>();
    
    private Set<String> resolvedReferences = new HashSet<>();
    
    public CFGStore() {
        CFGNode.resetId();
    }
    
    public void resetId() {
        CFGNode.resetId();
    }
    
    public void create(JavaProject jproject) {
        this.jproject = jproject;
        this.bcStore = new BytecodeClassStore(jproject);
        bcStore.create();
    }
    
    public void destroy() {
        bcStore.destroy();
    }
    
    public void flushCache() {
        bcStore.writeProjectCache();
    }
    
    public BytecodeClassStore getBCStore() {
        return bcStore;
    }
    
    public int analysisLevel() {
        return analysisLevel;
    }
    
    public JavaProject getJavaProject() {
        return jproject;
    }
    
    private void addCFG(CFG cfg) {
        cfgs.put(cfg.getQualifiedName().fqn(), cfg);
    }
    
    private void addCCFG(CCFG ccfg) {
        ccfgs.put(ccfg.getQualifiedName().fqn(), ccfg);
    }
    
    public CFG findCFG(String fqn) {
        return cfgs.get(fqn);
    }
    
    public CCFG findCCFG(String fqn) {
        return ccfgs.get(fqn);
    }
    
    public CCFG getCCFG(JavaClass jclass, boolean force) {
        if (!force) {
            CCFG ccfg = ccfgs.get(jclass.getQualifiedName().fqn());
            if (ccfg != null) {
                return ccfg;
            }
        }
        
        CCFG ccfg = CCFGBuilder.build(jclass, force);
        addCCFG(ccfg);
        
        ccfg.getEntryNode().getMethods().forEach(cfg -> addCFG(cfg));
        ccfg.getEntryNode().getFields().forEach(cfg -> addCFG(cfg));
        ccfg.getEntryNode().getClasses().forEach(ccfg2 -> {
            addCCFG(ccfg2);
            ccfg2.getCFGs().forEach(cfg -> addCFG(cfg));
        });
        return ccfg;
    }
    
    public CFG getCFG(JavaMethod jmethod, boolean force) {
        if (!force) {
            CFG cfg = cfgs.get(jmethod.getQualifiedName().fqn());
            if (cfg != null) {
                if (!resolvedReferences.contains(cfg.getQualifiedName().fqn())) {
                    CFGMethodBuilder.resolveReferences(jmethod.getJavaProject(), cfg);
                    resolvedReferences.add(cfg.getQualifiedName().fqn());
                }
                return cfg;
            }
        }
        
        CFG cfg = CFGMethodBuilder.build(jmethod, true);
        if (cfg != null) {
            addCFG(cfg);
            resolvedReferences.add(cfg.getQualifiedName().fqn());
        }
        return cfg;
    }
    
    public CFG getCFG(JavaField jfield, boolean force) {
        if (!force) {
            CFG cfg = cfgs.get(jfield.getQualifiedName().fqn());
            if (cfg != null) {
                if (!resolvedReferences.contains(cfg.getQualifiedName().fqn())) {
                    CFGMethodBuilder.resolveReferences(jfield.getJavaProject(), cfg);
                    resolvedReferences.add(cfg.getQualifiedName().fqn());
                }
                return cfg;
            }
        }
        
        CFG cfg = CFGFieldBuilder.build(jfield, true);
        if (cfg != null) {
            addCFG(cfg);
            resolvedReferences.add(cfg.getQualifiedName().fqn());
        }
        return cfg;
    }
    
    public CFG getCFGWithoutResolvingMethodCalls(JavaMethod jmethod) {
        CFG cfg = cfgs.get(jmethod.getQualifiedName().fqn());
        if (cfg != null) {
            return cfg;
        }
        
        cfg = CFGMethodBuilder.build(jmethod, false);
        if (cfg != null) {
            addCFG(cfg);
        }
        return cfg;
    }
    
    public CFG getCFGWithoutResolvingMethodCalls(JavaField jfield) {
        CFG cfg = cfgs.get(jfield.getQualifiedName().fqn());
        if (cfg != null) {
            return cfg;
        }
        
        cfg = CFGFieldBuilder.build(jfield, false);
        if (cfg != null) {
            addCFG(cfg);
        }
        return cfg;
    }
}
