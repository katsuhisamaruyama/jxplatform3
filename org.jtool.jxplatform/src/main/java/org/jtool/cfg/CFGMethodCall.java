/*
 *  Copyright 2022
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.cfg;

import org.jtool.cfg.internal.refmodel.JClass;
import org.jtool.srcmodel.QualifiedName;
import org.eclipse.jdt.core.dom.ASTNode;
import java.util.List;
import java.util.ArrayList;
import java.util.Set;

/**
 * A node that represents a method call.
 * 
 * @author Katsuhisa Maruyama
 */
public class CFGMethodCall extends CFGStatement {
    
    /**
     * The reference to the called method.
     */
    private final JMethodReference jmethodCall;
    
    /**
     * The collection of actual-in nodes on this method call.
     */
    private List<CFGParameter> actualIns = new ArrayList<>();
    
    /**
     * A actual-out node on this method call.
     */
    private CFGParameter actualOut = null;
    
    /**
     * The name of a variable that stores the return value.
     */
    private String returnValueName = null;
    
    /**
     * Creates a new object that represents a method call.
     * @param node the AST node corresponding to this node
     * @param kind the kind of this node
     * @param jcall a reference to the called method
     */
    public CFGMethodCall(ASTNode node, CFGNode.Kind kind, JMethodReference jcall) {
        super(node, kind);
        jmethodCall = jcall;
    }
    
    /**
     * Returns the reference to the called method
     * @return the method reference
     */
    public JMethodReference getMethodCall() {
        return jmethodCall;
    }
    
    /**
     * Tests if this method call has a receiver.
     * @return {@code true} if this method call has a receiver, otherwise {@code false}
     */
    public boolean hasReceiver() {
        return jmethodCall.hasReceiver();
    }
    
    /**
     * Tests if a method call corresponding to this reference has explicitly a receiver.
     * @return {@code true} a method call corresponding to this reference has a receiver, otherwise {@code false}
     */
    public boolean hasExplicitReceiver() {
        return jmethodCall.hasExplicitReceiver();
    }
    
    /**
     * Returns the receiver node on this method call.
     * @return the receiver node, or {@code null} if this method call done within the same class
     */
    public CFGReceiver getReceiver() {
        return jmethodCall.getReceiver();
    }
    
    /**
     * Returns the name of the called method.
     * @return the method name
     */
    public String getName() {
        return jmethodCall.getName();
    }
    
    /**
     * Returns the signature of the called method
     * @return the method signature
     */
    public String getSignature() {
        return jmethodCall.getSignature();
    }
    
    /**
     * Returns the fully-qualified name of the called method.
     * @return the fully-qualified name of the method
     */
    public QualifiedName getQualifiedName() {
        return jmethodCall.getQualifiedName();
    }
    
    /**
     * Returns the name of a class declaring the called method
     * @return the class name of the called method
     */
    public String getDeclaringClassName() {
        return jmethodCall.getDeclaringClassName();
    }
    
    /**
     * Returns the return type of the called method.
     * @return the return type
     */
    public String getReturnType() {
        return jmethodCall.getType();
    }
    
    /**
     * Tests if the type of the called method is primitive.
     * @return {@code true} if the primitive type, otherwise {@code false}
     */
    public boolean isPrimitiveType() {
        return jmethodCall.isPrimitiveType();
    }
    
    /**
     * Tests if the type of the called method is {@code void}.
     * @return {@code true} if the {@code void} type, otherwise {@code false}
     */
    public boolean isVoidType() {
        return jmethodCall.isVoidType();
    }
    
    /**
     * Tests if this is a call to a constructor.
     * @return {@code true} if this is a constructor call, otherwise {@code false}
     */
    public boolean isConstructorCall() {
        return jmethodCall.isConstructor();
    }
    
    /**
     * Returns the number of arguments on this method call.
     * @return the number of the arguments
     */
    public int getArgumentSize() {
        return jmethodCall.getArgumentSize();
    }
    
    /**
     * Returns the approximated types of receiver associated to this node.
     * These types include classes declaring method that might be dynamically called.
     * In the case of a field access, the approximated types are not supported
     * because no dynamic binding is performed.
     * @return the collection of the approximated types
     */
    public Set<JClass> getApproximatedTypes() {
        return jmethodCall.getApproximatedTypes();
    }
    
    /**
     * Returns the approximated type names of receiver associated to this node.
     * @return the collection of the approximated type names
     */
    public Set<String> getApproximatedTypeNames() {
        return jmethodCall.getApproximatedTypeNames();
    }
    
    /**
     * Returns type parameter names.
     * @return the collection of the type parameter names
     */
    public List<String> getTypeParameters() {
        return jmethodCall.getTypeParameters();
    }
    
    /**
     * Tests if the called method exists inside the target project.
     * @return {@code true} if this is a call to the inside method, otherwise {@code false}
     */
    public boolean isInProject() {
        return jmethodCall.isInProject();
    }
    
    /**
     * Adds an actual-in node on this method call.
     * This method is not intended to be invoked by clients.
     * @param node the actual-in node to be added
     */
    public void addActualIn(CFGParameter node) {
        assert node != null;
        
        actualIns.add(node);
    }
    
    /**
     * Sets actual-in nodes on this method call.
     * This method is not intended to be invoked by clients.
     * @param nodes the collection of actual-in nodes to be set
     */
    public void setActualIns(List<CFGParameter> nodes) {
        assert nodes != null;
        
        actualIns.addAll(nodes);
    }
    
    /**
     * Sets an actual-out node on this method call.
     * This method is not intended to be invoked by clients.
     * @param node the actual-out node to be set
     */
    public void setActualOut(CFGParameter node) {
        assert node != null;
        
        actualOut = node;
    }
    
    /**
     * Returns actual-in nodes on this method call.
     * @return the collection of the actual-in nodes
     */
    public List<CFGParameter> getActualIns() {
        return actualIns;
    }
    
    /**
     * Returns an actual-out node on this method call.
     * @return the actual-out node
     */
    public CFGParameter getActualOut() {
        return actualOut;
    }
    
    /**
     * Returns an actual-in node of an argument specified by the index on this method call.
     * @param index the index number of the argument to be retrieved 
     * @return the found actual-in node, or {@code null} if no argument is found
     */
    public CFGParameter getActualIn(int index) {
        return (index < 0 || index >= actualIns.size()) ? null : actualIns.get(index);
    }
    
    /**
     * Tests if this method call has any parameter.
     * @return {@code true} if this method call has any parameter, otherwise {@code false}
     */
    public boolean hasParameters() {
        return actualIns.size() != 0;
    }
    
    /**
     * Sets the name of a variable that stores the return value.
     * @param returnValueName the variable name
     */
    public void setReturnValueName(String returnValueName) {
        this.returnValueName = returnValueName;
    }
    
    /**
     * Returns the name of a variable that stores the return value.
     * @return the variable name
     */
    public String getReturnValueName() {
        return returnValueName;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return super.toString() + " TO = " + getQualifiedName();
    }
}
