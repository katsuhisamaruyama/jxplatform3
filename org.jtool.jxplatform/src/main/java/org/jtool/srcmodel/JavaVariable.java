/*
 *  Copyright 2022
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.srcmodel;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.IVariableBinding;
import org.eclipse.jdt.core.dom.Modifier;

/**
 * An object representing a field, an enum-constant, a local variable, or a parameter.
 * 
 * @author Katsuhisa Maruyama
 */
public abstract class JavaVariable extends JavaElement {
    
    /**
     * A file that declares this model element.
     */
    protected JavaFile jfile;
    
    /**
     * The fully-qualified name of this variable.
     */
    protected QualifiedName qname;
    
    /**
     * The type of this variable.
     */
    protected String type = "";
    
    /**
     * A flag indicating whether the type of this variable is primitive.
     */
    protected boolean isPrimitive = false;
    
    /**
     * The modifiers of this variable.
     */
    protected int modifiers;
    
    /**
     * The kind of this variable.
     */
    protected Kind kind;
    
    /**
     * The kind of a variable.
     */
    enum Kind {
        J_FIELD, J_ENUM_CONSTANT, J_LOCAL, J_PARAMETER, UNKNOWN;
    }
    
    /**
     * Creates a new object representing a variable.
     * This constructor is not intended to be invoked by clients.
     * @param node node the AST node for this variable
     * @param jfile the file that declares this variable
     */
    protected JavaVariable(ASTNode node, JavaFile jfile) {
        super(node);
        
        assert jfile != null;
        this.jfile = jfile;
    }
    
    /**
     * Returns the kind of a variable.
     * @param binding type binding information on the variable
     * @return the kind of the variable
     */
    protected JavaVariable.Kind getKind(IVariableBinding binding) {
        if (binding.isEnumConstant()) {
            return JavaVariable.Kind.J_ENUM_CONSTANT;
        } else if (binding.isField()) {
            return JavaVariable.Kind.J_FIELD;
        } else if (binding.isParameter()) {
            return JavaVariable.Kind.J_PARAMETER;
        } else {
            return JavaVariable.Kind.J_LOCAL;
        }
    }
    
    /**
     * Returns the project which this class exists in.
     * @return the project of this model element
     */
    public JavaProject getJavaProject() {
        return jfile.getJavaProject();
    }
    
    /**
     * Returns the file that declares this class.
     * @return the declaring file
     */
    public JavaFile getFile() {
        return jfile;
    }
    
    /**
     * Returns the fully-qualified name of this variable.
     * @return the fully-qualified variable name
     */
    @Override
    public QualifiedName getQualifiedName() {
        return qname;
    }
    
    /**
     * Returns the name of this variable.
     * @return the class name
     */
    public String getClassName() {
        return qname.getClassName();
    }
    
    /**
     * Returns the name of this variable.
     * @return the variable name
     */
    public String getName() {
        return qname.getMemberSignature();
    }
    
    /**
     * Returns the type of this variable.
     * @return the type name
     */
    public String getType() {
        return type;
    }
    
    /**
     * Tests if the type of this variable is primitive.
     * @return {@code true} if the type of this variable is primitive, otherwise {@code false}
     */
    public boolean isPrimitiveType() {
        return isPrimitive;
    }
    
    /**
     * The the value that stores information on the modifiers of this variable.
     * @return the modifier value of this variable
     */
    public int getModifiers() {
        return modifiers;
    }
    
    /**
     * Tests if this is a filed.
     * @return {@code true} if this is a field, otherwise {@code false}
     */
    public boolean isField() {
        return kind == JavaVariable.Kind.J_FIELD;
    }
    
    /**
     * Tests if this is an enum constant.
     * @return {@code true} if this is an enum constant, otherwise {@code false}
     */
    public boolean isEnumConstant() {
        return kind == JavaVariable.Kind.J_ENUM_CONSTANT;
    }
    
    /**
     * Tests if this is a local variable.
     * @return {@code true} if this is a local variable, otherwise {@code false}
     */
    public boolean isLocal() {
        return kind == JavaVariable.Kind.J_LOCAL;
    }
    
    /**
     * Tests if this is a parameter.
     * @return {@code true} if this is a parameter, otherwise {@code false}
     */
    public boolean isParameter() {
        return kind == JavaVariable.Kind.J_PARAMETER;
    }
    
    /**
     * Tests if this is a final variable.
     * @return {@code true} if this is a final variable, otherwise {@code false}
     */
    public boolean isFinal() {
        return Modifier.isFinal(modifiers);
    }
}
