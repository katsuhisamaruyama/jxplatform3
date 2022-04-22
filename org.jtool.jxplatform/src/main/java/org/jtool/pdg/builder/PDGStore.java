/*
 *  Copyright 2022
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.pdg.builder;

import org.jtool.pdg.PDG;
import org.jtool.pdg.ClDG;
import org.jtool.pdg.SDG;
import org.jtool.cfg.CCFG;
import org.jtool.cfg.CFG;
import org.jtool.cfg.CFGMethodEntry;
import org.jtool.cfg.CFGFieldEntry;
import org.jtool.cfg.builder.CFGStore;
import org.jtool.srcmodel.JavaClass;
import org.jtool.srcmodel.JavaField;
import org.jtool.srcmodel.JavaMethod;
import java.util.Map;
import java.util.HashMap;
import java.util.Set;
import java.util.HashSet;
import java.util.stream.Collectors;

/**
 * An object that stores information on PDGs in the project.
 * 
 * @author Katsuhisa Maruyama
 */
public class PDGStore {
    
    private CFGStore cfgStore;
    
    private Map<String, BarePDG> bpdgMap = new HashMap<>();
    
    private Map<String, ClDG> cldgMap = new HashMap<>();
    private Map<String, PDG> pdgMap = new HashMap<>();
    
    public PDGStore(CFGStore cfgStore) {
        this.cfgStore = cfgStore;
    }
    
    public void destroy() {
        bpdgMap.clear();
        pdgMap.clear();
        cldgMap.clear();
    }
    
    public BarePDG findBarePDG(String fqn) {
        return bpdgMap.get(fqn);
    }
    
    public ClDG findClDG(String fqn) {
        return cldgMap.get(fqn);
    }
    
    public PDG findPDG(String fqn) {
        return pdgMap.get(fqn);
    }
    
    public PDG getPDG(JavaField jfield, boolean force, boolean whole) {
        return getPDG(jfield.getQualifiedName().fqn(), jfield.getDeclaringClass(), force, whole);
    }
    
    public PDG getPDG(JavaMethod jmethod, boolean force, boolean whole) {
        return getPDG(jmethod.getQualifiedName().fqn(), jmethod.getDeclaringClass(), force, whole);
    }
    
    public PDG getPDG(CFG cfg, boolean force, boolean whole) {
        if (cfg.isMethod()) {
            JavaMethod jmethod = ((CFGMethodEntry)cfg.getEntryNode()).getJavaMethod();
            return getPDG(jmethod, force, whole);
        } else {
            JavaField jfield = ((CFGFieldEntry)cfg.getEntryNode()).getJavaField();
            return getPDG(jfield, force, whole);
        }
    }
    
    private PDG getPDG(String fqn, JavaClass jclass, boolean force, boolean whole) {
        if (!force) {
            PDG pdg = pdgMap.get(fqn);
            if (pdg != null) {
                return pdg;
            }
        }
        
        ClDG cldg = getClDG(jclass.getQualifiedName().fqn(), jclass, true, whole);
        if (cldg == null) {
            return null;
        }
        return cldg.findPDG(fqn);
    }
    
    public ClDG getClDG(JavaClass jclass, boolean force, boolean whole) {
        return getClDG(jclass.getQualifiedName().fqn(), jclass, force, whole);
    }
    
    public ClDG getClDG(CCFG ccfg, boolean force, boolean whole) {
        JavaClass jclass = ccfg.getEntryNode().getJavaClass();
        return getClDG(jclass, force, whole);
    }
    
    private ClDG getClDG(String fqn, JavaClass jclass, boolean force, boolean whole) {
        assert jclass != null;
        
        if (!force) {
            ClDG cldg = cldgMap.get(fqn);
            if (cldg != null) {
                return cldg;
            }
        } else {
            jclass.getMethods().forEach(jmethod -> bpdgMap.remove(jmethod.getQualifiedName().fqn()));
            jclass.getFields().forEach(jfield -> bpdgMap.remove(jfield.getQualifiedName().fqn()));
        }
        
        CCFG ccfg = cfgStore.getCCFG(jclass, force);
        if (ccfg == null) {
            return null;
        }
        
        if (whole) {
            SDG sdg = getSDG(getEfferentClasses(jclass), true, true);
            ClDG cldg = sdg.findClDG(fqn);
            if (cldg != null) {
                cldgMap.put(cldg.getQualifiedName().fqn(), cldg);
                cldg.getPDGs().forEach(pdg -> pdgMap.put(pdg.getQualifiedName().fqn(), pdg));
            }
            return cldg;
        } else {
            ClDG cldg = PDGBuilder.buildClDG(ccfg, bpdgMap);
            PDGBuilder.connectMethodCalls(cldg);
            PDGBuilder.connectFieldAccesses(cldg);
            return cldg;
        }
    }
    
    public SDG getSDG(boolean force) {
        Set<JavaClass> classes = new HashSet<>(cfgStore.getJavaProject().getClasses());
        return getSDG(classes, force, true);
    }
    
    public SDG getSDG(JavaClass jclass, boolean force, boolean whole) {
        if (whole) {
            return getSDG(getEfferentClasses(jclass), force, true);
        } else {
            Set<JavaClass> classes = new HashSet<>();
            classes.add(jclass);
            return getSDG(classes, force, false);
        }
    }
    
    public SDG getSDG(JavaClass jclass, boolean force) {
        return getSDG(jclass, force, true);
    }
    
    public SDG getSDG(Set<JavaClass> classes, boolean force, boolean whole) {
        SDG sdg = new SDG();
        for (JavaClass jclass : classes) {
            if (!force) {
                ClDG cldg = cldgMap.get(jclass.getQualifiedName().fqn());
                if (cldg != null) {
                    sdg.add(cldg);
                    continue;
                } else {
                    jclass.getMethods().forEach(jmethod -> bpdgMap.remove(jmethod.getQualifiedName().fqn()));
                    jclass.getFields().forEach(jfield -> bpdgMap.remove(jfield.getQualifiedName().fqn()));
                }
            }
            
            CCFG ccfg = cfgStore.getCCFG(jclass, force);
            if (ccfg != null) {
                ClDG cldg = PDGBuilder.buildClDG(ccfg, bpdgMap);
                if (cldg != null) {
                    sdg.add(cldg);
                    if (whole) {
                        cldgMap.put(cldg.getQualifiedName().fqn(), cldg);
                        cldg.getPDGs().forEach(pdg -> pdgMap.put(pdg.getQualifiedName().fqn(), pdg));
                    }
                }
            }
        }
        
        PDGBuilder.connectMethodCalls(sdg);
        PDGBuilder.connectFieldAccesses(sdg);
        return sdg;
    }
    
    Set<JavaClass> getEfferentClasses(JavaClass jclass) {
        Set<JavaClass> allClasses = new HashSet<>();
        collectEfferentClasses(jclass, allClasses);
        return allClasses;
    }
    
    Set<JavaClass> getEfferentClasses(Set<JavaClass> classes) {
        Set<JavaClass> allClasses = new HashSet<>();
        for (JavaClass jclass : classes) {
            collectEfferentClasses(jclass, allClasses);
        }
        return allClasses;
    }
    
    private void collectEfferentClasses(JavaClass jclass, Set<JavaClass> classes) {
        if (classes.contains(jclass)) {
            return;
        }
        classes.add(jclass);
        classes.addAll(collectDescendantClassesInProject(jclass));
        
        for (JavaClass jc : jclass.getEfferentClassesInProject()) {
            collectEfferentClasses(jc, classes);
        }
    }
    
    private Set<JavaClass> collectDescendantClassesInProject(JavaClass jclass) {
        return jclass.getDescendants().stream()
                     .filter(descendant -> descendant.isInProject())
                     .collect(Collectors.toSet());
    }
}
