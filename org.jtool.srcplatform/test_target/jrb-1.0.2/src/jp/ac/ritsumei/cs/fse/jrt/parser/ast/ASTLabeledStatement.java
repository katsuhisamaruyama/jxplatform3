/*
 *     ASTLabeledStatement.java  Aug 29, 2001
 *
 *     Katsuhisa Maruyama (maru@fse.jrt.cs.ritsumei.ac.jp)
 */

/* Generated By:JJTree: Do not edit this line. ASTLabeledStatement.java */

package jp.ac.ritsumei.cs.fse.jrt.parser.ast;
import jp.ac.ritsumei.cs.fse.jrt.parser.*;

public class ASTLabeledStatement extends SimpleNode {
    private String name;

    public ASTLabeledStatement(int id) {
        super(id);
    }

    public ASTLabeledStatement(JavaParser p, int id) {
        super(p, id);
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Object jjtAccept(JavaParserVisitor visitor, Object data) {
        return visitor.visit(this, data);
    }
}
