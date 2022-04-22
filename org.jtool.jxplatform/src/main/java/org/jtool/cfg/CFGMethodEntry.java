/*
 *  Copyright 2022
 *  Software Science and Technology Lab.
 *  Department of Computer Science, Ritsumeikan University
 */

package org.jtool.cfg;

import org.jtool.srcmodel.JavaMethod;
import java.util.List;
import java.util.ArrayList;

/**
 * The entry node for a CFG for a method or a constructor.
 * 
 * @author Katsuhisa Maruyama
 */
public class CFGMethodEntry extends CFGEntry {
    
    /**
     * The method associated with this entry node.
     */
    private JavaMethod jmethod;
    
    /**
     * The collection of formal-in nodes of the method associated with this entry node.
     */
    private List<CFGParameter> formalIns = new ArrayList<>();
    
    /**
     * The formal-out node for the return value of the method associated with this entry node.
     */
    private CFGParameter formalOut = null;
    
    /**
     * The collection of the exception nodes corresponding to {@code throws}. 
     */
    private List<CFGException> exceptionNodes = new ArrayList<>();
    
    /**
     * Creates a new object that represents an entry for a method.
     * This method is not intended to be invoked by clients.
     * @param jmethod the method associated with this entry node
     * @param kind the kind of this node
     */
    public CFGMethodEntry(JavaMethod jmethod, CFGNode.Kind kind) {
        super(jmethod.getASTNode(), kind, jmethod.getQualifiedName());
        this.jmethod = jmethod;
    }
    
    /**
     * Returns the method associated with this entry node.
     * @return the associated method
     */
    public JavaMethod getJavaMethod() {
        return jmethod;
    }
    
    /**
     * Adds a formal-in node to this method entry node.
     * This method is not intended to be invoked by clients.
     * @param node the formal-in node to be added
     */
    public void addFormalIn(CFGParameter node) {
        formalIns.add(node);
    }
    
    /**
     * Sets formal-in nodes to this method entry node.
     * This method is not intended to be invoked by clients.
     * @param nodes the collection of the formal-in nodes to be set
     */
    public void setFormalIns(List<CFGParameter> nodes) {
        formalIns.addAll(nodes);
    }
    
    /**
     * Sets a formal-out node for the return value to this method entry node.
     * @param node the formal-out node to be set
     */
    public void setFormalOut(CFGParameter node) {
        formalOut = node;
    }
    
    /**
     * Returns formal-in nodes of the method associated to this entry node.
     * @return the collection of the formal-in nodes
     */
    public List<CFGParameter> getFormalIns() {
        return formalIns;
    }
    
    /**
     * Returns the number of parameters of the method associated to this entry node.
     * @return the number of the parameters
     */
    public int getParameterSize() {
        return formalIns.size();
    }
    
    /**
     * Returns an formal-in node of a parameter specified by the index.
     * @param index the index number of the parameter to be retrieved
     * @return the found formal-in node, or {@code null} if no parameter is found
     */
    public CFGParameter getFormalIn(int index) {
        return (index < formalIns.size()) ? formalIns.get(index) : null;
    }
    
    /**
     * Returns an formal-out node of a parameter for the return value.
     * @return the formal-out node for the return value
     */
    public CFGParameter getFormalOut() {
        return formalOut;
    }
    
    /**
     * Tests if the method associated to this entry node has a parameter.
     * @return {@code true} if any parameter exists, otherwise {@code false}
     */
    public boolean hasParameters() {
        return formalIns.size() != 0;
    }
    
    /**
     * Adds an exception node to this method entry.
     * This method is not intended to be invoked by clients.
     * @param node the exception node to be added
     */
    public void addExceptionNode(CFGException node) {
        exceptionNodes.add(node);
    }
    
    /**
     * Returns the exception nodes corresponding to {@code throws} on this method entry.
     * @return the collection of the exception nodes
     */
    public List<CFGException> getExceptionNodes() {
        return exceptionNodes;
    }
    
    /**
     * Finds the exception node related to a specified type/
     * @param type the type of the exception
     * @return the found exception node, or {@code null} if no exception type is found
     */
    public CFGException getExceptionNode(String type) {
        return exceptionNodes.stream()
                             .filter(node -> node.getTypeName().equals(type))
                             .findFirst().orElse(null);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return super.toString() + " [ " + fqn.getMemberSignature() + " ]";
    }
}
