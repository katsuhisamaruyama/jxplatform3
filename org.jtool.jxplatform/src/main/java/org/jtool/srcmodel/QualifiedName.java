/*
 *  Copyright 2020
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.srcmodel;

/**
 * Stores fully-qualified name of an element.
 * 
 * @author Katsuhisa Maruyama
 */
public class QualifiedName {
    
    /**
     * The constant string that delimits a class name and a member name in the fully qualified name.
     */
    public static final String QualifiedNameSeparator = "#";
    
    /**
     * The name of the class. An anonymous class has a name that starts with "$".
     */
    private final String className;
    
    /**
     * The signature of the member.
     */
    private final String memberSignature;
    
    /**
     * Creates a new object that represents a fully-qualified name for a method or a field.
     * @param className the name of the class
     * @param memberSignature the signature of the member
     */
    public QualifiedName(String className, String memberSignature) {
        this.className = className;
        this.memberSignature = memberSignature;
    }
    
    /**
     * Creates a new object that represents a fully-qualified name for a class.
     * @param fqn the fully qualified name ({@code class#member})
     */
    public QualifiedName(String fqn) {
        int index = fqn.indexOf(QualifiedNameSeparator);
        if (index == -1) {
            this.className = fqn;
            this.memberSignature = "";
        } else {
            this.className = fqn.substring(0, index);
            this.memberSignature = fqn.substring(index + 1);
        }
    }
    
    /**
     * Creates a new object that represents an unresolved fully-qualified name.
     */
    public QualifiedName() {
        this("", "");
    }
    
    /**
     * Creates a new object that represents a fully-qualified name for a method or a field.
     * @param qname the fully-qualified name of the class
     * @param memberSignature the signature of the member
     */
    public QualifiedName(QualifiedName qname, String memberSignature) {
        this(qname.className, memberSignature);
    }
    
    /**
     * Creates a new object that represents a fully-qualified name for a method or a field.
     * @param qname the fully-qualified name of the class
     */
    public QualifiedName(QualifiedName qname) {
        this(qname.className, qname.memberSignature);
    }
    
    /**
     * Returns the name of the class.
     * @return the class name
     */
    public String getClassName() {
        return className;
    }
    
    /**
     * Returns the signature of the member.
     * @return the member signature
     */
    public String getMemberSignature() {
        return memberSignature;
    }
    
    /**
     * Returns the reference form of this fully qualified name
     * @return the reference string
     */
    public String fqn() {
        if (className.length() > 0) {
            return memberSignature.length() > 0 ?
                    className + QualifiedName.QualifiedNameSeparator + memberSignature : className;
        } else {
            return memberSignature.length() > 0 ?
                    memberSignature : ".UNRESOLVED";
        }
    }
    
    /**
     * Tests if this fully qualified name is unresolved.
     * @return {@code true} if this fully-qualified name is resolved, otherwise {@code false}
     */
    public boolean isResolve() {
        return className.length() > 0;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object obj) {
        return (obj instanceof QualifiedName) ? equals((QualifiedName)obj) : false;
    }
    
    /**
     * Tests if a given fully-qualified name is equal to this fully-qualified name.
     * @param qname the fully-qualified name to be checked
     * @return {@code true} if the given fully-qualified is equal to this fully-qualified
     */
    public boolean equals(QualifiedName qname) {
        return qname != null && (this == qname || fqn().equals(qname.fqn()));
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return fqn().hashCode();
    }
    
    /**
     * Obtains information on this fully-qualified name.
     * @return the string representing of the fully-qualified name
     */
    public String toString() {
        return fqn();
    }
}
