/*
 *  Copyright 2020
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.srcplatform.bytecode;

import org.jtool.srcmodel.QualifiedName;
import javassist.CannotCompileException;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.CtField;
import javassist.CtConstructor;
import javassist.CtBehavior;
import javassist.Modifier;
import javassist.NotFoundException;
import javassist.expr.ConstructorCall;
import javassist.expr.ExprEditor;
import javassist.expr.FieldAccess;
import javassist.expr.MethodCall;
import javassist.expr.NewExpr;
import java.util.ArrayList;

/**
 * An object that represents a class obtained from its byte-code.
 * 
 * @author Katsuhisa Maruyama
 */

public class BytecodeClassJavassist extends BytecodeClass {
    
    private CtClass ctClass;
    
    BytecodeClassJavassist(CtClass ctClass, String cacheName, BytecodeClassStore bcStore) {
        super(cacheName, bcStore);
        
        this.ctClass = ctClass;
        
        name = bcStore.getCanonicalClassName(ctClass);
        modifiers = ctClass.getModifiers();
        isInterface = ctClass.isInterface();
        isInProject = false;
        
        try {
            if (!ctClass.isInterface() && !ctClass.isEnum()) {
                CtClass sclass = ctClass.getSuperclass();
                if (sclass != null) {
                    superClass = bcStore.getCanonicalClassName(sclass);
                }
            }
        } catch (NotFoundException e) { /* empty */ }
        
        try {
            superInterfaces = new ArrayList<>();
            for (CtClass c : ctClass.getInterfaces()) {
                superInterfaces.add(bcStore.getCanonicalClassName(c));
            }
        } catch (NotFoundException e) { /* empty */ }
    }
    
    @Override
    protected void collectInfo() {
        for (CtMethod cm : ctClass.getDeclaredMethods()) {
            parseMethod(cm, bcStore.getMethodSignature(cm));
        }
        
        for (CtConstructor cc : ctClass.getDeclaredConstructors()) {
            parseMethod(cc, bcStore.getConstructorSignature(cc));
        }
        
        for (CtField cf : ctClass.getDeclaredFields()) {
            fields.add(cf.getName());
        }
        
        super.collectInfo();
    }
    
    private boolean exist;
    
    private void parseMethod(CtBehavior ctBehavior, String thisSignature) {
        exist = false;
        try {
            ctBehavior.instrument(new ExprEditor() {
                
                @Override
                public void edit(FieldAccess cf) throws CannotCompileException {
                    try {
                        if (cf.isWriter()) {
                            DefUseField def = createDefUseField(cf);
                            if (!defFieldsCache.containsEntry(thisSignature, def)) {
                                defFieldsCache.put(thisSignature, def);
                                exist = true;
                            }
                        }
                        if (cf.isReader()) {
                            DefUseField use = createDefUseField(cf);
                            if (!useFieldsCache.containsEntry(thisSignature, use)) {
                                useFieldsCache.put(thisSignature, use);
                                exist = true;
                            }
                        }
                    } catch (NotFoundException e) { /* empty */ }
                }
                
                private DefUseField createDefUseField(FieldAccess cf) throws NotFoundException {
                    return new DefUseField(cf.getClassName(),
                            cf.getFieldName(), cf.getClassName() + "." + cf.getFieldName(),
                            cf.getField().getType().getName(),
                            cf.getField().getType().isPrimitive(), cf.getField().getModifiers());
                }
                
                @Override
                public void edit(MethodCall cm) throws CannotCompileException {
                    try {
                        QualifiedName qname = methodQualifiedName(cm.getMethod());
                        if (!calledMethodsCache.containsEntry(thisSignature, qname)) {
                            calledMethodsCache.put(thisSignature, qname);
                            exist = true;
                        }
                    } catch (NotFoundException e) { /* empty */ }
                }
                
                private QualifiedName methodQualifiedName(CtMethod cm) throws NotFoundException {
                    String className = bcStore.getCanonicalClassName(cm.getDeclaringClass());
                    String signature = bcStore.getMethodSignature(cm);
                    return new QualifiedName(className, signature);
                }
                
                @Override
                public void edit(ConstructorCall cm) throws CannotCompileException {
                    try {
                        QualifiedName qname = constructorQualifiedName(cm.getConstructor());
                        if (!calledMethodsCache.containsEntry(thisSignature, qname)) {
                            calledMethodsCache.put(thisSignature, qname);
                            exist = true;
                        }
                    } catch (NotFoundException e) { /* empty */ }
                }
                
                @Override
                public void edit(NewExpr cm) throws CannotCompileException {
                    try {
                        QualifiedName qname = constructorQualifiedName(cm.getConstructor());
                        if (!calledMethodsCache.containsEntry(thisSignature, qname)) {
                            calledMethodsCache.put(thisSignature, qname);
                            exist = true;
                        }
                    } catch (NotFoundException e) { /* empty */ }
                }
                
                private QualifiedName constructorQualifiedName(CtConstructor cc) throws NotFoundException {
                    String className = bcStore.getCanonicalClassName(cc.getDeclaringClass());
                    String signature = bcStore.getConstructorSignature(cc);
                    return new QualifiedName(className, signature);
                }
            });
        } catch (CannotCompileException e) { /* empty */ }
        
        if (!exist) {
            notSpecialMethods.add(thisSignature);
        }
    }
    
    public static boolean isPublic(int mod) {
        return Modifier.isPublic(mod);
    }
    
    public static boolean isProtected(int mod) {
        return Modifier.isProtected(mod);
    }
    
    public static boolean isPrivate(int mod) {
        return Modifier.isPrivate(mod);
    }
    
    public static boolean isDefault(int mod) {
        return !isPublic(mod) && !isProtected(mod) && !isPrivate(mod);
    }
    
    public static boolean isStatic(int mod) {
        return Modifier.isStatic(mod);
    }
}
