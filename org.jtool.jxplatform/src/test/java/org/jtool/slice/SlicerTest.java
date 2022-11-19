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
        
        assertEquals("4;5",
                TestUtil.asStr(result));
    }
    
    @Test
    public void testSlice101_2() {
        SliceCriterion criterion = SliceTestUtil.getSliceCriterion(SliceProject, "Test101", 6, 12);
        Slicer slicer = new Slicer(criterion);
        List<String> result = PDGTestUtil.getIdList(criterion.getDependencyGraph(), slicer.getNodes());
        
        assertEquals("4;5;6",
                TestUtil.asStr(result));
    }
    
    @Test
    public void testSlice101_3() {
        SliceCriterion criterion = SliceTestUtil.getSliceCriterion(SliceProject, "Test101", 7, 12);
        Slicer slicer = new Slicer(criterion);
        List<String> result = PDGTestUtil.getIdList(criterion.getDependencyGraph(), slicer.getNodes());
        
        assertEquals("4;5;7;13;14",
                TestUtil.asStr(result));
    }
    
    @Test
    public void testSlice102_1() {
        SliceCriterion criterion = SliceTestUtil.getSliceCriterion(SliceProject, "Test102", 7, 12);
        Slicer slicer = new Slicer(criterion);
        List<String> result = PDGTestUtil.getIdList(criterion.getDependencyGraph(), slicer.getNodes());
        
        assertEquals("4;5;6;7;8;9;16;25;26;27;29",
                TestUtil.asStr(result));
    }
    
    @Test
    public void testSlice102_2() {
        SliceCriterion criterion = SliceTestUtil.getSliceCriterion(SliceProject, "Test102", 8, 12);
        Slicer slicer = new Slicer(criterion);
        List<String> result = PDGTestUtil.getIdList(criterion.getDependencyGraph(), slicer.getNodes());
        
        assertEquals("4;10;17",
                TestUtil.asStr(result));
    }
    
    @Test
    public void testSlice102_3() {
        SliceCriterion criterion = SliceTestUtil.getSliceCriterion(SliceProject, "Test102", 9, 12);
        Slicer slicer = new Slicer(criterion);
        List<String> result = PDGTestUtil.getIdList(criterion.getDependencyGraph(), slicer.getNodes());
        
        assertEquals("4;10;11;12;13;14;15;18;25;26;27;29",
                TestUtil.asStr(result));
    }
    
    @Test
    public void testSlice102_4() {
        SliceCriterion criterion = SliceTestUtil.getSliceCriterion(SliceProject, "Test102", 10, 27);
        Slicer slicer = new Slicer(criterion);
        List<String> result = PDGTestUtil.getIdList(criterion.getDependencyGraph(), slicer.getNodes());
        
        assertEquals("4;10;11;12;13;14;15;25;26;27;29",
                TestUtil.asStr(result));
    }
    
    @Test
    public void testSlice103_1() {
        SliceCriterion criterion = SliceTestUtil.getSliceCriterion(SliceProject, "Test103", 7, 12);
        Slicer slicer = new Slicer(criterion);
        List<String> result = PDGTestUtil.getIdList(criterion.getDependencyGraph(), slicer.getNodes());
        
        assertEquals("4;6;7;58;59",
                TestUtil.asStr(result));
    }
    
    @Test
    public void testSlice103_2() {
        SliceCriterion criterion = SliceTestUtil.getSliceCriterion(SliceProject, "Test103", 10, 12);
        Slicer slicer = new Slicer(criterion);
        List<String> result = PDGTestUtil.getIdList(criterion.getDependencyGraph(), slicer.getNodes());
        
        assertEquals("4;14;15;16;18;41;42;43;47;58;59",
                TestUtil.asStr(result));
    }
    
    @Test
    public void testSlice103_3() {
        SliceCriterion criterion = SliceTestUtil.getSliceCriterion(SliceProject, "Test103", 11, 12);
        Slicer slicer = new Slicer(criterion);
        List<String> result =PDGTestUtil.getIdList(criterion.getDependencyGraph(), slicer.getNodes());
        
        assertEquals("4;14;15;16;19;20;21;22;41;42;43;47;48;49;51;58;59",
                TestUtil.asStr(result));
    }
    
    @Test
    public void testSlice103_4() {
        SliceCriterion criterion = SliceTestUtil.getSliceCriterion(SliceProject, "Test103", 12, 12);
        Slicer slicer = new Slicer(criterion);
        List<String> result =PDGTestUtil.getIdList(criterion.getDependencyGraph(), slicer.getNodes());
        
        assertEquals("4;14;15;16;23;24;25;26;41;42;43;47;48;49;51;58;59",
                TestUtil.asStr(result));
    }
    
    @Test
    public void testSlice103_5() {
        SliceCriterion criterion = SliceTestUtil.getSliceCriterion(SliceProject, "Test103", 15, 12);
        Slicer slicer = new Slicer(criterion);
        List<String> result =PDGTestUtil.getIdList(criterion.getDependencyGraph(), slicer.getNodes());
        
        assertEquals("4;14;15;16;28;29;32;33;35;36;37;38;41;42;43;47;48;49;51;52;53;56;57;58;59",
                TestUtil.asStr(result));
    }
    
    @Test
    public void testSlice104_1() {
        SliceCriterion criterion = SliceTestUtil.getSliceCriterion(SliceProject, "Test104", 5, 8);
        Slicer slicer = new Slicer(criterion);
        List<String> result =PDGTestUtil.getIdList(criterion.getDependencyGraph(), slicer.getNodes());
        
        assertEquals("4;5;8",
                TestUtil.asStr(result));
    }
    
    @Test
    public void testSlice104_2() {
        SliceCriterion criterion = SliceTestUtil.getSliceCriterion(SliceProject, "Test104", 6, 8);
        Slicer slicer = new Slicer(criterion);
        List<String> result =PDGTestUtil.getIdList(criterion.getDependencyGraph(), slicer.getNodes());
        
        assertEquals("4;6;9",
                TestUtil.asStr(result));
    }
    
    @Test
    public void testSlice104_3() {
        SliceCriterion criterion = SliceTestUtil.getSliceCriterion(SliceProject, "Test104", 7, 8);
        Slicer slicer = new Slicer(criterion);
        List<String> result =PDGTestUtil.getIdList(criterion.getDependencyGraph(), slicer.getNodes());
        
        assertEquals("4;7;10",
                TestUtil.asStr(result));
    }
    
    @Test
    public void testSlice104_4() {
        SliceCriterion criterion = SliceTestUtil.getSliceCriterion(SliceProject, "Test104", 8, 12);
        Slicer slicer = new Slicer(criterion);
        List<String> result =PDGTestUtil.getIdList(criterion.getDependencyGraph(), slicer.getNodes());
        
        assertEquals("4;6;7;9;10;11",
                TestUtil.asStr(result));
    }
    
    @Test
    public void testSlice104_5() {
        SliceCriterion criterion = SliceTestUtil.getSliceCriterion(SliceProject, "Test104", 9, 8);
        Slicer slicer = new Slicer(criterion);
        List<String> result =PDGTestUtil.getIdList(criterion.getDependencyGraph(), slicer.getNodes());
        
        assertEquals("4;5;6;8;12",
                TestUtil.asStr(result));
    }
    
    @Test
    public void testSlice104_6() {
        SliceCriterion criterion = SliceTestUtil.getSliceCriterion(SliceProject, "Test104", 10, 8);
        Slicer slicer = new Slicer(criterion);
        List<String> result =PDGTestUtil.getIdList(criterion.getDependencyGraph(), slicer.getNodes());
        
        assertEquals("4;5;7;8;13",
                TestUtil.asStr(result));
    }
    
    @Test
    public void testSlice105_1() {
        SliceCriterion criterion = SliceTestUtil.getSliceCriterion(SliceProject, "Test105", 6, 12);
        Slicer slicer = new Slicer(criterion);
        List<String> result =PDGTestUtil.getIdList(criterion.getDependencyGraph(), slicer.getNodes());
        
        assertEquals("4;5;6;7;8;9;10;14;15;17;19",
                TestUtil.asStr(result));
    }
    
    @Test
    public void testSlice105_2() {
        SliceCriterion criterion = SliceTestUtil.getSliceCriterion(SliceProject, "Test105", 7, 12);
        Slicer slicer = new Slicer(criterion);
        List<String> result =PDGTestUtil.getIdList(criterion.getDependencyGraph(), slicer.getNodes());
        
        assertEquals("4;6;7;8;11;14;15;16;20;21;22",
                TestUtil.asStr(result));
    }
    
    @Test
    public void testSlice106_1() {
        SliceCriterion criterion = SliceTestUtil.getSliceCriterion(SliceProject, "Test106", 7, 12);
        Slicer slicer = new Slicer(criterion);
        List<String> result =PDGTestUtil.getIdList(criterion.getDependencyGraph(), slicer.getNodes());
        
        assertEquals("4;5;6;7;8;9;10;11;15;16;18;20",
                TestUtil.asStr(result));
    }
    
    @Test
    public void testSlice106_2() {
        SliceCriterion criterion = SliceTestUtil.getSliceCriterion(SliceProject, "Test106", 8, 12);
        Slicer slicer = new Slicer(criterion);
        List<String> result =PDGTestUtil.getIdList(criterion.getDependencyGraph(), slicer.getNodes());
        
        assertEquals("4;7;8;9;12;15;16;17;21;22;23",
                TestUtil.asStr(result));
    }
    
    @Test
    public void testSlice107_1() {
        SliceCriterion criterion = SliceTestUtil.getSliceCriterion(SliceProject, "Test107", 10, 12);
        Slicer slicer = new Slicer(criterion);
        List<String> result =PDGTestUtil.getIdList(criterion.getDependencyGraph(), slicer.getNodes());
        
        assertEquals("4;6;7;9",
                TestUtil.asStr(result));
    }
    
    @Test
    public void testSlice107_2() {
        SliceCriterion criterion = SliceTestUtil.getSliceCriterion(SliceProject, "Test107", 11, 12);
        Slicer slicer = new Slicer(criterion);
        List<String> result =PDGTestUtil.getIdList(criterion.getDependencyGraph(), slicer.getNodes());
        
        assertEquals("4;6;7;8;10",
                TestUtil.asStr(result));
    }
    
    @Test
    public void testSlice108_1() {
        SliceCriterion criterion = SliceTestUtil.getSliceCriterion(SliceProject, "Test108", 12, 12);
        Slicer slicer = new Slicer(criterion);
        List<String> result =PDGTestUtil.getIdList(criterion.getDependencyGraph(), slicer.getNodes());
        
        assertEquals("4;5;6;8;9;12",
                TestUtil.asStr(result));
    }
    
    @Test
    public void testSlice108_2() {
        SliceCriterion criterion = SliceTestUtil.getSliceCriterion(SliceProject, "Test108", 13, 12);
        Slicer slicer = new Slicer(criterion);
        List<String> result =PDGTestUtil.getIdList(criterion.getDependencyGraph(), slicer.getNodes());
        
        assertEquals("4;5;7;8;10;13",
                TestUtil.asStr(result));
    }
    
    @Test
    public void testSlice109_1() {
        SliceCriterion criterion = SliceTestUtil.getSliceCriterion(SliceProject, "Test109", 11, 12);
        Slicer slicer = new Slicer(criterion);
        List<String> result =PDGTestUtil.getIdList(criterion.getDependencyGraph(), slicer.getNodes());
        
        assertEquals("4;5;6;8;9;12",
                TestUtil.asStr(result));
    }
    
    @Test
    public void testSlice109_2() {
        SliceCriterion criterion = SliceTestUtil.getSliceCriterion(SliceProject, "Test109", 12, 12);
        Slicer slicer = new Slicer(criterion);
        List<String> result =PDGTestUtil.getIdList(criterion.getDependencyGraph(), slicer.getNodes());
        
        assertEquals("4;5;7;8;10;13",
                TestUtil.asStr(result));
    }
    
    @Test
    public void testSlice110_1() {
        SliceCriterion criterion = SliceTestUtil.getSliceCriterion(SliceProject, "Test110", 9, 12);
        Slicer slicer = new Slicer(criterion);
        List<String> result =PDGTestUtil.getIdList(criterion.getDependencyGraph(), slicer.getNodes());
        
        assertEquals("4;5;7;8;9",
                TestUtil.asStr(result));
    }
    
    @Test
    public void testSlice110_2() {
        SliceCriterion criterion = SliceTestUtil.getSliceCriterion(SliceProject, "Test110", 10, 12);
        Slicer slicer = new Slicer(criterion);
        List<String> result =PDGTestUtil.getIdList(criterion.getDependencyGraph(), slicer.getNodes());
        
        assertEquals("4;6;10",
                TestUtil.asStr(result));
    }
    
    @Test
    public void testSlice110_3() {
        SliceCriterion criterion = SliceTestUtil.getSliceCriterion(SliceProject, "Test110", 7, 16);
        Slicer slicer = new Slicer(criterion);
        List<String> result =PDGTestUtil.getIdList(criterion.getDependencyGraph(), slicer.getNodes());
        
        assertEquals("4;5;7;8",
                TestUtil.asStr(result));
    }
    
    @Test
    public void testSlice111_1() {
        SliceCriterion criterion = SliceTestUtil.getSliceCriterion(SliceProject, "Test111", 15, 12);
        Slicer slicer = new Slicer(criterion);
        List<String> result =PDGTestUtil.getIdList(criterion.getDependencyGraph(), slicer.getNodes());
        
        assertEquals("4;5;6;8;9;10;16",
                TestUtil.asStr(result));
    }
    
    @Test
    public void testSlice111_2() {
        SliceCriterion criterion = SliceTestUtil.getSliceCriterion(SliceProject, "Test111", 16, 12);
        Slicer slicer = new Slicer(criterion);
        List<String> result =PDGTestUtil.getIdList(criterion.getDependencyGraph(), slicer.getNodes());
        
        assertEquals("4;5;7;8;9;12;13;17",
                TestUtil.asStr(result));
    }
    
    @Test
    public void testSlice112_1() {
        SliceCriterion criterion = SliceTestUtil.getSliceCriterion(SliceProject, "Test112", 17, 12);
        Slicer slicer = new Slicer(criterion);
        List<String> result =PDGTestUtil.getIdList(criterion.getDependencyGraph(), slicer.getNodes());
        
        assertEquals("4;5;6;8;11;12;18",
                TestUtil.asStr(result));
    }
    
    @Test
    public void testSlice112_2() {
        SliceCriterion criterion = SliceTestUtil.getSliceCriterion(SliceProject, "Test112", 18, 12);
        Slicer slicer = new Slicer(criterion);
        List<String> result =PDGTestUtil.getIdList(criterion.getDependencyGraph(), slicer.getNodes());
        
        assertEquals("4;5;7;8;11;14;15;19",
                TestUtil.asStr(result));
    }
    
    @Test
    public void testSlice112_3() {
        SliceCriterion criterion = SliceTestUtil.getSliceCriterion(SliceProject, "Test112", 19, 12);
        Slicer slicer = new Slicer(criterion);
        List<String> result =PDGTestUtil.getIdList(criterion.getDependencyGraph(), slicer.getNodes());
        
        assertEquals("4;5;9;10;20",
                TestUtil.asStr(result));
    }
    
    @Test
    public void testSlice113_1() {
        SliceCriterion criterion = SliceTestUtil.getSliceCriterion(SliceProject, "Test113", 12, 12);
        Slicer slicer = new Slicer(criterion);
        List<String> result =PDGTestUtil.getIdList(criterion.getDependencyGraph(), slicer.getNodes());
        
        assertEquals("4;5;7;9;10;12",
                TestUtil.asStr(result));
    }
    
    @Test
    public void testSlice113_2() {
        SliceCriterion criterion = SliceTestUtil.getSliceCriterion(SliceProject, "Test113", 13, 12);
        Slicer slicer = new Slicer(criterion);
        List<String> result =PDGTestUtil.getIdList(criterion.getDependencyGraph(), slicer.getNodes());
        
        assertEquals("4;5;6;7;8;13",
                TestUtil.asStr(result));
    }
    
    @Test
    public void testSlice114_1() {
        SliceCriterion criterion = SliceTestUtil.getSliceCriterion(SliceProject, "Test114", 12, 12);
        Slicer slicer = new Slicer(criterion);
        List<String> result =PDGTestUtil.getIdList(criterion.getDependencyGraph(), slicer.getNodes());
        
        assertEquals("4;5;7;8;9;12",
                TestUtil.asStr(result));
    }
    
    @Test
    public void testSlice114_2() {
        SliceCriterion criterion = SliceTestUtil.getSliceCriterion(SliceProject, "Test114", 13, 12);
        Slicer slicer = new Slicer(criterion);
        List<String> result =PDGTestUtil.getIdList(criterion.getDependencyGraph(), slicer.getNodes());
        
        assertEquals("4;5;6;7;10;13",
                TestUtil.asStr(result));
    }
    
    @Test
    public void testSlice115_1() {
        SliceCriterion criterion = SliceTestUtil.getSliceCriterion(SliceProject, "Test115", 11, 12);
        Slicer slicer = new Slicer(criterion);
        List<String> result =PDGTestUtil.getIdList(criterion.getDependencyGraph(), slicer.getNodes());
        
        assertEquals("4;5;6;8;9;10;12;13",
                TestUtil.asStr(result));
    }
    
    @Test
    public void testSlice115_2() {
        SliceCriterion criterion = SliceTestUtil.getSliceCriterion(SliceProject, "Test115", 12, 12);
        Slicer slicer = new Slicer(criterion);
        List<String> result =PDGTestUtil.getIdList(criterion.getDependencyGraph(), slicer.getNodes());
        
        assertEquals("4;5;7;8;9;11;12;14",
                TestUtil.asStr(result));
    }
    
    @Test
    public void testSlice116_1() {
        SliceCriterion criterion = SliceTestUtil.getSliceCriterion(SliceProject, "Test116", 15, 12);
        Slicer slicer = new Slicer(criterion);
        List<String> result =PDGTestUtil.getIdList(criterion.getDependencyGraph(), slicer.getNodes());
        
        assertEquals("4;5;6;8;9;10;11;16;17",
                TestUtil.asStr(result));
    }
    
    @Test
    public void testSlice116_2() {
        SliceCriterion criterion = SliceTestUtil.getSliceCriterion(SliceProject, "Test116", 16, 12);
        Slicer slicer = new Slicer(criterion);
        List<String> result =PDGTestUtil.getIdList(criterion.getDependencyGraph(), slicer.getNodes());
        
        assertEquals("4;5;7;8;9;13;14;16;18",
                TestUtil.asStr(result));
    }
    
    @Test
    public void testSlice117_1() {
        SliceCriterion criterion = SliceTestUtil.getSliceCriterion(SliceProject, "Test117", 9, 12);
        Slicer slicer = new Slicer(criterion);
        List<String> result =PDGTestUtil.getIdList(criterion.getDependencyGraph(), slicer.getNodes());
        
        assertEquals("4;5;7;9;11;12;13",
                TestUtil.asStr(result));
    }
    
    @Test
    public void testSlice117_2() {
        SliceCriterion criterion = SliceTestUtil.getSliceCriterion(SliceProject, "Test117", 10, 12);
        Slicer slicer = new Slicer(criterion);
        List<String> result =PDGTestUtil.getIdList(criterion.getDependencyGraph(), slicer.getNodes());
        
        assertEquals("4;6;7;9;10;11;14",
                TestUtil.asStr(result));
    }
    
    @Test
    public void testSlice118_1() {
        SliceCriterion criterion = SliceTestUtil.getSliceCriterion(SliceProject, "Test118", 4, 12);
        Slicer slicer = new Slicer(criterion);
        List<String> result =PDGTestUtil.getIdList(criterion.getDependencyGraph(), slicer.getNodes());
        
        assertEquals("4;5;6;7;11;70;74;76",
                TestUtil.asStr(result));
    }
    
    @Test
    public void testSlice118_2() {
        SliceCriterion criterion = SliceTestUtil.getSliceCriterion(SliceProject, "Test118", 5, 12);
        Slicer slicer = new Slicer(criterion);
        List<String> result =PDGTestUtil.getIdList(criterion.getDependencyGraph(), slicer.getNodes());
        
        assertEquals("4;12;13;14;15;18;77;78;81;83",
                TestUtil.asStr(result));
    }
    
    @Test
    public void testSlice118_3() {
        SliceCriterion criterion = SliceTestUtil.getSliceCriterion(SliceProject, "Test118", 6, 12);
        Slicer slicer = new Slicer(criterion);
        List<String> result =PDGTestUtil.getIdList(criterion.getDependencyGraph(), slicer.getNodes());
        
        assertEquals("4;19;20;21;23;25;84;86;88;90",
                TestUtil.asStr(result));
    }
    
    @Test
    public void testSlice118_4() {
        SliceCriterion criterion = SliceTestUtil.getSliceCriterion(SliceProject, "Test118", 7, 12);
        Slicer slicer = new Slicer(criterion);
        List<String> result =PDGTestUtil.getIdList(criterion.getDependencyGraph(), slicer.getNodes());
        
        assertEquals("4;26;27;28;31;32;91;94;95;97",
                TestUtil.asStr(result));
    }
    
    @Test
    public void testSlice118_5() {
        SliceCriterion criterion = SliceTestUtil.getSliceCriterion(SliceProject, "Test118", 8, 12);
        Slicer slicer = new Slicer(criterion);
        List<String> result =PDGTestUtil.getIdList(criterion.getDependencyGraph(), slicer.getNodes());
        
        assertEquals("4;33;34;35;36;37;39;98;99;100;102;104",
                TestUtil.asStr(result));
    }
    
    @Test
    public void testSlice118_6() {
        SliceCriterion criterion = SliceTestUtil.getSliceCriterion(SliceProject, "Test118", 9, 12);
        Slicer slicer = new Slicer(criterion);
        List<String> result =PDGTestUtil.getIdList(criterion.getDependencyGraph(), slicer.getNodes());
        
        assertEquals("4;40;41;42;44;45;46;105;107;108;109;111",
                TestUtil.asStr(result));
    }
    
    @Test
    public void testSlice118_7() {
        SliceCriterion criterion = SliceTestUtil.getSliceCriterion(SliceProject, "Test118", 10, 12);
        Slicer slicer = new Slicer(criterion);
        List<String> result =PDGTestUtil.getIdList(criterion.getDependencyGraph(), slicer.getNodes());
        
        assertEquals("4;47;48;49;50;52;53;112;113;115;116;118",
                TestUtil.asStr(result));
    }
    
    @Test
    public void testSlice118_8() {
        SliceCriterion criterion = SliceTestUtil.getSliceCriterion(SliceProject, "Test118", 11, 12);
        Slicer slicer = new Slicer(criterion);
        List<String> result =PDGTestUtil.getIdList(criterion.getDependencyGraph(), slicer.getNodes());
        
        assertEquals("4;54;55;56;57;58;59;60;119;120;121;122;123;125",
                TestUtil.asStr(result));
    }
    
    @Test
    public void testSlice118_9() {
        SliceCriterion criterion = SliceTestUtil.getSliceCriterion(SliceProject, "Test118", 12, 12);
        Slicer slicer = new Slicer(criterion);
        List<String> result =PDGTestUtil.getIdList(criterion.getDependencyGraph(), slicer.getNodes());
        
        assertEquals("4;61;62;63;67;126;130;132",
                TestUtil.asStr(result));
    }
    
    @Test
    public void testSlice119_1() {
        SliceCriterion criterion = SliceTestUtil.getSliceCriterion(SliceProject, "Test119", 8, 12);
        Slicer slicer = new Slicer(criterion);
        List<String> result =PDGTestUtil.getIdList(criterion.getDependencyGraph(), slicer.getNodes());
        
        assertEquals("1;3;4;5;6;9;10;12;13;14;23;25;26;27;28;30;31;32;34;35;36;37;54",
                TestUtil.asStr(result));
    }
    
    @Test
    public void testSlice119_2() {
        SliceCriterion criterion = SliceTestUtil.getSliceCriterion(SliceProject, "Test119", 8, 16);
        Slicer slicer = new Slicer(criterion);
        List<String> result =PDGTestUtil.getIdList(criterion.getDependencyGraph(), slicer.getNodes());
        
        assertEquals("1;3;4;5;6;23;25;26;27;28;30;31;32;54",
                TestUtil.asStr(result));
    }
    
    @Test
    public void testSlice119_3() {
        SliceCriterion criterion = SliceTestUtil.getSliceCriterion(SliceProject, "Test119", 10, 12); 
        Slicer slicer = new Slicer(criterion);
        List<String> result =PDGTestUtil.getIdList(criterion.getDependencyGraph(), slicer.getNodes());
        
        assertEquals("1;3;4;5;6;9;10;12;13;14;23;25;26;27;28;39;40;41;43;44;45;46;55",
                TestUtil.asStr(result));
    }
    
    @Test
    public void testSlice119_4() {
        SliceCriterion criterion = SliceTestUtil.getSliceCriterion(SliceProject, "Test119", 9, 8); 
        Slicer slicer = new Slicer(criterion);
        List<String> result =PDGTestUtil.getIdList(criterion.getDependencyGraph(), slicer.getNodes());
        
        assertEquals("1;3;4;5;6;23;25;26;27;28;30;31;32;54",
                TestUtil.asStr(result));
    }
    
    @Test
    public void testSlice119_5() {
        SliceCriterion criterion = SliceTestUtil.getSliceCriterion(SliceProject, "Test119", 11, 12); 
        Slicer slicer = new Slicer(criterion);
        List<String> result =PDGTestUtil.getIdList(criterion.getDependencyGraph(), slicer.getNodes());
        
        assertEquals("1;3;4;5;6;13;14;16;17;23;24;25;26;27;28;39;40;41;47;55;69;70",
                TestUtil.asStr(result));
    }
    
    @Test
    public void testSlice119_6() {
        SliceCriterion criterion = SliceTestUtil.getSliceCriterion(SliceProject, "Test119", 11, 16); 
        Slicer slicer = new Slicer(criterion);
        List<String> result =PDGTestUtil.getIdList(criterion.getDependencyGraph(), slicer.getNodes());
        
        assertEquals("1;3;4;5;6;23;25;26;27;28;30;31;32;39;40;41;54;55",
                TestUtil.asStr(result));
    }
    
    @Test
    public void testSlice119_7() {
        SliceCriterion criterion = SliceTestUtil.getSliceCriterion(SliceProject, "Test119", 11, 18); 
        Slicer slicer = new Slicer(criterion);
        List<String> result =PDGTestUtil.getIdList(criterion.getDependencyGraph(), slicer.getNodes());
        
        assertEquals("1;3;4;5;6;13;14;23;25;26;27;28;39;40;41;55",
                TestUtil.asStr(result));
    }
    
    @Test
    public void testSlice119_8() {
        SliceCriterion criterion = SliceTestUtil.getSliceCriterion(SliceProject, "Test119", 12, 12); 
        Slicer slicer = new Slicer(criterion);
        List<String> result =PDGTestUtil.getIdList(criterion.getDependencyGraph(), slicer.getNodes());
        
        assertEquals("23;24;48;49;50;51;56;57;59;69;70",
                TestUtil.asStr(result));
    }
    
    @Test
    public void testSlice120_1() {
        SliceCriterion criterion = SliceTestUtil.getSliceCriterion(SliceProject, "Test120", 5, 12);
        Slicer slicer = new Slicer(criterion);
        List<String> result =PDGTestUtil.getIdList(criterion.getDependencyGraph(), slicer.getNodes());
        
        assertEquals("14;19;20;21;24;45;48;50",
                TestUtil.asStr(result));
    }
    
    @Test
    public void testSlice120_2() {
        SliceCriterion criterion = SliceTestUtil.getSliceCriterion(SliceProject, "Test120", 6, 12);
        Slicer slicer = new Slicer(criterion);
        List<String> result =PDGTestUtil.getIdList(criterion.getDependencyGraph(), slicer.getNodes());
        
        assertEquals("1;3;4;5;7;8;14;15;16;17;18;25;26;27;28;30;51;52;54;56",
                TestUtil.asStr(result));
    }
    
    @Test
    public void testSlice120_3() {
        SliceCriterion criterion = SliceTestUtil.getSliceCriterion(SliceProject, "Test120", 7, 12);
        Slicer slicer = new Slicer(criterion);
        List<String> result =PDGTestUtil.getIdList(criterion.getDependencyGraph(), slicer.getNodes());
        
        assertEquals("1;3;4;5;7;8;14;15;16;17;18;31;32;33;35;36;57;59;60;62",
                TestUtil.asStr(result));
    }
    
    @Test
    public void testSlice120_4() {
        SliceCriterion criterion = SliceTestUtil.getSliceCriterion(SliceProject, "Test120", 8, 12);
        Slicer slicer = new Slicer(criterion);
        List<String> result =PDGTestUtil.getIdList(criterion.getDependencyGraph(), slicer.getNodes());
        
        assertEquals("1;3;4;5;7;8;14;15;16;17;18;37;38;39;40;41;42;63;64;65;66;68",
                TestUtil.asStr(result));
    }
    
    @Test
    public void testSlice121_1() {
        SliceCriterion criterion = SliceTestUtil.getSliceCriterion(SliceProject, "Test121", 10, 12);
        Slicer slicer = new Slicer(criterion);
        List<String> result =PDGTestUtil.getIdList(criterion.getDependencyGraph(), slicer.getNodes());
        
        assertEquals("15;16;18",
                TestUtil.asStr(result));
    }
    
    @Test
    public void testSlice121_2() {
        SliceCriterion criterion = SliceTestUtil.getSliceCriterion(SliceProject, "Test121", 11, 12);
        Slicer slicer = new Slicer(criterion);
        List<String> result =PDGTestUtil.getIdList(criterion.getDependencyGraph(), slicer.getNodes());
        
        assertEquals("15;17;19",
                TestUtil.asStr(result));
    }
    
    @Test
    public void testSlice121_3() {
        SliceCriterion criterion = SliceTestUtil.getSliceCriterion(SliceProject, "Test121", 6, 12);
        Slicer slicer = new Slicer(criterion);
        List<String> result =PDGTestUtil.getIdList(criterion.getDependencyGraph(), slicer.getNodes());
        
        assertEquals("4;6;7;8;9;11;12;15;17;19;20;22",
                TestUtil.asStr(result));
    }
    
    @Test
    public void testSlice122_1() {
        SliceCriterion criterion = SliceTestUtil.getSliceCriterion(SliceProject, "Test122", 15, 12);
        Slicer slicer = new Slicer(criterion);
        List<String> result =PDGTestUtil.getIdList(criterion.getDependencyGraph(), slicer.getNodes());
        
        assertEquals("4;5;6;7;9;10;11;12;13;16;20;23;24;25;26;27;28;29;30;32;34",
                TestUtil.asStr(result));
    }
    
    @Test
    public void testSlice122_2() {
        SliceCriterion criterion = SliceTestUtil.getSliceCriterion(SliceProject, "Test122", 9, 16);
        Slicer slicer = new Slicer(criterion);
        List<String> result =PDGTestUtil.getIdList(criterion.getDependencyGraph(), slicer.getNodes());
        
        assertEquals("4;5;6;7;9;10;11;12;13;14;16;23;24;25;26;27;28;29;30;32;34",
                TestUtil.asStr(result));
    }
    
    @Test
    public void testSlice122_3() {
        SliceCriterion criterion = SliceTestUtil.getSliceCriterion(SliceProject, "Test122", 11, 22);
        Slicer slicer = new Slicer(criterion);
        List<String> result =PDGTestUtil.getIdList(criterion.getDependencyGraph(), slicer.getNodes());
        
        assertEquals("4;5;7;10;11;12;16;17;23;24;25;26;27;28;29;30",
                TestUtil.asStr(result));
    }
    
    @Test
    public void testSlice122_4() {
        SliceCriterion criterion = SliceTestUtil.getSliceCriterion(SliceProject, "Test122", 13, 16);
        Slicer slicer = new Slicer(criterion);
        List<String> result =PDGTestUtil.getIdList(criterion.getDependencyGraph(), slicer.getNodes());
        
        assertEquals("4;5;6;7;9;10;11;12;13;16;18;19;23;24;25;26;27;28;29;30;32;34",
                TestUtil.asStr(result));
    }
    
    @Test
    public void testSlice122_5() {
        SliceCriterion criterion = SliceTestUtil.getSliceCriterion(SliceProject, "Test122", 10, 27);
        Slicer slicer = new Slicer(criterion);
        List<String> result =PDGTestUtil.getIdList(criterion.getDependencyGraph(), slicer.getNodes());
        
        assertEquals("4;5;7;10;11;12;16;23;24;25;26;27;28;29;30",
                TestUtil.asStr(result));
    }
    
    @Test
    public void testSlice123_1() {
        SliceCriterion criterion = SliceTestUtil.getSliceCriterion(SliceProject, "Test123", 15, 12);
        Slicer slicer = new Slicer(criterion);
        List<String> result =PDGTestUtil.getIdList(criterion.getDependencyGraph(), slicer.getNodes());
        
        assertEquals("9;11;16;17;18;19;21;22;23;24;25;28;32;35;36;37;38;39;40;41;42;44;46",
                TestUtil.asStr(result));
    }
    
    @Test
    public void testSlice123_2() {
        SliceCriterion criterion = SliceTestUtil.getSliceCriterion(SliceProject, "Test123", 9, 16);
        Slicer slicer = new Slicer(criterion);
        List<String> result =PDGTestUtil.getIdList(criterion.getDependencyGraph(), slicer.getNodes());
        
        assertEquals("9;11;16;17;18;19;21;22;23;24;25;26;28;35;36;37;38;39;40;41;42;44;46",
                TestUtil.asStr(result));
    }
    
    @Test
    public void testSlice123_3() {
        SliceCriterion criterion = SliceTestUtil.getSliceCriterion(SliceProject, "Test123", 11, 22);
        Slicer slicer = new Slicer(criterion);
        List<String> result =PDGTestUtil.getIdList(criterion.getDependencyGraph(), slicer.getNodes());
        
        assertEquals("9;11;16;17;19;22;23;24;28;29;35;36;37;38;39;40;41;42",
                TestUtil.asStr(result));
    }
    
    @Test
    public void testSlice123_4() {
        SliceCriterion criterion = SliceTestUtil.getSliceCriterion(SliceProject, "Test123", 13, 16);
        Slicer slicer = new Slicer(criterion);
        List<String> result =PDGTestUtil.getIdList(criterion.getDependencyGraph(), slicer.getNodes());
        
        assertEquals("9;11;16;17;18;19;21;22;23;24;25;28;30;31;35;36;37;38;39;40;41;42;44;46",
                TestUtil.asStr(result));
    }
    
    @Test
    public void testSlice123_5() {
        SliceCriterion criterion = SliceTestUtil.getSliceCriterion(SliceProject, "Test123", 10, 27);
        Slicer slicer = new Slicer(criterion);
        List<String> result =PDGTestUtil.getIdList(criterion.getDependencyGraph(), slicer.getNodes());
        
        assertEquals("9;11;16;17;19;22;23;24;28;35;36;37;38;39;40;41;42",
                TestUtil.asStr(result));
    }
    
    @Test
    public void testSlice124_1() {
        SliceCriterion criterion = SliceTestUtil.getSliceCriterion(SliceProject, "Test124", 7, 13);
        Slicer slicer = new Slicer(criterion);
        List<String> result =PDGTestUtil.getIdList(criterion.getDependencyGraph(), slicer.getNodes());
        
        assertEquals("1;5;22;25;26;27;29",
                TestUtil.asStr(result));
    }
    
    @Test
    public void testSlice124_2() {
        SliceCriterion criterion = SliceTestUtil.getSliceCriterion(SliceProject, "Test124", 8, 12);
        Slicer slicer = new Slicer(criterion);
        List<String> result =PDGTestUtil.getIdList(criterion.getDependencyGraph(), slicer.getNodes());
        
        assertEquals("1;2;3;5;6;7;9;15;16;22;23;25;26;27;28;29;30;31;32;33;41",
                TestUtil.asStr(result));
    }
    
    @Test
    public void testSlice124_3() {
        SliceCriterion criterion = SliceTestUtil.getSliceCriterion(SliceProject, "Test124", 9, 12);
        Slicer slicer = new Slicer(criterion);
        List<String> result =PDGTestUtil.getIdList(criterion.getDependencyGraph(), slicer.getNodes());
        
        assertEquals("1;2;3;5;10;11;12;14;15;16;22;23;24;25;26;27;28;29;34;35;36;37;38;41",
                TestUtil.asStr(result));
    }
    
    @Test
    public void testSlice125_1() {
        SliceCriterion criterion = SliceTestUtil.getSliceCriterion(SliceProject, "Test125", 6, 12);
        Slicer slicer = new Slicer(criterion);
        List<String> result =PDGTestUtil.getIdList(criterion.getDependencyGraph(), slicer.getNodes());
        
        assertEquals("4;5;6;7;8;9;10;22;23;24;26",
                TestUtil.asStr(result));
    }
    
    @Test
    public void testSlice125_2() {
        SliceCriterion criterion = SliceTestUtil.getSliceCriterion(SliceProject, "Test125", 7, 12);
        Slicer slicer = new Slicer(criterion);
        List<String> result =PDGTestUtil.getIdList(criterion.getDependencyGraph(), slicer.getNodes());
        
        assertEquals("4;5;11;12;13;14;15;16;17;18;19;22;23;24;26;27;28;29;31",
                TestUtil.asStr(result));
    }
    
    @Test
    public void testSlice126_1() {
        SliceCriterion criterion = SliceTestUtil.getSliceCriterion(SliceProject, "Test126", 8, 13);
        Slicer slicer = new Slicer(criterion);
        List<String> result =PDGTestUtil.getIdList(criterion.getDependencyGraph(), slicer.getNodes());
        
        assertEquals("1;3;4;5;6;13;14;15;16;17;18;20;21;22;24;26;27;38;39;45;46;47;48;49;50;51;52;53;54;55;56",
                TestUtil.asStr(result));
    }
    
    @Test
    public void testSlice126_2() {
        SliceCriterion criterion = SliceTestUtil.getSliceCriterion(SliceProject, "Test126", 10, 12);
        Slicer slicer = new Slicer(criterion);
        List<String> result =PDGTestUtil.getIdList(criterion.getDependencyGraph(), slicer.getNodes());
        
        assertEquals("1;3;4;5;6;9;10;12;13;14;15;16;17;18;20;21;22;24;26;27;"
                + "38;39;45;46;47;48;49;50;51;52;53;54;55;56;57;58;59;60;61",
                TestUtil.asStr(result));
    }
    
    @Test
    public void testSlice126_3() {
        SliceCriterion criterion = SliceTestUtil.getSliceCriterion(SliceProject, "Test126", 11, 12);
        Slicer slicer = new Slicer(criterion);
        List<String> result =PDGTestUtil.getIdList(criterion.getDependencyGraph(), slicer.getNodes());
        
        assertEquals("1;3;4;5;6;9;10;12;13;14;15;16;17;18;20;21;22;24;26;27;38;39;45;46;47;48;49;"
                + "50;51;62;63;64;65;66;67;68;69",
                TestUtil.asStr(result));
    }
    
    @Test
    public void testSlice127_1() {
        SliceCriterion criterion = SliceTestUtil.getSliceCriterion(SliceProject, "Test127", 8, 12);
        Slicer slicer = new Slicer(criterion);
        List<String> result =PDGTestUtil.getIdList(criterion.getDependencyGraph(), slicer.getNodes());
        
        assertEquals("4;5;6;7;8;10;11;12;15;16;17;18;22;24;25;26;27;30;31;32;34;42;43",
                TestUtil.asStr(result));
    }
    
    @Test
    public void testSlice127_2() {
        SliceCriterion criterion = SliceTestUtil.getSliceCriterion(SliceProject, "Test127", 9, 12);
        Slicer slicer = new Slicer(criterion);
        List<String> result =PDGTestUtil.getIdList(criterion.getDependencyGraph(), slicer.getNodes());
        
        assertEquals("4;14;19;39;40",
                TestUtil.asStr(result));
    }
    
    @Test
    public void testSlice128_1() {
        SliceCriterion criterion = SliceTestUtil.getSliceCriterion(SliceProject, "Test128", 9, 12);
        Slicer slicer = new Slicer(criterion);
        List<String> result =PDGTestUtil.getIdList(criterion.getDependencyGraph(), slicer.getNodes());
        
        assertEquals("1;3;4;5;6;9;10;12;31;32;38;39;40;41;42;44;46;47;48;50;51;52;53;77",
                TestUtil.asStr(result));
    }
    
    @Test
    public void testSlice128_2() {
        SliceCriterion criterion = SliceTestUtil.getSliceCriterion(SliceProject, "Test128", 10, 12);
        Slicer slicer = new Slicer(criterion);
        List<String> result =PDGTestUtil.getIdList(criterion.getDependencyGraph(), slicer.getNodes());
        
        assertEquals("1;3;4;5;6;9;10;12;13;14;15;16;17;18;20;21;22;24;26;27;31;32;38;39;"
                + "40;41;42;43;44;46;47;48;54;55;56;57;58;59;60;61;77",
                TestUtil.asStr(result));
    }
    
    @Test
    public void testSlice128_3() {
        SliceCriterion criterion = SliceTestUtil.getSliceCriterion(SliceProject, "Test128", 11, 12);
        Slicer slicer = new Slicer(criterion);
        List<String> result =PDGTestUtil.getIdList(criterion.getDependencyGraph(), slicer.getNodes());
        
        assertEquals("1;3;4;5;6;9;10;12;13;14;15;16;17;18;20;21;22;24;26;27;31;32;38;39;"
                + "40;41;42;43;44;46;47;48;62;63;64;66;67;68;69;70;71;72;73;74;77;79;81;82;84",
                TestUtil.asStr(result));
    }
    
    @Test
    public void testSlice129_1() {
        SliceCriterion criterion = SliceTestUtil.getSliceCriterion(SliceProject, "Test129", 13, 15);
        Slicer slicer = new Slicer(criterion);
        List<String> result =PDGTestUtil.getIdList(criterion.getDependencyGraph(), slicer.getNodes());
        
        assertEquals("21;22;23;25;26;29;30;31;32;33;34;45;46;47;48;49;"
                + "50;52;60;62;64;66;67;68;70;71;72;73;74;75;115;118;119;"
                + "120;121;122;123;124;125;136;137;138;139;140;141;142;143;"
                + "185;186;187;188;189;221;222;224;225",
                TestUtil.asStr(result));
    }
    
    @Test
    public void testSlice129_2() {
        SliceCriterion criterion = SliceTestUtil.getSliceCriterion(SliceProject, "Test129", 15, 15);
        Slicer slicer = new Slicer(criterion);
        List<String> result =PDGTestUtil.getIdList(criterion.getDependencyGraph(), slicer.getNodes());
        
        assertEquals("30;31;32;33;34;60;62;64;66;67;68;70;71;72;73;74;75;"
                + "115;127;128;129;130;131;132;133;144;145;146;147;148;149;"
                + "150;151;152;191;192;193;194;195;197;198;199;"
                + "200;201;204;205;206;207;208;209;210;211;212;214;216;221;222;224;225",
                TestUtil.asStr(result));
    }
    
    @Test
    public void testSlice129_3() {
        SliceCriterion criterion = SliceTestUtil.getSliceCriterion(SliceProject, "Test129", 19, 15);
        Slicer slicer = new Slicer(criterion);
        List<String> result =PDGTestUtil.getIdList(criterion.getDependencyGraph(), slicer.getNodes());
        
        assertEquals("1;3;4;5;6;8;9;10;11;14;15;16;17;18;19;21;22;23;25;26;"
                + "30;31;32;33;34;36;37;38;39;40;41;42;44;45;46;47;48;49;50;52;60;62;115;"
                + "153;154;155;156;158;159;160;161;162;163;164;165;166;167",
                TestUtil.asStr(result));
    }
    
    @Test
    public void testSlice129_4() {
        SliceCriterion criterion = SliceTestUtil.getSliceCriterion(SliceProject, "Test129", 23, 15);
        Slicer slicer = new Slicer(criterion);
        List<String> result =PDGTestUtil.getIdList(criterion.getDependencyGraph(), slicer.getNodes());
        
        assertEquals("1;3;4;5;6;8;9;10;11;15;16;17;18;19;21;22;23;25;26;"
                + "30;31;32;33;34;36;37;38;39;40;41;42;44;45;46;47;48;49;50;52;60;62;78;"
                + "80;81;82;83;85;86;87;88;91;92;93;94;95;96;98;99;100;101;102;103;104;106;115;"
                + "168;169;170;171;173;174;175;176;177;178;179;180;181;182",
                TestUtil.asStr(result));
    }
    
    @Test
    public void testSlice130_1() {
        SliceCriterion criterion = SliceTestUtil.getSliceCriterion(SliceProject, "Test130", 6, 16);
        Slicer slicer = new Slicer(criterion);
        List<String> result =PDGTestUtil.getIdList(criterion.getDependencyGraph(), slicer.getNodes());
        
        assertEquals("4;6;7;8;9;10;11;12;13;17;18;19;20;21;22;23;25;26;27;28;30;36;37;38;40;42;43",
                TestUtil.asStr(result));
    }
    
    @Test
    public void testSlice131_1() {
        SliceCriterion criterion = SliceTestUtil.getSliceCriterion(SliceProject, "Test131", 10, 15);
        Slicer slicer = new Slicer(criterion);
        List<String> result =PDGTestUtil.getIdList(criterion.getDependencyGraph(), slicer.getNodes());
        
        assertEquals("4;6;7;8;10;11;12;13;16;17;18;19;20",
                TestUtil.asStr(result));
    }
    
    @Test
    public void testSlice132_1() {
        SliceCriterion criterion = SliceTestUtil.getSliceCriterion(SliceProject, "Test132", 6, 12);
        Slicer slicer = new Slicer(criterion);
        List<String> result =PDGTestUtil.getIdList(criterion.getDependencyGraph(), slicer.getNodes());
        
        assertEquals("4;5;6;7;8;11;12;13;14;15;16;17;19;20;22;23;25;26",
                TestUtil.asStr(result));
    }
    
    @Test
    public void testSlice133_1() {
        SliceCriterion criterion = SliceTestUtil.getSliceCriterion(SliceProject, "Test133", 8, 12);
        Slicer slicer = new Slicer(criterion);
        List<String> result =PDGTestUtil.getIdList(criterion.getDependencyGraph(), slicer.getNodes());
        
        assertEquals("4;5;83;84",
                TestUtil.asStr(result));
    }
    
    @Test
    public void testSlice134_1() {
        SliceCriterion criterion = SliceTestUtil.getSliceCriterion(SliceProject, "Test134", 7, 12);
        Slicer slicer = new Slicer(criterion);
        List<String> result =PDGTestUtil.getIdList(criterion.getDependencyGraph(), slicer.getNodes());
        
        assertEquals("36;37",
                TestUtil.asStr(result));
    }
    
    @Test
    public void testSlice135_1() {
        SliceCriterion criterion = SliceTestUtil.getSliceCriterion(SliceProject, "Test135", 10, 15);
        Slicer slicer = new Slicer(criterion);
        List<String> result =PDGTestUtil.getIdList(criterion.getDependencyGraph(), slicer.getNodes());
        
        assertEquals("1;3;4;5;6;7;8;11;12;13;14;15;16;17;19;21;22;25;26;27;28;37;38;39;"
                + "40;41;50;51;52;53;54;65;66;68;69;75;76",
                TestUtil.asStr(result));
    }
    
    @Test
    public void testSlice135_2() {
        SliceCriterion criterion = SliceTestUtil.getSliceCriterion(SliceProject, "Test135", 11, 15);
        Slicer slicer = new Slicer(criterion);
        List<String> result =PDGTestUtil.getIdList(criterion.getDependencyGraph(), slicer.getNodes());
        
        assertEquals("1;3;4;5;6;7;8;11;12;13;14;15;16;17;19;21;22;31;32;33;34;"
                + "42;43;44;45;46;56;57;58;59;60;65;66;68;69;77;78",
                TestUtil.asStr(result));
    }
    
    @Test
    public void testSlice136_1() {
        SliceCriterion criterion = SliceTestUtil.getSliceCriterion(SliceProject, "Test136", 13, 15);
        Slicer slicer = new Slicer(criterion);
        List<String> result =PDGTestUtil.getIdList(criterion.getDependencyGraph(), slicer.getNodes());
        
        assertEquals("1;3;4;5;6;8;9;12;13;14;15;16;17;19;20;21;22;23;24;26;"
                + "34;37;38;39;40;41;49;50;51;52;53;61;62;63;64;65",
                TestUtil.asStr(result));
    }
    
    @Test
    public void testSlice136_2() {
        SliceCriterion criterion = SliceTestUtil.getSliceCriterion(SliceProject, "Test136", 14, 15);
        Slicer slicer = new Slicer(criterion);
        List<String> result =PDGTestUtil.getIdList(criterion.getDependencyGraph(), slicer.getNodes());
        
        assertEquals("1;3;4;5;6;8;9;12;13;14;15;16;17;19;20;21;22;23;24;26;34;"
                + "43;44;45;46;47;54;55;56;57;58;67;68;69;70;71",
                TestUtil.asStr(result));
    }
    
    @Test
    public void testSlice137_1() {
        SliceCriterion criterion = SliceTestUtil.getSliceCriterion(SliceProject, "Test137", 8, 15);
        Slicer slicer = new Slicer(criterion);
        List<String> result =PDGTestUtil.getIdList(criterion.getDependencyGraph(), slicer.getNodes());
        
        assertEquals("1;3;4;5;7;8;11;12;13;14;15;16;18;19;20;21;22;23;25;"
                + "33;34;35;36;37;39;40;41;42;43;44;45;46;47",
                TestUtil.asStr(result));
    }
    
    @Test
    public void testSlice138_1() {
        SliceCriterion criterion = SliceTestUtil.getSliceCriterion(SliceProject, "Test138", 12, 18);
        Slicer slicer = new Slicer(criterion);
        List<String> result =PDGTestUtil.getIdList(criterion.getDependencyGraph(), slicer.getNodes());
        
        assertEquals("1;3;4;5;7;8;11;12;13;14;15;16;36;37;38;39;40;45;47;48;49;50;52;53;54;55",
                TestUtil.asStr(result));
    }
    
    @Test
    public void testSlice138_2() {
        SliceCriterion criterion = SliceTestUtil.getSliceCriterion(SliceProject, "Test138", 12, 13);
        Slicer slicer = new Slicer(criterion);
        List<String> result =PDGTestUtil.getIdList(criterion.getDependencyGraph(), slicer.getNodes());
        
        assertEquals("1;3;4;5;7;8;11;12;13;14;15;16;36;37;38;39;40;45;47;48;49;50;52;53;54;55;61",
                TestUtil.asStr(result));
    }
    
    @Test
    public void testSlice138_3() {
        SliceCriterion criterion = SliceTestUtil.getSliceCriterion(SliceProject, "Test138", 13, 21);
        Slicer slicer = new Slicer(criterion);
        List<String> result =PDGTestUtil.getIdList(criterion.getDependencyGraph(), slicer.getNodes());
        
        assertEquals("1;3;4;5;7;8;11;12;13;14;15;16;18;19;21;36;37;38;39;"
                + "40;45;47;48;49;50;52;53;54;55;62;63;64;65",
                TestUtil.asStr(result));
    }
    
    @Test
    public void testSlice138_4() {
        SliceCriterion criterion = SliceTestUtil.getSliceCriterion(SliceProject, "Test138", 14, 21);
        Slicer slicer = new Slicer(criterion);
        List<String> result =PDGTestUtil.getIdList(criterion.getDependencyGraph(), slicer.getNodes());
        
        assertEquals("1;3;4;5;7;8;11;12;13;14;15;16;18;19;21;36;37;38;39;"
                + "40;45;47;48;49;50;52;53;54;55;61;66;67;68;69",
                TestUtil.asStr(result));
    }
    
    @Test
    public void testSlice138_5() {
        SliceCriterion criterion = SliceTestUtil.getSliceCriterion(SliceProject, "Test138", 15, 21);
        Slicer slicer = new Slicer(criterion);
        List<String> result =PDGTestUtil.getIdList(criterion.getDependencyGraph(), slicer.getNodes());
        
        assertEquals("1;3;4;5;7;8;11;12;13;14;15;16;18;19;21;36;41;42;43;44;57;58;59;60;70;71;72;73",
                TestUtil.asStr(result));
    }
    
    @Test
    public void testSlice139_1() {
        SliceCriterion criterion = SliceTestUtil.getSliceCriterion(SliceProject, "Test139", 7, 12);
        Slicer slicer = new Slicer(criterion);
        List<String> result =PDGTestUtil.getIdList(criterion.getDependencyGraph(), slicer.getNodes());
        
        assertEquals("6;7;9;31;32;38;41;42;43;44",
                TestUtil.asStr(result));
    }
    
    @Test
    public void testSlice139_2() {
        SliceCriterion criterion = SliceTestUtil.getSliceCriterion(SliceProject, "PriceCode", 20, 13);
        Slicer slicer = new Slicer(criterion);
        List<String> result =PDGTestUtil.getIdList(criterion.getDependencyGraph(), slicer.getNodes());
        
        assertEquals("1;2;3",
                TestUtil.asStr(result));
    }
    
    @Test
    public void testSlice140_1() {
        SliceCriterion criterion = SliceTestUtil.getSliceCriterion(SliceProject, "S140", 19, 12);
        Slicer slicer = new Slicer(criterion);
        List<String> result =PDGTestUtil.getIdList(criterion.getDependencyGraph(), slicer.getNodes());
        
        assertEquals("13;14;17;19;20;21;23;26;27;33;38;49;50",
                TestUtil.asStr(result));
    }
    
    @Test
    public void testSlice140_2() {
        SliceCriterion criterion = SliceTestUtil.getSliceCriterion(SliceProject, "S140", 25, 12);
        Slicer slicer = new Slicer(criterion);
        List<String> result =PDGTestUtil.getIdList(criterion.getDependencyGraph(), slicer.getNodes());
        
        assertEquals("1;2;3;13;14;26;27;29;30;31;34;37",
                TestUtil.asStr(result));
    }
    
    @Test
    public void testSlice140_3() {
        SliceCriterion criterion = SliceTestUtil.getSliceCriterion(SliceProject, "S140", 31, 12);
        Slicer slicer = new Slicer(criterion);
        List<String> result =PDGTestUtil.getIdList(criterion.getDependencyGraph(), slicer.getNodes());
        
        assertEquals("13;14;40;41;46",
                TestUtil.asStr(result));
    }
    
    @Test
    public void testSlice141_1() {
        SliceCriterion criterion = SliceTestUtil.getSliceCriterion(SliceProject, "Test141", 11, 15);
        Slicer slicer = new Slicer(criterion);
        List<String> result =PDGTestUtil.getIdList(criterion.getDependencyGraph(), slicer.getNodes());
        
        assertEquals("4;6;7;8;13;14;15;16;23;24;25;26;27",
                TestUtil.asStr(result));
    }
    
    @Test
    public void testSlice141_2() {
        SliceCriterion criterion = SliceTestUtil.getSliceCriterion(SliceProject, "Test141", 12, 15);
        Slicer slicer = new Slicer(criterion);
        List<String> result =PDGTestUtil.getIdList(criterion.getDependencyGraph(), slicer.getNodes());
        
        assertEquals("4;10;11;12;17;18;19;20;29;30;31;32;33",
                TestUtil.asStr(result));
    }
    
    @Test
    public void testSlice141_3() {
        SliceCriterion criterion = SliceTestUtil.getSliceCriterion(SliceProject, "Test141", 18, 15);
        Slicer slicer = new Slicer(criterion);
        List<String> result =PDGTestUtil.getIdList(criterion.getDependencyGraph(), slicer.getNodes());
        
        assertEquals("29;30;31;32;33;35;41;42;43;44;45;46;47",
                TestUtil.asStr(result));
    }
    
    @Test
    public void testSlice141_4() {
        SliceCriterion criterion = SliceTestUtil.getSliceCriterion(SliceProject, "Test141", 19, 15);
        Slicer slicer = new Slicer(criterion);
        List<String> result =PDGTestUtil.getIdList(criterion.getDependencyGraph(), slicer.getNodes());
        
        assertEquals("23;24;25;26;27;35;37;38;39;48;49;50;51",
                TestUtil.asStr(result));
    }
    
    @Test
    public void testSlice142_1() {
        SliceCriterion criterion = SliceTestUtil.getSliceCriterion(SliceProject, "Test142", 7, 15);
        Slicer slicer = new Slicer(criterion);
        List<String> result =PDGTestUtil.getIdList(criterion.getDependencyGraph(), slicer.getNodes());
        
        assertEquals("4;6;7;8;11;12;13;14;21;22;23;24;25;27;28;30;34;36;37;39;50;51;56;58",
                TestUtil.asStr(result));
    }
    
    @Test
    public void testSlice142_2() {
        SliceCriterion criterion = SliceTestUtil.getSliceCriterion(SliceProject, "Test142", 8, 15);
        Slicer slicer = new Slicer(criterion);
        List<String> result =PDGTestUtil.getIdList(criterion.getDependencyGraph(), slicer.getNodes());
        
        assertEquals("4;6;7;8;9;15;16;17;18;21;22;23;24;25;27;28;29;30;31;34;35;"
                + "40;41;42;43;44;45;47;49;50;51;56;58;60;61",
                TestUtil.asStr(result));
    }
    
    @Test
    public void testSlice143_1() {
        SliceCriterion criterion = SliceTestUtil.getSliceCriterion(SliceProject, "Test143", 10, 15);
        Slicer slicer = new Slicer(criterion);
        List<String> result =PDGTestUtil.getIdList(criterion.getDependencyGraph(), slicer.getNodes());
        
        assertEquals("4;6;7;8;9;10;11;12;13;14;15;18;19;20;21;22;24;25;26;28;29;"
                + "32;33;34;35;36;37;39;40;41;42;43;44;46;50;52",
                TestUtil.asStr(result));
    }
    
    @Test
    public void testSlice144_1() {
        SliceCriterion criterion = SliceTestUtil.getSliceCriterion(SliceProject, "Test144", 9, 15);
        Slicer slicer = new Slicer(criterion);
        List<String> result =PDGTestUtil.getIdList(criterion.getDependencyGraph(), slicer.getNodes());
        
        assertEquals("4;5;6;7;8;10;11;12;13;14;15;16;17;18;19;52;54;55;56;57;59;"
                + "60;63;64;65;66;67;68;70;71;72;73;74;75;77",
                TestUtil.asStr(result));
    }
    
    @Test
    public void testSlice144_2() {
        SliceCriterion criterion = SliceTestUtil.getSliceCriterion(SliceProject, "Test144", 13, 15);
        Slicer slicer = new Slicer(criterion);
        List<String> result =PDGTestUtil.getIdList(criterion.getDependencyGraph(), slicer.getNodes());
        
        assertEquals("4;20;21;22;23;25;26;27;28;29;30;31;32;33;34;52;54;55;56;57;59;"
                + "60;63;64;65;66;67;68;70;71;72;73;74;75;77",
                TestUtil.asStr(result));
    }
    
    @Test
    public void testSlice144_3() {
        SliceCriterion criterion = SliceTestUtil.getSliceCriterion(SliceProject, "Test144", 17, 15);
        Slicer slicer = new Slicer(criterion);
        List<String> result =PDGTestUtil.getIdList(criterion.getDependencyGraph(), slicer.getNodes());
        
        assertEquals("4;35;36;37;38;40;41;42;43;44;45;46;47;48;49;52;54;55;56;57;59;"
                + "60;64;65;66;67;68;70;71;72;73;74;75;77;81;83;84;85;86;88;89;"
                + "90;91;94;95;96;97;98;99;101;102;103;104;105;106;107;109",
                TestUtil.asStr(result));
    }
    
    @Test
    public void testSlice145_1() {
        SliceCriterion criterion = SliceTestUtil.getSliceCriterion(SliceProject, "Test145", 9, 13);
        Slicer slicer = new Slicer(criterion);
        List<String> result =PDGTestUtil.getIdList(criterion.getDependencyGraph(), slicer.getNodes());
        
        assertEquals("1;5;21;23;24;25;26;28;29;30;31;32;33;48;49;50;51;52",
                TestUtil.asStr(result));
    }
    
    @Test
    public void testSlice145_2() {
        SliceCriterion criterion = SliceTestUtil.getSliceCriterion(SliceProject, "Test145", 10, 12);
        Slicer slicer = new Slicer(criterion);
        List<String> result =PDGTestUtil.getIdList(criterion.getDependencyGraph(), slicer.getNodes());
        
        assertEquals("1;5;6;7;9;14;15;21;23;24;25;26;28;29;30;31;32;33;34;35;36;37;48;49;50;51;52",
                TestUtil.asStr(result));
    }
    
    @Test
    public void testSlice145_3() {
        SliceCriterion criterion = SliceTestUtil.getSliceCriterion(SliceProject, "Test145", 11, 12);
        Slicer slicer = new Slicer(criterion);
        List<String> result =PDGTestUtil.getIdList(criterion.getDependencyGraph(), slicer.getNodes());
        
        assertEquals("1;5;10;11;13;21;23;24;25;26;28;29;30;31;32;33;38;39;40;41;48;49;50;51;52",
                TestUtil.asStr(result));
    }
    
    @Test
    public void testSlice145_4() {
        SliceCriterion criterion = SliceTestUtil.getSliceCriterion(SliceProject, "Test145", 12, 19);
        Slicer slicer = new Slicer(criterion);
        List<String> result =PDGTestUtil.getIdList(criterion.getDependencyGraph(), slicer.getNodes());
        
        assertEquals("1;5;21;23;24;25;26;28;29;42;43;44;45;48;49;50;51;52;55;56;58",
                TestUtil.asStr(result));
    }
    
    @Test
    public void testSlice146_1() {
        SliceCriterion criterion = SliceTestUtil.getSliceCriterion(SliceProject, "Test146", 8, 21);
        Slicer slicer = new Slicer(criterion);
        List<String> result =PDGTestUtil.getIdList(criterion.getDependencyGraph(), slicer.getNodes());
        
        assertEquals("4;6;7;8;9",
                TestUtil.asStr(result));
    }
    
    @Test
    public void testSlice146_2() {
        SliceCriterion criterion = SliceTestUtil.getSliceCriterion(SliceProject, "Test146", 9, 20);
        Slicer slicer = new Slicer(criterion);
        List<String> result =PDGTestUtil.getIdList(criterion.getDependencyGraph(), slicer.getNodes());
        
        assertEquals("4;6;7;8;9;10",
                TestUtil.asStr(result));
    }
    
    @Test
    public void testSlice146_3() {
        SliceCriterion criterion = SliceTestUtil.getSliceCriterion(SliceProject, "Test146", 10, 12);
        Slicer slicer = new Slicer(criterion);
        List<String> result =PDGTestUtil.getIdList(criterion.getDependencyGraph(), slicer.getNodes());
        
        assertEquals("4;5;6;7;8;9;10;11;12;13;14",
                TestUtil.asStr(result));
    }
    
    @Test
    public void testSlice146_4() {
        SliceCriterion criterion = SliceTestUtil.getSliceCriterion(SliceProject, "Test146", 12, 12);
        Slicer slicer = new Slicer(criterion);
        List<String> result =PDGTestUtil.getIdList(criterion.getDependencyGraph(), slicer.getNodes());
        
        assertEquals("4;5;6;7;8;9;10;11;12;13;14;15",
                TestUtil.asStr(result));
    }
    
    @Test
    public void testTest200_1() {
        SliceCriterion criterion = SliceTestUtil.getSliceCriterion(SliceProject, "Test200", 11, 12);
        Slicer slicer = new Slicer(criterion);
        List<String> result =PDGTestUtil.getIdList(criterion.getDependencyGraph(), slicer.getNodes());
        
        assertEquals("4;5;6",
                 TestUtil.asStr(result));
    }
    
    @Test
    public void testTest200_2() {
        SliceCriterion criterion = SliceTestUtil.getSliceCriterion(SliceProject, "Test200", 15, 12);
        Slicer slicer = new Slicer(criterion);
        List<String> result =PDGTestUtil.getIdList(criterion.getDependencyGraph(), slicer.getNodes());
        
        assertEquals("4;5;7",
                TestUtil.asStr(result));
    }
    
    @Test
    public void testTest200_3() {
        SliceCriterion criterion = SliceTestUtil.getSliceCriterion(SliceProject, "Test200", 19, 12);
        Slicer slicer = new Slicer(criterion);
        List<String> result =PDGTestUtil.getIdList(criterion.getDependencyGraph(), slicer.getNodes());
        
        assertEquals("4;5;6;8",
                TestUtil.asStr(result));
    }
    
    @Test
    public void testCustomer1() {
        SliceCriterion criterion = SliceTestUtil.getSliceCriterion(SliceProject, "Customer", 18, 12);
        Slicer slicer = new Slicer(criterion);
        List<String> result =PDGTestUtil.getIdList(criterion.getDependencyGraph(), slicer.getNodes());
        
        assertEquals("12;13;14;15;16;36;37;38;39;40;41;42;44;45;46;48;49;"
                + "57;58;59;62;63;64;65;66;68;69;70;71;72;"
                + "154;155;156;157;158;159;160;161;162;163;165;177;178",
                TestUtil.asStr(result));
    }
    
    @Test
    public void testCustomer2() {
        SliceCriterion criterion = SliceTestUtil.getSliceCriterion(SliceProject, "Customer", 23, 31);
        Slicer slicer = new Slicer(criterion);
        List<String> result =PDGTestUtil.getIdList(criterion.getDependencyGraph(), slicer.getNodes());
        
        assertEquals("154;155",
                TestUtil.asStr(result));
    }
    
    @Test
    public void testCustomer3() {
        SliceCriterion criterion = SliceTestUtil.getSliceCriterion(SliceProject, "Customer", 24, 12);
        Slicer slicer = new Slicer(criterion);
        List<String> result =PDGTestUtil.getIdList(criterion.getDependencyGraph(), slicer.getNodes());
        
        assertEquals("154;156",
                TestUtil.asStr(result));
    }
    
    @Test
    public void testCustomer4() {
        SliceCriterion criterion = SliceTestUtil.getSliceCriterion(SliceProject, "Customer", 18, 31);
        Slicer slicer = new Slicer(criterion);
        List<String> result =PDGTestUtil.getIdList(criterion.getDependencyGraph(), slicer.getNodes());
        
        assertEquals("12;13;14;15;16;57;58;59",
                TestUtil.asStr(result));
    }
    
    @Test
    public void testCustomer5() {
        SliceCriterion criterion = SliceTestUtil.getSliceCriterion(SliceProject, "Customer", 20, 15);
        
        Slicer slicer = new Slicer(criterion);
        List<String> result =PDGTestUtil.getIdList(criterion.getDependencyGraph(), slicer.getNodes());
        
        assertEquals("12;13;14;15;16;57;58;59;174;175",
                TestUtil.asStr(result));
    }
    
    @Test
    public void testCustomer6() {
        SliceCriterion criterion = SliceTestUtil.getSliceCriterion(SliceProject, "Customer", 26, 12);
        Slicer slicer = new Slicer(criterion);
        List<String> result =PDGTestUtil.getIdList(criterion.getDependencyGraph(), slicer.getNodes());
        
        assertEquals("12;13;14;15;16;36;37;38;39;40;41;42;44;45;46;48;49;"
                + "154;155;156;157;158;159;160;161;162;177;178",
                TestUtil.asStr(result));
    }
    
    @Test
    public void testCustomerTest1() {
        SliceCriterion criterion = SliceTestUtil.getSliceCriterion(SliceProject, "CustomerTest", 11, 44);
        Slicer slicer = new Slicer(criterion);
        List<String> result =PDGTestUtil.getIdList(criterion.getDependencyGraph(), slicer.getNodes());
        
        assertEquals("1;3;4;5;7;8;11;12;13;14;15;16;29;30;31;32;33;35;45;46;48;49;"
                + "76;87;88;89;90;91;92;93;94;95;96;98;99;100;101;2746;2747",
                TestUtil.asStr(result));
    }
    
    @Test
    public void testCustomerTest2() {
        SliceCriterion criterion = SliceTestUtil.getSliceCriterion(SliceProject, "CustomerTest", 12, 14);
        Slicer slicer = new Slicer(criterion);
        List<String> result =PDGTestUtil.getIdList(criterion.getDependencyGraph(), slicer.getNodes());
        
        assertEquals("1;3;4;5;7;8;11;12;13;14;15;16;29;30;31;32;33;35;45;46;48;49;"
                + "76;87;88;89;90;91;92;93;94;95;96;98;99;100;101;107;2746;2747",
                TestUtil.asStr(result));
    }
    
    @Test
    public void testCustomerTest3() {
        SliceCriterion criterion = SliceTestUtil.getSliceCriterion(SliceProject, "CustomerTest", 24, 44);
        Slicer slicer = new Slicer(criterion);
        List<String> result =PDGTestUtil.getIdList(criterion.getDependencyGraph(), slicer.getNodes());
        
        assertEquals("1;3;4;5;7;8;11;12;13;14;15;16;29;30;31;32;33;35;45;46;48;49;"
                + "110;121;122;123;124;125;126;127;128;129;"
                + "130;131;132;133;134;135;136;138;139;140;141;143;144;145;146;2753;2754",
                TestUtil.asStr(result));
    }
}
