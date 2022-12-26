/*
 *  Copyright 2022
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.srcmodel;

import static org.jtool.jxplatform.builder.ModelBuilder.br;
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
     * An AST node corresponding to this model element.
     */
    private final ASTNode astNode;
    
    /**
     * The name of this package.
     */
    private final String name;
    
    /**
     * A flag indicating whether this package exists inside the target project.
     */
    private boolean inProject = false;
    
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
     * @param name the name of this package
     */
    private JavaPackage(PackageDeclaration node, String name) {
        this.astNode = node;
        this.name = name;
    }
    
    /**
     * Creates a new object representing a default package.
     * This constructor is not intended to be invoked by clients.
     * @param jfile the file declaring this package
     * @return the created package
     */
    public static JavaPackage createDefault(JavaFile jfile) {
        return create(null, jfile);
    }
    
    /**
     * Creates a new object representing a package.
     * This constructor is not intended to be invoked by clients.
     * @param node an AST node for this package
     * @param jfile a file that declares this package
     * @return the created package
     */
    public static JavaPackage create(PackageDeclaration node, JavaFile jfile) {
        assert jfile != null;
        
        String name = "";
        if (node != null) {
            IPackageBinding binding = node.resolveBinding();
            if (binding != null) {
                name = binding.getName();
            }
        } else {
            name = DEFAUL_PACKAGE_NAME;
        }
        
        JavaPackage jpackage = jfile.getJavaProject().getPackage(name);
        if (jpackage == null) {
            jpackage = new JavaPackage(node, name);
            jfile.getJavaProject().addPackage(jpackage);
        }
        jfile.setPackage(jpackage);
        jpackage.inProject = true;
        return jpackage;
    }
    
    /**
     * Creates a new object representing a package used in a class not having its corresponding file.
     * This constructor is not intended to be invoked by clients.
     * @param pbinding the package binding for this package
     * @param jproject a project which this package is used in
     * @return the created package
     */
    public static JavaPackage createExternal(IPackageBinding pbinding, JavaProject jproject) {
        assert pbinding != null;
        assert jproject != null;
        
        String name = pbinding.getName();
        JavaPackage jpackage = jproject.getPackage(name);
        if (jpackage == null) {
            jpackage = new JavaPackage(null, name);
            jproject.addPackage(jpackage);
        }
        jpackage.inProject = false;
        return jpackage;
    }
    
    /**
     * Returns the AST node corresponding to this package.
     * @return the corresponding AST node
     */
    public ASTNode getASTNode() {
        return astNode;
    }
    
    /**
     * Returns the name of this package.
     * @return the package name
     */
    public String getName() {
        return name;
    }
    
    /**
     * Tests if this package exists inside the target project, which can resent the corresponding source code.
     * @return {@code true} if this package exists inside the target project, otherwise {@code false}
     */
    public boolean isInProject() {
        return inProject;
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
     * @param jclass the class to be added
     */
    void addClass(JavaClass jclass) {
        classes.add(jclass);
    }
    
    /**
     * Removes a class from this package.
     * @param jclass the class to be removed
     */
    void removeClass(JavaClass jclass) {
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
     * Obtains sorted classes belonging to this package.
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
        buf.append(br);
        buf.append("PACKAGE: ");
        buf.append(getName());
        return buf.toString();
    }
    
    /**
     * The collection of packages containing classes that a class of this package uses.
     */
    private Set<JavaPackage> efferentPackages = null;
    
    /**
     * The collection of packages whose class uses a class belonging to this package.
     */
    private Set<JavaPackage> afferentPackages = null;
    
    /**
     * Obtains packages containing classes that are used by classes belonging to this package.
     * @return the collection of the used packages, or an empty set if there is no file for this package
     */
    public Set<JavaPackage> getEfferentJavaPackages() {
        if (!inProject) {
            return new HashSet<>();
        }
        
        if (efferentPackages == null) {
            efferentPackages = new HashSet<>();
            collectEfferentPackages();
        }
        return efferentPackages;
    }
    
    /**
     * Collects packages containing classes that are used by classes belonging to this package.
     */
    private void collectEfferentPackages() {
        for (JavaClass jclass : classes) {
            for (JavaClass jc : jclass.getEfferentClasses()) {
                JavaPackage jpackage = jc.getPackage();
                if (jpackage != null && !jpackage.equals(this)) {
                    efferentPackages.add(jpackage);
                }
            }
        }
    }
    
    /**
     * Obtains packages containing classes that use classes belonging to this package.
     * @return the collection of the using packages, or an empty set if there is no file for this package
     */
    public Set<JavaPackage> getAfferentJavaPackages() {
        if (!inProject) {
            return new HashSet<>();
        }
        
        if (afferentPackages == null) {
            afferentPackages = new HashSet<>();
            collectAfferentPackages();
        }
        return afferentPackages;
    }
    
    /**
     * Collects packages containing classes that use classes belonging to this package.
     */
    private void collectAfferentPackages() {
        for (JavaClass jclass : classes) {
            for (JavaClass jc : jclass.getAfferentClasses()) {
                JavaPackage jpackage = jc.getPackage();
                if (jpackage != null && !jpackage.equals(this)) {
                    afferentPackages.add(jpackage);
                }
            }
        }
    }
}
