/*
 *     SimpleNode.java  Aug 29, 2001
 *
 *     Katsuhisa Maruyama (maru@fse.cs.ritsumei.ac.jp)
 */

/* Generated By:JJTree: Do not edit this line. SimpleNode.java */

package jp.ac.ritsumei.cs.fse.jrt.parser;
import jp.ac.ritsumei.cs.fse.jrt.parser.ast.*;

public class SimpleNode implements Node {
    protected Node parent;
    protected Node[] children;
    protected int id;
    protected JavaParser parser;
    protected Token first, last;

    public SimpleNode() {
    }

    public SimpleNode(int i) {
        id = i;
    }

    public SimpleNode(JavaParser p, int i) {
        this(i);
        parser = p;
    }

    public String getName() {
        return null;
    }

    public void jjtOpen() {
        first = parser.getToken(1);
    }

    public void jjtClose() {
        last = parser.getToken(0);
    }

    public void jjtSetParent(Node n) {
        parent = n;
    }

    public Node jjtGetParent() {
        return parent;
    }

    public void jjtAddChild(Node n, int i) {
        if (children == null) {
            children = new Node[i + 1];
        } else if (i >= children.length) {
            Node c[] = new Node[i + 1];
            System.arraycopy(children, 0, c, 0, children.length);
            children = c;
        }
        children[i] = n;
    }

    public Node jjtGetChild(int i) {
        return children[i];
    }

    public int jjtGetNumChildren() {
        return (children == null) ? 0 : children.length;
    }

    public Object jjtAccept(JavaParserVisitor visitor, Object data) {
        return visitor.visit(this, data);
    }

    public Object childrenAccept(JavaParserVisitor visitor, Object data) {
        if (children != null) {
            for (int i = 0; i < children.length; ++i) {
                children[i].jjtAccept(visitor, data);
            }
        }
        return data;
    }

    public Object childAccept(JavaParserVisitor visitor, Object data, int i) {
        return children[i].jjtAccept(visitor, data);
    }

    public Token getFirstToken() {
         return first;
    }  

    public Token getLastToken() {
         return last;
    }

    public int getID() {
        return(id);
    }

    public String toString() {
        return JavaParserTreeConstants.jjtNodeName[id];
    }

    public String toString(String prefix) {
        return prefix + toString();
    }

    public void dump(String prefix) {
        System.out.println(toString(prefix));
        if (children != null) {
            for (int i = 0; i < children.length; ++i) {
                SimpleNode n = (SimpleNode)children[i];
                if (n != null) {
                    n.dump(prefix + " ");
                }
            }
        }
    }
}
