/*
 *     ASTThrowStatement.java  Aug 31, 2001
 *
 *     Katsuhisa Maruyama (maru@fse.jrt.cs.ritsumei.ac.jp)
 */

/* Generated By:JJTree: Do not edit this line. ASTThrowStatement.java */

package jp.ac.ritsumei.cs.fse.jrt.parser.ast;
import jp.ac.ritsumei.cs.fse.jrt.parser.*;
import jp.ac.ritsumei.cs.fse.jrt.model.JavaStatement;

public class ASTThrowStatement extends SimpleNode {
    private JavaStatement jstatement = new JavaStatement(this);
 
    public ASTThrowStatement(int id) {
        super(id);
    }

    public ASTThrowStatement(JavaParser p, int id) {
        super(p, id);
    }

    public JavaStatement getJavaStatement() {
        return jstatement;
    }

    public void setResponsive(Token token) {
        jstatement.setResponsive(token.beginLine, token.beginColumn,
                                 token.endLine, token.endColumn);
    }

    public Object jjtAccept(JavaParserVisitor visitor, Object data) {
        return visitor.visit(this, data);
    }
}
