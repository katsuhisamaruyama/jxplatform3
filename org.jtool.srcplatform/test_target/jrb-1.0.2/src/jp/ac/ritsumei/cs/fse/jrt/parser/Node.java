/*
 *     Node.java  Aug 29, 2001
 *
 *     Katsuhisa Maruyama (maru@fse.cs.ritsumei.ac.jp)
 */

/* Generated By:JJTree: Do not edit this line. Node.java */

package jp.ac.ritsumei.cs.fse.jrt.parser;

public interface Node {
    public void jjtOpen();
    public void jjtClose();

    public void jjtSetParent(Node n);
    public Node jjtGetParent();

    public void jjtAddChild(Node n, int i);
    public Node jjtGetChild(int i);

    public int jjtGetNumChildren();

    public Object jjtAccept(JavaParserVisitor visitor, Object data);
}
