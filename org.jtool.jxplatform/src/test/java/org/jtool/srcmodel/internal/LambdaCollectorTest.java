/*
 *  Copyright 2022
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.srcmodel.internal;

import static org.jtool.srcmodel.AllSrcTests.CSclassroomProject;
import static org.jtool.srcmodel.AllSrcTests.LambdaProject;
import org.jtool.srcmodel.JavaClass;
import org.jtool.srcmodel.JavaMethod;
import org.jtool.jxplatform.builder.BuilderTestUtil;
import org.jtool.jxplatform.util.TestUtil;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import java.util.Set;
import org.junit.Test;
import org.junit.BeforeClass;
import static org.junit.Assert.assertEquals;

public class LambdaCollectorTest {
    
    @BeforeClass
    public static void setUp() {
        CSclassroomProject = BuilderTestUtil.getProject("CS-classroom");
        LambdaProject = BuilderTestUtil.getProject("Lambda");
    }
    
    @Test
    public void testLambdaCollector1() {
        JavaMethod jm = CSclassroomProject.getClass("Sample110FX").getMethod("start( javafx.stage.Stage )");
        LambdaCollector collector = new LambdaCollector(jm);
        MethodDeclaration node = (MethodDeclaration)jm.getASTNode();
        node.getBody().accept(collector);
        Set<JavaClass> result = collector.getLambdas();
        
        assertEquals(1, result.size());
        assertEquals("Sample110FX$LAMBDA1", TestUtil.asSortedStrOf(result));
    }
    
    @Test
    public void testLambdaCollector2() {
        JavaMethod jm = LambdaProject.getClass("Test").getMethod("run1( )");
        LambdaCollector collector = new LambdaCollector(jm);
        MethodDeclaration node = (MethodDeclaration)jm.getASTNode();
        node.getBody().accept(collector);
        Set<JavaClass> result = collector.getLambdas();
        
        assertEquals(1, result.size());
        assertEquals("Test$LAMBDA1", TestUtil.asSortedStrOf(result));
    }
    
    @Test
    public void testLambdaCollector3() {
        JavaMethod jm = LambdaProject.getClass("Test").getMethod("run2( )");
        LambdaCollector collector = new LambdaCollector(jm);
        MethodDeclaration node = (MethodDeclaration)jm.getASTNode();
        node.getBody().accept(collector);
        Set<JavaClass> result = collector.getLambdas();
        
        assertEquals(0, result.size());
    }
}
