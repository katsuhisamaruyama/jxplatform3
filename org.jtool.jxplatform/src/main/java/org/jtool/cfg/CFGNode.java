/*
 *  Copyright 2022
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.cfg;

import org.jtool.graph.GraphEdge;
import org.jtool.graph.GraphNode;
import org.jtool.graph.GraphElement;
import org.jtool.pdg.PDGNode;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.AssertStatement;
import org.eclipse.jdt.core.dom.Assignment;
import org.eclipse.jdt.core.dom.BooleanLiteral;
import org.eclipse.jdt.core.dom.BreakStatement;
import org.eclipse.jdt.core.dom.CatchClause;
import org.eclipse.jdt.core.dom.CharacterLiteral;
import org.eclipse.jdt.core.dom.ClassInstanceCreation;
import org.eclipse.jdt.core.dom.ConditionalExpression;
import org.eclipse.jdt.core.dom.ConstructorInvocation;
import org.eclipse.jdt.core.dom.ContinueStatement;
import org.eclipse.jdt.core.dom.DoStatement;
import org.eclipse.jdt.core.dom.EmptyStatement;
import org.eclipse.jdt.core.dom.EnhancedForStatement;
import org.eclipse.jdt.core.dom.EnumConstantDeclaration;
import org.eclipse.jdt.core.dom.ExpressionStatement;
import org.eclipse.jdt.core.dom.ForStatement;
import org.eclipse.jdt.core.dom.IfStatement;
import org.eclipse.jdt.core.dom.LabeledStatement;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.core.dom.Name;
import org.eclipse.jdt.core.dom.NullLiteral;
import org.eclipse.jdt.core.dom.NumberLiteral;
import org.eclipse.jdt.core.dom.PostfixExpression;
import org.eclipse.jdt.core.dom.PrefixExpression;
import org.eclipse.jdt.core.dom.ReturnStatement;
import org.eclipse.jdt.core.dom.SingleVariableDeclaration;
import org.eclipse.jdt.core.dom.StringLiteral;
import org.eclipse.jdt.core.dom.SuperConstructorInvocation;
import org.eclipse.jdt.core.dom.SuperMethodInvocation;
import org.eclipse.jdt.core.dom.SwitchCase;
import org.eclipse.jdt.core.dom.SwitchExpression;
import org.eclipse.jdt.core.dom.SwitchStatement;
import org.eclipse.jdt.core.dom.SynchronizedStatement;
import org.eclipse.jdt.core.dom.ThrowStatement;
import org.eclipse.jdt.core.dom.TryStatement;
import org.eclipse.jdt.core.dom.TypeDeclarationStatement;
import org.eclipse.jdt.core.dom.TypeLiteral;
import org.eclipse.jdt.core.dom.VariableDeclarationExpression;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;
import org.eclipse.jdt.core.dom.VariableDeclarationStatement;
import org.eclipse.jdt.core.dom.WhileStatement;
import java.util.Set;
import java.util.List;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.stream.Collectors;

/**
 * A node of a CFG.
 * 
 * @author Katsuhisa Maruyama
 */
public class CFGNode extends GraphNode {
    
    /**
     * The AST node corresponding to this node.
     */
    protected ASTNode astNode;
    
    /**
     * The kind of this node.
     */
    protected Kind kind;
    
    /**
     * The PDG node corresponding to this node.
     */
    protected PDGNode pdgNode = null;
    
    /**
     * The number prepared for generating the identification number when creating a new CFG node.
     */
    private static long num = 1;
    
    /**
     * The kind of a CFG node.
     */
    public enum Kind {
        
        /**
         * Representing the start node for a class declaration, which is an instance of {@code CFGClassEntry} in {@code CCFG}.
         * It corresponds to {@code TypeDeclaration} or {@code AnonymousClassDeclaration} in the JDT Core module.
         */
        classEntry,
        
        /**
         * Representing the start node for an interface declaration, which is an instance of {@code CFGClassEntry} in {@code CCFG}.
         * It corresponds to {@code TypeDeclaration} or {@code AnonymousClassDeclaration} in the JDT Core module.
         */
        interfaceEntry,
        
        /**
         * Representing the start node for an enum declaration, which is an instance of {@code CFGClassEntry} in {@code CCFG}.
         * It corresponds to {@code EnumDeclaration} in the JDT Core module.
         */
        enumEntry,
        
        /**
         * Representing the end node for a class declaration, which is an instance of {@code CFGExit} in {@code CCFG}.
         * There is no corresponding node in the JDT Core module.
         */
        classExit,
        
        /**
         * Representing the end node for an interface declaration, which is an instance of {@code CFGExit} in {@code CCFG}.
         * There is no corresponding node in the JDT Core module.
         */
        interfaceExit,
        
        /**
         * Representing the end node for an enum declaration, which is an instance of {@code CFGExit} in {@code CCFG}.
         * There is no corresponding node in the JDT Core module.
         */
        enumExit,
        
        /**
         * Representing the start node for a method declaration, which is an instance of {@code CFGMethodEntry} in {@code CFG}.
         * It corresponds to {@code MethodDeclaration} in the JDT Core module.
         */
        methodEntry,
        
        /**
         * Representing the start node of a constructor declaration, which is an instance of {@code CFGMethodEntry} in {@code CFG}.
         * It corresponds to {@code MethodDeclaration} in the JDT Core module.
         */
        constructorEntry,
        
        /**
         * Representing the start node for an initializer declaration, which is an instance of {@code CFGInitializerEntry} in {@code CFG}.
         * It corresponds to {@code Initializer} in the JDT Core module.
         */
        initializerEntry,
        
        /**
         * Representing the start node for a field declaration, which is an instance of {@code CFGFieldEntry} in {@code CFG}.
         * It corresponds to {@code VariableDeclarationFragment} or {@code FieldDeclaration} in the JDT Core module.
         */
        fieldEntry,
        
        /**
         * Representing the start node for an enum constant declaration, which is an instance of {@code CFGFieldEntry} in {@code CFG}.
         * It corresponds to {@code EnumConstantDeclaration} in the JDT Core module.
         */
        enumConstantEntry,
        
        /**
         * Representing the end node for a method declaration, which is an instance of {@code CFGExit} in {@code CFG}.
         * There is no corresponding node in the JDT Core module.
         */
        methodExit,
        
        /**
         * Representing the end node for a constructor declaration, which is an instance of {@code CFGExit} in {@code CFG}.
         * There is no corresponding node in the JDT Core module.
         */
        constructorExit,
        
        /**
         * Representing the end node for an initializer declaration, which is an instance of {@code CFGExit} in {@code CFG}.
         * There is no corresponding node in the JDT Core module.
         */
        initializerExit,
        
        /**
         * Representing the end node for a field declaration, which is an instance of {@code CFGExit} in {@code CFG}.
         * There is no corresponding node in the JDT Core module.
         */
        fieldExit,
        
        /**
         * Representing the end node for an enum constant declaration, which is an instance of {@code CFGExit} in {@code CFG}.
         * There is no corresponding node in the JDT Core module.
         */
        enumConstantExit,
        
        /**
         * Representing the node for an assignment expression, which is an instance of {@code CFGStatement} in {@code CFG}.
         * It corresponds to {@code Assignment} in the JDT Core module.
         */
        assignment,
        
        /**
         * Representing the node for a lambda expression, which is an instance of {@code CFGStatement} in {@code CFG}.
         * It corresponds to {@code LambdaExpression} in the JDT Core module.
         */
        lambda,
        
        /**
         * Representing the node for a conditional expression, which is an instance of {@code CFGStatement} in {@code CFG}.
         * It corresponds to {@code ConditionalExpression} in the JDT Core module.
         */
        conditionalExpression,
        
        /**
         * Representing the node for a switch expression, which is an instance of {@code CFGStatement} in {@code CFG}.
         * It corresponds to {@code SwitchExpression} in the JDT Core module.
         */
        switchExpression,
        
        /**
         * Representing the node for a method call expression, which is an instance of {@code CFGMethodCall} in {@code CFG}.
         * It corresponds to {@code MethodInvocation} or {@code SuperMethodInvocation} in the JDT Core module.
         */
        methodCall,
        
        /**
         * Representing the node for a constructor call expression, which is an instance of {@code CFGMethodCall} in {@code CFG}.
         * It corresponds to {@code ConstructorInvocation} or {@code SuperConstructorInvocation} in the JDT Core module.
         */
        constructorCall,
        
        /**
         * Representing the node for an instance creation ({@code new}) expression which is an instance of {@code CFGMethodCall} in {@code CFG}.
         * It corresponds to {@code InstanceCreation} in the JDT Core module.
         */
        instanceCreation,
        
        /**
         * Representing the node for a field variable declaration, which is an instance of {@code CFGStatement} in {@code CFG}.
         * It corresponds to {@code VariableDeclarationFragment} in the JDT Core module.
         */
        fieldDeclaration,
        
        /**
         * Representing the node for an enum constant variable declaration, which is an instance of {@code CFGStatement} in {@code CFG}.
         * It corresponds to {@code VariableDeclarationFragment} in the JDT Core module.
         */
        enumConstantDeclaration,
        
        /**
         * Representing the node for a local variable declaration, which is an instance of {@code CFGStatement} in {@code CFG}.
         * It corresponds to {@code VariableDeclarationFragment} in the JDT Core module.
         */
        localDeclaration,
        
        /**
         * Representing the node for an assert statement, which is an instance of {@code CFGStatement} in {@code CFG}.
         * It corresponds to {@code AssertStatement} in the JDT Core module.
         */
        assertSt,
        
        /**
         * Representing the node for a break statement, which is an instance of {@code CFGStatement} in {@code CFG}.
         * It corresponds to {@code BreakStatement} in the JDT Core module.
         */
        breakSt,
        
        /**
         * Representing the node for a continue statement, which is an instance of {@code CFGStatement} in {@code CFG}.
         * It corresponds to {@code ContinueStatement} in the JDT Core module.
         */
        continueSt,
        
        /**
         * Representing the node for a do statement, which is an instance of {@code CFGStatement} in {@code CFG}.
         * It corresponds to {@code DoStatement} in the JDT Core module.
         */
        doSt,
        
        /**
         * Representing the node for a for statement, which is an instance of {@code CFGStatement} in {@code CFG}.
         * It corresponds to {@code ForStatement} in the JDT Core module.
         */
        forSt,
        
        /**
         * Representing the node for an enhanced-for statement, which is an instance of {@code CFGStatement} in {@code CFG}.
         * It corresponds to {@code EnhancedForStatement} in the JDT Core module.
         */
        enhancedForSt,
        
        /**
         * Representing the node for an if statement, which is an instance of {@code CFGStatement} in {@code CFG}.
         * It corresponds to {@code IfStatement} in the JDT Core module.
         */
        ifSt,
        
        /**
         * Representing the node for a return statement, which is an instance of {@code CFGStatement} in {@code CFG}.
         * It corresponds to {@code ReturnStatement} in the JDT Core module.
         */
        returnSt,
        
        /**
         * Representing the node for a switch case statement, which is an instance of {@code CFGStatement} in {@code CFG}.
         * It corresponds to {@code SwitchCase} in the JDT Core module.
         */
        switchCase,
        
        /**
         * Representing the node for a switch-default statement, which is an instance of {@code CFGStatement} in {@code CFG}.
         * It corresponds to {@code SwitchCase} in the JDT Core module.
         */
        switchDefault,
        
        /**
         * Representing the node for a while statement, which is an instance of {@code CFGStatement} in {@code CFG}.
         * It corresponds to {@code WhileStatement} in the JDT Core module.
         */
        whileSt,
        
        /**
         *Representing the node for an empty statement, which is an instance of {@code CFGStatement} in {@code CFG}.
         * It corresponds to {@code EmptyStatement} in the JDT Core module.
         */
        emptySt,
        
        /**
         * Representing the node for a label statement, which is an instance of {@code CFGStatement} in {@code CFG}.
         * It corresponds to {@code Identifier} of {@code LabeledStatement} in the JDT Core module.
         */
        labelSt,
        
        /**
         * Representing the node for a switch statement, which is an instance of {@code CFGStatement} in {@code CFG}.
         * It corresponds to {@code SwitchStatement} in the JDT Core module.
         */
        switchSt,
        
        /**
         * Representing the node for a synchronized statement, which is an instance of {@code CFGStatement} in {@code CFG}.
         * It corresponds to {@code SynchronizedStatement} in the JDT Core module.
         */
        synchronizedSt,
        
        /**
         * Representing the node for a throw statement, which is an instance of {@code CFGStatement} in {@code CFG}.
         * It corresponds to {@code ThrowStatement} in the JDT Core module.
         */
        throwSt,
        
        /**
         * Representing the node for a try statement, which is an instance of {@code CFGStatement} in {@code CFG}.
         * It corresponds to {@code TryStatement} in the JDT Core module.
         */
        trySt,
        
        /**
         * Representing the node for a catch clause, which is an instance of {@code CFGStatement} in {@code CFG}.
         * It corresponds to {@code CatchClause} of {@code TryStatement} in the JDT Core module.
         */
        catchClause,
        
        /**
         * Representing the node for a finally clause, which is an instance of {@code CFGStatement} in {@code CFG}.
         * It corresponds to {@code Block} of {@code TryStatement} in the JDT Core module.
         */
        finallyClause,
        
        /**
         * Representing the node for a throws clause, which is an instance of {@code CFGStatement} in {@code CFG}.
         * It corresponds to the thrown exception type of {@code MethodDeclaration} in the JDT Core module.
         */
        throwsClause,
        
        /**
         * Representing the node for a formal-in parameter in a method declaration, which is an instance of {@code CFGParameter}.
         * It corresponds to {@code VariableDeclaration} in the JDT Core module.
         */
        formalIn,
        
        /**
         * Representing the node for a formal-out parameter in a method declaration, which is an instance of {@code CFGParameter}.
         * It corresponds to {@code VariableDeclaration} in the JDT Core module.
         */
        formalOut,
        
        /**
         * Representing the node for an actual-in parameter in a method call, which is an instance of {@code CFGParameter}.
         * It corresponds to {@code Expression} in the JDT Core module.
         */
        actualIn,
        
        /**
         * Representing the node for an actual-out parameter in a method call, which is an instance of {@code CFGParameter}.
         * It corresponds to {@code Expression} in the JDT Core module.
         */
        actualOut,
        
        /**
         * Representing the node for an actual-out parameter regarding the field value in a method call, which is an instance of {@code CFGParameter}.
         * It corresponds to {@code CFGMethodCall} in the JDT Core module.
         */
        actualOutByFieldAccess,
        
        /**
         * Representing the node for a receiver object in a method call, which is an instance of {@code CFGParameter}.
         * It corresponds to {@code Expression} in the JDT Core module.
         */
        receiver,
        
        /**
         * Representing the merge node of a branch or a loop, which is an instance of {@code CFGMerge} in {@code CFG}.
         * There is no corresponding node in the JDT Core module.
         */
        merge,
        
        /**
         * Representing the dummy node, which is an instance of {@code CFGDummy} in {@code CFG}.
         * There is no corresponding node in the JDT Core module.
         */
        dummy;
    }
    
    /**
     * Creates a new object that represents a dummy node.
     * This method is not intended to be invoked by clients.
     */
    public CFGNode() {
        super(0);
    }
    
    /**
     * Creates a new object that represents a CFG node.
     * This method is not intended to be invoked by clients.
     * @param node the ASt node corresponding to this node
     * @param kind the kind of this node
     */
    public CFGNode(ASTNode node, Kind kind) {
        super(0);
        
        this.astNode = node;
        this.kind = kind;
        
        if (kind == Kind.dummy) {
            super.setId(0);
        } else {
            super.setId(num);
            num++;
        }
    }
    
    /**
     * Resets the identification number for CFG nodes.
     * This method is not intended to be invoked by clients.
     */
    public static void resetId() {
        num = 1;
    }
    
    /**
     * Sets the AST node corresponding to this node.
     * This method is not intended to be invoked by clients.
     * @param node the AST node to be set
     */
    public void setASTNode(ASTNode node) {
        astNode = node;
    }
    
    /**
     * Returns the AST node corresponding to this node.
     * @return the corresponding AST node
     */
    public ASTNode getASTNode() {
        return astNode;
    }
    
    /**
     * Sets the kind of this node.
     * This method is not intended to be invoked by clients.
     * @param kind the kind to be set
     */
    public void setKind(Kind kind) {
        this.kind = kind;
    }
    
    /**
     * Returns the kind of this node.
     * @return the kind of this node
     */
    public Kind getKind() {
        return kind;
    }
    
    /**
     * Sets the PDG node corresponding to this node.
     * This method is not intended to be invoked by clients.
     * @param node the PDG node to be set
     */
    public void setPDGNode(PDGNode node) {
        assert node != null;
        
        pdgNode = node;
    }
    
    /**
     * Returns the PDG node corresponding to this node.
     * @return the corresponding PDG node
     */
    public PDGNode getPDGNode() {
        return pdgNode;
    }
    
    /**
     * Obtains predecessors of this node.
     * @return the collection of the predecessors
     */
    public Set<CFGNode> getPredecessors() {
        return convertNodes(getSrcNodes());
    }
    
    /**
     * Obtains successors of this node.
     * @return the collection of the successors
     */
    public Set<CFGNode> getSuccessors() {
        return convertNodes(getDstNodes());
    }
    
    /**
     * Returns the number of predecessors of this node.
     * @return the the number of the predecessors
     */
    public int getNumOfPredecessors() {
        return getSrcNodes().size();
    }
    
    /**
     * Returns the number of successors of this node.
     * @return the the number of the successors
     */
    public int getNumOfSuccessors() {
        return getDstNodes().size();
    }
    
    /**
     * Obtains control flow edges incoming to this node.
     * @return the collection of the incoming edges
     */
    public List<ControlFlow> getIncomingFlows() {
        return convertEdges(getIncomingEdges());
    }
    
    /**
     * Obtains control flow edges outgoing from this node.
     * @return the collection of the outgoing edges
     */
    public List<ControlFlow> getOutgoingFlows() {
        return convertEdges(getOutgoingEdges());
    }
    
    /**
     * Obtains true control flow edges incoming to this node.
     * @return the collection of the incoming true edges
     */
    public List<ControlFlow> getIncomingTrueFlows() {
        return getIncomingFlows().stream()
                .filter(edge -> edge.isTrue()).collect(Collectors.toList());
    }
    
    /**
     * Obtains true control flow edges incoming to this node.
     * @return the collection of the incoming false edges
     */
    public List<ControlFlow> getIncomingFalseFlows() {
        return getIncomingFlows().stream()
                .filter(edge -> edge.isFalse()).collect(Collectors.toList());
    }
    
    /**
     * Obtains true control flow edges outgoing from this node.
     * @return the outgoing true edge
     */
    public ControlFlow getOutgoingTrueFlow() {
        return getOutgoingFlows().stream()
                .filter(edge -> edge.isTrue()).findFirst().orElse(null);
    }
    
    /**
     * Obtains true control flow edges outgoing from this node.
     * @return the outgoing false edge
     */
    public ControlFlow getOutgoingFalseFlow() {
        return getOutgoingFlows().stream()
                .filter(edge -> edge.isFalse()).findFirst().orElse(null);
    }
    
    /**
     * Converts the collection of {@code GraphNode} objects into that of {@code CFGNode} objects.
     * @param nodes the collection of {@code GraphNode} objects
     * @return the collection of {@code CFGNode} objects
     */
    private Set<CFGNode> convertNodes(Set<GraphNode> nodes) {
        return nodes.stream().map(node -> (CFGNode)node).collect(Collectors.toSet());
    }
    
    /**
     * Converts the collection of {@code GraphEdge} objects into that of {@code ControlFlow} objects.
     * @param nodes the collection of {@code GraphEdge} objects
     * @return the collection of {@code ControlFlow} objects
     */
    private List<ControlFlow> convertEdges(List<GraphEdge> edges) {
        return edges.stream().map(edge -> (ControlFlow)edge).collect(Collectors.toList());
    }
    
    /**
     * Tests if this node causes branching, which has multiple outgoing control flow edges.
     * @return {@code true} if this node causes branching, otherwise {@code false}
     */
    public boolean isBranch() { 
        return getOutgoingEdges().size() > 1;
    }
    
    /**
     * Tests if this node causes selection.
     * @return {@code true} if this node causes selection, otherwise {@code false}
     */
    public boolean isSelection() {
        return kind == Kind.ifSt || kind == Kind.switchSt || kind == Kind.switchCase ||
               kind == Kind.switchDefault || kind == Kind.switchExpression;
    }
    
    /**
     * Tests if this node causes looping.
     * @return {@code true} if this node causes looping, otherwise {@code false}
     */
    public boolean isLoop() {
        return kind == Kind.whileSt || kind == Kind.doSt || kind == Kind.forSt || kind == Kind.enhancedForSt;
    }
    
    /**
     * Tests if this node causes joining, which has multiple incoming control flow edges.
     * @return {@code true} if this node causes joining, otherwise {@code false}
     */
    public boolean isJoin() {
        return getIncomingEdges().size() > 1;
    }
    
    /**
     * Tests if this is an entry node.
     * @return {@code true} if this is an entry node, otherwise {@code false}
     */
    public boolean isEntry() {
        return kind == Kind.classEntry ||
               kind == Kind.interfaceEntry ||
               kind == Kind.enumEntry ||
               kind == Kind.methodEntry ||
               kind == Kind.constructorEntry ||
               kind == Kind.fieldEntry ||
               kind == Kind.initializerEntry ||
               kind == Kind.enumConstantEntry;
    }
    
    /**
     * Tests if this is a class entry node.
     * @return {@code true} if this is a class entry node, otherwise {@code false}
     */
    public boolean isClassEntry() {
        return kind == Kind.classEntry;
    }
    
    /**
     * Tests if this is an interface entry node.
     * @return {@code true} if this is an interface entry node, otherwise {@code false}
     */
    public boolean isInterfaceEntry() {
        return kind == Kind.interfaceEntry;
    }
    
    /**
     * Tests if this is an enum entry node.
     * @return {@code true} if this is an enum entry node, otherwise {@code false}
     */
    public boolean isEnumEntry() {
        return kind == Kind.enumEntry;
    }
    
    /**
     * Tests if this is a method entry node.
     * @return {@code true} if this is a method entry node, otherwise {@code false}
     */
    public boolean isMethodEntry() {
        return kind == Kind.methodEntry;
    }
    
    /**
     * Tests if this is a constructor entry node.
     * @return {@code true} if this is a constructor entry node, otherwise {@code false}
     */
    public boolean isConstructorEntry() {
        return kind == Kind.constructorEntry;
    }
    
    /**
     * Tests if this is a static initializer entry node.
     * @return {@code true} if this is a static initializer entry node, otherwise {@code false}
     */
    public boolean isInitializerEntry() {
        return kind == Kind.initializerEntry;
    }
    
    /**
     * Tests if this is a field entry node.
     * @return {@code true} if this is a field entry node, otherwise {@code false}
     */
    public boolean isFieldEntry() {
        return kind == Kind.fieldEntry;
    }
    
    /**
     * Tests if this is an enum constant entry node.
     * @return {@code true} if this is an enum constant entry node, otherwise {@code false}
     */
    public boolean isEnumConstantEntry() {
        return kind == Kind.enumConstantEntry;
    }
    
    /**
     * Tests if this is an exit node.
     * @return {@code true} if this is an exit node, otherwise {@code false}
     */
    public boolean isExit() {
        return kind == Kind.classExit ||
               kind == Kind.interfaceExit ||
               kind == Kind.enumExit ||
               kind == Kind.methodExit ||
               kind == Kind.constructorExit ||
               kind == Kind.fieldExit ||
               kind == Kind.initializerExit ||
               kind == Kind.enumConstantExit;
    }
    
    /**
     * Tests if this is a statement node formed by an assignment.
     * @return {@code true} if this is an assignment node, otherwise {@code false}
     */
    public boolean isAssignment() {
        return kind == Kind.assignment;
    }
    
    /**
     * Tests if this is a statement node formed by a method call.
     * @return {@code true} if this is a method call node, otherwise {@code false}
     */
    public boolean isMethodCall() {
        return kind == Kind.methodCall ||
               kind == Kind.constructorCall ||
               kind == Kind.instanceCreation;
    }
    
    /**
     * Tests if this is a statement node formed by a field declaration.
     * @return {@code true} if this is a field declaration node, otherwise {@code false}
     */
    public boolean isFieldDeclaration() {
        return kind == Kind.fieldDeclaration;
    }
    
    /**
     * Tests if this is a statement node formed by a local variable declaration.
     * @return {@code true} if this is a local variable declaration node, otherwise {@code false}
     */
    public boolean isLocalDeclaration() {
        return kind == Kind.localDeclaration;
    }
    
    /**
     * Tests if this is an assert-statement node.
     * @return {@code true} if this is an assert-statement node, otherwise {@code false}
     */
    public boolean isAssert() {
        return kind == Kind.assertSt;
    }
    
    /**
     * Tests if this is a break-statement node.
     * @return {@code true} if this is a break-statement node, otherwise {@code false}
     */
    public boolean isBreak() {
        return kind == Kind.breakSt;
    }
    
    /**
     * Tests if this is a continue-statement node.
     * @return {@code true} if this is a continue-statement node, otherwise {@code false}
     */
    public boolean isContinue() {
        return kind == Kind.continueSt;
    }
    
    /**
     * Tests if this is a do-statement node.
     * @return {@code true} if this is a do-statement node, otherwise {@code false}
     */
    public boolean isDo() {
        return kind == Kind.doSt;
    }
    
    /**
     * Tests if this is a for-statement node.
     * @return {@code true} if this is a for-statement node, otherwise {@code false}
     */
    public boolean isFor() {
        return kind == Kind.forSt;
    }
    
    /**
     * Tests if this is an enhanced for-statement node.
     * @return {@code true} if this is an enhanced for-statement node, otherwise {@code false}
     */
    public boolean isEnhancedFor() {
        return kind == Kind.enhancedForSt;
    }
    
    /**
     * Tests if this is an if-statement node.
     * @return {@code true} if this is an if-statement, otherwise {@code false}
     */
    public boolean isIf() {
        return kind == Kind.ifSt;
    }
    
    /**
     * Tests if this is a return statement node.
     * @return {@code true} if this is a return statement, otherwise {@code false}
     */
    public boolean isReturn() {
        return kind == Kind.returnSt;
    }
    
    /**
     * Tests if this is a switch-case node for the case label.
     * @return {@code true} if this is a switch-case-statement, otherwise {@code false}
     */
    public boolean isSwitchCase() {
        return kind == Kind.switchCase;
    }
    
    /**
     * Tests if this is a switch-default-case node for the default label.
     * @return {@code true} if this is a switch-default-statement node, otherwise {@code false}
     */
    public boolean isSwitchDefault() {
        return kind == Kind.switchDefault;
    }
    
    /**
     * Tests if this is a while-statement node.
     * @return {@code true} if this is a while-statement node, otherwise {@code false}
     */
    public boolean isWhile() {
        return kind == Kind.whileSt;
    }
    
    /**
     * Tests if this is a statement node for the label.
     * @return {@code true} if this is a label statement node, otherwise {@code false}
     */
    public boolean isLabel() {
        return kind == Kind.labelSt;
    }
    
    /**
     * Tests if this is a switch-statement node.
     * @return {@code true} if this is a switch-statement, otherwise {@code false}
     */
    public boolean isSwitch() {
        return kind == Kind.switchSt;
    }
    
    /**
     * Tests if this is a synchronized-statement node.
     * @return {@code true} if this is a synchronized-statement node, otherwise {@code false}
     */
    public boolean isSynchronized() {
        return kind == Kind.synchronizedSt;
    }
    
    /**
     * Tests if this is a throw-statement node.
     * @return {@code true} if this is a throw-statement node, otherwise {@code false}
     */
    public boolean isThrow() {
        return kind == Kind.throwSt;
    }
    
    /**
     * Tests if this is a try-statement node.
     * @return {@code true} if this is a try-statement node, otherwise {@code false}
     */
    public boolean isTry() {
        return kind == Kind.trySt;
    }
    
    /**
     * Tests if this is a catch-clause node.
     * @return {@code true} if this is a catch-clause node, otherwise {@code false}
     */
    public boolean isCatchClause() {
        return kind == Kind.catchClause;
    }
    
    /**
     * Tests if this is a finally-clause node.
     * @return {@code true} if this is a finally-clause node, otherwise {@code false}
     */
    public boolean isFinallyClause() {
        return kind == Kind.finallyClause;
    }
    
    /**
     * Tests if this is a throws-clause node.
     * @return {@code true} if this is a throws-clause node, otherwise {@code false}
     */
    public boolean isThrowClause() {
        return kind == Kind.throwsClause;
    }
    
    /**
     * Tests if this is a statement node formed by a parameter.
     * @return {@code true} if this is a parameter node, otherwise {@code false}
     */
    public boolean isParameter() {
        return kind == Kind.formalIn ||
               kind == Kind.formalOut ||
               kind == Kind.actualIn ||
               kind == Kind.actualOut;
    }
    
    /**
     * Tests if this is a statement node formed by a formal parameter.
     * @return {@code true} if this is a formal parameter node, otherwise {@code false}
     */
    public boolean isFormal() {
        return isFormalIn() || isFormalOut();
    }
    
    /**
     * Tests if this is a statement node formed by a formal-in parameter.
     * @return {@code true} if this is a formal-in parameter node, otherwise {@code false}
     */
    public boolean isFormalIn() {
        return kind == Kind.formalIn;
    }
    
    /**
     * Tests if this is a statement node formed by a formal-out parameter.
     * @return {@code true} if this is a formal parameter-out parameter node, otherwise {@code false}
     */
    public boolean isFormalOut() {
        return kind == Kind.formalOut;
    }
    
    /**
     * Tests if this is a statement node formed by an actual argument.
     * @return {@code true} if this is an actual argument node, otherwise {@code false}
     */
    public boolean isActual() {
        return isActualIn() || isActualOut();
    }
    
    /**
     * Tests if this is a statement node formed by an actual-in argument.
     * @return {@code true} if this is an actual-in argument node, otherwise {@code false}
     */
    public boolean isActualIn() {
        return kind == Kind.actualIn;
    }
    
    /**
     * Tests if this is a statement node formed by an actual-out argument.
     * @return {@code true} if this is an actual-out argument node, otherwise {@code false}
     */
    public boolean isActualOut() {
        return kind == Kind.actualOut || kind == Kind.actualOutByFieldAccess;
    }
    
    /**
     * Tests if this is a statement node formed by a receiver.
     * @return {@code true} if this is a receiver node, otherwise {@code false}
     */
    public boolean isReceiver() {
        return kind == Kind.receiver;
    }
    
    /**
     * Tests if this node represents a statement but not a parameter.
     * @return {@code true} if this node represents a statement node but not a parameter, otherwise {@code false}
     */
    public boolean isStatementNotParameter() {
        return (this instanceof CFGStatement) && !(this instanceof CFGParameter);
    }
    
    /**
     * Tests if this is a a statement node.
     * @return {@code true} if this is a statement node, otherwise {@code false}
     */
    public boolean isStatement() {
        return (this instanceof CFGStatement);
    }
    
    /**
     * Tests if this node is responsible for merging branches.
     * @return {@code true} if this is a merge node, otherwise {@code false}
     */
    public boolean isMerge() {
        return kind == Kind.merge;
    }
    
    /**
     * Tests if this is a dummy node.
     * @return {@code true} if this is a dummy node, otherwise {@code false}
     */
    public boolean isDummy() {
        return kind == Kind.dummy;
    }
    
    /**
     * Tests if this node is a successor of the branch node.
     * @return {@code true} if this node is a successor of the branch node, otherwise {@code false}
     */
    public boolean isNextToBranch() {
        return getPredecessors().stream().anyMatch(node -> node.isBranch());
    }
    
    /**
     * Tests if a variable is defined in this node.
     * @return {@code true} if this has any used variable, otherwise {@code false}
     */
    public boolean hasDefVariable() {
        return false;
    }
    
    /**
     * Tests if a variable is used in this node.
     * @return {@code true} if this has any used variable, otherwise {@code false}
     */
    public boolean hasUseVariable() {
        return false;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object obj) {
        return (obj instanceof CFGNode) ? equals((CFGNode)obj) : false;
    }
    
    /**
     * Tests if a given CFG node is equal to this node.
     * @param node the node to be checked
     * @return the {@code true} if the given node is equal to this node
     */
    public boolean equals(CFGNode node) {
        return super.equals((GraphNode)node);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return super.hashCode();
    }
    
    /**
     * Displays information on this node.
     */
    public void print() {
        System.out.println(toString());
    }
    
    /**
     * Obtains information on this node.
     * @return the string representing the information
     */
    @Override
    public String toString() {
        if (getKind() != null) {
            return GraphElement.getIdString(id) + " " + getKind().toString();
        } else {
            return GraphElement.getIdString(id);
        }
    }
    
    /**
     * Sorts the list of CFG nodes
     * @param nodes the collection of the CFG nodes to be sorted
     * @return the sorted list of the CFG nodes
     */
    public static List<CFGNode> sortNodes(Collection<? extends CFGNode> nodes) {
        return nodes.stream()
                    .sorted(Comparator.comparingLong(CFGNode::getId))
                    .collect(Collectors.toList());
    }
    
    /**
     * Sorts inversely the list of CFG nodes
     * @param collection the collection of the CFG nodes to be sorted
     * @return the sorted list of the CFG nodes
     */
    public static List<CFGNode> sortNodesInverse(Collection<? extends CFGNode> collection) {
        List<CFGNode> nodes = new ArrayList<>(collection);
        nodes.stream().sorted(Comparator.comparingLong(CFGNode::getId).reversed());
        return nodes;
    }
    
    /**
     * Tests if a AST node is categorized into the statement.
     * @param node an AST node to be checked
     * @return {@code true} if the AST node is categorized into the statement, otherwise {@code false}
     */
    public static boolean isStatement(ASTNode node) {
        return (
                node instanceof VariableDeclarationStatement ||
                node instanceof VariableDeclarationExpression ||
                node instanceof AssertStatement ||
                node instanceof BreakStatement ||
                node instanceof ContinueStatement ||
                node instanceof DoStatement ||
                node instanceof EmptyStatement ||
                node instanceof ExpressionStatement ||
                node instanceof ForStatement ||
                node instanceof EnhancedForStatement ||
                node instanceof IfStatement ||
                node instanceof LabeledStatement ||
                node instanceof IfStatement ||
                node instanceof ReturnStatement ||
                node instanceof SwitchCase ||
                node instanceof SwitchStatement ||
                node instanceof SingleVariableDeclaration ||
                node instanceof SynchronizedStatement ||
                node instanceof ThrowStatement ||
                node instanceof TryStatement ||
                node instanceof CatchClause ||
                node instanceof TypeDeclarationStatement ||
                node instanceof WhileStatement ||
                node instanceof ConstructorInvocation ||
                node instanceof SuperConstructorInvocation ||
                node instanceof EnumConstantDeclaration
            );
    }
    
    /**
     * Tests if a AST node is categorized into the expression.
     * @param node an AST node to be checked
     * @return {@code true} if the AST node is categorized into the expression, otherwise {@code false}
     */
    public static boolean isExpression(ASTNode node) {
        return (
                node instanceof Assignment ||
                node instanceof ClassInstanceCreation ||
                node instanceof MethodInvocation ||
                node instanceof SuperMethodInvocation ||
                node instanceof PostfixExpression ||
                node instanceof PrefixExpression ||
                node instanceof SingleVariableDeclaration ||
                node instanceof VariableDeclarationFragment ||
                node instanceof ConditionalExpression ||
                node instanceof SwitchExpression
            );
    }
    
    /**
     * Tests if a AST node is categorized into the literal.
     * @param node an AST node to be checked
     * @return {@code true} if the AST node is categorized into the literal, otherwise {@code false}
     */
    public static boolean isLiteral(ASTNode node) {
        return (
                node instanceof BooleanLiteral ||
                node instanceof CharacterLiteral ||
                node instanceof NullLiteral ||
                node instanceof NumberLiteral ||
                node instanceof StringLiteral ||
                node instanceof TypeLiteral ||
                node instanceof Name
            );
    }
}
