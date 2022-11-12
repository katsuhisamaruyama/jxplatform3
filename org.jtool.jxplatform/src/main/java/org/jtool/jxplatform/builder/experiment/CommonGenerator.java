/*
 *  Copyright 2022
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.jxplatform.builder.experiment;

import org.jtool.srcmodel.JavaProject;
import org.jtool.srcmodel.JavaClass;
import org.jtool.srcmodel.JavaFile;
import org.jtool.cfg.CCFG;
import org.jtool.cfg.CFG;
import org.jtool.cfg.builder.CFGStore;
import org.jtool.pdg.ClDG;
import org.jtool.pdg.SDG;
import org.jtool.pdg.builder.PDGStore;
import org.jtool.jxplatform.builder.Logger;
import org.jtool.jxplatform.builder.ModelBuilderBatch;
import org.jtool.jxplatform.builder.TimeInfo;
import org.jtool.jxplatform.project.CommandLineOptions;
import org.jtool.jxplatform.project.ConsoleProgressMonitor;
import org.jtool.jxplatform.project.NullConsoleProgressMonitor;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.ArrayList;
import java.util.stream.Stream;
import java.util.stream.Collectors;
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
    protected boolean binanalysis;
    protected boolean logging;
    
    protected ModelBuilderBatch builder;
    protected List<JavaProject> jprojects;
    protected long timesecSrcModel;
    
    protected static String getCommandMessage(String generatorName) {
       return "java -Xms32G -Xmx32G -cp jxplatform-3.0.jar org.jtool.jxplatform.experiment." + generatorName +
                 " -target target_path [-name target_name] [-logging on/off] [-binanalysis on/off]";
    }
    
    protected void setOptions(String[] args) {
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
        binanalysis = options.get("-binanalysis", "on").equals("on") ? true : false;
    }
    
    static long getTimeSec(ZonedDateTime startTime, ZonedDateTime endTime) {
        long timesec = ChronoUnit.SECONDS.between(startTime, endTime);
        return timesec;
    }
    
    public static void printTimeSec(ZonedDateTime startTime, ZonedDateTime endTime) {
        long timesec = getTimeSec(startTime, endTime);
        System.out.println("Time: " + timesec + " (" +
                TimeInfo.getFormatedTime(startTime) + " - " +
                TimeInfo.getFormatedTime(endTime) + ")");
    }
    
    protected void setModelBuilder() {
        builder = new ModelBuilderBatch(binanalysis);
        builder.setLogVisible(logging);
    }
    
    protected void buildSrcModels(String name, String path) {
        ZonedDateTime startTime = TimeInfo.getCurrentTime();
        jprojects = builder.build(name, path);
        ZonedDateTime endTime = TimeInfo.getCurrentTime();
        timesecSrcModel = getTimeSec(startTime, endTime);
    }
    
    protected List<CCFG> generateCCFGs(JavaProject jproject, List<JavaClass> classes) {
        CFGStore cfgStore = jproject.getCFGStore();
        
        int size = classes.size();
        Logger logger = jproject.getModelBuilderImpl().getLogger();
        logger.printMessage("** Generating CFGs of " + size + " classes");
        ConsoleProgressMonitor pm = logger.isVisible() ? new ConsoleProgressMonitor() : new NullConsoleProgressMonitor();
        
        List<CCFG> ccfgs = new ArrayList<>();
        pm.begin(size);
        for (JavaClass jclass : classes) {
            CCFG ccfg = cfgStore.generateUnregisteredCCFG(jclass, false);
            if (ccfg != null) {
                ccfgs.add(ccfg);
            }
            pm.work(1);
        }
        pm.done();
        
        return ccfgs;
    }
    
    protected SDG generateClDGs(JavaProject jproject, List<CCFG> ccfgs) {
        PDGStore pdgStore = jproject.getPDGStore();
        
        int size = ccfgs.size();
        Logger logger = jproject.getModelBuilderImpl().getLogger();
        logger.printMessage("** Generating ClDGs of " + size + " classes");
        ConsoleProgressMonitor pm = logger.isVisible() ? new ConsoleProgressMonitor() : new NullConsoleProgressMonitor();
        
        SDG sdg = new SDG();
        pm.begin(size);
        for (CCFG ccfg : ccfgs) {
            ClDG cldg = pdgStore.generateUnregisteredClDG(ccfg);
            if (cldg != null) {
                sdg.add(cldg);
            }
            pm.work(1);
        }
        pm.done();
        
        pdgStore.findConnection(sdg);
        return sdg;
    }
    
    protected String getSrcModelInfo(String name, List<JavaProject> jprojects) {
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
    
    protected String getCFGInfo(List<CCFG> ccfgs) {
        List<CFG> cfgs = ccfgs.stream().flatMap(g -> g.getCFGs().stream()).collect(Collectors.toList());
        long nodes = cfgs.stream().flatMap(g -> g.getNodes().stream()).count();
        long edges = cfgs.stream().flatMap(g -> g.getEdges().stream()).count();
        
        StringBuilder buf = new StringBuilder();
        buf.append(nodes + " , ");
        buf.append(edges + " , ");
        return buf.toString();
    }
    
    protected String getPDGInfo(List<SDG> sdgs) {
        long nodes = sdgs.stream().flatMap(g -> g.getNodes().stream()).count();
        long edges = sdgs.stream().flatMap(g -> g.getEdges().stream()).count();
        
        StringBuilder buf = new StringBuilder();
        buf.append(nodes + " , ");
        buf.append(edges + " , ");
        return buf.toString();
    }
}
