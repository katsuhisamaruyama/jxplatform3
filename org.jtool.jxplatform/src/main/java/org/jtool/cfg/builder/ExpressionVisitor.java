/*
 *  Copyright 2022
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.cfg.builder;

import org.jtool.cfg.CFG;
import org.jtool.cfg.CFGMethodCall;
import org.jtool.cfg.CFGMethodEntry;
import org.jtool.cfg.CFGNode;
import org.jtool.cfg.CFGParameter;
import org.jtool.cfg.CFGReceiver;
import org.jtool.cfg.CFGStatement;
import org.jtool.cfg.ControlFlow;
import org.jtool.cfg.JFieldReference;
import org.jtool.cfg.JLocalVarReference;
import org.jtool.cfg.JMethodReference;
import org.jtool.cfg.JReference;
import org.jtool.cfg.JSpecialVarReference;
import org.jtool.graph.GraphEdge;
import org.jtool.srcmodel.JavaMethod;
import org.jtool.srcmodel.JavaProject;
import org.jtool.srcmodel.builder.ExceptionTypeCollector;
import org.jtool.srcmodel.JavaClass;
import org.jtool.srcmodel.JavaElementUtil;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ArrayAccess;
import org.eclipse.jdt.core.dom.ArrayCreation;
import org.eclipse.jdt.core.dom.Assignment;
import org.eclipse.jdt.core.dom.ClassInstanceCreation;
import org.eclipse.jdt.core.dom.ConditionalExpression;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.FieldAccess;
import org.eclipse.jdt.core.dom.SuperFieldAccess;
import org.eclipse.jdt.core.dom.InfixExpression;
import org.eclipse.jdt.core.dom.LambdaExpression;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.core.dom.SuperMethodInvocation;
import org.eclipse.jdt.core.dom.PostfixExpression;
import org.eclipse.jdt.core.dom.PrefixExpression;
import org.eclipse.jdt.core.dom.Name;
import org.eclipse.jdt.core.dom.SimpleName;
import org.eclipse.jdt.core.dom.ThisExpression;
import org.eclipse.jdt.core.dom.QualifiedName;
import org.eclipse.jdt.core.dom.VariableDeclaration;
import org.eclipse.jdt.core.dom.SingleVariableDeclaration;
import org.eclipse.jdt.core.dom.StringLiteral;
import org.eclipse.jdt.core.dom.SwitchExpression;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;
import org.eclipse.jdt.core.dom.ConstructorInvocation;
import org.eclipse.jdt.core.dom.SuperConstructorInvocation;
import org.eclipse.jdt.core.dom.EnumConstantDeclaration;
import org.eclipse.jdt.core.dom.IBinding;
import org.eclipse.jdt.core.dom.ITypeBinding;
import org.eclipse.jdt.core.dom.IMethodBinding;
import org.eclipse.jdt.core.dom.IVariableBinding;
import org.eclipse.jdt.core.dom.BooleanLiteral;
import org.eclipse.jdt.core.dom.CharacterLiteral;
import org.eclipse.jdt.core.dom.NullLiteral;
import org.eclipse.jdt.core.dom.NumberLiteral;
import org.eclipse.jdt.core.dom.TypeLiteral;
import org.eclipse.jdt.core.dom.CreationReference;
import org.eclipse.jdt.core.dom.ExpressionMethodReference;
import org.eclipse.jdt.core.dom.SuperMethodReference;
import org.eclipse.jdt.core.dom.TypeMethodReference;
import org.eclipse.jdt.core.dom.Statement;
import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;
import java.util.Stack;

/**
 * Creates a CFG nodes and edges by visiting AST nodes within an expression.
 * 
 * @see org.eclipse.jdt.core.dom.Expression
 * 
 * Expression:
 *   ArrayAccess
 *   
 *   ArrayCreation
 *   Assignment
 *   PrefixExpression
 *   PostfixExpression
 *   InfixExpression
 *   FieldAccess
 *   SuperFieldAccess
 *   ThisExpression
 *   LambdaExpression
 *   SingleVariableDeclaration
 *   VariableDeclarationExpression
 *    -VariableDeclarationFragment
 *   MethodInvocation
 *   SuperMethodInvocation
 *   ClassInstanceCreation
 *   ConstructorInvocation (this originally belongs to Statement)
 *   SuperConstructorInvocation (this originally belongs to Statement)
 *   EnumConstantDeclaration  (this originally belongs to BodyDeclaration)
 *   ConditionalExpression
 *   SwitchExpression
 *   StringLiteral
 *   TypeLiteral
 *   Name
 *    -SimpleName
 *    -QualifiedName
 *   LambdaExpression (this is treated as a method and its invocation)
 *   MethodReference
 *    -CreationReference
 *    -ExpressionMethodReference
 *    -SuperMethodReference
 *    -TypeMethodReference
 *   ArrayInitializer (this is treated in ArrayCreation)
 *   CastExpression (this is treated in MethodInvocation and ClassInstanceCreation)
 *   
 * Nothing special to do for the following AST nodes:
 *   Annotation
 *   InstanceofExpression
 *   ParenthesizedExpression
 *   LambdaExpression (this is treated as a method and its invocation)
 *   BooleanLiteral
 *   CharacterLiteral
 *   NullLiteral
 *   NumberLiteral
 * 
 * @author Katsuhisa Maruyama
 */
public class ExpressionVisitor extends ASTVisitor {
    
    protected StatementVisitor statementVisitor;
    
    protected CFG cfg;
    protected CFGStatement curNode;
    protected CFGStatement entryNode;
    
    protected static int temporaryVariableId = 1;
    
    private Stack<AnalysisMode> analysisMode = new Stack<>();
    private enum AnalysisMode {
        DEF, USE,
    }
    
    protected ExpressionVisitor(CFG cfg, CFGStatement node) {
        this(null, cfg, node);
    }
    
    protected ExpressionVisitor(StatementVisitor visitor, CFG cfg, CFGStatement node) {
        this.statementVisitor = visitor;
        this.cfg = cfg;
        
        curNode = node;
        entryNode = node;
        analysisMode.push(AnalysisMode.USE);
    }
    
    static void resetTemporaryVariableId() {
        temporaryVariableId = 100;
    }
    
    public CFGNode getEntryNode() {
        return entryNode;
    }
    
    public CFGNode getExitNode() {
        return curNode;
    }
    
    protected void insertBeforeCurrentNode(CFGStatement node) {
        Set<GraphEdge> edges = new HashSet<>(curNode.getIncomingEdges());
        for (GraphEdge edge : edges) {
            ControlFlow flow = (ControlFlow)edge;
            flow.setDstNode(node);
        }
        cfg.add(node);
        
        ControlFlow flow = new ControlFlow(node, curNode);
        flow.setTrue();
        cfg.add(flow);
    }
    
    @Override
    public boolean visit(ArrayAccess node) {
        Expression array = node.getArray();
        array.accept(this);
        
        Expression index = node.getIndex();
        analysisMode.push(AnalysisMode.USE);
        index.accept(this);
        analysisMode.pop();
        return false;
    }
    
    @Override
    @SuppressWarnings("unchecked")
    public boolean visit(ArrayCreation node) {
        analysisMode.push(AnalysisMode.USE);
        for (Expression expr : (List<Expression>)node.dimensions()) {
            expr.accept(this);
        }
        if (node.getInitializer() != null) {
            node.getInitializer().accept(this);
        }
        
        JReference jv = new JSpecialVarReference(node,
                "$" + String.valueOf(ExpressionVisitor.temporaryVariableId), JavaClass.ArrayClassFqn.fqn(), false);
        ExpressionVisitor.temporaryVariableId++;
        curNode.setUseVariable(jv);
        return false;
    }
    
    @Override
    public boolean visit(Assignment node) {
        curNode.setASTNode(node);
        curNode.setKind(CFGNode.Kind.assignment);
        
        Expression lefthand = node.getLeftHandSide();
        analysisMode.push(AnalysisMode.DEF);
        lefthand.accept(this);
        analysisMode.pop();
        
        if (node.getOperator() != Assignment.Operator.ASSIGN) {
            analysisMode.push(AnalysisMode.USE);
            lefthand.accept(this);
            analysisMode.pop();
        }
        
        Expression righthand = node.getRightHandSide();
        analysisMode.push(AnalysisMode.USE);
        righthand.accept(this);
        analysisMode.pop();
        return false;
    }
    
    @Override
    public boolean visit(PrefixExpression node) {
        curNode.setKind(CFGNode.Kind.assignment);
        
        Expression expr = node.getOperand();
        analysisMode.push(AnalysisMode.USE);
        expr.accept(this);
        analysisMode.pop();
        
        PrefixExpression.Operator operator = node.getOperator();
        if (operator == PrefixExpression.Operator.INCREMENT || operator == PrefixExpression.Operator.DECREMENT) {
            analysisMode.push(AnalysisMode.DEF);
            expr.accept(this);
            analysisMode.pop();
        }
        return false;
    }
    
    @Override
    public boolean visit(PostfixExpression node) {
        curNode.setKind(CFGNode.Kind.assignment);
        
        Expression expr = node.getOperand();
        analysisMode.push(AnalysisMode.USE);
        expr.accept(this);
        analysisMode.pop();
        
        PostfixExpression.Operator operator = node.getOperator();
        if (operator == PostfixExpression.Operator.INCREMENT || operator == PostfixExpression.Operator.DECREMENT) {
            analysisMode.push(AnalysisMode.DEF);
            expr.accept(this);
            analysisMode.pop();
        }
        return false;
    }
    
    @Override
    public boolean visit(InfixExpression node) {
        Expression expr = node.getLeftOperand();
        analysisMode.push(AnalysisMode.USE);
        expr.accept(this);
        analysisMode.pop();
        
        expr = node.getRightOperand();
        analysisMode.push(AnalysisMode.USE);
        expr.accept(this);
        analysisMode.pop();
        
        for (Object obj : node.extendedOperands()) {
            Expression e = (Expression)obj;
            analysisMode.push(AnalysisMode.USE);
            e.accept(this);
            analysisMode.pop();
        }
        return false;
    }
    
    @Override
    public boolean visit(FieldAccess node) {
        IVariableBinding vbinding = getVariableBinding(node.getName());
        if (vbinding == null) {
            return false;
        }
        
        Expression receiver = node.getExpression();
        analysisMode.push(AnalysisMode.USE);
        receiver.accept(this);
        analysisMode.pop();
        
        if (curNode.getUseVariables().size() > 0) {
            JReference ref = curNode.getUseVariables().get(curNode.getUseVariables().size() - 1);
            String referenceForm = ref.getReferenceForm();
            if (referenceForm.startsWith("$")) {
                referenceForm = referenceForm + "." + node.getName().getIdentifier();
            } else if (referenceForm.startsWith("this")) {
                referenceForm = referenceForm + "." + node.getName().getFullyQualifiedName();
            } else {
                referenceForm = node.getName().getFullyQualifiedName();
            }
            
            JFieldReference jfacc = new JFieldReference(node, node.getName(), vbinding);
            jfacc.changeReferenceForm(referenceForm);
            if (analysisMode.peek() == AnalysisMode.DEF) {
                curNode.addDefVariable(jfacc);
            } else {
                curNode.addUseVariable(jfacc);
            }
        }
        return false;
    }
    
    @Override
    public boolean visit(SuperFieldAccess node) {
        Name qualifier = node.getQualifier();
        String referenceName = "super." + node.getName().getIdentifier();
        if (qualifier != null) {
            referenceName = qualifier.getFullyQualifiedName() + "." + referenceName;
        }
        
        JFieldReference jfacc = new JFieldReference(node, node.getName(), referenceName, node.resolveFieldBinding());
        if (analysisMode.peek() == AnalysisMode.DEF) {
            curNode.addDefVariable(jfacc);
        } else {
            curNode.addUseVariable(jfacc);
        }
        return false;
    }
    
    @Override
    public boolean visit(ThisExpression node) {
        Name qualifier = node.getQualifier();
        JReference jvar;
        if (qualifier != null) {
            jvar = new JSpecialVarReference(node, "this", qualifier.resolveTypeBinding());
        } else {
            jvar = new JSpecialVarReference(node, "this", false);
        }
        curNode.addUseVariable(jvar);
        return false;
    }
    
    @Override
    @SuppressWarnings("unchecked")
    public boolean visit(LambdaExpression node) {
        ITypeBinding tbinding = node.resolveTypeBinding();
        if (tbinding == null) {
            return false;
        }
        
        CFGStatement lambdaNode = new CFGStatement(node, CFGNode.Kind.lambda);
        
        insertBeforeCurrentNode(lambdaNode);
        
        CFGStatement tmpNode = curNode;
        curNode = lambdaNode;
        analysisMode.push(AnalysisMode.USE);
        node.getBody().accept(this);
        analysisMode.pop();
        curNode = tmpNode;
        
        Set<String> formals = new HashSet<>();
        for (VariableDeclaration vardecl : (List<VariableDeclaration>)node.parameters()) {
            formals.add(vardecl.getName().getIdentifier());
        }
        
        for (JReference ref : new ArrayList<>(lambdaNode.getUseVariables())) {
            if (ref.isLocalAccess()) {
                JLocalVarReference var = (JLocalVarReference)ref;
                if (formals.contains(var.getName())) {
                    lambdaNode.getUseVariables().remove(ref);
                }
            }
        }
        
        JReference jv = new JSpecialVarReference(node,
                "$" + String.valueOf(temporaryVariableId),
                tbinding.getErasure().getQualifiedName(), tbinding.isPrimitive());
        temporaryVariableId++;
        lambdaNode.setDefVariable(jv);
        curNode.setUseVariable(jv);
        return false;
    }
    
    @Override
    public boolean visit(CreationReference node) {
        return methodReference(node, node.resolveTypeBinding());
    }
    
    @Override
    public boolean visit(SuperMethodReference node) {
        return methodReference(node, node.resolveTypeBinding());
    }
    
    @Override
    public boolean visit(TypeMethodReference node) {
        return methodReference(node, node.resolveTypeBinding());
    }
    
    private boolean methodReference(ASTNode node, ITypeBinding tbinding) {
        if (tbinding == null) {
            return false;
        }
        
        CFGStatement lambdaNode = new CFGStatement(node, CFGNode.Kind.lambda);
        
        insertBeforeCurrentNode(lambdaNode);
        
        JReference jv = new JSpecialVarReference(node,
                "$" + String.valueOf(temporaryVariableId),
                tbinding.getErasure().getQualifiedName(), tbinding.isPrimitive());
        temporaryVariableId++;
        lambdaNode.setDefVariable(jv);
        curNode.setUseVariable(jv);
        return false;
    }
    
    @Override
    public boolean visit(ExpressionMethodReference node) {
        ITypeBinding tbinding = node.resolveTypeBinding();
        if (tbinding == null) {
            return false;
        }
        
        CFGStatement lambdaNode = new CFGStatement(node, CFGNode.Kind.lambda);
        
        insertBeforeCurrentNode(lambdaNode);
        
        CFGStatement tmpNode = curNode;
        curNode = lambdaNode;
        analysisMode.push(AnalysisMode.USE);
        node.getExpression().accept(this);
        analysisMode.pop();
        curNode = tmpNode;
        
        JReference jv = new JSpecialVarReference(node,
                "$" + String.valueOf(temporaryVariableId),
                tbinding.getErasure().getQualifiedName(), tbinding.isPrimitive());
        temporaryVariableId++;
        lambdaNode.setDefVariable(jv);
        curNode.setUseVariable(jv);
        return false;
    }
    
    @Override
    public boolean visit(SingleVariableDeclaration node) {
        variableDeclaration(node);
        return false;
    }
    
    @Override
    public boolean visit(VariableDeclarationFragment node) {
        variableDeclaration(node);
        return false;
    }
    
    private void variableDeclaration(VariableDeclaration node) {
        IVariableBinding vbinding = getVariableBinding(node.getName());
        if (vbinding == null) {
            return;
        }
        
        if (vbinding.isEnumConstant()) {
            curNode.setASTNode(node);
            curNode.setKind(CFGNode.Kind.enumConstantDeclaration);
        } else if (vbinding.isField()) {
            curNode.setASTNode(node);
            curNode.setKind(CFGNode.Kind.fieldDeclaration);
        } else {
            curNode.setASTNode(node);
            curNode.setKind(CFGNode.Kind.localDeclaration);
        }
        
        SimpleName name = node.getName();
        analysisMode.push(AnalysisMode.DEF);
        name.accept(this);
        analysisMode.pop();
        
        Expression initializer = node.getInitializer();
        if (initializer != null) {
            analysisMode.push(AnalysisMode.USE);
            initializer.accept(this);
            analysisMode.pop();
        }
    }
    
    @Override
    @SuppressWarnings("unchecked")
    public boolean visit(MethodInvocation node) {
        IMethodBinding mbinding = node.resolveMethodBinding();
        if (mbinding == null) {
            return false;
        }
        
        Expression receiver = node.getExpression();
        CFGReceiver receiverNode;
        if (receiver != null) {
            receiverNode = new CFGReceiver(receiver, CFGNode.Kind.receiver);
        } else {
            receiverNode = new CFGReceiver(node, CFGNode.Kind.receiver);
        }
        receiverNode.setName("this");
        
        insertBeforeCurrentNode(receiverNode);
        
        if (receiver != null) {
            CFGStatement tmpNode = curNode;
            curNode = receiverNode;
            analysisMode.push(AnalysisMode.USE);
            receiver.accept(this);
            analysisMode.pop();
            curNode = tmpNode;
            
            if (receiverNode.getUseVariables().size() > 0) {
                JReference ref = receiverNode.getUseVariables().get(receiverNode.getUseVariables().size() - 1);
                receiverNode.setName(ref.getReferenceForm());
            }
        }
        
        JMethodReference jcall = new JMethodReference(node, node.getName(), mbinding, node.arguments());
        CFGMethodCall callNode = new CFGMethodCall(node, CFGNode.Kind.methodCall, jcall);
        jcall.setReceiver(receiverNode);
        jcall.setExplicitReceiver(receiver != null);
        
        setActualNodes(callNode, node.arguments());
        setExceptionFlow(callNode, jcall);
        return false;
    }
    
    @Override
    @SuppressWarnings("unchecked")
    public boolean visit(SuperMethodInvocation node) {
        IMethodBinding mbinding = node.resolveMethodBinding();
        if (mbinding == null) {
            return false;
        }
        
        CFGReceiver receiverNode = new CFGReceiver(node, CFGNode.Kind.receiver);
        Name qualifier = node.getQualifier();
        if (qualifier != null) {
            receiverNode.setName(qualifier.getFullyQualifiedName() + "." + "super");
        } else {
            receiverNode.setName("super");
        }
        insertBeforeCurrentNode(receiverNode);
        
        JMethodReference jcall = new JMethodReference(node, node.getName(), mbinding, node.arguments());
        CFGMethodCall callNode = new CFGMethodCall(node, CFGNode.Kind.methodCall, jcall);
        jcall.setReceiver(receiverNode);
        jcall.setExplicitReceiver(true);
        
        setActualNodes(callNode, node.arguments());
        setExceptionFlow(callNode, jcall);
        return false;
    }
    
    @Override
    @SuppressWarnings("unchecked")
    public boolean visit(ConstructorInvocation node) {
        IMethodBinding mbinding = node.resolveConstructorBinding();
        if (mbinding == null) {
            return false;
        }
        
        CFGReceiver receiverNode = new CFGReceiver(node, CFGNode.Kind.receiver);
        receiverNode.setName("this");
        
        insertBeforeCurrentNode(receiverNode);
        
        JMethodReference jcall = new JMethodReference(node, node, mbinding, node.arguments());
        CFGMethodCall callNode = new CFGMethodCall(node, CFGNode.Kind.constructorCall, jcall);
        jcall.setReceiver(receiverNode);
        jcall.setExplicitReceiver(false);
        
        setActualNodes(callNode, node.arguments());
        setExceptionFlow(callNode, jcall);
        return false;
    }
    
    @Override
    @SuppressWarnings("unchecked")
    public boolean visit(SuperConstructorInvocation node) {
        IMethodBinding mbinding = node.resolveConstructorBinding();
        if (mbinding == null) {
            return false;
        }
        
        Expression receiver = node.getExpression();
        CFGReceiver receiverNode;
        if (receiver != null) {
            receiverNode = new CFGReceiver(receiver, CFGNode.Kind.receiver);
        } else {
            receiverNode = new CFGReceiver(node, CFGNode.Kind.receiver);
        }
        receiverNode.setName("super");
        
        insertBeforeCurrentNode(receiverNode);
        
        if (receiver != null) {
            CFGStatement tmpNode = curNode;
            curNode = receiverNode;
            analysisMode.push(AnalysisMode.USE);
            receiver.accept(this);
            analysisMode.pop();
            curNode = tmpNode;
            
            if (receiverNode.getUseVariables().size() > 0) {
                JReference ref = receiverNode.getUseVariables().get(receiverNode.getUseVariables().size() - 1);
                receiverNode.setName(ref.getReferenceForm() + "." + "super");
            }
        }
        
        JMethodReference jcall = new JMethodReference(node, node, mbinding, node.arguments());
        CFGMethodCall callNode = new CFGMethodCall(node, CFGNode.Kind.constructorCall, jcall);
        jcall.setReceiver(receiverNode);
        jcall.setExplicitReceiver(receiver != null);
        
        setActualNodes(callNode, node.arguments());
        setExceptionFlow(callNode, jcall);
        return false;
    }
    
    @Override
    @SuppressWarnings("unchecked")
    public boolean visit(ClassInstanceCreation node) {
        IMethodBinding mbinding = node.resolveConstructorBinding();
        if (mbinding == null) {
            return false;
        }
        
        Expression receiver = node.getExpression();
        CFGReceiver receiverNode;
        if (receiver != null) {
            receiverNode = new CFGReceiver(receiver, CFGNode.Kind.receiver);
        } else {
            receiverNode = new CFGReceiver(node, CFGNode.Kind.receiver);
        }
        
        insertBeforeCurrentNode(receiverNode);
        
        final String TEMP_INSTANCE_NAME = "$InstanceName";
        receiverNode.setName(TEMP_INSTANCE_NAME);
        if (receiver != null) {
            CFGStatement tmpNode = curNode;
            curNode = receiverNode;
            analysisMode.push(AnalysisMode.USE);
            receiver.accept(this);
            analysisMode.pop();
            curNode = tmpNode;
            
            if (receiverNode.getUseVariables().size() > 0) {
                JReference ref = receiverNode.getUseVariables().get(receiverNode.getUseVariables().size() - 1);
                receiverNode.setName(ref.getReferenceForm() + "." + receiverNode.getName());
            }
        }
        
        JMethodReference jcall = new JMethodReference(node, node.getType(), mbinding, node.arguments());
        CFGMethodCall callNode = new CFGMethodCall(node, CFGNode.Kind.methodCall, jcall);
        jcall.setReceiver(receiverNode);
        
        setActualNodes(callNode, node.arguments());
        setExceptionFlow(callNode, jcall);
        
        replaceReceiverName(node, callNode, TEMP_INSTANCE_NAME);
        return false;
    }
    
    private void replaceReceiverName(ASTNode node, CFGMethodCall callNode, String replaced) {
        String name;
        if (node.getParent().equals(entryNode.getASTNode()) &&
           (entryNode.isLocalDeclaration() || entryNode.isFieldDeclaration())) {
            name = entryNode.getDefVariables().get(0).getReferenceForm();
        } else if (curNode.getDefVariables().size() > 0) {
            name = curNode.getDefVariables().get(0).getReferenceForm();
        } else {
            name = callNode.getActualOutForReturn().getDefVariable().getName();
        }
        
        String receiverName = callNode.getReceiver().getName().replace(replaced, name);
        callNode.getReceiver().setName(receiverName);
    }
    
    private void setActualNodes(CFGMethodCall callNode, List<Expression> arguments) {
        boolean actual = callNode.getMethodCall().isInProject();
        if (actual) {
            createActualIns(callNode, arguments);
            insertBeforeCurrentNode(callNode);
        } else {
            insertBeforeCurrentNode(callNode);
            mergeActualIn(callNode, arguments);
        }
        createActualOutForReturn(callNode);
    }
    
    private void createActualIns(CFGMethodCall callNode, List<Expression> arguments) {
        for (int ordinal = 0; ordinal < arguments.size(); ordinal++) {
            createActualIn(callNode, arguments.get(ordinal), ordinal);
        }
    }
    
    private void createActualIn(CFGMethodCall callNode, Expression node, int ordinal) {
        CFGParameter actualInNode = new CFGParameter(node, CFGNode.Kind.actualIn, ordinal);
        actualInNode.setParent(callNode);
        callNode.addActualIn(actualInNode);
        
        String type = callNode.getMethodCall().getArgumentType(ordinal);
        boolean primitive = callNode.getMethodCall().isArgumentPrimitiveType(ordinal);
        JReference actualIn = new JSpecialVarReference(node,
                "$" + String.valueOf(temporaryVariableId), type, primitive);
        temporaryVariableId++;
        actualInNode.addDefVariable(actualIn);
        
        insertBeforeCurrentNode(actualInNode);
        
        CFGStatement tmpNode = curNode;
        curNode = actualInNode;
        analysisMode.push(AnalysisMode.USE);
        node.accept(this);
        analysisMode.pop();
        curNode = tmpNode;
    }
    
    private void mergeActualIn(CFGMethodCall callNode, List<Expression> arguments) {
        CFGStatement tmpNode = curNode;
        curNode = callNode;
        for (int ordinal = 0; ordinal < arguments.size(); ordinal++) {
            analysisMode.push(AnalysisMode.USE);
            arguments.get(ordinal).accept(this);
            analysisMode.pop();
        }
        curNode = tmpNode;
    }
    
    private void createActualOutForReturn(CFGMethodCall callNode) {
        CFGParameter actualOutNodeForReturn = new CFGParameter(callNode.getASTNode(), CFGNode.Kind.actualOut, 0);
        actualOutNodeForReturn.setParent(callNode);
        callNode.setActualOutForReturn(actualOutNodeForReturn);
        
        String type = callNode.getReturnType();
        boolean primitive = callNode.isPrimitiveType();
        JReference def = new JSpecialVarReference(callNode.getASTNode(),
                "$" + String.valueOf(temporaryVariableId), type, primitive);
        temporaryVariableId++;
        actualOutNodeForReturn.addDefVariable(def);
        
        insertBeforeCurrentNode(actualOutNodeForReturn);
        
        curNode.addUseVariable(actualOutNodeForReturn.getDefVariable());
    }
    
    private void setExceptionFlow(CFGMethodCall callNode, JMethodReference jcall) {
        if (!cfg.isMethod()) {
            return;
        }
        
        if (statementVisitor != null) {
            for (ITypeBinding type : jcall.getExceptionTypes()) {
                statementVisitor.setExceptionFlowOnMethodCall(callNode, type);
            }
            
            CFGMethodEntry entry = (CFGMethodEntry)cfg.getEntryNode();
            JavaProject jproject = entry.getJavaMethod().getJavaProject();
            JavaClass jclass = jproject.getClass(jcall.getDeclaringClassName());
            if (jclass != null) {
                JavaMethod jmethod = jclass.getMethod(jcall.getSignature());
                if (jmethod != null && !jmethod.isSynthetic()) {
                    ExceptionTypeCollector collector = new ExceptionTypeCollector();
                    for (ITypeBinding type : collector.getExceptions(jmethod)) {
                        statementVisitor.setExceptionFlowOnMethodCall(callNode, type);
                    }
                }
            }
        }
    }
    
    @Override
    @SuppressWarnings("unchecked")
    public boolean visit(EnumConstantDeclaration node) {
        IMethodBinding mbinding = node.resolveConstructorBinding();
        if (mbinding == null) {
            return false;
        }
        
        JMethodReference jcall = new JMethodReference(node, node.getName(), mbinding, node.arguments());
        CFGMethodCall callNode = new CFGMethodCall(node, CFGNode.Kind.constructorCall, jcall);
        
        setActualNodes(callNode, node.arguments());
        setExceptionFlow(callNode, jcall);
        return false;
    }
    
    
    @Override
    public boolean visit(ConditionalExpression node) {
        CFGStatement condExpNode = new CFGStatement(node, CFGNode.Kind.conditionalExpression);
        
        insertBeforeCurrentNode(condExpNode);
        
        CFGStatement tmpNode = curNode;
        curNode = condExpNode;
        analysisMode.push(AnalysisMode.USE);
        node.getExpression().accept(this);
        node.getThenExpression().accept(this);
        node.getElseExpression().accept(this);
        analysisMode.pop();
        curNode = tmpNode;
        
        ITypeBinding tbinding = node.resolveTypeBinding();
        String type = tbinding.getErasure().getQualifiedName();
        if (!JavaElementUtil.isVoid(type)) {
            JReference def = new JSpecialVarReference(node,
                    "$" + String.valueOf(temporaryVariableId), type, tbinding.isPrimitive());
            temporaryVariableId++;
            condExpNode.addDefVariable(def);
            curNode.addUseVariable(def);
        }
        return false;
    }
    
    @Override
    @SuppressWarnings("unchecked")
    public boolean visit(SwitchExpression node) {
        
        CFGStatement switchExpNode = new CFGStatement(node, CFGNode.Kind.switchExpression);
        
        insertBeforeCurrentNode(switchExpNode);
        
        CFGStatement tmpNode = curNode;
        curNode = switchExpNode;
        analysisMode.push(AnalysisMode.USE);
        node.getExpression().accept(this);
        for (Statement st : (List<Statement>)node.statements()) {
            st.accept(this);
        }
        analysisMode.pop();
        curNode = tmpNode;
        
        ITypeBinding tbinding = node.resolveTypeBinding();
        String type = tbinding.getErasure().getQualifiedName();
        if (!JavaElementUtil.isVoid(type)) {
            JReference def = new JSpecialVarReference(node,
                "$" + String.valueOf(temporaryVariableId), type, tbinding.isPrimitive());
            temporaryVariableId++;
            switchExpNode.addDefVariable(def);
            curNode.addUseVariable(def);
        }
        return false;
    }
    
    @Override
    public boolean visit(SimpleName node) {
        collectVariable(node);
        return false;
    }
    
    @Override
    public boolean visit(QualifiedName node) {
        IVariableBinding vbinding = getVariableBinding(node);
        if (vbinding != null && vbinding.isField()) {
            Name prefix = node.getQualifier();
            if (prefix.isSimpleName()) {
                analysisMode.push(AnalysisMode.USE);
                JReference jvar = collectVariable((SimpleName)prefix);
                analysisMode.pop();
                
                JReference fvar;
                if (jvar != null) {
                    String name = jvar.getReferenceForm() + "." + node.getName().getIdentifier();
                    fvar = new JFieldReference(node, node.getName(), name, vbinding);
                } else {
                    fvar = new JFieldReference(node, node.getName(), node.getFullyQualifiedName(), vbinding);
                }
                if (analysisMode.peek() == AnalysisMode.DEF) {
                    curNode.addDefVariable(fvar);
                } else {
                    curNode.addUseVariable(fvar);
                }
                
            } else {
                JReference fvar = new JFieldReference(node, node, vbinding);
                if (analysisMode.peek() == AnalysisMode.DEF) {
                    curNode.addDefVariable(fvar);
                } else {
                    curNode.addUseVariable(fvar);
                }
                
                analysisMode.push(AnalysisMode.USE);
                node.getQualifier().accept(this);
                analysisMode.pop();
            }
        }
        return false;
    }
    
    private JReference collectVariable(SimpleName node) {
        IVariableBinding vbinding = getVariableBinding(node);
        if (vbinding != null) {
            JReference jvar;
            if (vbinding.isField()) {
                jvar = new JFieldReference(node, node, node.getIdentifier(), vbinding);
            } else {
                jvar = new JLocalVarReference(node, vbinding);
            }
            
            if (analysisMode.peek() == AnalysisMode.DEF) {
                curNode.addDefVariable(jvar);
            } else {
                curNode.addUseVariable(jvar);
            }
            return jvar;
        }
        return null;
    }
    
    private IVariableBinding getVariableBinding(Name node) {
        IBinding binding = node.resolveBinding();
        if (binding != null && binding.getKind() == IBinding.VARIABLE) {
            return (IVariableBinding)binding;
        }
        return null;
    }
    
    public static boolean isCFGNode(ASTNode node) {
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
    
    public static boolean isCFGNodeOnLiteral(ASTNode node) {
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
