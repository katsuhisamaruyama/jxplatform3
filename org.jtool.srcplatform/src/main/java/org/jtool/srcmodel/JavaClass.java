/*
 *  Copyright 2020
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.srcmodel;

import org.jtool.srcmodel.builder.TypeCollector;
import org.jtool.srcplatform.util.Logger;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.eclipse.jdt.core.dom.EnumDeclaration;
import org.eclipse.jdt.core.dom.AnonymousClassDeclaration;
import org.eclipse.jdt.core.dom.AnnotationTypeDeclaration;
import org.eclipse.jdt.core.dom.LambdaExpression;
import org.eclipse.jdt.core.dom.ITypeBinding;
import org.eclipse.jdt.core.dom.Modifier;
import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;
import java.util.stream.Collectors;

/**
 * An object representing a class, an interface, an enum, or an anonymous class for a lambda expression.
 * 
 * @author Katsuhisa Maruyama
 */
public class JavaClass extends JavaElement {
    
    /**
     * The type binding information on this class.
     */
    private ITypeBinding binding;
    
    /**
     * The kind of a class.
     */
    enum Kind {
        J_CLASS, J_INTERFACE, J_ENUM, J_ANONYMOUS, J_LAMBDA, UNKNOWN;
    }
    
    /**
     * The constant value that represents the array class.
     */
    public static QualifiedName ArrayClassFqn = new QualifiedName(".JavaArray", "");
    
    /**
     * The fully-qualified name of this class.
     */
    private QualifiedName qname;
    
    /**
     * The modifiers of this class.
     */
    private int modifiers;
    
    /**
     * The kind of this class.
     */
    private Kind kind;
    
    /**
     * A flag indicating whether this class exists inside the target project.
     */
    private boolean inProject = false;
    
    /**
     * A class that declares (and encloses the declaration of) this class.
     */
    private JavaClass declaringClass = null;
    
    /**
     * A method that declares (and encloses the declaration of) this class.
     */
    private JavaMethod declaringMethod = null;
    
    /**
     * The name of the super class of this class.
     */
    private String superClassName;
    
    /**
     * The names of the super interfaces of this class.
     */
    private Set<String> superInterfaceNames = new HashSet<>();
    
    /**
     * A collection of all fields within this class.
     */
    private List<JavaField> fields = new ArrayList<>();
    
    /**
     * A collection of all methods within this class.
     */
    private List<JavaMethod> methods = new ArrayList<>();
    
    /**
     * A collection of all inner classes within this class.
     */
    private List<JavaClass> innerClasses = new ArrayList<>();
    
    /**
     * Creates a new object representing a class.
     * @param node the AST node for this class or interface
     * @param jfile the file that declares this class
     */
    public JavaClass(TypeDeclaration node, JavaFile jfile) {
        this(node, node.resolveBinding(), jfile);
    }
    
    /**
     * Creates a new object representing a class.
     * @param node the AST node for this anonymous class
     * @param jfile the file that declares this class
     */
    public JavaClass(AnonymousClassDeclaration node, JavaFile jfile) {
        this(node, node.resolveBinding(), jfile);
    }
    
    /**
     * Creates a new object representing a class.
     * @param node the AST node for this enum
     * @param jfile the file that declares this class
     */
    public JavaClass(EnumDeclaration node, JavaFile jfile) {
        this(node, node.resolveBinding(), jfile);
    }
    
    /**
     * Creates a new object representing a class.
     * @param node the AST node for this annotation type
     * @param jfile the file that declares this class
     */
    public JavaClass(AnnotationTypeDeclaration node, JavaFile jfile) {
        this(node, node.resolveBinding(), jfile);
    }
    
    /**
     * Creates a new object representing a class.
     * @param node the AST node for this class
     * @param tbinding the type binding for this class
     * @param jfile the file that declares this class
     */
    public JavaClass(ASTNode node, ITypeBinding tbinding, JavaFile jfile) {
        super(node, jfile);
        
        if (tbinding != null) {
            this.binding = tbinding.getTypeDeclaration();
            this.qname = retrieveQualifiedName(binding);
            this.modifiers = binding.getModifiers();
            this.kind = getKind(binding);
            this.inProject = true;
            if (binding.getSuperclass() != null) {
                this.superClassName = binding.getSuperclass().getTypeDeclaration().getQualifiedName();
            } else {
                this.superClassName = null;
            }
            
            for (ITypeBinding tb : binding.getInterfaces()) {
                if (tb != null && tb.isInterface()) {
                    this.superInterfaceNames.add(tb.getTypeDeclaration().getQualifiedName());
                }
            }
            
            jfile.getProject().addClass(this);
            JavaPackage jpackage = jfile.getPackage();
            if (jpackage == null) {
                jpackage = JavaPackage.createDefault(jfile);
                jfile.setPackage(jpackage);
            }
            jpackage.addClass(this);
            jfile.addClass(this);
            
        } else {
            this.binding = null;
            this.qname = new QualifiedName();
            this.kind = JavaClass.Kind.UNKNOWN;
        }
    }
    
    /**
     * Creates a new object representing a class.
     * @param node the AST node for this anonymous class for a lambda expression
     * @param name the name of this class
     * @param tbinding the type binding for this class
     * @param jmethod the method that encloses this class
     */
    public JavaClass(LambdaExpression node, String name, ITypeBinding tbinding, JavaMethod jmethod) {
        super(node, jmethod.getFile());
        
        if (tbinding != null) {
            this.binding = tbinding.getTypeDeclaration();
            this.qname = new QualifiedName(name, "");
            this.modifiers = Modifier.PUBLIC;
            this.kind = JavaClass.Kind.J_LAMBDA;
            this.inProject = true;
            this.declaringClass = jmethod.getDeclaringClass();
            this.declaringMethod = jmethod;
            this.superClassName = null;
            this.superInterfaceNames.add(binding.getQualifiedName());
            
            jfile.getProject().addClass(this);
            JavaPackage jpackage = jfile.getPackage();
            if (jpackage == null) {
                jpackage = JavaPackage.createDefault(jfile);
                jfile.setPackage(jpackage);
            }
            jpackage.addClass(this);
            
        } else {
            this.binding = null;
            this.qname = new QualifiedName();
            this.kind = JavaClass.Kind.UNKNOWN;
        }
    }
    
    /**
     * Creates a new object representing a class that does not have its source code.
     * This method is not intended to be invoked by clients.
     * @param tbinding the type binding information on this class
     * @param inProject {@code true} if this class exists inside the target project, otherwise {@code false}
     */
    JavaClass(ITypeBinding tbinding, boolean inProject) {
        super(null, null);
        
        if (tbinding != null) {
            this.binding = tbinding.getTypeDeclaration();
            this.qname = retrieveQualifiedName(binding);
            this.modifiers = binding.getModifiers();
            this.kind = getKind(binding);
            this.inProject = inProject;
            if (binding.getSuperclass() != null) {
                this.superClassName = binding.getSuperclass().getTypeDeclaration().getQualifiedName();
            } else {
                this.superClassName = "";
            }
            
        } else {
            this.binding = null;
            this.qname = new QualifiedName();
            this.kind = JavaClass.Kind.UNKNOWN;
        }
    }
    
    /**
     * Creates a new object representing a class for the array.
     * This method is not intended to be invoked by clients
     * @param className the fully-qualified name of this class
     * @param inProject {@code true} if this class exists inside the target project, otherwise {@code false}
     */
    JavaClass(String className, boolean inProject) {
        super(null, null);
        
        this.binding = null;
        this.qname = new QualifiedName(className, "");
        this.kind = JavaClass.Kind.J_CLASS;
        this.modifiers = Modifier.PUBLIC;
        this.inProject = inProject;
        this.declaringClass = null;
        this.declaringMethod = null;
        this.superClassName = "";
    }
    
    /**
     * Returns the kind of this class.
     * @param binding the type binding information on the class
     * @return the kind of this class
     */
    private JavaClass.Kind getKind(ITypeBinding binding) {
        if (binding.isAnonymous()) {
            return JavaClass.Kind.J_ANONYMOUS;
        } else if (binding.isClass()) {
            return JavaClass.Kind.J_CLASS;
        } else if (binding.isInterface()) {
            return JavaClass.Kind.J_INTERFACE;
        } else if (binding.isEnum()) {
            return JavaClass.Kind.J_ENUM;
        }
        return JavaClass.Kind.UNKNOWN;
    }
    
    /**
     * Returns the fully-qualified name of this class.
     * @return the fully-qualified class name
     */
    @Override
    public QualifiedName getQualifiedName() {
        return qname;
    }
    
    /**
     * Returns the name of this class.
     * @return the class name
     */
    public String getClassName() {
        return qname.getClassName();
    }
    
    /**
     * The the value that stores information on the modifiers of this class.
     * @return the modifier value of this class
     */
    public int getModifiers() {
        return modifiers;
    }
    
    /**
     * Returns the type binding information on this class.
     * @return the type binding information
     */
    public ITypeBinding getTypeBinding() {
        return binding;
    }
    
    /**
     * Tests if this class exists inside the target project, which can resent the corresponding source code.
     * @return {@code true} if this class exists inside the target project, otherwise {@code false}
     */
    public boolean isInProject() {
        return inProject;
    }
    
    /**
     * A class that declares (and encloses the declaration of) this class.
     * @return the declaring class, or {@code null} if this class is not enclosed by any class
     */
    public JavaClass getDeclaringClass() {
        return declaringClass;
    }
    
    /**
     * A method that declares (and encloses the declaration of) this class.
     * @return the declaring method, or {@code null} if this class is not enclosed by any method
     */
    public JavaMethod getDeclaringMethod() {
        return declaringMethod;
    }
    
    /**
     * The package that this class belongs to.
     * @return the package of this class
     */
    public JavaPackage getPackage() {
        return jfile.getPackage();
    }
    
    /**
     * Tests if this is a class.
     * @return {@code true} if this is a class, otherwise {@code false}
     */
    public boolean isClass() {
        return kind == JavaClass.Kind.J_CLASS;
    }
    
    /**
     * Tests if this is an interface.
     * @return {@code true} if this is an interface, otherwise {@code false}
     */
    public boolean isInterface() {
        return kind == JavaClass.Kind.J_INTERFACE;
    }
    
    /**
     * Tests if this is an enum.
     * @return {@code true} if this is an enum, otherwise {@code false}
     */
    public boolean isEnum() {
        return kind == JavaClass.Kind.J_ENUM;
    }
    
    /**
     * Tests if this is an anonymous class.
     * @return {@code true} if this is an anonymous class, otherwise {@code false}
     */
    public boolean isAnonymous() {
        return kind == JavaClass.Kind.J_ANONYMOUS;
    }
    
    /**
     * Tests if this is an anonymous class for the lambda expression.
     * @return {@code true} if this is an anonymous class for the lambda expression, otherwise {@code false}
     */
    public boolean isLambda() {
        return kind == JavaClass.Kind.J_LAMBDA;
    }
    
    /**
     * Tests if this class has the public visibility.
     * @return {@code true} if this class has the public visibility, otherwise {@code false}
     */
    public boolean isPublic() {
        return Modifier.isPublic(modifiers);
    }
    
    /**
     * Tests if this class has the protected visibility.
     * @return {@code true} if this class has the protected visibility, otherwise {@code false}
     */
    public boolean isProtected() {
        return Modifier.isProtected(modifiers);
    }
    
    /**
     * Tests if this class has the private visibility.
     * @return {@code true} if this class has the private visibility, otherwise {@code false}
     */
    public boolean isPrivate() {
        return Modifier.isPrivate(modifiers);
    }
    
    /**
     * Tests if this class has the default visibility.
     * @return {@code true} if this class has the default visibility, otherwise {@code false}
     */
    public boolean isDefault() {
        return !isPublic() && !isProtected() && !isPrivate();
    }
    
    /**
     * Tests if this is a final class.
     * @return {@code true} if this is a final class, otherwise {@code false}
     */
    public boolean isFinal() {
        return Modifier.isFinal(modifiers);
    }
    
    /**
     * Tests if this is an abstract class.
     * @return {@code true} if this is an abstract class, otherwise {@code false}
     */
    public boolean isAbstract() {
        return Modifier.isAbstract(modifiers);
    }
    
    /**
     * Tests if this is a static class.
     * @return {@code true} if this is a static class, otherwise {@code false}
     */
    public boolean isStatic() {
        return Modifier.isStatic(modifiers);
    }
    
    /**
     * Tests if this is a strict-floating-point class.
     * @return {@code true} if this is a strict-floating-point class, otherwise {@code false}
     */
    public boolean isStrictfp() {
        return Modifier.isStrictfp(modifiers);
    }
    
    /**
     * Returns the name of the super class of this class.
     * @return the super class name
     */
    public String getSuperClassName() {
        return superClassName;
    }
    
    /**
     * Return the collection of the super interfaces of this class.
     * @return the super interfaces names
     */
    public Set<String> getSuperInterfaceNames() {
        return superInterfaceNames;
    }
    
    /**
     * Adds a field enclosed in this class.
     * This method is not intended to be invoked by clients.
     * @param jfield the field to be added
     */
    protected void addField(JavaField jfield) {
        if (!fields.contains(jfield)) {
            fields.add(jfield);
        }
    }
    
    /**
     * Obtains a field enclosed in this class, having a given name.
     * @param name the name of the field to be retrieved
     * @return the found field, or {@code null} if a corresponding field is not found
     */
    public JavaField getField(String name) {
        return fields.stream().filter(jf -> jf.getName().equals(name)).findFirst().orElse(null);
    }
    
    /**
     * Obtains fields enclosed in this class.
     * @return the collection of the fields
     */
    public List<JavaField> getFields() {
        return fields;
    }
    
    /**
     * Obtains fields enclosed in this class, which are sorted in the dictionary order.
     * @return the sorted list of the fields
     */
    public List<JavaField> getSortedFields() {
        return JavaClass.sortFields(fields);
    }
    
    /**
     * Adds a method enclosed in this class.
     * This method is not intended to be invoked by clients.
     * @param jmethod the method to be added
     */
    public void addMethod(JavaMethod jmethod) {
        if (!methods.contains(jmethod)) {
            methods.add(jmethod);
        }
    }
    
    /**
     * Obtains a method enclosed in this class, having a given name.
     * @param sig the signature of the method to be retrieved
     * @return the found method, or {@code null} if a corresponding method is not found
     */
    public JavaMethod getMethod(String sig) {
        return methods.stream()
                .filter(jm -> jm.getSignature().equals(sig)).findFirst().orElse(null);
    }
    
    /**
     * Obtains a static initializer enclosed in this class.
     * @return the static initializer, or {@code null} if this class does not have any static initializer
     */
    public JavaMethod getInitializer() {
        return getMethod(JavaMethod.InitializerName);
    }
    
    /**
     * Obtains methods enclosed in this class.
     * @return the collection of the methods
     */
    public List<JavaMethod> getMethods() {
        return methods;
    }
    
    /**
     * Obtains methods enclosed in this class, which are sorted in the dictionary order.
     * @return the sorted list of the methods
     */
    public List<JavaMethod> getSortedMethods() {
        return JavaClass.sortMethods(methods);
    }
    
    /**
     * Adds an inner class enclosed in this class.
     * This method is not intended to be invoked by clients.
     * @param jclass the method to be added
     */
    public void addInnerClass(JavaClass jclass) {
        if (!innerClasses.contains(jclass)) {
            innerClasses.add(jclass);
        }
    }
    
    /**
     * Obtains classes enclosed in this class.
     * @return the collection of the inner classes
     */
    public List<JavaClass> getInnerClasses() {
        return innerClasses;
    }
    
    /**
     * Obtains classes enclosed in this class, which are sorted in the dictionary order.
     * @return the sorted list of the classes
     */
    public List<JavaClass> getSortedInnerClasses() {
        return JavaClass.sortClasses(innerClasses);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object obj) {
        return (obj instanceof JavaClass) ? equals((JavaClass)obj) : false;
    }
    
    /**
     * Tests if a given class is equal to this class.
     * @param jclass the class to be checked
     * @return the {@code true} if the given class is equal to this class
     */
    public boolean equals(JavaClass jclass) {
        return jclass != null && (this == jclass || qname.fqn().equals(jclass.qname.fqn()));
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return qname.fqn().hashCode();
    }
    
    /**
     * Returns the label that represents the kind of this class.
     * @return the kind label
     */
    private String getKindLabel() {
        if (isClass()) {
            return "CLASS";
        } else if (isInterface()) {
            return "INTERFACE";
        } else if (isEnum()) {
            return "ENUM";
        } else if (isLambda()) {
            return "LAMBDA";
        } else {
            return kind.toString();
        }
    }
    
    /**
     * Obtains information on this class.
     * @return the string representing the information
     */
    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append("\n");
        buf.append(getKindLabel() + ": ");
        buf.append(getQualifiedName());
        buf.append("\n");
        if (getSuperClassName().length() > 0) {
            buf.append(" EXTENDS: ");
            buf.append(getSuperClassName());
        }
        if (getSuperInterfaceNames().size() != 0) {
            buf.append("\n");
            buf.append(" IMPLEMENTS:");
            for (String name : getSuperInterfaceNames()) {
                buf.append(" " + name);
            }
        }
        buf.append(toStringForFields());
        buf.append(toStringForMethods());
        buf.append(toStringInnerClasses());
        return buf.toString();
    }
    
    /**
     * Obtains information on all the fields enclosed in this class.
     * @return the string representing the information
     */
    protected String toStringForFields() {
        return getFields().stream().map(jf -> jf.toString()).collect(Collectors.joining());
    }
    
    /**
     * Obtains information on all the methods enclosed in this class.
     * @return the string representing the information
     */
    protected String toStringForMethods() {
        return getMethods().stream().map(jm -> jm.toString()).collect(Collectors.joining());
    }
    
    /**
     * Obtains information on all the inner classes enclosed in this class.
     * @return the string representing the information
     */
    protected String toStringInnerClasses() {
        return getInnerClasses().stream().map(jc -> jc.toString()).collect(Collectors.joining());
    }
    
    /**
     * Sorts the list of fields.
     * @param list the field list
     * @return the sorted field list
     */
    protected static List<JavaField> sortFields(List<? extends JavaField> list) {
        return list
                .stream()
                .sorted((jf1, jf2) -> jf1.getName().compareTo(jf2.getName()))
                .collect(Collectors.toList());
    }
    
    /**
     * Sorts the list of methods.
     * @param list the method list
     * @return the sorted method list
     */
    protected static List<JavaMethod> sortMethods(List<? extends JavaMethod> list) {
        return list
                .stream()
                .sorted((jm1, jm2) -> jm1.getSignature().compareTo(jm2.getSignature()))
                .collect(Collectors.toList());
    }
    
    /**
     * Sorts the list of classes.
     * @param list the class list
     * @return the sorted class list
     */
    protected static List<JavaClass> sortClasses(List<? extends JavaClass> list) {
        return list
                .stream()
                .sorted((jc1, jc2) -> jc1.getQualifiedName().fqn().compareTo(jc2.getQualifiedName().fqn()))
                .collect(Collectors.toList());
    }
    
    /**
     * A flag indicating whether the collected binding information is resolved.
     */
    protected boolean resolved = false;
    
    /**
     * The superclass of this class.
     */
    protected JavaClass superClass = null;
    
    /**
     * The collection of super interfaces.
     */
    protected Set<JavaClass> superInterfaces = new HashSet<>();
    
    /**
     * The collection of classes used by this class.
     */
    protected Set<JavaClass> usedClasses = new HashSet<>();
    
    /**
     * The collection of classes whose method or field accesses this class.
     */
    protected Set<JavaClass> afferentClasses = new HashSet<>();
    
    /**
     * The collection of classes that a method or a field of this class accesses.
     */
    protected Set<JavaClass> efferentClasses = new HashSet<>();
    
    /**
     * Collects additional information on this class.
     * This method is not intended to be invoked by clients, which will be automatically invoked as needed.
     */
    protected void collectInfo() {
        if (!inProject || resolved) {
            return;
        }
        
        boolean resolveOk = true;
        if (binding != null) {
            if (!binding.isTopLevel()) {
                declaringClass = findDeclaringClass(getJavaProject(), binding.getDeclaringClass());
            }
            declaringMethod = findDeclaringMethod(getJavaProject(), binding.getDeclaringMethod());
            
            resolveOk = resolveOk & findSuperClass();
            resolveOk = resolveOk & findSuperInterfaces();
            if (inProject) {
                resolveOk = resolveOk & findUsedClass();
                for (JavaMethod jmethod : methods) {
                    jmethod.collectInfo();
                }
                for (JavaField jfield : fields) {
                    jfield.collectInfo();
                }
                findEfferentClasses();
            }
        }
        
        if (!resolveOk) {
            Logger.getInstance().printUnresolvedError("Class in " + jfile.getPath());
        }
        resolved = true;
    }
    
    private boolean findSuperClass() {
        if (isClass()) {
            if (binding.getSuperclass() != null) {
                superClass = findDeclaringClass(getJavaProject(), binding.getSuperclass());
                if (superClass == null) {
                    return false;
                }
            }
        }
        return true;
    }
    
    private boolean findSuperInterfaces() {
        boolean resolveOk = true;
        if (isLambda()) {
            if (binding.isIntersectionType()) {
                for (ITypeBinding tbinding : binding.getTypeBounds()) {
                    if (tbinding.isInterface()) {
                        JavaClass jclass = findDeclaringClass(getJavaProject(), tbinding);
                        if (jclass != null) {
                            superInterfaces.add(jclass);
                        } else {
                            resolveOk = false;
                        }
                    }
                }
                
            } else {
                JavaClass jclass = findDeclaringClass(getJavaProject(), binding);
                if (jclass != null) {
                    superInterfaces.add(jclass);
                } else {
                    resolveOk = false;
                }
            }
            
        } else {
            for (ITypeBinding tbinding : binding.getInterfaces()) {
                JavaClass jclass = findDeclaringClass(getJavaProject(), tbinding);
                if (jclass != null) {
                    superInterfaces.add(jclass);
                } else {
                    resolveOk = false;
                }
            }
        }
        return resolveOk;
    }
    
    private boolean findUsedClass() {
        TypeCollector visitor = new TypeCollector(this);
        astNode.accept(visitor);
        return visitor.isBindingOk();
    }
    
    /**
     * Adds a class used by this class.
     * This method is not intended to be invoked by clients.
     * @param jclass the class to be added
     */
    public void addUsedClass(JavaClass jclass) {
        if (jclass != null && !jclass.equals(this) && !usedClasses.contains(jclass)) {
            usedClasses.add(jclass);
        }
    }
    
    private void findEfferentClasses() {
        if (efferentClasses == null) {
            efferentClasses = new HashSet<>();
        }
        if (afferentClasses == null) {
            afferentClasses = new HashSet<>();
        }
        for (JavaClass jclass : usedClasses) {
            if (!jclass.equals(this)) {
                efferentClasses.add(jclass);
                jclass.addAfferentClass(this);
            }
        }
        
        for (JavaMethod jmethod : getMethods()) {
            for (JavaMethod jm : jmethod.getCalledMethods()) {
                JavaClass jclass = jm.getDeclaringClass();
                if (!jclass.equals(this)) {
                    efferentClasses.add(jclass);
                    jclass.addAfferentClass(this);
                }
            }
            for (JavaField jf : jmethod.getAccessedFields()) {
                JavaClass jclass = jf.getDeclaringClass();
                if (!jclass.equals(this)) {
                    efferentClasses.add(jclass);
                    jclass.addAfferentClass(this);
                }
            }
        }
        
        for (JavaField jfield : getFields()) {
            for (JavaMethod jm : jfield.getCalledMethods()) {
                JavaClass jclass = jm.getDeclaringClass();
                if (!jclass.equals(this)) {
                    efferentClasses.add(jclass);
                    jclass.addAfferentClass(this);
                }
            }
            for (JavaField jf : jfield.getAccessedFields()) {
                JavaClass jclass = jf.getDeclaringClass();
                if (!jclass.equals(this)) {
                    efferentClasses.add(jclass);
                    jclass.addAfferentClass(this);
                }
            }
        }
    }
    
    private void addAfferentClass(JavaClass jclass) {
        if (jclass != null && !afferentClasses.contains(jclass)) {
            afferentClasses.add(jclass);
        }
    }
    
    /**
     * Obtains a super class of this class.
     * @return the super class
     */
    public JavaClass getSuperClass() {
        collectInfo();
        return superClass;
    }
    
    /**
     * Obtains super interfaces of this class.
     * @return the collection of the super interfaces
     */
    public Set<JavaClass> getSuperInterfaces() {
        collectInfo();
        return superInterfaces;
    }
    
    /**
     * Obtains classes used by this class.
     * @return the collection of the used classes
     */
    public Set<JavaClass> getUsedClasses() {
        collectInfo();
        return usedClasses;
    }
    
    /**
     * Obtains child classes of this class.
     * @return the list of the child classes
     */
    public List<JavaClass> getChildren() {
        collectInfo();
        
        return jfile.getProject().getClasses()
                .stream()
                .filter(jc -> jc.isChildOf(this))
                .collect(Collectors.toList());
    }
    
    /**
     * Tests if this class is a child of a given parent class.
     * @param jclass the possible parent class
     * @return {@code true} if this class is a child of the given class, otherwise {@code false}
     */
    public boolean isChildOf(JavaClass jclass) {
        collectInfo();
        if (superClass != null && superClass.getQualifiedName().equals(jclass.getQualifiedName())) {
            return true;
        }
        return superInterfaces
                .stream()
                .anyMatch(jc -> jc.getQualifiedName().equals(jclass.getQualifiedName()));
    }
    
    /**
     * Obtains super classes in the distance order.
     * The closest ancestor (i.e. parent) is located at the first position.
     * @return the ordered list of the super classes
     */
    public List<JavaClass> getAllSuperClasses() {
        collectInfo();
        List<JavaClass> types = new ArrayList<>();
        JavaClass parent = this.getSuperClass();
        while (parent != null) {
            types.add(parent);
            parent = parent.getSuperClass();
        }
        return types;
    }
    
    /**
     * Obtains super interfaces.
     * @return the collection of the super interfaces
     */
    public List<JavaClass> getAllSuperInterfaces() {
        collectInfo();
        List<JavaClass> jclasses = new ArrayList<>();
        getAllSuperInterfaces(this, jclasses);
        return jclasses;
    }
    
    private void getAllSuperInterfaces(JavaClass jclass, List<JavaClass> jclasses) {
        for (JavaClass parent : jclass.getSuperInterfaces()) {
            if (!jclasses.contains(parent)) {
                jclasses.add(parent);
                getAllSuperInterfaces(parent, jclasses);
            }
        }
    }
    
    private void getAllChildren(JavaClass jclass, List<JavaClass> jclasses) {
        for (JavaClass child : jclass.getChildren()) {
            if (child != null) {
                if (!jclasses.contains(child)) {
                    jclasses.add(child);
                    getAllChildren(child, jclasses);
                }
            }
        }
    }
    
    /**
     * Obtains ancestors of this class.
     * @return the collection of the ancestors
     */
    public List<JavaClass> getAncestors() {
        collectInfo();
        List<JavaClass> jclasses = new ArrayList<>();
        jclasses.addAll(getAllSuperClasses());
        jclasses.addAll(getAllSuperInterfaces());
        return jclasses;
    }
    
    /**
     * Obtains descendants of this class.
     * @return the collection of the descendants
     */
    public List<JavaClass> getDescendants() {
        collectInfo();
        List<JavaClass> jclasses = new ArrayList<>();
        getAllChildren(this, jclasses);
        return jclasses;
    }
    
    /**
     * Obtains classes whose method or field accesses this class.
     * @return the collection of the afferent classes
     */
    public Set<JavaClass> getAfferentClasses() {
        return afferentClasses;
    }
    
    /**
     * Obtains classes whose method or field accesses this class, which are enclosed in the target project.
     * @return the collection of the afferent classes
     */
    public Set<JavaClass> getAfferentClassesInProject() {
        return afferentClasses
                .stream()
                .filter(jc -> jc.isInProject())
                .collect(Collectors.toSet());
    }
    
    /**
     * Obtains classes whose method or field accesses this class.
     * @return the collection of the efferent classes
     */
    public Set<JavaClass> getEfferentClasses() {
        return efferentClasses;
    }
    
    /**
     * Obtains classes whose method or field accesses this class, which are enclosed in the target project.
     * @return the collection of the efferent classes
     */
    public Set<JavaClass> getEfferentClassesInProject() {
        return efferentClasses
                .stream()
                .filter(jc -> jc.isInProject())
                .collect(Collectors.toSet());
    }
}
