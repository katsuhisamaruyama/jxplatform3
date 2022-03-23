/*
 *  Copyright 2022
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.cfg.builder;

import org.jtool.cfg.CFG;
import org.jtool.cfg.CFGException;
import org.jtool.cfg.CFGMerge;
import org.jtool.cfg.CFGMethodEntry;
import org.jtool.cfg.CFGNode;
import org.jtool.cfg.CFGStatement;
import org.jtool.cfg.ControlFlow;
import org.jtool.cfg.JLocalVarReference;
import org.jtool.cfg.JReference;
import org.jtool.cfg.JSpecialVarReference;
import org.jtool.graph.GraphEdge;
import org.jtool.srcmodel.JavaMethod;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.VariableDeclarationStatement;
import org.eclipse.jdt.core.dom.VariableDeclarationExpression;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;
import org.eclipse.jdt.core.dom.SingleVariableDeclaration;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.core.dom.Statement;
import org.eclipse.jdt.core.dom.AssertStatement;
import org.eclipse.jdt.core.dom.Block;
import org.eclipse.jdt.core.dom.BreakStatement;
import org.eclipse.jdt.core.dom.ConstructorInvocation;
import org.eclipse.jdt.core.dom.ContinueStatement;
import org.eclipse.jdt.core.dom.DoStatement;
import org.eclipse.jdt.core.dom.EmptyStatement;
import org.eclipse.jdt.core.dom.ExpressionStatement;
import org.eclipse.jdt.core.dom.ForStatement;
import org.eclipse.jdt.core.dom.EnhancedForStatement;
import org.eclipse.jdt.core.dom.EnumConstantDeclaration;
import org.eclipse.jdt.core.dom.IfStatement;
import org.eclipse.jdt.core.dom.LabeledStatement;
import org.eclipse.jdt.core.dom.ReturnStatement;
import org.eclipse.jdt.core.dom.SuperConstructorInvocation;
import org.eclipse.jdt.core.dom.SwitchCase;
import org.eclipse.jdt.core.dom.SwitchStatement;
import org.eclipse.jdt.core.dom.SynchronizedStatement;
import org.eclipse.jdt.core.dom.ThrowStatement;
import org.eclipse.jdt.core.dom.TryStatement;
import org.eclipse.jdt.core.dom.CatchClause;
import org.eclipse.jdt.core.dom.TypeDeclarationStatement;
import org.eclipse.jdt.core.dom.WhileStatement;
import org.eclipse.jdt.core.dom.Type;
import org.eclipse.jdt.core.dom.UnionType;
import org.eclipse.jdt.core.dom.Name;
import org.eclipse.jdt.core.dom.ITypeBinding;
import org.eclipse.jdt.core.dom.IMethodBinding;
import org.eclipse.jdt.core.dom.IVariableBinding;
import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;
import java.util.Stack;

/**
 * Creates a CFG nodes and edges by visiting AST nodes within a statement.
 * 
 * @see org.eclipse.jdt.core.dom.Statement
 * 
 * Statement:
 *   Block
 *   EmptyStatement
 *   TypeDeclarationStatement (not needed to visit because it was already visited under model creation)
 *   ExpressionStatement
 *   VariableDeclarationStatement
 *   VariableDeclarationExpression (this originally belongs to Expression)
 *   ConstructorInvocation
 *   SuperConstructorInvocation
 *   IfStatement
 *   SwitchStatement
 *   SwitchCase
 *   WhileStatement
 *   DoStatement,
 *   ForStatement
 *   EnhancedForStatement
 *   BreakStatement
 *   ContinueStatement
 *   ReturnStatement
 *   AssertStatement
 *   LabeledStatement  
 *   SynchronizedStatement
 *   ThrowStatement
 *   TryStatement
 *   
 *   @author Katsuhisa Maruyama
 */
public class StatementVisitor extends ASTVisitor {
    
    protected CFG cfg;
    protected CFGNode prevNode;
    protected CFGNode nextNode;
    
    private Stack<CFGNode> blockEntries = new Stack<>();
    private Stack<CFGNode> blockExits = new Stack<>();
    
    private Set<Label> labels = new HashSet<>();
    
    private Stack<TryNode> tryNodeStack = new Stack<>();
    
    protected StatementVisitor(CFG cfg, CFGNode prevNode, CFGNode nextNode) {
         this.cfg = cfg;
         
         this.prevNode = prevNode;
         this.nextNode = nextNode;
    }
    
    protected CFGNode getNextCFGNode() {
        return nextNode;
    }
    
    private ControlFlow createFlow(CFGNode src, CFGNode dst) {
        ControlFlow edge = new ControlFlow(src, dst);
        cfg.add(edge);
        return edge;
    }
    
    private void reconnect(CFGNode origNode, CFGNode node) {
        Set<GraphEdge> edges = new HashSet<>(origNode.getIncomingEdges());
        for (GraphEdge edge : edges) {
            edge.setDstNode(node);
        }
        
        cfg.add(node);
        origNode.clear();
        prevNode = node;
    }
    
    private void reconnect(CFGNode node) {
        reconnect(nextNode, node);
    }
    
    @Override
    public boolean visit(Block node) {
        return true;
    }
    
    @Override
    public boolean visit(EmptyStatement node) {
        CFGStatement emptyNode = new CFGStatement(node, CFGNode.Kind.emptySt);
        reconnect(emptyNode);
        
        ControlFlow edge = createFlow(emptyNode, nextNode);
        edge.setTrue();
        return false;
    }
    
    @Override
    public boolean visit(TypeDeclarationStatement node) {
        return false;
    }
    
    @Override
    public boolean visit(ExpressionStatement node) {
        CFGStatement expNode = new CFGStatement(node, CFGNode.Kind.assignment);
        reconnect(expNode);
        
        Expression expression = node.getExpression();
        ExpressionVisitor exprVisitor = new ExpressionVisitor(this, cfg, expNode);
        expression.accept(exprVisitor);
        CFGNode curNode = exprVisitor.getExitNode();
        
        ControlFlow edge = createFlow(curNode, nextNode);
        edge.setTrue();
        return false;
    }
    
    @Override
    @SuppressWarnings("unchecked")
    public boolean visit(VariableDeclarationStatement node) {
        variableDeclaration(node, node.fragments());
        return false;
    }
    
    @Override
    @SuppressWarnings("unchecked")
    public boolean visit(VariableDeclarationExpression node) {
        variableDeclaration(node, node.fragments());
        return false;
    }
    
    private void variableDeclaration(ASTNode node, List<VariableDeclarationFragment> fragments) {
        for (VariableDeclarationFragment frag : fragments) {
            CFGStatement declNode = new CFGStatement(node, CFGNode.Kind.assignment);
            reconnect(declNode);
            
            ExpressionVisitor exprVisitor = new ExpressionVisitor(this, cfg, declNode);
            frag.accept(exprVisitor);
            CFGNode curNode = exprVisitor.getExitNode();
            
            ControlFlow edge = createFlow(curNode, nextNode);
            edge.setTrue();
        }
    }
    
    @Override
    public boolean visit(ConstructorInvocation node) {
        CFGStatement invNode = new CFGStatement(node, CFGNode.Kind.assignment);
        reconnect(invNode);
        
        ExpressionVisitor prefixVisitor = new ExpressionVisitor(this, cfg, invNode);
        node.accept(prefixVisitor);
        CFGNode curNode = prefixVisitor.getExitNode();
        
        ControlFlow edge = createFlow(curNode, nextNode);
        edge.setTrue();
        return false;
    }
    
    @Override
    public boolean visit(SuperConstructorInvocation node) {
        CFGStatement invNode = new CFGStatement(node, CFGNode.Kind.assignment);
        reconnect(invNode);
        
        ExpressionVisitor prefixVisitor = new ExpressionVisitor(this, cfg, invNode);
        node.accept(prefixVisitor);
        CFGNode curNode = prefixVisitor.getExitNode();
        
        ControlFlow edge = createFlow(curNode, nextNode);
        edge.setTrue();
        return false;
    }
    
    @Override
    public boolean visit(IfStatement node) {
        CFGStatement ifNode = new CFGStatement(node, CFGNode.Kind.ifSt);
        reconnect(ifNode);
        
        Expression condition = node.getExpression();
        ExpressionVisitor condVisitor = new ExpressionVisitor(this, cfg, ifNode);
        condition.accept(condVisitor);
        CFGNode curNode = condVisitor.getExitNode();
        
        ControlFlow trueEdge = createFlow(curNode, nextNode);
        trueEdge.setTrue();
        
        Statement thenSt = node.getThenStatement();
        thenSt.accept(this);
        
        ControlFlow trueMergeEdge = cfg.getFlow(prevNode, nextNode);
        ControlFlow falseEdge = createFlow(curNode, nextNode);
        falseEdge.setFalse();
        
        Statement elseSt = node.getElseStatement();
        if (elseSt != null) {
            elseSt.accept(this);
            if (trueMergeEdge != null) {
                trueMergeEdge.setDstNode(nextNode);
            }
        }
        CFGMerge mergeNode = new CFGMerge(node, ifNode);
        reconnect(mergeNode);
        
        ControlFlow edge = createFlow(mergeNode, nextNode);
        edge.setTrue();
        return false;
    }
    
    @Override
    @SuppressWarnings("unchecked")
    public boolean visit(SwitchStatement node) {
        SwitchNode switchNode = new SwitchNode(node, CFGNode.Kind.switchSt);
        reconnect(switchNode);
        
        Expression condition = node.getExpression();
        ExpressionVisitor condVisitor = new ExpressionVisitor(this, cfg, switchNode);
        condition.accept(condVisitor);
        CFGNode curNode = condVisitor.getExitNode();
        
        ControlFlow caseEdge = createFlow(curNode, nextNode);
        caseEdge.setTrue();
        
        CFGNode exitNode = new CFGNode();
        blockEntries.push(switchNode);
        blockExits.push(exitNode);
        
        List<Statement> remaining = new ArrayList<>();
        for (Statement statement : (List<Statement>)node.statements()) {
            remaining.add(statement);
        }
        for (Statement statement : (List<Statement>)node.statements()) {
            remaining.remove(0);
            
            if (statement instanceof SwitchCase) {
                switchCase((SwitchCase)statement, switchNode, remaining);
            }
        }
        if (switchNode.hasDefault()) {
            CFGNode successor = switchNode.getSuccessorOfDefault();
            List<GraphEdge> nextEdges = new ArrayList<>();
            for (GraphEdge edge : nextNode.getIncomingEdges()) {
                nextEdges.add(edge);
            }
            List<GraphEdge> incomingEdges = new ArrayList<>();
            for (GraphEdge edge : switchNode.getDefaultStartNode().getIncomingEdges()) {
                incomingEdges.add(edge);
            }
            List<GraphEdge> outgoingEdges = new ArrayList<>();
            for (GraphEdge edge : successor.getIncomingEdges()) {
                outgoingEdges.add(edge);
            }
            for (GraphEdge edge : nextEdges) {
                ControlFlow flow = (ControlFlow)edge;
                flow.setDstNode(switchNode.getDefaultStartNode());
            }
            for (GraphEdge edge : incomingEdges) {
                ControlFlow flow = (ControlFlow)edge;
                flow.setDstNode(successor);
            }
            for (GraphEdge edge : outgoingEdges) {
                ControlFlow flow = (ControlFlow)edge;
                if (!flow.isFalse()) {
                    flow.setDstNode(nextNode);
                } 
            }
        }
        
        nextNode.addIncomingEdges(exitNode.getIncomingEdges());
        CFGMerge mergeNode = new CFGMerge(node, switchNode);
        reconnect(mergeNode);
        
        ControlFlow falseEdge = createFlow(switchNode, mergeNode);
        falseEdge.setFalse();
        
        blockEntries.pop();
        blockExits.pop();
        
        ControlFlow edge = createFlow(mergeNode, nextNode);
        edge.setTrue();
        return false;
    }
    
    @SuppressWarnings("deprecation")
    private void switchCase(SwitchCase node, SwitchNode switchNode, List<Statement> remaining)  {
        CFGStatement caseNode;
        if (!node.isDefault()) {
            caseNode = new CFGStatement(node, CFGNode.Kind.switchCaseSt);
            reconnect(caseNode);
            
            ExpressionVisitor condVisitor = new ExpressionVisitor(this, cfg, caseNode);
            Expression condition = node.getExpression();
            condition.accept(condVisitor);
            
            caseNode.addDefVariables(switchNode.getDefVariables());
            caseNode.addUseVariables(switchNode.getUseVariables());
            CFGNode curNode = condVisitor.getExitNode();
            
            ControlFlow edge = createFlow(curNode, nextNode);
            edge.setTrue();
        } else {
            caseNode = new CFGStatement(node, CFGNode.Kind.switchDefaultSt);
            reconnect(caseNode);
            
            ControlFlow edge = createFlow(caseNode, nextNode);
            edge.setTrue();
            switchNode.setDefaultStartNode(caseNode);
        }
        for (Statement statement : remaining) {
            if (statement instanceof SwitchCase) {
                break;
            }
            statement.accept(this); 
        }
        
        ControlFlow edge = createFlow(caseNode, nextNode);
        edge.setFalse();
        if (node.isDefault()) {
            switchNode.setDefaultEndNode(prevNode);
        }
    }
    
    @Override
    public boolean visit(WhileStatement node) {
        CFGStatement whileNode = new CFGStatement(node, CFGNode.Kind.whileSt);
        reconnect(whileNode);
        
        Expression condition = node.getExpression();
        ExpressionVisitor condVisitor = new ExpressionVisitor(this, cfg, whileNode);
        condition.accept(condVisitor);
        CFGNode curNode = condVisitor.getExitNode();
        
        ControlFlow trueEdge = createFlow(curNode, nextNode);
        trueEdge.setTrue();
        
        CFGNode entryNode = condVisitor.getEntryNode();
        CFGNode exitNode = new CFGNode();
        blockEntries.push(entryNode);
        blockExits.push(exitNode);
        Statement body = node.getBody();
        body.accept(this);
        
        ControlFlow loopbackEdge = cfg.getFlow(prevNode, nextNode);
        if (loopbackEdge != null) {
            loopbackEdge.setDstNode(entryNode);
            loopbackEdge.setLoopBack(whileNode);
        }
         
        ControlFlow falseEdge = createFlow(whileNode, nextNode);
        falseEdge.setFalse();
        prevNode = whileNode;
        nextNode.addIncomingEdges(exitNode.getIncomingEdges());
        
        blockEntries.pop();
        blockExits.pop();
        return false;
    }
    
    @Override
    public boolean visit(DoStatement node) {
        CFGNode entryNode = new CFGNode();
        CFGNode exitNode = new CFGNode();
        blockEntries.push(entryNode);
        blockExits.push(exitNode);
        
        ControlFlow entryEdge = cfg.getFlow(prevNode, nextNode);
        Statement body = node.getBody();
        body.accept(this);
        
        nextNode.addIncomingEdges(entryNode.getIncomingEdges());
        CFGStatement doNode = new CFGStatement(node, CFGNode.Kind.doSt);
        reconnect(doNode);
        
        Expression condition = node.getExpression();
        ExpressionVisitor condVisitor = new ExpressionVisitor(this, cfg, doNode);
        condition.accept(condVisitor);
        CFGNode curNode = condVisitor.getExitNode();
        
        ControlFlow loopbackEdge = createFlow(curNode, entryEdge.getDstNode());
        loopbackEdge.setTrue();
        loopbackEdge.setLoopBack(doNode);
        
        ControlFlow falseEdge = createFlow(doNode, nextNode);
        falseEdge.setFalse();
        nextNode.addIncomingEdges(exitNode.getIncomingEdges());
        
        blockEntries.pop();
        blockExits.pop();
        return false;
    }
    
    @Override
    @SuppressWarnings("unchecked")
    public boolean visit(ForStatement node) {
        for (Expression initializer : (List<Expression>)node.initializers()) {
            if (initializer instanceof VariableDeclarationExpression) {
                initializer.accept(this);
            } else {
                CFGStatement initNode = new CFGStatement(node, CFGNode.Kind.assignment);
                ExpressionVisitor initVisitor = new ExpressionVisitor(this, cfg, initNode);
                initializer.accept(initVisitor);
                CFGNode curNode = initVisitor.getExitNode();
                reconnect(initNode);
                
                ControlFlow edge = createFlow(curNode, nextNode);
                edge.setTrue();
            }
        }
        
        CFGStatement forNode = new CFGStatement(node, CFGNode.Kind.forSt);
        CFGNode entryNode;
        Expression condition = node.getExpression();
        if (condition != null) {
            ExpressionVisitor condVisitor = new ExpressionVisitor(this, cfg, forNode);
            condition.accept(condVisitor);
            CFGNode curNode = condVisitor.getExitNode();
            reconnect(forNode);
            
            ControlFlow edge = createFlow(curNode, nextNode);
            edge.setTrue();
            entryNode = condVisitor.getEntryNode();
        } else {
            reconnect(forNode);
            
            ControlFlow edge = createFlow(forNode, nextNode);
            edge.setTrue();
            entryNode = forNode;
        }
        
        CFGNode exitNode = new CFGNode();
        blockEntries.push(entryNode);
        blockExits.push(exitNode);
        Statement body = node.getBody();
        body.accept(this);
        for (Expression update : (List<Expression>)node.updaters()) {
            CFGStatement updateNode = new CFGStatement(update, CFGNode.Kind.assignment);
            ExpressionVisitor updateVisitor = new ExpressionVisitor(this, cfg, updateNode);
            update.accept(updateVisitor);
            CFGNode curNode = updateVisitor.getExitNode();
            reconnect(updateNode);
            
            ControlFlow edge = createFlow(curNode, nextNode);
            edge.setTrue();
        }
        
        ControlFlow loopbackEdge = cfg.getFlow(prevNode, nextNode);
        if (loopbackEdge != null) {
            loopbackEdge.setDstNode(entryNode);
            loopbackEdge.setLoopBack(forNode);
        }
        
        ControlFlow falseEdge = createFlow(forNode, nextNode);
        falseEdge.setFalse();
        prevNode = forNode;
        nextNode.addIncomingEdges(exitNode.getIncomingEdges());
        
        blockEntries.pop();
        blockExits.pop();
        return false;
    }
    
    @Override
    public boolean visit(EnhancedForStatement node) {
        CFGStatement forNode = new CFGStatement(node, CFGNode.Kind.forSt);
        reconnect(forNode);
        
        Name name = node.getParameter().getName();
        ExpressionVisitor paramVisitor = new ExpressionVisitor(this, cfg, forNode);
        name.accept(paramVisitor);
        Expression expression = node.getExpression();
        ExpressionVisitor exprVisitor = new ExpressionVisitor(this, cfg, forNode);
        expression.accept(exprVisitor);
        CFGNode curNode = exprVisitor.getExitNode();
        
        ControlFlow edge = createFlow(curNode, nextNode);
        edge.setTrue();
        
        CFGNode exitNode = new CFGNode();
        blockEntries.push(curNode);
        blockExits.push(exitNode);
        Statement body = node.getBody();
        body.accept(this);
        
        ControlFlow loopbackEdge = cfg.getFlow(prevNode, nextNode);
        if (loopbackEdge != null) {
            loopbackEdge.setDstNode(curNode);
            loopbackEdge.setLoopBack(forNode);
        }
        
        ControlFlow falseEdge = createFlow(forNode, nextNode);
        falseEdge.setFalse();
        prevNode = forNode;
        nextNode.addIncomingEdges(exitNode.getIncomingEdges());
        
        blockEntries.pop();
        blockExits.pop();
        return false;
    }
    
    @Override
    public boolean visit(BreakStatement node) {
        CFGStatement breakNode = new CFGStatement(node, CFGNode.Kind.breakSt);
        reconnect(breakNode);
        
        CFGNode jumpNode;
        if (node.getLabel() != null) {
            String name = node.getLabel().getFullyQualifiedName();
            jumpNode = getLabel(name).getNode();
        } else {
            jumpNode = (CFGNode)blockExits.peek();
            // Goes to the entry point and moves its false-successor immediately.
            // Not go to the exit point directly according to the Java specification.
        }
        if (jumpNode != null) {
            ControlFlow edge = createFlow(breakNode, jumpNode);
            edge.setTrue();
            edge = createFlow(breakNode, nextNode);
            edge.setFallThrough();
        }
        return false;
    }
    
    @Override
    public boolean visit(ContinueStatement node) {
        CFGStatement continueNode = new CFGStatement(node, CFGNode.Kind.continueSt);
        reconnect(continueNode);
        
        CFGNode jumpNode;
        if (node.getLabel() != null) {
            String name = node.getLabel().getFullyQualifiedName();
            jumpNode = getLabel(name).getNode();
        } else {
            jumpNode = (CFGNode)blockEntries.peek();
        }
        if (jumpNode != null) {
            ControlFlow edge = createFlow(continueNode, jumpNode);
            edge.setTrue();
            edge = createFlow(continueNode, nextNode);
            edge.setFallThrough();
        }
        return false;
    }
    
    @Override
    public boolean visit(ReturnStatement node) {
        CFGStatement returnNode = new CFGStatement(node, CFGNode.Kind.returnSt);
        reconnect(returnNode);
        
        CFGNode curNode = returnNode;
        Expression expression = node.getExpression();
        if (expression != null) {
            ExpressionVisitor exprVisitor = new ExpressionVisitor(this, cfg, returnNode);
            expression.accept(exprVisitor);
            
            CFGMethodEntry methodNode = (CFGMethodEntry)cfg.getEntryNode();
            String type = methodNode.getJavaMethod().getReturnType();
            boolean primitive = methodNode.getJavaMethod().isPrimitiveReturnType();
            JReference jvar = new JSpecialVarReference(methodNode.getASTNode(), "$_", type, primitive);
            returnNode.addDefVariable(jvar);
            
            curNode = exprVisitor.getExitNode();
        }
        
        ControlFlow trueEdge = createFlow(curNode, cfg.getExitNode());
        trueEdge.setTrue();
        
        ControlFlow fallEdge = createFlow(curNode, nextNode);
        fallEdge.setFallThrough();
        return false;
    }
    
    @Override
    public boolean visit(AssertStatement node) {
        CFGStatement assertNode = new CFGStatement(node, CFGNode.Kind.assertSt);
        reconnect(assertNode);
        
        Expression expression = node.getExpression();
        ExpressionVisitor exprVisitor = new ExpressionVisitor(this, cfg, assertNode);
        expression.accept(exprVisitor);
        CFGNode curNode = exprVisitor.getExitNode();
        Expression message = node.getMessage();
        if (message != null) {
            ExpressionVisitor mesgVisitor = new ExpressionVisitor(this, cfg, assertNode);
            message.accept(mesgVisitor);
            curNode = mesgVisitor.getExitNode();
        }
        
        ControlFlow edge = createFlow(curNode, nextNode);
        edge.setTrue();
        return false;
    }
    
    @Override
    public boolean visit(LabeledStatement node) {
        CFGStatement labelNode = new CFGStatement(node, CFGNode.Kind.labelSt);
        reconnect(labelNode);
        
        ControlFlow trueEdge = createFlow(labelNode, nextNode);
        trueEdge.setTrue();
        
        String name = node.getLabel().getFullyQualifiedName();
        labels.add(new Label(name, labelNode));
        Statement body = node.getBody();
        body.accept(this);
        return false;
    }
    
    @Override
    public boolean visit(SynchronizedStatement node) {
        CFGStatement syncNode = new CFGStatement(node, CFGNode.Kind.synchronizedSt);
        reconnect(syncNode);
        
        Expression expression = node.getExpression();
        ExpressionVisitor exprVisitor = new ExpressionVisitor(this, cfg, syncNode);
        expression.accept(exprVisitor);
        CFGNode curNode = exprVisitor.getExitNode();
        
        ControlFlow trueEdge = createFlow(curNode, nextNode);
        trueEdge.setTrue();
        
        Statement body = node.getBody();
        body.accept(this);
        return false;
    }
    
    @Override
    public boolean visit(ThrowStatement node) {
        CFGStatement throwNode = new CFGStatement(node, CFGNode.Kind.throwSt);
        reconnect(throwNode);
        
        Expression expression = node.getExpression();
        ExpressionVisitor exprVisitor = new ExpressionVisitor(this, cfg, throwNode);
        expression.accept(exprVisitor);
        CFGNode curNode = exprVisitor.getExitNode();
        
        if (expression instanceof MethodInvocation) {
            IMethodBinding mbinding = ((MethodInvocation)expression).resolveMethodBinding();
            if (mbinding != null) {
                setExceptionFlowOnThrow(throwNode, mbinding.getReturnType());
            }
        } else {
            setExceptionFlowOnThrow(throwNode, expression.resolveTypeBinding());
        }
        
        ControlFlow fallEdge = createFlow(curNode, nextNode);
        fallEdge.setFallThrough();
        return false;
    }
    
    @Override
    @SuppressWarnings("unchecked")
    public boolean visit(TryStatement node) {
        TryNode tryNode = new TryNode(node, CFGNode.Kind.trySt);
        reconnect(tryNode);
        
        tryNodeStack.push(tryNode);
        
        ControlFlow edge = createFlow(tryNode, nextNode);
        edge.setTrue();
        
        for (Expression resource : (List<Expression>)node.resources()) {
            CFGStatement resourceNode = new CFGStatement(node, CFGNode.Kind.assignment);
            reconnect(resourceNode);
            
            ExpressionVisitor resourceVisitor = new ExpressionVisitor(this, cfg, resourceNode);
            resource.accept(resourceVisitor);
            CFGNode curNode = resourceVisitor.getExitNode();
            
            ControlFlow e = createFlow(curNode, nextNode);
            e.setTrue();
        }
        
        Statement body = node.getBody();
        body.accept(this);
        
        CFGMerge mergeNode = new CFGMerge(node, tryNode);
        reconnect(mergeNode);
        
        ControlFlow fallEdge = createFlow(tryNode, mergeNode);
        fallEdge.setFallThrough();
        
        for (CatchClause clause : (List<CatchClause>)node.catchClauses()) { 
            visitCatchClause(tryNode, clause, mergeNode);
        }
        
        ControlFlow trueEdge = createFlow(mergeNode, nextNode);
        trueEdge.setTrue();
        
        Block finallyBlock = node.getFinally();
        if (finallyBlock != null) {
            visitFinallyBlock(tryNode, finallyBlock, mergeNode);
        }
        
        catchClause(tryNode);
        
        return false;
    }
    
    @SuppressWarnings("unchecked")
    private void visitCatchClause(TryNode tryNode, CatchClause node, CFGMerge mergeNode) {
        IVariableBinding vbinding = node.getException().resolveBinding();
        
        CFGException catchNode = null;
        Type type = node.getException().getType();
        List<Type> types = new ArrayList<>();
        if (type.isUnionType()) {
            UnionType unionType = (UnionType)type;
            types.addAll(unionType.types());
        } else {
            types.add(type);
        }
        
        for (Type expceptionType : types) {
            catchNode = new CFGException(node,
                    CFGNode.Kind.catchClause, expceptionType.resolveBinding().getTypeDeclaration());
            tryNode.addCatchNode(catchNode);
            
            JReference def = new JLocalVarReference(node.getException().getName(), vbinding);
            catchNode.setDefVariable(def);
            
            reconnect(catchNode);
        }
        
        ControlFlow trueEdge = createFlow(catchNode, nextNode);
        trueEdge.setTrue();
        
        Statement body = node.getBody();
        body.accept(this);
        reconnect(mergeNode);
    }
    
    private void visitFinallyBlock(TryNode tryNode, Block block, CFGMerge mergeNode) {
        CFGStatement finallyNode = new CFGStatement(block, CFGNode.Kind.finallyClause);
        tryNode.setFinallyNode(finallyNode);
        
        reconnect(finallyNode);
        
        ControlFlow edge = createFlow(finallyNode, nextNode);
        edge.setTrue();
        
        block.accept(this);
    }
    
    private void catchClause(TryNode tryNode) {
        Set<TryNode.ExceptionOccurrence> occurrences = new HashSet<>(tryNode.getExceptionOccurrences());
        for (TryNode.ExceptionOccurrence occurence : occurrences) {
            for (CFGException catchNode : getCatchNodes(occurence.getType(), tryNode.getCatchNodes())) {
                ControlFlow exceptionEdge = createFlow(occurence.getNode(), catchNode);
                if (occurence.isMethodCall()) {
                    exceptionEdge.setExceptionCatch();
                } else {
                    exceptionEdge.setTrue();
                }
                tryNode.getExceptionOccurrences().remove(occurence);
            }
        }
        
        tryNodeStack.pop();
        if (tryNodeStack.size() > 0) {
            for (TryNode.ExceptionOccurrence occurence : tryNode.getExceptionOccurrences()) {
                tryNodeStack.peek().addExceptionOccurrence(occurence.getNode(), occurence.getType(), occurence.isMethodCall());
            }
        } else {
            for (TryNode.ExceptionOccurrence occurence : tryNode.getExceptionOccurrences()) {
                setExceptionFlowOnMethod(occurence.getNode(), occurence.getType(), occurence.isMethodCall());
            }
        }
    }
    
    void setExceptionFlowOnMethodCall(CFGStatement node, ITypeBinding tbinding) {
        setExceptionFlow(node, tbinding.getTypeDeclaration(), true);
    }
    
    private void setExceptionFlowOnThrow(CFGStatement node, ITypeBinding tbinding) {
        setExceptionFlow(node, tbinding, false);
    }
    
    private void setExceptionFlow(CFGStatement node, ITypeBinding tbinding, boolean methodCall) {
        if (tbinding == null) {
            System.err.println("Unknown type in exception " + node.getASTNode());
            return;
        }
        
        if (tryNodeStack.size() > 0) {
            tryNodeStack.peek().addExceptionOccurrence(node, tbinding.getTypeDeclaration(), methodCall);
        } else {
            setExceptionFlowOnMethod(node, tbinding.getTypeDeclaration(), methodCall);
        }
    }
    
    private void setExceptionFlowOnMethod(CFGStatement node, ITypeBinding tbinding, boolean methodCall) {
        if (!cfg.isMethod()) {
            return;
        }
        
        CFGMethodEntry entry = (CFGMethodEntry)cfg.getEntryNode();
        JavaMethod jmethod = entry.getJavaMethod();
        if (jmethod.isInitializer()) {
            return;
        }
        
        for (CFGException catchNode : getCatchNodes(tbinding, entry.getExceptionNodes())) {
            ControlFlow exceptionEdge = createFlow(node, catchNode);
            if (methodCall) {
                exceptionEdge.setExceptionCatch();
            } else {
                exceptionEdge.setTrue();
            }
        }
    }
    
    private Set<CFGException> getCatchNodes(ITypeBinding tbinding, List<? extends CFGNode> catchNodes) {
        Set<CFGException> nodes = new HashSet<>();
        while (tbinding != null) {
            for (CFGNode node : catchNodes) {
                CFGException catchNode = (CFGException)node;
                if (tbinding.getQualifiedName().equals(catchNode.getTypeName())) {
                    nodes.add(catchNode);
                }
            }
            tbinding = tbinding.getSuperclass();
        }
        return nodes;
    }
    
    class Label {
        String name = "";
        CFGNode node;
        
        Label(String name, CFGNode node) {
            this.name = name;
            this.node = node;
        }
        
        CFGNode getNode() {
            return node;
        }
    }
    
    private Label getLabel(String name) {
        for (Label label : labels) {
            if (label.name.compareTo(name) == 0) {
                return label;
            }
        }
        return null;
    }
    
    public static boolean isCFGNode(ASTNode node) {
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
    
    class SwitchNode extends CFGStatement {
        
        private CFGNode defaultStartNode = null;
        private CFGNode defaultEndNode = null;
        
        SwitchNode(ASTNode node, CFGNode.Kind kind) {
            super(node, kind);
        }
        
        void setDefaultStartNode(CFGNode node) {
            defaultStartNode = node;
        }
        
        CFGNode getDefaultStartNode() {
            return defaultStartNode;
        }
        
        void setDefaultEndNode(CFGNode node) {
            defaultEndNode = node;
        }
        
        CFGNode getDefaultEndNode() {
            return defaultEndNode;
        }
        
        CFGNode getPredecessorOfDefault() {
            return defaultStartNode.getIncomingEdges().stream()
                    .filter(edge -> ((ControlFlow)edge).isFalse()).map(flow -> (CFGNode)flow.getSrcNode()).findFirst().orElse(null);
        }
        
        CFGNode getSuccessorOfDefault() {
            return defaultStartNode.getOutgoingEdges().stream()
                    .filter(edge -> ((ControlFlow)edge).isFalse()).map(flow -> (CFGNode)flow.getDstNode()).findFirst().orElse(null);
        }
        
        boolean hasDefault() {
            return defaultStartNode != null;
        }
    }
    
    class TryNode extends CFGStatement {
        
        private Set<ExceptionOccurrence> exceptionOccurrences = new HashSet<>();
        
        private List<CFGStatement> catchNodes = new ArrayList<>();
        
        private CFGStatement finallyNode;
        
        TryNode(ASTNode node, Kind kind) {
            super(node, kind);
        }
        
        void addExceptionOccurrence(CFGStatement node, ITypeBinding type, boolean methidCall) {
            exceptionOccurrences.add(new ExceptionOccurrence(node, type, methidCall));
        }
        
        Set<ExceptionOccurrence> getExceptionOccurrences() {
            return exceptionOccurrences;
        }
        
        void addCatchNode(CFGStatement node) {
            catchNodes.add(node);
        }
        
        void setCatchNodes(List<CFGException> nodes) {
            for (CFGException node : nodes) {
                addCatchNode(node);
            }
        }
        
        List<CFGStatement> getCatchNodes() {
            return catchNodes;
        }
        
        void setFinallyNode(CFGStatement node) {
            finallyNode = node;
        }
        
        CFGStatement getFinallyNode() {
            return finallyNode;
        }
        
        class ExceptionOccurrence {
            private CFGStatement node;
            private ITypeBinding type;
            private boolean methodCall;
            
            ExceptionOccurrence(CFGStatement node, ITypeBinding type, boolean methodCall) {
                this.node = node;
                this.type = type;
                this.methodCall = methodCall;
            }
            
            CFGStatement getNode() {
                return node;
            }
            
            ITypeBinding getType() {
              return type;
            }
            
            String getTypeName() {
                return type.getQualifiedName();
            }
            
            boolean isMethodCall() {
                return methodCall;
            }
        }
    }
}

