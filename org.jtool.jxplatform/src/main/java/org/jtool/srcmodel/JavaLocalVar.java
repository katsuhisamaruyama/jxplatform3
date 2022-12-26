/*
 *  Copyright 2020
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.srcmodel;

import static org.jtool.jxplatform.builder.ModelBuilder.br;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.IVariableBinding;
import org.eclipse.jdt.core.dom.ITypeBinding;
import org.eclipse.jdt.core.dom.VariableDeclaration;

/**
 * An object representing a local variable or a formal parameter.
 * 
 * @author Katsuhisa Maruyama
 */
public class JavaLocalVar extends JavaVariable {
    
    /**
     * The variable binding information on this class.
     */
    private final IVariableBinding binding;
    
    /**
     * the identification number of this local variable.
     */
    private final long variableId;
    
    /**
     * A method that contains this local variable.
     */
    private final JavaMethod declaringMethod;
    
    /**
     * Creates a new object representing a local variable.
     * This constructor is not intended to be invoked by clients.
     * @param node the AST node for this local variable
     * @param jmethod the method that contains this local variable in its body
     */
    public JavaLocalVar(VariableDeclaration node, JavaMethod jmethod) {
        this(node, node.resolveBinding().getVariableDeclaration(), jmethod);
    }
    
    /**
     * Creates a new object representing a local variable.
     * This constructor is not intended to be invoked by clients.
     * @param node the AST node for this local variable
     * @param vbinding the variable binding information on this local variable
     * @param jmethod the method that contains this local variable in its body
     */
    JavaLocalVar(ASTNode node, IVariableBinding vbinding, JavaMethod jmethod) {
        super(node, jmethod.getFile());
        
        if (vbinding != null && vbinding.getType() != null) {
            this.binding = vbinding.getVariableDeclaration();
            this.qname = new QualifiedName("", vbinding.getName());
            this.type = vbinding.getType().getErasure().getQualifiedName();
            this.isPrimitive = vbinding.getType().isPrimitive();
            this.modifiers = vbinding.getModifiers();
            this.kind = getKind(vbinding);
            this.variableId = vbinding.getVariableId();
            this.declaringMethod = jmethod;
        } else {
            this.binding = null;
            this.qname = new QualifiedName();
            this.kind = JavaVariable.Kind.UNKNOWN;
            this.variableId = -1;
            this.declaringMethod = null;
        }
    }
    
    /**
     * Creates a new object representing a formal parameter.
     * This constructor is not intended to be invoked by clients.
     * @param tbinding the type binding information on this formal parameter
     * @param jmethod the method that contains this local variable in its body
     */
    JavaLocalVar(ITypeBinding tbinding, JavaMethod jmethod) {
        super(null, jmethod.getFile());
        
        assert tbinding != null;
        
        tbinding = tbinding.getTypeDeclaration();
        this.binding = null;
        this.qname = new QualifiedName("", tbinding.getName());
        this.type = tbinding.getQualifiedName();
        this.isPrimitive = tbinding.isPrimitive();
        this.modifiers = tbinding.getModifiers();
        this.kind = JavaVariable.Kind.J_PARAMETER;
        this.variableId = -1;
        this.declaringMethod = jmethod;
    }
    
    /**
     * Returns the variable binding information on this local variable.
     * @return the variable binding information
     */
    public IVariableBinding getVariableBinding() {
        return binding;
    }
    
    /**
     * Returns the identification number of this local variable.
     * @return the identification number
     */
    public long getVariableId() {
        return variableId;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object obj) {
        return (obj instanceof JavaLocalVar) ? equals((JavaLocalVar)obj) : false;
    }
    
    /**
     * Tests if a given local variable is equal to this local variable.
     * @param jlocal the local variable to be checked
     * @return the {@code true} if the given local variable is equal to this local variable
     */
    public boolean equals(JavaLocalVar jlocal) {
        return jlocal != null && declaringMethod != null &&
                (this == jlocal ||
                (declaringMethod.equals(jlocal.declaringMethod) &&
                 qname.fqn().equals(jlocal.qname.fqn()) && variableId == jlocal.variableId));
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return qname.fqn().hashCode();
    }
    
    /**
     * Obtains information on this local variable.
     * @return the string representing the information
     */
    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append(br);
        buf.append("LOCAL: ");
        buf.append(getName());
        buf.append("@");
        buf.append(getType());
        return buf.toString();
    }
}
