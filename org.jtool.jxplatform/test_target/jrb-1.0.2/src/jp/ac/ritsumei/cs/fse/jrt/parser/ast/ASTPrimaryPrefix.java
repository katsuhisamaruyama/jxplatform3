/*
 *     ASTPrimaryPrefix.java  Sep 7, 2001
 *
 *     Katsuhisa Maruyama (maru@fse.jrt.cs.ritsumei.ac.jp)
 */

/* Generated By:JJTree: Do not edit this line. ASTPrimaryPrefix.java */

package jp.ac.ritsumei.cs.fse.jrt.parser.ast;
import jp.ac.ritsumei.cs.fse.jrt.parser.*;

public class ASTPrimaryPrefix extends SimpleNode {
    private String name;

    public ASTPrimaryPrefix(int id) {
        super(id);
    }

    public ASTPrimaryPrefix(JavaParser p, int id) {
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