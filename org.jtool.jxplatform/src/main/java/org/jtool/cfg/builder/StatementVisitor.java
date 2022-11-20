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
import org.jtool.cfg.JVariableReference;
import org.jtool.cfg.JVersatileReference;
import org.jtool.cfg.CFGTry;
import org.jtool.graph.GraphEdge;
import org.jtool.srcmodel.JavaProject;
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
import org.eclipse.jdt.core.dom.ITypeBinding;
import org.eclipse.jdt.core.dom.IMethodBinding;
import org.eclipse.jdt.core.dom.IVariableBinding;
import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;
import java.util.Map;
import java.util.HashMap;
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
    
    public static final String RETURN_VALUE_SYMBOL = "$_";
    
    protected JavaProject jproject;
    protected CFG cfg;
    protected CFGNode prevNode;
    protected CFGNode nextNode;
    
    private Stack<CFGNode> blockEntries = new Stack<>();
    private Stack<CFGNode> blockExits = new Stack<>();
    
    private Set<Label> labels = new HashSet<>();
    
    private Stack<CFGTry> tryNodeStack = new Stack<>();
    private Map<CFGTry, Set<ExceptionOccurrence>> exceptionOccurrences = new HashMap<>();
    
    protected StatementVisitor(JavaProject jproject, CFG cfg, CFGNode prevNode, CFGNode nextNode) {
         this.jproject = jproject;
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
        ExpressionVisitor exprVisitor = new ExpressionVisitor(this, jproject, cfg, expNode);
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
            
            ExpressionVisitor exprVisitor = new ExpressionVisitor(this, jproject, cfg, declNode);
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
        
        ExpressionVisitor prefixVisitor = new ExpressionVisitor(this, jproject, cfg, invNode);
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
        
        ExpressionVisitor prefixVisitor = new ExpressionVisitor(this, jproject, cfg, invNode);
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
        ExpressionVisitor condVisitor = new ExpressionVisitor(this, jproject, cfg, ifNode);
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
        CFGStatement switchNode = new CFGStatement(node, CFGNode.Kind.switchSt);
        reconnect(switchNode);
        
        Expression condition = node.getExpression();
        ExpressionVisitor condVisitor = new ExpressionVisitor(this, jproject, cfg, switchNode);
        condition.accept(condVisitor);
        CFGNode curNode = condVisitor.getExitNode();
        
        ITypeBinding tbinding = condition.resolveTypeBinding();
        JVariableReference jvar = new JVersatileReference(node,
                "$SwitchDef", tbinding.getQualifiedName(), tbinding.isPrimitive());
        switchNode.addDefVariable(jvar);
        
        ControlFlow caseEdge = createFlow(curNode, nextNode);
        caseEdge.setTrue();
        
        CFGNode exitNode = new CFGNode();
        blockExits.push(exitNode);
        
        List<SwitchCaseLabel> caseLabels = new ArrayList<>();
        SwitchCaseLabel caseLabel = null;
        for (Statement statement : (List<Statement>)node.statements()) {
            if (statement instanceof SwitchCase) {
                caseLabel = new SwitchCaseLabel((SwitchCase)statement);
                caseLabels.add(caseLabel);
                continue;
            }
            caseLabel.add(statement);
        }
        
        for (SwitchCaseLabel label : caseLabels) {
            switchCase(switchNode, label);
        }
        
        nextNode.addIncomingEdges(exitNode.getIncomingEdges());
        CFGMerge mergeNode = new CFGMerge(node, switchNode);
        reconnect(mergeNode);
        
        blockExits.pop();
        
        ControlFlow edge = createFlow(mergeNode, nextNode);
        edge.setTrue();
        return false;
    }
    
    @SuppressWarnings("unchecked")
    private void switchCase(CFGStatement switchNode, SwitchCaseLabel caseLabel) {
        CFGNode curNode = null;
        CFGStatement caseNode;
        if (caseLabel.getNode().isDefault()) {
            caseNode = new CFGStatement(caseLabel.getNode(), CFGNode.Kind.switchDefault);
            reconnect(caseNode);
            
            curNode = caseNode;
        } else {
            caseNode = new CFGStatement(caseLabel.getNode(), CFGNode.Kind.switchCase);
            reconnect(caseNode);
            
            curNode = caseNode;
            for (Expression condition : (List<Expression>)caseLabel.getNode().expressions()) {
                ExpressionVisitor condVisitor = new ExpressionVisitor(this, jproject, cfg, caseNode);
                condition.accept(condVisitor);
                curNode = condVisitor.getExitNode();
            }
            
            caseNode.addUseVariables(switchNode.getDefVariables());
        }
        
        ControlFlow trueEdge = createFlow(curNode, nextNode);
        trueEdge.setTrue();
        
        for (Statement statement : caseLabel.statements()) {
            statement.accept(this); 
        }
        
        ControlFlow falseEdge = createFlow(caseNode, nextNode);
        falseEdge.setFalse();
    }
    
    @Override
    public boolean visit(WhileStatement node) {
        CFGStatement whileNode = new CFGStatement(node, CFGNode.Kind.whileSt);
        reconnect(whileNode);
        
        Expression condition = node.getExpression();
        ExpressionVisitor condVisitor = new ExpressionVisitor(this, jproject, cfg, whileNode);
        condition.accept(condVisitor);
        CFGNode curNode = condVisitor.getExitNode();
        
        ControlFlow trueEdge = createFlow(curNode, nextNode);
        trueEdge.setTrue();
        
        CFGNode entryNode = condVisitor.getEntryNode();
        CFGNode exitNode = new CFGNode();
        blockEntries.push(entryNode);
        blockExits.push(exitNode);
        
        setLabelNode(node, entryNode);
        
        Statement body = node.getBody();
        body.accept(this);
        
        ControlFlow loopbackEdge = cfg.getFlow(prevNode, nextNode);
        if (loopbackEdge != null) {
            loopbackEdge.setDstNode(entryNode);
            loopbackEdge.setLoopBack(entryNode);
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
        
        setLabelNode(node, entryNode);
        
        ControlFlow entryEdge = cfg.getFlow(prevNode, nextNode);
        Statement body = node.getBody();
        body.accept(this);
        
        nextNode.addIncomingEdges(entryNode.getIncomingEdges());
        CFGStatement doNode = new CFGStatement(node, CFGNode.Kind.doSt);
        reconnect(doNode);
        
        Expression condition = node.getExpression();
        ExpressionVisitor condVisitor = new ExpressionVisitor(this, jproject, cfg, doNode);
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
                ExpressionVisitor initVisitor = new ExpressionVisitor(this, jproject, cfg, initNode);
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
            ExpressionVisitor condVisitor = new ExpressionVisitor(this, jproject, cfg, forNode);
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
        
        setLabelNode(node, entryNode);
        
        Statement body = node.getBody();
        body.accept(this);
        for (Expression update : (List<Expression>)node.updaters()) {
            CFGStatement updateNode = new CFGStatement(update, CFGNode.Kind.assignment);
            ExpressionVisitor updateVisitor = new ExpressionVisitor(this, jproject, cfg, updateNode);
            update.accept(updateVisitor);
            CFGNode curNode = updateVisitor.getExitNode();
            reconnect(updateNode);
            
            ControlFlow edge = createFlow(curNode, nextNode);
            edge.setTrue();
        }
        
        ControlFlow loopbackEdge = cfg.getFlow(prevNode, nextNode);
        if (loopbackEdge != null) {
            loopbackEdge.setDstNode(entryNode);
            loopbackEdge.setLoopBack(entryNode);
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
        CFGStatement forNode = new CFGStatement(node, CFGNode.Kind.enhancedForSt);
        reconnect(forNode);
        
        SingleVariableDeclaration param = node.getParameter();
        ExpressionVisitor paramVisitor = new ExpressionVisitor(this, jproject, cfg, forNode);
        param.accept(paramVisitor);
        Expression expression = node.getExpression();
        ExpressionVisitor exprVisitor = new ExpressionVisitor(this, jproject, cfg, forNode);
        expression.accept(exprVisitor);
        CFGNode entryNode = exprVisitor.getEntryNode();
        CFGNode curNode = exprVisitor.getExitNode();
        
        ControlFlow edge = createFlow(curNode, nextNode);
        edge.setTrue();
        
        CFGNode exitNode = new CFGNode();
        blockEntries.push(entryNode);
        blockExits.push(exitNode);
        
        setLabelNode(node, entryNode);
        
        Statement body = node.getBody();
        body.accept(this);
        
        ControlFlow loopbackEdge = cfg.getFlow(prevNode, nextNode);
        if (loopbackEdge != null) {
            loopbackEdge.setDstNode(entryNode);
            loopbackEdge.setLoopBack(entryNode);
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
            jumpNode = getLabel(name).getEndNode();
        } else {
            jumpNode = (CFGNode)blockExits.peek();
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
            jumpNode = getLabel(name).getBeginNode();
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
            CFGMethodEntry methodNode = (CFGMethodEntry)cfg.getEntryNode();
            String type = methodNode.getJavaMethod().getReturnType();
            boolean primitive = methodNode.getJavaMethod().isPrimitiveReturnType();
            JVariableReference jvar = new JVersatileReference(methodNode.getASTNode(),
                    StatementVisitor.RETURN_VALUE_SYMBOL, type, primitive);
            returnNode.addDefVariable(jvar);
            
            ExpressionVisitor exprVisitor = new ExpressionVisitor(this, jproject, cfg, returnNode);
            expression.accept(exprVisitor);
            
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
        ExpressionVisitor exprVisitor = new ExpressionVisitor(this, jproject, cfg, assertNode);
        expression.accept(exprVisitor);
        CFGNode curNode = exprVisitor.getExitNode();
        Expression message = node.getMessage();
        if (message != null) {
            ExpressionVisitor mesgVisitor = new ExpressionVisitor(this, jproject, cfg, assertNode);
            message.accept(mesgVisitor);
            curNode = mesgVisitor.getExitNode();
        }
        
        ControlFlow edge = createFlow(curNode, nextNode);
        edge.setTrue();
        return false;
    }
    
    @Override
    public boolean visit(LabeledStatement node) {
        String name = node.getLabel().getFullyQualifiedName();
        CFGNode exitNode = new CFGNode();
        labels.add(new Label(name, exitNode));
        
        Statement body = node.getBody();
        body.accept(this);
        
        nextNode.addIncomingEdges(exitNode.getIncomingEdges());
        return false;
    }
    
    @Override
    public boolean visit(SynchronizedStatement node) {
        CFGStatement syncNode = new CFGStatement(node, CFGNode.Kind.synchronizedSt);
        reconnect(syncNode);
        
        Expression expression = node.getExpression();
        ExpressionVisitor exprVisitor = new ExpressionVisitor(this, jproject, cfg, syncNode);
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
        ExpressionVisitor exprVisitor = new ExpressionVisitor(this, jproject, cfg, throwNode);
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
        CFGTry tryNode = new CFGTry(node);
        exceptionOccurrences.put(tryNode, new HashSet<ExceptionOccurrence>());
        
        reconnect(tryNode);
        
        tryNodeStack.push(tryNode);
        
        ControlFlow edge = createFlow(tryNode, nextNode);
        edge.setTrue();
        
        for (Expression resource : (List<Expression>)node.resources()) {
            CFGStatement resourceNode = new CFGStatement(resource, CFGNode.Kind.assignment);
            reconnect(resourceNode);
            
            ExpressionVisitor resourceVisitor = new ExpressionVisitor(this, jproject, cfg, resourceNode);
            resource.accept(resourceVisitor);
            CFGNode curNode = resourceVisitor.getExitNode();
            
            ControlFlow e = createFlow(curNode, nextNode);
            e.setTrue();
        }
        
        Statement body = node.getBody();
        body.accept(this);
        
        CFGMerge mergeNode = new CFGMerge(node, tryNode);
        reconnect(mergeNode);
        
        for (CatchClause clause : (List<CatchClause>)node.catchClauses()) { 
            visitCatchClause(tryNode, clause, mergeNode);
        }
        
        ControlFlow trueEdge = createFlow(mergeNode, nextNode);
        trueEdge.setTrue();
        
        Block finallyBlock = node.getFinally();
        if (finallyBlock != null) {
            visitFinallyBlock(tryNode, finallyBlock);
        }
        
        catchClause(tryNode);
        
        return false;
    }
    
    @SuppressWarnings("unchecked")
    private void visitCatchClause(CFGTry tryNode, CatchClause node, CFGMerge mergeNode) {
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
            
            JVariableReference def = new JLocalVarReference(node.getException().getName(), vbinding);
            catchNode.setDefVariable(def);
            
            reconnect(catchNode);
        }
        
        ControlFlow trueEdge = createFlow(catchNode, nextNode);
        trueEdge.setTrue();
        
        ControlFlow fallEdge = createFlow(catchNode, mergeNode);
        fallEdge.setFallThrough();
        
        node.getBody().accept(this);
        reconnect(mergeNode);
    }
    
    private void visitFinallyBlock(CFGTry tryNode, Block block) {
        CFGStatement finallyNode = new CFGStatement(block, CFGNode.Kind.finallyClause);
        tryNode.setFinallyNode(finallyNode);
        
        reconnect(finallyNode);
        
        ControlFlow edge = createFlow(finallyNode, nextNode);
        edge.setTrue();
        
        block.accept(this);
        
        ControlFlow fallEdge = createFlow(finallyNode, nextNode);
        fallEdge.setFallThrough();
    }
    
    private void catchClause(CFGTry tryNode) {
        for (ExceptionOccurrence occurence : new HashSet<>(exceptionOccurrences.get(tryNode))) {
            for (CFGException catchNode : getCatchNodes(occurence.getType(), tryNode.getCatchNodes())) {
                ControlFlow exceptionEdge = createFlow(occurence.getNode(), catchNode);
                if (occurence.isMethodCall()) {
                    exceptionEdge.setExceptionCatch();
                } else {
                    exceptionEdge.setTrue();
                }
                exceptionOccurrences.get(tryNode).remove(occurence);
            }
        }
        
        tryNodeStack.pop();
        if (tryNodeStack.size() > 0) {
            for (ExceptionOccurrence occurence : exceptionOccurrences.get(tryNode)) {
                Set<ExceptionOccurrence> occurrences = exceptionOccurrences.get(tryNodeStack.peek());
                occurrences.add(
                        new ExceptionOccurrence(occurence.getNode(), occurence.getType(), occurence.isMethodCall()));
            }
        } else {
            for (ExceptionOccurrence occurence : exceptionOccurrences.get(tryNode)) {
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
            Set<ExceptionOccurrence> occurrences = exceptionOccurrences.get(tryNodeStack.peek());
            occurrences.add(new ExceptionOccurrence(node, tbinding.getTypeDeclaration(), methodCall));
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
    
    private void setLabelNode(ASTNode node, CFGNode entryNode) {
        if (node.getParent() instanceof LabeledStatement) {
            LabeledStatement label = (LabeledStatement)node.getParent();
            String name = label.getLabel().getFullyQualifiedName();
            getLabel(name).setBeginNode(entryNode);
        }
    }
    
    private Label getLabel(String name) {
        for (Label label : labels) {
            if (label.name.compareTo(name) == 0) {
                return label;
            }
        }
        return new Label("", null, null);
    }
    
    private class Label {
        String name = "";
        CFGNode begin = null;
        CFGNode end;
        
        Label(String name, CFGNode begin, CFGNode end) {
            this.name = name;
            this.begin = begin;
            this.end = end;
        }
        
        Label(String name, CFGNode end) {
            this.name = name;
            this.end = end;
        }
        
        void setBeginNode(CFGNode begin) {
            this.begin = begin;
        }
        
        CFGNode getBeginNode() {
            return begin;
        }
        
        CFGNode getEndNode() {
            return end;
        }
    }
    
    private class SwitchCaseLabel {
        
        private SwitchCase node;
        
        private List<Statement> statements = new ArrayList<>();
        
        SwitchCaseLabel(SwitchCase node) {
            this.node = node;
        }
        
        SwitchCase getNode() {
            return node;
        }
        
        void add(Statement statement) {
            statements.add(statement);
        }
        
        List<Statement> statements() {
            return statements;
        }
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
        
        boolean isMethodCall() {
            return methodCall;
        }
    }
}
