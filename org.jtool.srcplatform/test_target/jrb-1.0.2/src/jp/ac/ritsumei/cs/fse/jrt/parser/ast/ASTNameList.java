/*
 *     ASTNameList.java  Aug 24, 2001
 *
 *     Katsuhisa Maruyama (maru@fse.jrt.cs.ritsumei.ac.jp)
 */

/* Generated By:JJTree: Do not edit this line. ASTNameList.java */

package jp.ac.ritsumei.cs.fse.jrt.parser.ast;
import jp.ac.ritsumei.cs.fse.jrt.parser.*;

public class ASTNameList extends SimpleNode {
    public ASTNameList(int id) {
        super(id);
    }

    public ASTNameList(JavaParser p, int id) {
        super(p, id);
    }

    public Object jjtAccept(JavaParserVisitor visitor, Object data) {
        return visitor.visit(this, data);
    }
}
