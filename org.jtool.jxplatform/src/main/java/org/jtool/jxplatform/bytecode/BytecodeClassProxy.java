/*
 *  Copyright 2022
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.jxplatform.bytecode;

import org.jtool.srcmodel.QualifiedName;
import java.util.Map;

/**
 * An object that represents a proxy class restored from its cache.
 * 
 * @author Katsuhisa Maruyama
 */

public class BytecodeClassProxy extends BytecodeClass {
    
    protected final static String ElementSeparator = ";";
    
    BytecodeClassProxy(String cacheName, BytecodeClassStore bcStore) {
        super(cacheName, bcStore);
    }
    
    @Override
    boolean isCache() {
        return true;
    }
    
    void addClass(Map<String, String> attr) {
        name = attr.get(BytecodeCacheManager.NameAttr);
        modifiers = Integer.parseInt(attr.get(BytecodeCacheManager.ModifierAttr));
        isInterface = Boolean.parseBoolean(attr.get(BytecodeCacheManager.isInterfaceAttr));
        isInProject = Boolean.parseBoolean(attr.get(BytecodeCacheManager.isInProjectAttr));
        superClass = attr.get(BytecodeCacheManager.SuperClassAttr);
        superInterfaces = BytecodeClass.collectStringElems(attr.get(BytecodeCacheManager.SuperInterfaceAttr));
    }
    
    void addMethod(Map<String, String> attr) {
        String signature = attr.get(BytecodeCacheManager.SignatureAttr);
        
        String def = attr.get(BytecodeCacheManager.DefAttr);
        if (def != null) {
            defFieldsCache.put(signature, DefUseField.create(def));
        }
        String use = attr.get(BytecodeCacheManager.UseAttr);
        if (use != null) {
            useFieldsCache.put(signature, DefUseField.create(use));
        }
        String call = attr.get(BytecodeCacheManager.CallAttr);
        if (call != null) {
            calledMethodsCache.put(signature, new QualifiedName(call));
        }
        
        if (def == null && use == null && call == null) {
            notSpecialMethods.add(signature);
        }
    }
    
    void addField(Map<String, String> attr) {
        String signature = attr.get(BytecodeCacheManager.SignatureAttr);
        fields.add(signature);
    }
    
    @Override
    protected void collectInfo() {
        super.collectInfo();
        
        bcStore.registerBytecode(this);
    }
}
