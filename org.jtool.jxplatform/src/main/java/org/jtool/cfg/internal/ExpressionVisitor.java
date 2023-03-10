/*
 *  Copyright 2022
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.cfg.internal;

import org.jtool.cfg.CFG;
import org.jtool.cfg.CFGMethodCall;
import org.jtool.cfg.CFGNode;
import org.jtool.cfg.CFGParameter;
import org.jtool.cfg.CFGReceiver;
import org.jtool.cfg.CFGStatement;
import org.jtool.cfg.ControlFlow;
import org.jtool.cfg.JReference;
import org.jtool.cfg.JVariableReference;
import org.jtool.cfg.JFieldReference;
import org.jtool.cfg.JLocalVarReference;
import org.jtool.cfg.JMethodReference;
import org.jtool.graph.GraphEdge;
import org.jtool.srcmodel.JavaProject;
import org.jtool.srcmodel.JavaClass;
import org.jtool.srcmodel.JavaMethod;
import org.jtool.srcmodel.JavaField;
import org.jtool.srcmodel.internal.UncaughtExceptionTypeCollector;
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
import org.eclipse.jdt.core.dom.SwitchExpression;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;
import org.eclipse.jdt.core.dom.ConstructorInvocation;
import org.eclipse.jdt.core.dom.SuperConstructorInvocation;
import org.eclipse.jdt.core.dom.EnumConstantDeclaration;
import org.eclipse.jdt.core.dom.IBinding;
import org.eclipse.jdt.core.dom.ITypeBinding;
import org.eclipse.jdt.core.dom.IMethodBinding;
import org.eclipse.jdt.core.dom.IVariableBinding;
import org.eclipse.jdt.core.dom.CreationReference;
import org.eclipse.jdt.core.dom.ExpressionMethodReference;
import org.eclipse.jdt.core.dom.SuperMethodReference;
import org.eclipse.jdt.core.dom.TypeMethodReference;
import org.eclipse.jdt.core.dom.Statement;
import org.eclipse.jdt.core.dom.TypeLiteral;
import org.eclipse.jdt.core.dom.StringLiteral;
import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;
import java.util.Stack;
import java.util.stream.Collectors;

/**
 * Creates a CFG nodes and edges by visiting AST nodes within an expression.
 * 
 * @see org.eclipse.jdt.core.dom.Expression
 * 
 * Expression:
 *   ArrayAccess
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
    
    protected CFG cfg;
    protected JavaProject jproject;
    protected CFGStatement curNode;
    protected CFGStatement entryNode;
    
    protected StatementVisitor statementVisitor = null;
    
    protected Stack<CFGStatement> declarationOrAssignmentNode = new Stack<>();
    
    private Stack<AnalysisMode> analysisMode = new Stack<>();
    private enum AnalysisMode {
        DEF, USE,
    }
    
    private Stack<Expression> receivers = new Stack<>();
    
    private DominantStatement dominantStatement;
    
    protected ExpressionVisitor(StatementVisitor visitor, CFGStatement node) {
        assert visitor != null;
        assert node != null;
        
        this.statementVisitor = visitor;
        this.jproject = visitor.getProject();
        this.cfg = visitor.getCFG();
        this.dominantStatement = visitor.getStructuredStatement();
        
        curNode = node;
        entryNode = node;
        analysisMode.push(AnalysisMode.USE);
    }
    
    protected ExpressionVisitor(JavaProject jproject, CFGStatement node) {
        this(jproject, new CFG(), new DominantStatement(), node);
    }
    
    protected ExpressionVisitor(JavaProject jproject, CFG cfg, DominantStatement dominantStatement, CFGStatement node) {
        assert jproject != null;
        assert cfg != null;
        assert dominantStatement != null;
        assert node != null;
        
        this.jproject = jproject;
        this.cfg = cfg;
        this.dominantStatement = dominantStatement;
        
        curNode = node;
        entryNode = node;
        analysisMode.push(AnalysisMode.USE);
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
        analysisMode.pop();
        return false;
    }
    
    @Override
    public boolean visit(Assignment node) {
        if (curNode.isAssignment()) {
            curNode.setASTNode(node);
        }
        declarationOrAssignmentNode.push(curNode);
        
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
        
        declarationOrAssignmentNode.pop();
        return false;
    }
    
    @Override
    public boolean visit(PrefixExpression node) {
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
        visitReceiver(receiver, curNode);
        
        JVariableReference prefix = curNode.getUseLast();
        String referenceForm = prefix.getReferenceForm();
        if (referenceForm.indexOf(JReturnValueReference.METHOD_RETURN_SYMBOL) != -1) {
            referenceForm = referenceForm + "." + node.getName().getIdentifier();
        } else if (referenceForm.equals("this")) {
            referenceForm = referenceForm + "." + node.getName().getIdentifier();
            curNode.removeUseVariable(prefix);
        } else {
            referenceForm = node.getName().getFullyQualifiedName();
        }
        
        JFieldReference jvar = new JFieldReference(node, node.getName(), vbinding);
        jvar.changeReferenceForm(referenceForm);
        jvar.setPrefix(prefix);
        
        if (analysisMode.peek() == AnalysisMode.DEF) {
            curNode.addDefVariable(jvar);
        } else {
            curNode.addUseVariable(jvar);
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
        
        JFieldReference jvar = new JFieldReference(node, node.getName(), referenceName, node.resolveFieldBinding());
        if (analysisMode.peek() == AnalysisMode.DEF) {
            curNode.addDefVariable(jvar);
        } else {
            curNode.addUseVariable(jvar);
        }
        return false;
    }
    
    @Override
    public boolean visit(ThisExpression node) {
        Name qualifier = node.getQualifier();
        String className;
        if (qualifier != null) {
            className = qualifier.resolveTypeBinding().getTypeDeclaration().getQualifiedName();
        } else {
            className = node.resolveTypeBinding().getTypeDeclaration().getQualifiedName();
        }
        JVariableReference jvar = new JFieldReference(node, className, "this", "this");
        curNode.addUseVariable(jvar);
        return false;
    }
    
    @Override
    public boolean visit(SingleVariableDeclaration node) {
        if (curNode.isEnhancedFor()) {
            SimpleName name = node.getName();
            analysisMode.push(AnalysisMode.DEF);
            name.accept(this);
            analysisMode.pop();
        } else {
            variableDeclaration(node);
        }
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
        
        curNode.setASTNode(node);
        declarationOrAssignmentNode.push(curNode);
        
        if (vbinding.isEnumConstant()) {
            curNode.setKind(CFGNode.Kind.enumConstantDeclaration);
        } else if (vbinding.isField()) {
            curNode.setKind(CFGNode.Kind.fieldDeclaration);
        } else {
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
        
        declarationOrAssignmentNode.pop();
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
        entryNode = receiverNode;
        
        dominantStatement.addImmediatePostDominator(receiverNode);
        
        insertBeforeCurrentNode(receiverNode);
        
        JVariableReference prefix = null;
        if (receiver != null) {
            visitReceiver(receiver, receiverNode);
            
            prefix = receiverNode.getUseLast();
            if (prefix != null) {
                receiverNode.setName(prefix.getReferenceForm());
            }
        }
        
        JMethodReference jcall = new JMethodReference(node, node.getName(), mbinding, node.arguments());
        CFGMethodCall callNode = new CFGMethodCall(node, CFGNode.Kind.methodCall, jcall);
        jcall.setReceiver(receiverNode);
        jcall.setExplicitReceiver(receiver != null);
        receiverNode.setMethodCall(callNode);
        
        dominantStatement.addImmediatePostDominator(callNode);
        
        createActualIn(callNode, node.arguments());
        JReturnValueReference ret = createActualOutForReturn(callNode, receiverNode.getName());
        ret.setPrefix(prefix);
        
        if (declarationOrAssignmentNode.empty() && entryNode.isReturn()) {
            callNode.setReturnValueName(StatementVisitor.RETURN_VALUE_SYMBOL);
        } else {
            callNode.setReturnValueName(ret.getReferenceForm());
        }
        
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
        entryNode = receiverNode;
        
        dominantStatement.addImmediatePostDominator(receiverNode);
        
        insertBeforeCurrentNode(receiverNode);
        
        JMethodReference jcall = new JMethodReference(node, node.getName(), mbinding, node.arguments());
        CFGMethodCall callNode = new CFGMethodCall(node, CFGNode.Kind.methodCall, jcall);
        jcall.setReceiver(receiverNode);
        jcall.setExplicitReceiver(true);
        receiverNode.setMethodCall(callNode);
        
        dominantStatement.addImmediatePostDominator(callNode);
        
        createActualIn(callNode, node.arguments());
        JReturnValueReference ret = createActualOutForReturn(callNode, receiverNode.getName());
        ret.setPrefix(null);
        
        if (declarationOrAssignmentNode.empty() && entryNode.isReturn()) {
            callNode.setReturnValueName(StatementVisitor.RETURN_VALUE_SYMBOL);
        } else {
            callNode.setReturnValueName(ret.getReferenceForm());
        }
        
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
        entryNode = receiverNode;
        
        dominantStatement.addImmediatePostDominator(receiverNode);
        
        insertBeforeCurrentNode(receiverNode);
        
        JMethodReference jcall = new JMethodReference(node, node, mbinding, node.arguments());
        CFGMethodCall callNode = new CFGMethodCall(node, CFGNode.Kind.constructorCall, jcall);
        jcall.setReceiver(receiverNode);
        jcall.setExplicitReceiver(false);
        receiverNode.setMethodCall(callNode);
        
        dominantStatement.addImmediatePostDominator(callNode);
        
        createActualIn(callNode, node.arguments());
        JReturnValueReference ret = createActualOutForReturn(callNode, receiverNode.getName());
        ret.setPrefix(null);
        callNode.setReturnValueName(ret.getReferenceForm());
        
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
        entryNode = receiverNode;
        
        dominantStatement.addImmediatePostDominator(receiverNode);
        
        insertBeforeCurrentNode(receiverNode);
        
        if (receiver != null) {
            visitReceiver(receiver, receiverNode);
            
            JVariableReference prefix = receiverNode.getUseLast();
            if (prefix != null) {
                receiverNode.setName(prefix.getReferenceForm() + "." + "super");
            }
        }
        
        JMethodReference jcall = new JMethodReference(node, node, mbinding, node.arguments());
        CFGMethodCall callNode = new CFGMethodCall(node, CFGNode.Kind.constructorCall, jcall);
        jcall.setReceiver(receiverNode);
        jcall.setExplicitReceiver(receiver != null);
        receiverNode.setMethodCall(callNode);
        
        dominantStatement.addImmediatePostDominator(callNode);
        
        createActualIn(callNode, node.arguments());
        JReturnValueReference ret = createActualOutForReturn(callNode, receiverNode.getName());
        ret.setPrefix(null);
        callNode.setReturnValueName(ret.getReferenceForm());
        
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
        String className = node.resolveConstructorBinding()
                .getDeclaringClass().getErasure().getQualifiedName();
        receiverNode.setName(className);
        entryNode = receiverNode;
        
        dominantStatement.addImmediatePostDominator(receiverNode);
        
        insertBeforeCurrentNode(receiverNode);
        
        JVariableReference prefix = null;
        if (receiver != null) {
            visitReceiver(receiver, receiverNode);
            
            prefix = receiverNode.getUseLast();
            if (prefix != null) {
                receiverNode.setName(prefix.getReferenceForm() + "." + receiverNode.getName());
            }
        }
        
        JMethodReference jcall = new JMethodReference(node, node.getType(), mbinding, node.arguments());
        CFGMethodCall callNode = new CFGMethodCall(node, CFGNode.Kind.constructorCall, jcall);
        jcall.setReceiver(receiverNode);
        jcall.setExplicitReceiver(receiver != null);
        receiverNode.setMethodCall(callNode);
        
        dominantStatement.addImmediatePostDominator(callNode);
        
        createActualIn(callNode, node.arguments());
        JReturnValueReference ret = createActualOutForReturn(callNode, receiverNode.getName());
        ret.setPrefix(prefix);
        
        if (declarationOrAssignmentNode.empty() && entryNode.isReturn()) {
            callNode.setReturnValueName(StatementVisitor.RETURN_VALUE_SYMBOL);
        } else {
            callNode.setReturnValueName(ret.getReferenceForm());
        }
        
        setExceptionFlow(callNode, jcall);
        return false;
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
        entryNode = callNode;
        
        dominantStatement.addImmediatePostDominator(callNode);
        
        createActualIn(callNode, node.arguments());
        JReturnValueReference ret = createActualOutForReturn(callNode, mbinding.getName());
        ret.setPrefix(null);
        
        if (declarationOrAssignmentNode.empty() && entryNode.isReturn()) {
            callNode.setReturnValueName(StatementVisitor.RETURN_VALUE_SYMBOL);
        } else {
            callNode.setReturnValueName(ret.getReferenceForm());
        }
        
        setExceptionFlow(callNode, jcall);
        return false;
    }
    
    private void visitReceiver(Expression receiver, CFGStatement receiverNode) {
        CFGStatement tmpNode = curNode;
        curNode = receiverNode;
        analysisMode.push(AnalysisMode.USE);
        receivers.push(receiver);
        receiver.accept(this);
        receivers.pop();
        analysisMode.pop();
        curNode = tmpNode;
    }
    
    private void createActualIn(CFGMethodCall callNode, List<Expression> arguments) {
        boolean actual = callNode.getMethodCall().isInProject();
        if (actual) {
            createActualIns(callNode, arguments);
            insertBeforeCurrentNode(callNode);
        } else {
            insertBeforeCurrentNode(callNode);
            mergeActualIn(callNode, arguments);
        }
    }
    
    private void createActualIns(CFGMethodCall callNode, List<Expression> arguments) {
        for (int ordinal = 0; ordinal < arguments.size(); ordinal++) {
            Expression arg = arguments.get(ordinal);
            CFGParameter actualInNode = new CFGParameter(arg, CFGNode.Kind.actualIn, ordinal);
            actualInNode.setParent(callNode);
            callNode.addActualIn(actualInNode);
            
            dominantStatement.addImmediatePostDominator(actualInNode);
            
            insertBeforeCurrentNode(actualInNode);
            
            CFGStatement tmpNode = curNode;
            curNode = actualInNode;
            analysisMode.push(AnalysisMode.USE);
            arg.accept(this);
            analysisMode.pop();
            curNode = tmpNode;
        }
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
    
    private JReturnValueReference createActualOutForReturn(CFGMethodCall callNode, String receiverName) {
        CFGParameter actualOutNode = new CFGParameter(callNode.getASTNode(), CFGNode.Kind.actualOut, 0);
        actualOutNode.setParent(callNode);
        callNode.setActualOut(actualOutNode);
        
        dominantStatement.addImmediatePostDominator(actualOutNode);
        
        insertBeforeCurrentNode(actualOutNode);
        
        String name = receiverName + "." + JReturnValueReference.METHOD_RETURN_SYMBOL + callNode.getSignature();
        String type = callNode.getReturnType();
        boolean primitive = callNode.isPrimitiveType();
        JReturnValueReference def = new JReturnValueReference(callNode.getASTNode(), name, type, primitive);
        actualOutNode.addDefVariable(def);
        
        curNode.addUseVariable(def);
        return def;
    }
    
    private void setExceptionFlow(CFGMethodCall callNode, JMethodReference jcall) {
        if (!cfg.isMethod()) {
            return;
        }
        
        if (statementVisitor != null) {
            for (ITypeBinding type : jcall.getExceptionTypes()) {
                statementVisitor.setExceptionFlowOnMethodCall(callNode, type);
            }
            
            JavaClass jclass = jproject.getClass(jcall.getDeclaringClassName());
            if (jclass != null) {
                JavaMethod jmethod = jclass.getMethod(jcall.getSignature());
                if (jmethod != null && !jmethod.isSynthetic()) {
                    UncaughtExceptionTypeCollector collector = new UncaughtExceptionTypeCollector();
                    for (ITypeBinding type : collector.getExceptions(jmethod)) {
                        statementVisitor.setExceptionFlowOnMethodCall(callNode, type);
                    }
                }
            }
        }
    }
    
    @Override
    public boolean visit(ConditionalExpression node) {
        analysisMode.push(AnalysisMode.USE);
        node.getExpression().accept(this);
        node.getThenExpression().accept(this);
        node.getElseExpression().accept(this);
        analysisMode.pop();
        return false;
    }
    
    @Override
    public boolean visit(SimpleName node) {
        IVariableBinding vbinding = getVariableBinding(node);
        if (vbinding != null) {
            JVariableReference var;
            if (vbinding.isField()) {
                var = new JFieldReference(node, node, node.getIdentifier(), vbinding);
            } else {
                var = new JLocalVarReference(node, vbinding);
            }
            
            if (analysisMode.peek() == AnalysisMode.DEF) {
                curNode.addDefVariable(var);
            } else {
                curNode.removeUseVariable(var);
                curNode.addUseVariable(var);
                if (!var.isPrimitiveType() && receivers.empty()) {
                    curNode.addUseVariables(getDanglingFields(var));
                }
            }
        }
        return false;
    }
    
    @Override
    public boolean visit(QualifiedName node) {
        IVariableBinding vbinding = getVariableBinding(node);
        if (vbinding != null && vbinding.isField()) {
            
            analysisMode.push(AnalysisMode.USE);
            node.getQualifier().accept(this);
            analysisMode.pop();
            
            List<JVariableReference> vars = curNode.getUseVariables().stream()
                    .filter(v -> v.isAvailable()).collect(Collectors.toList());
            JVariableReference prefix = vars.size() > 0 ? vars.get(vars.size() - 1) : null;
            
            JFieldReference var;
            if (prefix != null) {
                String name = prefix.getReferenceForm() + "." + node.getName().getIdentifier();
                var = new JFieldReference(node, node.getName(), name, vbinding);
                var.setPrefix(prefix);
            } else {
                var = new JFieldReference(node, node.getName(), node.getFullyQualifiedName(), vbinding);
            }
            
            if (analysisMode.peek() == AnalysisMode.DEF) {
                curNode.addDefVariable(var);
            } else {
                curNode.removeUseVariable(var);
                curNode.addUseVariable(var);
            }
        }
        return false;
    }
    
    private List<JVariableReference> getDanglingFields(JVariableReference var) {
        List<JVariableReference> vars = new ArrayList<>();
        JavaClass jc = jproject.getClass(var.getType());
        if (jc != null && !jc.isEnum()) {
            for (JavaField jf : jc.getFields() ) {
                if (!jf.isStatic()) {
                    JFieldReference jv =  new JFieldReference(var.getASTNode(),
                            jf.getClassName(), jf.getName(), var.getReferenceForm() + "." + jf.getName(),
                            jf.getType(), jf.isPrimitiveType(), jf.getModifiers(), jf.isInProject(), false);
                    vars.add(jv);
                }
            }
        }
        return vars;
    }
    
    private IVariableBinding getVariableBinding(Name node) {
        IBinding binding = node.resolveBinding();
        if (binding != null && binding.getKind() == IBinding.VARIABLE) {
            return (IVariableBinding)binding;
        }
        return null;
    }
    
    @Override
    public boolean visit(StringLiteral node) {
        setVariableForLiteral(node, node.resolveTypeBinding());
        return false;
    }
    
    @Override
    public boolean visit(TypeLiteral node) {
        setVariableForLiteral(node, node.resolveTypeBinding());
        return false;
    }
    
    private void setVariableForLiteral(ASTNode node, ITypeBinding tbinding) {
        JVariableReference jvar = new JVersatileReference(node,
                "$" + tbinding.getErasure().getQualifiedName(), tbinding);
        if (analysisMode.peek() == AnalysisMode.DEF) {
            curNode.addDefVariable(jvar);
        } else {
            curNode.addUseVariable(jvar);
        }
    }
    
    /*
     * This implementation is incomplete and has not tested.
     */
    @Override
    @SuppressWarnings("unchecked")
    public boolean visit(LambdaExpression node) {
        ITypeBinding tbinding = node.resolveTypeBinding();
        if (tbinding == null) {
            return false;
        }
        
        CFGStatement lambdaNode = new CFGStatement(node, CFGNode.Kind.lambda);
        entryNode = lambdaNode;
        
        dominantStatement.addImmediatePostDominator(lambdaNode);
        
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
        
        JVariableReference jv = new JUnsupportedReference(node,
                "$LAMBDA", tbinding.getErasure().getQualifiedName(), tbinding.isPrimitive());
        lambdaNode.setDefVariable(jv);
        curNode.setUseVariable(jv);
        return false;
    }
    
    /*
     * This implementation is incomplete and has not tested.
     */
    @Override
    public boolean visit(CreationReference node) {
        return methodReference(node, node.resolveTypeBinding());
    }
    
    /*
     * This implementation is incomplete and has not tested.
     */
    @Override
    public boolean visit(SuperMethodReference node) {
        return methodReference(node, node.resolveTypeBinding());
    }
    
    /*
     * This implementation is incomplete and has not tested.
     */
    @Override
    public boolean visit(TypeMethodReference node) {
        return methodReference(node, node.resolveTypeBinding());
    }
    
    private boolean methodReference(ASTNode node, ITypeBinding tbinding) {
        if (tbinding == null) {
            return false;
        }
        
        CFGStatement lambdaNode = new CFGStatement(node, CFGNode.Kind.lambda);
        entryNode = lambdaNode;
        
        dominantStatement.addImmediatePostDominator(lambdaNode);
        
        insertBeforeCurrentNode(lambdaNode);
        
        JVariableReference jv = new JUnsupportedReference(node,
                "$LAMBDA", tbinding.getErasure().getQualifiedName(), tbinding.isPrimitive());
        lambdaNode.setDefVariable(jv);
        curNode.setUseVariable(jv);
        return false;
    }
    
    /*
     * This implementation is incomplete and has not tested.
     */
    @Override
    public boolean visit(ExpressionMethodReference node) {
        ITypeBinding tbinding = node.resolveTypeBinding();
        if (tbinding == null) {
            return false;
        }
        
        CFGStatement lambdaNode = new CFGStatement(node, CFGNode.Kind.lambda);
        entryNode = lambdaNode;
        
        dominantStatement.addImmediatePostDominator(lambdaNode);
        
        insertBeforeCurrentNode(lambdaNode);
        
        CFGStatement tmpNode = curNode;
        curNode = lambdaNode;
        analysisMode.push(AnalysisMode.USE);
        node.getExpression().accept(this);
        analysisMode.pop();
        curNode = tmpNode;
        
        JVariableReference jv = new JUnsupportedReference(node,
                "$LAMBDA", tbinding.getErasure().getQualifiedName(), tbinding.isPrimitive());
        lambdaNode.setDefVariable(jv);
        curNode.setUseVariable(jv);
        return false;
    }
    
    /*
     * Switch expression is not supported under Java SE 11.
     * Thus, this method has not tested.
     */
    @Override
    @SuppressWarnings("unchecked")
    public boolean visit(SwitchExpression node) {
        CFGStatement switchExpNode = new CFGStatement(node, CFGNode.Kind.switchExpression);
        entryNode = switchExpNode;
        
        dominantStatement.addImmediatePostDominator(switchExpNode);
        
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
            JVariableReference def = new JUnsupportedReference(node,
                    "$SWITCH_EXPRESSION", type, tbinding.isPrimitive());
            switchExpNode.addDefVariable(def);
            curNode.addUseVariable(def);
        }
        return false;
    }
}
