/*
 *  Copyright 2020
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.jxplatform.bytecode;

import org.jtool.cfg.JReference;

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
    private int modifier;
    
    public DefUseField(JReference var) {
        this(var.getDeclaringClassName(), var.getName(), var.getReferenceForm(),
                var.getType(), var.isPrimitiveType(), var.getModifiers());
    }
    
    public DefUseField(String className, String name, String referenceForm, String type,
            boolean isPrimitive, int modifier) {
        this.className = className;
        this.name = name;
        this.referenceForm = referenceForm;
        this.type = type;
        this.isPrimitive = isPrimitive;
        this.modifier = modifier;
    }
    
    static DefUseField create(String str) {
        String[] s = str.split(FieldPropertySeparator);
        return new DefUseField(s[0], s[1], s[2], s[3], Boolean.parseBoolean(s[4]), Integer.parseInt(s[5]));
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
    
    public String getReferenceForm() {
        return referenceForm;
    }
    
    public String getType() {
        return type;
    }
    
    public boolean isPrimitive() {
        return isPrimitive;
    }
    
    public int getModifier() {
        return modifier;
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
        return getQualifiedName() + FieldPropertySeparator + type + FieldPropertySeparator +
                String.valueOf(isPrimitive) + FieldPropertySeparator + String.valueOf(modifier);
    }
    
    @Override
    public String toString() {
        return toStr();
    }
}
