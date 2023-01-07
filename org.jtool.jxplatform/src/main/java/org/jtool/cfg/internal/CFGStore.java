/*
 *  Copyright 2022-2023
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.cfg.internal;

import org.jtool.cfg.CCFG;
import org.jtool.cfg.CFG;
import org.jtool.cfg.CFGNode;
import org.jtool.cfg.internal.refmodel.BytecodeClassStore;
import org.jtool.srcmodel.JavaClass;
import org.jtool.srcmodel.JavaField;
import org.jtool.srcmodel.JavaMethod;
import org.jtool.srcmodel.JavaProject;
import java.util.Map;
import java.util.HashMap;
import java.util.Set;
import java.util.HashSet;
import java.util.Collection;
import java.util.Arrays;

/**
 * A repository that stores information on CFGs in the project.
 * 
 * @author Katsuhisa Maruyama
 */
public class CFGStore {
    
    protected JavaProject jproject;
    protected BytecodeClassStore bcStore;
    
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
    
    public void clear() {
        cfgs.clear();
        ucfgs.clear();
        ccfgs.clear();
        updateBCStore();
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
    
    protected void createBCStore() {
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
    
    public Collection<CCFG> getCCFGs() {
        return ccfgs.values();
    }
    
    public Collection<CFG> getCCFs() {
        return cfgs.values();
    }
    
    public CFG findCFG(String fqn) {
        return cfgs.get(fqn);
    }
    
    public CCFG findCCFG(String fqn) {
        return ccfgs.get(fqn);
    }
    
    public void removeCFGs(JavaClass jclass) {
        removeCFGs(new HashSet<>(Arrays.asList(jclass)));
    }
    
    public void removeCFGs(Set<JavaClass> classes) {
        for (JavaClass jclass : classes) {
            ccfgs.remove(jclass.getQualifiedName().fqn());
            for (JavaClass jc : jclass.getObsoleteClasses()) {
                jc.getMethods().forEach(jm -> {
                    cfgs.remove(jm.getQualifiedName().fqn());
                    ucfgs.remove(jm.getQualifiedName().fqn());
                });
                jc.getFields().forEach(jf -> {
                    cfgs.remove(jf.getQualifiedName().fqn());
                    ucfgs.remove(jf.getQualifiedName().fqn());
                });
            }
        }
    }
    
    public CCFG getCCFG(JavaClass jclass, boolean force) {
        assert jclass != null;
        
        if (force) {
            removeCFGs(jclass);
            updateBCStore();
        }
        return getCCFG(jclass);
    }
    
    public CCFG getCCFG(JavaClass jclass) {
        assert jclass != null;
        
        CCFG ccfg = ccfgs.get(jclass.getQualifiedName().fqn());
        if (ccfg != null) {
            return ccfg;
        }
        
        Set<CFG> existingCFGs = new HashSet<>();
        for (JavaMethod jm : jclass.getMethods()) {
            CFG cfg = cfgs.get(jm.getQualifiedName().fqn());
            if (cfg != null) {
                existingCFGs.add(cfg);
            }
        }
        for (JavaField  jf: jclass.getFields()) {
            CFG cfg = cfgs.get(jf.getQualifiedName().fqn());
            if (cfg != null) {
                existingCFGs.add(cfg);
            }
        }
        
        ccfg = CCFGBuilder.build(jclass, existingCFGs);
        ccfgs.put(ccfg.getQualifiedName().fqn(), ccfg);
        ccfg.getCFGs().forEach(cfg -> cfgs.put(cfg.getQualifiedName().fqn(), cfg));
        
        return ccfg;
    }
    
    public CFG getCFG(JavaMethod jmethod, boolean force) {
        assert jmethod != null;
        
        if (force) {
            removeCFGs(jmethod.getDeclaringClass());
            updateBCStore();
        }
        return getCFG(jmethod);
    }
    
    public CFG getCFG(JavaMethod jmethod) {
        assert jmethod != null;
        
        String fqn = jmethod.getQualifiedName().fqn();
        if (CFGsForVisit.contains(fqn)) {
            return findRegisteredCFG(fqn);
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
        CFGMethodBuilder.addUseVariablesForReturn(cfg);
        
        ucfgs.remove(fqn);
        cfgs.put(fqn, cfg);
        return cfg;
    }
    
    public CFG getCFG(JavaField jfield, boolean force) {
        assert jfield != null;
        
        if (force) {
            removeCFGs(jfield.getDeclaringClass());
            updateBCStore();
        }
        return getCFG(jfield);
    }
    
    public CFG getCFG(JavaField jfield) {
        assert jfield != null;
        
        String fqn = jfield.getQualifiedName().fqn();
        if (CFGsForVisit.contains(fqn)) {
            return findRegisteredCFG(fqn);
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
        Resolver.resolveReferences(jfield.getJavaProject(), cfg);
        Resolver.resolveLocalAlias(jfield.getJavaProject(), cfg);
        CFGsForVisit.remove(fqn);
        
        ucfgs.remove(fqn);
        cfgs.put(fqn, cfg);
        return cfg;
    }
    
    public CCFG generateUnregisteredCCFG(JavaClass jclass) {
        return CCFGBuilder.build(jclass);
    }
    
    private CFG findRegisteredCFG(String fqn) {
        CFG cfg = cfgs.get(fqn);
        if (cfg != null) {
            return cfg;
        }
        
        return ucfgs.get(fqn);
    }
    
    CFG getUnresolvedCFG(JavaMethod jmethod) {
        assert jmethod != null;
        
        String fqn = jmethod.getQualifiedName().fqn();
        
        CFG cfg = findRegisteredCFG(fqn);
        if (cfg != null) {
            return cfg;
        }
        
        cfg = CFGMethodBuilder.build(jmethod);
        CFGMethodBuilder.addUseVariablesForReturn(cfg);
        
        ucfgs.put(fqn, cfg);
        return cfg;
    }
    
    CFG getUnresolvedCFG(JavaField jfield) {
        assert jfield != null;
        
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
