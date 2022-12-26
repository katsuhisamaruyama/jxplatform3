/*
 *  Copyright 2022
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.srcmodel;

import static org.jtool.jxplatform.builder.ModelBuilder.br;
import org.jtool.srcmodel.internal.FieldInitializerCollector;
import org.jtool.srcmodel.internal.MethodCallCollector;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.EnumConstantDeclaration;
import org.eclipse.jdt.core.dom.IVariableBinding;
import org.eclipse.jdt.core.dom.Modifier;
import org.eclipse.jdt.core.dom.VariableDeclaration;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;
import java.util.Set;
import java.util.HashSet;
import java.util.stream.Collectors;

/**
 * An object representing a field.
 * 
 * @author Katsuhisa Maruyama
 */
public class JavaField extends JavaVariable {
    
    /**
     * Variable binding information on this class.
     */
    private final IVariableBinding binding;
    
    /**
     * A class that declares (or encloses the declaration of) this field.
     */
    private final JavaClass declaringClass;
    
    /**
     * Creates a new object representing a field.
     * This constructor is not intended to be invoked by clients.
     * @param node the AST node for this field
     * @param jclass the class that declares this field
     * @throws JavaElementException the exception occurs when the creation of a new object fails
     */
    public JavaField(VariableDeclaration node, JavaClass jclass) throws JavaElementException {
        this(node, node.resolveBinding(), jclass);
    }
    
    /**
     * Creates a new object representing a field.
     * This constructor is not intended to be invoked by clients.
     * @param node the AST node for this field
     * @param jclass the class that declares this field
     * @throws JavaElementException the exception occurs when the creation of a new object fails
     */
    public JavaField(VariableDeclarationFragment node, JavaClass jclass) throws JavaElementException {
        this(node, node.resolveBinding(), jclass);
    }
    
    /**
     * Creates a new object representing a field.
     * This constructor is not intended to be invoked by clients.
     * @param node the AST node for this enum constant
     * @param jclass the class that declares this field
     * @throws JavaElementException the exception occurs when the creation of a new object fails
     */
    public JavaField(EnumConstantDeclaration node, JavaClass jclass) throws JavaElementException {
        this(node, node.resolveVariable(), jclass);
    }
    
    /**
     * Creates a new object representing a field.
     * @param node the AST node for this field
     * @param vbinding the variable binding of this field
     * @param jclass the class that declares this field
     * @throws JavaElementException the exception occurs when the creation of a new object fails
     */
    JavaField(ASTNode node, IVariableBinding vbinding, JavaClass jclass) throws JavaElementException {
        super(node, jclass.getFile());
        
        assert jclass != null;
        
        this.declaringClass = jclass;
        
        if (vbinding != null) {
            this.binding = vbinding.getVariableDeclaration();
            this.qname = new QualifiedName(jclass.getQualifiedName(), vbinding.getName());
            this.kind = getKind(binding);
            this.type = JavaElementUtil.findQualifiedName(binding.getType()).fqn();
            this.isPrimitive = vbinding.getType().isPrimitive();
            this.modifiers = vbinding.getModifiers();
            
            jclass.addField(this);
            
        } else {
            throw new JavaElementException("for field in class" + jclass.getClassName());
        }
    }
    
    /**
     * Creates a new object representing a field.
     * This constructor is not intended to be invoked by clients.
     * @param vbinding the variable binding information on this field
     * @param jclass the class that declares this field
     * @throws JavaElementException the exception occurs when the creation of a new object fails
     */
    public JavaField(IVariableBinding vbinding, JavaClass jclass) throws JavaElementException {
        this(null, vbinding, jclass);
    }
    
    /**
     * Returns the variable binding information on this field.
     * @return the variable binding information
     */
    public IVariableBinding getVariableBinding() {
        return binding;
    }
    
    /**
     * Tests if this field exists inside the target project, which can resent the corresponding source code.
     * @return {@code true} if this field exists inside the target project, otherwise {@code false}
     */
    public boolean isInProject() {
        return declaringClass.isInProject();
    }
    
    /**
     * Returns the class that declares this class.
     * @return the declaring class, or {@code null} if this class is not enclosed by any class
     */
    public JavaClass getDeclaringClass() {
        return declaringClass;
    }
    
    /**
     * Tests if this field has the public visibility.
     * @return {@code true} if this field has the public visibility, otherwise {@code false}
     */
    public boolean isPublic() {
        return Modifier.isPublic(modifiers);
    }
    
    /**
     * Tests if this field has the protected visibility.
     * @return {@code true} if this field has the protected visibility, otherwise {@code false}
     */
    public boolean isProtected() {
        return Modifier.isProtected(modifiers);
    }
    
    /**
     * Tests if this field has the private visibility.
     * @return {@code true} if this field has the private visibility, otherwise {@code false}
     */
    public boolean isPrivate() {
        return Modifier.isPrivate(modifiers);
    }
    
    /**
     * Tests if this field has the default visibility.
     * @return {@code true} if this field has the default visibility, otherwise {@code false}
     */
    public boolean isDefault() {
        return !isPublic() && !isProtected() && !isPrivate();
    }
    
    /**
     * Tests if this is a static field.
     * @return {@code true} if this is a static field, otherwise {@code false}
     */
    public boolean isStatic() {
        return Modifier.isStatic(modifiers);
    }
    
    /**
     * Tests if this is a volatile field.
     * @return {@code true} if this is a volatile field, otherwise {@code false}
     */
    public boolean isVolatile() {
        return Modifier.isVolatile(modifiers);
    }
    
    /**
     * Tests if this is a transient field.
     * @return {@code true} if this is a transient field, otherwise {@code false}
     */
    public boolean isTransient() {
        return Modifier.isTransient(modifiers);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object obj) {
        return (obj instanceof JavaField) ? equals((JavaField)obj) : false;
    }
    
    /**
     * Tests if a given field is equal to this field.
     * @param jfield the field to be checked
     * @return the {@code true} if the given field is equal to this field
     */
    public boolean equals(JavaField jfield) {
        return jfield != null && (this == jfield || qname.fqn().equals(jfield.qname.fqn()));
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return qname.fqn().hashCode();
    }
    
    /**
     * Obtains information on this field.
     * @return the string representing the information
     */
    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append(br);
        buf.append("FIELD: ");
        buf.append(getName());
        buf.append("@");
        buf.append(getType());
        return buf.toString();
    }
    
    /**
     * A flag indicating whether the collected binding information is resolved.
     */
    private boolean resolved = false;
    
    /**
     * The collection of methods that this field calls in its declaration.
     */
    private Set<JavaMethod> calledMethods = new HashSet<>();
    
    /**
     * The collection of methods that access this field in their bodies.
     */
    private Set<JavaMethod> accessingMethods = new HashSet<>();
    
    /**
     * The collection of fields that this field accesses in its declaration.
     */
    private Set<JavaField> accessedFields = new HashSet<>();
    
    /**
     * The collection of fields that access this field in their declarations.
     */
    private Set<JavaField> accessingFields = new HashSet<>();
    
    /**
     * Collects additional information on this field.
     * This method is not intended to be invoked by clients, which will be automatically invoked as needed.
     */
    void collectInfo() {
        if (resolved) {
            return;
        }
        
        boolean resolveOk = true;
        if (binding != null) {
            resolveOk = resolveOk && findCalledMethods();
            resolveOk = resolveOk && findAccessedFields();
        } else {
            resolveOk = false;
        }
        
        if (!resolveOk) {
            String message;
            if (declaringClass != null) {
                message = "Field " + getQualifiedName() + " of " + declaringClass.getQualifiedName()
                          + " in " + getFile().getPath();
            } else {
                message = "Field in " + getFile().getPath();
            }
            getJavaProject().getModelBuilderImpl().printUnresolvedError(message);
        }
        resolved = true;
    }
    
    /**
     * Finds methods accessed by this field.
     * @return {@code true} if all methods were successfully specified, otherwise {@code false}
     */
    private boolean findCalledMethods() {
        MethodCallCollector visitor = new MethodCallCollector(getJavaProject());
        astNode.accept(visitor);
        if (visitor.isBindingOk()) {
            calledMethods.addAll(visitor.getCalledMethods());
            for (JavaMethod jm : calledMethods) {
                jm.addAccessingField(this);
            }
            return true;
        }
        return false;
    }
    
    /**
     * Adds a method that accesses this field.
     * This method is not intended to be invoked by clients.
     * @param jmethod the method to be added
     */
    void addAccessingMethod(JavaMethod jmethod) {
        accessingMethods.add(jmethod);
    }
    
    /**
     * Finds fields accessed by this field.
     * @return {@code true} if all fields were successfully specified, otherwise {@code false}
     */
    private boolean findAccessedFields() {
        FieldInitializerCollector visitor = new FieldInitializerCollector(getJavaProject());
        astNode.accept(visitor);
        if (visitor.isBindingOk()) {
            accessedFields.addAll(visitor.getAccessedFields());
            for (JavaField jf : accessedFields) {
                jf.addAccessingField(this);
            }
            return true;
        }
        return false;
    }
    
    /**
     * Adds a field that accesses this field.
     * This method is not intended to be invoked by clients.
     * @param jfield the field to be added
     */
    void addAccessingField(JavaField jfield) {
        accessingFields.add(jfield);
    }
    
    /**
     * Returns fields that this field accesses in its declaration.
     * @return the collection of the accessed fields
     */
    public Set<JavaField> getAccessedFields() {
        collectInfo();
        return accessedFields;
    }
    
    /**
     * Returns fields that access this field in their declarations.
     * @return the collection of the accessing fields
     */
    public Set<JavaField> getAccessingFields() {
        collectInfo();
        return accessingFields;
    }
    
    /**
     * Returns fields that this field accesses in its declaration, which are enclosed in the target project.
     * @return the collection of the accessed fields
     */
    public Set<JavaField> getAccessedFieldsInProject() {
        collectInfo();
        return accessedFields
                .stream()
                .filter(jf -> jf.isInProject())
                .collect(Collectors.toSet());
    }
    
    /**
     * Returns fields that access this field in their declarations, which are enclosed in the target project.
     * @return the collection of the accessing fields
     */
    public Set<JavaField> getAccessingFieldsInProject() {
        collectInfo();
        return accessingFields.stream()
                .filter(jf -> jf.isInProject())
                .collect(Collectors.toSet());
    }
    
    /**
     * Returns methods that this field calls in its declaration.
     * @return the collection of the called methods
     */
    public Set<JavaMethod> getCalledMethods() {
        collectInfo();
        return calledMethods;
    }
    
    /**
     * Returns methods that access this field in its bodies.
     * @return the collection of the accessing methods
     */
    public Set<JavaMethod> getAccessingMethods() {
        collectInfo();
        return accessingMethods;
    }
    
    /**
     * Returns methods that this field calls in its declaration, which are enclosed in the target project.
     * @return the collection of the called methods
     */
    public Set<JavaMethod> getCalledMethodsInProject() {
        collectInfo();
        return calledMethods.stream()
                .filter(jm -> jm.isInProject())
                .collect(Collectors.toSet());
    }
    
    /**
     * Returns methods that access this field in its bodies, which are enclosed in the target project.
     * @return the collection of the accessing methods
     */
    public Set<JavaMethod> getAccessingMethodsInProject() {
        collectInfo();
        return accessingMethods.stream()
                .filter(jm -> jm.isInProject())
                .collect(Collectors.toSet());
    }
}
