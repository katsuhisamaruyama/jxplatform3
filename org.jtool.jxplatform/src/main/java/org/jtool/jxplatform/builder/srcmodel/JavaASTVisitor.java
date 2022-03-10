/*
 *  Copyright 2020
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.jxplatform.builder.srcmodel;

import org.jtool.srcmodel.JavaClass;
import org.jtool.srcmodel.JavaField;
import org.jtool.srcmodel.JavaFile;
import org.jtool.srcmodel.JavaMethod;
import org.jtool.srcmodel.JavaPackage;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.PackageDeclaration;
import org.eclipse.jdt.core.dom.ImportDeclaration;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.eclipse.jdt.core.dom.AnonymousClassDeclaration;
import org.eclipse.jdt.core.dom.EnumDeclaration;
import org.eclipse.jdt.core.dom.EnumConstantDeclaration;
import org.eclipse.jdt.core.dom.AnnotationTypeDeclaration;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.Initializer;
import org.eclipse.jdt.core.dom.FieldDeclaration;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;
import java.util.List;
import java.util.Stack;

/**
 * Visits Java source code and stores its information.
 * 
 * @author Katsuhisa Maruyama
 */
public class JavaASTVisitor extends ASTVisitor {
    
    protected JavaFile jfile;
    private Stack<JavaClass> outerClasses = new Stack<JavaClass>();
    private JavaPackage jpackage = null;
    
    public JavaASTVisitor(JavaFile jfile) {
        this.jfile = jfile;
    }
    
    public void terminate() {
        jfile = null;
        outerClasses.clear();
        jpackage = null;
    }
    
    @Override
    public boolean visit(PackageDeclaration node) {
        jpackage = JavaPackage.create(node, jfile);
        return true;
    }
    
    @Override
    public boolean visit(ImportDeclaration node) {
        jfile.addImport(node);
        return true;
    }
    
    @Override
    public boolean visit(TypeDeclaration node) {
        JavaClass jclass = new JavaClass(node, jfile);
        createClass(jclass);
        return true;
    }
    
    @Override
    public void endVisit(TypeDeclaration node) {
        endVisitClass();
    }
    
    @Override
    public boolean visit(AnonymousClassDeclaration node) {
        JavaClass jclass = new JavaClass(node, jfile);
        createClass(jclass);
        return true;
    }
    
    public void endVisit(AnonymousClassDeclaration node) {
        endVisitClass();
    }
    
    @Override
    public boolean visit(EnumDeclaration node) {
        JavaClass jclass = new JavaClass(node, jfile);
        createClass(jclass);
        return true;
    }
    
    @Override
    public void endVisit(EnumDeclaration node) {
        endVisitClass();
    }
    
    @Override
    public boolean visit(AnnotationTypeDeclaration node) {
        JavaClass jclass = new JavaClass(node, jfile);
        createClass(jclass);
        return true;
    }
    
    @Override
    public void endVisit(AnnotationTypeDeclaration node) {
        endVisitClass();
    }
    
    private void createClass(JavaClass jclass) {
        if (!outerClasses.empty()) {
            JavaClass jc = outerClasses.peek();
            jc.addInnerClass(jclass);
        }
        outerClasses.push(jclass);
    }
    
    private void endVisitClass() {
        outerClasses.pop();
    }
    
    @Override
    public boolean visit(MethodDeclaration node) {
        if (outerClasses.empty()) {
            return false;
        }
        
        JavaClass jclass = outerClasses.peek();
        new JavaMethod(node, jclass);
        return true;
    }
    
    @Override
    public boolean visit(Initializer node) {
        if (outerClasses.empty()) {
            return false;
        }
        
        JavaClass jclass = outerClasses.peek();
        new JavaMethod(node, jclass);
        return true;
    }
    
    @Override
    public boolean visit(FieldDeclaration node) {
        if (outerClasses.empty()) {
            return false;
        }
        
        JavaClass jclass = outerClasses.peek();
        @SuppressWarnings("unchecked")
        List<VariableDeclarationFragment> fields = (List<VariableDeclarationFragment>)node.fragments();
        for (VariableDeclarationFragment fragment : fields) {
            new JavaField(fragment, jclass);
        }
        return false;
    }
    
    @Override
    public boolean visit(EnumConstantDeclaration node) {
        if (outerClasses.empty()) {
            return false;
        }
        
        JavaClass jclass = outerClasses.peek();
        new JavaField(node, jclass);
        return true;
    }
}
