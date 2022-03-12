/*
 *  Copyright 2022
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.jxplatform.builder.srcmodel;

import org.jtool.srcmodel.JavaMethod;
import org.jtool.srcmodel.JavaProject;
import org.jtool.srcmodel.JavaElementUtil;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.ClassInstanceCreation;
import org.eclipse.jdt.core.dom.ConstructorInvocation;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.core.dom.SuperConstructorInvocation;
import org.eclipse.jdt.core.dom.SuperMethodInvocation;
import org.eclipse.jdt.core.dom.ThrowStatement;
import org.eclipse.jdt.core.dom.ITypeBinding;
import org.eclipse.jdt.core.dom.IMethodBinding;
import java.util.Set;
import java.util.HashSet;

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
        
        private static final String EXCEPTION_NAME = "java.lang.Exception";
        private static final String RUNTIME_EXCEPTION_NAME = "java.lang.RuntimeException";
        
        private Set<JavaMethod> calledMethods = new HashSet<JavaMethod>();
        private Set<ITypeBinding> exceptionTypes = new HashSet<ITypeBinding>();
        
        @Override
        public boolean visit(MethodInvocation node) {
            if (node.resolveMethodBinding() != null) {
                addMethodCall(node.resolveMethodBinding());
            }
            return false;
        }
        
        @Override
        public boolean visit(SuperMethodInvocation node) {
            if (node.resolveMethodBinding() != null) {
                addMethodCall(node.resolveMethodBinding());
            }
            return false;
        }
        
        @Override
        public boolean visit(ConstructorInvocation node) {
            if (node.resolveConstructorBinding() != null) {
                addMethodCall(node.resolveConstructorBinding());
            }
            return false;
        }
        
        @Override
        public boolean visit(SuperConstructorInvocation node) {
            if (node.resolveConstructorBinding() != null) {
                addMethodCall(node.resolveConstructorBinding());
            }
            return false;
        }
        
        @Override
        public boolean visit(ClassInstanceCreation node) {
            if (node.resolveConstructorBinding() != null) {
                addMethodCall(node.resolveConstructorBinding());
            }
            return false;
        }
        
        @Override
        public boolean visit(ThrowStatement node) {
            ITypeBinding tbinding = node.getExpression().resolveTypeBinding();
            if (tbinding != null) {
                exceptionTypes.add(tbinding);
            }
            return false;
        }
        
        private void addMethodCall(IMethodBinding mbinding) {
            if (mbinding != null) {
                if (mbinding.isDefaultConstructor() || mbinding.isAnnotationMember()) {
                    return;
                }
                
                JavaMethod jmethod = JavaElementUtil.findDeclaringMethod(mbinding, jproject);
                if (jmethod != null && jmethod.getASTNode() != null) {
                    calledMethods.add(jmethod);
                }
                for (ITypeBinding tbinding : mbinding.getExceptionTypes()) {
                    if (isUncheckedException(tbinding)) {
                        exceptionTypes.add(tbinding);
                    }
                }
            }
        }
        
        private boolean isUncheckedException(ITypeBinding tbinding) {
            while (tbinding != null) {
                String type = tbinding.getQualifiedName();
                if (type.equals(EXCEPTION_NAME) || type.equals(RUNTIME_EXCEPTION_NAME)) {
                    return true;
                }
                tbinding = tbinding.getSuperclass();
            }
            return false;
        }
    }
}
