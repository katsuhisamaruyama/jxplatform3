/*
 *  Copyright 2022
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.jxplatform.builder.experiment;

import org.jtool.cfg.CCFG;
import org.jtool.jxplatform.builder.TimeInfo;
import org.jtool.pdg.SDG;
import org.jtool.srcmodel.JavaProject;
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
    
    protected List<SDG> sdgs;
    protected long timesecPDG;
    
    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("java -cp jxplatform-3.0.jar org.jtool.jxplatform.builder.experiment.PDGGenerator" +
                    "-target target_dir [-name target_name] [-logging on/off] [-binanalysis on/off]");
            System.exit(0);
        }
        
        PDGGenerator generator = new PDGGenerator();
        generator.setOptions(args);
        
        generator.run(true);
        generator.run(false);
    }
    
    private void run(boolean removeCahche) {
        File dir = new File(path);
        if (!dir.exists()) {
            System.err.println("Not found project " + path);
            return;
        }
        
        setModelBuilder();
        
        System.out.println("-SrcModel Generator");
        System.out.println("Target: " + name + " (" + path + ")");
        buildSrcModels(name, path);
        
        if (removeCahche) {
            jprojects.forEach(p -> p.getCFGStore().getBCStore().removeBytecodeCache());
        }
        
        System.out.println("-CFG/PDG Generator");
        System.out.println("Target: " + name + " (" + path + ")");
        buildPDGs(jprojects);
        
        String srcinfo = getSrcModelInfo(name, jprojects);
        String cfginfo = getCFGInfo(ccfgs);
        String pdginfo = getPDGInfo(sdgs);
        
        builder.unbuild();
        
        System.out.print("**** , ");
        System.out.print(srcinfo);
        System.out.print(cfginfo);
        System.out.print(pdginfo);
        System.out.print(timesecSrcModel + " , ");
        System.out.print(timesecCFG + " , ");
        System.out.print(timesecPDG + " , ");
        System.out.print("BYTECODE=" + binanalysis + " , ");
        System.out.print("CACHE=" + !removeCahche + " , ");
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
        
        ZonedDateTime startTime;
        ZonedDateTime endTime;
        
        for (JavaProject jproject: jprojects) {
            startTime = TimeInfo.getCurrentTime();
            List<CCFG> pccfgs = generateCCFGs(jproject, jproject.getClasses());
            ccfgs.addAll(pccfgs);
            endTime = TimeInfo.getCurrentTime();
            timesecCFG = timesecCFG + getTimeSec(startTime, endTime);
            
            startTime = TimeInfo.getCurrentTime();
            SDG sdg = generateClDGs(jproject, pccfgs);
            sdgs.add(sdg);
            endTime = TimeInfo.getCurrentTime();
            timesecPDG = timesecPDG + getTimeSec(startTime, endTime);
            
            jproject.getCFGStore().destroy();
        }
    }
}
