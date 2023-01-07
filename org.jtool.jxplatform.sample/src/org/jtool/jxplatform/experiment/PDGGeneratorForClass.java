/*
 *  Copyright 2023
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.jxplatform.experiment;

import org.jtool.cfg.internal.CFGStore;
import org.jtool.pdg.internal.PDGStore;
import org.jtool.cfg.CCFG;
import org.jtool.srcmodel.JavaClass;
import org.jtool.jxplatform.builder.CommandLineOptions;
import org.jtool.jxplatform.builder.TimeInfo;
import java.io.File;
import java.util.List;
import java.util.stream.Collectors;
import java.util.ArrayList;
import java.time.ZonedDateTime;

/**
 * Experimentally build CCFG and ClDG from each class.
 * 
 * @author Katsuhisa Maruyama
 */
public class PDGGeneratorForClass extends CommonGenerator {
    
    protected List<Integer> classNumbers = new ArrayList<>();
    
    public static void main(String[] args) {
        PDGGeneratorForClass generator = new PDGGeneratorForClass();
        generator.setOptions(args);
        
        generator.run();
    }
    
    protected CommandLineOptions setOptions(String[] args) {
        CommandLineOptions options = super.setOptions(args);
        
        String numberStr = options.get("-select", "[0, ]");
        numberStr = numberStr.replace("[", "");
        numberStr = numberStr.replace("]", "");
        numberStr = numberStr.replace(" ", "");
        
        String[] numbers = numberStr.split(",");
        for (int i = 0; i < numbers.length; i++) {
            int classNumber = Integer.parseInt(numbers[i]);
            classNumbers.add(classNumber);
        }
        return options;
    }
    
    private void run() {
        File dir = new File(path);
        if (!dir.exists()) {
            System.err.println("Not found project " + path);
            return;
        }
        
        setModelBuilder();
        
        System.out.print("Target: " + name + " (" + path + ")");
        ZonedDateTime startTime = TimeInfo.getCurrentTime();
        jprojects = builder.build(name, path);
        List<JavaClass> allClasses = jprojects.stream()
                .flatMap(p -> p.getClasses().stream()).collect(Collectors.toList());
        buildPDGs(allClasses);
        ZonedDateTime endTime = TimeInfo.getCurrentTime();
        long timeSec = getTimeMilliSec(startTime, endTime);
        
        System.out.print("**** , ");
        System.out.print(name + " , ");
        System.out.print(timeSec + " , ");
        System.out.print("BYTECODE=" + binaryAnalysis + " , ");
        System.out.print("CACHE=" + useCache + " , ");
        System.out.println();
        
        printTimeSec(startTime, endTime);
        System.out.println();
    }
    
    private void buildPDGs(List<JavaClass> allClasses) {
        List<JavaClass> classes = new ArrayList<>();
        for (int classNumber : classNumbers) {
            JavaClass jclass = allClasses.get(classNumber);
            if (jclass != null) {
                classes.add(jclass);
            }
        }
        System.out.println("CLASSES = " + classes.size());
        
        List<CCFG> ccfgs = new ArrayList<>();
        for (JavaClass jclass : classes) {
            CFGStore cfgStore = jclass.getJavaProject().getCFGStore();
            CCFG ccfg = cfgStore.getCCFG(jclass);
            ccfgs.add(ccfg);
        }
        
        for (JavaClass jclass : classes) {
            PDGStore pdgStore = jclass.getJavaProject().getPDGStore();
            pdgStore.getClDG(jclass, false);
        }
    }
}
