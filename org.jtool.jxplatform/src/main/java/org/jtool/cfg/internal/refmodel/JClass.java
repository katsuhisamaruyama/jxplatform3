/*
 *  Copyright 2022
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.cfg.internal.refmodel;

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
    
    protected void destroy() {
        super.destroy();
        superClass = null;
        superInterfaces = null;
        methods.forEach(m -> m.destroy());
        methods = null;
        fields.forEach(f -> f.destroy());
        fields = null;
        superClassChain = null;
        ancestors = null;
        descendants = null;
    }
    
    @Override
    public String getName() {
        return qname.getClassName();
    }
    
    public String getSimpleName() {
        String className = qname.getClassName();
        int index = className.lastIndexOf(".");
        String name = (index == -1) ? className : className.substring(index + 1);
        String sepName = name.replaceAll("\\$\\d+", "-");
        int index2 = sepName.lastIndexOf("-");
        return (index2 == -1) ? sepName : sepName.substring(index2 + 1);
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
    
    public boolean isInterface() {
        return isInterface;
    }
    
    @Override
    public boolean isInProject() {
        return isInProject;
    }
    
    abstract protected void findSuperClassChain();
    
    public List<JClass> getAncestorClasses() {
        if (ancestors == null) {
            ancestors = new ArrayList<>();
            findAncestorClasses();
        }
        return ancestors;
    }
    
    abstract protected void findAncestorClasses();
    
    public List<JClass> getDescendantClasses() {
        if (descendants == null) {
            descendants = new ArrayList<>();
            findDescendantClasses();
        }
        return descendants;
    }
    
    abstract protected void findDescendantClasses();
    
    @Override
    public boolean equals(Object obj) {
        return (obj instanceof JClass) ? equals((JClass)obj) : false;
    }
    
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
