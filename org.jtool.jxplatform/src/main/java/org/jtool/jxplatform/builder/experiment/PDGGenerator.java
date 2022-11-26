/*
 *  Copyright 2022
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.jxplatform.builder.experiment;

import org.jtool.cfg.CCFG;
import org.jtool.pdg.DependencyGraph;
import org.jtool.srcmodel.JavaProject;
import org.jtool.jxplatform.builder.TimeInfo;
import java.io.File;
import java.util.List;
import java.util.ArrayList;
import java.time.ZonedDateTime;

/**
 * Experimentally build PDGs from open-source Java programs.
 * 
 * @author Katsuhisa Maruyama
 */
public class PDGGenerator extends CommonGenerator {
    
    protected List<CCFG> ccfgs;
    protected long timesecCFG;
    protected int bytecodenum;
    
    protected List<DependencyGraph> sdgs;
    protected long timesecPDG;
    
    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println(getCommandMessage("PDGGenerator"));
            System.exit(0);
        }
        
        PDGGenerator generator = new PDGGenerator();
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
        
        System.out.println("-CFG/PDG Generator");
        System.out.println("Target: " + name + " (" + path + ")");
        buildPDGs(jprojects);
        
        String srcinfo = SrcModelGenerator.getSrcModelInfo(name, jprojects);
        String cfginfo = CFGGenerator.getCFGInfo(ccfgs);
        String pdginfo = PDGGenerator.getPDGInfo(sdgs);
        
        builder.unbuild();
        
        System.out.print("**** , ");
        System.out.print(srcinfo);
        System.out.print(cfginfo);
        System.out.print(pdginfo);
        System.out.print(timesecSrcModel + " , ");
        System.out.print(timesecCFG + " , ");
        System.out.print(timesecPDG + " , ");
        System.out.print("BYTECODE=" + binanalysis + " , ");
        System.out.print("CACHE=" + useCache + " , ");
        System.out.print("BYTECODE_NUM=" + bytecodenum + " , ");
        System.out.println();
        System.out.println();
    }
    
    private void buildPDGs(List<JavaProject> jprojects) {
        ccfgs = new ArrayList<>();
        timesecCFG = 0;
        bytecodenum = 0;
        
        sdgs = new ArrayList<>();
        timesecPDG = 0;
        
        ZonedDateTime startTime = TimeInfo.getCurrentTime();
        for (JavaProject jproject: jprojects) {
            ZonedDateTime startTimeCFG = TimeInfo.getCurrentTime();
            List<CCFG> pccfgs = generateCCFGs(jproject, jproject.getClasses());
            ccfgs.addAll(pccfgs);
            ZonedDateTime endTimeCFG = TimeInfo.getCurrentTime();
            timesecCFG = timesecCFG + getTimeSec(startTimeCFG, endTimeCFG);
            
            ZonedDateTime startTimePDG = TimeInfo.getCurrentTime();
            DependencyGraph sdg = generateClDGs(jproject, pccfgs);
            sdgs.add(sdg);
            ZonedDateTime endTimePDG = TimeInfo.getCurrentTime();
            timesecPDG = timesecPDG + getTimeSec(startTimePDG, endTimePDG);
            
            jproject.getCFGStore().destroy();
        }
        ZonedDateTime endTime = TimeInfo.getCurrentTime();
        printTimeSec(startTime, endTime);
    }
    
    static String getPDGInfo(List<DependencyGraph> sdgs) {
        long nodes = sdgs.stream().flatMap(g -> g.getNodes().stream()).count();
        long edges = sdgs.stream().flatMap(g -> g.getEdges().stream()).count();
        
        StringBuilder buf = new StringBuilder();
        buf.append(nodes + " , ");
        buf.append(edges + " , ");
        return buf.toString();
    }
}
