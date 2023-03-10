/*
 *  Copyright 2022
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.cfg.internal.refmodel;

import org.jtool.srcmodel.QualifiedName;
import java.util.List;
import java.util.ArrayList;
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

/**
 * An object that represents a class obtained from its byte-code.
 * 
 * @author Katsuhisa Maruyama
 */

public class BytecodeClassJavassist extends BytecodeClass {
    
    private CtClass ctClass;
    
    BytecodeClassJavassist(CtClass ctClass, String cacheName, boolean bootModule, BytecodeClassStore bcStore) {
        super(cacheName, bootModule, bcStore);
        
        this.ctClass = ctClass;
        
        name = bcStore.getCanonicalClassName(ctClass);
        modifiers = ctClass.getModifiers();
        isInterface = ctClass.isInterface();
        isInProject = false;
        
        for (CtField cf : ctClass.getDeclaredFields()) {
            fields.add(cf.getName());
        }
        
        for (CtMethod cm : ctClass.getDeclaredMethods()) {
            methods.add(bcStore.getMethodSignature(cm));
        }
        
        for (CtConstructor cc : ctClass.getDeclaredConstructors()) {
            methods.add(bcStore.getConstructorSignature(cc));
        }
        
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
    protected void destroy() {
        super.destroy();
        ctClass = null;
    }
    
    @Override
    protected void collectInfo() {
        for (CtMethod cm : ctClass.getDeclaredMethods()) {
            parseMethod(cm, bcStore.getMethodSignature(cm));
        }
        
        for (CtConstructor cc : ctClass.getDeclaredConstructors()) {
            parseMethod(cc, bcStore.getConstructorSignature(cc));
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
                            cf.getField().getType().isPrimitive(),
                            cf.getField().getModifiers(), false, true, false, false);
                }
                
                @Override
                public void edit(MethodCall cm) throws CannotCompileException {
                    try {
                        for (QualifiedName qname : createMethodQualifiedNames(cm)) {
                            if (!calledMethodsCache.containsEntry(thisSignature, qname)) {
                                calledMethodsCache.put(thisSignature, qname);
                                exist = true;
                            }
                        }
                    } catch (NotFoundException e) { /* empty */ }
                }
                
                private List<QualifiedName> createMethodQualifiedNames(MethodCall cm) throws NotFoundException {
                    List<QualifiedName> qnames = new ArrayList<>();
                    String className = bcStore.getCanonicalClassName(cm.getMethod().getDeclaringClass());
                    String signature = bcStore.getMethodSignature(cm.getMethod());
                    QualifiedName qname = new QualifiedName(className, signature);
                    
                    if (className != null && signature != null) {
                        JClass clazz = bcStore.getJClass(className);
                        if (clazz != null && clazz.getMethod(signature) != null) {
                            qnames.add(qname);
                            
                            //Comment out since it takes much time to check invocations to all descendants
                            //for (JClass c : clazz.getDescendantClasses()) {
                            //    if (c.getMethod(signature) != null) {
                            //        qnames.add(new QualifiedName(c.getClassName(), signature));
                            //    }
                            //}
                        }
                        return qnames;
                    }
                    throw new NotFoundException(qname.fqn());
                }
                
                @Override
                public void edit(ConstructorCall cm) throws CannotCompileException {
                    try {
                        QualifiedName qname = createConstructorQualifiedName(cm.getConstructor());
                        if (!calledMethodsCache.containsEntry(thisSignature, qname)) {
                            calledMethodsCache.put(thisSignature, qname);
                            exist = true;
                        }
                    } catch (NotFoundException e) { /* empty */ }
                }
                
                @Override
                public void edit(NewExpr cm) throws CannotCompileException {
                    try {
                        QualifiedName qname = createConstructorQualifiedName(cm.getConstructor());
                        if (!calledMethodsCache.containsEntry(thisSignature, qname)) {
                            calledMethodsCache.put(thisSignature, qname);
                            exist = true;
                        }
                    } catch (NotFoundException e) { /* empty */ }
                }
                
                private QualifiedName createConstructorQualifiedName(CtConstructor cc) throws NotFoundException {
                    String className = bcStore.getCanonicalClassName(cc.getDeclaringClass());
                    String signature = bcStore.getConstructorSignature(cc);
                    QualifiedName qname = new QualifiedName(className, signature);
                    
                    if (className != null && signature != null) {
                        JClass clazz = bcStore.getJClass(className);
                        if (clazz != null && clazz.getMethod(signature) != null) {
                            return qname;
                        }
                    }
                    throw new NotFoundException(qname.fqn());
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
