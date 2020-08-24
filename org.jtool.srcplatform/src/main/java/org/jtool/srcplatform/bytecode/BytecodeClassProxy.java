/*
 *  Copyright 2020
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.srcplatform.bytecode;

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
    
    void addClass(Map<String, String> attr) {
        name = attr.get(BytecodeCacheManager.NameAttr);
        modifiers = Integer.parseInt(attr.get(BytecodeCacheManager.ModifierAttr));
        isInterface = Boolean.parseBoolean(attr.get(BytecodeCacheManager.isInterfaceAttr));
        superClass = attr.get(BytecodeCacheManager.SuperClassAttr);
        superInterfaces = BytecodeClass.collectStringElems(attr.get(BytecodeCacheManager.SuperInterfaceAttr));
    }
    
    void addMethod(Map<String, String> attr) {
        String signature = attr.get(BytecodeCacheManager.SignatureAttr);
        defFieldsCache.put(signature, DefUseField.create(attr.get(BytecodeCacheManager.DefAttr)));
        useFieldsCache.put(signature, DefUseField.create(attr.get(BytecodeCacheManager.UseAttr)));
        calledMethodsCache.put(signature, new QualifiedName(name, attr.get(BytecodeCacheManager.CallAttr)));
    }
    
    @Override
    protected void collectInfo() {
        super.collectInfo();
    }
}
