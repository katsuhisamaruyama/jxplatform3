/*
 *     ASTPreDecrementExpression.java  Aug 28, 2001
 *
 *     Katsuhisa Maruyama (maru@fse.jrt.cs.ritsumei.ac.jp)
 */

/* Generated By:JJTree: Do not edit this line. ASTPreDecrementExpression.java */

package jp.ac.ritsumei.cs.fse.jrt.parser.ast;
import jp.ac.ritsumei.cs.fse.jrt.parser.*;
import jp.ac.ritsumei.cs.fse.jrt.model.JavaStatement;

public class ASTPreDecrementExpression extends SimpleNode {
    private JavaStatement jstatement = new JavaStatement(this);

    public ASTPreDecrementExpression(int id) {
        super(id);
    }

    public ASTPreDecrementExpression(JavaParser p, int id) {
        super(p, id);
    }

    public JavaStatement getJavaStatement() {
        return jstatement;
    }

    public Object jjtAccept(JavaParserVisitor visitor, Object data) {
        return visitor.visit(this, data);
    }
}
