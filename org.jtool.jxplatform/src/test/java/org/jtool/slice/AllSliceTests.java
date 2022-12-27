/*
 *  Copyright 2022
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.slice;

import org.jtool.srcmodel.JavaProject;
import org.jtool.jxplatform.builder.BuilderTestUtil;
import org.junit.ClassRule;
import org.junit.runner.RunWith;
import org.junit.rules.ExternalResource;
import org.junit.runners.Suite;

@RunWith( Suite.class )
@Suite.SuiteClasses({
    org.jtool.slice.SliceCriterionTest.class,
    org.jtool.slice.SliceTest.class,
    
    org.jtool.slice.internal.SlicerTest.class,
})
public class AllSliceTests {
    
    //static JavaProject CSclassroomProject;
    //static JavaProject DrawToolProject;
    //static JavaProject LambdaProject;
    //static JavaProject SimpleProject;
    static JavaProject SliceProject;
    //static JavaProject TetrisProject;
    //static JavaProject VideoStoreProject;
    
    @ClassRule
    public static ExternalResource testRule = new ExternalResource() {
        
        @Override
        protected void before() throws Throwable {
            //CSclassroomProject = BuilderTestUtil.getProject("CS-classroom");
            //DrawToolProject = BuilderTestUtil.getProject("DrawTool");
            //LambdaProject = BuilderTestUtil.getProject("Lambda");
            //SimpleProject = BuilderTestUtil.getProject("Simple");
            SliceProject = BuilderTestUtil.getProject("Slice");
            //TetrisProject = BuilderTestUtil.getProject("Tetris");
            //VideoStoreProject = BuilderTestUtil.getProject("VideoStore");
        };
        
        @Override
        protected void after(){
            BuilderTestUtil.clearProject();
        };
    };
}
