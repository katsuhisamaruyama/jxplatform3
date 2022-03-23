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
import org.eclipse.jdt.core.dom.CatchClause;
import org.eclipse.jdt.core.dom.ITypeBinding;
import org.eclipse.jdt.core.dom.IMethodBinding;
import java.util.Set;
import java.util.HashSet;
import java.util.stream.Stream;

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
        
        Set<JavaMethod> methods = new HashSet<JavaMethod>();
        collectExceptions(jmethod, methods);
        return exceptionTypes;
    }
    
    private void collectExceptions(JavaMethod jmethod, Set<JavaMethod> methods) {
        if (methods.contains(jmethod)) {
            return;
        }
        
        methods.add(jmethod);
        
        MethodCallVisitor visitor = new MethodCallVisitor();
        jmethod.getASTNode().accept(visitor);
        exceptionTypes.addAll(visitor.exceptionTypes);
        for (JavaMethod jm : visitor.calledMethods) {
            collectExceptions(jm, methods);
        }
    }
    
    private class MethodCallVisitor extends ASTVisitor {
        
        private static final String RUNTIME_EXCEPTION_NAME = "java.lang.RuntimeException";
        
        private Set<JavaMethod> calledMethods = new HashSet<JavaMethod>();
        private Set<ITypeBinding> exceptionTypes = new HashSet<ITypeBinding>();
        
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
            
            JavaMethod cmethod = JavaElementUtil.findDeclaringMethod(mbinding, jproject);
            if (cmethod != null && cmethod.getASTNode() != null) {
                calledMethods.add(cmethod);
            }
            for (ITypeBinding tbinding : mbinding.getExceptionTypes()) {
                if (isUncheckedException(tbinding) && !existsCatchCause(node, tbinding)) {
                    exceptionTypes.add(tbinding);
                }
            }
        }
        
        @Override
        public boolean visit(ThrowStatement node) {
            ITypeBinding tbinding = node.getExpression().resolveTypeBinding();
            if (tbinding != null) {
                if (isUncheckedException(tbinding) && !existsCatchCause(node, tbinding)) {
                    exceptionTypes.add(tbinding);
                }
            }
            return false;
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
        
        @SuppressWarnings("unchecked")
        private boolean existsCatchCause(ASTNode node, ITypeBinding tbinding) {
            TryStatement tryNode = (TryStatement)JavaElementUtil.findAncestorNode(node, ASTNode.TRY_STATEMENT);
            if (tryNode == null) {
                return false;
            }
            
            Stream<CatchClause> stream = (Stream<CatchClause>)tryNode.catchClauses().stream();
            return stream.map(c -> c.getException().getType().resolveBinding())
                         .filter(b -> b != null)
                         .map(b -> b.getQualifiedName())
                         .anyMatch(n -> n.equals(tbinding.getQualifiedName()));
        }
    }
}
