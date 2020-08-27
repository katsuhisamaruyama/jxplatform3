/*
 *  Copyright 2020
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.srcplatform.bytecode;

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
    protected BytecodeClassStore bcStore;
    
    protected String name;
    protected int modifiers;
    protected boolean isInterface;
    protected String superClass;
    protected List<String> superInterfaces;
    
    protected Multimap<String, DefUseField> defFieldsCache = HashMultimap.create();
    protected Multimap<String, DefUseField> useFieldsCache = HashMultimap.create();
    protected Multimap<String, QualifiedName> calledMethodsCache = HashMultimap.create();
    
    protected Map<String, Collection<DefUseField>> defFieldsMap;
    protected Map<String, Collection<DefUseField>> useFieldsMap;
    protected Map<String, Collection<QualifiedName>> calledMethodsMap;
    
    protected Set<String> methods = new HashSet<>();
    protected Set<String> fields = new HashSet<>();
    
    protected List<String> superClassChain;
    protected List<String> ancestors;
    protected List<String> descendants;
    
    private List<BytecodeClass> superClassChainCache;
    private List<BytecodeClass> ancestorsCache;
    private List<BytecodeClass> descendantsCache = new ArrayList<>();
    
    protected final static String ElementSeparator = ";";
    
    BytecodeClass(String cacheName, BytecodeClassStore bcStore) {
        this.cacheName = cacheName;
        this.bcStore = bcStore;
    }
    
    protected void collectInfo() {
        defFieldsMap = defFieldsCache.asMap();
        useFieldsMap = useFieldsCache.asMap();
        calledMethodsMap = calledMethodsCache.asMap();
        
        methods.addAll(defFieldsMap.keySet());
        methods.addAll(useFieldsMap.keySet());
        methods.addAll(calledMethodsMap.keySet());
    }
    
    String getCacheName() {
        return cacheName;
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
        return defFieldsMap.get(signature);
    }
    
    public Collection<DefUseField> getUseFields(String signature) {
        return useFieldsMap.get(signature);
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
                attr.put(label, qname.getMemberSignature());
                cache.add(attr);
            }
        }
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
        descendantsCache.add(bclass);
    }
    
    void addDescendant(String className) {
        descendants.add(className);
    }
}
