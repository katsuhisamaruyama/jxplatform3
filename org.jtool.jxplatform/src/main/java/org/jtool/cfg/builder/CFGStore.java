/*
 *  Copyright 2022
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
import java.util.Collection;

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
    private Map<String, CFG> ucfgs = new HashMap<>();
    private Map<String, CCFG> ccfgs = new HashMap<>();
    
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
    
    public Collection<CCFG> getCCFG() {
        return ccfgs.values();
    }
    
    private void addCFG(CFG cfg, boolean toBeResolved) {
        if (cfg != null) {
            if (toBeResolved) {
                cfgs.put(cfg.getQualifiedName().fqn(), cfg);
            } else {
                ucfgs.put(cfg.getQualifiedName().fqn(), cfg);
            }
        }
    }
    
    private void addCCFG(CCFG ccfg, boolean toBeResolved) {
        if (ccfg != null) {
            if (toBeResolved) {
                ccfgs.put(ccfg.getQualifiedName().fqn(), ccfg);
            }
            ccfg.getCFGs().forEach(cfg -> addCFG(cfg, toBeResolved));
        }
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
        addCCFG(ccfg, true);
        return ccfg;
    }
    
    public CFG getCFG(JavaMethod jmethod, boolean force) {
        if (!force) {
            CFG cfg = cfgs.get(jmethod.getQualifiedName().fqn());
            if (cfg != null) {
                return cfg;
            }
            
            CFG ucfg = ucfgs.get(jmethod.getQualifiedName().fqn());
            if (ucfg != null) {
                cfg = ucfg.clone();
                Resolver.resolveReferences(jmethod.getJavaProject(), cfg);
                Resolver.resolveLocalAlias(cfg);
                addCFG(cfg, true);
                return cfg;
            }
        }
        
        CFG cfg = CFGMethodBuilder.build(jmethod, true);
        addCFG(cfg, true);
        return cfg;
    }
    
    public CFG getCFG(JavaField jfield, boolean force) {
        if (!force) {
            CFG cfg = cfgs.get(jfield.getQualifiedName().fqn());
            if (cfg != null) {
                return cfg;
            }
            
            CFG ucfg = ucfgs.get(jfield.getQualifiedName().fqn());
            if (ucfg != null) {
                cfg = ucfg.clone();
                Resolver.resolveReferences(jfield.getJavaProject(), cfg);
                Resolver.resolveLocalAlias(cfg);
                addCFG(cfg, true);
                return cfg;
            }
        }
        
        CFG cfg = CFGFieldBuilder.build(jfield, true);
        addCFG(cfg, true);
        return cfg;
    }
    
    CFG getUnresolvedCFG(JavaMethod jmethod) {
        CFG cfg = cfgs.get(jmethod.getQualifiedName().fqn());
        if (cfg != null) {
            return cfg;
        }
        
        cfg = ucfgs.get(jmethod.getQualifiedName().fqn());
        if (cfg != null) {
            return cfg;
        }
        
        cfg = CFGMethodBuilder.build(jmethod, false);
        addCFG(cfg, false);
        return cfg;
    }
    
    CFG getUnresolvedCFG(JavaField jfield) {
        CFG cfg = cfgs.get(jfield.getQualifiedName().fqn());
        if (cfg != null) {
            return cfg;
        }
        
        cfg = ucfgs.get(jfield.getQualifiedName().fqn());
        if (cfg != null) {
            return cfg;
        }
        
        cfg = CFGFieldBuilder.build(jfield, false);
        addCFG(cfg, false);
        return cfg;
    }
}
