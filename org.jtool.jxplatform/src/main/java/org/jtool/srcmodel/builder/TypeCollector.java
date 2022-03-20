/*
 *  Copyright 2022
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.srcmodel.builder;

import org.jtool.srcmodel.JavaProject;
import org.jtool.srcmodel.JavaClass;
import org.jtool.srcmodel.JavaElementUtil;
import org.jtool.jxplatform.project.Logger;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.AnonymousClassDeclaration;
import org.eclipse.jdt.core.dom.PrimitiveType;
import org.eclipse.jdt.core.dom.SimpleType;
import org.eclipse.jdt.core.dom.SuperMethodReference;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.eclipse.jdt.core.dom.TypeMethodReference;
import org.eclipse.jdt.core.dom.QualifiedType;
import org.eclipse.jdt.core.dom.NameQualifiedType;
import org.eclipse.jdt.core.dom.WildcardType;
import org.eclipse.jdt.core.dom.ArrayType;
import org.eclipse.jdt.core.dom.CreationReference;
import org.eclipse.jdt.core.dom.EnumDeclaration;
import org.eclipse.jdt.core.dom.ExpressionMethodReference;
import org.eclipse.jdt.core.dom.ParameterizedType;
import org.eclipse.jdt.core.dom.UnionType;
import org.eclipse.jdt.core.dom.IntersectionType;
import org.eclipse.jdt.core.dom.LambdaExpression;
import org.eclipse.jdt.core.dom.ITypeBinding;
import java.util.Set;
import java.util.HashSet;

/**
 * Parses Java source code and stores information on types.
 * 
 * @see org.eclipse.jdt.core.dom.Type
 * 
 * Type:
 *   AnnotatableType:
 *     PrimitiveType
 *     SimpleType
 *     QualifiedType
 *     NameQualifiedType
 *     WildcardType
 *   ArrayType
 *   ParameterizedType
 *   ----UnionType
 *   ----IntersectionType
 * PrimitiveType:
 *   { Annotation } byte
 *   { Annotation } short
 *   { Annotation } char
 *   { Annotation } int
 *   { Annotation } long
 *   { Annotation } float
 *   { Annotation } double
 *   { Annotation } boolean
 *   { Annotation } void
 * ArrayType:
 *   Type { Annotation } [] { { Annotation } [] }
 * SimpleType:
 *   { Annotation } TypeName
 * QualifiedType:
 *   Type . {Annotation} SimpleName
 * NameQualifiedType:
 *   Name . { Annotation } SimpleName
 * WildcardType:
 *   { Annotation } ? [ ( extends | super) Type ]
 * ParameterizedType:
 *   Type &lt; Type { , Type } &gt;
 * UnionType:
 *   Type | Type { | Type }
 * IntersectionType:
 *   Type &amp; Type { &amp; Type }
 * 
 * @author Katsuhisa Maruyama
 */
public class TypeCollector extends ASTVisitor {
    
    private JavaProject jproject;
    
    private Set<JavaClass> types = new HashSet<>();
    
    private boolean bindingOk = true;
    
    public TypeCollector(JavaClass jclass) {
        this.jproject = jclass.getJavaProject();
    }
    
    public Set<JavaClass> getTypes() {
        return types;
    }
    
    public boolean isBindingOk() {
        return bindingOk;
    }
    
    @Override
    public boolean visit(PrimitiveType node) {
        return false;
    }
    
    @Override
    public boolean visit(SimpleType node) {
        ITypeBinding tbinding = node.resolveBinding();
        if (tbinding != null) {
            JavaClass jc = JavaElementUtil.findDeclaringClass(tbinding, jproject);
            types.add(jc);
        }
        return false;
    }
    
    @Override
    public boolean visit(QualifiedType node) {
        return true;
    }
    
    @Override
    public boolean visit(NameQualifiedType node) {
        return false;
    }
    
    @Override
    public boolean visit(WildcardType node) {
        return false;
    }
    
    @Override
    public boolean visit(ArrayType node) {
        return true;
    }
    
    @Override
    public boolean visit(ParameterizedType node) {
        ITypeBinding tbinding = node.resolveBinding();
        if (tbinding != null) {
            JavaClass jc = JavaElementUtil.findDeclaringClass(tbinding, jproject);
            if (jc != null) {
                types.add(jc);
                for (ITypeBinding b : tbinding.getTypeArguments()) {
                    collectUsedClasses(jc, b);
                }
            } else {
                bindingOk = false;
            }
        } else {
            bindingOk = false;
        }
        return false;
    }
    
    private void collectUsedClasses(JavaClass jc, ITypeBinding tbinding) {
        Logger logger = jproject.getModelBuilderImpl().getLogger();
        
        if (tbinding.isRawType()) {
            JavaClass jc2 = JavaElementUtil.findDeclaringClass(tbinding, jproject);
            if (jc2 != null) {
                jc.addUsedClass(jc2);
            } else {
                bindingOk = false;
                logger.printUnresolvedError(tbinding.getQualifiedName());
            }
            
        } else if (tbinding.isParameterizedType()) {
            JavaClass jc2 = JavaElementUtil.findDeclaringClass(tbinding, jproject);
            if (jc2 != null) {
                jc.addUsedClass(jc2);
                for (ITypeBinding b : tbinding.getTypeArguments()) {
                    collectUsedClasses(jc2, b);
                }
            } else {
                bindingOk = false;
                logger.printUnresolvedError(tbinding.getQualifiedName());
            }
        } else if (tbinding.isWildcardType()) {
            ITypeBinding b = tbinding.getBound();
            if (b != null && b.isRawType()) {
                JavaClass jc2 = JavaElementUtil.findDeclaringClass(tbinding.getBound(), jproject);
                if (jc2 != null) {
                    jc.addUsedClass(jc2);
                } else {
                    bindingOk = false;
                    logger.printUnresolvedError(b.getQualifiedName());
                }
            }
        }
    }
    
    @Override
    public boolean visit(UnionType node) {
        return true;
    }
    
    @Override
    public boolean visit(IntersectionType node) {
        return true;
    }
    
    @Override
    public boolean visit(TypeDeclaration node) {
        return true;
    }
    
    @Override
    public boolean visit(AnonymousClassDeclaration node) {
        return true;
    }
    
    @Override
    public boolean visit(EnumDeclaration node) {
        return true;
    }
    
    @Override
    public boolean visit(LambdaExpression node) {
        return true;
    }
    
    @Override
    public boolean visit(CreationReference node) {
        return true;
    }
    
    @Override
    public boolean visit(ExpressionMethodReference node) {
        return true;
    }
    
    @Override
    public boolean visit(SuperMethodReference node) {
        return true;
    }
    
    @Override
    public boolean visit(TypeMethodReference node) {
        return true;
    }
}
