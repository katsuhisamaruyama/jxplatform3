/*
 *  Copyright 2022
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.srcmodel;

import org.jtool.jxplatform.builder.BuilderTestUtil;
import org.junit.ClassRule;
import org.junit.runner.RunWith;
import org.junit.rules.ExternalResource;
import org.junit.runners.Suite;

@RunWith( Suite.class )
@Suite.SuiteClasses({
    org.jtool.srcmodel.CodeRangeTest.class,
    org.jtool.srcmodel.JavaClassTest.class,
    org.jtool.srcmodel.JavaElementTest.class,
    org.jtool.srcmodel.JavaFieldTest.class,
    org.jtool.srcmodel.JavaFileTest.class,
    org.jtool.srcmodel.JavaLocalVarTest.class,
    org.jtool.srcmodel.JavaMethodTest.class,
    
    org.jtool.srcmodel.internal.FieldAccessCollectorTest.class,
    org.jtool.srcmodel.internal.FieldInitializerCollectorTest.class,
    org.jtool.srcmodel.internal.LambdaCollectorTest.class,
    org.jtool.srcmodel.internal.LocalDeclarationCollectorTest.class,
    org.jtool.srcmodel.internal.MethodCallCollectorTest.class,
    org.jtool.srcmodel.internal.StatementCollectorTest.class,
    org.jtool.srcmodel.internal.TypeCollectorTest.class,
    org.jtool.srcmodel.internal.UncaughtExceptionTypeCollectorTest.class,
    
    org.jtool.srcmodel.JavaPackageTest.class,
    org.jtool.srcmodel.JavaProjectTest.class,
    
    org.jtool.srcmodel.internal.ProjectStoreTest.class,
})
public class AllSrcTests {
    
    public static JavaProject CSclassroomProject;
    public static JavaProject DrawToolProject;
    public static JavaProject LambdaProject;
    public static JavaProject SimpleProject;
    public static JavaProject SliceProject;
    public static JavaProject TetrisProject;
    public static JavaProject VideoStoreProject;
    
    @ClassRule
    public static ExternalResource testRule = new ExternalResource() {
        
        @Override
        protected void before() throws Throwable {
            CSclassroomProject = BuilderTestUtil.getProject("CS-classroom");
            DrawToolProject = BuilderTestUtil.getProject("DrawTool");
            LambdaProject = BuilderTestUtil.getProject("Lambda");
            SimpleProject = BuilderTestUtil.getProject("Simple");
            SliceProject = BuilderTestUtil.getProject("Slice");
            TetrisProject = BuilderTestUtil.getProject("Tetris");
            VideoStoreProject = BuilderTestUtil.getProject("VideoStore");
        };
        
        @Override
        protected void after(){
            BuilderTestUtil.clearProject();
        };
    };
}
