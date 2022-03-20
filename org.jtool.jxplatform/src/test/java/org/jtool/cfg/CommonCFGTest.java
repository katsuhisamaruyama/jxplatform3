/*
 *  Copyright 2022
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.cfg;

import org.jtool.srcmodel.JavaClass;
import org.jtool.srcmodel.JavaMethod;
import org.jtool.srcmodel.JavaProject;

import org.jtool.jxplatform.util.BuilderTestUtil;
import org.jtool.jxplatform.util.TestUtil;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.AnnotationTypeDeclaration;
import org.eclipse.jdt.core.dom.AnonymousClassDeclaration;
import org.eclipse.jdt.core.dom.EnumDeclaration;
import org.eclipse.jdt.core.dom.IMethodBinding;
import org.eclipse.jdt.core.dom.ITypeBinding;
import org.eclipse.jdt.core.dom.IVariableBinding;
import org.eclipse.jdt.core.dom.Initializer;
import org.eclipse.jdt.core.dom.LambdaExpression;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.Modifier;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.jtool.jxplatform.project.Logger;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.junit.Test;
import org.junit.Ignore;
import org.junit.BeforeClass;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;

public class CommonCFGTest {
    
    private static JavaProject SimpleProject;
    private static JavaProject SliceProject;
    private static JavaProject VideoStoreProject;
    
    @BeforeClass
    public static void setUp() {
        SimpleProject = BuilderTestUtil.createProject("Simple", "", "");
        SliceProject = BuilderTestUtil.createProject("Slice", "", "");
        VideoStoreProject = BuilderTestUtil.createProject("VideoStore", "/lib/*", "");
    }
    
    @AfterClass
    public static void tearDown() {
        SimpleProject.getModelBuilder().unbuild();
        SliceProject.getModelBuilder().unbuild();
        VideoStoreProject.getModelBuilder().unbuild();
    }
    
    @Test
    public void testAppend1() {
        JavaMethod jm = SimpleProject.getClass("Test01").getMethod("main()");
        
        JavaClass jc = VideoStoreProject.getClass("org.jtool.videostore.after.Customer");
        int result = jc.getCodeRange().getStartPosition();
        
        assertEquals(89, result);
    }
    
    @Test
    public void testGetBasicBlocks1() {
        JavaClass jc = VideoStoreProject.getClass("org.jtool.videostore.after.Customer");
        int result = jc.getCodeRange().getStartPosition();
        
        assertEquals(89, result);
    }
}
