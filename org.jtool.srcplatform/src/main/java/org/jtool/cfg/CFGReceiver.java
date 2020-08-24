/*
 *  Copyright 2020
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.cfg;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.core.dom.Modifier;
import org.eclipse.jdt.core.dom.Name;
import org.eclipse.jdt.core.dom.FieldAccess;
import org.eclipse.jdt.core.dom.IBinding;
import org.eclipse.jdt.core.dom.IMethodBinding;
import org.eclipse.jdt.core.dom.IVariableBinding;
import java.util.Set;

/**
 * A node for a receiver on a method call or a field access.
 * 
 * @author Katsuhisa Maruyama
 */
public class CFGReceiver extends CFGStatement {
    
    /**
     * The name of the receiver associated to this node.
     */
    private String name = "";
    
    /**
     * The type of the receiver associated to this node.
     */
    private String type;
    
    /**
     * The modifier information on the receiver associated to this node.
     */
    private int modifiers;
    
    /**
     * A flag that indicates whether this node is associated for a method call.
     */
    private boolean isMethodRef = false;
    
    /**
     * The collection of the approximated types of receiver associated to this node.
     */
    private Set<String> approximatedTypes;
    
    /**
     * Creates a new object that represents a receiver on a method call.
     * @param node the AST node correspond to this node
     * @param kind the kind of this node
     */
    public CFGReceiver(ASTNode node, CFGNode.Kind kind) {
        super(node, kind);
        
        if (node instanceof MethodInvocation) {
            IMethodBinding mbinding = ((MethodInvocation)node).resolveMethodBinding();
            if (mbinding != null) {
                type = mbinding.getReturnType().getQualifiedName();
                modifiers = mbinding.getModifiers();
                isMethodRef = true;
            }
        } else if (node instanceof FieldAccess) {
            IVariableBinding vbinding = ((FieldAccess)node).resolveFieldBinding();
            if (vbinding != null) {
                modifiers = vbinding.getModifiers();
                type = vbinding.getType().getQualifiedName();
                isMethodRef = false;
                
            }
        } else if (node instanceof Name) {
            IVariableBinding vbinding = getVariableBinding((Name)node);
            if (vbinding != null && vbinding.isField()) {
                modifiers = vbinding.getModifiers();
                type = vbinding.getType().getQualifiedName();
                isMethodRef = false;
            }
        }
    }
    
    /**
     * Obtains the variable binding information on a receiver.
     * @param node the AST node corresponding to the receiver
     * @return the variable binding information
     */
    private IVariableBinding getVariableBinding(Name node) {
        IBinding binding = node.resolveBinding();
        if (binding != null && binding.getKind() == IBinding.VARIABLE) {
            return (IVariableBinding)binding;
        }
        return null;
    }
    
    /**
     * Sets the name of the receiver associated to this node.
     * @param name the name to be set
     */
    public void setName(String name) {
        this.name = name;
    }
    
    /**
     * Returns the name of the receiver associated to this node.
     * @return the receiver name
     */
    public String getName() {
        return name;
    }
    
    /**
     * Returns the type of the receiver associated to this node.
     * @return the receiver type
     */
    public String getType() {
        return type;
    }
    
    /**
     * Tests if this node is associated for a method call.
     * @return {@code true} if this node is for a method call, otherwise {@code false}
     */
    public boolean isMethodRef() {
        return isMethodRef;
    }
    
    /**
     * Tests if a called method or an accessed field has the public visibility.
     * @return {@code true} if the visibility is public, otherwise {@code false}
     */
    public boolean isPublic() {
        return Modifier.isPublic(modifiers);
    }
    
    /**
     * Tests if a called method or an accessed field has the protected visibility.
     * @return {@code true} if the visibility is protected, otherwise {@code false}
     */
    public boolean isProtected() {
        return Modifier.isProtected(modifiers);
    }
    
    /**
     * Tests if a called method or an accessed field has the private visibility.
     * @return {@code true} if the visibility is private, otherwise {@code false}
     */
    public boolean isPrivate() {
        return Modifier.isPrivate(modifiers);
    }
    
    /**
     * Tests if a called method or an accessed field has the default visibility.
     * @return {@code true} if the visibility is default, otherwise {@code false}
     */
    public boolean isDefault() {
        return !isPublic() && !isProtected() && !isPrivate();
    }
    
    /**
     * Tests if a called method or an accessed field is a static.
     * @return {@code true} if this is a static call or access, otherwise {@code false}
     */
    public boolean isStatic() {
        return Modifier.isStatic(modifiers);
    }
    
    /**
     * Sets the approximated types of receiver associated to this node.
     * @param types the collection of the approximated types to be set
     */
    public void setApproximatedTypes(Set<String> types) {
        approximatedTypes = types;
    }
    
    /**
     * Returns the approximated types of receiver associated to this node.
     * These types include classes declaring method that might be dynamically called.
     * In the case of a field access, the approximated types are not supported
     * because no dynamic binding is performed.
     * @return the collection of the approximated types
     */
    public Set<String> getApproximatedTypes() {
        return approximatedTypes;
    }
}
