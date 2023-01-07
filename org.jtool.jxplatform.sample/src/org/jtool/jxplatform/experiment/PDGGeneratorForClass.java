/*
 *  Copyright 2023
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.jxplatform.experiment;

import org.jtool.cfg.CCFG;
import org.jtool.cfg.internal.CFGStore;
import org.jtool.pdg.internal.PDGStore;
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
        ZonedDateTime endTime = TimeInfo.getCurrentTime();
        timesecSrcModel = getTimeMilliSec(startTime, endTime);
        System.out.print("**** , ");
        System.out.print(name + " , ");
        System.out.print(timesecSrcModel + " , ");
        System.out.print("BYTECODE=" + binaryAnalysis + " , ");
        System.out.print("CACHE=" + useCache + " , ");
        System.out.println();
        
        List<JavaClass> classes = jprojects.stream().flatMap(p -> p.getClasses().stream()).collect(Collectors.toList());
        buildPDGs(classes);
    }
    
    private void buildPDGs(List<JavaClass> classes) {
        ZonedDateTime startTime = TimeInfo.getCurrentTime();
        for (int classNumber : classNumbers) {
            JavaClass selectedClass = classes.get(classNumber);
            System.out.print("#### " + classNumber + " ");
            System.out.print(selectedClass.getQualifiedName().fqn());
            System.out.println();
            
            CFGStore cfgStore = selectedClass.getJavaProject().getCFGStore();
            ZonedDateTime startTimeCFG = TimeInfo.getCurrentTime();
            CCFG ccfg = cfgStore.generateUnregisteredCCFG(selectedClass);
            ZonedDateTime endTimeCFG = TimeInfo.getCurrentTime();
            long timesecCFG = getTimeMilliSec(startTimeCFG, endTimeCFG);
            
            PDGStore pdgStore = selectedClass.getJavaProject().getPDGStore();
            ZonedDateTime startTimePDG = TimeInfo.getCurrentTime();
            pdgStore.generateUnregisteredClDG(ccfg);
            ZonedDateTime endTimePDG = TimeInfo.getCurrentTime();
            long timesecPDG = getTimeMilliSec(startTimePDG, endTimePDG);
            
            System.out.print("**** , ");
            System.out.print(name + " , ");
            System.out.print(selectedClass.getQualifiedName().fqn() + " , ");
            System.out.print(selectedClass.getCodeRange().getLoc() + " , ");
            System.out.print(timesecCFG + " , ");
            System.out.print(timesecPDG + " , ");
            System.out.println();
            
        }
        ZonedDateTime endTime = TimeInfo.getCurrentTime();
        printTimeSec(startTime, endTime);
        System.out.println();
    }
}
