/*
 *  Copyright 2020
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.srcmodel;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.ASTNode;
import java.util.Set;
import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;
import java.io.File;

/**
 * An object representing a Java source file.
 * 
 * @author Katsuhisa Maruyama
 */
public class JavaFile {
    
    /**
     * The compilation unit created by the Eclipse's JDT parser.
     */
    protected CompilationUnit compilationUnit;
    
    /**
     * The project that this file exists.
     */
    protected JavaProject jproject;
    
    /**
     * The absolute path that indicates the location of this file in the file system.
     */
    protected String path;
    
    /**
     * The contents (source code) of this file.
     */
    protected String source;
    
    /**
     * The character set of the contents of this file.
     */
    protected String charset;
    
    /**
     * The package declared in this file.
     */
    protected JavaPackage jpackage;
    
    /**
     * The collection of classes declared in this file.
     */
    protected Set<JavaClass> classes = new HashSet<>();
    
    /**
     * The collection of AST nodes corresponding to the import declarations appearing in this file.
     */
    protected List<ASTNode> imports = new ArrayList<>();
    
    /**
     * Creates a new object representing a file.
     * @param cu the compilation unit of this file
     * @param path the absolute path of this file in the file system
     * @param source the contents of this file
     * @param charset the character set of the contents of this file
     * @param jproject the project that this file exists
     */
    public JavaFile(CompilationUnit cu, String path, String source, String charset, JavaProject jproject) {
        this.compilationUnit = cu;
        this.jproject = jproject;
        this.path = path;
        this.source = source;
        this.charset = charset;
        jproject.addFile(this);
    }
    
    /**
     * Returns the compilation unit created by the Eclipse's JDT parser.
     * @return the compilation unit of this file
     */
    public CompilationUnit getCompilationUnit() {
        return compilationUnit;
    }
    
    /**
     * Returns the project that this file exists.
     * @return the project of this file
     */
    public JavaProject getJavaProject() {
        return jproject;
    }
    
    /**
     * Returns the absolute path that indicates the location of this file in the file system.
     * @return the absolute path of this file
     */
    public String getPath() {
        return path;
    }
    
    /**
     * Returns the relative path of this file based on the project path.
     * @return the string of the relative path of this file
     */
    public String getRelativePath() {
        return path.substring(jproject.getPath().length() + 1);
    }
    
    /**
     * returns the name of this file.
     * @return the file name
     */
    public String getName() {
        return path.substring(path.lastIndexOf(File.separator) + 1);
    }
    
    /**
     * Returns the contents of this file, which represents source code.
     * @return the contents of this file
     */
    public String getSource() {
        return source;
    }
    
    /**
     * Returns the character set of the contents of this file.
     * @return the character set for this file
     */
    public String getCharset() {
        return charset;
    }
    
    /**
     * Sets a package declared in this file.
     * This method is not intended to be invoked by clients.
     * @param jpackage the package for this file
     */
    public void setPackage(JavaPackage jpackage) {
        this.jpackage = jpackage;
    }
    
    /**
     * Returns the project that this file exists.
     * All the classes declared in this file belong to the package.
     * @return the package for this file
     */
    public JavaPackage getPackage() {
        return jpackage;
    }
    
    /**
     * Adds a class declared in this file.
     * This method is not intended to be invoked by clients.
     * @param jclass a class to be added
     */
    protected void addClass(JavaClass jclass) {
        classes.add(jclass);
        jproject.addClass(jclass);
    }
    
    /**
     * Returns classes declared in this file.
     * @return the collection of the declared classes
     */
    public Set<JavaClass> getClasses() {
        return classes;
    }
    
    /**
     * Adds an AST node corresponding to an import declaration appearing in this file.
     * @param node the corresponding AST node 
     */
    public void addImport(ASTNode node) {
        imports.add(node);
    }
    
    /**
     * Returns AST nodes corresponding to the import declarations appearing in this file.
     * @return the collection of the AST nodes related to the import declarations
     */
    public List<ASTNode> getImports() {
        return imports;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object obj) {
        return (obj instanceof JavaFile) ? equals((JavaFile)obj) : false;
    }
    
    /**
     * Tests if a given file is equal to this file.
     * @param jfile the file to be checked
     * @return the {@code true} if the given file is equal to this file
     */
    public boolean equals(JavaFile jfile) {
        return jfile != null && (this == jfile || path.equals(jfile.path));
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return path.hashCode();
    }
    
    /**
     * Obtains information on this file.
     * @return the string representing the information
     */
    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append("\n");
        buf.append("FILE: ");
        buf.append(getPath());
        return buf.toString();
    }
    
    /**
     * Obtains the relative path of a given path
     * @param path the path string that indicates the location of this file
     * @param base the path string that indicates the base location
     * @return the string of the relative path
     */
    public static String getRelativePath(String path, String base) {
        if (path.startsWith(base) && path.length() > base.length()) {
            return path.substring(base.length() + 1);
        }
        return null;
    }
    
    /**
     * Creates the path string of a file after its file extension is changed into a given string. 
     * @param path the the original path string
     * @param ext the new file extension
     * @return the created file path string
     */
    public static String changeExtension(String path, String ext) {
        int pos = path.lastIndexOf(".");
        if (pos != -1) {
            return path.substring(0, pos + 1) + ext;
        }
        return path + "." + ext;
    }
}
