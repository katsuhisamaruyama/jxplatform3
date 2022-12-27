/*
 *  Copyright 2022
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.cfg;

import org.jtool.srcmodel.JavaProject;
import org.jtool.jxplatform.builder.BuilderTestUtil;
import org.junit.ClassRule;
import org.junit.runner.RunWith;
import org.junit.rules.ExternalResource;
import org.junit.runners.Suite;

@RunWith( Suite.class )
@Suite.SuiteClasses({
    org.jtool.cfg.BasicBlockTest.class,
    org.jtool.cfg.CallGraphTest.class,
    org.jtool.cfg.CCFGEntryTest.class,
    org.jtool.cfg.CCFGTest.class,
    org.jtool.cfg.CFGExceptionTest.class,
    org.jtool.cfg.CFGExitTest.class,
    org.jtool.cfg.CFGFieldEntryTest.class,
    org.jtool.cfg.CFGMergeTest.class,
    org.jtool.cfg.CFGMethodCallTest.class,
    org.jtool.cfg.CFGMethodEntryTest.class,
    org.jtool.cfg.CFGNodeTest.class,
    org.jtool.cfg.CFGParameterTest.class,
    org.jtool.cfg.CFGReceiverTest.class,
    org.jtool.cfg.CFGStatementTest.class,
    org.jtool.cfg.CFGTest.class,
    org.jtool.cfg.ControlFlowTest.class,
    org.jtool.cfg.JFieldReferenceTest.class,
    org.jtool.cfg.JLocalVarReferenceTest.class,
    org.jtool.cfg.JMethodReferenceTest.class,
    org.jtool.cfg.JUncoverFieldReferenceTest.class,
    
    org.jtool.cfg.internal.CFGTryTest.class,
    org.jtool.cfg.internal.ExpressionVisitorTest.class,
    org.jtool.cfg.internal.FieldReferenceResolverTest.class,
    org.jtool.cfg.internal.JAliasReferenceTest.class,
    org.jtool.cfg.internal.JReturnValueReferenceTest.class,
    org.jtool.cfg.internal.JVersatileReferenceTest.class,
    org.jtool.cfg.internal.LocalAliasResolverTest.class,
    org.jtool.cfg.internal.ReceiverTypeResolverTest.class,
    org.jtool.cfg.internal.StatementVisitorTest.class,
})
public class AllCFGTests {
    
    //public static JavaProject CSclassroomProject;
    //public static JavaProject DrawToolProject;
    //public static JavaProject LambdaProject;
    public static JavaProject SimpleProject;
    public static JavaProject SliceProject;
    //public static JavaProject TetrisProject;
    public static JavaProject VideoStoreProject;
    
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
            VideoStoreProject = BuilderTestUtil.getProject("VideoStore");
        };
        
        @Override
        protected void after(){
            BuilderTestUtil.clearProject();
        };
    };
}
