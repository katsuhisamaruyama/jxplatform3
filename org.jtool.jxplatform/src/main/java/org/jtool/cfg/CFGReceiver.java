/*
 *  Copyright 2020
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.cfg;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.StringLiteral;
import org.eclipse.jdt.core.dom.TypeLiteral;
import org.eclipse.jdt.core.dom.ParenthesizedExpression;
import org.eclipse.jdt.core.dom.CastExpression;

/**
 * A node for a receiver on a method call or a field access.
 * 
 * @author Katsuhisa Maruyama
 */
public class CFGReceiver extends CFGStatement {
    
    /**
     * The name of an instance specified by this receiver node.
     */
    private String name = "";
    
    /**
     * A method call node having this receiver.
     */
    private CFGMethodCall methodCall = null;
    
    /**
     * Creates a new object that represents a receiver on a method call.
     * This method is not intended to be invoked by clients.
     * @param node the AST node correspond to this node
     * @param kind the kind of this node
     */
    public CFGReceiver(ASTNode node, CFGNode.Kind kind) {
        super(node, kind);
    }
    
    /**
     * Sets the name of an instance specified by this receiver node.
     * This method is not intended to be invoked by clients.
     * @param name the instance name to be set
     */
    public void setName(String name) {
        this.name = name;
    }
    
    /**
     * Returns the name of an instance specified by this receiver node.
     * @return the instance name of this receiver
     */
    public String getName() {
        return name;
    }
    
    /**
     * Sets a method call node having this receiver.
     * @param methodCall the method call node
     */
    public void setMethodCall(CFGMethodCall methodCall) {
        this.methodCall = methodCall;
    }
    
    /**
     * Returns a method call node having this receiver.
     * @return the the method call node
     */
    public CFGMethodCall getMethodCall() {
        return methodCall;
    }
    
    /**
     * Returns the explicit type of of the receiver.
     * @return the receiver type, or {@code null} if the receiver does not have an explicit type
     */
    public String explicitType() {
        String type = stringLiteral();
        if (type != null) {
            return type;
        }
        type = typeLiteral();
        if (type != null) {
            return type;
        }
        return castType();
    }
    
    /**
     * Returns the type of the receiver.
     * @return the receiver type ({@code java.lang.String}), or {@code null} if the receiver is not a string literal
     */
    public String stringLiteral() {
        if (astNode instanceof StringLiteral) {
            return "java.lang.String";
        }
        return null;
    }
    
    /**
     * Returns the type of the receiver.
     * @return the receiver type ({@code java.lang.Class}), or {@code null} if the receiver is not a type literal
     */
    public String typeLiteral() {
        if (astNode instanceof TypeLiteral) {
            return "java.lang.Class";
            // TypeLiteral type = (TypeLiteral)astNode;
            // return type.resolveTypeBinding().getErasure().getQualifiedName();
        }
        return null;
    }
    
    /**
     * Returns the type of the receiver.
     * @return the cast receiver type, or {@code null} if the receiver is not cast
     */
    public String castType() {
        if (astNode instanceof ParenthesizedExpression) {
            ParenthesizedExpression paren = (ParenthesizedExpression)astNode;
            if (paren.getExpression() instanceof CastExpression) {
                return paren.getExpression().resolveTypeBinding().getErasure().getQualifiedName();
            }
        }
        return null;
    }
}
