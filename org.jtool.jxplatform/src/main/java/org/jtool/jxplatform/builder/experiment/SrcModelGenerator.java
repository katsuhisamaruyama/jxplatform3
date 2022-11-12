/*
 *  Copyright 2022
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.jxplatform.builder.experiment;

import org.jtool.srcmodel.JavaClass;
import org.jtool.srcmodel.JavaFile;
import org.jtool.srcmodel.JavaProject;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
        
        String srcinfo = SrcModelGenerator.getSrcModelInfo(name, jprojects);
        
        builder.unbuild();
        
        System.out.print("**** , ");
        System.out.print(srcinfo);
        System.out.print(timesecSrcModel + " , ");
        System.out.print("BYTECODE=" + binanalysis + " , ");
        System.out.println();
        System.out.println();
    }
    
    static String getSrcModelInfo(String name, List<JavaProject> jprojects) {
        List<JavaClass> classes = jprojects.stream().flatMap(p -> p.getClasses().stream()).collect(Collectors.toList());
        long loc = getLoc(jprojects);
        long files = jprojects.stream().flatMap(p -> p.getFiles().stream()).count();
        long cnum = classes.size();
        long mnum = classes.stream().flatMap(p -> p.getMethods().stream()).count();
        long fnum = classes.stream().flatMap(p -> p.getFields().stream()).count();
        
        StringBuilder buf = new StringBuilder();
        buf.append(name + " , ");
        buf.append(loc + " , ");
        buf.append(jprojects.size() + " , ");
        buf.append(files + " , ");
        buf.append(cnum + " , ");
        buf.append(mnum + " , ");
        buf.append(fnum + " , ");
        return buf.toString();
    }
    
    private static long getLoc(List<JavaProject> jprojects) {
        long loc = 0;
        for (JavaProject jproject : jprojects) {
            for (JavaFile jfile : jproject.getFiles()) {
                Path file = Paths.get(jfile.getPath());
                try (Stream<String> stream = Files.lines(file)) {
                    long count = stream.count();
                    loc = loc + count;
                } catch (Exception e) {
                    System.err.println("ERROR " + e.getMessage() + jfile.getPath());
                }
            }
        }
        return loc;
    }
}
