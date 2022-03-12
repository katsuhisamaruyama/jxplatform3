/*
 *  Copyright 2022
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.jxplatform.builder.srcmodel;

import org.jtool.srcmodel.JavaClass;
import org.jtool.srcmodel.JavaMethod;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.AnonymousClassDeclaration;
import org.eclipse.jdt.core.dom.CreationReference;
import org.eclipse.jdt.core.dom.EnumDeclaration;
import org.eclipse.jdt.core.dom.ExpressionMethodReference;
import org.eclipse.jdt.core.dom.IMethodBinding;
import org.eclipse.jdt.core.dom.ITypeBinding;
import org.eclipse.jdt.core.dom.LambdaExpression;
import org.eclipse.jdt.core.dom.SuperMethodReference;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.eclipse.jdt.core.dom.TypeMethodReference;
import java.util.Set;
import java.util.HashSet;

/**
 * Parses Java source code and stores information on lambda expressions.
 * 
 * @see org.eclipse.jdt.core.dom.LambdaExpression
 * 
 * LambdaExpression
 * 
 * @author Katsuhisa Maruyama
 */
public class LambdaCollector extends ASTVisitor {
    
    private JavaMethod jmethod;
    
    private Set<JavaClass> lambdas = new HashSet<>();
    
    private int id = 1;
    
    public LambdaCollector(JavaMethod jmethod) {
        this.jmethod = jmethod;
    }
    
    public Set<JavaClass> getLambdas() {
        return lambdas;
    }
    
    @Override
    public boolean visit(LambdaExpression node) {
        registerLambda(node);
        id++;
        return true;
    }
    
    private void registerLambda(LambdaExpression node) {
        ITypeBinding tbinding = node.resolveTypeBinding();
        IMethodBinding mbinding = node.resolveMethodBinding();
        if (tbinding != null && mbinding != null) {
            tbinding = tbinding.getTypeDeclaration();
            mbinding = mbinding.getMethodDeclaration();
            
            String name = jmethod.getClassName() + "$LAMBDA" + String.valueOf(id);
            JavaClass anonymousClass = new JavaClass(node, tbinding, name, jmethod);
            JavaMethod anonymousMethod = new JavaMethod(node, mbinding, anonymousClass);
            anonymousClass.addMethod(anonymousMethod);
            lambdas.add(anonymousClass);
        }
    }
    
    @Override
    public boolean visit(TypeDeclaration node) {
        return false;
    }
    
    @Override
    public boolean visit(AnonymousClassDeclaration node) {
        return false;
    }
    
    @Override
    public boolean visit(EnumDeclaration node) {
        return false;
    }
    
    @Override
    public boolean visit(CreationReference node) {
        return false;
    }
    
    @Override
    public boolean visit(ExpressionMethodReference node) {
        return false;
    }
    
    @Override
    public boolean visit(SuperMethodReference node) {
        return false;
    }
    
    @Override
    public boolean visit(TypeMethodReference node) {
        return false;
    }
}
