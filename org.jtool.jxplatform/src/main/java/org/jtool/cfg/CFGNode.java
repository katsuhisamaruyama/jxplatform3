/*
 *  Copyright 2020
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.cfg;

import org.jtool.graph.GraphEdge;
import org.jtool.graph.GraphElement;
import org.jtool.graph.GraphNode;
import org.jtool.pdg.PDGNode;
import org.eclipse.jdt.core.dom.ASTNode;
import java.util.Set;
import java.util.List;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
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
     * The basic block that encloses this node.
     */
    protected BasicBlock basicBlock = null;
    
    /**
     * The number prepared for generating the identification number when creating a new CFG node.
     */
    private static long num = 1;
    
    /**
     * The kind of a CFG node.
     */
    public enum Kind {
        
        classEntry,                 // CFGClassEntry (TypeDeclaration, AnonymousClassDeclaration)
        interfaceEntry,             // CFGClassEntry (TypeDeclaration, AnonymousClassDeclaration)
        enumEntry,                  // CFGClassEntry (EnumDeclaration)
        methodEntry,                // CFGMethodEntry (MethodDeclaration)
        constructorEntry,           // CFGMethodEntry (MethodDeclaration)
        initializerEntry,           // CFGInitializerEntry (Initializer)
        fieldEntry,                 // CFGFieldEntry (VariableDeclarationFragment/FieldDeclaration)
        enumConstantEntry,          // CFGFieldEntry (EnumConstantDeclaration)
        
        classExit,                  // CFGExit
        interfaceExit,              // CFGExit
        enumExit,                   // CFGExit
        methodExit,                 // CFGExit
        constructorExit,            // CFGExit
        initializerExit,            // CFGExit
        fieldExit,                  // CFGExit
        enumConstantExit,           // CFGExit
        
        assignment,                 // CFGStatement (Assignment)
        lambda,                     // CFGStatement (LambdaExpression)
        conditionalExpression,      // CFGStatement (ConditionalExpression)
        switchExpression,           // CFGStatement (SwitchExpression)
        methodCall,                 // CFGMethodCall (MethodInvocation/SuperMethodInvocation)
        constructorCall,            // CFGMethodCall (ConstructorInvocation/SuperConstructorInvocation)
        instanceCreation,           // CFGMethodCall (InstanceCreation)
        
        fieldDeclaration,           // CFGStatement (VariableDeclarationFragment)
        enumConstantDeclaration,    // CFGStatement (VariableDeclarationFragment)
        localDeclaration,           // CFGStatement (VariableDeclarationFragment)
        
        assertSt,                   // CFGStatement (AssertStatement)
        breakSt,                    // CFGStatement (BreakStatement)
        continueSt,                 // CFGStatement (ContinueStatement)
        doSt,                       // CFGStatement (DoStatement)
        forSt,                      // CFGStatement (ForStatement)
        ifSt,                       // CFGStatement (IfStatement)
        returnSt,                   // CFGStatement (ReturnStatement)
        switchCase,                 // CFGStatement (SwitchCase)
        switchDefault,              // CFGStatement (SwitchCase)
        whileSt,                    // CFGStatement (WhileStatement)
        emptySt,                    // CFGStatement (EmptyStatement)
        
        labelSt,                    // CFGStatement (Identifier in LabeledStatement)
        switchSt,                   // CFGStatement (SwitchStatement)
        synchronizedSt,             // CFGStatement (SynchronizedStatement)
        throwSt,                    // CFGStatement (ThrowStatement)
        trySt,                      // CFGStatement (TryStatement)
        catchClause,                // CFGStatement (CatchClause in TryStatement)
        finallyClause,              // CFGStatement (Block in TryStatement)
        throwsClause,               // CFGStatement (thrown exception Types in MethodDeclaration)
        
        formalIn,                   // CFGParameter
        formalOut,                  // CFGParameter
        actualIn,                   // CFGParameter
        actualOut,                  // CFGParameter
        receiver,                   // CFGStatement
        
        merge,                      // CFGMerge (for merge)
        dummy;                      // CFGDummy (for dummy)
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
     * Sets a basic block enclosing this node.
     * This method is not intended to be invoked by clients.
     * @param block the enclosing basic block
     */
    public void setBasicBlock(BasicBlock block) {
        basicBlock = block;
    }
    
    /**
     * Returns the basic block enclosing this node.
     * @return the enclosing basic block
     */
    public BasicBlock getBasicBlock() {
        return basicBlock;
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
    public Set<ControlFlow> getIncomingFlows() {
        return convertEdges(getIncomingEdges());
    }
    
    /**
     * Obtains control flow edges outgoing from this node.
     * @return the collection of the outgoing edges
     */
    public Set<ControlFlow> getOutgoingFlows() {
        return convertEdges(getOutgoingEdges());
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
    private Set<ControlFlow> convertEdges(Set<GraphEdge> edges) {
        return edges.stream().map(edge -> (ControlFlow)edge).collect(Collectors.toSet());
    }
    
    /**
     * Tests if this node causes branching, which has multiple outgoing control flow edges.
     * @return {@code true} if this node causes branching, otherwise {@code false}
     */
    public boolean isBranch() { 
        return getOutgoingEdges().size() > 1;
    }
    
    /**
     * Tests if this node causes looping.
     * @return {@code true} if this node causes looping, otherwise {@code false}
     */
    public boolean isLoop() {
        return kind == Kind.whileSt || kind == Kind.doSt || kind == Kind.forSt;
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
        return kind == Kind.throwSt;
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
    public boolean isCatch() {
        return kind == Kind.catchClause;
    }
    
    /**
     * Tests if this is a finally-clause node.
     * @return {@code true} if this is a finally-clause node, otherwise {@code false}
     */
    public boolean isFinally() {
        return kind == Kind.finallyClause;
    }
    
    /**
     * Tests if this is a throws-clause node.
     * @return {@code true} if this is a throws-clause node, otherwise {@code false}
     */
    public boolean isThrows() {
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
        return kind == Kind.actualOut;
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
     * Tests if this mode is a leader of a basic block.
     * @return {@code true} if this is a leader node, otherwise {@code false}
     */
    public boolean isLeader() {
        return basicBlock != null && equals(basicBlock.getLeader());
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(GraphElement elem) {
        return (elem instanceof CFGNode) ? equals((CFGNode)elem) : false;
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
            return getIdString(id) + " " + getKind().toString();
        } else {
            return getIdString(id);
        }
    }
    
    /**
     * Sorts the list of CFG nodes
     * @param co the list of the CFG nodes to be sorted
     * @return the sorted list of the CFG nodes
     */
    public static List<CFGNode> sortCFGNodes(Collection<? extends CFGNode> co) {
        List<CFGNode> nodes = new ArrayList<>(co);
        Collections.sort(nodes, new Comparator<>() {
            
            @Override
            public int compare(CFGNode node1, CFGNode node2) {
                return (node2.id == node1.id) ? 0 : (node1.id > node2.id) ? 1 : -1;
            }
        });
        return nodes;
    }
    
    /**
     * Sorts inversely the list of CFG nodes
     * @param co the list of the CFG nodes to be sorted
     * @return the sorted list of the CFG nodes
     */
    public static List<CFGNode> sortCFGNodesInverse(Collection<? extends CFGNode> co) {
        List<CFGNode> nodes = new ArrayList<>(co);
        Collections.sort(nodes, new Comparator<>() {
            
            @Override
            public int compare(CFGNode node1, CFGNode node2) {
                return (node2.id == node1.id) ? 0 : (node1.id < node2.id) ? 1 : -1;
            }
        });
        return nodes;
    }
}
