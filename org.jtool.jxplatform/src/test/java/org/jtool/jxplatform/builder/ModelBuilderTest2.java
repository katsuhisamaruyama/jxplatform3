/*
 *  Copyright 2023
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.jxplatform.builder;

import org.jtool.cfg.CCFG;
import org.jtool.pdg.ClDG;
import org.jtool.srcmodel.JavaClass;
import org.jtool.srcmodel.JavaProject;
import java.util.List;
import org.junit.Test;
import org.junit.Ignore;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class ModelBuilderTest2 {
    
    final static String EXP_DIR = "/Users/maru/Work/JxPlatformExpTarget/targetProjects/";
    
    static void buildClDG(String name, String cname) {
        String target = EXP_DIR + name;
        ModelBuilderBatch builder = new ModelBuilderBatch(true, true);
        List<JavaProject> jprojects = builder.build(name, target);
        
        JavaClass jclass = jprojects.stream().flatMap(p -> p.getClasses().stream())
                .filter(jc -> jc.getQualifiedName().fqn().equals(cname)).findFirst().orElse(null);
        
        if (jclass == null) {
            System.err.println("Not found: " + cname);
            return;
        }
        
        System.out.println("CLASS = " + jclass.getQualifiedName().fqn());
        
        CCFG ccfg = jclass.getJavaProject().getCFGStore().generateUnregisteredCCFG(jclass);
        ccfg.print();
        
        ClDG cldg = jclass.getJavaProject().getPDGStore().generateUnregisteredClDG(ccfg);
        cldg.print();
        
        System.out.println("Fin");
        builder.unbuild();
    }
    
    @Ignore
    public void testCreateClDG1() {
        buildClDG("druid-1.2.13/core", "com.alibaba.druid.stat.TestOracle");
    }
    
    @Test
    public void testCreateClDG2() {
        buildClDG("druid-1.2.13/core", "com.alibaba.druid.bvt.filter.wall.WallSelectIntoTest");
    }
}
