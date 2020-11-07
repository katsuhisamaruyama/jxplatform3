/*
 *  Copyright 2020
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.cfg.builder;

import org.jtool.cfg.CFG;
import org.jtool.cfg.CFGMethodCall;
import org.jtool.cfg.CFGMethodEntry;
import org.jtool.cfg.CFGNode;
import org.jtool.cfg.CFGParameter;
import org.jtool.cfg.CFGStatement;
import org.jtool.cfg.JMethodReference;
import org.jtool.cfg.JReference;
import org.jtool.cfg.StopConditionOnReachablePath;
import org.jtool.srcmodel.JavaClass;
import org.jtool.srcmodel.JavaMethod;
import org.jtool.srcmodel.JavaField;
import org.jtool.srcmodel.JavaProject;
import org.jtool.srcplatform.bytecode.BytecodeClassStore;
import org.jtool.srcplatform.bytecode.JClass;
import org.jtool.srcplatform.bytecode.JClassInternal;
import org.jtool.srcplatform.bytecode.JMethod;
import org.jtool.srcplatform.util.Logger;
import java.util.Set;
import java.util.HashSet;

/**
 * Searches the approximated types of a receiver for dynamic binding of method invocations.
 * 
 * @author Katsuhisa Maruyama
 */
class ReceiverTypeResolver {
    
    private BytecodeClassStore bcStore;
    
    ReceiverTypeResolver(JavaProject jproject) {
        this.bcStore = jproject.getCFGStore().getBCStore();
    }
    
    void findReceiverTypes(CFG cfg) {
        if (bcStore.analyzingBytecode()) {
            for (CFGMethodCall callnode : cfg.getMethodCallNodes()) {
                findReceiverTypes(callnode.getMethodCall(), cfg, new HashSet<>());
            }
        } else {
            for (CFGMethodCall callnode : cfg.getMethodCallNodes()) {
                findReceiverTypesWithoutBytecode(callnode.getMethodCall());
            }
        }
    }
    
    private void findReceiverTypes(JMethodReference jcall, CFG cfg, Set<CFGNode> track) {
        Set<String> types = new HashSet<>();
        String upperType = jcall.getDeclaringClassName();
        
        if (jcall.hasReceiver()) {
            String stringLiteral = jcall.getReceiver().stringLiteral();
            if (stringLiteral != null) {
                JClass clazz = getTargetClass(stringLiteral, jcall.getSignature());
                if (clazz != null) {
                    types.add(clazz.getClassName());
                    jcall.setApproximatedTypes(types);
                }
                return;
            }
            
            String typeLiteral = jcall.getReceiver().typeLiteral();
            if (typeLiteral != null) {
                JClass clazz = getTargetClass(typeLiteral, jcall.getSignature());
                if (clazz != null) {
                    types.add(clazz.getClassName());
                    jcall.setApproximatedTypes(types);
                }
                return;
            }
            
            String type = jcall.getReceiver().castType();
            if (type != null) {
                upperType = type;
            }
        }
        
        // This condition should be checked prior to the conditions "jcall.isConstructor()" and "jcall.isStatic()"
        // because the call to the enum is a kind of a constructor call or a static call.
        if (jcall.isEnum()) { 
            JClass clazz = bcStore.getJClass(jcall.getDeclaringClassName());
            if (clazz != null) {
                types.add(clazz.getClassName());
                jcall.setApproximatedTypes(types);
            } else {
                Logger.getInstance().printError(
                        "**Not found receiver enum type for method invocation " + jcall.getQualifiedName());
            }
            return;
        }
        
        if (jcall.isConstructor()) {
            if (jcall.getSignature().endsWith("( )")) {
                JClass clazz = getTargetClassForDefaultConstructor(jcall.getDeclaringClassName());
                if (clazz != null) {
                    types.add(clazz.getClassName());
                    jcall.setApproximatedTypes(types);
                } else {
                    types.add(jcall.getDeclaringClassName());
                    jcall.setApproximatedTypes(types);
                }
                
            } else {
                JMethod method = bcStore.getJMethod(jcall.getDeclaringClassName(), jcall.getSignature());
                if (method != null) {
                    types.add(method.getClassName());
                    jcall.setApproximatedTypes(types);
                } else {
                    Logger.getInstance().printError(
                            "**Not found receiver type for constructor invocation " + jcall.getQualifiedName());
                }
            }
            return;
        }
        
        if(jcall.isStatic()) {
            JClass clazz = bcStore.getJClass(jcall.getDeclaringClassName());
            if (clazz != null) {
                types.add(clazz.getClassName());
                jcall.setApproximatedTypes(types);
            }
            return;
        }
        
        if (jcall.isSuper()) {
            JClass clazz = getTargetClass(jcall.getDeclaringClassName(), jcall.getSignature());
            if (clazz != null) {
                types.add(clazz.getClassName());
                jcall.setApproximatedTypes(types);
            }
            return;
        }
        
        if (jcall.isLocal()) {
            JClass clazz = getTargetClass(jcall.getDeclaringClassName(), jcall.getSignature());
            if (clazz != null) {
                types.add(clazz.getClassName());
                jcall.setApproximatedTypes(types);
            }
            return;
        }
        
        if (jcall.hasReceiver()) {
            JReference jv = jcall.getReceiver().getUseVariables().get(0);
            if (jv.isFieldAccess()) {
                Set<String> fieldTypes = getFieldTypes(jv);
                if (fieldTypes.size() > 0) {
                    types.addAll(fieldTypes);
                    jcall.setApproximatedTypes(types);
                    
                } else {
                    upperType = findUpperType(upperType, jv.getType());
                    types.addAll(getAllPosssibleDescendants(upperType, jcall.getSignature()));
                    jcall.setApproximatedTypes(types);
                }
            } else {
                collectReceiverTypes(jcall.getReceiver(), jcall.getSignature(), upperType, jv, cfg, track, types);
                jcall.setApproximatedTypes(types);
            }
        }
    }
    
    private Set<String> getFieldTypes(JReference jv) {
        Set<String> types = new HashSet<>();
        JClass clazz = bcStore.getJClass(jv.getDeclaringClassName());
        
        if (clazz == null || !clazz.isInProject()) {
            return types;
        }
        
        JClassInternal internalClass = (JClassInternal)clazz;
        JavaClass jclass = internalClass.getJavaClass();
        JavaField jfield = jclass.getField(jv.getSignature());
        if (jfield == null) {
            return types;
        }
        
        CFG cfg = bcStore.getJavaProject().getCFGStore().getCFGWithoutResolvingMethodCalls(jfield);
        for (CFGNode cfgnode : cfg.getNodes()) {
            if (cfgnode.isFieldDeclaration()) {
                CFGStatement fieldDecl = (CFGStatement)cfgnode;
                for (JReference use : fieldDecl.getUseVariables()) {
                    types.add(use.getType());
                }
            }
        }
        
        return types;
    }
    
    private String findUpperType(String upperType, String targetType) {
        JClass clazz = bcStore.getJClass(upperType);
        if (clazz != null) {
            if (clazz.getClassName().equals(targetType)) {
                return targetType;
            }
            for (JClass jc: clazz.getAncestors()) {
                if (jc.getClassName().equals(targetType)) {
                    return upperType;
                }
            }
        }
        return targetType;
    }
    
    private String simpleClassName(String className) {
        int index = className.lastIndexOf(".");
        return index != -1 ? className.substring(index + 1) : className;
    }
    
    private JClass getTargetClassForDefaultConstructor(String className) {
        JClass clazz = bcStore.getJClass(className);
        if (clazz == null) {
            return null;
        }
        
        if (clazz.getMethod(className + "( )") != null) {
            return clazz;
        }
        
        for (JClass jc: clazz.getSuperClassChain()) {
            if (jc.getMethod(simpleClassName(jc.getClassName()) + "( )") != null) {
                return jc;
            }
        }
        return null;
    }
    
    private JClass getTargetClass(String className, String signature) {
        JClass clazz = bcStore.getJClass(className);
        if (clazz == null) {
            return null;
        }
        
        if (clazz.getMethod(signature) != null) {
            return clazz;
        }
        
        for (JClass jc: clazz.getSuperClassChain()) {
            if (jc.getMethod(signature) != null) {
                return jc;
            }
        }
        return null;
    }
    
    private Set<String> getAllPosssibleDescendants(String className, String signature) {
        Set<String> types = new HashSet<>();
        JClass clazz = getTargetClass(className, signature);
        if (clazz == null) {
            return types;
        }
        types.add(clazz.getClassName());
        
        for (JClass jc : clazz.getDescendants()) {
            if (jc.getMethod(signature) != null) {
                types.add(jc.getClassName());
            }
        }
        return types;
    }
    
    
    
    private void collectReceiverTypes(CFGNode node, String signature, String upperType, JReference jv,
            CFG cfg, Set<CFGNode> track, Set<String> types) {
        if (track.contains(node)) {
            return;
        }
        track.add(node);
        
        for (CFGStatement defnode : getDefineNode(node, jv, cfg)) {
            if (defnode.isActualOut()) {
                checkReturn(defnode, signature, upperType, types);
            } else if (defnode.isFormalIn()) {
                checkCallingMethods((CFGParameter)defnode, signature, upperType, track, types);
            } else {
                checkDataFlow(defnode, signature, upperType, jv, cfg, track, types);
            }
        }
    }
    
    private Set<CFGStatement> getDefineNode(CFGNode node, JReference jv, CFG cfg) {
        Set<CFGStatement> nodes = new HashSet<>();
        cfg.backwardReachableNodes(node, true, new StopConditionOnReachablePath() {
            
            @Override
            public boolean isStop(CFGNode node) {
                if (node.hasDefVariable()) {
                    CFGStatement cfgnode = (CFGStatement)node;
                    if (cfgnode.defineVariable(jv)) {
                        nodes.add(cfgnode);
                        return true;
                    }
                }
                return false;
            }
        });
        return nodes;
    }
    
    private void checkReturn(CFGStatement defnode, String signature, String upperType, Set<String> types) {
        CFGParameter actualOut = (CFGParameter)defnode;
        CFGMethodCall callnode = (CFGMethodCall)actualOut.getParent();
        if (callnode.isConstructorCall()) {
            JClass clazz = getTargetClass(callnode.getReturnType(), signature);
            if (clazz != null) {
                types.add(clazz.getClassName());
            }
        } else {
            upperType = findUpperType(upperType, callnode.getReturnType());
            types.addAll(getAllPosssibleDescendants(upperType, signature));
        }
    }
    
    private void checkCallingMethods(CFGParameter formalIn, String signature, String upperType,
            Set<CFGNode> track, Set<String> types) {
        CFGMethodEntry entry = (CFGMethodEntry)formalIn.getParent();
        JavaMethod jmethod = entry.getJavaMethod();
        
        for (JavaMethod jm : jmethod.getCallingMethods()) {
            CFG cfg = bcStore.getJavaProject().getCFGStore().getCFGWithoutResolvingMethodCalls(jm);
            if (cfg != null) {
                for (CFGNode node : cfg.getMethodCallNodes()) {
                    CFGMethodCall callnode = (CFGMethodCall)node;
                    if (callnode.getSignature().equals(jmethod.getSignature()) && callnode.getActualIns().size() > 0) {
                        
                        findReceiverTypes(callnode.getMethodCall(), cfg, track);
                        if (callnode.getApproximatedTypes().contains(jmethod.getDeclaringClass().getClassName())) {
                            CFGParameter actualIn = callnode.getActualIn(formalIn.getIndex());
                            if (callnode.getMethodCall().isVarargs() && actualIn == null) {
                                continue;
                            }
                            if (actualIn.getUseVariables().size() > 0) {
                                JReference jv = actualIn.getUseVariables().get(actualIn.getUseVariables().size() - 1);
                                
                                for (CFGStatement defnode : getDefineNode(actualIn, jv, cfg)) {
                                    collectReceiverTypes(defnode, signature, upperType, jv, cfg, track, types);
                                }
                            }
                        }
                    }
                }
            }
        }
    }
    
    private void checkDataFlow(CFGStatement defnode, String signature, String upperType, JReference jv,
            CFG cfg, Set<CFGNode> track, Set<String> types) {
        if (defnode.getUseVariables().size() > 0) {
            JReference use = defnode.getUseVariables().get(defnode.getUseVariables().size() - 1);
            if (use.isFieldAccess()) {
                upperType = findUpperType(upperType, jv.getType());
                types.addAll(getAllPosssibleDescendants(upperType, signature));
            } else {
                collectReceiverTypes(defnode, signature, upperType, use, cfg, track, types);
            }
        }
    }
    
    private void findReceiverTypesWithoutBytecode(JMethodReference jcall) {
        Set<String> types = new HashSet<>();
        if (jcall.hasReceiver()) {
            String type = jcall.getReceiver().explicitType();
            if (type != null) {
                types.add(type);
                jcall.setApproximatedTypes(types);
                return;
            }
        }
        
        if (jcall.isConstructor() || jcall.isStatic() || jcall.isLocal()) {
            types.add(jcall.getDeclaringClassName());
        } else if (jcall.isSuper()) {
            JavaClass jc = bcStore.getJavaProject().getClass(jcall.getDeclaringClassName());
            if (jc != null) {
                types.add(jc.getSuperClassName());
            }
        } else if (jcall.hasReceiver()) {
            JReference jv = jcall.getReceiver().getUseVariables().get(0);
            types.add(jv.getType());
        }
        jcall.setApproximatedTypes(types);
    }
}
