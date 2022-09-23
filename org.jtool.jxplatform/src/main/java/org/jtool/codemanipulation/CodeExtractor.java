/*
 *  Copyright 2022
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.codemanipulation;

import org.jtool.pdg.PDGNode;
import org.jtool.pdg.PDGStatement;
import org.jtool.cfg.CFGMethodCall;
import org.jtool.cfg.CFGNode;
import org.jtool.cfg.JReference;
import org.jtool.srcmodel.JavaClass;
import org.jtool.srcmodel.JavaField;
import org.jtool.srcmodel.JavaFile;
import org.jtool.srcmodel.JavaMethod;
import org.jtool.srcmodel.JavaElementUtil;
import org.jtool.jxplatform.builder.ModelBuilder;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.Initializer;
import org.eclipse.jdt.core.dom.LambdaExpression;
import org.eclipse.jdt.core.dom.FieldDeclaration;
import org.eclipse.jdt.core.dom.EnumConstantDeclaration;
import org.eclipse.jdt.core.dom.VariableDeclarationStatement;
import org.eclipse.jdt.core.dom.VariableDeclarationExpression;
import org.eclipse.jdt.core.dom.SingleVariableDeclaration;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;
import org.eclipse.jdt.core.dom.Assignment;
import org.eclipse.jdt.core.dom.CatchClause;
import org.eclipse.jdt.core.dom.ClassInstanceCreation;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.core.dom.Block;
import org.eclipse.jdt.core.dom.BodyDeclaration;
import org.eclipse.jdt.core.dom.Statement;
import org.eclipse.jdt.core.dom.StructuralPropertyDescriptor;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.ConditionalExpression;
import org.eclipse.jdt.core.dom.ExpressionStatement;
import org.eclipse.jdt.core.dom.AssertStatement;
import org.eclipse.jdt.core.dom.BreakStatement;
import org.eclipse.jdt.core.dom.ConstructorInvocation;
import org.eclipse.jdt.core.dom.ContinueStatement;
import org.eclipse.jdt.core.dom.DoStatement;
import org.eclipse.jdt.core.dom.EnhancedForStatement;
import org.eclipse.jdt.core.dom.EnumDeclaration;
import org.eclipse.jdt.core.dom.EmptyStatement;
import org.eclipse.jdt.core.dom.ForStatement;
import org.eclipse.jdt.core.dom.IfStatement;
import org.eclipse.jdt.core.dom.ReturnStatement;
import org.eclipse.jdt.core.dom.SimpleName;
import org.eclipse.jdt.core.dom.SimpleType;
import org.eclipse.jdt.core.dom.SuperConstructorInvocation;
import org.eclipse.jdt.core.dom.SuperMethodInvocation;
import org.eclipse.jdt.core.dom.SwitchCase;
import org.eclipse.jdt.core.dom.SwitchStatement;
import org.eclipse.jdt.core.dom.SynchronizedStatement;
import org.eclipse.jdt.core.dom.ThrowStatement;
import org.eclipse.jdt.core.dom.TryStatement;
import org.eclipse.jdt.core.dom.TypeDeclarationStatement;
import org.eclipse.jdt.core.dom.WhileStatement;
import org.eclipse.jdt.core.dom.Type;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.eclipse.jdt.core.dom.ITypeBinding;
import org.eclipse.jdt.core.dom.IMethodBinding;
import java.util.Set;
import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Extracts a slice and returns Java source code corresponding to the slice.
 * 
 * @author Katsuhisa Maruyama
 */
public class CodeExtractor extends ASTVisitor {
    
    protected JavaClass jclass;
    protected Set<PDGNode> pdgNodes;
    
    protected ASTNode astNode = null;
    protected Set<ASTNode> targetNodes = new HashSet<>();
    
    public CodeExtractor(JavaClass jclass, Set<PDGNode> pdgNodes) {
        this.jclass = jclass;
        ModelBuilder builder = jclass.getJavaProject().getModelBuilder();
        
        JavaFile jfile = builder.copyJavaFile(jclass.getFile());
        for (JavaClass jc : jfile.getClasses()) {
            if (jc.getQualifiedName().equals(jclass.getQualifiedName())) {
                createCodeExtractor(pdgNodes, jfile, jc.getASTNode());
            }
        }
    }
    
    public CodeExtractor(JavaMethod jmethod, Set<PDGNode> pdgNodes) {
        this.jclass = jmethod.getDeclaringClass();
        ModelBuilder builder = jclass.getJavaProject().getModelBuilder();
        
        JavaFile jfile = builder.copyJavaFile(jmethod.getDeclaringClass().getFile());
        for (JavaClass jc : jfile.getClasses()) {
            if (jc.getQualifiedName().equals(jmethod.getDeclaringClass().getQualifiedName())) {
                for (JavaMethod jm : jc.getMethods()) {
                    if (jm.getQualifiedName().equals(jmethod.getQualifiedName())) {
                        createCodeExtractor(pdgNodes, jfile, jm.getASTNode());
                    }
                }
            }
        }
    }
    
    public CodeExtractor(JavaField jfield, Set<PDGNode> pdgNodes) {
        this.jclass = jfield.getDeclaringClass();
        ModelBuilder builder = jclass.getJavaProject().getModelBuilder();
        
        JavaFile jfile = builder.copyJavaFile(jfield.getDeclaringClass().getFile());
        for (JavaClass jc : jfile.getClasses()) {
            if (jc.getQualifiedName().equals(jfield.getDeclaringClass().getQualifiedName())) {
                for (JavaField jf : jc.getFields()) {
                    if (jf.getQualifiedName().equals(jfield.getQualifiedName())) {
                        createCodeExtractor(pdgNodes, jfile, jf.getASTNode().getParent());
                    }
                }
            }
        }
    }
    
    private void createCodeExtractor(Set<PDGNode> pdgNodes, JavaFile jfile, ASTNode astNode) {
        if(astNode == null) {
            return;
        }
        
        this.pdgNodes = pdgNodes;
        this.astNode = astNode;
        
        targetNodes.add(astNode);
        ASTNodeOnCFGCollector collector = new ASTNodeOnCFGCollector(jfile.getCompilationUnit());
        for (PDGNode pdgnode : pdgNodes) {
            registerASTNode(pdgnode.getCFGNode().getASTNode(), collector);
            
            if (pdgnode.isStatement()) {
                PDGStatement stnode = (PDGStatement)pdgnode;
                for (JReference var : stnode.getDefVariables()) {
                    if (!var.isVersatileReference()) {
                        registerASTNode(var.getASTNode(), collector);
                    }
                }
            }
        }
    }
    
    private void registerASTNode(ASTNode astNode, ASTNodeOnCFGCollector collector) {
        ASTNode correspondingNode = collector.get(astNode);
        if (correspondingNode != null) {
            targetNodes.add(correspondingNode);
        }
    }
    
    protected Set<ASTNode> getTargetNodes() {
        return targetNodes;
    }
    
    public ASTNode extractAST() {
        if (astNode != null) {
            astNode.accept(this);
        }
        return astNode;
    }
    
    public String extract() {
        return extract(null);
    }
    
    public String extract(Map<String, String> options) {
        astNode.accept(this);
        
        CodeGenerator codeGenerator = new CodeGenerator();
        codeGenerator.setOptions(options);
        
        String code = codeGenerator.generate(astNode, jclass.getFile().getSource(), targetNodes);
        return code;
    }
    
    protected PDGNode getPDGNode(ASTNode node) {
        for (PDGNode pdgnode : pdgNodes) {
            if (hasConcreteASTNode(pdgnode) && equalsASTNode(node, pdgnode.getCFGNode().getASTNode())) {
                return pdgnode;
            }
        }
        return null;
    }
    
    protected boolean hasConcreteASTNode(PDGNode pdgnode) {
        return !pdgnode.getCFGNode().isReceiver() &&
               !pdgnode.getCFGNode().isActualOut() &&
               !pdgnode.getCFGNode().isFormalOut() &&
               !pdgnode.getCFGNode().isDummy() &&
               !pdgnode.getCFGNode().isMerge();
    }
    
    protected boolean equalsASTNode(ASTNode node1, ASTNode node2) {
        return node1 != null && node2 != null &&
               node1.getStartPosition() == node2.getStartPosition() &&
               node1.getLength() == node2.getLength();
    }
    
    protected boolean contains(ASTNode node) {
        if (node == null) {
            return false;
        }
        
        for (ASTNode n : targetNodes) {
            if (equalsASTNode(node, n)) {
                return true;
            }
        }
        return false;
    }
    
    protected boolean containsAnyInSubTree(ASTNode node) {
        if (node == null) {
            return false;
        }
        
        ASTNodeOnCFGCollector collector = new ASTNodeOnCFGCollector(node);
        for (ASTNode n : collector.getNodeSet()) {
            if (contains(n)) {
                return true;
            }
        }
        return false;
    }
    
    protected boolean removeWholeElement(ASTNode node) {
        if (node == null) {
            return true;
        }
        
        if (!containsAnyInSubTree(node)) {
            node.delete();
            return true;
        } else {
            return false;
        }
    }
    
    @Override
    @SuppressWarnings("unchecked")
    public boolean visit(MethodDeclaration node) {
        if (removeWholeElement(node)) {
            return false;
        }
        
        if (node.getJavadoc() != null) {
            node.getJavadoc().accept(this);
        }
        
        ITypeBinding[] bindings = node.resolveBinding().getParameterTypes();
        StringBuilder buf = new StringBuilder();
        buf.append(node.getName().getIdentifier());
        buf.append("(");
        Set<SingleVariableDeclaration> removeNodes = new HashSet<>();
        Set<SingleVariableDeclaration> preserveNodes = new HashSet<>();
        int index = 0;
        for (SingleVariableDeclaration param : (List<SingleVariableDeclaration>)node.parameters()) {
            if (containsAnyInSubTree(param)) {
                buf.append(" ");
                if (bindings[index].isTypeVariable()) {
                    buf.append("java.lang.Object");
                } else {
                    buf.append(bindings[index].getQualifiedName());
                }
                preserveNodes.add(param);
            } else { 
                removeNodes.add(param);
            }
            index++;
        }
        buf.append(" )");
        
        if (jclass.getMethod(buf.toString()) != null) {
            preserveNodes.addAll((List<SingleVariableDeclaration>)node.parameters());
            removeNodes.clear();
        }
        
        for (SingleVariableDeclaration param : preserveNodes) {
            param.accept(this);
        }
        for (SingleVariableDeclaration param : removeNodes) {
            param.delete();
        }
        
        node.getBody().accept(this);
        return false;
    }
    
    @Override
    public boolean visit(Initializer node) {
        if (removeWholeElement(node)) {
            return false;
        }
        return true;
    }
    
    @Override
    public boolean visit(LambdaExpression node) {
        if (removeWholeElement(node)) {
            return false;
        }
        return true;
    }
    
    @Override
    public boolean visit(ConditionalExpression node) {
        if (removeWholeElement(node)) {
            return false;
        }
        
        Expression thenExpr = node.getThenExpression();
        if (!containsAnyInSubTree(thenExpr)) {
            ITypeBinding tbinding = thenExpr.resolveTypeBinding();
            Expression dummyExpr = getDummyTypeExpression(thenExpr, tbinding);
            node.setThenExpression(dummyExpr);
        }
        
        Expression elseExpr = node.getElseExpression();
        if (!containsAnyInSubTree(elseExpr)) {
            ITypeBinding tbinding = elseExpr.resolveTypeBinding();
            Expression dummyExpr = getDummyTypeExpression(elseExpr, tbinding);
            node.setElseExpression(dummyExpr);
        }
        return true;
    }
    
    @Override
    @SuppressWarnings("unchecked")
    public boolean visit(FieldDeclaration node) {
        if (removeWholeElement(node)) {
            return false;
        }
        
        if (node.getJavadoc() != null) {
            node.getJavadoc().accept(this);
        }
        node.getType().accept(this);
        for (VariableDeclarationFragment frag : (List<VariableDeclarationFragment>)node.fragments()) {
            frag.accept(this);
        }
        
        checkDeclaration(node, node.fragments());
        return false;
    }
    
    @Override
    public boolean visit(EnumConstantDeclaration node) {
        if (removeWholeElement(node)) {
            return false;
        }
        return true;
    }
    
    @Override
    @SuppressWarnings("unchecked")
    public boolean visit(VariableDeclarationStatement node) {
        if (removeWholeElement(node)) {
            return false;
        }
        
        node.getType().accept(this);
        for (VariableDeclarationFragment frag : (List<VariableDeclarationFragment>)node.fragments()) {
            frag.accept(this);
        }
        
        checkDeclaration(node, node.fragments());
        return false;
    }
    
    @Override
    @SuppressWarnings("unchecked")
    public boolean visit(VariableDeclarationExpression node) {
        if (removeWholeElement(node)) {
            return false;
        }
        
        node.getType().accept(this);
        for (VariableDeclarationFragment frag : (List<VariableDeclarationFragment>)node.fragments()) {
            frag.accept(this);
        }
        
        checkDeclaration(node, node.fragments());
        return false;
    }
    
    protected void checkDeclaration(ASTNode node, List<VariableDeclarationFragment> fragments) {
        List<VariableDeclarationFragment> removeNodes = new ArrayList<>();
        for (VariableDeclarationFragment frag : fragments) {
            if (!containsAnyInSubTree(frag)) {
                removeNodes.add(frag);
            } else if (!contains(frag) && fragments.size() == 1) {
                pullUpExpressionInVariableDeclaration(frag, frag.getInitializer());
            }
        }
        for (VariableDeclarationFragment n : removeNodes) {
            n.delete();
        }
    }
    
    @SuppressWarnings("unchecked")
    protected void pullUpExpressionInVariableDeclaration(ASTNode astnode, Expression expr) {
        if (expr != null && containsAnyInSubTree(expr)) {
            
            ASTNode enclosingStatement = getEnclosingStatement(astnode.getParent());
            if (enclosingStatement == null) {
                return;
            }
            ASTNode parent = enclosingStatement.getParent();
            
            if (parent instanceof Block) {
                Expression newExpression = (Expression)ASTNode.copySubtree(expr.getAST(), expr);
                ExpressionStatement newStatement = (ExpressionStatement)expr.getAST()
                        .newExpressionStatement(newExpression);
                
                Block block = (Block)parent;
                for (int index = 0; index < block.statements().size(); index++) {
                    Statement target = (Statement)block.statements().get(index);
                    if (target.getStartPosition() == astnode.getParent().getStartPosition() &&
                            target.getLength() == astnode.getParent().getLength()) {
                        block.statements().add(index, newStatement);
                        target.delete();
                        return;
                    }
                }
            }
        }
    }
    
    protected void pullUpMethodInvocations(Statement statement, Expression expr) {
        List<Expression> exprs = new ArrayList<>();
        exprs.add(expr);
        pullUpMethodInvocations(statement, exprs);
    }
    
    @SuppressWarnings("unchecked")
    protected void pullUpMethodInvocations(Statement statement, List<Expression> exprs) {
        List<MethodInvocation> invocations = new ArrayList<>();
        for (Expression expr : exprs) {
            MethodInvocationCollector collector = new MethodInvocationCollector(expr);
            for (ASTNode n : collector.getNodes()) {
                if (n instanceof MethodInvocation && contains(n)) {
                    invocations.add((MethodInvocation)n);
                }
            }
        }
        
        if (invocations.size() == 1) {
            MethodInvocation oldInvocation = invocations.get(0);
            MethodInvocation newInvocation = (MethodInvocation)ASTNode
                    .copySubtree(statement.getAST(), oldInvocation);
            ExpressionStatement newStatement = (ExpressionStatement)statement.getAST()
                    .newExpressionStatement(newInvocation);
            
            replaceStatementWithStatement(statement, newStatement);
            
        } else if (invocations.size() > 1) {
            Block newBlock = (Block)statement.getAST().newBlock();
            for (int index = 0; index < invocations.size(); index++) {
                MethodInvocation oldInvocation = invocations.get(index);
                MethodInvocation newInvocation = (MethodInvocation)ASTNode
                        .copySubtree(statement.getAST(), oldInvocation);
                ExpressionStatement newStatement = (ExpressionStatement)statement.getAST()
                        .newExpressionStatement(newInvocation);
                newBlock.statements().add(newStatement);
            }
            
            replaceStatementWithBlock(statement, newBlock);
        }
        return;
    }
    
    @SuppressWarnings("unchecked")
    protected void replaceStatementWithStatement(Statement statement, Statement newStatement) {
        StructuralPropertyDescriptor location = statement.getLocationInParent();
        if (location != null) {
            if (location.isChildProperty()) {
                statement.getParent().setStructuralProperty(location, newStatement);
            } else if (location.isChildListProperty()) {
                List<ASTNode> property = (List<ASTNode>)statement.getParent().getStructuralProperty(location);
                property.set(property.indexOf(statement), newStatement);
            }
        }
    }
    
    @SuppressWarnings("unchecked")
    protected void replaceStatementWithBlock(Statement statement, Block block) {
        StructuralPropertyDescriptor location = statement.getLocationInParent();
        if (location != null) {
            if (location.isChildProperty()) {
                statement.getParent().setStructuralProperty(location, block);
            } else if (location.isChildListProperty()) {
                List<ASTNode> property = (List<ASTNode>)statement.getParent().getStructuralProperty(location);
                int index = property.indexOf(statement);
                for (Statement st : (List<Statement>)block.statements()) {
                    Statement newStatement = (Statement)ASTNode.copySubtree(st.getAST(), st);
                    property.add(index, newStatement);
                    index++;
                }
                statement.delete();
            }
        }
    }
    
    @Override
    public boolean visit(Assignment node) {
        if (removeWholeElement(node)) {
            return false;
        }
        if (contains(node)) {
            return true;
        }
        
        Statement statement = getEnclosingStatement(node);
        Expression expr = node.getRightHandSide();
        if (containsAnyInSubTree(expr)) {
            if (statement instanceof ExpressionStatement) {
                Expression newExpression = (Expression)ASTNode.copySubtree(expr.getAST(), expr);
                
                ExpressionStatement parentExpression = (ExpressionStatement)statement;
                parentExpression.setExpression(newExpression);
            }
        } else {
            return false;
        }
        return true;
    }
    
    protected Statement getEnclosingStatement(ASTNode node) {
        while (node != null) {
            if (node instanceof Statement) {
                return (Statement)node;
            }
            node = node.getParent();
        }
        return null;
    }
    
    protected String findEnclosingClass(ASTNode node) {
        TypeDeclaration tnode = (TypeDeclaration)JavaElementUtil.findAncestorNode(node, ASTNode.TYPE_DECLARATION);
        if (tnode != null) {
            return tnode.resolveBinding().getQualifiedName();
        }
        
        EnumDeclaration enode = (EnumDeclaration)JavaElementUtil.findAncestorNode(node, ASTNode.ENUM_DECLARATION);
        if (enode != null) {
            return enode.resolveBinding().getQualifiedName();
        }
        return null;
    }
    
    @Override
    @SuppressWarnings("unchecked")
    public boolean visit(MethodInvocation node) {
        Statement statement = getEnclosingStatement(node);
        if (removeWholeElement(statement)) {
            return false;
        }
        
        if (contains(node)) {
            if (node.resolveMethodBinding() != null) {
                if (!removeMethodCallArgument(node)) {
                    replaceMethodCallArgumentWithDummy((List<Expression>)node.arguments(), getPDGNode(node));
                }
            }
            return true;
        } else {
            visit(node, statement);
            return false;
        }
    }
    
    @SuppressWarnings("unchecked")
    protected boolean removeMethodCallArgument(MethodInvocation node) {
        String declaringClassName = node.resolveMethodBinding().getDeclaringClass().getQualifiedName();
        String enclosingClassName = findEnclosingClass(node);
        if (declaringClassName.equals(enclosingClassName)) {
            removeMethodCallArgument(node.getName().getIdentifier(), (List<Expression>)node.arguments());
            return true;
        }
        return false;
    }
    
    protected void removeMethodCallArgument(String name, List<Expression> arguments) {
        StringBuilder buf = new StringBuilder();
        buf.append(name);
        buf.append("(");
        Set<Expression> removeNodes = new HashSet<>();
        Set<Expression> preserveNodes = new HashSet<>();
        for (Expression arg : arguments) {
            if (containsAnyInSubTree(arg)) {
                buf.append(" ");
                ITypeBinding tbinding = arg.resolveTypeBinding();
                if (tbinding.isTypeVariable()) {
                    buf.append("java.lang.Object");
                } else {
                    buf.append(tbinding.getQualifiedName());
                }
                preserveNodes.add(arg);
            } else {
                removeNodes.add(arg);
            }
        }
        buf.append(" )");
        
        if (jclass.getMethod(buf.toString()) != null) {
            preserveNodes.addAll(arguments);
            removeNodes.clear();
        }
        
        for (Expression arg : preserveNodes) {
            arg.accept(this);
        }
        for (Expression arg : removeNodes) {
            arg.delete();
        }
    }
    
    protected void replaceMethodCallArgumentWithDummy(List<Expression> arguments, PDGNode pdgnode) {
        if (pdgnode == null || !pdgnode.getCFGNode().isMethodCall()) {
            return;
        }
        
        CFGMethodCall callNode = (CFGMethodCall)pdgnode.getCFGNode();
        if (callNode.getActualIns().size() == 0) {
            return;
        }
        
        for (int index = 0; index < arguments.size(); index++) {
            Expression expr = (Expression)arguments.get(index);
            
            ITypeBinding tbinding = expr.resolveTypeBinding();
            if (tbinding != null && !containsAnyInSubTree(expr)) {
                Expression dummyExpr = getDummyTypeExpression(expr, tbinding);
                arguments.remove(index);
                arguments.add(index, dummyExpr);
            }
        }
    }
    
    @SuppressWarnings("unchecked")
    protected void visit(MethodInvocation node, Statement statement) {
        if (node.getName() != null) {
            node.getName().accept(this);
        }
        if (node.getExpression() != null) {
            node.getExpression().accept(this);
        }
        for (Type type : (List<Type>)node.typeArguments()) {
            type.accept(this);
        }
        
        for (int index = 0; index < node.arguments().size(); index++) {
            ((Expression)node.arguments().get(index)).accept(this);
        }
        
        pullUpMethodInvocations(statement, (List<Expression>)node.arguments());
    }
    
    @Override
    @SuppressWarnings("unchecked")
    public boolean visit(SuperMethodInvocation node) {
        Statement statement = getEnclosingStatement(node);
        if (removeWholeElement(statement)) {
            return false;
        }
        
        if (contains(node)) {
            replaceMethodCallArgumentWithDummy((List<Expression>)node.arguments(), getPDGNode(node));
            return true;
        } else {
            visit(node, statement);
            return false;
        }
    }
    
    @SuppressWarnings("unchecked")
    protected void visit(SuperMethodInvocation node, Statement statement) {
        if (node.getName() != null) {
            node.getName().accept(this);
        }
        if (node.getQualifier() != null) {
            node.getQualifier().accept(this);
        }
        for (Type type : (List<Type>)node.typeArguments()) {
            type.accept(this);
        }
        
        for (int index = 0; index < node.arguments().size(); index++) {
            ((Expression)node.arguments().get(index)).accept(this);
        }
        
        pullUpMethodInvocations(statement, (List<Expression>)node.arguments());
    }
    
    @Override
    @SuppressWarnings("unchecked")
    public boolean visit(ClassInstanceCreation node) {
        Statement statement = getEnclosingStatement(node);
        if (removeWholeElement(statement)) {
            return false;
        }
        
        if (contains(node)) {
            replaceMethodCallArgumentWithDummy((List<Expression>)node.arguments(), getPDGNode(node));
            return true;
        } else {
            visit(node, statement);
            return false;
        }
    }
    
    @SuppressWarnings("unchecked")
    protected void visit(ClassInstanceCreation node, Statement statement) {
        if (node.getType() != null) {
            node.getType().accept(this);
        }
        if (node.getExpression() != null) {
            node.getExpression().accept(this);
        }
        for (Type type : (List<Type>)node.typeArguments()) {
            type.accept(this);
        }
        
        for (int index = 0; index < node.arguments().size(); index++) {
            ((Expression)node.arguments().get(index)).accept(this);
        }
        
        pullUpMethodInvocations(statement, (List<Expression>)node.arguments());
    }
    
    @Override
    @SuppressWarnings("unchecked")
    public boolean visit(ConstructorInvocation node) {
        if (removeWholeElement(node)) {
            return false;
        }
        
        replaceMethodCallArgumentWithDummy((List<Expression>)node.arguments(), getPDGNode(node));
        return true;
    }
    
    @Override
    @SuppressWarnings("unchecked")
    public boolean visit(SuperConstructorInvocation node) {
        if (removeWholeElement(node)) {
            return false;
        }
        
        replaceMethodCallArgumentWithDummy((List<Expression>)node.arguments(), getPDGNode(node));
        return true;
    }
    
    @Override
    public boolean visit(AssertStatement node) {
        if (removeWholeElement(node)) {
            return false;
        }
        
        addEnclosedAssignments(node.getExpression());
        return true;
    }
    
    @Override
    public boolean visit(BreakStatement node) {
        if (removeWholeElement(node.getParent())) {
            return false;
        }
        return true;
    }
    
    @SuppressWarnings("unchecked")
    private void checkBreakStatement(Statement node) {
        if (node instanceof Block) {
            Block block = (Block)node;
            for (Statement statement : (List<Statement>)block.statements()) {
                if (statement instanceof BreakStatement) {
                    checkBreakStatement((BreakStatement)statement);
                }
            }
        } else if (node instanceof BreakStatement) {
            checkBreakStatement((BreakStatement)node);
        }
    }
    
    private void checkBreakStatement(BreakStatement statement) {
        if (statement instanceof BreakStatement && !targetNodes.contains(statement)) {
            targetNodes.add(statement);
        }
    }
    
    @Override
    public boolean visit(ContinueStatement node) {
        if (removeWholeElement(node.getParent())) {
            return false;
        }
        return true;
    }
    
    @SuppressWarnings("unchecked")
    private void checkContinueStatement(Statement node) {
        if (node instanceof Block) {
            Block block = (Block)node;
            for (Statement statement : (List<Statement>)block.statements()) {
                if (statement instanceof ContinueStatement) {
                    checkContinueStatement((ContinueStatement)statement);
                }
            }
        } else if (node instanceof ContinueStatement) {
            checkContinueStatement((ContinueStatement)node);
        }
    }
    
    private void checkContinueStatement(ContinueStatement statement) {
        if (statement instanceof ContinueStatement && !targetNodes.contains(statement)) {
            targetNodes.add(statement);
        }
    }
    
    @Override
    public boolean visit(DoStatement node) {
        if (removeWholeElement(node)) {
            return false;
        }
        
        addEnclosedAssignments(node.getExpression());
        
        if (contains(node)) {
            checkReturnStatement(node.getBody());
            checkThrowStatement(node.getBody());
            checkBreakStatement(node.getBody());
            checkContinueStatement(node.getBody());
        }
        return true;
    }
    
    @Override
    public boolean visit(EmptyStatement node) {
        if (removeWholeElement(node)) {
            return false;
        }
        return true;
    }
    
    @Override
    public boolean visit(EnhancedForStatement node) {
        if (removeWholeElement(node)) {
            return false;
        }
        
        if (contains(node)) {
            checkReturnStatement(node.getBody());
            checkThrowStatement(node.getBody());
            checkBreakStatement(node.getBody());
            checkContinueStatement(node.getBody());
        }
        
        return true;
    }
    
    @Override
    public boolean visit(ExpressionStatement node) {
        if (removeWholeElement(node)) {
            return false;
        }
        return true;
    }
    
    @Override
    @SuppressWarnings("unchecked")
    public boolean visit(ForStatement node) {
        if (removeWholeElement(node)) {
            return false;
        }
        
        if (node.getExpression() != null) {
            addEnclosedAssignments(node.getExpression());
        }
        
        if (contains(node)) {
            checkReturnStatement(node.getBody());
            checkThrowStatement(node.getBody());
            checkBreakStatement(node.getBody());
            checkContinueStatement(node.getBody());
        }
        
        List<Expression> removeNodes = new ArrayList<>();
        for (Expression expr : (List<Expression>)node.updaters()) {
            if (!contains(expr)) {
                removeNodes.add(expr);
            }
        }
        for (Expression n : removeNodes) {
            n.delete();
        }
        
        return true;
    }
    
    @Override
    
    public boolean visit(IfStatement node) {
        if (removeWholeElement(node)) {
            return false;
        }
        
        addEnclosedAssignments(node.getExpression());
        
        if (contains(node)) {
            checkReturnStatement(node.getThenStatement());
            checkReturnStatement(node.getElseStatement());
            checkThrowStatement(node.getThenStatement());
            checkThrowStatement(node.getElseStatement());
            checkBreakStatement(node.getThenStatement());
            checkBreakStatement(node.getElseStatement());
            checkContinueStatement(node.getThenStatement());
            checkContinueStatement(node.getElseStatement());
        }
        
        if (node.getElseStatement()!= null && !containsAnyInSubTree(node.getElseStatement())) {
            node.getElseStatement().delete();
        }
        
        if (!(node.getThenStatement() instanceof Block) && !containsAnyInSubTree(node.getThenStatement())) {
            EmptyStatement empty = node.getAST().newEmptyStatement();
            node.setThenStatement(empty);
            targetNodes.add(empty);
        }
        return true;
    }
    
    @Override
    public boolean visit(SwitchCase node) {
        return true;
    }
    
    @Override
    @SuppressWarnings("unchecked")
    public boolean visit(SwitchStatement node) {
        if (removeWholeElement(node)) {
            return false;
        }
        
        addEnclosedAssignments(node.getExpression());
        
        if (contains(node)) {
            for (Statement statement : (List<Statement>)node.statements()) {
                if (statement instanceof ReturnStatement) {
                    checkReturnStatement((ReturnStatement)statement);
                    checkThrowStatement((ThrowStatement)statement);
                    checkBreakStatement((BreakStatement)statement);
                    checkContinueStatement((ContinueStatement)statement);
                }
            }
        }
        
        return true;
    }
    
    @Override
    public boolean visit(TypeDeclarationStatement node) {
        if (removeWholeElement(node)) {
            return false;
        }
        return true;
    }
    
    @Override
    public boolean visit(WhileStatement node) {
        if (removeWholeElement(node)) {
            return false;
        }
        
        addEnclosedAssignments(node.getExpression());
        
        if (contains(node)) {
            checkReturnStatement(node.getBody());
            checkThrowStatement(node.getBody());
            checkBreakStatement(node.getBody());
            checkContinueStatement(node.getBody());
        }
        
        return true;
    }
    
    protected MethodDeclaration getEnclosingMethod(ASTNode node) {
        while (node != null) {
            if (node instanceof MethodDeclaration) {
                return (MethodDeclaration)node;
            }
            node = node.getParent();
        }
        return null;
    }
    
    @Override
    public boolean visit(ReturnStatement node) {
        if (contains(node)) {
            return true;
        }
        
        if (!contains(getEnclosingStatement(node.getParent())) && !contains(getEnclosingMethod(node.getParent()))) {
            node.delete();
            return false;
        }
        
        pullUpMethodInvocationInReturn(node);
        return true;
    }
    
    @SuppressWarnings("unchecked")
    protected void pullUpMethodInvocationInReturn(ReturnStatement statement) {
        Expression returnExpression = getDummyReturnExpression(statement);
        
        Expression expr = statement.getExpression();
        if (!containsAnyInSubTree(expr)) {
            statement.setExpression(returnExpression);
            return;
        }
        
        List<MethodInvocation> invocations = new ArrayList<>();
        MethodInvocationCollector collector = new MethodInvocationCollector(expr);
        for (ASTNode n : collector.getNodes()) {
            if (n instanceof MethodInvocation && contains(n)) {
                invocations.add((MethodInvocation)n);
            }
        }
        
        if (invocations.size() > 0) {
            Block block = (Block)statement.getAST().newBlock();
            for (int index = 0; index < invocations.size(); index++) {
                MethodInvocation oldInvocation = invocations.get(index);
                MethodInvocation newInvocation = (MethodInvocation)ASTNode
                        .copySubtree(statement.getAST(), oldInvocation);
                ExpressionStatement newStatement = (ExpressionStatement)statement.getAST()
                        .newExpressionStatement(newInvocation);
                block.statements().add(newStatement);
            }
            
            ReturnStatement newStatement = (ReturnStatement)ASTNode.copySubtree(statement.getAST(), statement);
            newStatement.setExpression(returnExpression);
            block.statements().add(newStatement);
            
            replaceStatementWithBlock(statement, block);
            
        } else {
            statement.setExpression(returnExpression);
        }
    }
    
    protected Expression getDummyReturnExpression(ReturnStatement node) {
        MethodDeclaration methodNode = getEnclosingMethod(node);
        if (methodNode == null) {
            return null;
        }
        
        IMethodBinding mbinding = methodNode.resolveBinding();
        if (mbinding == null) {
            return null;
        }
        
        return getDummyTypeExpression(node, mbinding.getReturnType());
    }
    
    private Expression getDummyTypeExpression(ASTNode node, ITypeBinding tbinding) {
        Expression newExpression;
        if (tbinding.isPrimitive()) {
            if (tbinding.getName().equals("boolean")) {
                newExpression = node.getAST().newBooleanLiteral(false);
            } else if (tbinding.getName().equals("char")) {
                newExpression = node.getAST().newCharacterLiteral();
            } else if (tbinding.getName().equals("void")) {
                newExpression = null;
            } else {
                newExpression = node.getAST().newNumberLiteral();
            }
        } else {
            newExpression = node.getAST().newNullLiteral();
        }
        return newExpression;
    }
    
    private void addEnclosedAssignments(Expression node) {
        ASTNodeOnCFGCollector collector = new ASTNodeOnCFGCollector(node);
        for (ASTNode n : collector.getNodeSet()) {
            if (n instanceof Assignment) {
                targetNodes.add(n);
            }
        }
    }
    
    @SuppressWarnings("unchecked")
    private void checkReturnStatement(Statement node) {
        if (node instanceof Block) {
            Block block = (Block)node;
            for (Statement statement : (List<Statement>)block.statements()) {
                if (statement instanceof ReturnStatement) {
                    checkReturnStatement((ReturnStatement)statement);
                }
            }
        } else if (node instanceof ReturnStatement) {
            checkReturnStatement((ReturnStatement)node);
        }
    }
    
    private void checkReturnStatement(ReturnStatement statement) {
        if (statement instanceof ReturnStatement && !targetNodes.contains(statement)) {
            Expression returnExpression = getDummyReturnExpression(statement);
            statement.setExpression(returnExpression);
            
            targetNodes.add(statement);
        }
    }
    
    @Override
    public boolean visit(SynchronizedStatement node) {
        if (removeWholeElement(node)) {
            return false;
        }
        return true;
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public boolean visit(TryStatement node) {
        if (removeWholeElement(node)) {
            return false;
        }
        
        List<CatchClause> catchClauses = new ArrayList<CatchClause>(node.catchClauses());
        for (CatchClause catchClause : catchClauses) {
            if (removeWholeElement(catchClause)) {
                catchClause.delete();
            }
        }
        
        Block finallyBlock = node.getFinally();
        if (removeWholeElement(finallyBlock)) {
            finallyBlock.delete();
        }
        
        return true;
    }
    
    @Override
    public void endVisit(TryStatement node) {
        if (node.catchClauses().size() == 0 && !containsAnyInSubTree(node.getFinally())) {
            replaceStatementWithBlock(node, node.getBody());
        }
    }
    
    @Override
    public boolean visit(ThrowStatement node) {
        if (removeWholeElement(node)) {
            return false;
        }
        return true;
    }
    
    @SuppressWarnings("unchecked")
    private void checkThrowStatement(Statement node) {
        if (node instanceof Block) {
            Block block = (Block)node;
            for (Statement statement : (List<Statement>)block.statements()) {
                if (statement instanceof ThrowStatement) {
                    checkThrowStatement((ThrowStatement)statement);
                }
            }
        } else if (node instanceof ThrowStatement) {
            checkThrowStatement((ThrowStatement)node);
        }
    }
    
    private void checkThrowStatement(ThrowStatement statement) {
        if (statement instanceof ThrowStatement && !targetNodes.contains(statement)) {
            Expression throwExpression = getDummyThrowExpression(statement);
            statement.setExpression(throwExpression);
            
            targetNodes.add(statement);
        }
    }
    
    protected Expression getDummyThrowExpression(ThrowStatement statement) {
        ClassInstanceCreation instanceCreation = statement.getAST().newClassInstanceCreation();
        SimpleName name = statement.getAST().newSimpleName("Throwable");
        SimpleType type = statement.getAST().newSimpleType(name);
        instanceCreation.setType(type);
        return instanceCreation;
    }
    
    /**
     * Collect AST nodes corresponding to method invocations.
     * 
     * @author Katsuhisa Maruyama
     */
    private class MethodInvocationCollector extends ASTVisitor {
        
        private List<MethodInvocation> nodes = new ArrayList<>();
        
        public MethodInvocationCollector(ASTNode node) {
            node.accept(this);
        }
        
        public List<MethodInvocation> getNodes() {
            return nodes;
        }
        
        @Override
        public boolean visit(MethodInvocation node) {
            nodes.add(node);
            return true;
        }
    }
    
    /**
     * Collects AST nodes appearing on a CFG.
     * 
     * @author Katsuhisa Maruyama
     */
    private class ASTNodeOnCFGCollector extends ASTVisitor {
        
        private Map<String, ASTNode> nodeMap = new HashMap<>();
        
        public ASTNodeOnCFGCollector(ASTNode node) {
            node.accept(this);
        }
        
        public Set<ASTNode> getNodeSet() {
            return new HashSet<ASTNode>(nodeMap.values());
        }
        
        public ASTNode get(ASTNode node) {
            if (node == null) {
                return null;
            }
            return nodeMap.get(key(node));
        }
        
        @Override
        public void preVisit(ASTNode node) {
            if (isCFGNode(node)) {
                nodeMap.put(key(node), node);
            }
        }
        
        private String key(ASTNode node) {
            return String.valueOf(node.getStartPosition() + "-" + node.getLength());
        }
        
        private boolean isCFGNode(ASTNode node) {
            return node instanceof BodyDeclaration ||
                   CFGNode.isStatement(node) || CFGNode.isExpression(node) || CFGNode.isLiteral(node);
        }
    }
}
