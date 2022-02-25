/*
 *  Copyright 2020
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.cfg;

import org.jtool.srcmodel.JavaClass;
import org.jtool.srcmodel.QualifiedName;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.SuperFieldAccess;
import org.eclipse.jdt.core.dom.IVariableBinding;
import org.eclipse.jdt.core.dom.Modifier;
import org.eclipse.jdt.core.dom.ITypeBinding;

/**
 * An class that represents a reference to a field or an enum constant.
 * 
 * @author Katsuhisa Maruyama
 */
public class JFieldReference extends JReference {
    
    /**
     * A flag that indicates whether this is a reference to a field.
     */
    private boolean isField;
    
    /**
     * A flag that indicates whether this is a reference to an enum constant.
     */
    private boolean isEnumConstant;
    
    /**
     * A flag that indicates whether this is a reference a field within the class itself.
     */
    private boolean isLocal;
    
    /**
     * A flag that indicates whether this is a reference to a field within the parent class.
     */
    private boolean isSuper;
    
    /**
     * The AST node corresponding to the name of this reference.
     */
    protected ASTNode nameNode;
    
    /**
     * A flag that indicates whether this is a reference to a visible field.
     */
    protected boolean visible = true;
    
    /**
     * Creates a new object that represents a reference to a field.
     * @param node the AST node corresponding to this reference
     * @param nameNode the node of the name part of this field reference
     * @param vbinding the variable binding information on this reference
     */
    public JFieldReference(ASTNode node, ASTNode nameNode, IVariableBinding vbinding) {
        super(node);
        
        this.nameNode = nameNode;
        
        IVariableBinding binding = vbinding.getVariableDeclaration();
        this.enclosingClassName = findEnclosingClassName(node);
        this.enclosingMethodName = findEnclosingMethodName(node);
        ITypeBinding tbinding = binding.getDeclaringClass();
        if (tbinding != null) {
            this.declaringClassName = getQualifiedClassName(binding.getDeclaringClass().getTypeDeclaration());
        } else {
            this.declaringClassName = JavaClass.ArrayClassFqn.fqn();
        }
        this.declaringMethodName = "";
        
        this.name = binding.getName();
        this.fqn = new QualifiedName(declaringClassName, binding.getName());
        this.referenceForm = fqn.fqn();
        this.type = binding.getType().getErasure().getQualifiedName();
        this.isPrimitiveType = binding.getType().isPrimitive();
        this.modifiers = binding.getModifiers();
        if (tbinding != null) {
            this.inProject = tbinding.isFromSource();
        } else {
            this.inProject = false;
        }
        this.isField = isField(binding);
        this.isEnumConstant = isEnumConstant(binding);
        this.isLocal = enclosingClassName.equals(declaringClassName);
        this.isSuper = node instanceof SuperFieldAccess;
    }
    
    /**
     * Creates a new object that represents a reference to a field.
     * @param node the AST node corresponding to this reference
     * @param nameNode the node of the name part of this field reference
     * @param referenceForm the form of this reference
     * @param vbinding the variable binding information on this reference
     */
    public JFieldReference(ASTNode node, ASTNode nameNode, String referenceForm, IVariableBinding vbinding) {
        this(node, nameNode, vbinding);
        
        if (referenceForm.indexOf(".") == -1) {
            if (Modifier.isStatic(vbinding.getModifiers())) {
                this.referenceForm = declaringClassName + "." + referenceForm;
            } else {
                this.referenceForm = "this." + referenceForm;
            }
        } else {
            this.referenceForm = referenceForm;
        }
    }
    
    /**
     * Creates a new object that represents a reference to a type literal.
     * @param node the AST node corresponding to this reference
     * @param tbinding the type binding information on this reference
     */
    public JFieldReference(ASTNode node, ITypeBinding tbinding) {
        super(node);
        
        this.nameNode = node;
        
        this.enclosingClassName = findEnclosingClassName(node);
        this.enclosingMethodName = findEnclosingMethodName(node);
        if (tbinding != null) {
            this.declaringClassName = getQualifiedClassName(tbinding);
            this.type = tbinding.getErasure().getQualifiedName();
            this.isPrimitiveType = tbinding.isPrimitive();
            this.modifiers = tbinding.getModifiers();
            this.inProject = tbinding.isFromSource();
        } else {
            this.declaringClassName = "void";
            this.type = declaringClassName;
            this.isPrimitiveType = false;
            this.modifiers = 0;
            this.inProject = false;
        }
        this.declaringMethodName = "";
        this.name = "class";
        this.fqn = new QualifiedName(declaringClassName, name);
        this.referenceForm = fqn.fqn();
        
        this.isField = false;
        this.isEnumConstant = false;
        this.isLocal = enclosingClassName.equals(declaringClassName);
        this.isSuper = false;
    }
    
    /**
     * Creates a new object that represents a reference to a field.
     * @param node the AST node corresponding to this reference
     * @param className the name of a class declaring the referenced field
     * @param name the name of the referenced field
     * @param referenceForm the form of this reference
     * @param type the type of the referenced field
     * @param primitive {@code true} if the type of the referenced field is primitive, otherwise {@code false}
     * @param modifiers the modifier information on the referenced field
     * @param inProject {@code true} if the referenced field exists in the target project, otherwise {@code false}
     * @param visible {@code true} if this is a reference to a visible field, otherwise {@code false}
     */
    public JFieldReference(ASTNode node, String className, String name,
            String referenceForm, String type, boolean primitive, int modifiers, boolean inProject, boolean visible) {
        super(node);
        
        this.nameNode = node;
        this.enclosingClassName = findEnclosingClassName(node);
        this.enclosingMethodName = findEnclosingMethodName(node);
        this.declaringClassName = className;
        this.declaringMethodName = "";
        
        this.name = name;
        this.fqn = new QualifiedName(declaringClassName, name);
        this.referenceForm = referenceForm;
        this.type = type;
        this.isPrimitiveType = primitive;
        this.modifiers = modifiers;
        this.inProject = inProject;
        this.isField = true;
        this.isEnumConstant = false;
        this.isLocal = enclosingClassName.equals(declaringClassName);
        this.isSuper = node instanceof SuperFieldAccess;
        this.visible = visible;
    }
    
    /**
     * Returns the AST node corresponding to the name of this reference.
     * @return the AST node of the name part
     */
    public ASTNode getNameNode() {
        return nameNode;
    }
    
    /**
     * Changes the form of the reference to a field.
     * @param referenceForm the reference form to be set
     */
    public void changeReferenceForm(String referenceForm) {
        this.referenceForm = referenceForm;
    }
    
    /**
     * Tests if this reference is visible.
     * @return {@code true} if this is a reference to a visible field, otherwise {@code false}
     */
    @Override
    public boolean isVisible() {
        return visible;
    }
    
    /**
     * Tests if this is a reference to a field.
     * @return always {@code true} that indicates a field reference
     */
    @Override
    public boolean isFieldAccess() {
        return true;
    }
    
    /**
     * Tests if this is a reference to a field.
     * @return {@code true} if this is a field reference, otherwise {@code false}
     */
    public boolean isField() {
        return isField;
    }
    
    /**
     * Tests if this is a reference to an enum constant.
     * @return {@code true} if this is an enum constant reference, otherwise {@code false}
     */
    public boolean isEnumConstant() {
        return isEnumConstant;
    }
    
    /**
     * Tests if this is a reference to a type literal.
     * @return {@code true} if this is a type literal reference, otherwise {@code false}
     */
    public boolean isTypeLiteral() {
        return fqn.getMemberSignature().equals("class");
    }
    
    /**
     * Tests if this is a reference a field within the same class.
     * @return {@code true} if this is a reference to a field within the same class, otherwise {@code false}
     */
    public boolean isLocal() {
        return isLocal;
    }
    
    /**
     * Tests if this is a reference to a field within the parent class.
     * @return {@code true} if this is a reference to a field within the parent, otherwise {@code false}
     */
    public boolean isSuper() {
        return isSuper;
    }
    
    /**
     * Tests if this is a reference to a final field.
     * @return {@code true} if this is a final field reference, otherwise {@code false}
     */
    public boolean isFinal() {
        return Modifier.isFinal(modifiers);
    }
    
    /**
     * Tests if this is a reference to a static field.
     * @return {@code true} if this is a static field reference, otherwise {@code false}
     */
    public boolean isStatic() {
        return Modifier.isStatic(modifiers);
    }
    
    /**
     * Tests if this is a reference to a volatile field.
     * @return {@code true} if this is a volatile field reference, otherwise {@code false}
     */
    public boolean isVolatile() {
        return Modifier.isVolatile(modifiers);
    }
    
    /**
     * Tests if this is a reference to a transient field.
     * @return {@code true} if this is a transient field reference, otherwise {@code false}
     */
    public boolean isTransient() {
        return Modifier.isTransient(modifiers);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public int getStartPosition() {
        return nameNode.getStartPosition();
    }
    
    /**
     * Obtains information on this field reference.
     * @return the string representing the information
     */
    @Override
    public String toString() {
        return fqn + "@" + type;
    }
}
