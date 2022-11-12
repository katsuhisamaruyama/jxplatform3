/*
 *  Copyright 2022
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.jxplatform.builder.experiment;

import java.io.File;

/**
 * Experimentally build source models from open-source Java programs.
 * 
 * @author Katsuhisa Maruyama
 */
public class SrcModelGenerator extends CommonGenerator {
    
    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println(getCommandMessage("SrcModelGenerator"));
            System.exit(0);
        }
        
        SrcModelGenerator generator = new SrcModelGenerator();
        generator.setOptions(args);
        generator.run();
    }
    
    private void run() {
        File dir = new File(path);
        if (!dir.exists()) {
            System.err.println("Not found project " + path);
            return;
        }
        
        setModelBuilder();
        
        System.out.println("-SrcModel Generator");
        System.out.println("Target: " + name + " (" + path + ")");
        buildSrcModels(name, path);
        
        String srcinfo = getSrcModelInfo(name, jprojects);
        
        builder.unbuild();
        
        System.out.print("**** , ");
        System.out.print(srcinfo);
        System.out.print(timesecSrcModel + " , ");
        System.out.print("BYTECODE=" + binanalysis + " , ");
        System.out.println();
        System.out.println();
    }
}
