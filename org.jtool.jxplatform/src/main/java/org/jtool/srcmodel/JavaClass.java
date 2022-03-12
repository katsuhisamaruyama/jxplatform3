/*
 *  Copyright 2022
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.srcmodel;

import org.jtool.jxplatform.builder.srcmodel.TypeCollector;
import org.jtool.jxplatform.project.Logger;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.eclipse.jdt.core.dom.EnumDeclaration;
import org.eclipse.jdt.core.dom.AnonymousClassDeclaration;
import org.eclipse.jdt.core.dom.AnnotationTypeDeclaration;
import org.eclipse.jdt.core.dom.LambdaExpression;
import org.eclipse.jdt.core.dom.ITypeBinding;
import org.eclipse.jdt.core.dom.IMethodBinding;
import org.eclipse.jdt.core.dom.IPackageBinding;
import org.eclipse.jdt.core.dom.Modifier;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Set;
import java.util.HashSet;
import java.util.stream.Collectors;
import java.util.Optional;

/**
 * An object representing a class, an interface, an enum, or an anonymous class for a lambda expression.
 * 
 * @author Katsuhisa Maruyama
 */
public class JavaClass extends JavaElement {
    
    /**
     * A project which this model element exists in
     */
    protected JavaProject jproject;
    
    /**
     * A file that declares this model element.
     */
    protected JavaFile jfile;
    
    /**
     * Type binding information on this class.
     */
    private ITypeBinding binding;
    
    /**
     * The fully-qualified name of this class.
     */
    private QualifiedName qname;
    
    /**
     * The constant value that represents the array class.
     */
    public static QualifiedName ArrayClassFqn = new QualifiedName(".JavaArray", "");
    
    /**
     * The modifiers of this class.
     */
    private int modifiers;
    
    /**
     * The kind of this class.
     */
    private Kind kind;
    
    /**
     * The kind of a class.
     */
    enum Kind {
        J_CLASS, J_INTERFACE, J_ENUM, J_ANONYMOUS, J_LAMBDA, UNKNOWN;
    }
    
    /**
     * The name of the super class of this class.
     */
    private String superClassName;
    
    /**
     * The names of the super interfaces of this class.
     */
    private Set<String> superInterfaceNames = new HashSet<>();
    
    /**
     * The collection of all fields within this class.
     */
    private List<JavaField> fields = new ArrayList<>();
    
    /**
     * The collection of all methods within this class.
     */
    private List<JavaMethod> methods = new ArrayList<>();
    
    /**
     * The collection of all inner classes within this class.
     */
    private List<JavaClass> innerClasses = new ArrayList<>();
    
    /**
     * A flag indicating whether this class exists inside the target project.
     */
    private boolean inProject = false;
    
    /**
     * A class that declares (or encloses the declaration of) this class.
     */
    private JavaClass declaringClass;
    
    /**
     * A method that declares (or encloses the declaration of) this class.
     */
    private JavaMethod declaringMethod;
    
    /**
     * A package that declares this class.
     */
    private JavaPackage jpackage;
    
    /**
     * Creates a new object representing a class.
     * @param node the AST node for this class or interface
     * @param jfile the file that declares this class
     * @throws the exception occurs when the creation of a new object fails
     */
    public JavaClass(TypeDeclaration node, JavaFile jfile) throws JavaElementException {
        this(node, node.resolveBinding(), jfile);
    }
    
    /**
     * Creates a new object representing a class.
     * @param node the AST node for this anonymous class
     * @param jfile the file that declares this class
     * @throws the exception occurs when the creation of a new object fails
     */
    public JavaClass(AnonymousClassDeclaration node, JavaFile jfile) throws JavaElementException {
        this(node, node.resolveBinding(), jfile);
    }
    
    /**
     * Creates a new object representing a class.
     * @param node the AST node for this class holding the enum constant declarations
     * @param jfile the file that declares this class
     * @throws the exception occurs when the creation of a new object fails
     */
    public JavaClass(EnumDeclaration node, JavaFile jfile) throws JavaElementException {
        this(node, node.resolveBinding(), jfile);
    }
    
    /**
     * Creates a new object representing a class.
     * @param node the AST node for this annotation type
     * @param jfile the file that declares this class
     * @throws the exception occurs when the creation of a new object fails
     */
    public JavaClass(AnnotationTypeDeclaration node, JavaFile jfile) throws JavaElementException {
        this(node, node.resolveBinding(), jfile);
    }
    
    /**
     * Creates a new object representing a class.
     * @param node an AST node for this class
     * @param tbinding type binding information on this class
     * @param jfile a file that declares this class
     * @throws the exception occurs when the creation of a new object fails
     */
    public JavaClass(ASTNode node, ITypeBinding tbinding, JavaFile jfile) throws JavaElementException {
        super(node);
        
        this.jproject = jfile.getJavaProject();
        this.jfile = jfile;
        
        if (tbinding != null) {
            this.binding = tbinding.getTypeDeclaration();
            this.qname = JavaElementUtil.findQualifiedName(binding);
            this.modifiers = binding.getModifiers();
            this.kind = getKind(binding);
            
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
            
            this.inProject = true;
            this.declaringClass = JavaElementUtil.findDeclaringClass(binding.getDeclaringClass(), jproject);
            this.declaringMethod = JavaElementUtil.findDeclaringMethod(binding.getDeclaringMethod(), jproject);
            
            jfile.getJavaProject().addClass(this);
            JavaPackage jpackage = jfile.getPackage();
            if (jpackage == null) {
                jpackage = JavaPackage.createDefault(jfile);
            }
            jpackage.addClass(this);
            jfile.addClass(this);
            this.jpackage = jpackage;
            
            Optional<IMethodBinding> defaultConstructor = Arrays.asList(binding.getDeclaredMethods()).stream()
                    .filter(e -> e.isDefaultConstructor()).findFirst();
            
            if (defaultConstructor.isPresent()) {
                IMethodBinding mb = defaultConstructor.get();
                if (mb.getName().length() > 0) {
                    new JavaMethod(mb, this);
                }
            }
            
        } else {
            throw new JavaElementException("class in file " + jfile.getName());
        }
    }
    
    /**
     * Creates a new object representing a class that does not have its source code.
     * This method is not intended to be invoked by clients.
     * @param tbinding type binding information on this class
     * @param jproject a project which this model element exists in
     */
    JavaClass(ITypeBinding tbinding, JavaProject jproject) {
        super(null);
        
        this.jproject = jproject;
        this.jfile = null;
        
        this.binding = tbinding.getTypeDeclaration();
        this.qname = JavaElementUtil.findQualifiedName(binding);
        this.modifiers = binding.getModifiers();
        this.kind = getKind(binding);
        this.inProject = false;
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
        
        for (int index = 0; index < binding.getDeclaredTypes().length; index++) {
            ITypeBinding tb = binding.getDeclaredTypes()[index];
            JavaClass jc = new JavaClass(tb, jproject);
            this.addInnerClass(jc);
        }
        
        IPackageBinding pbinding = tbinding.getPackage();
        JavaPackage jpackage = JavaPackage.createExternal(pbinding, jproject);
        jpackage.addClass(this);
        this.jpackage = jpackage;
    }
    
    /**
     * Creates a new object representing a class.
     * @param node the AST node for this anonymous class for a lambda expression
     * @param tbinding type binding information on this class
     * @param name the name of this class
     * @param jmethod a method that encloses this class
     */
    public JavaClass(LambdaExpression node, ITypeBinding tbinding, String name, JavaMethod jmethod) {
        super(node);
        
        this.jproject = jmethod.getFile().getJavaProject();
        this.jfile = jmethod.getFile();
        
        this.binding = tbinding.getTypeDeclaration();
        this.qname = new QualifiedName(name, "");
        this.modifiers = Modifier.PUBLIC;
        this.kind = JavaClass.Kind.J_LAMBDA;
        this.inProject = true;
        this.superClassName = null;
        this.superInterfaceNames.add(binding.getQualifiedName());
        this.declaringClass = jmethod.getDeclaringClass();
        this.declaringMethod = jmethod;
        
        jfile.getJavaProject().addClass(this);
        JavaPackage jpackage = jfile.getPackage();
        if (jpackage == null) {
            jpackage = JavaPackage.createDefault(jfile);
        }
        jpackage.addClass(this);
        this.jpackage = jpackage;
    }
    
    /**
     * Creates a special class that represents an array.
     * @param jproject a project which this model element exists in
     * @return the special array class
     */
    protected static JavaClass getArrayClass(JavaProject jproject) {
        QualifiedName qname = JavaClass.ArrayClassFqn;
        JavaClass jclass = jproject.getExternalClass(qname.fqn());
        if (jclass == null) {
            jclass = new JavaClass(qname, jproject);
            jproject.addExternalClass(jclass);
        }
        return jclass;
    }
    
    /**
     * Creates a new object representing a class.
     * The object is not allowed to be directly created.
     * @param qname the fully-qualified name of this class
     * @param jproject a project which this model element exists in
     */
    private JavaClass(QualifiedName qname, JavaProject jproject) {
        super(null);
        
        this.jproject = jproject;
        this.jfile = null;
        
        this.binding = null;
        this.qname = qname;
        this.kind = JavaClass.Kind.J_CLASS;
        this.modifiers = Modifier.PUBLIC;
        this.inProject = false;
        this.superClassName = null;
        this.superInterfaceNames = null;
        this.declaringClass = null;
        this.declaringMethod = null;
        this.jpackage = null;
    }
    
    /**
     * Returns the kind of a class.
     * @param binding type binding information on the class
     * @return the kind of the class
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
     * Returns the project which this class exists in.
     * @return the project
     */
    public JavaProject getJavaProject() {
        return jproject;
    }
    
    /**
     * Returns the file that declares this class.
     * @return the declaring file
     */
    public JavaFile getFile() {
        return jfile;
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
    public String getName() {
        return qname.getClassName().substring(qname.getClassName().lastIndexOf('.') + 1);
    }
    
    /**
     * Returns the fully-qualified name of this class.
     * @return the fully-qualified class name
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
     * Returns the class that declares this class.
     * @return the declaring class, or {@code null} if this class is not enclosed by any class
     */
    public JavaClass getDeclaringClass() {
        return declaringClass;
    }
    
    /**
     * Returns the method that declares this class.
     * @return the declaring method, or {@code null} if this class is not enclosed by any method
     */
    public JavaMethod getDeclaringMethod() {
        return declaringMethod;
    }
    
    /**
     * Returns the package that this class belongs to.
     * @return the package of this class, or {@code null} if this class does not have its source code
     */
    public JavaPackage getPackage() {
        return jpackage; 
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
     * Tests if this is a class holding the enum constant declarations.
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
     * @return the super class name, or {@code null} if this is an interface
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
        if (getSuperClassName() != null && getSuperClassName().length() > 0) {
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
        return list.stream()
                .sorted((jf1, jf2) -> jf1.getName().compareTo(jf2.getName()))
                .collect(Collectors.toList());
    }
    
    /**
     * Sorts the list of methods.
     * @param list the method list
     * @return the sorted method list
     */
    protected static List<JavaMethod> sortMethods(List<? extends JavaMethod> list) {
        return list.stream()
                .sorted((jm1, jm2) -> jm1.getSignature().compareTo(jm2.getSignature()))
                .collect(Collectors.toList());
    }
    
    /**
     * Sorts the list of classes.
     * @param list the class list
     * @return the sorted class list
     */
    protected static List<JavaClass> sortClasses(List<? extends JavaClass> list) {
        return list.stream()
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
     * The collection of classes that a method or a field of this class accesses.
     */
    protected Set<JavaClass> efferentClasses = new HashSet<>();
    
    /**
     * The collection of classes whose method or field accesses this class.
     */
    protected Set<JavaClass> afferentClasses = null;
    
    /**
     * Collects additional information on this class.
     * This method is not intended to be invoked by clients, which will be automatically invoked as needed.
     */
    protected void collectInfo() {
        if (resolved) {
            return;
        }
        
        boolean resolveOk = true;
        if (binding != null) {
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
                collectEfferentClasses();
            }
        }
        
        if (!resolveOk) {
            Logger logger = jproject.getModelBuilderImpl().getLogger();
            logger.printUnresolvedError("Class in " + jfile.getPath());
        }
        resolved = true;
    }
    
    /**
     * Finds a super class of this class.
     * @return {@code true} if a super class was successfully specified, otherwise {@code false}
     */
    private boolean findSuperClass() {
        if (isClass()) {
            if (binding.getSuperclass() != null) {
                superClass = JavaElementUtil.findDeclaringClass(binding.getSuperclass(), jproject);
                if (superClass == null) {
                    return false;
                }
            }
        }
        return true;
    }
    
    /**
     * Finds super interfaces of this class.
     * @return {@code true} if all super interfaces were successfully specified, otherwise {@code false}
     */
    private boolean findSuperInterfaces() {
        boolean resolveOk = true;
        if (isLambda()) {
            if (binding.isIntersectionType()) {
                for (ITypeBinding tbinding : binding.getTypeBounds()) {
                    if (tbinding.isInterface()) {
                        JavaClass jclass = JavaElementUtil.findDeclaringClass(tbinding, jproject);
                        if (jclass != null) {
                            superInterfaces.add(jclass);
                        } else {
                            resolveOk = false;
                        }
                    }
                }
                
            } else {
                JavaClass jclass = JavaElementUtil.findDeclaringClass(binding, jproject);
                if (jclass != null) {
                    superInterfaces.add(jclass);
                } else {
                    resolveOk = false;
                }
            }
            
        } else {
            for (ITypeBinding tbinding : binding.getInterfaces()) {
                JavaClass jclass = JavaElementUtil.findDeclaringClass(tbinding, jproject);
                if (jclass != null) {
                    superInterfaces.add(jclass);
                } else {
                    resolveOk = false;
                }
            }
        }
        return resolveOk;
    }
    
    /**
     * Finds classes appearing in this class.
     * @return {@code true} if all classes were successfully specified, otherwise {@code false}
     */
    private boolean findUsedClass() {
        TypeCollector visitor = new TypeCollector(this);
        astNode.accept(visitor);
        boolean bindingOk = visitor.isBindingOk();
        if (visitor.isBindingOk()) {
            usedClasses.addAll(visitor.getTypes());
        }
        return bindingOk;
    }
    
    /**
     * Collects classes required by this class.
     */
    private void collectEfferentClasses() {
        for (JavaClass jclass : usedClasses) {
            if (!jclass.equals(this)) {
                efferentClasses.add(jclass);
            }
        }
        
        for (JavaMethod jmethod : methods) {
            for (JavaMethod jm : jmethod.getCalledMethods()) {
                JavaClass jclass = jm.getDeclaringClass();
                if (!jclass.equals(this)) {
                    efferentClasses.add(jclass);
                }
            }
            for (JavaField jf : jmethod.getAccessedFields()) {
                JavaClass jclass = jf.getDeclaringClass();
                if (!jclass.equals(this)) {
                    efferentClasses.add(jclass);
                }
            }
        }
        
        for (JavaField jfield : fields) {
            for (JavaMethod jm : jfield.getCalledMethods()) {
                JavaClass jclass = jm.getDeclaringClass();
                if (!jclass.equals(this)) {
                    efferentClasses.add(jclass);
                }
            }
            for (JavaField jf : jfield.getAccessedFields()) {
                JavaClass jclass = jf.getDeclaringClass();
                if (!jclass.equals(this)) {
                    efferentClasses.add(jclass);
                }
            }
        }
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
    
    /**
     * Returns the super class of this class.
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
     * @return the collection of the used classes, an empty set when this class does not have its corresponding file
     */
    public Set<JavaClass> getUsedClasses() {
        collectInfo();
        return usedClasses;
    }
    
    /**
     * Obtains classes required by this class.
     * @return the collection of the required classes
     */
    public Set<JavaClass> getEfferentClasses() {
        collectInfo();
        return efferentClasses;
    }
    
    /**
     * Obtains child classes of this class.
     * @return the list of the child classes
     */
    public Set<JavaClass> getChildren() {
        collectInfo();
        return jproject.getAllClasses().stream()
                .filter(jc -> jc.isChildOf(this))
                .collect(Collectors.toSet());
    }
    
    /**
     * Tests if this class is a child of a given parent class.
     * @param jclass the possible parent class
     * @return {@code true} if this class is a child of the given class, otherwise {@code false}
     */
    public boolean isChildOf(JavaClass jclass) {
        if (superClassName != null && superClassName.equals(jclass.getClassName())) {
            return true;
        }
        return superInterfaceNames.stream()
                .anyMatch(n -> n.equals(jclass.getClassName()));
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
    public Set<JavaClass> getAllSuperInterfaces() {
        collectInfo();
        Set<JavaClass> jclasses = new HashSet<>();
        collectAllSuperInterfaces(this, jclasses);
        return jclasses;
    }
    
    /**
     * Collects super interfaces of a given class
     * @param jclass the class of interest
     * @param jclasses the collection of the super interfaces
     */
    private void collectAllSuperInterfaces(JavaClass jclass, Set<JavaClass> jclasses) {
        for (JavaClass parent : jclass.getSuperInterfaces()) {
            if (!jclasses.contains(parent)) {
                jclasses.add(parent);
                collectAllSuperInterfaces(parent, jclasses);
            }
        }
    }
    
    /**
     * Collects sub-classes of a given class
     * @param jclass the class of interest
     * @param jclasses the collection of sub-classes
     */
    private void collectAllChildren(JavaClass jclass, Set<JavaClass> jclasses) {
        for (JavaClass child : jclass.getChildren()) {
            if (child != null) {
                if (!jclasses.contains(child)) {
                    jclasses.add(child);
                    collectAllChildren(child, jclasses);
                }
            }
        }
    }
    
    /**
     * Obtains ancestors of this class.
     * @return the collection of the ancestors
     */
    public Set<JavaClass> getAncestors() {
        collectInfo();
        Set<JavaClass> jclasses = new HashSet<>();
        jclasses.addAll(getAllSuperClasses());
        jclasses.addAll(getAllSuperInterfaces());
        return jclasses;
    }
    
    /**
     * Obtains descendants of this class.
     * @return the collection of the descendants
     */
    public Set<JavaClass> getDescendants() {
        collectInfo();
        Set<JavaClass> jclasses = new HashSet<>();
        collectAllChildren(this, jclasses);
        return jclasses;
    }
    
    /**
     * Obtains classes required by this class that has its corresponding file.
     * @return the collection of the required classes
     */
    public Set<JavaClass> getEfferentClassesInProject() {
        return getEfferentClasses().stream()
                .filter(jc -> jc.isInProject())
                .collect(Collectors.toSet());
    }
    
    /**
     * Obtains classes requiring this class.
     * @return the collection of the requiring classes
     */
    public Set<JavaClass> getAfferentClasses() {
        if (afferentClasses == null) {
            afferentClasses = new HashSet<>();
            collectAfferentClasses();
        }
        return afferentClasses;
    }
    
    /**
     * Obtains classes requiring this class, which have their corresponding files.
     * @return the collection of the requiring classes
     */
    public Set<JavaClass> getAfferentClassesInProject() {
        return getAfferentClasses();
    }
    
    /**
     * Collects classes requiring this class.
     */
    private void collectAfferentClasses() {
        for (JavaClass jclass : getJavaProject().getClasses()) {
            jclass.collectInfo();
            jclass.getEfferentClasses().forEach(jc -> jc.addAfferentClass(jclass));
        }
    }
    
    /**
     * Adds a class requiring this class.
     * @param jclass the requiring class
     */
    private void addAfferentClass(JavaClass jclass) {
        if (afferentClasses == null) {
            afferentClasses = new HashSet<>();
        }
        afferentClasses.add(jclass);
    }
}
