/*
 *  Copyright 2022
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.jxplatform.sample;

import org.jtool.jxplatform.builder.ModelBuilderBatch;
import org.jtool.pdg.ClDG;
import org.jtool.pdg.PDG;
import org.jtool.srcmodel.JavaClass;
import org.jtool.srcmodel.JavaProject;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Sample code for building a PDG.
 * 
 * @author Katsuhisa Maruyama
 */
public class PDGGeneratorSample {
    
    public static void main(String[] args) {
        PDGGeneratorSample generator = new PDGGeneratorSample();
        generator.run(SrcModelGeneratorSample.SAMPLE_PROJECT_DIR);
    }
    
    private void run(String path) {
        ModelBuilderBatch builder = new ModelBuilderBatch();
        builder.analyzeBytecode(true);
        builder.useCache(true);
        builder.setConsoleVisible(true);
        
        String name = path;
        List<JavaProject> jprojects = builder.build(name, path);
        
        List<JavaClass> classes = jprojects.stream().flatMap(p -> p.getClasses().stream()).collect(Collectors.toList());
        for (JavaClass jclass : classes) {
            ClDG cldg = builder.getClDG(jclass);
            for (PDG pdg : cldg.getPDGs()) {
                pdg.print();
            }
        }
        
        builder.unbuild();
    }
}
