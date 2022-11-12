/*
 *  Copyright 2022
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.jxplatform.builder.experiment;

import org.jtool.cfg.CCFG;
import org.jtool.jxplatform.builder.TimeInfo;
import org.jtool.srcmodel.JavaProject;
import java.io.File;
import java.util.List;
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
    protected int bytecodenum;
    
    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println(getCommandMessage("CFGGenerator"));
            System.exit(0);
        }
        
        CFGGenerator generator = new CFGGenerator();
        
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
        
        System.out.println("-CFG Generator");
        System.out.println("Target: " + name + " (" + path + ")");
        buildCFGs(jprojects);
        
        String srcinfo = getSrcModelInfo(name, jprojects);
        String cfginfo = getCFGInfo(ccfgs);
        
        builder.unbuild();
        
        System.out.print("**** , ");
        System.out.print(srcinfo);
        System.out.print(cfginfo);
        System.out.print(timesecSrcModel + " , ");
        System.out.print(timesecCFG + " , ");
        System.out.print("BYTECODE=" + binanalysis + " , ");
        System.out.print("CACHE=" + !removeCahche + " , ");
        System.out.print("BYTECODE_NUM=" + bytecodenum + " , ");
        System.out.println();
        System.out.println();
    }
    
    private void buildCFGs(List<JavaProject> jprojects) {
        ccfgs = new ArrayList<>();
        timesecCFG = 0;
        bytecodenum = 0;
        
        for (JavaProject jproject: jprojects) {
            ZonedDateTime startTime = TimeInfo.getCurrentTime();
            List<CCFG> result = generateCCFGs(jproject, jproject.getClasses());
            ccfgs.addAll(result);
            ZonedDateTime endTime = TimeInfo.getCurrentTime();
            timesecCFG = timesecCFG + CommonGenerator.getTimeSec(startTime, endTime);
            bytecodenum = bytecodenum + jproject.getCFGStore().getBCStore().getAnalyzedBytecodeNum();
            
            jproject.getCFGStore().destroy();
        }
    }
}
