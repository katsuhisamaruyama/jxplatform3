/*
 *  Copyright 2022
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.srcmodel.builder;

import org.jtool.srcmodel.JavaMethod;
import org.jtool.srcmodel.JavaClass;
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
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.UnionType;
import org.eclipse.jdt.core.dom.CatchClause;
import org.eclipse.jdt.core.dom.ITypeBinding;
import org.eclipse.jdt.core.dom.IMethodBinding;
import java.util.List;
import java.util.Set;
import java.util.HashSet;
import java.util.Stack;
import java.util.stream.Stream;
import java.util.stream.Collectors;

/**
 * Parses Java source code and stores information on implicit (not explicitly caught) exceptions
 * when calling methods and constructors.
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
public class UncaughtExceptionTypeCollector {
    
    private JavaProject jproject;
    
    private Set<ITypeBinding> exceptionTypes = new HashSet<>();
    
    public UncaughtExceptionTypeCollector() {
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
            
            Set<String> types = getExceptionTypes(caughtTypes, tryNodes);
            JavaMethod jmethod = JavaElementUtil.findDeclaringMethod(mbinding, jproject);
            if (jmethod != null && jmethod.getASTNode() != null) {
                calledMethods.add(new CalledMethod(jmethod.getASTNode(), types));
            }
            
            for (ITypeBinding tbinding : mbinding.getExceptionTypes()) {
                if (!contains(types, tbinding)) {
                    if (isUncheckedException(tbinding)) {
                        exceptionTypes.add(tbinding);
                    }
                }
            }
        }
        
        private boolean contains(Set<String> types, ITypeBinding tbinding) {
            if (types.contains(tbinding.getQualifiedName())) {
                return true;
            }
            
            JavaClass jclass = jproject.getClass(tbinding.getQualifiedName());
            if (jclass == null) {
                return false;
            }
            
            for (JavaClass jc : jclass.getAncestors()) {
                if (types.contains(jc.getQualifiedName().fqn())) {
                    return true;
                }
            }
            return false;
        }
        
        @SuppressWarnings("unchecked")
        @Override
        public boolean visit(TryStatement node) {
            tryNodes.push(node);
            ((List<Expression>)node.resources()).stream().forEach(n -> n.accept(this));
            node.getBody().accept(this);
            tryNodes.pop();
            
            ((List<CatchClause>)node.catchClauses()).forEach(n -> n.accept(this));
            if (node.getFinally() != null) {
                node.getFinally().accept(this);
            }
            return false;
        }
        
        @Override
        public boolean visit(ThrowStatement node) {
            ITypeBinding tbinding = node.getExpression().resolveTypeBinding();
            if (tbinding != null && isUncheckedException(tbinding)) {
                Set<String> types = getExceptionTypes(caughtTypes, tryNodes);
                if (!contains(types, tbinding)) {
                    exceptionTypes.add(tbinding);
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
                    Set<String> types = typeStream.map(t -> t.resolveBinding().getQualifiedName())
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
