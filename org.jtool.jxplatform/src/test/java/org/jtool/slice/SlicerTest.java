/*
 *  Copyright 2022
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.slice;

import org.jtool.srcmodel.JavaProject;
import org.jtool.jxplatform.util.BuilderTestUtil;
import org.jtool.jxplatform.util.PDGTestUtil;
import org.jtool.jxplatform.util.TestUtil;
import org.jtool.jxplatform.util.SliceTestUtil;
import java.util.List;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class SlicerTest {
    
    private static JavaProject SliceProject;
    
    @BeforeClass
    public static void setUp() {
        SliceProject = BuilderTestUtil.createProject("Slice", "", "");
    }
    
    @AfterClass
    public static void tearDown() {
        SliceProject.getModelBuilder().unbuild();
    }
    
    @Test
    public void testSlice101_1() {
        SliceCriterion criterion = SliceTestUtil.getSliceCriterion(SliceProject, "Test101", 5, 12);
        Slicer slicer = new Slicer(criterion);
        List<String> result = PDGTestUtil.getIdList(criterion.getDependencyGraph(), slicer.getNodes());
        
        assertEquals("5;6",
                TestUtil.asStr(result));
    }
    
    @Test
    public void testSlice101_2() {
        SliceCriterion criterion = SliceTestUtil.getSliceCriterion(SliceProject, "Test101", 6, 12);
        Slicer slicer = new Slicer(criterion);
        List<String> result = PDGTestUtil.getIdList(criterion.getDependencyGraph(), slicer.getNodes());
        
        assertEquals("5;6;7",
                TestUtil.asStr(result));
    }
    
    @Test
    public void testSlice101_3() {
        SliceCriterion criterion = SliceTestUtil.getSliceCriterion(SliceProject, "Test101", 7, 12);
        Slicer slicer = new Slicer(criterion);
        List<String> result = PDGTestUtil.getIdList(criterion.getDependencyGraph(), slicer.getNodes());
        
        assertEquals("5;6;8;14;15",
                TestUtil.asStr(result));
    }
    
    @Test
    public void testSlice102_1() {
        SliceCriterion criterion = SliceTestUtil.getSliceCriterion(SliceProject, "Test102", 7, 12);
        Slicer slicer = new Slicer(criterion);
        List<String> result = PDGTestUtil.getIdList(criterion.getDependencyGraph(), slicer.getNodes());
        
        assertEquals("5;6;7;8;9;10;17;26;27;28;30",
                TestUtil.asStr(result));
    }
    
    @Test
    public void testSlice102_2() {
        SliceCriterion criterion = SliceTestUtil.getSliceCriterion(SliceProject, "Test102", 8, 12);
        Slicer slicer = new Slicer(criterion);
        List<String> result = PDGTestUtil.getIdList(criterion.getDependencyGraph(), slicer.getNodes());
        
        assertEquals("5;11;18",
                TestUtil.asStr(result));
    }
    
    @Test
    public void testSlice102_3() {
        SliceCriterion criterion = SliceTestUtil.getSliceCriterion(SliceProject, "Test102", 9, 12);
        Slicer slicer = new Slicer(criterion);
        List<String> result = PDGTestUtil.getIdList(criterion.getDependencyGraph(), slicer.getNodes());
        
        assertEquals("5;11;12;13;14;15;16;19;26;27;28;30",
                TestUtil.asStr(result));
    }
    
    @Test
    public void testSlice102_4() {
        SliceCriterion criterion = SliceTestUtil.getSliceCriterion(SliceProject, "Test102", 10, 27);
        Slicer slicer = new Slicer(criterion);
        List<String> result = PDGTestUtil.getIdList(criterion.getDependencyGraph(), slicer.getNodes());
        
        assertEquals("5;11;12;13;14;15;16;26;27;28;30",
                TestUtil.asStr(result));
    }
    
    @Test
    public void testSlice103_1() {
        SliceCriterion criterion = SliceTestUtil.getSliceCriterion(SliceProject, "Test103", 7, 12);
        Slicer slicer = new Slicer(criterion);
        List<String> result = PDGTestUtil.getIdList(criterion.getDependencyGraph(), slicer.getNodes());
        
        assertEquals("5;7;8;59;60",
                TestUtil.asStr(result));
    }
    
    @Test
    public void testSlice103_2() {
        SliceCriterion criterion = SliceTestUtil.getSliceCriterion(SliceProject, "Test103", 10, 12);
        Slicer slicer = new Slicer(criterion);
        List<String> result = PDGTestUtil.getIdList(criterion.getDependencyGraph(), slicer.getNodes());
        
        assertEquals("5;15;16;17;19;42;43;44;48;59;60",
                TestUtil.asStr(result));
    }
    
    @Test
    public void testSlice103_3() {
        SliceCriterion criterion = SliceTestUtil.getSliceCriterion(SliceProject, "Test103", 11, 12);
        Slicer slicer = new Slicer(criterion);
        List<String> result = PDGTestUtil.getIdList(criterion.getDependencyGraph(), slicer.getNodes());
        
        assertEquals("5;15;16;17;20;21;22;23;42;43;44;48;49;50;52;59;60",
                TestUtil.asStr(result));
    }
    
    @Test
    public void testSlice103_4() {
        SliceCriterion criterion = SliceTestUtil.getSliceCriterion(SliceProject, "Test103", 12, 12);
        Slicer slicer = new Slicer(criterion);
        List<String> result = PDGTestUtil.getIdList(criterion.getDependencyGraph(), slicer.getNodes());
        
        assertEquals("5;15;16;17;24;25;26;27;42;43;44;48;49;50;52;59;60",
                TestUtil.asStr(result));
    }
    
    @Test
    public void testSlice103_5() {
        SliceCriterion criterion = SliceTestUtil.getSliceCriterion(SliceProject, "Test103", 15, 12);
        Slicer slicer = new Slicer(criterion);
        List<String> result = PDGTestUtil.getIdList(criterion.getDependencyGraph(), slicer.getNodes());
        
        assertEquals("5;15;16;17;29;30;33;34;36;37;38;39;42;43;44;48;49;50;52;53;54;57;58;59;60",
                TestUtil.asStr(result));
    }
    
    @Test
    public void testSlice104_1() {
        SliceCriterion criterion = SliceTestUtil.getSliceCriterion(SliceProject, "Test104", 5, 8);
        Slicer slicer = new Slicer(criterion);
        List<String> result = PDGTestUtil.getIdList(criterion.getDependencyGraph(), slicer.getNodes());
        
        assertEquals("5;6;9",
                TestUtil.asStr(result));
    }
    
    @Test
    public void testSlice104_2() {
        SliceCriterion criterion = SliceTestUtil.getSliceCriterion(SliceProject, "Test104", 6, 8);
        Slicer slicer = new Slicer(criterion);
        List<String> result = PDGTestUtil.getIdList(criterion.getDependencyGraph(), slicer.getNodes());
        
        assertEquals("5;7;10",
                TestUtil.asStr(result));
    }
    
    @Test
    public void testSlice104_3() {
        SliceCriterion criterion = SliceTestUtil.getSliceCriterion(SliceProject, "Test104", 7, 8);
        Slicer slicer = new Slicer(criterion);
        List<String> result = PDGTestUtil.getIdList(criterion.getDependencyGraph(), slicer.getNodes());
        
        assertEquals("5;8;11",
                TestUtil.asStr(result));
    }
    
    @Test
    public void testSlice104_4() {
        SliceCriterion criterion = SliceTestUtil.getSliceCriterion(SliceProject, "Test104", 8, 12);
        Slicer slicer = new Slicer(criterion);
        List<String> result = PDGTestUtil.getIdList(criterion.getDependencyGraph(), slicer.getNodes());
        
        assertEquals("5;7;8;10;11;12",
                TestUtil.asStr(result));
    }
    
    @Test
    public void testSlice104_5() {
        SliceCriterion criterion = SliceTestUtil.getSliceCriterion(SliceProject, "Test104", 9, 8);
        Slicer slicer = new Slicer(criterion);
        List<String> result = PDGTestUtil.getIdList(criterion.getDependencyGraph(), slicer.getNodes());
        
        assertEquals("5;6;7;9;13",
                TestUtil.asStr(result));
    }
    
    @Test
    public void testSlice104_6() {
        SliceCriterion criterion = SliceTestUtil.getSliceCriterion(SliceProject, "Test104", 10, 8);
        Slicer slicer = new Slicer(criterion);
        List<String> result = PDGTestUtil.getIdList(criterion.getDependencyGraph(), slicer.getNodes());
        
        assertEquals("5;6;8;9;14",
                TestUtil.asStr(result));
    }
    
    @Test
    public void testSlice105_1() {
        SliceCriterion criterion = SliceTestUtil.getSliceCriterion(SliceProject, "Test105", 6, 12);
        Slicer slicer = new Slicer(criterion);
        List<String> result = PDGTestUtil.getIdList(criterion.getDependencyGraph(), slicer.getNodes());
        
        assertEquals("5;6;7;8;9;10;11;15;16;18;20",
                TestUtil.asStr(result));
    }
    
    @Test
    public void testSlice105_2() {
        SliceCriterion criterion = SliceTestUtil.getSliceCriterion(SliceProject, "Test105", 7, 12);
        Slicer slicer = new Slicer(criterion);
        List<String> result = PDGTestUtil.getIdList(criterion.getDependencyGraph(), slicer.getNodes());
        
        assertEquals("5;7;8;9;12;15;16;17;21;22;23",
                TestUtil.asStr(result));
    }
    
    @Test
    public void testSlice106_1() {
        SliceCriterion criterion = SliceTestUtil.getSliceCriterion(SliceProject, "Test106", 7, 12);
        Slicer slicer = new Slicer(criterion);
        List<String> result = PDGTestUtil.getIdList(criterion.getDependencyGraph(), slicer.getNodes());
        
        assertEquals("5;6;7;8;9;10;11;12;16;17;19;21",
                TestUtil.asStr(result));
    }
    
    @Test
    public void testSlice106_2() {
        SliceCriterion criterion = SliceTestUtil.getSliceCriterion(SliceProject, "Test106", 8, 12);
        Slicer slicer = new Slicer(criterion);
        List<String> result = PDGTestUtil.getIdList(criterion.getDependencyGraph(), slicer.getNodes());
        
        assertEquals("5;8;9;10;13;16;17;18;22;23;24",
                TestUtil.asStr(result));
    }
    
    @Test
    public void testSlice107_1() {
        SliceCriterion criterion = SliceTestUtil.getSliceCriterion(SliceProject, "Test107", 10, 12);
        Slicer slicer = new Slicer(criterion);
        List<String> result = PDGTestUtil.getIdList(criterion.getDependencyGraph(), slicer.getNodes());
        
        assertEquals("5;7;8;10",
                TestUtil.asStr(result));
    }
    
    @Test
    public void testSlice107_2() {
        SliceCriterion criterion = SliceTestUtil.getSliceCriterion(SliceProject, "Test107", 11, 12);
        Slicer slicer = new Slicer(criterion);
        List<String> result = PDGTestUtil.getIdList(criterion.getDependencyGraph(), slicer.getNodes());
        
        assertEquals("5;7;8;9;11",
                TestUtil.asStr(result));
    }
    
    @Test
    public void testSlice108_1() {
        SliceCriterion criterion = SliceTestUtil.getSliceCriterion(SliceProject, "Test108", 12, 12);
        Slicer slicer = new Slicer(criterion);
        List<String> result = PDGTestUtil.getIdList(criterion.getDependencyGraph(), slicer.getNodes());
        
        assertEquals("5;6;7;9;10;13",
                TestUtil.asStr(result));
    }
    
    @Test
    public void testSlice108_2() {
        SliceCriterion criterion = SliceTestUtil.getSliceCriterion(SliceProject, "Test108", 13, 12);
        Slicer slicer = new Slicer(criterion);
        List<String> result = PDGTestUtil.getIdList(criterion.getDependencyGraph(), slicer.getNodes());
        
        assertEquals("5;6;8;9;11;14",
                TestUtil.asStr(result));
    }
    
    @Test
    public void testSlice109_1() {
        SliceCriterion criterion = SliceTestUtil.getSliceCriterion(SliceProject, "Test109", 11, 12);
        Slicer slicer = new Slicer(criterion);
        List<String> result = PDGTestUtil.getIdList(criterion.getDependencyGraph(), slicer.getNodes());
        
        assertEquals("5;6;7;9;10;13",
                TestUtil.asStr(result));
    }
    
    @Test
    public void testSlice109_2() {
        SliceCriterion criterion = SliceTestUtil.getSliceCriterion(SliceProject, "Test109", 12, 12);
        Slicer slicer = new Slicer(criterion);
        List<String> result = PDGTestUtil.getIdList(criterion.getDependencyGraph(), slicer.getNodes());
        
        assertEquals("5;6;8;9;11;14",
                TestUtil.asStr(result));
    }
    
    @Test
    public void testSlice110_1() {
        SliceCriterion criterion = SliceTestUtil.getSliceCriterion(SliceProject, "Test110", 9, 12);
        Slicer slicer = new Slicer(criterion);
        List<String> result = PDGTestUtil.getIdList(criterion.getDependencyGraph(), slicer.getNodes());
        
        assertEquals("5;6;8;9;10",
                TestUtil.asStr(result));
    }
    
    @Test
    public void testSlice110_2() {
        SliceCriterion criterion = SliceTestUtil.getSliceCriterion(SliceProject, "Test110", 10, 12);
        Slicer slicer = new Slicer(criterion);
        List<String> result = PDGTestUtil.getIdList(criterion.getDependencyGraph(), slicer.getNodes());
        
        assertEquals("5;7;11",
                TestUtil.asStr(result));
    }
    
    @Test
    public void testSlice110_3() {
        SliceCriterion criterion = SliceTestUtil.getSliceCriterion(SliceProject, "Test110", 7, 16);
        Slicer slicer = new Slicer(criterion);
        List<String> result = PDGTestUtil.getIdList(criterion.getDependencyGraph(), slicer.getNodes());
        
        assertEquals("5;6;8;9",
                TestUtil.asStr(result));
    }
    
    @Test
    public void testSlice111_1() {
        SliceCriterion criterion = SliceTestUtil.getSliceCriterion(SliceProject, "Test111", 15, 12);
        Slicer slicer = new Slicer(criterion);
        List<String> result = PDGTestUtil.getIdList(criterion.getDependencyGraph(), slicer.getNodes());
        
        assertEquals("5;6;7;9;10;11;17",
                TestUtil.asStr(result));
    }
    
    @Test
    public void testSlice111_2() {
        SliceCriterion criterion = SliceTestUtil.getSliceCriterion(SliceProject, "Test111", 16, 12);
        Slicer slicer = new Slicer(criterion);
        List<String> result = PDGTestUtil.getIdList(criterion.getDependencyGraph(), slicer.getNodes());
        
        assertEquals("5;6;8;9;10;13;14;18",
                TestUtil.asStr(result));
    }
    
    @Test
    public void testSlice112_1() {
        SliceCriterion criterion = SliceTestUtil.getSliceCriterion(SliceProject, "Test112", 17, 12);
        Slicer slicer = new Slicer(criterion);
        List<String> result = PDGTestUtil.getIdList(criterion.getDependencyGraph(), slicer.getNodes());
        
        assertEquals("5;6;7;9;12;13;19",
                TestUtil.asStr(result));
    }
    
    @Test
    public void testSlice112_2() {
        SliceCriterion criterion = SliceTestUtil.getSliceCriterion(SliceProject, "Test112", 18, 12);
        Slicer slicer = new Slicer(criterion);
        List<String> result = PDGTestUtil.getIdList(criterion.getDependencyGraph(), slicer.getNodes());
        
        assertEquals("5;6;8;9;12;15;16;20",
                TestUtil.asStr(result));
    }
    
    @Test
    public void testSlice112_3() {
        SliceCriterion criterion = SliceTestUtil.getSliceCriterion(SliceProject, "Test112", 19, 12);
        Slicer slicer = new Slicer(criterion);
        List<String> result = PDGTestUtil.getIdList(criterion.getDependencyGraph(), slicer.getNodes());
        
        assertEquals("5;6;10;11;21",
                TestUtil.asStr(result));
    }
    
    @Test
    public void testSlice113_1() {
        SliceCriterion criterion = SliceTestUtil.getSliceCriterion(SliceProject, "Test113", 12, 12);
        Slicer slicer = new Slicer(criterion);
        List<String> result = PDGTestUtil.getIdList(criterion.getDependencyGraph(), slicer.getNodes());
        
        assertEquals("5;6;8;10;11;13",
                TestUtil.asStr(result));
    }
    
    @Test
    public void testSlice113_2() {
        SliceCriterion criterion = SliceTestUtil.getSliceCriterion(SliceProject, "Test113", 13, 12);
        Slicer slicer = new Slicer(criterion);
        List<String> result = PDGTestUtil.getIdList(criterion.getDependencyGraph(), slicer.getNodes());
        
        assertEquals("5;6;7;8;9;14",
                TestUtil.asStr(result));
    }
    
    @Test
    public void testSlice114_1() {
        SliceCriterion criterion = SliceTestUtil.getSliceCriterion(SliceProject, "Test114", 12, 12);
        Slicer slicer = new Slicer(criterion);
        List<String> result = PDGTestUtil.getIdList(criterion.getDependencyGraph(), slicer.getNodes());
        
        assertEquals("5;6;8;9;10;13",
                TestUtil.asStr(result));
    }
    
    @Test
    public void testSlice114_2() {
        SliceCriterion criterion = SliceTestUtil.getSliceCriterion(SliceProject, "Test114", 13, 12);
        Slicer slicer = new Slicer(criterion);
        List<String> result = PDGTestUtil.getIdList(criterion.getDependencyGraph(), slicer.getNodes());
        
        assertEquals("5;6;7;8;11;14",
                TestUtil.asStr(result));
    }
    
    @Test
    public void testSlice115_1() {
        SliceCriterion criterion = SliceTestUtil.getSliceCriterion(SliceProject, "Test115", 11, 12);
        Slicer slicer = new Slicer(criterion);
        List<String> result = PDGTestUtil.getIdList(criterion.getDependencyGraph(), slicer.getNodes());
        
        assertEquals("5;6;7;9;10;11;13;14",
                TestUtil.asStr(result));
    }
    
    @Test
    public void testSlice115_2() {
        SliceCriterion criterion = SliceTestUtil.getSliceCriterion(SliceProject, "Test115", 12, 12);
        Slicer slicer = new Slicer(criterion);
        List<String> result = PDGTestUtil.getIdList(criterion.getDependencyGraph(), slicer.getNodes());
        
        assertEquals("5;6;8;9;10;12;13;15",
                TestUtil.asStr(result));
    }
    
    @Test
    public void testSlice116_1() {
        SliceCriterion criterion = SliceTestUtil.getSliceCriterion(SliceProject, "Test116", 15, 12);
        Slicer slicer = new Slicer(criterion);
        List<String> result = PDGTestUtil.getIdList(criterion.getDependencyGraph(), slicer.getNodes());
        
        assertEquals("5;6;7;9;10;11;12;17;18",
                TestUtil.asStr(result));
    }
    
    @Test
    public void testSlice116_2() {
        SliceCriterion criterion = SliceTestUtil.getSliceCriterion(SliceProject, "Test116", 16, 12);
        Slicer slicer = new Slicer(criterion);
        List<String> result = PDGTestUtil.getIdList(criterion.getDependencyGraph(), slicer.getNodes());
        
        assertEquals("5;6;8;9;10;14;15;17;19",
                TestUtil.asStr(result));
    }
    
    @Test
    public void testSlice117_1() {
        SliceCriterion criterion = SliceTestUtil.getSliceCriterion(SliceProject, "Test117", 9, 12);
        Slicer slicer = new Slicer(criterion);
        List<String> result = PDGTestUtil.getIdList(criterion.getDependencyGraph(), slicer.getNodes());
        
        assertEquals("5;6;8;10;12;13;14",
                TestUtil.asStr(result));
    }
    
    @Test
    public void testSlice117_2() {
        SliceCriterion criterion = SliceTestUtil.getSliceCriterion(SliceProject, "Test117", 10, 12);
        Slicer slicer = new Slicer(criterion);
        List<String> result = PDGTestUtil.getIdList(criterion.getDependencyGraph(), slicer.getNodes());
        
        assertEquals("5;7;8;10;11;12;15",
                TestUtil.asStr(result));
    }
    
    @Test
    public void testSlice118_1() {
        SliceCriterion criterion = SliceTestUtil.getSliceCriterion(SliceProject, "Test118", 4, 12);
        Slicer slicer = new Slicer(criterion);
        List<String> result = PDGTestUtil.getIdList(criterion.getDependencyGraph(), slicer.getNodes());
        
        assertEquals("5;6;7;8;12;71;75;77",
                TestUtil.asStr(result));
    }
    
    @Test
    public void testSlice118_2() {
        SliceCriterion criterion = SliceTestUtil.getSliceCriterion(SliceProject, "Test118", 5, 12);
        Slicer slicer = new Slicer(criterion);
        List<String> result = PDGTestUtil.getIdList(criterion.getDependencyGraph(), slicer.getNodes());
        
        assertEquals("5;13;14;15;16;19;78;79;82;84",
                TestUtil.asStr(result));
    }
    
    @Test
    public void testSlice118_3() {
        SliceCriterion criterion = SliceTestUtil.getSliceCriterion(SliceProject, "Test118", 6, 12);
        Slicer slicer = new Slicer(criterion);
        List<String> result = PDGTestUtil.getIdList(criterion.getDependencyGraph(), slicer.getNodes());
        
        assertEquals("5;20;21;22;24;26;85;87;89;91",
                TestUtil.asStr(result));
    }
    
    @Test
    public void testSlice118_4() {
        SliceCriterion criterion = SliceTestUtil.getSliceCriterion(SliceProject, "Test118", 7, 12);
        Slicer slicer = new Slicer(criterion);
        List<String> result = PDGTestUtil.getIdList(criterion.getDependencyGraph(), slicer.getNodes());
        
        assertEquals("5;27;28;29;32;33;92;95;96;98",
                TestUtil.asStr(result));
    }
    
    @Test
    public void testSlice118_5() {
        SliceCriterion criterion = SliceTestUtil.getSliceCriterion(SliceProject, "Test118", 8, 12);
        Slicer slicer = new Slicer(criterion);
        List<String> result = PDGTestUtil.getIdList(criterion.getDependencyGraph(), slicer.getNodes());
        
        assertEquals("5;34;35;36;37;38;40;99;100;101;103;105",
                TestUtil.asStr(result));
    }
    
    @Test
    public void testSlice118_6() {
        SliceCriterion criterion = SliceTestUtil.getSliceCriterion(SliceProject, "Test118", 9, 12);
        Slicer slicer = new Slicer(criterion);
        List<String> result = PDGTestUtil.getIdList(criterion.getDependencyGraph(), slicer.getNodes());
        
        assertEquals("5;41;42;43;45;46;47;106;108;109;110;112",
                TestUtil.asStr(result));
    }
    
    @Test
    public void testSlice118_7() {
        SliceCriterion criterion = SliceTestUtil.getSliceCriterion(SliceProject, "Test118", 10, 12);
        Slicer slicer = new Slicer(criterion);
        List<String> result = PDGTestUtil.getIdList(criterion.getDependencyGraph(), slicer.getNodes());
        
        assertEquals("5;48;49;50;51;53;54;113;114;116;117;119",
                TestUtil.asStr(result));
    }
    
    @Test
    public void testSlice118_8() {
        SliceCriterion criterion = SliceTestUtil.getSliceCriterion(SliceProject, "Test118", 11, 12);
        Slicer slicer = new Slicer(criterion);
        List<String> result = PDGTestUtil.getIdList(criterion.getDependencyGraph(), slicer.getNodes());
        
        assertEquals("5;55;56;57;58;59;60;61;120;121;122;123;124;126",
                TestUtil.asStr(result));
    }
    
    @Test
    public void testSlice118_9() {
        SliceCriterion criterion = SliceTestUtil.getSliceCriterion(SliceProject, "Test118", 12, 12);
        Slicer slicer = new Slicer(criterion);
        List<String> result = PDGTestUtil.getIdList(criterion.getDependencyGraph(), slicer.getNodes());
        
        assertEquals("5;62;63;64;68;127;131;133",
                TestUtil.asStr(result));
    }
    
    @Test
    public void testSlice119_1() {
        SliceCriterion criterion = SliceTestUtil.getSliceCriterion(SliceProject, "Test119", 8, 12);
        Slicer slicer = new Slicer(criterion);
        List<String> result = PDGTestUtil.getIdList(criterion.getDependencyGraph(), slicer.getNodes());
        
        assertEquals("2;4;5;6;7;10;11;13;14;15;24;26;27;28;29;31;32;33;35;36;37;38;55",
                TestUtil.asStr(result));
    }
    
    @Test
    public void testSlice119_2() {
        SliceCriterion criterion = SliceTestUtil.getSliceCriterion(SliceProject, "Test119", 8, 16);
        Slicer slicer = new Slicer(criterion);
        List<String> result = PDGTestUtil.getIdList(criterion.getDependencyGraph(), slicer.getNodes());
        
        assertEquals("2;4;5;6;7;24;26;27;28;29;31;32;33;55",
                TestUtil.asStr(result));
    }
    
    @Test
    public void testSlice119_3() {
        SliceCriterion criterion = SliceTestUtil.getSliceCriterion(SliceProject, "Test119", 10, 12); 
        Slicer slicer = new Slicer(criterion);
        List<String> result = PDGTestUtil.getIdList(criterion.getDependencyGraph(), slicer.getNodes());
        
        assertEquals("2;4;5;6;7;10;11;13;14;15;24;26;27;28;29;40;41;42;44;45;46;47;56",
                TestUtil.asStr(result));
    }
    
    @Test
    public void testSlice119_4() {
        SliceCriterion criterion = SliceTestUtil.getSliceCriterion(SliceProject, "Test119", 9, 8); 
        Slicer slicer = new Slicer(criterion);
        List<String> result = PDGTestUtil.getIdList(criterion.getDependencyGraph(), slicer.getNodes());
        
        assertEquals("2;4;5;6;7;24;26;27;28;29;31;32;33;55",
                TestUtil.asStr(result));
    }
    
    @Test
    public void testSlice119_5() {
        SliceCriterion criterion = SliceTestUtil.getSliceCriterion(SliceProject, "Test119", 11, 12); 
        Slicer slicer = new Slicer(criterion);
        List<String> result = PDGTestUtil.getIdList(criterion.getDependencyGraph(), slicer.getNodes());
        
        assertEquals("2;4;5;6;7;14;15;17;18;24;25;26;27;28;29;40;41;42;48;56;70;71",
                TestUtil.asStr(result));
    }
    
    @Test
    public void testSlice119_6() {
        SliceCriterion criterion = SliceTestUtil.getSliceCriterion(SliceProject, "Test119", 11, 16); 
        Slicer slicer = new Slicer(criterion);
        List<String> result = PDGTestUtil.getIdList(criterion.getDependencyGraph(), slicer.getNodes());
        
        assertEquals("2;4;5;6;7;24;26;27;28;29;31;32;33;40;41;42;55;56",
                TestUtil.asStr(result));
    }
    
    @Test
    public void testSlice119_7() {
        SliceCriterion criterion = SliceTestUtil.getSliceCriterion(SliceProject, "Test119", 11, 18); 
        Slicer slicer = new Slicer(criterion);
        List<String> result = PDGTestUtil.getIdList(criterion.getDependencyGraph(), slicer.getNodes());
        
        assertEquals("2;4;5;6;7;14;15;24;26;27;28;29;40;41;42;56",
                TestUtil.asStr(result));
    }
    
    @Test
    public void testSlice119_8() {
        SliceCriterion criterion = SliceTestUtil.getSliceCriterion(SliceProject, "Test119", 12, 12); 
        Slicer slicer = new Slicer(criterion);
        List<String> result = PDGTestUtil.getIdList(criterion.getDependencyGraph(), slicer.getNodes());
        
        assertEquals("24;25;49;50;51;52;57;58;60;70;71",
                TestUtil.asStr(result));
    }
    
    @Test
    public void testSlice120_1() {
        SliceCriterion criterion = SliceTestUtil.getSliceCriterion(SliceProject, "Test120", 5, 12);
        Slicer slicer = new Slicer(criterion);
        List<String> result = PDGTestUtil.getIdList(criterion.getDependencyGraph(), slicer.getNodes());
        
        assertEquals("15;20;21;22;25;46;49;51",
                TestUtil.asStr(result));
    }
    
    @Test
    public void testSlice120_2() {
        SliceCriterion criterion = SliceTestUtil.getSliceCriterion(SliceProject, "Test120", 6, 12);
        Slicer slicer = new Slicer(criterion);
        List<String> result = PDGTestUtil.getIdList(criterion.getDependencyGraph(), slicer.getNodes());
        
        assertEquals("2;4;5;6;8;9;15;16;17;18;19;26;27;28;29;31;52;53;55;57",
                TestUtil.asStr(result));
    }
    
    @Test
    public void testSlice120_3() {
        SliceCriterion criterion = SliceTestUtil.getSliceCriterion(SliceProject, "Test120", 7, 12);
        Slicer slicer = new Slicer(criterion);
        List<String> result = PDGTestUtil.getIdList(criterion.getDependencyGraph(), slicer.getNodes());
        
        assertEquals("2;4;5;6;8;9;15;16;17;18;19;32;33;34;36;37;58;60;61;63",
                TestUtil.asStr(result));
    }
    
    @Test
    public void testSlice120_4() {
        SliceCriterion criterion = SliceTestUtil.getSliceCriterion(SliceProject, "Test120", 8, 12);
        Slicer slicer = new Slicer(criterion);
        List<String> result = PDGTestUtil.getIdList(criterion.getDependencyGraph(), slicer.getNodes());
        
        assertEquals("2;4;5;6;8;9;15;16;17;18;19;38;39;40;41;42;43;64;65;66;67;69",
                TestUtil.asStr(result));
    }
    
    @Test
    public void testSlice121_1() {
        SliceCriterion criterion = SliceTestUtil.getSliceCriterion(SliceProject, "Test121", 10, 12);
        Slicer slicer = new Slicer(criterion);
        List<String> result = PDGTestUtil.getIdList(criterion.getDependencyGraph(), slicer.getNodes());
        
        assertEquals("16;17;19",
                TestUtil.asStr(result));
    }
    
    @Test
    public void testSlice121_2() {
        SliceCriterion criterion = SliceTestUtil.getSliceCriterion(SliceProject, "Test121", 11, 12);
        Slicer slicer = new Slicer(criterion);
        List<String> result = PDGTestUtil.getIdList(criterion.getDependencyGraph(), slicer.getNodes());
        
        assertEquals("16;18;20",
                TestUtil.asStr(result));
    }
    
    @Test
    public void testSlice121_3() {
        SliceCriterion criterion = SliceTestUtil.getSliceCriterion(SliceProject, "Test121", 6, 12);
        Slicer slicer = new Slicer(criterion);
        List<String> result = PDGTestUtil.getIdList(criterion.getDependencyGraph(), slicer.getNodes());
        
        assertEquals("5;7;8;9;10;12;13;16;18;20;21;23",
                TestUtil.asStr(result));
    }
    
    @Test
    public void testSlice122_1() {
        SliceCriterion criterion = SliceTestUtil.getSliceCriterion(SliceProject, "Test122", 15, 12);
        Slicer slicer = new Slicer(criterion);
        List<String> result = PDGTestUtil.getIdList(criterion.getDependencyGraph(), slicer.getNodes());
        
        assertEquals("5;6;7;8;10;11;12;13;14;17;21;24;25;26;27;28;29;30;31;33;35",
                TestUtil.asStr(result));
    }
    
    @Test
    public void testSlice122_2() {
        SliceCriterion criterion = SliceTestUtil.getSliceCriterion(SliceProject, "Test122", 9, 16);
        Slicer slicer = new Slicer(criterion);
        List<String> result = PDGTestUtil.getIdList(criterion.getDependencyGraph(), slicer.getNodes());
        
        assertEquals("5;6;7;8;10;11;12;13;14;15;17;24;25;26;27;28;29;30;31;33;35",
                TestUtil.asStr(result));
    }
    
    @Test
    public void testSlice122_3() {
        SliceCriterion criterion = SliceTestUtil.getSliceCriterion(SliceProject, "Test122", 11, 22);
        Slicer slicer = new Slicer(criterion);
        List<String> result = PDGTestUtil.getIdList(criterion.getDependencyGraph(), slicer.getNodes());
        
        assertEquals("5;6;8;11;12;13;17;18;24;25;26;27;28;29;30;31",
                TestUtil.asStr(result));
    }
    
    @Test
    public void testSlice122_4() {
        SliceCriterion criterion = SliceTestUtil.getSliceCriterion(SliceProject, "Test122", 13, 16);
        Slicer slicer = new Slicer(criterion);
        List<String> result = PDGTestUtil.getIdList(criterion.getDependencyGraph(), slicer.getNodes());
        
        assertEquals("5;6;7;8;10;11;12;13;14;17;19;20;24;25;26;27;28;29;30;31;33;35",
                TestUtil.asStr(result));
    }
    
    @Test
    public void testSlice122_5() {
        SliceCriterion criterion = SliceTestUtil.getSliceCriterion(SliceProject, "Test122", 10, 27);
        Slicer slicer = new Slicer(criterion);
        List<String> result = PDGTestUtil.getIdList(criterion.getDependencyGraph(), slicer.getNodes());
        
        assertEquals("5;6;8;11;12;13;17;24;25;26;27;28;29;30;31",
                TestUtil.asStr(result));
    }
    
    @Test
    public void testSlice123_1() {
        SliceCriterion criterion = SliceTestUtil.getSliceCriterion(SliceProject, "Test123", 15, 12);
        Slicer slicer = new Slicer(criterion);
        List<String> result = PDGTestUtil.getIdList(criterion.getDependencyGraph(), slicer.getNodes());
        
        assertEquals("10;12;17;18;19;20;22;23;24;25;26;29;33;36;37;38;39;40;41;42;43;45;47",
                TestUtil.asStr(result));
    }
    
    @Test
    public void testSlice123_2() {
        SliceCriterion criterion = SliceTestUtil.getSliceCriterion(SliceProject, "Test123", 9, 16);
        Slicer slicer = new Slicer(criterion);
        List<String> result = PDGTestUtil.getIdList(criterion.getDependencyGraph(), slicer.getNodes());
        
        assertEquals("10;12;17;18;19;20;22;23;24;25;26;27;29;36;37;38;39;40;41;42;43;45;47",
                TestUtil.asStr(result));
    }
    
    @Test
    public void testSlice123_3() {
        SliceCriterion criterion = SliceTestUtil.getSliceCriterion(SliceProject, "Test123", 11, 22);
        Slicer slicer = new Slicer(criterion);
        List<String> result = PDGTestUtil.getIdList(criterion.getDependencyGraph(), slicer.getNodes());
        
        assertEquals("10;12;17;18;20;23;24;25;29;30;36;37;38;39;40;41;42;43",
                TestUtil.asStr(result));
    }
    
    @Test
    public void testSlice123_4() {
        SliceCriterion criterion = SliceTestUtil.getSliceCriterion(SliceProject, "Test123", 13, 16);
        Slicer slicer = new Slicer(criterion);
        List<String> result = PDGTestUtil.getIdList(criterion.getDependencyGraph(), slicer.getNodes());
        
        assertEquals("10;12;17;18;19;20;22;23;24;25;26;29;31;32;36;37;38;39;40;41;42;43;45;47",
                TestUtil.asStr(result));
    }
    
    @Test
    public void testSlice123_5() {
        SliceCriterion criterion = SliceTestUtil.getSliceCriterion(SliceProject, "Test123", 10, 27);
        Slicer slicer = new Slicer(criterion);
        List<String> result = PDGTestUtil.getIdList(criterion.getDependencyGraph(), slicer.getNodes());
        
        assertEquals("10;12;17;18;20;23;24;25;29;36;37;38;39;40;41;42;43",
                TestUtil.asStr(result));
    }
    
    @Test
    public void testSlice124_1() {
        SliceCriterion criterion = SliceTestUtil.getSliceCriterion(SliceProject, "Test124", 7, 13);
        Slicer slicer = new Slicer(criterion);
        List<String> result = PDGTestUtil.getIdList(criterion.getDependencyGraph(), slicer.getNodes());
        
        assertEquals("2;6;23;26;27;28;30",
                TestUtil.asStr(result));
    }
    
    @Test
    public void testSlice124_2() {
        SliceCriterion criterion = SliceTestUtil.getSliceCriterion(SliceProject, "Test124", 8, 12);
        Slicer slicer = new Slicer(criterion);
        List<String> result = PDGTestUtil.getIdList(criterion.getDependencyGraph(), slicer.getNodes());
        
        assertEquals("2;3;4;6;7;8;10;16;17;23;24;26;27;28;29;30;31;32;33;34;42",
                TestUtil.asStr(result));
    }
    
    @Test
    public void testSlice124_3() {
        SliceCriterion criterion = SliceTestUtil.getSliceCriterion(SliceProject, "Test124", 9, 12);
        Slicer slicer = new Slicer(criterion);
        List<String> result = PDGTestUtil.getIdList(criterion.getDependencyGraph(), slicer.getNodes());
        
        assertEquals("2;3;4;6;11;12;13;15;16;17;23;24;25;26;27;28;29;30;35;36;37;38;39;42",
                TestUtil.asStr(result));
    }
    
    @Test
    public void testSlice125_1() {
        SliceCriterion criterion = SliceTestUtil.getSliceCriterion(SliceProject, "Test125", 6, 12);
        Slicer slicer = new Slicer(criterion);
        List<String> result = PDGTestUtil.getIdList(criterion.getDependencyGraph(), slicer.getNodes());
        
        assertEquals("5;6;7;8;9;10;11;23;24;25;27",
                TestUtil.asStr(result));
    }
    
    @Test
    public void testSlice125_2() {
        SliceCriterion criterion = SliceTestUtil.getSliceCriterion(SliceProject, "Test125", 7, 12);
        Slicer slicer = new Slicer(criterion);
        List<String> result = PDGTestUtil.getIdList(criterion.getDependencyGraph(), slicer.getNodes());
        
        assertEquals("5;6;12;13;14;15;16;17;18;19;20;23;24;25;27;28;29;30;32",
                TestUtil.asStr(result));
    }
    
    @Test
    public void testSlice126_1() {
        SliceCriterion criterion = SliceTestUtil.getSliceCriterion(SliceProject, "Test126", 8, 13);
        Slicer slicer = new Slicer(criterion);
        List<String> result = PDGTestUtil.getIdList(criterion.getDependencyGraph(), slicer.getNodes());
        
        assertEquals("2;4;5;6;7;14;15;16;17;18;19;21;22;23;25;27;28;39;40;46;47;48;49;50;51;52;53;54;55;56;57",
                TestUtil.asStr(result));
    }
    
    @Test
    public void testSlice126_2() {
        SliceCriterion criterion = SliceTestUtil.getSliceCriterion(SliceProject, "Test126", 10, 12);
        Slicer slicer = new Slicer(criterion);
        List<String> result = PDGTestUtil.getIdList(criterion.getDependencyGraph(), slicer.getNodes());
        
        assertEquals("2;4;5;6;7;10;11;13;14;15;16;17;18;19;21;22;23;25;27;28;39;40;46;47;48;49;50;51;52;53;54;55;56;57;58;59;60;61;62",
                TestUtil.asStr(result));
    }
    
    @Test
    public void testSlice126_3() {
        SliceCriterion criterion = SliceTestUtil.getSliceCriterion(SliceProject, "Test126", 11, 12);
        Slicer slicer = new Slicer(criterion);
        List<String> result = PDGTestUtil.getIdList(criterion.getDependencyGraph(), slicer.getNodes());
        
        assertEquals("2;4;5;6;7;10;11;13;14;15;16;17;18;19;21;22;23;25;27;28;39;"
                + "40;46;47;48;49;50;51;52;63;64;65;66;67;68;69;70",
                TestUtil.asStr(result));
    }
    
    @Test
    public void testSlice127_1() {
        SliceCriterion criterion = SliceTestUtil.getSliceCriterion(SliceProject, "Test127", 8, 12);
        Slicer slicer = new Slicer(criterion);
        List<String> result = PDGTestUtil.getIdList(criterion.getDependencyGraph(), slicer.getNodes());
        
        assertEquals("5;6;7;8;9;11;12;13;16;17;18;19;23;25;26;27;28;31;32;33;35;43;44",
                TestUtil.asStr(result));
    }
    
    @Test
    public void testSlice127_2() {
        SliceCriterion criterion = SliceTestUtil.getSliceCriterion(SliceProject, "Test127", 9, 12);
        Slicer slicer = new Slicer(criterion);
        List<String> result = PDGTestUtil.getIdList(criterion.getDependencyGraph(), slicer.getNodes());
        
        assertEquals("5;15;20;40;41",
                TestUtil.asStr(result));
    }
    
    @Test
    public void testSlice128_1() {
        SliceCriterion criterion = SliceTestUtil.getSliceCriterion(SliceProject, "Test128", 9, 12);
        Slicer slicer = new Slicer(criterion);
        List<String> result = PDGTestUtil.getIdList(criterion.getDependencyGraph(), slicer.getNodes());
        
        assertEquals("2;4;5;6;7;10;11;13;32;33;39;40;41;42;43;45;47;48;49;51;52;53;54;78",
                TestUtil.asStr(result));
    }
    
    @Test
    public void testSlice128_2() {
        SliceCriterion criterion = SliceTestUtil.getSliceCriterion(SliceProject, "Test128", 10, 12);
        Slicer slicer = new Slicer(criterion);
        List<String> result = PDGTestUtil.getIdList(criterion.getDependencyGraph(), slicer.getNodes());
        
        assertEquals("2;4;5;6;7;10;11;13;14;15;16;17;18;19;21;22;23;25;27;28;32;33;39;"
                + "40;41;42;43;44;45;47;48;49;55;56;57;58;59;60;61;62;78",
                TestUtil.asStr(result));
    }
    
    @Test
    public void testSlice128_3() {
        SliceCriterion criterion = SliceTestUtil.getSliceCriterion(SliceProject, "Test128", 11, 12);
        Slicer slicer = new Slicer(criterion);
        List<String> result = PDGTestUtil.getIdList(criterion.getDependencyGraph(), slicer.getNodes());
        
        assertEquals("2;4;5;6;7;10;11;13;14;15;16;17;18;19;21;22;23;25;27;28;32;33;39;"
                + "40;41;42;43;44;45;47;48;49;63;64;65;67;68;69;70;71;72;73;74;75;78;80;82;83;85",
                TestUtil.asStr(result));
    }
    
    @Test
    public void testSlice129_1() {
        SliceCriterion criterion = SliceTestUtil.getSliceCriterion(SliceProject, "Test129", 13, 15);
        Slicer slicer = new Slicer(criterion);
        List<String> result = PDGTestUtil.getIdList(criterion.getDependencyGraph(), slicer.getNodes());
        
        assertEquals("22;23;24;26;27;30;31;32;33;34;35;46;47;48;49;"
                + "50;51;53;61;63;65;67;68;69;71;72;73;74;75;76;"
                + "116;119;120;121;122;123;124;125;126;137;138;139;"
                + "140;141;142;143;144;186;187;188;189;190;222;223;225;226",
                TestUtil.asStr(result));
    }
    
    @Test
    public void testSlice129_2() {
        SliceCriterion criterion = SliceTestUtil.getSliceCriterion(SliceProject, "Test129", 15, 15);
        Slicer slicer = new Slicer(criterion);
        List<String> result = PDGTestUtil.getIdList(criterion.getDependencyGraph(), slicer.getNodes());
        
        assertEquals("31;32;33;34;35;61;63;65;67;68;69;71;72;73;74;75;76;"
                + "116;128;129;130;131;132;133;134;145;146;147;148;149;"
                + "150;151;152;153;192;193;194;195;196;198;199;"
                + "200;201;202;205;206;207;208;209;210;211;212;213;215;217;222;223;225;226",
                TestUtil.asStr(result));
    }
    
    @Test
    public void testSlice129_3() {
        SliceCriterion criterion = SliceTestUtil.getSliceCriterion(SliceProject, "Test129", 19, 15);
        Slicer slicer = new Slicer(criterion);
        List<String> result = PDGTestUtil.getIdList(criterion.getDependencyGraph(), slicer.getNodes());
        
        assertEquals("2;4;5;6;7;9;10;11;12;15;16;17;18;19;20;22;23;24;26;27;31;32;33;34;35;37;38;39;"
                + "40;41;42;43;45;46;47;48;49;50;51;53;61;63;"
                + "116;154;155;156;157;159;160;161;162;163;164;165;166;167;168",
                TestUtil.asStr(result));
    }
    
    @Test
    public void testSlice129_4() {
        SliceCriterion criterion = SliceTestUtil.getSliceCriterion(SliceProject, "Test129", 23, 15);
        Slicer slicer = new Slicer(criterion);
        List<String> result = PDGTestUtil.getIdList(criterion.getDependencyGraph(), slicer.getNodes());
        
        assertEquals("2;4;5;6;7;9;10;11;12;16;17;18;19;20;22;23;24;26;27;31;32;33;34;35;37;38;39;"
                + "40;41;42;43;45;46;47;48;49;50;51;53;61;63;79;81;82;83;84;86;87;88;89;"
                + "92;93;94;95;96;97;99;100;101;102;103;104;105;107;116;169;"
                + "170;171;172;174;175;176;177;178;179;180;181;182;183",
                TestUtil.asStr(result));
    }
    
    @Test
    public void testSlice130_1() {
        SliceCriterion criterion = SliceTestUtil.getSliceCriterion(SliceProject, "Test130", 6, 16);
        Slicer slicer = new Slicer(criterion);
        List<String> result = PDGTestUtil.getIdList(criterion.getDependencyGraph(), slicer.getNodes());
        
        assertEquals("5;7;8;9;10;11;12;13;14;18;19;20;21;22;23;24;26;27;28;29;31;37;38;39;41;43;44",
                TestUtil.asStr(result));
    }
    
    @Test
    public void testSlice131_1() {
        SliceCriterion criterion = SliceTestUtil.getSliceCriterion(SliceProject, "Test131", 10, 15);
        Slicer slicer = new Slicer(criterion);
        List<String> result = PDGTestUtil.getIdList(criterion.getDependencyGraph(), slicer.getNodes());
        
        assertEquals("5;7;8;9;11;12;13;14;17;18;19;20;21",
                TestUtil.asStr(result));
    }
    
    @Test
    public void testSlice132_1() {
        SliceCriterion criterion = SliceTestUtil.getSliceCriterion(SliceProject, "Test132", 6, 12);
        Slicer slicer = new Slicer(criterion);
        List<String> result = PDGTestUtil.getIdList(criterion.getDependencyGraph(), slicer.getNodes());
        
        assertEquals("5;6;7;8;9;12;13;14;15;16;17;18;20;21;23;24;26;27",
                TestUtil.asStr(result));
    }
    
    @Test
    public void testSlice133_1() {
        SliceCriterion criterion = SliceTestUtil.getSliceCriterion(SliceProject, "Test133", 8, 12);
        Slicer slicer = new Slicer(criterion);
        List<String> result = PDGTestUtil.getIdList(criterion.getDependencyGraph(), slicer.getNodes());
        
        assertEquals("5;6;84;85",
                TestUtil.asStr(result));
    }
    
    @Test
    public void testSlice134_1() {
        SliceCriterion criterion = SliceTestUtil.getSliceCriterion(SliceProject, "Test134", 7, 12);
        Slicer slicer = new Slicer(criterion);
        List<String> result = PDGTestUtil.getIdList(criterion.getDependencyGraph(), slicer.getNodes());
        
        assertEquals("37;38",
                TestUtil.asStr(result));
    }
    
    @Test
    public void testSlice135_1() {
        SliceCriterion criterion = SliceTestUtil.getSliceCriterion(SliceProject, "Test135", 10, 15);
        Slicer slicer = new Slicer(criterion);
        List<String> result = PDGTestUtil.getIdList(criterion.getDependencyGraph(), slicer.getNodes());
        
        assertEquals("2;4;5;6;7;8;9;12;13;14;15;16;17;18;20;22;23;26;27;28;29;38;39;"
                + "40;41;42;51;52;53;54;55;66;67;69;70;76;77",
                TestUtil.asStr(result));
    }
    
    @Test
    public void testSlice135_2() {
        SliceCriterion criterion = SliceTestUtil.getSliceCriterion(SliceProject, "Test135", 11, 15);
        Slicer slicer = new Slicer(criterion);
        List<String> result = PDGTestUtil.getIdList(criterion.getDependencyGraph(), slicer.getNodes());
        
        assertEquals("2;4;5;6;7;8;9;12;13;14;15;16;17;18;20;22;23;"
                + "32;33;34;35;43;44;45;46;47;57;58;59;60;61;66;67;69;70;78;79",
                TestUtil.asStr(result));
    }
    
    @Test
    public void testSlice136_1() {
        SliceCriterion criterion = SliceTestUtil.getSliceCriterion(SliceProject, "Test136", 13, 15);
        Slicer slicer = new Slicer(criterion);
        List<String> result = PDGTestUtil.getIdList(criterion.getDependencyGraph(), slicer.getNodes());
        
        assertEquals("2;4;5;6;7;9;10;13;14;15;16;17;18;20;21;22;23;24;25;27;35;38;39;"
                + "40;41;42;50;51;52;53;54;62;63;64;65;66",
                TestUtil.asStr(result));
    }
    
    @Test
    public void testSlice136_2() {
        SliceCriterion criterion = SliceTestUtil.getSliceCriterion(SliceProject, "Test136", 14, 15);
        Slicer slicer = new Slicer(criterion);
        List<String> result = PDGTestUtil.getIdList(criterion.getDependencyGraph(), slicer.getNodes());
        
        assertEquals("2;4;5;6;7;9;10;13;14;15;16;17;18;20;21;22;23;24;25;27;35;"
                + "44;45;46;47;48;55;56;57;58;59;68;69;70;71;72",
                TestUtil.asStr(result));
    }
    
    @Test
    public void testSlice137_1() {
        SliceCriterion criterion = SliceTestUtil.getSliceCriterion(SliceProject, "Test137", 8, 15);
        Slicer slicer = new Slicer(criterion);
        List<String> result = PDGTestUtil.getIdList(criterion.getDependencyGraph(), slicer.getNodes());
        
        assertEquals("2;4;5;6;8;9;12;13;14;15;16;17;19;20;21;22;23;24;26;34;35;36;37;38;"
                + "40;41;42;43;44;45;46;47;48",
                TestUtil.asStr(result));
    }
    
    @Test
    public void testSlice138_1() {
        SliceCriterion criterion = SliceTestUtil.getSliceCriterion(SliceProject, "Test138", 12, 18);
        Slicer slicer = new Slicer(criterion);
        List<String> result = PDGTestUtil.getIdList(criterion.getDependencyGraph(), slicer.getNodes());
        
        assertEquals("2;4;5;6;8;9;12;13;14;15;16;17;37;38;39;40;41;46;48;49;50;51;53;54;55;56",
                TestUtil.asStr(result));
    }
    
    @Test
    public void testSlice138_2() {
        SliceCriterion criterion = SliceTestUtil.getSliceCriterion(SliceProject, "Test138", 12, 13);
        Slicer slicer = new Slicer(criterion);
        List<String> result = PDGTestUtil.getIdList(criterion.getDependencyGraph(), slicer.getNodes());
        
        assertEquals("2;4;5;6;8;9;12;13;14;15;16;17;37;38;39;40;41;46;48;49;50;51;53;54;55;56;62",
                TestUtil.asStr(result));
    }
    
    @Test
    public void testSlice138_3() {
        SliceCriterion criterion = SliceTestUtil.getSliceCriterion(SliceProject, "Test138", 13, 21);
        Slicer slicer = new Slicer(criterion);
        List<String> result = PDGTestUtil.getIdList(criterion.getDependencyGraph(), slicer.getNodes());
        
        assertEquals("2;4;5;6;8;9;12;13;14;15;16;17;19;20;22;37;38;39;40;41;46;48;49;"
                + "50;51;53;54;55;56;63;64;65;66",
                TestUtil.asStr(result));
    }
    
    @Test
    public void testSlice138_4() {
        SliceCriterion criterion = SliceTestUtil.getSliceCriterion(SliceProject, "Test138", 14, 21);
        Slicer slicer = new Slicer(criterion);
        List<String> result = PDGTestUtil.getIdList(criterion.getDependencyGraph(), slicer.getNodes());
        
        assertEquals("2;4;5;6;8;9;12;13;14;15;16;17;19;20;22;37;38;39;40;41;46;48;49;"
                + "50;51;53;54;55;56;62;67;68;69;70",
                TestUtil.asStr(result));
    }
    
    @Test
    public void testSlice138_5() {
        SliceCriterion criterion = SliceTestUtil.getSliceCriterion(SliceProject, "Test138", 15, 21);
        Slicer slicer = new Slicer(criterion);
        List<String> result = PDGTestUtil.getIdList(criterion.getDependencyGraph(), slicer.getNodes());
        
        assertEquals("2;4;5;6;8;9;12;13;14;15;16;17;19;20;22;37;42;43;44;45;58;59;60;61;71;72;73;74",
                TestUtil.asStr(result));
    }
    
    @Test
    public void testSlice139_1() {
        SliceCriterion criterion = SliceTestUtil.getSliceCriterion(SliceProject, "Test139", 7, 12);
        Slicer slicer = new Slicer(criterion);
        List<String> result = PDGTestUtil.getIdList(criterion.getDependencyGraph(), slicer.getNodes());
        
        assertEquals("7;8;10;32;33;39;42;43;44;45",
                TestUtil.asStr(result));
    }
    
    @Test
    public void testSlice139_2() {
        SliceCriterion criterion = SliceTestUtil.getSliceCriterion(SliceProject, "PriceCode", 20, 13);
        Slicer slicer = new Slicer(criterion);
        List<String> result = PDGTestUtil.getIdList(criterion.getDependencyGraph(), slicer.getNodes());
        
        assertEquals("2;3;4",
                TestUtil.asStr(result));
    }
    
    @Test
    public void testSlice140_1() {
        SliceCriterion criterion = SliceTestUtil.getSliceCriterion(SliceProject, "S140", 19, 12);
        Slicer slicer = new Slicer(criterion);
        List<String> result = PDGTestUtil.getIdList(criterion.getDependencyGraph(), slicer.getNodes());
        
        assertEquals("14;15;18;20;21;22;24;27;28;34;39;50;51",
                TestUtil.asStr(result));
    }
    
    @Test
    public void testSlice140_2() {
        SliceCriterion criterion = SliceTestUtil.getSliceCriterion(SliceProject, "S140", 25, 12);
        Slicer slicer = new Slicer(criterion);
        List<String> result = PDGTestUtil.getIdList(criterion.getDependencyGraph(), slicer.getNodes());
        
        assertEquals("2;3;4;14;15;27;28;30;31;32;35;38",
                TestUtil.asStr(result));
    }
    
    @Test
    public void testSlice140_3() {
        SliceCriterion criterion = SliceTestUtil.getSliceCriterion(SliceProject, "S140", 31, 12);
        Slicer slicer = new Slicer(criterion);
        List<String> result = PDGTestUtil.getIdList(criterion.getDependencyGraph(), slicer.getNodes());
        
        assertEquals("14;15;41;42;47",
                TestUtil.asStr(result));
    }
    
    @Test
    public void testSlice141_1() {
        SliceCriterion criterion = SliceTestUtil.getSliceCriterion(SliceProject, "Test141", 11, 15);
        Slicer slicer = new Slicer(criterion);
        List<String> result = PDGTestUtil.getIdList(criterion.getDependencyGraph(), slicer.getNodes());
        
        assertEquals("5;7;8;9;14;15;16;17;24;25;26;27;28",
                TestUtil.asStr(result));
    }
    
    @Test
    public void testSlice141_2() {
        SliceCriterion criterion = SliceTestUtil.getSliceCriterion(SliceProject, "Test141", 12, 15);
        Slicer slicer = new Slicer(criterion);
        List<String> result = PDGTestUtil.getIdList(criterion.getDependencyGraph(), slicer.getNodes());
        
        assertEquals("5;11;12;13;18;19;20;21;30;31;32;33;34",
                TestUtil.asStr(result));
    }
    
    @Test
    public void testSlice141_3() {
        SliceCriterion criterion = SliceTestUtil.getSliceCriterion(SliceProject, "Test141", 18, 15);
        Slicer slicer = new Slicer(criterion);
        List<String> result = PDGTestUtil.getIdList(criterion.getDependencyGraph(), slicer.getNodes());
        
        assertEquals("30;31;32;33;34;36;42;43;44;45;46;47;48",
                TestUtil.asStr(result));
    }
    
    @Test
    public void testSlice141_4() {
        SliceCriterion criterion = SliceTestUtil.getSliceCriterion(SliceProject, "Test141", 19, 15);
        Slicer slicer = new Slicer(criterion);
        List<String> result = PDGTestUtil.getIdList(criterion.getDependencyGraph(), slicer.getNodes());
        
        assertEquals("24;25;26;27;28;36;38;39;40;49;50;51;52",
                TestUtil.asStr(result));
    }
    
    @Test
    public void testSlice142_1() {
        SliceCriterion criterion = SliceTestUtil.getSliceCriterion(SliceProject, "Test142", 7, 15);
        Slicer slicer = new Slicer(criterion);
        List<String> result = PDGTestUtil.getIdList(criterion.getDependencyGraph(), slicer.getNodes());
        
        assertEquals("5;7;8;9;12;13;14;15;22;23;24;25;26;28;29;31;35;37;38;40;51;52;57;59",
                TestUtil.asStr(result));
    }
    
    @Test
    public void testSlice142_2() {
        SliceCriterion criterion = SliceTestUtil.getSliceCriterion(SliceProject, "Test142", 8, 15);
        Slicer slicer = new Slicer(criterion);
        List<String> result = PDGTestUtil.getIdList(criterion.getDependencyGraph(), slicer.getNodes());
        
        assertEquals("5;7;8;9;10;16;17;18;19;22;23;24;25;26;28;29;30;31;32;35;36;"
                + "41;42;43;44;45;46;48;50;51;52;57;59;61;62",
                TestUtil.asStr(result));
    }
    
    @Test
    public void testSlice143_1() {
        SliceCriterion criterion = SliceTestUtil.getSliceCriterion(SliceProject, "Test143", 10, 15);
        Slicer slicer = new Slicer(criterion);
        List<String> result = PDGTestUtil.getIdList(criterion.getDependencyGraph(), slicer.getNodes());
        
        assertEquals("5;7;8;9;10;11;12;13;14;15;16;19;20;21;22;23;25;26;27;29;"
                + "30;33;34;35;36;37;38;40;41;42;43;44;45;47;51;53",
                TestUtil.asStr(result));
    }
    
    @Test
    public void testSlice144_1() {
        SliceCriterion criterion = SliceTestUtil.getSliceCriterion(SliceProject, "Test144", 9, 15);
        Slicer slicer = new Slicer(criterion);
        List<String> result = PDGTestUtil.getIdList(criterion.getDependencyGraph(), slicer.getNodes());
        
        assertEquals("5;6;7;8;9;11;12;13;14;15;16;17;18;19;20;53;55;56;57;58;"
                + "60;61;64;65;66;67;68;69;71;72;73;74;75;76;78",
                TestUtil.asStr(result));
    }
    
    @Test
    public void testSlice144_2() {
        SliceCriterion criterion = SliceTestUtil.getSliceCriterion(SliceProject, "Test144", 13, 15);
        Slicer slicer = new Slicer(criterion);
        List<String> result = PDGTestUtil.getIdList(criterion.getDependencyGraph(), slicer.getNodes());
        
        assertEquals("5;21;22;23;24;26;27;28;29;30;31;32;33;34;35;53;55;56;57;58;"
                + "60;61;64;65;66;67;68;69;71;72;73;74;75;76;78",
                TestUtil.asStr(result));
    }
    
    @Test
    public void testSlice144_3() {
        SliceCriterion criterion = SliceTestUtil.getSliceCriterion(SliceProject, "Test144", 17, 15);
        Slicer slicer = new Slicer(criterion);
        List<String> result = PDGTestUtil.getIdList(criterion.getDependencyGraph(), slicer.getNodes());
        
        assertEquals("5;36;37;38;39;41;42;43;44;45;46;47;48;49;50;53;55;56;57;58;"
                + "60;61;65;66;67;68;69;71;72;73;74;75;76;78;82;84;85;86;87;89;"
                + "90;91;92;95;96;97;98;99;100;102;103;104;105;106;107;108;110",
                TestUtil.asStr(result));
    }
    
    @Test
    public void testSlice145_1() {
        SliceCriterion criterion = SliceTestUtil.getSliceCriterion(SliceProject, "Test145", 9, 13);
        Slicer slicer = new Slicer(criterion);
        List<String> result = PDGTestUtil.getIdList(criterion.getDependencyGraph(), slicer.getNodes());
        
        assertEquals("2;6;22;24;25;26;27;29;30;31;32;33;34;49;50;51;52;53",
                TestUtil.asStr(result));
    }
    
    @Test
    public void testSlice145_2() {
        SliceCriterion criterion = SliceTestUtil.getSliceCriterion(SliceProject, "Test145", 10, 12);
        Slicer slicer = new Slicer(criterion);
        List<String> result = PDGTestUtil.getIdList(criterion.getDependencyGraph(), slicer.getNodes());
        
        assertEquals("2;6;7;8;10;15;16;22;24;25;26;27;29;30;31;32;33;34;35;36;37;38;49;50;51;52;53",
                TestUtil.asStr(result));
    }
    
    @Test
    public void testSlice145_3() {
        SliceCriterion criterion = SliceTestUtil.getSliceCriterion(SliceProject, "Test145", 11, 12);
        Slicer slicer = new Slicer(criterion);
        List<String> result = PDGTestUtil.getIdList(criterion.getDependencyGraph(), slicer.getNodes());
        
        assertEquals("2;6;11;12;14;22;24;25;26;27;29;30;31;32;33;34;39;40;41;42;49;50;51;52;53",
                TestUtil.asStr(result));
    }
    
    @Test
    public void testSlice145_4() {
        SliceCriterion criterion = SliceTestUtil.getSliceCriterion(SliceProject, "Test145", 12, 19);
        Slicer slicer = new Slicer(criterion);
        List<String> result = PDGTestUtil.getIdList(criterion.getDependencyGraph(), slicer.getNodes());
        
        assertEquals("2;6;22;24;25;26;27;29;30;43;44;45;46;49;50;51;52;53;56;57;59",
                TestUtil.asStr(result));
    }
    
    @Test
    public void testSlice146_1() {
        SliceCriterion criterion = SliceTestUtil.getSliceCriterion(SliceProject, "Test146", 8, 21);
        Slicer slicer = new Slicer(criterion);
        List<String> result = PDGTestUtil.getIdList(criterion.getDependencyGraph(), slicer.getNodes());
        
        assertEquals("5;7;8;9;10",
                TestUtil.asStr(result));
    }
    
    @Test
    public void testSlice146_2() {
        SliceCriterion criterion = SliceTestUtil.getSliceCriterion(SliceProject, "Test146", 9, 20);
        Slicer slicer = new Slicer(criterion);
        List<String> result = PDGTestUtil.getIdList(criterion.getDependencyGraph(), slicer.getNodes());
        
        assertEquals("5;7;8;9;10;11",
                TestUtil.asStr(result));
    }
    
    @Test
    public void testSlice146_3() {
        SliceCriterion criterion = SliceTestUtil.getSliceCriterion(SliceProject, "Test146", 10, 12);
        Slicer slicer = new Slicer(criterion);
        List<String> result = PDGTestUtil.getIdList(criterion.getDependencyGraph(), slicer.getNodes());
        
        assertEquals("5;6;7;8;9;10;11;12;13;14;15",
                TestUtil.asStr(result));
    }
    
    @Test
    public void testSlice146_4() {
        SliceCriterion criterion = SliceTestUtil.getSliceCriterion(SliceProject, "Test146", 12, 12);
        Slicer slicer = new Slicer(criterion);
        List<String> result = PDGTestUtil.getIdList(criterion.getDependencyGraph(), slicer.getNodes());
        
        assertEquals("5;6;7;8;9;10;11;12;13;14;15;16",
                TestUtil.asStr(result));
    }
    
    @Test
    public void testTest200_1() {
        SliceCriterion criterion = SliceTestUtil.getSliceCriterion(SliceProject, "Test200", 11, 12);
        Slicer slicer = new Slicer(criterion);
        List<String> result = PDGTestUtil.getIdList(criterion.getDependencyGraph(), slicer.getNodes());
        
        assertEquals("5;6;7",
                 TestUtil.asStr(result));
    }
    
    @Test
    public void testTest200_2() {
        SliceCriterion criterion = SliceTestUtil.getSliceCriterion(SliceProject, "Test200", 15, 12);
        Slicer slicer = new Slicer(criterion);
        List<String> result = PDGTestUtil.getIdList(criterion.getDependencyGraph(), slicer.getNodes());
        
        assertEquals("5;6;8",
                TestUtil.asStr(result));
    }
    
    @Test
    public void testTest200_3() {
        SliceCriterion criterion = SliceTestUtil.getSliceCriterion(SliceProject, "Test200", 19, 12);
        Slicer slicer = new Slicer(criterion);
        List<String> result = PDGTestUtil.getIdList(criterion.getDependencyGraph(), slicer.getNodes());
        
        assertEquals("5;6;7;9",
                TestUtil.asStr(result));
    }
    
    @Test
    public void testCustomer1() {
        SliceCriterion criterion = SliceTestUtil.getSliceCriterion(SliceProject, "Customer", 18, 12);
        Slicer slicer = new Slicer(criterion);
        List<String> result = PDGTestUtil.getIdList(criterion.getDependencyGraph(), slicer.getNodes());
        
        assertEquals("13;14;15;16;17;37;38;39;40;41;42;43;45;46;47;49;50;58;59;"
                + "60;63;64;65;66;67;69;70;71;72;73;155;156;157;158;159;160;161;162;163;164;166;178;179",
                TestUtil.asStr(result));
    }
    
    @Test
    public void testCustomer2() {
        SliceCriterion criterion = SliceTestUtil.getSliceCriterion(SliceProject, "Customer", 23, 31);
        Slicer slicer = new Slicer(criterion);
        List<String> result = PDGTestUtil.getIdList(criterion.getDependencyGraph(), slicer.getNodes());
        
        assertEquals("155;156",
                TestUtil.asStr(result));
    }
    
    @Test
    public void testCustomer3() {
        SliceCriterion criterion = SliceTestUtil.getSliceCriterion(SliceProject, "Customer", 24, 12);
        Slicer slicer = new Slicer(criterion);
        List<String> result = PDGTestUtil.getIdList(criterion.getDependencyGraph(), slicer.getNodes());
        
        assertEquals("155;157",
                TestUtil.asStr(result));
    }
    
    @Test
    public void testCustomer4() {
        SliceCriterion criterion = SliceTestUtil.getSliceCriterion(SliceProject, "Customer", 18, 31);
        Slicer slicer = new Slicer(criterion);
        List<String> result = PDGTestUtil.getIdList(criterion.getDependencyGraph(), slicer.getNodes());
        
        assertEquals("13;14;15;16;17;58;59;60",
                TestUtil.asStr(result));
    }
    
    @Test
    public void testCustomer5() {
        SliceCriterion criterion = SliceTestUtil.getSliceCriterion(SliceProject, "Customer", 20, 15);
        Slicer slicer = new Slicer(criterion);
        List<String> result = PDGTestUtil.getIdList(criterion.getDependencyGraph(), slicer.getNodes());
        
        assertEquals("13;14;15;16;17;58;59;60;175;176",
                TestUtil.asStr(result));
    }
    
    @Test
    public void testCustomer6() {
        SliceCriterion criterion = SliceTestUtil.getSliceCriterion(SliceProject, "Customer", 26, 12);
        Slicer slicer = new Slicer(criterion);
        List<String> result = PDGTestUtil.getIdList(criterion.getDependencyGraph(), slicer.getNodes());
        
        assertEquals("13;14;15;16;17;37;38;39;40;41;42;43;45;46;47;49;"
                + "50;155;156;157;158;159;160;161;162;163;178;179",
                TestUtil.asStr(result));
    }
    
    @Test
    public void testCustomerTest1() {
        SliceCriterion criterion = SliceTestUtil.getSliceCriterion(SliceProject, "CustomerTest", 11, 44);
        Slicer slicer = new Slicer(criterion);
        List<String> result = PDGTestUtil.getIdList(criterion.getDependencyGraph(), slicer.getNodes());
        
        assertEquals("2;4;5;6;8;9;12;13;14;15;16;17;30;31;32;33;34;36;46;47;49;"
                + "50;77;88;89;90;91;92;93;94;95;96;97;99;100;101;102;2747;2748",
                TestUtil.asStr(result));
    }
    
    @Test
    public void testCustomerTest2() {
        SliceCriterion criterion = SliceTestUtil.getSliceCriterion(SliceProject, "CustomerTest", 12, 14);
        Slicer slicer = new Slicer(criterion);
        List<String> result = PDGTestUtil.getIdList(criterion.getDependencyGraph(), slicer.getNodes());
        
        assertEquals("2;4;5;6;8;9;12;13;14;15;16;17;30;31;32;33;34;36;46;47;49;"
                + "50;77;88;89;90;91;92;93;94;95;96;97;99;100;101;102;108;2747;2748",
                TestUtil.asStr(result));
    }
    
    @Test
    public void testCustomerTest3() {
        SliceCriterion criterion = SliceTestUtil.getSliceCriterion(SliceProject, "CustomerTest", 24, 44);
        Slicer slicer = new Slicer(criterion);
        List<String> result = PDGTestUtil.getIdList(criterion.getDependencyGraph(), slicer.getNodes());
        
        assertEquals("2;4;5;6;8;9;12;13;14;15;16;17;30;31;32;33;34;36;46;47;49;"
                + "50;111;122;123;124;125;126;127;128;129;130;131;132;133;134;135;136;137;139;"
                + "140;141;142;144;145;146;147;2754;2755",
                TestUtil.asStr(result));
    }
}
