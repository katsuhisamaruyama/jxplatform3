/*
 *  Copyright 2022
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.pdg;

import org.jtool.srcmodel.JavaProject;
import org.jtool.jxplatform.builder.BuilderTestUtil;
import org.junit.ClassRule;
import org.junit.runner.RunWith;
import org.junit.rules.ExternalResource;
import org.junit.runners.Suite;

@RunWith( Suite.class )
@Suite.SuiteClasses({
    org.jtool.pdg.CDTest.class,
    org.jtool.pdg.ClDGEntryTest.class,
    org.jtool.pdg.ClDGTest.class,
    org.jtool.pdg.DDTest.class,
    org.jtool.pdg.DependenceTest.class,
    org.jtool.pdg.DependencyGraphTest.class,
    org.jtool.pdg.InterPDGCDTest.class,
    org.jtool.pdg.InterPDGDDTest.class,
    org.jtool.pdg.InterPDGEdgeTest.class,
    org.jtool.pdg.PDGEntryTest.class,
    org.jtool.pdg.PDGNodeTest.class,
    org.jtool.pdg.PDGStatementTest.class,
    org.jtool.pdg.PDGTest.class,
    org.jtool.pdg.SDGTest.class,
})
public class AllPDGTests {
    
    //public static JavaProject CSclassroomProject;
    //public static JavaProject DrawToolProject;
    //public static JavaProject LambdaProject;
    public static JavaProject SimpleProject;
    public static JavaProject SliceProject;
    //public static JavaProject TetrisProject;
    //public static JavaProject VideoStoreProject;
    
    @ClassRule
    public static ExternalResource testRule = new ExternalResource() {
        
        @Override
        protected void before() throws Throwable {
            //CSclassroomProject = BuilderTestUtil.getProject("CS-classroom");
            //DrawToolProject = BuilderTestUtil.getProject("DrawTool");
            //LambdaProject = BuilderTestUtil.getProject("Lambda");
            SimpleProject = BuilderTestUtil.getProject("Simple");
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
