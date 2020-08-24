/*
 *  Copyright 2020
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.cfg;

import org.jtool.srcmodel.QualifiedName;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.SuperMethodInvocation;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.IMethodBinding;
import org.eclipse.jdt.core.dom.ITypeBinding;
import org.eclipse.jdt.core.dom.Modifier;
import java.util.List;
import java.util.ArrayList;

/**
 * An class that represents reference to a method or a constructor.
 * 
 * @author Katsuhisa Maruyama
 */
public class JMethodReference extends JReference {
    
    /**
     * A flag that indicates whether this is a reference to a method.
     */
    private boolean isMethod;
    
    /**
     * A flag that indicates whether this is a reference to a constructor.
     */
    private boolean isConstructor;
    
    /**
     * A flag that indicates whether this is a reference a method within the same class.
     */
    private boolean isLocal;
    
    /**
     * A flag that indicates whether this is a reference to a method within the parent class.
     */
    private boolean isSuper;
    
    /**
     * The AST node corresponding to the name of this reference.
     */
    protected ASTNode nameNode;
    
    /**
     * The collection of AST nodes corresponding to the arguments of this method reference.
     */
    private List<Expression> arguments = new ArrayList<>();
    
    /**
     * The collection of the types of arguments of the referencing method.
     */
    private List<String> argumentTypes = new ArrayList<>();
    
    /**
     * The collection stores information on whether each argument of this method reference is primitive.
     */
    private List<Boolean> argumentPrimitiveTypes = new ArrayList<>();
    
    /**
     * The collection of the exception types that the referenced method might throw.
     */
    private List<ITypeBinding> exceptionTypes = new ArrayList<>();
    
    /**
     * The node corresponding to the receiver of this method reference.
     */
    private CFGReceiver receiver = null;
    
    /**
     * Creates a new object that represents a reference to a method.
     * @param node the AST node corresponding to this reference
     * @param nameNode the node of the name part of this method reference
     * @param mbinding the method binding information on this reference
     * @param args the AST nodes corresponding to the arguments of this method reference
     */
    public JMethodReference(ASTNode node, ASTNode nameNode, IMethodBinding mbinding, List<Expression> args) {
        super(node);
        
        this.nameNode = nameNode;
        
        IMethodBinding binding = mbinding.getMethodDeclaration();
        this.enclosingClassName = findEnclosingClassName(node);
        this.enclosingMethodName = findEnclosingMethodName(node);
        this.declaringClassName = getQualifiedClassName(binding.getDeclaringClass().getTypeDeclaration());
        this.declaringMethodName = "";
        
        this.fqn = new QualifiedName(declaringClassName, getSignature(binding));
        this.referenceForm = "";
        this.isConstructor = isConstructor(binding);
        if (isConstructor) {
            this.type = declaringClassName;
        } else {
            this.type = binding.getReturnType().getQualifiedName();
        }
        this.isPrimitiveType = binding.getReturnType().isPrimitive();
        this.modifiers = binding.getModifiers();
        this.inProject = binding.getDeclaringClass().isFromSource();
        this.isMethod = isMethod(binding);
        this.isLocal = enclosingClassName.equals(declaringClassName);
        this.isSuper = node instanceof SuperMethodInvocation;
        for (ITypeBinding tbinding : mbinding.getExceptionTypes()) {
            this.exceptionTypes.add(tbinding);
        }
        
        this.arguments.addAll(args);
        setArgumentTypes(binding);
    }
    
    /**
     * Returns the AST node corresponding to the name of this reference.
     * @return the AST node of the name part
     */
    public ASTNode getNameNode() {
        return nameNode;
    }
    
    /**
     * Tests if this is a reference to a method.
     * @return always {@code true} that indicates a method reference
     */
    @Override
    public boolean isMethodCall() {
        return true;
    }
    
    /**
     * Tests if this is a reference to a method.
     * @return {@code true} if this is a method reference, otherwise {@code false}
     */
    public boolean isMethod() {
        return isMethod;
    }
    
    /**
     * Tests if this is a reference to a constructor.
     * @return {@code true} if this is a method reference, otherwise {@code false}
     */
    public boolean isConstructor() {
        return isConstructor;
    }
    
    /**
     * Tests if this is a reference a method within the same class.
     * @return {@code true} if this is a reference to a method within the same class, otherwise {@code false}
     */
    public boolean isLocal() {
        return isLocal;
    }
    
    /**
     * Tests if this is a reference to a method within the parent class.
     * @return {@code true} if this is a reference to a method within the parent, otherwise {@code false}
     */
    public boolean isSuper() {
        return isSuper;
    }
    
    /**
     * Tests if this is a reference to a final method.
     * @return {@code true} if this is a final method reference, otherwise {@code false}
     */
    public boolean isFinal() {
        return Modifier.isFinal(modifiers);
    }
    
    /**
     * Tests if this is a reference to a abstract method.
     * @return {@code true} if this is a abstract method reference, otherwise {@code false}
     */
    public boolean isAbstract() {
        return Modifier.isAbstract(modifiers);
    }
    
    /**
     * Tests if this is a reference to a static method.
     * @return {@code true} if this is a static method reference, otherwise {@code false}
     */
    public boolean isStatic() {
        return Modifier.isStatic(modifiers);
    }
    
    /**
     * Tests if this is a reference to a synchronized method.
     * @return {@code true} if this is a synchronized method reference, otherwise {@code false}
     */
    public boolean isSynchronized() {
        return Modifier.isSynchronized(modifiers);
    }
    
    /**
     * Tests if this is a reference to a native method.
     * @return {@code true} if this is a native method reference, otherwise {@code false}
     */
    public boolean isNative() {
        return Modifier.isNative(modifiers);
    }
    
    /**
     * Tests if this is a reference to a strict-floating-point method.
     * @return {@code true} if this is a strict-floating-point method reference, otherwise {@code false}
     */
    public boolean isStrictfp() {
        return Modifier.isStrictfp(modifiers);
    }
    
    /**
     * Returns the exception types that the referenced method might throw.
     * @return the collection of type binding information for the thrown exceptions
     */
    public List<ITypeBinding> getExceptionTypes() {
        return exceptionTypes;
    }
    
    /**
     * Returns argument of the referencing method.
     * @return the collection of the arguments
     */
    public List<Expression> getArguments() {
        return arguments;
    }
    
    /**
     * Return the number of arguments of the referencing method.
     * @return the number of the arguments
     */
    public int getArgumentSize() {
        return arguments.size();
    }
    
    /**
     * Obtains an argument specified by the index for the argument list.
     * @param index the index number of the argument of the referencing method
     * @return the AST node of the found argument
     */
    public Expression getArgument(int index) {
        return (index >= 0 && index < arguments.size()) ? arguments.get(index) : null;
    }
    
    /**
     * Sets the types of arguments of the referencing method.
     * @param mbinding the method binding information on the method
     */
    private void setArgumentTypes(IMethodBinding mbinding) {
        ITypeBinding[] types = mbinding.getParameterTypes();
        for (int i = 0; i < types.length; i++) {
            argumentTypes.add(types[i].getTypeDeclaration().getQualifiedName());
            argumentPrimitiveTypes.add(types[i].getTypeDeclaration().isPrimitive());
        }
    }
    
    /**
     * Obtains the type of an argument specified by the index for the argument list.
     * @param index the index number of the argument of the referencing method
     * @return the AST node of the found argument
     */
    public String getArgumentType(int index) {
        return (index >= 0 && index < argumentTypes.size()) ? argumentTypes.get(index) : "";
    }
    
    /**
     * Tests if the type of an argument specified by the index is primitive.
     * @param index the index number of the argument of the referencing method
     * @return {@code true} if the type of the argument is primitive, otherwise {@code false}
     */
    public boolean getArgumentPrimitiveType(int index) {
        return (index >= 0 && index < argumentPrimitiveTypes.size())
                ? argumentPrimitiveTypes.get(index) : false;
    }
    
    /**
     * Tests if this method reference has a receiver.
     * @return {@code true} this reference has a receiver, otherwise {@code false}
     */
    public boolean hasReceiver() {
        return receiver != null;
    }
    
    /**
     * Sets a receiver of this method reference.
     * @param receiver the receiver node to be set
     */
    public void setReceiver(CFGReceiver receiver) {
        this.receiver = receiver;
        referenceForm = new QualifiedName(receiver.getName(), fqn.getMemberSignature()).fqn();
    }
    
    /**
     * Returns the node corresponding to the receiver of this method reference.
     * @return the receiver node
     */
    public CFGReceiver getReceiver() {
        return receiver;
    }
    
    /**
     * Tests if this is a reference to the same method.
     * @return {@code true} if the referencing method is same as the referenced method, otherwise {@code false}
     */
    public boolean callSelfDirectly() {
        return fqn.fqn().equals(new QualifiedName(enclosingClassName, enclosingMethodName).fqn());
    }
    
    /**
     * Obtains information on this method reference.
     * @return the string representing the information
     */
    @Override
    public String toString() {
        return(callSelfDirectly()) ? fqn.getMemberSignature() + "@" + type : fqn.fqn() + "@" + type;
    }
}
