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
import java.util.Set;
import java.util.HashSet;
import java.util.Collection;

/**
 * A repository that stores information on CFGs in the project.
 * 
 * @author Katsuhisa Maruyama
 */
public class CFGStore {
    
    private JavaProject jproject;
    private BytecodeClassStore bcStore;
    
    private Map<String, CFG> cfgs = new HashMap<>();
    private Map<String, CFG> ucfgs = new HashMap<>();
    private Map<String, CCFG> ccfgs = new HashMap<>();
    
    private Set<String> CFGsForVisit = new HashSet<>();
    
    public CFGStore() {
        CFGNode.resetId();
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
    
    public JavaProject getJavaProject() {
        return jproject;
    }
    
    public Collection<CCFG> getCCFG() {
        return ccfgs.values();
    }
    
    public CFG findCFG(String fqn) {
        return cfgs.get(fqn);
    }
    
    public CCFG findCCFG(String fqn) {
        return ccfgs.get(fqn);
    }
    
    public CCFG getCCFG(JavaClass jclass, boolean force) {
        if (force) {
            updateBCStore();
            removeCFGs(jclass);
        }
        
        CCFG ccfg = ccfgs.get(jclass.getQualifiedName().fqn());
        if (ccfg != null) {
            return ccfg;
        }
        
        ccfg = CCFGBuilder.build(jclass, force);
        ccfgs.put(ccfg.getQualifiedName().fqn(), ccfg);
        ccfg.getCFGs().forEach(cfg -> cfgs.put(cfg.getQualifiedName().fqn(), cfg));
        return ccfg;
    }
    
    public CCFG generateUnregisteredCCFG(JavaClass jclass, boolean force) {
        return CCFGBuilder.build(jclass, force);
    }
    
    private CFG findRegisteredCFG(String fqn) {
        CFG cfg = cfgs.get(fqn);
        if (cfg != null) {
            return cfg;
        }
        
        return ucfgs.get(fqn);
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
    
    public CFG getCFG(JavaMethod jmethod, boolean force) {
        String fqn = jmethod.getQualifiedName().fqn();
        
        if (CFGsForVisit.contains(fqn)) {
            return findRegisteredCFG(fqn);
        }
        
        if (force) {
            updateBCStore();
            removeCFGs(jmethod.getDeclaringClass());
        }
        
        CFG cfg = cfgs.get(fqn);
        if (cfg != null) {
            return cfg;
        }
        
        cfg = ucfgs.get(fqn);
        if (cfg == null) {
            cfg = CFGMethodBuilder.build(jmethod);
            ucfgs.put(fqn, cfg);
        }
        
        CFGsForVisit.add(fqn);
        Resolver.resolveReferences(jmethod.getJavaProject(), cfg);
        Resolver.resolveLocalAlias(jmethod.getJavaProject(), cfg);
        CFGsForVisit.remove(fqn);
        
        ucfgs.remove(fqn);
        cfgs.put(fqn, cfg);
        return cfg;
    }
    
    public CFG getCFG(JavaField jfield, boolean force) {
        String fqn = jfield.getQualifiedName().fqn();
        
        if (CFGsForVisit.contains(fqn)) {
            return findRegisteredCFG(fqn);
        }
        
        if (force) {
            updateBCStore();
            removeCFGs(jfield.getDeclaringClass());
        }
        
        CFG cfg = cfgs.get(fqn);
        if (cfg != null) {
            return cfg;
        }
        
        cfg = ucfgs.get(fqn);
        if (cfg == null) {
            cfg = CFGFieldBuilder.build(jfield);
            ucfgs.put(fqn, cfg);
        }
        
        CFGsForVisit.add(fqn);
        Resolver.resolveLocalAlias(jfield.getJavaProject(), cfg);
        CFGsForVisit.remove(fqn);
        
        ucfgs.remove(fqn);
        cfgs.put(fqn, cfg);
        return cfg;
    }
    
    CFG getUnresolvedCFG(JavaMethod jmethod) {
        String fqn = jmethod.getQualifiedName().fqn();
        
        CFG cfg = findRegisteredCFG(fqn);
        if (cfg != null) {
            return cfg;
        }
        
        cfg = CFGMethodBuilder.build(jmethod);
        ucfgs.put(fqn, cfg);
        return cfg;
    }
    
    CFG getUnresolvedCFG(JavaField jfield) {
        String fqn = jfield.getQualifiedName().fqn();
        
        CFG cfg = findRegisteredCFG(fqn);
        if (cfg != null) {
            return cfg;
        }
        
        cfg = CFGFieldBuilder.build(jfield);
        ucfgs.put(fqn, cfg);
        return cfg;
    }
}
