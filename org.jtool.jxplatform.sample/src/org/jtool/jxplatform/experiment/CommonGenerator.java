/*
 *  Copyright 2022-2023
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.jxplatform.experiment;

import org.jtool.srcmodel.JavaProject;
import org.jtool.srcmodel.JavaClass;
import org.jtool.cfg.CCFG;
import org.jtool.cfg.internal.CFGStore;
import org.jtool.pdg.ClDG;
import org.jtool.pdg.SDG;
import org.jtool.pdg.internal.PDGStore;
import org.jtool.jxplatform.builder.ModelBuilderBatch;
import org.jtool.jxplatform.builder.TimeInfo;
import org.jtool.jxplatform.builder.CommandLineOptions;
import org.jtool.jxplatform.builder.ConsoleProgressMonitor;
import java.io.File;
import java.util.List;
import java.util.ArrayList;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;

/**
 * Common for experimentally building of source models, CFGs, and PDGs.
 * 
 * @author Katsuhisa Maruyama
 */
public class CommonGenerator {
    
    protected String path;
    protected String name;
    protected boolean logging;
    protected boolean binaryAnalysis;
    protected boolean useCache;
    protected int bytecodenum;
    
    protected ModelBuilderBatch builder;
    protected List<JavaProject> jprojects;
    protected long timesecSrcModel;
    
    protected static String getCommandMessage(String generatorName) {
       return "java -cp jxplatform-3-SNAPSHOT.jar org.jtool.jxplatform.experiment." + generatorName + "\n" +
              "   -target target_path [-name target_name]\n" +
              "   [-logging on/off] [-binanalysis on/off] [-cache on/off]";
    }
    
    protected CommandLineOptions setOptions(String[] args) {
        CommandLineOptions options = new CommandLineOptions(args);
        String target = options.get("-target", ".");
        name = options.get("-name", target);
        if (!target.startsWith("/")) {
            String cdir = new File(".").getAbsoluteFile().getParent();
            path = cdir + File.separatorChar + target;
        } else {
            path = target;
        }
        logging = options.get("-logging", "on").equals("on") ? true : false;
        binaryAnalysis = options.get("-binanalysis", "on").equals("on") ? true : false;
        useCache = options.get("-cache", "on").equals("on") ? true : false;
        
        return options;
    }
    
    static long getTimeSec(ZonedDateTime startTime, ZonedDateTime endTime) {
        long timesec = ChronoUnit.SECONDS.between(startTime, endTime);
        return timesec;
    }
    
    static long getTimeMilliSec(ZonedDateTime startTime, ZonedDateTime endTime) {
        long timesec = ChronoUnit.MILLIS.between(startTime, endTime);
        return timesec;
    }
    
    public static void printTimeSec(ZonedDateTime startTime, ZonedDateTime endTime) {
        long timesec = getTimeSec(startTime, endTime);
        System.out.println("Time: " + timesec + " (" +
                TimeInfo.getFormatedTime(startTime) + " - " +
                TimeInfo.getFormatedTime(endTime) + ")");
    }
    
    protected void setModelBuilder() {
        builder = new ModelBuilderBatch(binaryAnalysis, useCache);
        builder.setConsoleVisible(logging);
    }
    
    protected void buildSrcModels(String name, String path) {
        ZonedDateTime startTime = TimeInfo.getCurrentTime();
        jprojects = builder.build(name, path);
        ZonedDateTime endTime = TimeInfo.getCurrentTime();
        timesecSrcModel = getTimeSec(startTime, endTime);
        
        printTimeSec(startTime, endTime);
    }
    
    protected List<CCFG> generateCCFGs(JavaProject jproject, List<JavaClass> classes) {
        CFGStore cfgStore = jproject.getCFGStore();
        int size = classes.size();
        
        jproject.getModelBuilderImpl().printMessage("** Generating CFGs of " + size + " classes");
        ConsoleProgressMonitor monitor = jproject.getModelBuilderImpl().getConsoleProgressMonitor();
        
        List<CCFG> ccfgs = new ArrayList<>();
        
        monitor.begin(size);
        for (JavaClass jclass : classes) {
            CCFG ccfg = cfgStore.generateUnregisteredCCFG(jclass);
            if (ccfg != null) {
                ccfgs.add(ccfg);
            }
            monitor.work(1);
        }
        monitor.done();
        
        return ccfgs;
    }
    
    protected SDG generateClDGs(JavaProject jproject, List<CCFG> ccfgs) {
        PDGStore pdgStore = jproject.getPDGStore();
        int size = ccfgs.size();
        
        jproject.getModelBuilderImpl().printMessage("** Generating ClDGs of " + size + " classes");
        ConsoleProgressMonitor monitor = jproject.getModelBuilderImpl().getConsoleProgressMonitor();
        
        SDG sdg = new SDG();
        
        monitor.begin(size);
        for (CCFG ccfg : ccfgs) {
            ClDG cldg = pdgStore.generateUnregisteredClDG(ccfg);
            if (cldg != null) {
                sdg.add(cldg);
            }
            monitor.work(1);
        }
        monitor.done();
        
        pdgStore.collectInterPDGEdges(sdg);
        return sdg;
    }
}
