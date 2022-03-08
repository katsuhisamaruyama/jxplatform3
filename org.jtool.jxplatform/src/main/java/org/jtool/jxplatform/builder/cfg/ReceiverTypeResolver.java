/*
 *  Copyright 2022
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.jxplatform.builder.cfg;

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
import org.jtool.jxplatform.project.Logger;
import org.jtool.jxplatform.refmodel.BytecodeClassStore;
import org.jtool.jxplatform.refmodel.JClass;
import org.jtool.jxplatform.refmodel.JMethod;
import java.util.Set;
import java.util.HashSet;
import java.util.stream.Collectors;

/**
 * Searches the approximated types of a receiver for dynamic binding of method invocations.
 * 
 * @author Katsuhisa Maruyama
 */
class ReceiverTypeResolver {
    
    /**
     * A flag that indicates whether descendants of the uppermost type
     * are included when type is not clearly specified.
     */
    public static boolean includesDescendants = false;
    
    private BytecodeClassStore bcStore;
    private Logger logger;
    
    ReceiverTypeResolver(JavaProject jproject) {
        this.bcStore = jproject.getCFGStore().getBCStore();
        this.logger = jproject.getModelBuilderImpl().getLogger();
    }
    
    void findReceiverTypes(CFG cfg) {
        if (bcStore.analyzingBytecode()) {
            for (CFGMethodCall callNode : cfg.getMethodCallNodes()) {
                findReceiverTypes(callNode.getMethodCall(), cfg, new HashSet<>());
            }
        } else {
            for (CFGMethodCall callNode : cfg.getMethodCallNodes()) {
                findReceiverTypesWithoutBytecode(callNode.getMethodCall());
            }
        }
    }
    
    private void findReceiverTypes(JMethodReference jcall, CFG cfg, Set<CFGNode> track) {
        Set<JClass> types = new HashSet<>();
        String uppermostType = jcall.getDeclaringClassName();
        
        if (jcall.hasReceiver()) {
            String stringLiteral = jcall.getReceiver().stringLiteral();
            if (stringLiteral != null) {
                JClass clazz = getTargetClass(stringLiteral, jcall.getSignature());
                if (clazz != null) {
                    types.add(clazz);
                    jcall.setApproximatedTypes(types);
                }
                return;
            }
            
            String typeLiteral = jcall.getReceiver().typeLiteral();
            if (typeLiteral != null) {
                JClass clazz = getTargetClass(typeLiteral, jcall.getSignature());
                if (clazz != null) {
                    types.add(clazz);
                    jcall.setApproximatedTypes(types);
                }
                return;
            }
            
            String type = jcall.getReceiver().castType();
            if (type != null) {
                uppermostType = type;
            }
        }
        
        // This condition should be checked prior to the conditions "jcall.isConstructor()" and "jcall.isStatic()"
        // because the call to the enum is a kind of a constructor call or a static call.
        if (jcall.isEnum()) { 
            JClass clazz = bcStore.getJClass(jcall.getDeclaringClassName());
            if (clazz != null) {
                types.add(clazz);
                jcall.setApproximatedTypes(types);
            } else {
                logger.printError(
                        "**Not found receiver enum type for method invocation " + jcall.getQualifiedName());
            }
            return;
        }
        
        if (jcall.isConstructor()) {
            if (jcall.getSignature().endsWith("( )")) {
                JClass clazz = getTargetClassForDefaultConstructor(jcall.getDeclaringClassName());
                if (clazz != null) {
                    types.add(clazz);
                    jcall.setApproximatedTypes(types);
                } else {
                    logger.printError(
                            "**Not found receiver type for constructor invocation " + jcall.getQualifiedName());
                }
                
            } else {
                JMethod method = bcStore.getJMethod(jcall.getDeclaringClassName(), jcall.getSignature());
                if (method != null) {
                    types.add(method.getDeclaringClass());
                    jcall.setApproximatedTypes(types);
                } else {
                    logger.printError(
                            "**Not found receiver type for constructor invocation " + jcall.getQualifiedName());
                }
            }
            return;
        }
        
        if(jcall.isStatic()) {
            JClass clazz = bcStore.getJClass(jcall.getDeclaringClassName());
            if (clazz != null) {
                types.add(clazz);
                jcall.setApproximatedTypes(types);
            }
            return;
        }
        
        if (jcall.isSuper()) {
            JClass clazz = getTargetClass(jcall.getDeclaringClassName(), jcall.getSignature());
            if (clazz != null) {
                types.add(clazz);
                jcall.setApproximatedTypes(types);
            }
            return;
        }
        
        if (jcall.isLocal()) {
            JClass clazz = getTargetClass(jcall.getDeclaringClassName(), jcall.getSignature());
            if (clazz != null) {
                types.add(clazz);
                jcall.setApproximatedTypes(types);
            }
            return;
        }
        
        if (jcall.hasReceiver()) {
            JReference jv = jcall.getReceiver().getUseVariables().get(0);
            if (jv.isFieldAccess()) {
                Set<JClass> fieldTypes = getFieldTypes(jv);
                if (fieldTypes.size() > 0) {
                    types.addAll(fieldTypes);
                    jcall.setApproximatedTypes(types);
                    
                } else {
                    uppermostType = findUppermostType(uppermostType, jv.getType());
                    types.addAll(getAllPosssibleTypes(uppermostType, jcall.getSignature()));
                    jcall.setApproximatedTypes(types);
                }
            } else {
                collectReceiverTypes(jcall.getReceiver(), jcall.getSignature(), uppermostType, jv, cfg, track, types);
                jcall.setApproximatedTypes(types);
            }
        }
    }
    
    private Set<JClass> getFieldTypes(JReference jv) {
        Set<JClass> types = new HashSet<>();
        JavaClass jclass = bcStore.getJavaProject().getClass(jv.getDeclaringClassName());
        if (jclass == null || !jclass.isInProject()) {
            return types;
        }
        
        JavaField jfield = jclass.getField(jv.getSignature());
        if (jfield == null) {
            return types;
        }
        
        CFG cfg = bcStore.getJavaProject().getCFGStore().getCFGWithoutResolvingMethodCalls(jfield);
        for (CFGNode cfgnode : cfg.getNodes()) {
            if (cfgnode.isFieldDeclaration()) {
                CFGStatement fieldDecl = (CFGStatement)cfgnode;
                for (JClass type : findLowermostTypes(fieldDecl)) {
                    types.add(type);
                    for (JClass ancestor : type.getAncestorClasses()) {
                        if (ancestor.getFields().size() > 0) {
                            types.add(ancestor);
                        }
                    }
                }
            }
        }
        
        return types;
    }
    
    private Set<JClass> findLowermostTypes(CFGStatement fieldDecl) {
        Set<JClass> classes = fieldDecl.getUseVariables().stream()
                .map(use -> bcStore.getJClass(use.getType())).filter(c -> c != null).collect(Collectors.toSet());
        Set<JClass> types = new HashSet<>();
        for (JClass clazz : classes) {
            Set<JClass> tmpClasses = new HashSet<>(classes);
            tmpClasses.remove(clazz);
            boolean exists = clazz.getDescendantClasses().stream().anyMatch(c -> tmpClasses.contains(c));
            if (!exists) {
                types.add(clazz);
            }
        }
        return types;
    }
    
    private String findUppermostType(String upperType, String targetType) {
        JClass clazz = bcStore.getJClass(upperType);
        if (clazz != null) {
            if (clazz.getClassName().equals(targetType)) {
                return targetType;
            }
            for (JClass jc: clazz.getAncestorClasses()) {
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
        return clazz;
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
    
    private Set<JClass> getAllPosssibleTypes(String className, String signature) {
        Set<JClass> types = new HashSet<>();
        JClass clazz = getTargetClass(className, signature);
        if (clazz == null) {
            return types;
        }
        types.add(clazz);
        
        if (includesDescendants) {
            for (JClass jc : clazz.getDescendantClasses()) {
                if (jc.getMethod(signature) != null) {
                    types.add(jc);
                }
            }
        }
        return types;
    }
    
    private void collectReceiverTypes(CFGNode node, String signature, String upperType, JReference jv,
            CFG cfg, Set<CFGNode> track, Set<JClass> types) {
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
    
    private void checkReturn(CFGStatement defnode, String signature, String upperType, Set<JClass> types) {
        CFGParameter actualOut = (CFGParameter)defnode;
        CFGMethodCall callNode = (CFGMethodCall)actualOut.getParent();
        if (callNode.isConstructorCall()) {
            JClass clazz = getTargetClass(callNode.getReturnType(), signature);
            if (clazz != null) {
                types.add(clazz);
            }
        } else {
            upperType = findUppermostType(upperType, callNode.getReturnType());
            types.addAll(getAllPosssibleTypes(upperType, signature));
        }
    }
    
    private void checkCallingMethods(CFGParameter formalIn, String signature, String upperType,
            Set<CFGNode> track, Set<JClass> types) {
        CFGMethodEntry entry = (CFGMethodEntry)formalIn.getParent();
        JavaMethod jmethod = entry.getJavaMethod();
        
        for (JavaMethod jm : jmethod.getCallingMethods()) {
            CFG cfg = bcStore.getJavaProject().getCFGStore().getCFGWithoutResolvingMethodCalls(jm);
            if (cfg != null) {
                for (CFGNode node : cfg.getMethodCallNodes()) {
                    CFGMethodCall callNode = (CFGMethodCall)node;
                    if (callNode.getSignature().equals(jmethod.getSignature()) && callNode.getActualIns().size() > 0) {
                        
                        findReceiverTypes(callNode.getMethodCall(), cfg, track);
                        if (callNode.getApproximatedTypeNames().contains(jmethod.getDeclaringClass().getClassName())) {
                            CFGParameter actualIn = callNode.getActualIn(formalIn.getIndex());
                            if (callNode.getMethodCall().isVarargs() && actualIn == null) {
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
            CFG cfg, Set<CFGNode> track, Set<JClass> types) {
        if (defnode.getUseVariables().size() > 0) {
            JReference use = defnode.getUseVariables().get(defnode.getUseVariables().size() - 1);
            if (use.isFieldAccess()) {
                upperType = findUppermostType(upperType, jv.getType());
                types.addAll(getAllPosssibleTypes(upperType, signature));
            } else {
                collectReceiverTypes(defnode, signature, upperType, use, cfg, track, types);
            }
        }
    }
    
    private void findReceiverTypesWithoutBytecode(JMethodReference jcall) {
        Set<JClass> types = new HashSet<>();
        if (jcall.hasReceiver()) {
            String type = jcall.getReceiver().explicitType();
            if (type != null) {
                JClass clazz = bcStore.getJClass(type);
                if (clazz != null) {
                    types.add(clazz);
                }
                jcall.setApproximatedTypes(types);
                return;
            }
        }
        
        JClass clazz = null;
        if (jcall.isConstructor() || jcall.isStatic() || jcall.isLocal()) {
            clazz = bcStore.getJClass(jcall.getDeclaringClassName());
        } else if (jcall.isSuper()) {
            JavaClass jc = bcStore.getJavaProject().getClass(jcall.getDeclaringClassName());
            if (jc != null) {
                clazz = bcStore.getJClass(jc.getSuperClassName());
            }
        } else if (jcall.hasReceiver()) {
            JReference jv = jcall.getReceiver().getUseVariables().get(0);
            clazz = bcStore.getJClass(jv.getType());
        }
        if (clazz != null) {
            types.add(clazz);
        }
        jcall.setApproximatedTypes(types);
    }
}
