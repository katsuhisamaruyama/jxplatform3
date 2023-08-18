/*
 *  Copyright 2022-2023
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.cfg.internal;

import org.jtool.cfg.CFG;
import org.jtool.cfg.CFGMethodCall;
import org.jtool.cfg.CFGMethodEntry;
import org.jtool.cfg.CFGNode;
import org.jtool.cfg.CFGParameter;
import org.jtool.cfg.CFGStatement;
import org.jtool.cfg.JMethodReference;
import org.jtool.cfg.JVariableReference;
import org.jtool.cfg.JFieldReference;
import org.jtool.cfg.StopConditionOnReachablePath;
import org.jtool.cfg.internal.refmodel.BytecodeClassStore;
import org.jtool.cfg.internal.refmodel.JClass;
import org.jtool.cfg.internal.refmodel.JMethod;
import org.jtool.srcmodel.JavaClass;
import org.jtool.srcmodel.JavaMethod;
import org.jtool.srcmodel.JavaField;
import org.jtool.srcmodel.JavaProject;
import org.eclipse.jdt.core.dom.StringLiteral;
import org.eclipse.jdt.core.dom.TypeLiteral;
import java.util.Set;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Searches the approximated types of a receiver for dynamic binding of method invocations.
 * 
 * @author Katsuhisa Maruyama
 */
class ReceiverTypeResolver {
    
    /**
     * A flag that indicates whether descendants of the uppermost type
     * should be included when type is not clearly specified.
     */
    static boolean includeDescendants = false;
    
    private BytecodeClassStore bcStore;
    
    ReceiverTypeResolver(JavaProject jproject) {
        this.bcStore = jproject.getCFGStore().getBCStore();
    }
    
    void findReceiverTypes(CFG cfg) throws InterruptedException {
        if (bcStore.analyzingBytecode()) {
            for (CFGMethodCall node : cfg.getMethodCallNodes()) {
                if (Thread.currentThread().isInterrupted()) {
                    throw new InterruptedException();
                }
                
                findReceiverTypes(node.getMethodCall(), cfg, new HashSet<>());
            }
        } else {
            for (CFGMethodCall node : cfg.getMethodCallNodes()) {
                if (Thread.currentThread().isInterrupted()) {
                    throw new InterruptedException();
                }
                
                findReceiverTypesWithoutBytecode(node.getMethodCall());
            }
        }
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
    
    private Set<JClass> getAllPosssibleTypes(String className, String signature) {
        Set<JClass> types = new HashSet<>();
        JClass clazz = getTargetClass(className, signature);
        if (clazz == null) {
            return types;
        }
        types.add(clazz);
        
        if (includeDescendants) {
            for (JClass jc : clazz.getDescendantClasses()) {
                if (jc.getMethod(signature) != null) {
                    types.add(jc);
                }
            }
        }
        return types;
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
    
    private void findReceiverTypes(JMethodReference jcall, CFG cfg, Set<CFGNode> track) {
        Set<JClass> types = new HashSet<>();
        
        if (jcall.hasReceiver() && findExplicitReceiverTypes(jcall, types)) {
            jcall.setApproximatedTypes(types);
            return;
        }
        
        if (findImplicitReceiverTypes(jcall, types)) {
            jcall.setApproximatedTypes(types);
            return;
        }
        
        if (jcall.hasReceiver()) {
            String uppermostType = jcall.getDeclaringClassName();
            findTypes(jcall.getReceiver(), jcall.getSignature(), cfg, uppermostType, types, track);
            jcall.setApproximatedTypes(types);
        }
    }
    
    private boolean findExplicitReceiverTypes(JMethodReference jcall, Set<JClass> types) {
        String type = jcall.getReceiver().explicitType();
        if (type != null) {
            JClass clazz = getTargetClass(type, jcall.getSignature());
            if (clazz != null) {
                types.add(clazz);
            }
            return true;
        }
        return false;
    }
    
    private boolean findImplicitReceiverTypes(JMethodReference jcall, Set<JClass> types) {
        if (jcall.isEnumConstant()) {
            JClass clazz = bcStore.getJClass(jcall.getDeclaringClassName());
            if (clazz != null) {
                types.add(clazz);
            }
            return true;
        }
        
        if (jcall.isConstructor()) {
            if (jcall.getSignature().endsWith("( )")) {
                JClass clazz = getTargetClassForDefaultConstructor(jcall.getDeclaringClassName());
                if (clazz != null) {
                    types.add(clazz);
                }
            } else {
                JMethod method = bcStore.getJMethod(jcall.getDeclaringClassName(), jcall.getSignature());
                if (method != null) {
                    types.add(method.getDeclaringClass());
                }
            }
            return true;
        }
        
        if(jcall.isStatic()) {
            JClass clazz = bcStore.getJClass(jcall.getDeclaringClassName());
            if (clazz != null) {
                types.add(clazz);
            }
            return true;
        }
        
        if (jcall.isSuper()) {
            JClass clazz = getTargetClass(jcall.getDeclaringClassName(), jcall.getSignature());
            if (clazz != null) {
                types.add(clazz);
            }
            return true;
        }
        
        if (jcall.isLocal()) {
            JClass clazz = getTargetClass(jcall.getDeclaringClassName(), jcall.getSignature());
            if (clazz != null) {
                types.add(clazz);
            }
            return true;
        }
        return false;
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
            int index = className.lastIndexOf(".");
            String simpaleName = index != -1 ? className.substring(index + 1) : className;
            if (jc.getMethod(simpaleName + "( )") != null) {
                return jc;
            }
        }
        return clazz;
    }
    
    private CFG getCFG(JVariableReference jv) {
        JavaClass jclass = bcStore.getJavaProject().getClass(jv.getDeclaringClassName());
        if (jclass == null || !jclass.isInProject()) {
            return null;
        }
        
        JavaField jfield = jclass.getField(jv.getSignature());
        if (jfield == null) {
            return null;
        }
        
        return bcStore.getJavaProject().getCFGStore().getUnresolvedCFG(jfield);
    }
    
    private CFG getCFG(JMethodReference jcall) {
        JavaClass jclass = bcStore.getJavaProject().getClass(jcall.getDeclaringClassName());
        if (jclass == null || !jclass.isInProject()) {
            return null;
        }
        
        JavaMethod jmethod = jclass.getMethod(jcall.getSignature());
        if (jmethod == null) {
            return null;
        }
        
        return bcStore.getJavaProject().getCFGStore().getUnresolvedCFG(jmethod);
    }
    
    private void findTypes(CFGStatement node, String signature, CFG cfg,
            String uppermostType, Set<JClass> types, Set<CFGNode> track) {
        if (node.getUseVariables().stream().anyMatch(v -> v.getASTNode() instanceof StringLiteral)) {
            types.addAll(getAllPosssibleTypes("java.lang.String", signature));
            return;
        }
        if (node.getUseVariables().stream().anyMatch(v -> v.getASTNode() instanceof TypeLiteral)) {
            types.addAll(getAllPosssibleTypes("java.lang.Class", signature));
            return;
        }
        
        List<JVariableReference> vars = node.findPrimaryUseVariables();
        if (vars.size() > 0) {
            for (JVariableReference jv : vars) {
                if (jv.isFieldAccess()) {
                    checkFieldReceiver(jv, signature, uppermostType, types, track);
                } else {
                    collectTypes(jv, node, signature, cfg, uppermostType, types, track);
                }
            }
        } else {
            if (node.hasDefVariable()) {
                uppermostType = findUppermostType(uppermostType, node.getDefFirst().getType());
                types.addAll(getAllPosssibleTypes(uppermostType, signature));
            } else if (node.hasUseVariable()) {
                uppermostType = findUppermostType(uppermostType, node.getUseLast().getType());
                types.addAll(getAllPosssibleTypes(uppermostType, signature));
            }
        }
    }
    
    private void collectTypes(JVariableReference jv, CFGNode node, String signature, CFG cfg,
            String uppermostType, Set<JClass> types, Set<CFGNode> track) {
        if (track.contains(node)) {
            return;
        }
        track.add(node);
        
        for (CFGStatement defnode : getDefineNodes(node, jv, cfg)) {
            if (isEnumConstant(defnode)) {
                String type = defnode.getUseFirst().getType();
                types.addAll(getAllPosssibleTypes(type, signature));
            } else if (defnode.isActualOut()) {
                checkReturn((CFGParameter)defnode, signature, uppermostType, types, track);
            } else if (defnode.isFormalIn()) {
                checkFormal((CFGParameter)defnode, signature, uppermostType, types, track);
            } else if (defnode.isEnhancedFor()) {
                types.addAll(getAllPosssibleTypes(uppermostType, signature));
            } else {
                findTypes(defnode, signature, cfg, uppermostType, types, track);
            }
        }
    }
    
    private Set<CFGStatement> getDefineNodes(CFGNode node, JVariableReference jv, CFG cfg) {
        Set<CFGStatement> nodes = new HashSet<>();
        cfg.backwardReachableNodes(node, true, true, new StopConditionOnReachablePath() {
            
            @Override
            public boolean isStop(CFGNode node) {
                if (node.hasDefVariable()) {
                    CFGStatement defnode = (CFGStatement)node;
                    if (defnode.defineVariable(jv)) {
                        nodes.add(defnode);
                        return true;
                    }
                }
                return false;
            }
        });
        return nodes;
    }
    
    private boolean isEnumConstant(CFGStatement defnode) {
        if (defnode.getUseVariables().size() == 1) {
            JVariableReference jv = defnode.getUseFirst();
            if (jv.isFieldAccess()) {
                return ((JFieldReference)jv).isEnumConstant();
            }
        }
        return false;
    }
    
    private void checkFieldReceiver(JVariableReference jv, String signature,
            String uppermostType, Set<JClass> types, Set<CFGNode> track) {
        CFG cfg = getCFG(jv);
        if (cfg == null) {
            uppermostType = findUppermostType(uppermostType, jv.getType());
            types.addAll(getAllPosssibleTypes(uppermostType, signature));
            return;
        }
        
        CFGStatement node = cfg.getNodes().stream()
                                          .filter(n -> n.isFieldDeclaration())
                                          .map(n -> (CFGStatement)n)
                                          .findFirst().orElse(null);
        if (node != null) {
            findTypes(node, signature, cfg, uppermostType, types, track);
        }
    }
    
    private void checkReturn(CFGParameter node, String signature,
            String uppermostType, Set<JClass> types, Set<CFGNode> track) {
        CFGMethodCall callNode = (CFGMethodCall)node.getParent();
        if (callNode.isConstructorCall()) {
            JClass clazz = getTargetClass(callNode.getReturnType(), signature);
            if (clazz != null) {
                types.add(clazz);
            }
            return;
        }
        
        uppermostType = findUppermostType(uppermostType, callNode.getReturnType());
        CFG cfg = getCFG(callNode.getMethodCall());
        if (cfg == null) {
            types.addAll(getAllPosssibleTypes(uppermostType, signature));
            return;
        }
        
        Set<CFGStatement> returnNodes = cfg.getNodes().stream()
                .filter(n -> n.isReturn()).map(n -> (CFGStatement)n).collect(Collectors.toSet());
        for (CFGStatement rnode : returnNodes) {
            findTypes(rnode, signature, cfg, uppermostType, types, track);
        }
    }
    
    private void checkFormal(CFGParameter fin, String signature,
            String uppermostType, Set<JClass> types, Set<CFGNode> track) {
        CFGMethodEntry entry = (CFGMethodEntry)fin.getParent();
        JavaMethod jmethod = entry.getJavaMethod();
        Set<JavaMethod> methods = jmethod.getCallingMethods();
        if (methods.size() > 0) {
            for (JavaMethod jm : jmethod.getCallingMethods()) {
                CFG cfg = bcStore.getJavaProject().getCFGStore().getUnresolvedCFG(jm);
                if (cfg != null) {
                    for (CFGMethodCall node : cfg.getMethodCallNodes()) {
                        if (node.getSignature().equals(jmethod.getSignature())) {
                            findReceiverTypes(node.getMethodCall(), cfg, track);
                            
                            if (node.getApproximatedTypeNames().contains(jmethod.getDeclaringClass().getClassName())) {
                                CFGParameter ain = node.getActualIn(fin.getIndex());
                                if (ain != null && !node.getMethodCall().isVarargs()) {
                                    findTypes(ain, signature, cfg, uppermostType, types, track);
                                }
                            }
                        }
                    }
                }
            }
        } else {
            uppermostType = findUppermostType(uppermostType, fin.getDefFirst().getType());
            types.addAll(getAllPosssibleTypes(uppermostType, signature));
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
        
        if (jcall.isConstructor() || jcall.isStatic() || jcall.isLocal()) {
            JClass clazz = bcStore.getJClass(jcall.getDeclaringClassName());
            if (clazz != null) {
                types.add(clazz);
            }
            jcall.setApproximatedTypes(types);
            return;
        }
        
        if (jcall.isSuper()) {
            JavaClass jc = bcStore.getJavaProject().getClass(jcall.getDeclaringClassName());
            if (jc != null) {
                JClass clazz = bcStore.getJClass(jc.getSuperClassName());
                if (clazz != null) {
                    types.add(clazz);
                }
            }
            jcall.setApproximatedTypes(types);
            return;
        }
        
        if (jcall.hasReceiver()) {
            jcall.getReceiver().findPrimaryUseVariables().stream()
                    .map(v -> bcStore.getJClass(v.getType()))
                    .filter(c -> c != null)
                    .forEach(c -> types.add(c));
            jcall.setApproximatedTypes(types);
        }
    }
}
