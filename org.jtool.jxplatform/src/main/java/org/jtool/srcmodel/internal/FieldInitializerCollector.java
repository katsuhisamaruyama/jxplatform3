/*
 *  Copyright 2022
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.srcmodel.internal;

import org.jtool.srcmodel.JavaField;
import org.jtool.srcmodel.JavaProject;
import org.jtool.srcmodel.JavaElementUtil;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.AnonymousClassDeclaration;
import org.eclipse.jdt.core.dom.CreationReference;
import org.eclipse.jdt.core.dom.EnumDeclaration;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.ExpressionMethodReference;
import org.eclipse.jdt.core.dom.IBinding;
import org.eclipse.jdt.core.dom.SimpleName;
import org.eclipse.jdt.core.dom.SuperMethodReference;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.eclipse.jdt.core.dom.TypeMethodReference;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;
import org.eclipse.jdt.core.dom.IVariableBinding;
import org.eclipse.jdt.core.dom.LambdaExpression;
import java.util.Set;
import java.util.HashSet;

/**
 * Parses Java source code and stores information on field initializers appearing in a field.
 * 
 * @see org.eclipse.jdt.core.dom.Expression
 * 
 * VariableDeclarationFragment
 * 
 * @author Katsuhisa Maruyama
 */
public class FieldInitializerCollector extends ASTVisitor {
    
    private JavaProject jproject;
    
    private Set<JavaField> accessedFields = new HashSet<>();
    
    private boolean bindingOk = true;
    
    public FieldInitializerCollector(JavaProject jproject) {
        this.jproject = jproject;
    }
    
    public Set<JavaField> getAccessedFields() {
        return accessedFields;
    }
    
    public boolean isBindingOk() {
        return bindingOk;
    }
    
    @Override
    public boolean visit(VariableDeclarationFragment node) {
        IVariableBinding binding = node.resolveBinding();
        if (binding != null && binding.isField()) {
            Expression initializer = node.getInitializer();
            if (initializer != null) {
                initializer.accept(this);
            }
        }
        return false;
    }
    
    @Override
    public boolean visit(SimpleName node) {
        addFieldAccess(node.resolveBinding());
        return false;
    }
    
    private void addFieldAccess(IBinding binding) {
        if (binding != null) {
            if (binding.getKind() == IBinding.VARIABLE) {
                IVariableBinding vbinding = (IVariableBinding)binding;
                if (vbinding.isField() || vbinding.isEnumConstant()) {
                    JavaField jfield = JavaElementUtil.findDeclaringField(vbinding, jproject);
                    if (jfield != null) {
                        if (!accessedFields.contains(jfield)) {
                            accessedFields.add(jfield);
                        }
                    } else {
                        bindingOk = false;
                        String message = binding.getName() + " of " + vbinding.getDeclaringClass().getQualifiedName();
                        jproject.getModelBuilderImpl().printUnresolvedError(message);
                    }
                }
            }
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
    public boolean visit(LambdaExpression node) {
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
