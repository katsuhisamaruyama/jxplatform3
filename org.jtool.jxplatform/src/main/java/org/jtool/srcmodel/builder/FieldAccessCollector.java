/*
 *  Copyright 2022
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.srcmodel.builder;

import org.jtool.srcmodel.JavaField;
import org.jtool.srcmodel.JavaProject;
import org.jtool.srcmodel.JavaElementUtil;
import org.jtool.jxplatform.builder.Logger;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.AnonymousClassDeclaration;
import org.eclipse.jdt.core.dom.IBinding;
import org.eclipse.jdt.core.dom.IVariableBinding;
import org.eclipse.jdt.core.dom.QualifiedName;
import org.eclipse.jdt.core.dom.SimpleName;
import org.eclipse.jdt.core.dom.SuperMethodReference;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.eclipse.jdt.core.dom.TypeMethodReference;
import org.eclipse.jdt.core.dom.LabeledStatement;
import org.eclipse.jdt.core.dom.LambdaExpression;
import org.eclipse.jdt.core.dom.BreakStatement;
import org.eclipse.jdt.core.dom.ContinueStatement;
import org.eclipse.jdt.core.dom.CreationReference;
import org.eclipse.jdt.core.dom.EnumDeclaration;
import org.eclipse.jdt.core.dom.ExpressionMethodReference;
import java.util.Set;
import java.util.HashSet;

/**
 * Parses Java source code and stores information on variable access appearing in a method or field.
 * 
 * @see org.eclipse.jdt.core.dom.Expression
 * 
 * Name:
 *   SimpleName
 *   QualifiedName
 * 
 * @author Katsuhisa Maruyama
 */
public class FieldAccessCollector extends ASTVisitor {
    
    private JavaProject jproject;
    
    private Set<JavaField> accessedFields = new HashSet<>();
    
    private boolean bindingOk = true;
    
    public FieldAccessCollector(JavaProject jproject) {
        this.jproject = jproject;
    }
    
    public Set<JavaField> getAccessedFields() {
        return accessedFields;
    }
    
    public boolean isBindingOk() {
        return bindingOk;
    }
    
    @Override
    public boolean visit(SimpleName node) { 
        addVariableAccess(node, node.resolveBinding());
        return true;
    }
    
    @Override
    public boolean visit(QualifiedName node) {
        addVariableAccess(node.getName(), node.resolveBinding());
        return true;
    }
    
    @Override
    public boolean visit(LabeledStatement node) {
        node.getBody().accept(this);
        return true;
    }
    
    @Override
    public boolean visit(BreakStatement node) {
        return false;
    }
    
    @Override
    public boolean visit(ContinueStatement node) {
        return false;
    }
    
    private void addVariableAccess(SimpleName name, IBinding binding) {
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
                        Logger logger = jproject.getModelBuilderImpl().getLogger();
                        logger.printUnresolvedError(binding.getName() + " of " + vbinding.getDeclaringClass().getQualifiedName());
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
