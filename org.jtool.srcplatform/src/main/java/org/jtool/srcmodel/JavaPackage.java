/*
 *  Copyright 2020
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.srcmodel;

import org.eclipse.jdt.core.dom.PackageDeclaration;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.IPackageBinding;
import java.util.Set;
import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;

/**
 * An object representing a package.
 * 
 * @author Katsuhisa Maruyama
 */
public class JavaPackage {
    
    /**
     * The AST node corresponding to this model element.
     */
    private ASTNode astNode;
    
    /**
     * The file that declares this model element.
     */
    private JavaFile jfile;
    
    /**
     * The name of this package.
     */
    private String name;
    
    /**
     * The collection of classes that belong to this package.
     */
    private Set<JavaClass> classes = new HashSet<>();
    
    /**
     * The unique name for the default package.
     */
    private static final String DEFAUL_PACKAGE_NAME = "(default)";
    
    /**
     * Creates a new object representing a package.
     * The object is not allowed to be directly created.
     * @param node the AST node for this method
     * @param jfile the file that declares this package
     * @param name the name of this package
     */
    private JavaPackage(PackageDeclaration node, JavaFile jfile, String name) {
        this.astNode = node;
        this.jfile = jfile;
        this.name = name;
    }
    
    /**
     * Creates a default package
     * @param jfile the file declaring this package
     * @return the created package
     */
    public static JavaPackage createDefault(JavaFile jfile) {
        return create(null, jfile);
    }
    
    /**
     * Creates a package.
     * @param node the AST node for this package
     * @param jfile the file declaring this package
     * @return the created package
     */
    public static JavaPackage create(PackageDeclaration node, JavaFile jfile) {
        String name = "";
        if (node != null) {
            IPackageBinding binding = node.resolveBinding();
            if (binding != null) {
                name = binding.getName();
            }
        } else {
            name = DEFAUL_PACKAGE_NAME;
        }
        
        JavaPackage jpackage = jfile.getProject().getPackage(name);
        if (jpackage != null) {
            return jpackage;
        }
        
        jpackage = new JavaPackage(node, jfile, name);
        jfile.getProject().addPackage(jpackage);
        return jpackage;
    }
    
    /**
     * Returns the AST node corresponding to this model element.
     * @return the corresponding AST node
     */
    public ASTNode getASTNode() {
        return astNode;
    }
    
    /**
     * Returns the file that declares this package.
     * @return the declaring file
     */
    public JavaFile getFile() {
        return jfile;
    }
    
    /**
     * Returns the name of this package.
     * @return the package name
     */
    public String getName() {
        return name;
    }
    
    /**
     * Tests if this is default package.
     * @return {@code true} if this is default package, otherwise {@code false}
     */
    public boolean isDefault() {
        return name.equals(DEFAUL_PACKAGE_NAME);
    }
    
    /**
     * Adds a class to this package.
     * This method is not intended to be invoked by clients.
     * @param jclass the class to be added
     */
    protected void addClass(JavaClass jclass) {
        classes.add(jclass);
    }
    
    /**
     * Removes a class from this package.
     * This method is not intended to be invoked by clients.
     * @param jclass the class to be removed
     */
    protected void removeClass(JavaClass jclass) {
        classes.remove(jclass);
    }
    
    /**
     * Obtains classes belonging to this package.
     * @return the collection of the belonging classes
     */
    public Set<JavaClass> getClasses() {
        return classes;
    }
    
    /**
     * Obtains classes belonging to this package.
     * @return the sorted collection of the belonging classes
     */
    public List<JavaClass> getSortedClasses() {
        return JavaClass.sortClasses(new ArrayList<>(classes));
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object obj) {
        return (obj instanceof JavaPackage) ? equals((JavaPackage)obj) : false;
    }
    
    /**
     * Tests if a given package is equal to this package.
     * @param jpackage the package to be checked
     * @return the {@code true} if the given package is equal to this package
     */
    public boolean equals(JavaPackage jpackage) {
        return jpackage != null && (this == jpackage || name.equals(jpackage.name)); 
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return name.hashCode();
    }
    
    /**
     * Obtains information on this package.
     * @return the string representing the information
     */
    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append("\n");
        buf.append("PACKAGE: ");
        buf.append(getName());
        return buf.toString();
    }
    
    /**
     * A flag indicating whether the collected binding information is resolved.
     */
    protected boolean resolved = false;
    
    /**
     * The collection of packages whose class uses a class belonging to this package.
     */
    protected Set<JavaPackage> afferentPackages = null;
    
    /**
     * The collection of packages containing classes that a class of this package uses.
     */
    protected Set<JavaPackage> efferentPackages = null;
    
    /**
     * Obtains packages whose class uses a class belonging to this package.
     * @return the collection of the afferent packages
     */
    public Set<JavaPackage> getAfferentJavaPackages() {
        if (afferentPackages == null) {
            findEfferentPackages();
        }
        return afferentPackages;
    }
    
    /**
     * Obtains packages containing classes that a class of this package uses.
     * @return the collection of the efferent packages
     */
    public Set<JavaPackage> getEfferentJavaPackages() {
        if (efferentPackages == null) {
            findEfferentPackages();
        }
        return efferentPackages;
    }
    
    private void findEfferentPackages() {
        if (efferentPackages == null) {
            efferentPackages = new HashSet<>();
        }
        if (afferentPackages == null) {
            afferentPackages = new HashSet<>();
        }
        for (JavaClass jclass : classes) {
            for (JavaClass jc : jclass.getEfferentClassesInProject()) {
                JavaPackage jpackage = jc.getPackage();
                if (jpackage != null && !jpackage.equals(this)) {
                    efferentPackages.add(jpackage);
                    jpackage.addAfferentPackage(this);
                }
            }
        }
    }
    
    private void addAfferentPackage(JavaPackage jpackage) {
        if (afferentPackages == null) {
            afferentPackages = new HashSet<>();
        }
        if (jpackage != null && !afferentPackages.contains(jpackage)) {
            afferentPackages.add(jpackage);
        }
    }
}
