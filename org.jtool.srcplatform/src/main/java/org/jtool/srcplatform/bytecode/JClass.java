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
    protected boolean isInProject;
    protected String superClass = null;
    protected List<String> superInterfaces;
    
    protected List<JMethod> methods = new ArrayList<>();
    protected List<JField> fields = new ArrayList<>();
    
    protected List<JClass> superClassChain = null;
    protected List<JClass> ancestors = null;
    protected List<JClass> descendants = null;
    
    protected final static String ClassSeparator = ";";
    
    protected JClass(QualifiedName qname, BytecodeClassStore bcStore) {
        super(qname, bcStore);
    }
    
    public String getName() {
        return qname.getClassName();
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
    
    public String getSuperClass() {
        return superClass;
    }
    
    public List<String> getSuperInterfaces() {
        return superInterfaces;
    }
    
    public List<JClass> getSuperClassChain() {
        if (superClassChain == null) {
            superClassChain = new ArrayList<>();
            findSuperClassChain();
        }
        return superClassChain;
    }
    
    @Override
    public boolean isInProject() {
        return isInProject;
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
        cache.put(BytecodeCacheManager.isInProjectAttr, String.valueOf(isInProject));
        if (superClass != null) {
            cache.put(BytecodeCacheManager.SuperClassAttr, superClass);
        }
        if (superInterfaces.size() > 0) {
            cache.put(BytecodeCacheManager.SuperInterfaceAttr, BytecodeClass.convertString(superInterfaces));
        }
        return cache;
    }
    
    @Override
    public List<Map<String, String>> getMethodCacheData() {
        List<Map<String, String>> cache = new ArrayList<>();
        for (JMethod method : methods) {
            boolean exist = false;
            String signature = method.getSignature();
            
            for (DefUseField def : method.getDefFields()) {
                Map<String, String> attr = new HashMap<>();
                attr.put(BytecodeCacheManager.SignatureAttr, signature);
                attr.put(BytecodeCacheManager.DefAttr, def.toString());
                cache.add(attr);
                exist = true;
            }
            for (DefUseField use : method.getUseFields()) {
                Map<String, String> attr = new HashMap<>();
                attr.put(BytecodeCacheManager.SignatureAttr, signature);
                attr.put(BytecodeCacheManager.UseAttr, use.toString());
                cache.add(attr);
                exist = true;
            }
            for (JMethod acc : method.getAccessedMethods()) {
                Map<String, String> attr = new HashMap<>();
                attr.put(BytecodeCacheManager.SignatureAttr, signature);
                attr.put(BytecodeCacheManager.CallAttr, acc.getQualifiedName().fqn());
                cache.add(attr);
                exist = true;
            }
            
            if (!exist) {
                Map<String, String> attr = new HashMap<>();
                attr.put(BytecodeCacheManager.SignatureAttr, signature);
                cache.add(attr);
            }
        }
        return cache;
    }
    
    @Override
    public List<Map<String, String>> getFieldCacheData() {
        List<Map<String, String>> cache = new ArrayList<>();
        for (JField field : fields) {
            Map<String, String> attr = new HashMap<>();
            attr.put(BytecodeCacheManager.SignatureAttr, field.getSignature());
            cache.add(attr);
        }
        return cache;
    }
}
