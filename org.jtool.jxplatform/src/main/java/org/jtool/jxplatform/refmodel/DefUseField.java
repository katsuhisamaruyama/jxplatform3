/*
 *  Copyright 2022
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.jxplatform.refmodel;

import org.jtool.cfg.JFieldReference;
import org.eclipse.jdt.core.dom.Modifier;

/**
 * A class that represents a field defined or used.
 * 
 * @author Katsuhisa Maruyama
 */
public class DefUseField {
    
    final static String FieldPropertySeparator = "%";
    
    private String className;
    private String name;
    private String referenceForm;
    private String type;
    private boolean isPrimitive;
    private int modifiers;
    private boolean isInThis;
    
    public DefUseField(JFieldReference var) {
        this(var.getDeclaringClassName(), var.getName(), var.getReferenceForm(),
                var.getType(), var.isPrimitiveType(), var.getModifiers(), var.isThis());
    }
    
    public DefUseField(DefUseField var) {
        this(var.getClassName(), var.getName(), var.getReferenceForm(),
                var.getType(), var.isPrimitive(), var.getModifiers(), var.isInThis());
    }
    
    public DefUseField(String className, String name, String referenceForm, String type,
            boolean isPrimitive, int modifiers, boolean isInThis) {
        this.className = className;
        this.name = name;
        this.referenceForm = referenceForm;
        this.type = type;
        this.isPrimitive = isPrimitive;
        this.modifiers = modifiers;
        this.isInThis = isInThis;
    }
    
    static DefUseField create(String str) {
        String[] s = str.split(FieldPropertySeparator);
        return new DefUseField(s[0], s[1], s[2], s[3],
                Boolean.parseBoolean(s[4]), Integer.parseInt(s[5]), Boolean.parseBoolean(s[6]));
    }
    
    public String getQualifiedName() {
        return className + FieldPropertySeparator + name + FieldPropertySeparator + referenceForm;
    }
    
    public void updateClassName(String newClassName) {
        if (referenceForm.startsWith(className)) {
            referenceForm = newClassName + referenceForm.substring(className.length());
        }
        className = newClassName;
    }
    
    public String getClassName() {
        return className;
    }
    
    public String getName() {
        return name;
    }
    
    public void updateReferenceForm(String newReferenceForm) {
        referenceForm = newReferenceForm;
    }
    
    public String getReferenceForm() {
        return referenceForm;
    }
    
    public String getType() {
        return type;
    }
    
    public boolean isPrimitive() {
        return isPrimitive;
    }
    
    public int getModifiers() {
        return modifiers;
    }
    
    public boolean isInThis() {
        return isInThis;
    }
    
    public boolean isStatic() {
        return Modifier.isStatic(modifiers);
    }
    
    boolean equals(DefUseField field) {
        return field != null && (this == field || getQualifiedName().equals(field.getQualifiedName()));
    }
    
    @Override
    public boolean equals(Object obj) {
        return (obj instanceof DefUseField) ? equals((DefUseField)obj) : false;
    }
    
    @Override
    public int hashCode() {
        return getQualifiedName().hashCode();
    }
    
    public String toStr() {
        return getQualifiedName() +
                FieldPropertySeparator + type +
                FieldPropertySeparator + String.valueOf(isPrimitive) +
                FieldPropertySeparator + String.valueOf(modifiers) +
                FieldPropertySeparator + String.valueOf(isInThis);
    }
    
    @Override
    public String toString() {
        return toStr();
    }
}
