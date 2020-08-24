/*
 *  Copyright 2020
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.cfg;

import org.jtool.srcmodel.CodeRange;
import org.jtool.srcmodel.JavaMethod;
import org.jtool.srcmodel.QualifiedName;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.EnumDeclaration;
import org.eclipse.jdt.core.dom.ITypeBinding;
import org.eclipse.jdt.core.dom.IVariableBinding;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.Modifier;
import org.eclipse.jdt.core.dom.IMethodBinding;
import org.eclipse.jdt.core.dom.TypeDeclaration;

/**
 * An abstract class that represents a reference to a field, a local variable, or a method.
 * 
 * @author Katsuhisa Maruyama
 */
public abstract class JReference {
    
    /**
     * The AST node corresponding to this reference.
     */
    protected ASTNode astNode;
    
    /**
     * The name of a class that encloses the referencing element.
     */
    protected String enclosingClassName;
    
    /**
     * The name of a method that encloses the referencing element.
     */
    protected String enclosingMethodName;
    
    /**
     * The name of a class that declares the referenced element.
     */
    protected String declaringClassName;
    
    /**
     * The name of a method that declares the referenced element.
     */
    protected String declaringMethodName;
    
    /**
     * The fully-qualified name of the referenced name.
     */
    protected QualifiedName fqn;
    
    /**
     * The form of this reference, e.g., {@code a}, {@code x.b} or {@code y.m()}
     */
    protected String referenceForm;
    
    /**
     * the type of the referenced element.
     */
    protected String type;
    
    /**
     * A flag that indicates whether the type of the referenced element is primitive.
     */
    protected boolean isPrimitiveType;
    
    /**
     * The modifier information of the referenced element.
     */
    protected int modifiers;
    
    /**
     * A flag that indicates whether the referenced element exists inside the target project.
     */
    protected boolean inProject;
    
    /**
     * The code range information of the AST node for this reference.
     */
    protected CodeRange codeRange = null;
    
    /**
     * Creates a new object that represents a reference
     * @param node AST node corresponding to this reference
     */
    public JReference(ASTNode node) {
        astNode = node;
    }
    
    /**
     * Returns the AST node corresponding to this reference.
     * @return the AST node
     */
    public ASTNode getASTNode() {
        return astNode;
    }
    
    /**
     * Returns the name of a class that encloses the referencing element.
     * @return the class name for the referencing element
     */
    public String getEnclosingClassName() {
        return enclosingClassName;
    }
    
    /**
     * Returns the name of a method that encloses the referencing element.
     * @return the method name for the referencing element
     */
    public String getEnclosingMethodName() {
        return enclosingMethodName;
    }
    
    /**
     * Returns the name of a class that declares the referenced element.
     * @return the class name for the referenced element
     */
    public String getDeclaringClassName() {
        return declaringClassName;
    }
    
    /**
     * Returns the name of a method that declares the referenced element.
     * @return the method name for the referenced element
     */
    public String getDeclaringMethodName() {
        return declaringMethodName;
    }
    
    /**
     * Returns the name of the referenced element, which is equal to its signature.
     * @return the name
     */
    public String getName() {
        return getSignature();
    }
    
    /**
     * Returns the signature of the referenced element.
     * @return the signature
     */
    public String getSignature() {
        return fqn.getMemberSignature();
    }
    
    /**
     * Returns the fully-qualified name of the referenced element.
     * @return the fully-qualified name
     */
    public QualifiedName getQualifiedName() {
        return fqn;
    }
    
    /**
     * Returns the reference name of the referenced element.
     * @return
     */
    public String getReferenceForm() {
        return referenceForm;
    }
    
    /**
     * Returns the receiver name of the referenced element.
     * @return the receiver name, or the empty string if the element does not have any receiver
     */
    public String getReceiverName() {
        int index = referenceForm.lastIndexOf(".");
        return index != -1 ? referenceForm.substring(0, index) : "";
    }
    
    /**
     * Returns the type of the referenced element.
     * @return
     */
    public String getType() {
        return type;
    }
    
    /**
     * Tests if the type of the referenced element is primitive.
     * @return {@code true} if the primitive type, otherwise {@code false}
     */
    public boolean isPrimitiveType() {
        return isPrimitiveType;
    }
    
    /**
     * Tests if the type of the referenced element is {@code void}.
     * @return {@code true} if the {@code void} type, otherwise {@code false}
     */
    public boolean isVoidType() {
        return type.equals("void");
    }
    
    /**
     * Returns the modifiers of the referenced element
     * @return the modifier information
     */
    public int getModifiers() {
        return modifiers;
    }
    
    /**
     * Tests if this is a reference to an element that has the public visibility.
     * @return {@code true} if this is a reference having the public visibility, otherwise {@code false}
     */
    public boolean isPublic() {
        return Modifier.isPublic(modifiers);
    }
    
    /**
     * Tests if this is a reference to an element that has the protected visibility.
     * @return {@code true} if this is a reference having the protected visibility, otherwise {@code false}
     */
    public boolean isProtected() {
        return Modifier.isProtected(modifiers);
    }
    
    /**
     * Tests if this is a reference to an element that has the default visibility.
     * @return {@code true} if this is a reference having the default visibility, otherwise {@code false}
     */
    public boolean isDefault() {
        return Modifier.isDefault(modifiers);
    }
    
    /**
     * Tests if this is a reference to an element that has the private visibility.
     * @return {@code true} if this is a reference having the private visibility, otherwise {@code false}
     */
    public boolean isPrivate() {
        return Modifier.isPrivate(modifiers);
    }
    
    /**
     * Tests if this is a reference to an element inside the target project.
     * @return {@code true} if this is a reference to the inside element, otherwise {@code false}
     */
    public boolean isInProject() {
        return inProject;
    }
    
    /**
     * Tests if this is a reference to a field.
     * @return default {@code false} that means that this is not a field reference
     */
    public boolean isFieldAccess() {
        return false;
    }
    
    /**
     * Tests if this is a reference to a local variable.
     * @return default {@code false} that means that this is not a local variable reference
     */
    public boolean isLocalAccess() {
        return false;
    }
    
    /**
     * Tests if this is a reference to a variable.
     * @return {@code true} if this is a reference to a variable, otherwise {@code false}
     */
    public boolean isVariableAccess() {
        return isFieldAccess() || isLocalAccess();
    }
    
    /**
     * Tests if this reference is visible.
     * @return default {@code true} that indicate a reference to an visible element.
     */
    public boolean isVisible() {
        return true;
    }
    
    /**
     * Tests if this is a reference to a method.
     * @return always {@code false} that means that this is not a method reference
     */
    public boolean isMethodCall() {
        return false;
    }
    
    /**
     * Obtains the code range information of the AST node for this reference.
     * @return the code range of this reference
     */
    public CodeRange getCodeRange() {
        if (codeRange == null) {
            codeRange = new CodeRange(astNode);
        }
        return codeRange;
    }
    
    /**
     * Returns the position that indicates where the code fragment for this reference begins.
     * @return the index value of the position on the source code
     */
    public int getStartPosition() {
        return astNode.getStartPosition();
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object obj) {
        return (obj instanceof JReference) ? equals((JReference)obj) : false;
    }
    
    /**
     * Tests if a given reference is equal to this reference.
     * @param node the reference to be checked
     * @return the {@code true} if the given reference is equal to this reference
     */
    public boolean equals(JReference jvar) {
        return jvar != null && (this == jvar || referenceForm.equals(jvar.referenceForm));
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return fqn.hashCode();
    }
    
    /**
     * Tests if a given method binding indicates a reference to a field.
     * @param vbinding the variable biding information to be checked
     * @return {@code true} if the method binding indicates a reference to a field, otherwise {@code false}
     */
    protected static boolean isField(IVariableBinding vbinding) {
        return vbinding != null && vbinding.isField() && !vbinding.isEnumConstant();
    }
    
    /**
     * Tests if a given method binding indicates a reference to an enum contant.
     * @param vbinding the variable biding information to be checked
     * @return {@code true} if the method binding indicates a reference to an enum contant, otherwise {@code false}
     */
    protected static boolean isEnumConstant(IVariableBinding vbinding) {
        return vbinding != null && vbinding.isEnumConstant();
    }
    
    /**
     * Tests if a given method binding indicates a reference to a local variable.
     * @param vbinding the variable biding information to be checked
     * @return {@code true} if the method binding indicates a reference to a local variable, otherwise {@code false}
     */
    protected static boolean isLocal(IVariableBinding vbinding) {
        return vbinding != null && !vbinding.isField();
    }
    
    /**
     * Tests if a given method binding indicates a reference to a method.
     * @param mbinding the method binding information to be checked
     * @return {@code true} if the method binding indicates a reference to a method, otherwise {@code false}
     */
    protected static boolean isMethod(IMethodBinding mbinding) {
        return mbinding != null && !mbinding.isConstructor() && !mbinding.isDefaultConstructor();
    }
    
    /**
     * Tests a given method binding indicates a reference to a constructor.
     * @param mbinding the method binding information to be checked
     * @return {@code true} if the method binding indicates a reference to a constructor, otherwise {@code false}
     */
    protected static boolean isConstructor(IMethodBinding mbinding) {
        return mbinding != null && (mbinding.isConstructor() || mbinding.isDefaultConstructor());
    }
    
    /**
     * Obtains the qualified name of a class enclosing a given AST node.
     * @param node the node of interest
     * @return the qualified name, or the empty string if no class is found
     */
    protected static String findEnclosingClassName(ASTNode node) {
        return getQualifiedClassName(findEnclosingClass(node));
    }
    
    /**
     * Obtains the qualified name of a method enclosing a given AST node.
     * @param node the node of interest
     * @return the qualified name, or the empty string if no method is found
     */
    protected static String findEnclosingMethodName(ASTNode node) {
        return getQualifiedMethodName(findEnclosingMethod(node));
    }
    
    /**
     * Returns the qualified name of a class related to a given type binding information.
     * @param tbinding the type biding information on the class
     * @return the qualified name, or the empty string if no class is found
     */
    protected static String getQualifiedClassName(ITypeBinding tbinding) {
        if (tbinding == null) {
            return "";
        }
        
        String qname = tbinding.getQualifiedName();
        if (qname.length() != 0) {
            return qname;
        }
        qname = tbinding.getBinaryName();
        if (qname != null && qname.length() != 0) {
            return qname;
        }
        
        ITypeBinding tb = tbinding.getDeclaringClass();
        while (tb != null && tb.getQualifiedName().length() == 0) {
            tb = tb.getDeclaringClass();
        }
        if (tb == null) {
            return "";
        }
        qname = tb.getQualifiedName();
        String key = tbinding.getKey();
        int index = key.indexOf('$');
        if (index != -1) {
            key = key.substring(index, key.length() - 1);
        } else {
            key = "$";
        }
        String fqn = qname + key;
        return fqn;
    }
    
    /**
     * Returns the qualified name of a method related to a given method binding information.
     * @param mbinding the method binding information on the method
     * @return the qualified name, or the empty string if no method is found
     */
    protected static String getQualifiedMethodName(IMethodBinding mbinding) {
        return mbinding != null ? getSignature(mbinding) : "";
    }
    
    /**
     * Returns the signature of a method related to a given method binding information.
     * @param mbinding the method binding information on the method
     * @return the signature string
     */
    protected static String getSignature(IMethodBinding mbinding) {
        return JavaMethod.getSignature(mbinding);
    }
    
    /**
     * Finds an ancestor of a given AST node, having a specified sort.
     * @param node the node of interest
     * @param sort the value representing the sort
     * @return the found node, or {@code null} if no node is found
     */
    protected static ASTNode getEnclosingElement(ASTNode node, int sort) {
        if (node.getNodeType() == sort) {
            return node;
        }
        ASTNode parent = node.getParent();
        if (parent != null) {
            return getEnclosingElement(parent, sort);
        }
        return null;
    }
    
    /**
     * Finds a class enclosing a given AST node.
     * @param node the node of interest
     * @return the type binding information on the found class, or {@code null} if no class is found
     */
    public static ITypeBinding findEnclosingClass(ASTNode node) {
        TypeDeclaration tnode = (TypeDeclaration)getEnclosingElement(node, ASTNode.TYPE_DECLARATION);
        if (tnode != null) {
            return tnode.resolveBinding();
        }
        EnumDeclaration enode = (EnumDeclaration)getEnclosingElement(node, ASTNode.ENUM_DECLARATION);
        if (enode != null) {
            return enode.resolveBinding();
        }
        return null;
    }
    
    /**
     * Finds a method enclosing a given AST node.
     * @param node the node of interest
     * @return the method binding information on the found method, or {@code null} if no method is found
     */
    public static IMethodBinding findEnclosingMethod(ASTNode node) {
        MethodDeclaration mnode = (MethodDeclaration)getEnclosingElement(node, ASTNode.METHOD_DECLARATION);
        if (mnode != null) {
            return mnode.resolveBinding();
        }
        return null;
    }
}
