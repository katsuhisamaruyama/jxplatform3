/*
 *  Copyright 2020
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.cfg;

import org.jtool.srcmodel.QualifiedName;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.SuperMethodInvocation;
import org.eclipse.jdt.core.dom.SuperConstructorInvocation;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.Type;
import org.eclipse.jdt.core.dom.IMethodBinding;
import org.eclipse.jdt.core.dom.ITypeBinding;
import org.eclipse.jdt.core.dom.Modifier;
import java.util.List;
import java.util.ArrayList;
import java.util.Set;

/**
 * An class that represents reference to a method or a constructor.
 * 
 * @author Katsuhisa Maruyama
 */
public class JMethodReference extends JReference {
    
    /**
     * The AST node corresponding to the name of this reference.
     */
    protected ASTNode nameNode;
    
    /**
     * A flag that indicates whether this is a reference to a method.
     */
    private boolean isMethod;
    
    /**
     * A flag that indicates whether this is a reference to a constructor.
     */
    private boolean isConstructor;
    
    /**
     * A flag that indicates whether this is a reference to an enum.
     */
    private boolean isEnum;
    
    /**
     * A flag that indicates whether this is a reference to a variable-arity method.
     */
    private boolean isVarargs;
    
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
     * The collection of the approximated types of receiver associated to this node.
     */
    private Set<String> approximatedTypes;
    
    /**
     * Creates a new object that represents a reference to a method or a constructor.
     * @param node the AST node corresponding to this reference
     * @param nameNode the node of the name part of this method reference
     * @param mbinding the method binding information on this reference
     * @param args the AST nodes corresponding to the arguments of this method reference
     */
    public JMethodReference(ASTNode node, ASTNode nameNode, IMethodBinding mbinding, List<Expression> args) {
        super(node);
        
        this.nameNode = nameNode;
        
        IMethodBinding binding = mbinding.getMethodDeclaration();
        this.isMethod = isMethod(binding);
        this.isConstructor = isConstructor(binding);
        this.isEnum = binding.getDeclaringClass().isEnum();
        this.isVarargs = binding.isVarargs();
        
        this.enclosingClassName = findEnclosingClassName(node);
        this.enclosingMethodName = findEnclosingMethodName(node);
        this.declaringClassName = getQualifiedClassName(binding.getDeclaringClass().getTypeDeclaration().getErasure());
        this.declaringMethodName = "";
        
        String signature = getSignature(binding);
        if (isConstructor) {
            if (binding.getName().length() > 0) {
                signature = binding.getName() + "(" + getParameterString(binding) + ")";
            } else {
                if (nameNode instanceof Type) {
                    Type instanceType = (Type)nameNode;
                    this.declaringClassName = getQualifiedClassName(instanceType.resolveBinding().getErasure());
                }
                int index = declaringClassName.lastIndexOf(".");
                String className = index != -1 ? declaringClassName.substring(index + 1) : declaringClassName;
                signature = className + "(" + getParameterString(binding) + ")";
            }
            
            this.type = declaringClassName;
        } else {
            this.type = binding.getReturnType().getErasure().getQualifiedName();
        }
        
        this.fqn = new QualifiedName(declaringClassName, signature);
        this.referenceForm = "";
        this.isPrimitiveType = binding.getReturnType().isPrimitive();
        this.modifiers = binding.getModifiers();
        this.inProject = binding.getDeclaringClass().isFromSource();
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
     * Returns the receiver name of the referenced element.
     * @return the receiver name, or the empty string if the element does not have any receiver
     */
    public String getReceiverName() {
        if (receiver != null) {
            receiver.getName();
        }
        return super.getReceiverName();
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
     * Tests if this is a reference to an enum.
     * @return {@code true} if this is a method reference, otherwise {@code false}
     */
    public boolean isEnum() {
        return isEnum;
    }
    
    /**
     * Tests if this is a reference to a variable-arity method.
     * @return {@code true} if this is a variable-arity method reference, otherwise {@code false}
     */
    public boolean isVarargs() {
        return isVarargs;
    }
    
    /**
     * Tests if this is a reference to a constructor within the parent class.
     * @return {@code true} if this is a reference to a constructor within the parent, otherwise {@code false}
     */
    public boolean isSuper() {
        return astNode instanceof SuperMethodInvocation || astNode instanceof SuperConstructorInvocation;
    }
    
    /**
     * Tests if this is a reference to a local method or constructor within the class itself.
     * @return {@code true} if this is a reference to a local method or constructor within the class itself, otherwise {@code false}
     */
    public boolean isLocal() {
        return receiver != null ? "this".equals(receiver.getName()) : false;
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
        if (receiver != null) {
            referenceForm = new QualifiedName(receiver.getName(), fqn.getMemberSignature()).fqn();
        }
    }
    
    /**
     * Returns the node corresponding to the receiver of this method reference.
     * @return the receiver node
     */
    public CFGReceiver getReceiver() {
        return receiver;
    }
    
    /**
     * Sets the approximated types of receiver associated to this node.
     * @param types the collection of the approximated types to be set
     */
    public void setApproximatedTypes(Set<String> types) {
        approximatedTypes = types;
    }
    
    /**
     * Returns the approximated types of receiver associated to this node.
     * These types include classes declaring method that might be dynamically called.
     * In the case of a field access, the approximated types are not supported
     * because no dynamic binding is performed.
     * @return the collection of the approximated types
     */
    public Set<String> getApproximatedTypes() {
        return approximatedTypes;
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
