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
        Resolver.initialize();
    }
    
    public void resetId() {
        CFGNode.resetId();
    }
    
    public void create(JavaProject jproject) {
        this.jproject = jproject;
        createBCStore();
    }
    
    public void cleanup() {
        bcStore.destroy();
        bcStore = null;
    }
    
    public void destroy() {
        cfgs = null;
        ucfgs = null;
        ccfgs = null;
        bcStore = null;
    }
    
    public BytecodeClassStore getBCStore() {
        if (bcStore == null) {
            createBCStore();
        }
        return bcStore;
    }
    
    private void createBCStore() {
        this.bcStore = new BytecodeClassStore(jproject);
        bcStore.create();
    }
    
    private void updateBCStore() {
        if (bcStore == null) {
            createBCStore();
        } else {
            bcStore.update();
        }
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
    
    private void removeCFGs(JavaClass jclass) {
        jclass.getObsoleteClasses().forEach(jc -> {
            ccfgs.remove(jclass.getQualifiedName().fqn());
            jc.getMethods().forEach(jmethod -> {
                cfgs.remove(jmethod.getQualifiedName().fqn());
                ucfgs.remove(jmethod.getQualifiedName().fqn());
            });
        });
    }
    
    public CCFG getCCFG(JavaClass jclass, boolean force) {
        if (!force) {
            CCFG ccfg = ccfgs.get(jclass.getQualifiedName().fqn());
            if (ccfg != null) {
                return ccfg;
            }
        }
        
        if (force) {
            updateBCStore();
            removeCFGs(jclass);
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
            
            cfg = ucfgs.get(jmethod.getQualifiedName().fqn());
            if (cfg != null) {
                ucfgs.remove(cfg.getQualifiedName().fqn());
                Resolver.resolveReferences(jmethod.getJavaProject(), cfg);
                Resolver.resolveLocalAlias(jmethod.getJavaProject(), cfg);
                addCFG(cfg, true);
                return cfg;
            }
        }
        
        if (force) {
            updateBCStore();
            removeCFGs(jmethod.getDeclaringClass());
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
            
            cfg = ucfgs.get(jfield.getQualifiedName().fqn());
            if (cfg != null) {
                ucfgs.remove(cfg.getQualifiedName().fqn());
                Resolver.resolveReferences(jfield.getJavaProject(), cfg);
                Resolver.resolveLocalAlias(jfield.getJavaProject(), cfg);
                addCFG(cfg, true);
                return cfg;
            }
        }
        
        if (force) {
            updateBCStore();
            removeCFGs(jfield.getDeclaringClass());
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
