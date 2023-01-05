/*
 *  Copyright 2022-2023
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.jxplatform.experiment;

import org.jtool.cfg.CCFG;
import org.jtool.cfg.CFG;
import org.jtool.jxplatform.builder.TimeInfo;
import org.jtool.srcmodel.JavaProject;
import java.io.File;
import java.util.List;
import java.util.stream.Collectors;
import java.util.ArrayList;
import java.time.ZonedDateTime;

/**
 * Experimentally build CFGs from open-source Java programs.
 * 
 * @author Katsuhisa Maruyama
 */
public class CFGGenerator extends CommonGenerator {
    
    protected List<CCFG> ccfgs;
    protected long timesecCFG;
    
    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println(getCommandMessage("CFGGenerator"));
            System.exit(0);
        }
        
        CFGGenerator generator = new CFGGenerator();
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
        
        System.out.println("-CFG Generator");
        System.out.println("Target: " + name + " (" + path + ")");
        buildCFGs(jprojects);
        
        String srcinfo = SrcModelGenerator.getSrcModelInfo(name, jprojects);
        String cfginfo = CFGGenerator.getCFGInfo(ccfgs);
        
        builder.unbuild();
        
        System.out.print("**** , ");
        System.out.print(srcinfo);
        System.out.print(cfginfo);
        System.out.print(timesecSrcModel + " , ");
        System.out.print(timesecCFG + " , ");
        System.out.print("BYTECODE=" + binaryAnalysis + " , ");
        System.out.print("CACHE=" + useCache + " , ");
        System.out.print("BYTECODE_NUM=" + bytecodenum + " , ");
        System.out.println();
        System.out.println();
    }
    
    private void buildCFGs(List<JavaProject> jprojects) {
        ccfgs = new ArrayList<>();
        timesecCFG = 0;
        bytecodenum = 0;
        
        ZonedDateTime startTime = TimeInfo.getCurrentTime();
        for (JavaProject jproject : jprojects) {
            ZonedDateTime startTimeCFG = TimeInfo.getCurrentTime();
            List<CCFG> result = generateCCFGs(jproject, jproject.getClasses());
            ccfgs.addAll(result);
            ZonedDateTime endTimeCFG = TimeInfo.getCurrentTime();
            timesecCFG = timesecCFG + CommonGenerator.getTimeSec(startTimeCFG, endTimeCFG);
            bytecodenum = bytecodenum + jproject.getCFGStore().getBCStore().getAnalyzedBytecodeNum();
            
            jproject.getCFGStore().destroy();
        }
        ZonedDateTime endTime = TimeInfo.getCurrentTime();
        printTimeSec(startTime, endTime);
    }
    
    static String getCFGInfo(List<CCFG> ccfgs) {
        List<CFG> cfgs = ccfgs.stream().flatMap(g -> g.getCFGs().stream()).collect(Collectors.toList());
        long nodes = cfgs.stream().flatMap(g -> g.getNodes().stream()).count();
        long edges = cfgs.stream().flatMap(g -> g.getEdges().stream()).count();
        
        StringBuilder buf = new StringBuilder();
        buf.append(nodes + " , ");
        buf.append(edges + " , ");
        return buf.toString();
    }
}
