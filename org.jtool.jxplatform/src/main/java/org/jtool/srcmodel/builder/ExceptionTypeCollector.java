/*
 *  Copyright 2022
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.srcmodel.builder;

import org.jtool.srcmodel.JavaMethod;
import org.jtool.srcmodel.JavaProject;
import org.jtool.srcmodel.JavaElementUtil;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ClassInstanceCreation;
import org.eclipse.jdt.core.dom.ConstructorInvocation;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.core.dom.SuperConstructorInvocation;
import org.eclipse.jdt.core.dom.SuperMethodInvocation;
import org.eclipse.jdt.core.dom.ThrowStatement;
import org.eclipse.jdt.core.dom.TryStatement;
import org.eclipse.jdt.core.dom.Type;
import org.eclipse.jdt.core.dom.UnionType;
import org.eclipse.jdt.core.dom.CatchClause;
import org.eclipse.jdt.core.dom.ITypeBinding;
import org.eclipse.jdt.core.dom.IMethodBinding;
import java.util.Set;
import java.util.HashSet;
import java.util.Stack;
import java.util.stream.Stream;
import java.util.stream.Collectors;

/**
 * Parses Java source code and stores information on possible exceptions when calling methods and constructors.
 * 
 * @see org.eclipse.jdt.core.dom.Expression
 * 
 * MethodInvocation
 * SuperMethodInvocation
 * ConstructorInvocation
 * SuperConstructorInvocation
 * ClassInstanceCreation
 * ThrowStatement
 * 
 * @author Katsuhisa Maruyama
 */
public class ExceptionTypeCollector {
    
    private JavaProject jproject;
    
    private Set<ITypeBinding> exceptionTypes = new HashSet<>();
    
    public ExceptionTypeCollector() {
    }
    
    public Set<ITypeBinding> getExceptions(JavaMethod jmethod) {
        jproject = jmethod.getJavaProject();
        
        collectExceptions(new CalledMethod(jmethod.getASTNode(), new HashSet<>()), new HashSet<>());
        return exceptionTypes;
    }
    
    private void collectExceptions(CalledMethod cmethod, Set<CalledMethod> cmethods) {
        if (cmethods.contains(cmethod)) {
            return;
        }
        cmethods.add(cmethod);
        
        MethodCallVisitor visitor = new MethodCallVisitor(cmethod);
        cmethod.getASTNode().accept(visitor);
        for (CalledMethod cm : visitor.calledMethods) {
            collectExceptions(cm, cmethods);
        }
    }
    
    private class MethodCallVisitor extends ASTVisitor {
        
        private static final String RUNTIME_EXCEPTION_NAME = "java.lang.RuntimeException";
        
        private Set<String> caughtTypes;
        
        private Stack<TryStatement> tryNodes = new Stack<>();
        
        private Set<CalledMethod> calledMethods = new HashSet<>();
        
        MethodCallVisitor(CalledMethod cmethod) {
            this.caughtTypes = cmethod.getCaughtTypes();
        }
        
        @Override
        public boolean visit(MethodInvocation node) {
            addMethodCall(node, node.resolveMethodBinding());
            return false;
        }
        
        @Override
        public boolean visit(SuperMethodInvocation node) {
            addMethodCall(node, node.resolveMethodBinding());
            return false;
        }
        
        @Override
        public boolean visit(ConstructorInvocation node) {
            addMethodCall(node, node.resolveConstructorBinding());
            return false;
        }
        
        @Override
        public boolean visit(SuperConstructorInvocation node) {
            addMethodCall(node, node.resolveConstructorBinding());
            return false;
        }
        
        @Override
        public boolean visit(ClassInstanceCreation node) {
            addMethodCall(node, node.resolveConstructorBinding());
            return false;
        }
        
        private void addMethodCall(ASTNode node, IMethodBinding mbinding) {
            if (mbinding == null || mbinding.isDefaultConstructor() || mbinding.isAnnotationMember()) {
                return;
            }
            
            Set<String> allTypes = getExceptionTypes(caughtTypes, tryNodes);
            JavaMethod jmethod = JavaElementUtil.findDeclaringMethod(mbinding, jproject);
            if (jmethod != null && jmethod.getASTNode() != null) {
                
                
                calledMethods.add(new CalledMethod(jmethod.getASTNode(), allTypes));
            }
            
            for (ITypeBinding tbinding : mbinding.getExceptionTypes()) {
                if (isUncheckedException(tbinding) && !allTypes.contains(tbinding.getQualifiedName())) {
                    
                    System.err.println(tbinding.getQualifiedName());
                    
                    exceptionTypes.add(tbinding);
                }
            }
        }
        
        @Override
        public boolean visit(TryStatement node) {
            tryNodes.push(node);
            return true;
        }
        
        @Override
        public void endVisit(TryStatement node) {
            tryNodes.pop();
        }
        
        @Override
        public boolean visit(ThrowStatement node) {
            ITypeBinding tbinding = node.getExpression().resolveTypeBinding();
            if (tbinding != null) {
                if (isUncheckedException(tbinding)) {
                    Set<String> allTypes = getExceptionTypes(caughtTypes, tryNodes);
                    if (!allTypes.contains(tbinding.getQualifiedName())) {
                        exceptionTypes.add(tbinding);
                    }
                }
            }
            return false;
        }
        
        @SuppressWarnings("unchecked")
        private Set<String> getExceptionTypes(Set<String> caughtTypes, Stack<TryStatement>tryNodes) {
            Set <String> allTypes = new HashSet<>();
            Stream<CatchClause> catchClasuseStream = tryNodes.stream().flatMap(n -> n.catchClauses().stream());
            for (CatchClause node: catchClasuseStream.collect(Collectors.toSet())) {
                Type type = node.getException().getType();
                if (type.isUnionType()) {
                    UnionType unionType = (UnionType)type;
                    Stream<Type> typeStream = unionType.types().stream();
                    Set <String> types = typeStream.map(t -> t.resolveBinding().getQualifiedName())
                                                   .collect(Collectors.toSet());
                    allTypes.addAll(types);
                } else {
                    allTypes.add(type.resolveBinding().getQualifiedName());
                }
            }
            allTypes.addAll(caughtTypes);
            return allTypes;
        }
        
        private boolean isUncheckedException(ITypeBinding tbinding) {
            while (tbinding != null) {
                String type = tbinding.getQualifiedName();
                if (type.equals(RUNTIME_EXCEPTION_NAME)) {
                    return true;
                }
                tbinding = tbinding.getSuperclass();
            }
            return false;
        }
    }
    
    private class CalledMethod {
        
        private ASTNode methodNode;
        private Set<String> caughtTypes;
        
        CalledMethod(ASTNode methodNode, Set<String> caughtTypes) {
            this.methodNode = methodNode;
            this.caughtTypes = caughtTypes;
        }
        
        ASTNode getASTNode() {
            return methodNode;
        }
        
        Set<String> getCaughtTypes() {
            return caughtTypes;
        }
        
        @Override
        public boolean equals(Object obj) {
            return (obj instanceof CalledMethod) ? equals((CalledMethod)obj) : false;
        }
        
        public boolean equals(CalledMethod cmethod) {
            return cmethod != null &&
                    (this == cmethod || cmethod.methodNode.equals(methodNode));
        }
        
        @Override
        public int hashCode() {
            return methodNode.hashCode();
        }
    }
}
