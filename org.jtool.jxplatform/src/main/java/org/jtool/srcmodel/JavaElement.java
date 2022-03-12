/*
 *  Copyright 2022
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.srcmodel;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.eclipse.jdt.core.dom.EnumDeclaration;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.Initializer;
import org.eclipse.jdt.core.dom.IMethodBinding;
import org.eclipse.jdt.core.dom.IVariableBinding;
import org.eclipse.jdt.core.dom.ITypeBinding;

/**
 * A root object for a Java model element.
 * 
 * @author Katsuhisa Maruyama
 */
public abstract class JavaElement {
    
    /**
     * An AST node corresponding to this model element.
     */
    protected ASTNode astNode;
    
    /**
     * Information on a code fragment for this model element.
     */
    protected CodeRange codeRange;
    
    /**
     * A class that encloses the declaration of this this model element.
     */
    protected JavaClass declaringClass;
    
    /**
     * Returns the fully qualified name of this model element.
     * @return the fully qualified name
     */
    public abstract QualifiedName getQualifiedName();
    
    /**
     * Returns the AST node corresponding to this model element.
     * @return the corresponding AST node
     */
    public ASTNode getASTNode() {
        return astNode;
    }
    
    /**
     * Returns the file that declares this model element.
     * @return the declaring file
     */
    public JavaFile getFile() {
        return declaringClass.getFile();
    }
    
    /**
     * Returns the class that encloses the declaration of this model element.
     * @return the declaring class
     */
    public JavaClass getDeclaringClass() {
        return declaringClass;
    }
    
    /**
     * Returns the project that this model element exists in.
     * @return the project of this model element
     */
    public JavaProject getJavaProject() {
        return declaringClass.getFile().getProject();
    }
    
    /**
     * Creates a new object of this model element.
     * This constructor is intended to be invoked by subclasses when creation their objects.
     * @param node an AST node corresponding to this model element
     * @param jclass a class that encloses the declaration of this this model element
     */
    protected JavaElement(ASTNode node, JavaClass jclass) {
        this.astNode = node;
        this.declaringClass = jclass;
        if (node != null) {
            codeRange = new CodeRange(node);
        } else {
            codeRange = null;
        }
    }
    
    /**
     * Returns the range information on a code fragment of this model element.
     * @return the code range information on this model element
     */
    public CodeRange getCodeRange() {
        return codeRange;
    }
    
    /**
     * Obtains the source code corresponding to a code fragment of this model element.
     * @return the source code of this model element, or the empty string if there is no source code
     */
    public String getSource() {
        if (codeRange != null && codeRange.getCodeLength() > 0) {
            StringBuilder buf = new StringBuilder(declaringClass.getFile().getSource());
            return buf.substring(codeRange.getStartPosition(), codeRange.getEndPosition() + 1);
        }
        return "";
    }
    
    /**
     * Obtains the source code corresponding to a code fragment of this model element,
     * including comments and whitespace immediately exist before or after.
     * @return the source code of this model element, or the empty string if there is no source code
     */
    public String getExtendedSource() {
        if (codeRange != null && codeRange.getCodeLength() > 0) {
            StringBuffer buf = new StringBuffer(declaringClass.getFile().getSource());
            return buf.substring(codeRange.getExtendedStartPosition(), codeRange.getExtendedEndPosition() + 1);
        }
        return "";
    }
    
    /**
     * Finds the fully-qualified name of a class having given type binding information.
     * @param tbinding the type binding information on the class
     * @return the fully-qualified name
     */
    protected static QualifiedName findQualifiedName(ITypeBinding tbinding) {
        if (tbinding.isTypeVariable()) {
            return new QualifiedName("java.lang.Object", "");
        }
        
        String className = tbinding.getQualifiedName();
        if (className.length() != 0) {
            return new QualifiedName(className, "");
        }
        
        className = tbinding.getBinaryName();
        if (className.length() != 0) {
            return new QualifiedName(className, "");
        }
        
        ITypeBinding tb = tbinding.getDeclaringClass();
        while (tb != null && tb.getQualifiedName().length() == 0) {
            tb = tb.getDeclaringClass();
        }
        if (tb == null) {
            return new QualifiedName();
        }
        
        className = tb.getQualifiedName();
        String key = tbinding.getKey();
        int index = key.indexOf('$');
        if (index != -1) {
            key = key.substring(index, key.length() - 1);
        } else {
            key = "$";
        }
        return new QualifiedName(className + key, "");
    }
    
    /**
     * Finds a class having given type binding information within a given project.
     * @param tbinding the type binding information on the class
     * @param jproject the project that the class might exist
     * @return the found class, or {@code null} if no class is found
     */
    public static JavaClass findDeclaringClass(ITypeBinding tbinding, JavaProject jproject) {
        if (tbinding != null) {
            tbinding = tbinding.getTypeDeclaration();
            QualifiedName qname = findQualifiedName(tbinding);
            
            if (qname.isResolve()) {
                if (tbinding.isFromSource()) {
                    return jproject.getClass(qname.fqn());
                } else {
                    JavaClass jclass = jproject.getExternalClass(qname.fqn());
                    if (jclass == null) {
                        jclass = new JavaClass(tbinding, false);
                        jproject.addExternalClass(jclass);
                        
                        for (IMethodBinding mbinding : tbinding.getDeclaredMethods()) {
                            JavaMethod jmethod = new JavaMethod(mbinding, jclass, false);
                            jclass.addMethod(jmethod);
                        }
                        
                        for (IVariableBinding vbinding : tbinding.getDeclaredFields()) {
                            if (vbinding.isField()) {
                                JavaField jfield = new JavaField(vbinding, jclass);
                                jclass.addField(jfield);
                            }
                        }
                    }
                    return jclass;
                }
            }
        }
        return null;
    }
    
    /**
     * Finds a method having given method binding information within a given project.
     * @param mbinding the method binding information on the method
     * @param jproject the project that the method might exist
     * @return the found method, or {@code null} if no method is found
     */
    public static JavaMethod findDeclaringMethod(IMethodBinding mbinding, JavaProject jproject) {
        if (mbinding != null) { 
            mbinding = mbinding.getMethodDeclaration();
            JavaClass jclass = findDeclaringClass(mbinding.getDeclaringClass(), jproject);
            if (jclass != null) {
                if (jclass.isInProject()) {
                    return jclass.getMethod(JavaMethod.getSignature(mbinding));
                    
                } else {
                    JavaMethod jmethod = jclass.getMethod(JavaMethod.getSignature(mbinding));
                    if (jmethod == null) {
                        jmethod = new JavaMethod(mbinding, jclass, false);
                    }
                    return jmethod;
                }
            } else {
                jclass = JavaClass.getArrayClass(jproject);
                JavaMethod jmethod = jclass.getMethod(mbinding.getName());
                if (jmethod == null) {
                    jmethod = new JavaMethod(mbinding, jclass, false);
                }
                return jmethod;
            }
        }
        return null;
    }
    
    /**
     * Finds a having given variable binding information within a given project.
     * @param vbinding the variable binding information on the field
     * @param jproject the project that the field might exist
     * @return the found field, or {@code null} if no field is found
     */
    public static JavaField findDeclaringField(IVariableBinding vbinding, JavaProject jproject) {
        if (vbinding != null && vbinding.isField()) {
            vbinding = vbinding.getVariableDeclaration();
            JavaClass jclass = findDeclaringClass(vbinding.getDeclaringClass(), jproject);
            if (jclass != null) {
                if (jclass.isInProject()) {
                    return jclass.getField(vbinding.getName());
                    
                } else {
                    JavaField jfield = jclass.getField(vbinding.getName());
                    if (jfield == null) {
                        jfield = new JavaField(vbinding, jclass);
                        jclass.addField(jfield);
                    }
                    return jfield;
                }
            } else {
                jclass = JavaClass.getArrayClass(jproject);
                JavaField jfield = jclass.getField(vbinding.getName());
                if (jfield == null) {
                    jfield = new JavaField(vbinding, jclass);
                    jclass.addField(jfield);
                }
                return jfield;
            }
        }
        return null;
    }
    
    /**
     * Finds an ancestor of an AST node for this model element, having a specified sort.
     * @param node the base AST node
     * @param sort the value representing the sort
     *        (e.g., {@code ASTNode.TYPE_DECLARATION} for the type declaration)
     * @return the found ancestor, or {@code null} if no AST node is found
     */
    public static ASTNode findAncestorNode(ASTNode node, int sort) {
        if (node.getNodeType() == sort) {
            return node;
        }
        ASTNode parent = node.getParent();
        if (parent != null) {
            return findAncestorNode(parent, sort);
        }
        return null;
    }
    
    /**
     * Finds a class that encloses a given model element within a given project.
     * @param node the enclosed model element
     * @param jproject the project that the model element exists
     * @return the found class, or {@code null} if there is no class enclosing the model element
     */
    public static JavaClass findEnclosingClass(ASTNode node, JavaProject jproject) {
        TypeDeclaration tnode = (TypeDeclaration)findAncestorNode(node, ASTNode.TYPE_DECLARATION);
        if (tnode != null) {
            return findDeclaringClass(tnode.resolveBinding(), jproject);
        }
        EnumDeclaration enode = (EnumDeclaration)findAncestorNode(node, ASTNode.ENUM_DECLARATION);
        if (enode != null) {
            return findDeclaringClass(enode.resolveBinding(), jproject);
        }
        return null;
    }
    
    /**
     * Finds a method that encloses a given model element within a given project.
     * @param node the enclosed model element
     * @param jproject the project that the model element exists
     * @return the found method, or {@code null} if there is no method enclosing the model element
     */
    public static JavaMethod findEnclosingMethod(ASTNode node, JavaProject jproject) {
        MethodDeclaration mnode = (MethodDeclaration)findAncestorNode(node, ASTNode.METHOD_DECLARATION);
        if (mnode != null) {
            return findDeclaringMethod(mnode.resolveBinding(), jproject);
        }
        Initializer inode = (Initializer)findAncestorNode(node, ASTNode.INITIALIZER);
        if (inode != null) {
            JavaClass jc = findEnclosingClass(inode, jproject);
            if (jc != null) {
                return jc.getInitializer();
            }
        }
        return null;
    }
    
    /**
     * Tests if the string indicates the {@code void} type.
     * @param type the type string
     * @return {@code true} if the string indicates the {@code void} type, otherwise {@code false}
     */
    public static boolean isVoid(String type) {
        return type.equals("void");
    }
    
    /**
     * Tests if the string indicates the primitive type.
     * @param type the type string
     * @return {@code true} if the string indicates the primitive type, otherwise {@code false}
     */
    public static boolean isPrimitiveType(String type) {
        return type.equals("byte") || type.equals("short") || type.equals("int") || type.equals("long") ||
               type.equals("float") || type.equals("double") || type.equals("char") || type.equals("boolean");
    }
}
