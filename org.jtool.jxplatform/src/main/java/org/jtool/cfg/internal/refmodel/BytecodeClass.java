/*
 *  Copyright 2022
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.cfg.internal.refmodel;

import org.jtool.srcmodel.QualifiedName;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.Map.Entry;
import java.util.HashSet;
import java.util.Collection;
import java.util.HashMap;
import java.util.stream.Collectors;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;

/**
 * An object that represents a proxy class restored from its cache.
 * 
 * @author Katsuhisa Maruyama
 */

public abstract class BytecodeClass implements BytecodeClassCache {
    
    protected String cacheName;
    protected boolean bootModule;
    protected BytecodeClassStore bcStore;
    
    protected String name;
    protected int modifiers;
    protected boolean isInterface;
    protected boolean isInProject;
    protected String superClass;
    protected List<String> superInterfaces;
    
    protected Multimap<String, DefUseField> defFieldsCache = HashMultimap.create();
    protected Multimap<String, DefUseField> useFieldsCache = HashMultimap.create();
    protected Multimap<String, QualifiedName> calledMethodsCache = HashMultimap.create();
    protected Set<String> notSpecialMethods = new HashSet<>();
    
    protected Map<String, Collection<DefUseField>> defFieldsMap;
    protected Map<String, Collection<DefUseField>> useFieldsMap;
    protected Map<String, Collection<QualifiedName>> calledMethodsMap;
    
    protected Set<String> methods = new HashSet<>();
    protected Set<String> fields = new HashSet<>();
    
    protected List<String> superClassChain = null;
    protected List<String> ancestors = null;
    protected List<String> descendants = null;
    
    private List<BytecodeClass> superClassChainCache = null;
    private List<BytecodeClass> ancestorsCache = null;
    private List<BytecodeClass> descendantsCache = new ArrayList<>();
    
    protected final static String ElementSeparator = ";";
    
    BytecodeClass(String cacheName, boolean bootModule, BytecodeClassStore bcStore) {
        this.cacheName = cacheName;
        this.bootModule = bootModule;
        this.bcStore = bcStore;
    }
    
    protected void destroy() {
        bcStore = null;
        
        superClass = null;
        superInterfaces = null;
        defFieldsCache = null;
        useFieldsCache = null;
        calledMethodsCache = null;
        notSpecialMethods = null;
        
        defFieldsMap = null;
        useFieldsMap = null;
        calledMethodsMap = null;
        methods = null;
        fields = null;
        
        superClassChain = null;
        ancestors = null;
        descendants = null;
        superClassChainCache = null;
        ancestorsCache = null;
        descendantsCache = null;
    }
    
    boolean isCache() {
        return false;
    }
    
    protected void collectInfo() {
        defFieldsMap = defFieldsCache.asMap();
        useFieldsMap = useFieldsCache.asMap();
        calledMethodsMap = calledMethodsCache.asMap();
    }
    
    JClass createCacheClass(BytecodeClassStore bcStore) {
        JClass clazz = new JClassCache(this, bcStore);
        return clazz;
    }
    
    String getCacheName() {
        return cacheName;
    }
    
    boolean isBootModule() {
        return bootModule;
    }
    
    public String getName() {
        return name;
    }
    
    public int getModifiers() {
        return modifiers;
    }
    
    public boolean isInterface() {
        return isInterface;
    }
    
    public boolean isInProject() {
        return isInProject;
    }
    
    public String getSuperClass() {
        return superClass;
    }
    
    public List<String> getSuperInterfaces() {
        return superInterfaces;
    }
    
    public Set<String> getMethods() {
        return methods;
    }
    
    public Set<String> getFields() {
        return fields;
    }
    
    public Collection<DefUseField> getDefFields(String signature) {
        Collection<DefUseField> defs = defFieldsMap.get(signature);
        if (defs != null) {
            return defs;
        } else {
            return new HashSet<>();
        }
    }
    
    public Collection<DefUseField> getUseFields(String signature) {
        Collection<DefUseField> uses = useFieldsMap.get(signature);
        if (uses != null) {
            return uses;
        } else {
            return new HashSet<>();
        }
    }
    
    public Collection<QualifiedName> getCalledMethods(String signature) {
        return calledMethodsMap.get(signature);
    }
    
    @Override
    public Map<String, String> getClassCacheData() {
        Map<String, String> cache = new HashMap<>();
        cache.put(BytecodeCacheManager.NameAttr, name);
        cache.put(BytecodeCacheManager.ModifierAttr, String.valueOf(modifiers));
        cache.put(BytecodeCacheManager.isInterfaceAttr, String.valueOf(isInterface));
        cache.put(BytecodeCacheManager.isInProjectAttr, String.valueOf(isInProject));
        if (superClass != null) {
            cache.put(BytecodeCacheManager.SuperClassAttr, superClass);
        }
        if (superInterfaces.size() > 0) {
            cache.put(BytecodeCacheManager.SuperInterfaceAttr, BytecodeClass.convertString(superInterfaces));
        }
        return cache;
    }
    
    public static List<String> collectStringElems(String value) {
        List<String> elems = new ArrayList<>();
        if (value != null && value.indexOf(ElementSeparator) != -1) {
            String[] strs = value.split(ElementSeparator, 0);
            for (int index = 0; index < strs.length; index++) {
                elems.add(strs[index]);
            }
        }
        return elems;
    }
    
    public static String convertString(Collection<String> elems) {
        StringBuilder buf = new StringBuilder();
        for (String elem : elems) {
            buf.append(elem + ElementSeparator);
        }
        return buf.toString();
    }
    
    @Override
    public List<Map<String, String>> getMethodCacheData() {
        List<Map<String, String>> cache = new ArrayList<>();
        cache.addAll(createCacheDataForDefUseFields(defFieldsMap, BytecodeCacheManager.DefAttr));
        cache.addAll(createCacheDataForDefUseFields(useFieldsMap, BytecodeCacheManager.UseAttr));
        cache.addAll(createCacheDataForAccessedMethods(calledMethodsMap, BytecodeCacheManager.CallAttr));
        cache.addAll(createCacheDataForNotSpecialMethodsOrFields(notSpecialMethods));
        return cache;
    }
    
    private List<Map<String, String>> createCacheDataForDefUseFields(
            Map<String, Collection<DefUseField>> map, String label) {
        List<Map<String, String>> cache = new ArrayList<>();
        for (Entry<String, Collection<DefUseField>> entry : map.entrySet()) {
            String signature = entry.getKey();
            
            for (DefUseField field : entry.getValue()) {
                Map<String, String> attr = new HashMap<>();
                attr.put(BytecodeCacheManager.SignatureAttr, signature);
                attr.put(label, field.toStr());
                cache.add(attr);
            }
        }
        return cache;
    }
    
    private List<Map<String, String>> createCacheDataForAccessedMethods(
            Map<String, Collection<QualifiedName>> map, String label) {
        List<Map<String, String>> cache = new ArrayList<>();
        for (Entry<String, Collection<QualifiedName>> entry : map.entrySet()) {
            String signature = entry.getKey();
            
            for (QualifiedName qname : entry.getValue()) {
                Map<String, String> attr = new HashMap<>();
                attr.put(BytecodeCacheManager.SignatureAttr, signature);
                attr.put(label, qname.fqn());
                cache.add(attr);
            }
        }
        return cache;
    }
    
    private List<Map<String, String>> createCacheDataForNotSpecialMethodsOrFields(Set<String> set) {
        List<Map<String, String>> cache = new ArrayList<>();
        for (String signature : set) {
            Map<String, String> attr = new HashMap<>();
            attr.put(BytecodeCacheManager.SignatureAttr, signature);
            cache.add(attr);
        }
        return cache;
    }
    
    @Override
    public List<Map<String, String>> getFieldCacheData() {
        List<Map<String, String>> cache = new ArrayList<>();
        cache.addAll(createCacheDataForNotSpecialMethodsOrFields(fields));
        return cache;
    }
    
    void setClassHierarchy() {
        superClassChain = superClassChainCache.stream().map(bc -> bc.getName()).collect(Collectors.toList());
        ancestors = ancestorsCache.stream().map(bclass -> bclass.getName()).collect(Collectors.toList());
        descendants = descendantsCache.stream().map(bclass -> bclass.getName()).collect(Collectors.toList());
    }
    
    public List<String> getSuperClassChain() {
        return superClassChain;
    }
    
    public List<String> getAncestors() {
        return ancestors;
    }
    
    public List<String> getDescendants() {
        return descendants;
    }
    
    void findClassHierarchy() {
        collectSuperClassChain();
        collectAncestors();
    }
    
    List<BytecodeClass> collectSuperClassChain() {
        if (superClassChainCache == null) {
            superClassChainCache = new ArrayList<>();
            
            BytecodeClass bclass = bcStore.getBcClass(superClass);
            if (bclass != null) {
                superClassChainCache.add(bclass);
                superClassChainCache.addAll(bclass.collectSuperClassChain());
            }
        }
        return superClassChainCache;
    }
    
    List<BytecodeClass> collectAncestors() {
        if (ancestorsCache == null) {
            ancestorsCache = new ArrayList<>();
            
            BytecodeClass bclass = bcStore.getBcClass(superClass);
            if (bclass != null) {
                ancestorsCache.add(bclass);
                bclass.addDescendant(this);
                
                List<BytecodeClass> aclasses = bclass.collectAncestors();
                ancestorsCache.addAll(aclasses);
                aclasses.forEach(a -> a.addDescendant(this));
            }
            
            for (String parent : superInterfaces) {
                BytecodeClass pclass = bcStore.getBcClass(parent);
                if (pclass != null) {
                    ancestorsCache.add(pclass);
                    pclass.addDescendant(this);
                    
                    List<BytecodeClass> aclasses = pclass.collectAncestors();
                    ancestorsCache.addAll(aclasses);
                    aclasses.forEach(a -> a.addDescendant(this));
                }
            }
        }
        return ancestorsCache;
    }
    
    void addDescendant(BytecodeClass bclass) {
        if (!descendantsCache.contains(bclass)) {
            descendantsCache.add(bclass);
        }
    }
    
    void addDescendant(String className) {
        if (!descendants.contains(className)) {
            descendants.add(className);
        }
    }
}
