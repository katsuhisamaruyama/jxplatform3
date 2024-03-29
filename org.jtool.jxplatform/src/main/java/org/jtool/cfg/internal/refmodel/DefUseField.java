/*
 *  Copyright 2022-2023
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.cfg.internal.refmodel;

import org.jtool.cfg.JFieldReference;
import org.jtool.cfg.CFGStatement;
import org.jtool.cfg.CFGMethodCall;
import org.eclipse.jdt.core.dom.Modifier;
import java.util.List;
import java.util.Set;
import java.util.HashSet;

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
    private boolean inProject;
    private boolean isThis;
    private boolean isUncovered;
    private boolean isReturnValue;
    private Set<CFGStatement> holdingNodes = new HashSet<>();
    
    public DefUseField(JFieldReference fvar, CFGStatement node) {
        this(fvar.getDeclaringClassName(), fvar.getName(), fvar.getReferenceForm(),
                fvar.getType(), fvar.isPrimitiveType(), fvar.getModifiers(),
                fvar.isInProject(), fvar.isThis(),
                fvar.isUncoveredFieldReference(), fvar.isReturnValueReference());
        if (node != null) {
            holdingNodes.add(node);
        }
    }
    
    public DefUseField(DefUseField var) {
        this(var.className, var.name, var.referenceForm,
                var.type, var.isPrimitive, var.modifiers, var.inProject, var.isThis(),
                var.isUncovered, var.isReturnValue);
        var.getHoldingNodes().stream().filter(node -> node != null).forEach(node -> holdingNodes.add(node));
    }
    
    public DefUseField(String className, String name, String referenceForm, String type,
            boolean isPrimitive, int modifiers, boolean inProject, boolean isThis,
            boolean isUncovered, boolean isReturnValue) {
        this.className = className;
        this.name = name;
        this.referenceForm = referenceForm;
        this.type = type;
        this.isPrimitive = isPrimitive;
        this.modifiers = modifiers;
        this.inProject = inProject;
        this.isThis = isThis;
        this.isUncovered = isUncovered;
        this.isReturnValue = isReturnValue;
    }
    
    static DefUseField create(String str) {
        String[] s = str.split(FieldPropertySeparator);
        return new DefUseField(s[0], s[1], s[2], s[3],
                Boolean.parseBoolean(s[4]), Integer.parseInt(s[5]),
                Boolean.parseBoolean(s[6]), Boolean.parseBoolean(s[7]),
                Boolean.parseBoolean(s[8]), Boolean.parseBoolean(s[9]));
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
    
    public boolean isInProject() {
        return inProject;
    }
    
    public boolean isThis() {
        return isThis;
    }
    
    public boolean isUncovered() {
        return isUncovered;
    }
    
    public boolean isReturnValue() {
        return isReturnValue;
    }
    
    public void addHoldingNodes(List<CFGMethodCall> nodes) {
        nodes.stream().filter(node -> node != null).forEach(node -> holdingNodes.add(node));
    }
    
    public void addHoldingNodes(Set<? extends CFGStatement> nodes) {
        nodes.stream().filter(node -> node != null).forEach(node -> holdingNodes.add(node));
    }
    
    public Set<CFGStatement> getHoldingNodes() {
        return holdingNodes;
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
                FieldPropertySeparator + String.valueOf(inProject) +
                FieldPropertySeparator + String.valueOf(isThis) +
                FieldPropertySeparator + String.valueOf(isUncovered) +
                FieldPropertySeparator + String.valueOf(isReturnValue);
    }
    
    @Override
    public String toString() {
        return toStr();
    }
}
