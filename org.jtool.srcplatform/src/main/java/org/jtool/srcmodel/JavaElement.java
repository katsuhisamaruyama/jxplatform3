/*
 *  Copyright 2020
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
     * The AST node corresponding to this model element.
     */
    protected ASTNode astNode;
    
    /**
     * The file that declares this model element.
     */
    protected JavaFile jfile;
    
    /**
     * The information on a code fragment for this model element.
     */
    protected CodeRange codeRange;
    
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
        return jfile;
    }
    
    /**
     * Returns the project that this model element exists in.
     * @return the project of this model element
     */
    public JavaProject getJavaProject() {
        return jfile.getProject();
    }
    
    /**
     * Creates a new object of this model element.
     * This constructor is intended to be invoked by subclasses when creation their objects.
     * @param node the AST node corresponding to this model element
     * @param jfile the file that declares this model element
     */
    protected JavaElement(ASTNode node, JavaFile jfile) {
        this.astNode = node;
        this.jfile = jfile;
        if (node != null) {
            codeRange = new CodeRange(node);
        } else {
            codeRange = null;
        }
    }
    
    /**
     * Returns the range information on a code fragment for an AST node of this model element.
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
            StringBuilder buf = new StringBuilder(jfile.getCode());
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
            StringBuffer buf = new StringBuffer(jfile.getCode());
            return buf.substring(codeRange.getExtendedStartPosition(), codeRange.getExtendedEndPosition() + 1);
        }
        return "";
    }
    
    /**
     * Retrieves the fully-qualified name of a class from a given type binding information.
     * @param tbinding the type binding information on the class
     * @return the fully-qualified name
     */
    protected static QualifiedName retrieveQualifiedName(ITypeBinding tbinding) {
        if (tbinding.isTypeVariable()) {
            return new QualifiedName("java.lang.Object");
        }
        
        String qname = tbinding.getQualifiedName();
        if (qname.length() != 0) {
            return new QualifiedName(qname);
        }
        qname = tbinding.getBinaryName();
        if (qname.length() != 0) {
            return new QualifiedName(qname);
        }
        
        ITypeBinding tb = tbinding.getDeclaringClass();
        while (tb != null && tb.getQualifiedName().length() == 0) {
            tb = tb.getDeclaringClass();
        }
        if (tb == null) {
            return new QualifiedName();
        }
        
        qname = tb.getQualifiedName();
        String key = tbinding.getKey();
        int index = key.indexOf('$');
        if (index != -1) {
            key = key.substring(index, key.length() - 1);
        } else {
            key = "$";
        }
        return new QualifiedName(qname + key);
    }
    
    /**
     * Finds a class existing a given project by using the type binding information.
     * @param jproject the project that the class might exist
     * @param tbinding the type binding information on the class
     * @return the found class, or {@code null} if no class is found
     */
    public static JavaClass findDeclaringClass(JavaProject jproject, ITypeBinding tbinding) {
        if (tbinding != null) {
            tbinding = tbinding.getTypeDeclaration();
            QualifiedName qname = retrieveQualifiedName(tbinding);
            
            if (qname.isResolve()) {
                if (tbinding.isFromSource()) {
                    return jproject.getClass(qname.fqn());
                } else {
                    JavaClass jclass = jproject.getExternalClass(qname.fqn());
                    if (jclass == null) {
                        jclass = new JavaClass(tbinding, false);
                        jproject.addExternalClass(jclass);
                    }
                    return jclass;
                }
            }
        }
        return null;
    }
    
    /**
     * Finds a method existing a given project by using the method binding information.
     * @param jproject the project that the method might exist
     * @param mbinding the method binding information on the method
     * @return the found method, or {@code null} if no method is found
     */
    public static JavaMethod findDeclaringMethod(JavaProject jproject, IMethodBinding mbinding) {
        if (mbinding != null) { 
            mbinding = mbinding.getMethodDeclaration();
            JavaClass jclass = findDeclaringClass(jproject, mbinding.getDeclaringClass());
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
            }
        }
        return null;
    }
    
    /**
     * Finds a field existing a given project by using the variable binding information.
     * @param jproject the project that the field might exist
     * @param vbinding the variable binding information on the field
     * @return the found field, or {@code null} if no field is found
     */
    public static JavaField findDeclaringField(JavaProject jproject, IVariableBinding vbinding) {
        if (vbinding != null && vbinding.isField()) {
            vbinding = vbinding.getVariableDeclaration();
            JavaClass jclass = findDeclaringClass(jproject, vbinding.getDeclaringClass());
            if (jclass != null) {
                if (jclass.isInProject()) {
                    return jclass.getField(vbinding.getName());
                    
                } else {
                    JavaField jfield = jclass.getField(vbinding.getName());
                    if (jfield == null) {
                        jfield = new JavaField(vbinding, jclass, false);
                    }
                    return jfield;
                }
            } else {
                jclass = getArrayClass(jproject);
                JavaField jfield = jclass.getField(vbinding.getName());
                if (jfield == null) {
                    jfield = new JavaField(vbinding, jclass, false);
                }
                return jfield;
            }
        }
        return null;
    }
    
    /**
     * Creates a special class that represents an array.
     * @param jproject the project that the array class might exist
     * @return the special array class
     */
    private static JavaClass getArrayClass(JavaProject jproject) {
        QualifiedName qname = JavaClass.ArrayClassFqn;
        JavaClass jclass = jproject.getExternalClass(qname.fqn());
        if (jclass == null) {
            jclass = new JavaClass(qname.fqn(), false);
            jproject.addExternalClass(jclass);
        }
        return jclass;
    }
    
    /**
     * Finds an ancestor of an AST node for this model element, having a specified sort.
     * @param node the base AST node
     * @param sort the value representing the sort
     * (e.g., {@code ASTNode.TYPE_DECLARATION} for the type declaration)
     * @return the found ancestor, or {@code null} if no AST node is found
     */
    public static ASTNode findAncestorWith(ASTNode node, int sort) {
        if (node.getNodeType() == sort) {
            return node;
        }
        ASTNode parent = node.getParent();
        if (parent != null) {
            return findAncestorWith(parent, sort);
        }
        return null;
    }
    
    /**
     * Finds a class that enclosing a given model element within a given project.
     * @param jproject the project that the model element exists
     * @param node the enclosed model element
     * @return the found class, or {@code null} if there is no class enclosing the model element
     */
    public static JavaClass findEnclosingClass(JavaProject jproject, ASTNode node) {
        TypeDeclaration tnode = (TypeDeclaration)findAncestorWith(node, ASTNode.TYPE_DECLARATION);
        if (tnode != null) {
            return findDeclaringClass(jproject, tnode.resolveBinding());
        }
        EnumDeclaration enode = (EnumDeclaration)findAncestorWith(node, ASTNode.ENUM_DECLARATION);
        if (enode != null) {
            return findDeclaringClass(jproject, enode.resolveBinding());
        }
        return null;
    }
    
    /**
     * Finds a method that enclosing a given model element within a given project.
     * @param jproject the project that the model element exists
     * @param node the enclosed model element
     * @return the found method, or {@code null} if there is no method enclosing the model element
     */
    public static JavaMethod findEnclosingMethod(JavaProject jproject, ASTNode node) {
        MethodDeclaration mnode = (MethodDeclaration)findAncestorWith(node, ASTNode.METHOD_DECLARATION);
        if (mnode != null) {
            return findDeclaringMethod(jproject, mnode.resolveBinding());
        }
        Initializer inode = (Initializer)findAncestorWith(node, ASTNode.INITIALIZER);
        if (inode != null) {
            JavaClass jc = findEnclosingClass(jproject, inode);
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
    protected static boolean isVoid(String type) {
        return type.equals("void");
    }
    
    /**
     * Tests if the string indicates the primitive type.
     * @param type the type string
     * @return {@code true} if the string indicates the primitive type, otherwise {@code false}
     */
    protected static boolean isPrimitiveType(String type) {
        return type.equals("byte") || type.equals("short") || type.equals("int") || type.equals("long") ||
               type.equals("float") || type.equals("double") || type.equals("char") || type.equals("boolean");
    }
}
