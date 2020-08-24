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
import org.jtool.srcmodel.JavaField;
import org.jtool.srcmodel.JavaMethod;
import org.jtool.srcmodel.JavaProject;
import org.jtool.srcplatform.bytecode.BytecodeClassStore;
import org.jtool.srcplatform.bytecode.JClass;
import org.jtool.srcplatform.bytecode.JMethod;

import java.util.Set;
import java.util.HashSet;

/**
 * Searches the approximated types of a receiver for dynamic binding of method invocations.
 * 
 * @author Katsuhisa Maruyama
 */
class ReceiverTypeResolver {
    
    private JavaProject jproject;
    private BytecodeClassStore bcStore;
    
    ReceiverTypeResolver(JavaProject jproject) {
        this.jproject = jproject;
        this.bcStore = jproject.getCFGStore().getBCStore();
    }
    
    void findReceiverTypes(CFG cfg) {
        for (CFGMethodCall callnode : cfg.getMethodCallNodes()) {
            findReceiverTypes(callnode.getMethodCall(), cfg);
        }
    }
    
    private void findReceiverTypes(JMethodReference jcall, CFG cfg) {
        Set<String> types = new HashSet<>();
        if (jcall.isConstructor()) {
            JClass clazz = checkDefaultConstructor(jcall);
            if (clazz != null) {
                types.add(clazz.getQualifiedName().getClassName());
            }
            
        } else if(jcall.isStatic()) {
            JClass clazz = bcStore.getJClass(jcall.getDeclaringClassName());
            if (clazz != null) {
                types.add(clazz.getQualifiedName().getClassName());
            }
            
        } else {
            JReference jv = jcall.getReceiver().getUseVariables().get(0);
            if (jv.getName().equals("this")) {
                JClass clazz = getTargetClass(jcall.getEnclosingClassName(), jcall.getSignature());
                if (clazz != null) {
                    types.add(clazz.getQualifiedName().getClassName());
                }
                
            } else if (jv.isFieldAccess()) {
                types.addAll(getAllPosssibleTypes(jv.getType(), jcall.getSignature()));
                
            } else {
                collectReceiverTypes(jcall.getReceiver(), jv, jcall.getSignature(), cfg, new HashSet<>(), types);
            }
        }
        
        jcall.getReceiver().setApproximatedTypes(types);
        
        for (String type : types) {
            JavaClass jclass = jproject.getClass(type);
            if (jclass != null && jclass.isInProject()) {
                JavaMethod jmethod = jclass.getMethod(jcall.getSignature());
                jproject.getCFGStore().getCFG(jmethod, false);
            }
        }
    }
    
    private JClass checkDefaultConstructor(JMethodReference jcall) {
        JClass clazz = bcStore.getJClass(jcall.getDeclaringClassName());
        if (clazz != null) {
            String signature = clazz.getQualifiedName().getClassName() + "( )";
            
            if (clazz.getMethod(signature) != null) {
                return clazz;
            }
            for (JClass jc: clazz.getSuperClassChain()) {
                if (jc.getMethod(signature) != null) {
                    return jc;
                }
            }
        }
        return null;
    }
    
    private JClass getTargetClass(String className, String signature) {
        JMethod method = bcStore.getJMethod(className, signature);
        if (method != null) {
            return method.getDeclaringClass();
        }
        return null;
    }
    
    private Set<String> getAllPosssibleTypes(String className, String signature) {
        Set<String> types = new HashSet<>();
        JClass clazz = getTargetClass(className, signature);
        if (clazz == null) {
            return types;
        }
        
        types.add(clazz.getQualifiedName().getClassName());
        for (JClass jc : clazz.getDescendants()) {
            if (jc.getMethod(signature) != null) {
                types.add(clazz.getQualifiedName().getClassName());
            }
        }
        return types;
    }
    
    private void collectReceiverTypes(CFGNode node, JReference jv, String signature,
            CFG cfg, Set<CFGNode> track, Set<String> types) {
        if (track.contains(node)) {
            return;
        }
        track.add(node);
        
        for (CFGStatement defnode : getDefineNode(node, jv, cfg)) {
            if (defnode.isActualOut()) {
                CFGParameter actualOut = (CFGParameter)defnode;
                CFGMethodCall callnode = (CFGMethodCall)actualOut.getParent();
                if (callnode.isConstructorCall() || callnode.getMethodCall().isStatic()) {
                    types.add(callnode.getReturnType());
                    
                } else {
                    types.addAll(getAllPosssibleTypes(callnode.getReturnType(), signature));
                }
                
            } else if (defnode.isFormalIn()) {
                CFGParameter formalIn = (CFGParameter)defnode;
                CFGMethodEntry entry = (CFGMethodEntry)formalIn.getParent();
                for (CFGMethodCall callnode2 : getMethodCalls(entry.getJavaMethod(), cfg)) {
                    CFGParameter actualIn = callnode2.getActualIn(formalIn.getIndex());
                    if (actualIn.getUseVariables().size() > 0) {
                        JReference jv2 = actualIn.getUseVariables().get(actualIn.getUseVariables().size() - 1);
                        CFG cfg2 = jproject.getCFGStore().findCFG(callnode2.getQualifiedName().fqn());
                        
                        for (CFGStatement defnode2 : getDefineNode(actualIn, jv2, cfg2)) {
                            collectReceiverTypes(defnode2, jv2, signature, cfg2, track, types);
                        }
                    }
                }
                
            } else {
                if (defnode.getUseVariables().size() > 0) {
                    JReference use = defnode.getUseVariables().get(defnode.getUseVariables().size() - 1);
                    if (use.isFieldAccess()) {
                        types.addAll(getAllPosssibleTypes(jv.getType(), signature));
                    } else {
                        collectReceiverTypes(defnode, use, signature, cfg, track, types);
                    }
                }
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
    
    Set<CFGMethodCall> getMethodCalls(String className, String signature) {
        Set<CFGMethodCall> callnodes = new HashSet<>();
        
        JavaClass jclass = jproject.getClass(className);
        if (jclass != null) {
            
            JavaMethod jmethod = jclass.getMethod(signature);
            if (jmethod != null) {
                for (JavaMethod jm : jmethod.getCallingMethods()) {
                    if (jm.isInProject()) {
                        CFG cfg = jproject.getCFGStore().createCFGWithoutResolvingMethodCalls(jm);
                        callnodes.addAll(getMethodCalls(jmethod, cfg));
                    }
                }
                
                for (JavaField jf : jmethod.getAccessingFields()) {
                    if (jf.isInProject()) {
                        CFG cfg = jproject.getCFGStore().createCFGWithoutResolvingMethodCalls(jf);
                        callnodes.addAll(getMethodCalls(jmethod, cfg));
                    }
                }
            }
        }
        return callnodes;
    }
    
    private Set<CFGMethodCall> getMethodCalls(JavaMethod jmethod, CFG cfg) {
        Set<CFGMethodCall> callnodes = new HashSet<>();
        if (cfg == null) {
            return callnodes;
        }
        
        for (CFGNode node : cfg.getMethodCallNodes()) {
            CFGMethodCall callnode = (CFGMethodCall)node;
            if (callnode.getSignature().equals(jmethod.getSignature())) {
                callnodes.add(callnode);
            }
        }
        return callnodes;
    }
}
