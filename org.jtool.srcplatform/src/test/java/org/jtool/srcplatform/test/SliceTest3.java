package org.jtool.srcplatform.test;


import javassist.CtField;
import javassist.Modifier;
import javassist.NotFoundException;
import javassist.expr.ExprEditor;
import javassist.expr.FieldAccess;
import javassist.expr.MethodCall;
import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;

public class SliceTest3 {
    
    static void perform(CtClass cc) {
        
        System.out.println("A");
        
        try {
            cc.instrument(new ExprEditor() {
                
                @Override
                public void edit(FieldAccess cf) throws CannotCompileException {
                    if (cf.isWriter()) {
                        System.out.println("DEF = " + cf.getClassName() + "." + cf.getFieldName());
                    }
                    if (cf.isReader()) {
                        System.out.println("USE = " + cf.getClassName() + "." + cf.getFieldName());
                    }
                }
                
                @Override
                public void edit(MethodCall cm) throws CannotCompileException {
                    System.out.println("CALL = " + cm.getClassName() + "." + cm.getMethodName());
                }
            });
        } catch (CannotCompileException e) { /* empty */ }
    }
    
    public static void main(String[] args) {
        try {
            ClassPool cp = new ClassPool();
            cp.appendSystemPath();
            cp.appendClassPath("/Users/maru/git/jxplatform2/org.jtool.eclipse/src/test/java/org/jtool/eclipse/test");
            
            CtClass cc = cp.get("Test1");
            System.out.println("CLASS = " + cc.getName());
            perform(cc);
        } catch (NotFoundException e) {
            e.printStackTrace();
        }
        
    }
}
