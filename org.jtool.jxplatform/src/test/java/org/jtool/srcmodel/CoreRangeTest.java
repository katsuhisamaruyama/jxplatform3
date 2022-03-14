/*
 *  Copyright 2022
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.srcmodel;

import org.jtool.jxplatform.util.BuilderTestUtil;
import org.junit.Test;
import org.junit.BeforeClass;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;

public class CoreRangeTest {
    
    private static JavaProject DrawToolProject;
    private static JavaProject SliceProject;
    private static JavaProject VideoStoreProject;
    
    @BeforeClass
    public static void setUp() {
        DrawToolProject = BuilderTestUtil.createProject("DrawTool", "", "/src");
        SliceProject = BuilderTestUtil.createProject("Slice", "", "");
        VideoStoreProject = BuilderTestUtil.createProject("VideoStore", "/lib/*", "");
    }
    
    @AfterClass
    public static void tearDown() {
        DrawToolProject.getModelBuilder().unbuild();
        SliceProject.getModelBuilder().unbuild();
        VideoStoreProject.getModelBuilder().unbuild();
    }
    
    @Test
    public void testGetStartPosition1() {
        JavaClass jc = VideoStoreProject.getClass("org.jtool.videostore.after.Customer");
        int result = jc.getCodeRange().getStartPosition();
        
        assertEquals(89, result);
    }
    
    @Test
    public void testGetStartPosition2() {
        JavaClass jc = DrawToolProject.getClass("jp.ac.ritsumei.cs.draw.Figure");
        int result = jc.getCodeRange().getStartPosition();
        
        assertEquals(54, result);
    }
    
    @Test
    public void testGetStartPosition3() {
        JavaClass jc = SliceProject.getClass("Test200");
        int result = jc.getCodeRange().getStartPosition();
        
        assertEquals(22, result);
    }
    
    @Test
    public void testGetStartPosition4() {
        JavaMethod jm = VideoStoreProject.getClass("org.jtool.videostore.after.Customer").getMethod("statement( )");
        int result = jm.getCodeRange().getStartPosition();
        
        assertEquals(425, result);
    }
    
    @Test
    public void testGetStartPosition5() {
        JavaMethod jm = DrawToolProject.getClass("jp.ac.ritsumei.cs.draw.Figure").getMethod("setStart( int int )");
        int result = jm.getCodeRange().getStartPosition();
        
        assertEquals(1818, result);
    }
    
    @Test
    public void testGetStartPosition6() {
        JavaField jf = VideoStoreProject.getClass("org.jtool.videostore.after.Customer").getField("rentals");
        int result = jf.getCodeRange().getStartPosition();
        
        assertEquals(165, result);
    }
    
    @Test
    public void testGetStartPosition7() {
        JavaField jf = DrawToolProject.getClass("jp.ac.ritsumei.cs.draw.Figure").getField("color");
        int result = jf.getCodeRange().getStartPosition();
        
        assertEquals(606, result);
    }
    
    @Test
    public void testGetEndPosition1() {
        JavaClass jc = VideoStoreProject.getClass("org.jtool.videostore.after.Customer");
        int result = jc.getCodeRange().getEndPosition();
        
        assertEquals(1332, result);
    }
    
    @Test
    public void testGetEndPosition2() {
        JavaClass jc = DrawToolProject.getClass("jp.ac.ritsumei.cs.draw.Figure");
        int result = jc.getCodeRange().getEndPosition();
        
        assertEquals(8550, result);
    }
    
    @Test
    public void testGetEndPosition3() {
        JavaClass jc = SliceProject.getClass("Test200");
        int result = jc.getCodeRange().getEndPosition();
        
        assertEquals(319, result);
    }
    
    @Test
    public void testGetEndPosition4() {
        JavaMethod jm = VideoStoreProject.getClass("org.jtool.videostore.after.Customer").getMethod("statement( )");
        int result = jm.getCodeRange().getEndPosition();
        
        assertEquals(934, result);
    }
    
    @Test
    public void testGetEndPosition5() {
        JavaMethod jm = DrawToolProject.getClass("jp.ac.ritsumei.cs.draw.Figure").getMethod("setStart( int int )");
        int result = jm.getCodeRange().getEndPosition();
        
        assertEquals(2063, result);
    }
    
    @Test
    public void testGetEndPosition6() {
        JavaField jf = VideoStoreProject.getClass("org.jtool.videostore.after.Customer").getField("rentals");
        int result = jf.getCodeRange().getEndPosition();
        
        assertEquals(191, result);
    }
    
    @Test
    public void testGetEndPosition7() {
        JavaField jf = DrawToolProject.getClass("jp.ac.ritsumei.cs.draw.Figure").getField("color");
        int result = jf.getCodeRange().getEndPosition();
        
        assertEquals(610, result);
    }
    
    @Test
    public void testGetUpperLineNumber1() {
        JavaClass jc = VideoStoreProject.getClass("org.jtool.videostore.after.Customer");
        int result = jc.getCodeRange().getUpperLineNumber();
        
        assertEquals(6, result);
    }
    
    @Test
    public void testGetUpperLineNumber2() {
        JavaClass jc = DrawToolProject.getClass("jp.ac.ritsumei.cs.draw.Figure");
        int result = jc.getCodeRange().getUpperLineNumber();
        
        assertEquals(6, result);
    }
    
    @Test
    public void testGetUpperLineNumber3() {
        JavaClass jc = SliceProject.getClass("Test200");
        int result = jc.getCodeRange().getUpperLineNumber();
        
        assertEquals(4, result);
    }
    
    @Test
    public void testGetUpperLineNumber4() {
        JavaMethod jm = VideoStoreProject.getClass("org.jtool.videostore.after.Customer").getMethod("statement( )");
        int result = jm.getCodeRange().getUpperLineNumber();
        
        assertEquals(24, result);
    }
    
    @Test
    public void testGetUpperLineNumber5() {
        JavaMethod jm = DrawToolProject.getClass("jp.ac.ritsumei.cs.draw.Figure").getMethod("setStart( int int )");
        int result = jm.getCodeRange().getUpperLineNumber();
        
        assertEquals(84, result);
    }
    
    @Test
    public void testGetUpperLineNumber6() {
        JavaField jf = VideoStoreProject.getClass("org.jtool.videostore.after.Customer").getField("rentals");
        int result = jf.getCodeRange().getUpperLineNumber();
        
        assertEquals(10, result);
    }
    
    @Test
    public void testGetUpperLineNumber7() {
        JavaField jf = DrawToolProject.getClass("jp.ac.ritsumei.cs.draw.Figure").getField("color");
        int result = jf.getCodeRange().getUpperLineNumber();
        
        assertEquals(34, result);
    }
    
    @Test
    public void testGetBottomLineNumber1() {
        JavaClass jc = VideoStoreProject.getClass("org.jtool.videostore.after.Customer");
        int result = jc.getCodeRange().getBottomLineNumber();
        
        assertEquals(53, result);
    }
    
    @Test
    public void testGetBottomLineNumber2() {
        JavaClass jc = DrawToolProject.getClass("jp.ac.ritsumei.cs.draw.Figure");
        int result = jc.getCodeRange().getBottomLineNumber();
        
        assertEquals(320, result);
    }
    
    @Test
    public void testGetBottomLineNumber3() {
        JavaClass jc = SliceProject.getClass("Test200");
        int result = jc.getCodeRange().getBottomLineNumber();
        
        assertEquals(22, result);
    }
    
    @Test
    public void testGetBottomLineNumber4() {
        JavaMethod jm = VideoStoreProject.getClass("org.jtool.videostore.after.Customer").getMethod("statement( )");
        int result = jm.getCodeRange().getBottomLineNumber();
        
        assertEquals(36, result);
    }
    
    @Test
    public void testGetBottomLineNumber5() {
        JavaMethod jm = DrawToolProject.getClass("jp.ac.ritsumei.cs.draw.Figure").getMethod("setStart( int int )");
        int result = jm.getCodeRange().getBottomLineNumber();
        
        assertEquals(92, result);
    }
    
    @Test
    public void testGetBottomLineNumber6() {
        JavaField jf = VideoStoreProject.getClass("org.jtool.videostore.after.Customer").getField("rentals");
        int result = jf.getCodeRange().getBottomLineNumber();
        
        assertEquals(10, result);
    }
    
    @Test
    public void testGetBottomLineNumber7() {
        JavaField jf = DrawToolProject.getClass("jp.ac.ritsumei.cs.draw.Figure").getField("color");
        int result = jf.getCodeRange().getBottomLineNumber();
        
        assertEquals(34, result);
    }
    
    @Test
    public void testGetCodeLength1() {
        JavaClass jc = VideoStoreProject.getClass("org.jtool.videostore.after.Customer");
        int result = jc.getCodeRange().getCodeLength();
        
        assertEquals(1244, result);
    }
    
    @Test
    public void testGetCodeLength2() {
        JavaClass jc = DrawToolProject.getClass("jp.ac.ritsumei.cs.draw.Figure");
        int result = jc.getCodeRange().getCodeLength();
        
        assertEquals(8497, result);
    }
    
    @Test
    public void testGetCodeLength3() {
        JavaClass jc = SliceProject.getClass("Test200");
        int result = jc.getCodeRange().getCodeLength();
        
        assertEquals(298, result);
    }
    
    @Test
    public void testGetCodeLength4() {
        JavaMethod jm = VideoStoreProject.getClass("org.jtool.videostore.after.Customer").getMethod("statement( )");
        int result = jm.getCodeRange().getCodeLength();
        
        assertEquals(510, result);
    }
    
    @Test
    public void testGetCodeLength5() {
        JavaMethod jm = DrawToolProject.getClass("jp.ac.ritsumei.cs.draw.Figure").getMethod("setStart( int int )");
        int result = jm.getCodeRange().getCodeLength();
        
        assertEquals(246, result);
    }
    
    @Test
    public void testGetCodeLength6() {
        JavaField jf = VideoStoreProject.getClass("org.jtool.videostore.after.Customer").getField("rentals");
        int result = jf.getCodeRange().getCodeLength();
        
        assertEquals(27, result);
    }
    
    @Test
    public void testGetCodeLength7() {
        JavaField jf = DrawToolProject.getClass("jp.ac.ritsumei.cs.draw.Figure").getField("color");
        int result = jf.getCodeRange().getCodeLength();
        
        assertEquals(5, result);
    }
    
    @Test
    public void testGetLoc1() {
        JavaClass jc = VideoStoreProject.getClass("org.jtool.videostore.after.Customer");
        int result = jc.getCodeRange().getLoc();
        
        assertEquals(48, result);
    }
    
    @Test
    public void testGetLoc2() {
        JavaClass jc = DrawToolProject.getClass("jp.ac.ritsumei.cs.draw.Figure");
        int result = jc.getCodeRange().getLoc();
        
        assertEquals(315, result);
    }
    
    @Test
    public void testGetLoc3() {
        JavaClass jc = SliceProject.getClass("Test200");
        int result = jc.getCodeRange().getLoc();
        
        assertEquals(19, result);
    }
    
    @Test
    public void testGetGetLoc4() {
        JavaMethod jm = VideoStoreProject.getClass("org.jtool.videostore.after.Customer").getMethod("statement( )");
        int result = jm.getCodeRange().getLoc();
        
        assertEquals(13, result);
    }
    
    @Test
    public void testGetLoc5() {
        JavaMethod jm = DrawToolProject.getClass("jp.ac.ritsumei.cs.draw.Figure").getMethod("setStart( int int )");
        int result = jm.getCodeRange().getLoc();
        
        assertEquals(9, result);
    }
    
    @Test
    public void testGetLoc6() {
        JavaField jf = VideoStoreProject.getClass("org.jtool.videostore.after.Customer").getField("rentals");
        int result = jf.getCodeRange().getLoc();
        
        assertEquals(1, result);
    }
    
    @Test
    public void testGetLoc7() {
        JavaField jf = DrawToolProject.getClass("jp.ac.ritsumei.cs.draw.Figure").getField("color");
        int result = jf.getCodeRange().getLoc();
        
        assertEquals(1, result);
    }
}
