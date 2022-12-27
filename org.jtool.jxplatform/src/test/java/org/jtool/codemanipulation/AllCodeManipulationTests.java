/*
 *  Copyright 2022
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.codemanipulation;

import org.jtool.srcmodel.JavaProject;
import org.jtool.jxplatform.builder.BuilderTestUtil;
import org.junit.ClassRule;
import org.junit.runner.RunWith;
import org.junit.rules.ExternalResource;
import org.junit.runners.Suite;

@RunWith( Suite.class )
@Suite.SuiteClasses({
    CodeExtractorTest.class,
    CodeGeneratorTest.class,
})
public class AllCodeManipulationTests {
    
    //public static JavaProject CSclassroomProject;
    //public static JavaProject DrawToolProject;
    //public static JavaProject LambdaProject;
    public static JavaProject SimpleProject;
    //public static JavaProject SliceProject;
    //public static JavaProject TetrisProject;
    //public tatic JavaProject VideoStoreProject;
    
    @ClassRule
    public static ExternalResource testRule = new ExternalResource() {
        
        @Override
        protected void before() throws Throwable {
            //CSclassroomProject = BuilderTestUtil.getProject("CS-classroom");
            //DrawToolProject = BuilderTestUtil.getProject("DrawTool");
            //LambdaProject = BuilderTestUtil.getProject("Lambda");
            SimpleProject = BuilderTestUtil.getProject("Simple");
            //SliceProject = BuilderTestUtil.getProject("Slice");
            //TetrisProject = BuilderTestUtil.getProject("Tetris");
            //VideoStoreProject = BuilderTestUtil.getProject("VideoStore");
        };
        
        @Override
        protected void after(){
            BuilderTestUtil.clearProject();
        };
    };
}
