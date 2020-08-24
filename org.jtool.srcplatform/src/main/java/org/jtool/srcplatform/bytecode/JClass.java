/*
 *  Copyright 2020
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.srcplatform.bytecode;

import org.jtool.srcmodel.QualifiedName;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

/**
 * Concise information on a class.
 * 
 * @author Katsuhisa Maruyama
 */
abstract public class JClass extends JCommon implements BytecodeClassCache {
    
    protected int modifiers;
    protected boolean isInterface;
    protected String superClass = null;
    protected List<String> superInterfaces = new ArrayList<>();
    
    protected List<JMethod> methods = new ArrayList<>();
    protected List<JField> fields = new ArrayList<>();
    
    protected List<JClass> superClassChain = null;
    protected List<JClass> ancestors = null;
    protected List<JClass> descendants = null;
    
    protected final static String ClassSeparator = ";";
    
    protected JClass(QualifiedName qname, BytecodeClassStore bcStore) {
        super(qname, bcStore);
    }
    
    public List<JMethod> getMethods() {
        return methods;
    }
    
    public JMethod getMethod(String signature) {
        return methods.stream()
                .filter(method -> method.getSignature().equals(signature)).findAny().orElse(null);
    }
    
    public List<JField> getFields() {
        return fields;
    }
    
    public JField getField(String signature) {
        return fields.stream()
                .filter(field -> field.getSignature().equals(signature)).findAny().orElse(null);
    }
    
    public List<JClass> getSuperClassChain() {
        if (superClassChain == null) {
            superClassChain = new ArrayList<>();
            findSuperClassChain();
        }
        return superClassChain;
    }
    
    abstract protected void findSuperClassChain();
    
    public List<JClass> getAncestors() {
        if (ancestors == null) {
            ancestors = new ArrayList<>();
            findAncestors();
        }
        return ancestors;
    }
    
    abstract protected void findAncestors();
    
    public List<JClass> getDescendants() {
        if (descendants == null) {
            descendants = new ArrayList<>();
            findDescendants();
        }
        return descendants;
    }
    
    abstract protected void findDescendants();
    
    public boolean equals(JClass clazz) {
        return clazz != null && (this == clazz || getQualifiedName().equals(clazz.getQualifiedName()));
    }
    
    @Override
    public Map<String, String> getClassCacheData() {
        Map<String, String> cache = new HashMap<>();
        cache.put(BytecodeCacheManager.NameAttr, qname.getClassName());
        cache.put(BytecodeCacheManager.ModifierAttr, String.valueOf(modifiers));
        cache.put(BytecodeCacheManager.isInterfaceAttr, String.valueOf(isInterface));
        if (superClass != null) {
            cache.put(BytecodeCacheManager.SuperClassAttr, superClass);
        }
        cache.put(BytecodeCacheManager.SuperClassAttr, BytecodeClass.convertString(superInterfaces));
        return cache;
    }
    
    @Override
    public List<Map<String, String>> getMethodCacheData() {
        List<Map<String, String>> cache = new ArrayList<>();
        for (JMethod method : methods) {
            Map<String, String> attr = new HashMap<>();
            String signature = method.getSignature();
            
            for (DefUseField def : method.getDefFields()) {
                attr.put(BytecodeCacheManager.SignatureAttr, signature);
                attr.put(BytecodeCacheManager.DefAttr, def.toString());
                cache.add(attr);
            }
            for (DefUseField use : method.getUseFields()) {
                attr.put(BytecodeCacheManager.SignatureAttr, signature);
                attr.put(BytecodeCacheManager.UseAttr, use.toString());
                cache.add(attr);
            }
            for (JMethod acc : method.getAccessedMethods()) {
                attr.put(BytecodeCacheManager.SignatureAttr, signature);
                attr.put(BytecodeCacheManager.CallAttr, acc.getQualifiedName().fqn());
                cache.add(attr);
            }
        }
        return cache;
    }
}
