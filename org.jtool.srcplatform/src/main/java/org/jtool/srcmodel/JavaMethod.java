/*
 *  Copyright 2020
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.srcmodel;

import org.jtool.srcmodel.builder.FieldAccessCollector;
import org.jtool.srcmodel.builder.LambdaCollector;
import org.jtool.srcmodel.builder.LocalDeclarationCollector;
import org.jtool.srcmodel.builder.MethodCallCollector;
import org.jtool.srcplatform.util.Logger;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.Initializer;
import org.eclipse.jdt.core.dom.LambdaExpression;
import org.eclipse.jdt.core.dom.Modifier;
import org.eclipse.jdt.core.dom.Type;
import org.eclipse.jdt.core.dom.VariableDeclaration;
import org.eclipse.jdt.core.dom.ITypeBinding;
import org.eclipse.jdt.core.dom.IMethodBinding;
import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;
import java.util.Map;
import java.util.HashMap;
import java.util.Collection;
import java.util.stream.Collectors;

/**
 * An object representing a method, a constructor, a static initializer, or a lambda expression.
 * 
 * @author Katsuhisa Maruyama
 */
public class JavaMethod extends JavaElement {
    
    /**
     * The method binding information on this method.
     */
    private IMethodBinding binding;
    
    /**
     * The constant value that represents the a static initializer.
     */
    static final String InitializerName = ".init()";
    
    /**
     * The kind of a method.
     */
    enum Kind {
        J_METHOD, J_CONSTRUCTOR, J_INITIALIZER, J_LAMBDA, UNKNOWN;
    }
    
    /**
     * The fully-qualified name of this method.
     */
    private QualifiedName qname;
    
    /**
     * The the return type of this method.
     */
    private String returnType;
    
    /**
     * The modifiers of this method.
     */
    private int modifiers;
    
    /**
     * The kind of this method.
     */
    private Kind kind;
    
    /**
     * A flag indicating whether this method exists inside the target project.
     */
    private boolean inProject;
    
    /**
     * A class that declares (and encloses the declaration of) this method.
     */
    private JavaClass declaringClass = null;
    
    /**
     * The list of parameters of this method.
     */
    private List<JavaLocalVar> parameters = new ArrayList<>();
    
    /**
     * The list of local variables used in this method.
     */
    private List<JavaLocalVar> localDecls = new ArrayList<>();
    
    /**
     * The map of the types of exceptions that this method might throw and their AST nodes.
     */
    private Map<String, Type> exceptionTypes = new HashMap<>();
    
    /**
     * Creates a new object representing a method.
     * @param node the AST node for this method
     * @param jclass the class that declares this method
     */
    @SuppressWarnings("unchecked")
    public JavaMethod(MethodDeclaration node, JavaClass jclass) {
        super(node, jclass.getFile());
        
        IMethodBinding mbinding = node.resolveBinding();
        if (mbinding != null) {
            this.binding = mbinding.getMethodDeclaration();
            this.qname = new QualifiedName(jclass.getQualifiedName(), getSignature(binding));
            if (mbinding.isConstructor()) {
                this.returnType = binding.getName();
            } else {
                this.returnType = binding.getReturnType().getErasure().getQualifiedName();
            }
            this.modifiers = binding.getModifiers();
            this.kind = getKind(binding);
            this.inProject = true;
            this.declaringClass = jclass;
            
            collectParameters(node.parameters());
            collectLocalVariables(node.getBody());
            collectLambdas(node.getBody());
            for (Type exceptionType : (List<Type>)node.thrownExceptionTypes()) {
                this.exceptionTypes.put(exceptionType.resolveBinding().getTypeDeclaration().getQualifiedName(), exceptionType);
            }
            
            jclass.addMethod(this);
            
        } else {
            this.binding = null;
            this.qname = new QualifiedName();
            this.kind = JavaMethod.Kind.UNKNOWN;
        }
    }
    
    /**
     * Creates a new object representing a method.
     * @param node the AST node for this static initializer
     * @param jclass the class that declares this method
     */
    public JavaMethod(Initializer node, JavaClass jclass) {
        super(node, jclass.getFile());
        
        this.qname = new QualifiedName(jclass.getQualifiedName(), JavaMethod.InitializerName);
        this.returnType = "void";
        this.modifiers = 0;
        this.kind = JavaMethod.Kind.J_INITIALIZER;
        this.inProject = true;
        this.declaringClass = jclass;
        
        collectLocalVariables(node.getBody());
        collectLambdas(node.getBody());
        
        jclass.addMethod(this);
    }
    
    /**
     * Creates a new object representing a method.
     * @param node the AST node for this method for a lambda expression
     * @param jclass the class that declares this method
     */
    @SuppressWarnings("unchecked")
    public JavaMethod(LambdaExpression node, IMethodBinding mbinding, JavaClass jclass) {
        super(node, jclass.getFile());
        
        if (mbinding != null) {
            this.binding = mbinding.getMethodDeclaration();
            this.qname =  new QualifiedName(jclass.getQualifiedName(), getSignature(binding));
            this.returnType = binding.getReturnType().getQualifiedName();
            this.modifiers = binding.getModifiers();
            this.kind = JavaMethod.Kind.J_LAMBDA;
            this.inProject = true;
            this.declaringClass = jclass;
            
            collectParameters(node.parameters());
            collectLocalVariables(node.getBody());
            collectLambdas(node.getBody());
            
            jclass.addMethod(this);
            
        } else {
            this.binding = null;
            this.qname = new QualifiedName();
            this.kind = JavaMethod.Kind.UNKNOWN;
        }
    }
    
    /**
     * Creates a new object representing a method.
     * @param mbinding the method binding information on this method
     * @param jclass the class that declares this field
     * @param inProject {@code true} if this field exists inside the target project, otherwise {@code false}
     */
    protected JavaMethod(IMethodBinding mbinding, JavaClass jclass, boolean inProject) {
        super(null, null);
        
        if (mbinding != null) {
            this.binding = mbinding.getMethodDeclaration();
            this.qname =  new QualifiedName(jclass.getQualifiedName(), getSignature(binding));
            if (mbinding.isConstructor()) {
                this.returnType = binding.getName();
            } else {
                this.returnType = binding.getReturnType().getQualifiedName();
            }
            this.modifiers = binding.getModifiers();
            this.kind = getKind(binding);
            this.inProject = inProject;
            this.declaringClass = jclass;
            
            collectParameters(mbinding.getParameterTypes());
            
            jclass.addMethod(this);
            
        } else {
            this.binding = null;
            this.qname = new QualifiedName();
            this.kind = JavaMethod.Kind.UNKNOWN;
        }
    }
    
    private Kind getKind(IMethodBinding mbinding) {
        if (mbinding.isConstructor() || mbinding.isDefaultConstructor()) {
            return JavaMethod.Kind.J_CONSTRUCTOR;
        } else {
            return JavaMethod.Kind.J_METHOD;
        }
    }
    
    private void collectParameters(List<VariableDeclaration> params) {
        for (VariableDeclaration decl : params) {
            if (decl.resolveBinding() != null) {
                JavaLocalVar param = new JavaLocalVar(decl, this);
                parameters.add(param);
            }
        }
    }
    
    private void collectParameters(ITypeBinding[] types) {
        for (ITypeBinding tbinding : types) {
            JavaLocalVar param = new JavaLocalVar(tbinding, this);
            parameters.add(param);
        }
    }
    
    private void collectLocalVariables(ASTNode node) {
        if (node == null) {
            return;
        }
        LocalDeclarationCollector visitor = new LocalDeclarationCollector(this);
        node.accept(visitor);
        localDecls.addAll(visitor.getLocalDeclarations());
    }
    
    private void collectLambdas(ASTNode node) {
        if (node == null) {
            return;
        }
        LambdaCollector visitor = new LambdaCollector(this);
        node.accept(visitor);
    }
    
    /**
     * Returns the fully-qualified name of this method.
     * @return the fully-qualified method name
     */
    @Override
    public QualifiedName getQualifiedName() {
        return qname;
    }
    
    /**
     * Returns the name of this method.
     * @return the class name
     */
    public String getClassName() {
        return qname.getClassName();
    }
    
    /**
     * Returns the signature of this method.
     * @return the method signature
     */
    public String getSignature() {
        return qname.getMemberSignature();
    }
    
    /**
     * Returns the return type of this method.
     * @return the type name
     */
    public String getReturnType() {
        return returnType;
    }
    
    /**
     * Tests if the return type of this method is primitive.
     * @return {@code true} if the return type of this method is primitive, otherwise {@code false}
     */
    public boolean isPrimitiveReturnType() {
        return returnType != null ? isPrimitiveType(returnType) : false;
    }
    
    /**
     * Tests if the return type of this method is void, that is, it does not have the return type. 
     * @return {@code true} if the return type of this method is void, otherwise {@code false}
     */
    public boolean isVoid() {
        return returnType != null ? JavaElement.isVoid(returnType) : false;
    }
    /**
     * The the value that stores information on the modifiers of this variable.
     * @return the modifier value of this variable
     */
    public int getModifiers() {
        return modifiers;
    }
    
    /**
     * Returns the method binding information on this method.
     * @return the method binding information
     */
    public IMethodBinding getMethodBinding() {
        return binding;
    }
    
    /**
     * Tests if this method exists inside the target project, which can resent the corresponding source code.
     * @return {@code true} if this method exists inside the target project, otherwise {@code false}
     */
    public boolean isInProject() {
        return inProject;
    }
    
    /**
     * A class that declares (and encloses the declaration of) this method.
     * @return the declaring class
     */
    public JavaClass getDeclaringClass() {
        return declaringClass;
    }
    
    /**
     * Tests if this is a method.
     * @return {@code true} if this is a method, otherwise {@code false}
     */
    public boolean isMethod() {
        return kind == JavaMethod.Kind.J_METHOD;
    }
    
    /**
     * Tests if this is a constructor.
     * @return {@code true} if this is a constructor, otherwise {@code false}
     */
    public boolean isConstructor() {
        return kind == JavaMethod.Kind.J_CONSTRUCTOR;
    }
    
    /**
     * Tests if this is a static initializer.
     * @return {@code true} if this is a static initializer, otherwise {@code false}
     */
    public boolean isInitializer() {
        return kind == JavaMethod.Kind.J_INITIALIZER;
    }
    
    /**
     * Tests if this is a lambda expression.
     * @return {@code true} if this is lambda expression, otherwise {@code false}
     */
    public boolean isLambda() {
        return kind == JavaMethod.Kind.J_LAMBDA;
    }
    
    /**
     * Obtains the list of all parameters of this method.
     * @return the parameter list
     */
    public List<JavaLocalVar> getParameters() {
        return parameters;
    }
    
    /**
     * Obtains the number of parameters of this method.
     * @return the number of the parameters
     */
    public int getParameterSize() {
        return parameters.size();
    }
    
    /**
     * Obtains a parameter specified by the index for the parameter list.
     * @param index the index number of the parameter of this method
     * @return the local variable of the found parameter
     */
    public JavaLocalVar getParameter(int index) {
        return (index >= 0 && index < parameters.size()) ? parameters.get(index) : null;
    }
    
    /**
     * Obtains a parameter having a given name for the parameter list.
     * @param name the parameter name
     * @return the local variable of the found parameter
     */
    public JavaLocalVar getParameter(String name) {
        int index = getParameterIndex(name); 
        return (index != -1) ? getParameter(index) : null;
    }
    
    /**
     * Obtains the index value of a parameter having a given name for the parameter list.
     * @param name the parameter name
     * @return the local variable of the found parameter
     */
    public int getParameterIndex(String name) {
        for (int ordinal = 0; ordinal < parameters.size(); ordinal++) {
            JavaLocalVar param = getParameter(ordinal);
            if (param.getName().equals(name)) {
                return ordinal;
            }
        }
        return -1;
    }
    
    /**
     * Obtains the list of local variables used in this method.
     * @return the local variable list
     */
    public List<JavaLocalVar> getLocalVariables() {
        return localDecls;
    }
    
    /**
     * Obtains a local variable having a given name and identification number.
     * @param name the name of the local variable to be retrieved
     * @param id the identification number of the local variable to be retrieved
     * @return the found local variable, or {@code null} if any local variable is not found
     */
    public JavaLocalVar getLocalVariable(String name, int id) {
        return localDecls.stream()
                .filter(jl -> name.equals(jl.getName()) && id == jl.getVariableId()).findFirst().orElse(null);
    }
    
    /**
     * Returns the collection of AST nodes corresponding to the types of exceptions that this method might throw.
     * @return the collection of type nodes for the thrown exceptions
     */
    public Collection<Type> getExceptionTypeNodes() {
        return exceptionTypes.values();
    }
    
    /**
     * Obtains the AST node corresponding to the type of an exception.
     * @param type the type of the thrown exception
     * @return the type node for the the thrown exception, or {@code null} if there is no corresponding type node
     */
    public ASTNode getExceptionTypeNode(String type) {
        return exceptionTypes.get(type);
    }
    
    /**
     * Tests if this method has the public visibility.
     * @return {@code true} if this method has the public visibility, otherwise {@code false}
     */
    public boolean isPublic() {
        return Modifier.isPublic(modifiers);
    }
    
    /**
     * Tests if this method has the protected visibility.
     * @return {@code true} if this method has the protected visibility, otherwise {@code false}
     */
    public boolean isProtected() {
        return Modifier.isProtected(modifiers);
    }
    
    /**
     * Tests if this method has the private visibility.
     * @return {@code true} if this method has the private visibility, otherwise {@code false}
     */
    public boolean isPrivate() {
        return Modifier.isPrivate(modifiers);
    }
    
    /**
     * Tests if this method has the default visibility.
     * @return {@code true} if this method has the default visibility, otherwise {@code false}
     */
    public boolean isDefault() {
        return !isPublic() && !isProtected() && !isPrivate();
    }
    
    /**
     * Tests if this is a final method.
     * @return {@code true} if this is a final method, otherwise {@code false}
     */
    public boolean isFinal() {
        return Modifier.isFinal(modifiers);
    }
    
    /**
     * Tests if this is an abstract method.
     * @return {@code true} if this is an abstract method, otherwise {@code false}
     */
    public boolean isAbstract() {
        return Modifier.isAbstract(modifiers);
    }
    
    /**
     * Tests if this is a static method.
     * @return {@code true} if this is a static method, otherwise {@code false}
     */
    public boolean isStatic() {
        return Modifier.isStatic(modifiers);
    }
    
    /**
     * Tests if this is a synchronized method.
     * @return {@code true} if this is a synchronized method, otherwise {@code false}
     */
    public boolean isSynchronized() {
        return Modifier.isSynchronized(modifiers);
    }
    
    /**
     * Tests if this is a native method.
     * @return {@code true} if this is a native method, otherwise {@code false}
     */
    public boolean isNative() {
        return Modifier.isNative(modifiers);
    }
    
    /**
     * Tests if this is a strict-floating-point method.
     * @return {@code true} if this is a strict-floating-point method, otherwise {@code false}
     */
    public boolean isStrictfp() {
        return Modifier.isStrictfp(modifiers);
    }
    
    /**
     * Obtains the signature of a method having a given method binding information.
     * @param mbinding the method binding information
     * @return the string representing the signature
     */
    public static String getSignature(IMethodBinding mbinding) {
        return mbinding.getName() + "(" + getParameterString(mbinding) + ")";
    }
    
    /**
     * Obtains the string that represents parameters of a method having a given method binding information.
     * @param mbinding the method binding information
     * @return the string representing the parameters
     */
    public static String getParameterString(IMethodBinding mbinding) {
        StringBuilder buf = new StringBuilder();
        ITypeBinding[] bindings = mbinding.getParameterTypes();
        for (int i = 0; i < bindings.length; i++) {
            buf.append(" ");
            if (bindings[i].isTypeVariable() || bindings[i].isCapture()) {
                ITypeBinding[] types = bindings[i].getTypeBounds();
                if (types.length > 0) {
                    buf.append(types[0].getErasure().getQualifiedName());
                } else {
                    buf.append("java.lang.Object");
                }
            } else {
                buf.append(bindings[i].getErasure().getQualifiedName());
            }
        }
        return buf.toString() + " ";
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object obj) {
        return (obj instanceof JavaMethod) ? equals((JavaMethod)obj) : false;
    }
    
    /**
     * Tests if a given method is equal to this method.
     * @param jfield the method to be checked
     * @return the {@code true} if the given method is equal to this method
     */
    public boolean equals(JavaMethod jmethod) {
        return jmethod != null && (this == jmethod || qname.fqn().equals(jmethod.qname.fqn()));
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
        buf.append("\n");
        buf.append("METHOD: ");
        buf.append(getSignature());
        buf.append("@");
        buf.append(getReturnType());
        buf.append(toStringForParameters());
        return buf.toString();
    }
    
    /**
     * Obtains information on all parameters of this method.
     * @return the string representing the information
     */
    public String toStringForParameters() {
        StringBuilder buf = new StringBuilder();
        for (JavaLocalVar param : parameters) {
            buf.append("\n");
            buf.append(" PARAMETER : ");
            buf.append(param.getName());
            buf.append("@");
            buf.append(param.getType());
        }
        return buf.toString();
    }
    
    /**
     * Obtains information on all methods invoked by this method.
     * @return the string representing the information
     */
    protected String toStringCalledMethods() {
        StringBuilder buf = new StringBuilder();
        for (JavaMethod jm : getCalledMethods()) {
            buf.append("\n");
            buf.append(" THIS METHOD CALLS : ");
            buf.append(jm.getSignature());
        }
        return buf.toString();
    }
    
    /**
     * A flag indicating whether the collected binding information is resolved.
     */
    protected boolean resolved = false;
    
    /**
     * The collection of classes corresponding to the types of exceptions.
     */
    protected Set<JavaClass> exceptions = new HashSet<>();
    
    /**
     * The collection of methods that this method invokes.
     */
    protected Set<JavaMethod> calledMethods = new HashSet<>();
    
    /**
     * The collection of methods that invoke this method.
     */
    protected Set<JavaMethod> callingMethods = new HashSet<>();
    
    /**
     * The collection of fields that this method accesses.
     */
    protected Set<JavaField> accessedFields = new HashSet<>();
    
    /**
     * The collection of fields that call this method in their declarations.
     */
    protected Set<JavaField> accessingFields = new HashSet<>();
    
    /**
     * The collection of methods that this method overrides.
     */
    protected Set<JavaMethod> overriddenMethods = null;
    
    /**
     * The collection of methods that override this method.
     */
    protected Set<JavaMethod> overridingMethods = null;
    
    /**
     * Collects additional information on this method.
     * This method is not intended to be invoked by clients, which will be automatically invoked as needed.
     */
    protected void collectInfo() {
        if (!inProject || resolved) {
            return;
        }
        
        boolean resolveOk = true;
        if (!isInitializer()) {
            if (binding != null) {
                resolveOk = resolveOk && findExceptions();
                resolveOk = resolveOk && findCalledMethods();
                resolveOk = resolveOk && findAccessedFields();
            } else {
                resolveOk = false;
            }
        } else {
            resolveOk = resolveOk && findCalledMethods();
            resolveOk = resolveOk && findAccessedFields();
        }
        
        if (!resolveOk) {
            if (declaringClass != null) {
                Logger.getInstance().printUnresolvedError("Method " + getSignature() + " of " +
                    declaringClass.getQualifiedName() + " in " + jfile.getPath());
            } else {
                Logger.getInstance().printUnresolvedError("Method in " + jfile.getPath());
            }
        }
        resolved = true;
    }
    
    private boolean findExceptions() {
        boolean resolveOk = true;
        for (ITypeBinding tbinding : binding.getExceptionTypes()) {
            if (tbinding.isTypeVariable()) {
                break;
            }
            
            JavaClass jc = findDeclaringClass(getJavaProject(), tbinding);
            if (jc != null) {
                exceptions.add(jc);
            } else {
                resolveOk = false;
                Logger.getInstance().printUnresolvedError("Exception type in " + jfile.getPath());
            }
        }
        return resolveOk;
    }
    
    private boolean findCalledMethods() {
        MethodCallCollector visitor = new MethodCallCollector(getJavaProject());
        astNode.accept(visitor);
        if (visitor.isBindingOk()) {
            calledMethods.addAll(visitor.getCalledMethods());
            for (JavaMethod jmethod : calledMethods) {
                jmethod.addCallingMethod(this);
            }
            return true;
        }
        return false;
    }
    
    /**
     * Adds a method that invokes this method.
     * This method is not intended to be invoked by clients.
     * @param jmethod the method to be added
     */
    void addCallingMethod(JavaMethod jmethod) {
        callingMethods.add(jmethod);
    }
    
    private boolean findAccessedFields() {
        FieldAccessCollector visitor = new FieldAccessCollector(getJavaProject());
        astNode.accept(visitor);
        if (visitor.isBindingOk()) {
            accessedFields.addAll(visitor.getAccessedFields());
            for (JavaField jfield : accessedFields) {
                jfield.addAccessingMethod(this);
            }
            return true;
        }
        return false;
    }
    
    /**
     * Adds a field that calls this method in their declarations.
     * This method is not intended to be invoked by clients.
     * @param jmethod the method to be added
     */
    void addAccessingField(JavaField jfield) {
        accessingFields.add(jfield);
    }
    
    /**
     * Obtains classes corresponding to the types of exceptions.
     * @return the collection of the corresponding classes
     */
    public Set<JavaClass> getExceptions() {
        collectInfo();
        return exceptions;
    }
    
    /**
     * Obtains methods that this method invokes.
     * @return the collection of the called methods
     */
    public Set<JavaMethod> getCalledMethods() {
        collectInfo();
        return calledMethods;
    }
    
    /**
     * Obtains methods that this method invokes, which are enclosed in the target project.
     * @return the collection of the called methods
     */
    public Set<JavaMethod> getCalledMethodsInProject() {
        collectInfo();
        return calledMethods
                .stream()
                .filter(jm -> jm.isInProject())
                .collect(Collectors.toSet());
    }
    
    /**
     * Obtains methods that invoke this method.
     * @return the collection of the calling methods
     */
    public Set<JavaMethod> getCallingMethods() {
        collectInfo();
        return callingMethods;
    }
    
    /**
     * Obtains methods that invoke this method, which are enclosed in the target project.
     * @return the collection of the calling methods
     */
    public Set<JavaMethod> getCallingMethodsInProject() {
        collectInfo();
        return callingMethods
                .stream()
                .filter(jm -> jm.isInProject())
                .collect(Collectors.toSet());
    }
    
    /**
     * Obtains fields that this method accesses.
     * @return the collection of the accessed fields
     */
    public Set<JavaField> getAccessedFields() {
        collectInfo();
        return accessedFields;
    }
    
    /**
     * Obtains fields that this method accesses, which are enclosed in the target project.
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
     * Obtains fields that call this method in their declaration.
     * @return the collection of the accessing fields
     */
    public Set<JavaField> getAccessingFields() {
        collectInfo();
        return accessingFields;
    }
    
    /**
     * Obtains fields that call this method in their declaration, which are enclosed in the target project.
     * @return the collection of the accessing fields
     */
    public Set<JavaField> getAccessingFieldsInProject() {
        collectInfo();
        return accessingFields
                .stream()
                .filter(jf -> jf.isInProject())
                .collect(Collectors.toSet());
    }
    
    /**
     * Obtains methods that this method overrides.
     * @return the collection of the overridden methods
     */
    public Set<JavaMethod> getOverriddenMethods() {
        collectInfo();
        if (overriddenMethods == null) {
            findOverriddenMethods();
        }
        if (overriddenMethods == null) {
            overriddenMethods = new HashSet<>();
        }
        return overriddenMethods;
    }
    
    /**
     * Obtains methods that override this method.
     * @return the collection of the overriding methods
     */
    public Set<JavaMethod> getOverridingMethods() {
        collectInfo();
        if (overridingMethods == null) {
            findOverriddenMethods();
        }
        if (overridingMethods == null) {
            overridingMethods = new HashSet<>();
        }
        return overridingMethods;
    }
    
    private void findOverriddenMethods() {
        for (JavaClass jc : declaringClass.getAncestors()) {
            for (JavaMethod jm : jc.getMethods()) {
                if (qname.getMemberSignature().equals(jm.getSignature())) {
                    addOverriddenMethod(jm);
                    jm.addOverridingMethod(this);
                }
            }
        }
    }
    
    /**
     * Adds a method that this method overrides.
     * This method is not intended to be invoked by clients.
     * @param jm the overridden method to be added
     */
    void addOverriddenMethod(JavaMethod jm) {
        if (overriddenMethods == null) {
            overriddenMethods = new HashSet<>();
        }
        overriddenMethods.add(jm);
    }
    
    /**
     * Adds a method that overrides this method.
     * This method is not intended to be invoked by clients.
     * @param jm the overriding method to be added
     */
    void addOverridingMethod(JavaMethod jm) {
        if (overridingMethods == null) {
            overridingMethods = new HashSet<>();
        }
        overridingMethods.add(jm);
    }
}
