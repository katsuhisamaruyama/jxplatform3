/*
 *  Copyright 2022
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.jxplatform.project;

import org.jtool.srcmodel.JavaProject;
import org.jtool.jxplatform.builder.ModelBuilder;
import java.util.List;
import java.util.ArrayList;
import java.io.File;
import java.nio.file.Paths;
import java.nio.file.Path;

/**
 * A batch processing builder implementation that builds models related to Java source code.
 * 
 * @author Katsuhisa Maruyama
 */
public class ModelBuilderBatchImpl extends ModelBuilderImpl {
    
    public ModelBuilderBatchImpl(ModelBuilder modelBuiler) {
        super(modelBuiler);
    }
    
    public List<JavaProject> build(String name, String target) {
        ProjectEnv topProjectEnv = createTopProjectEnv(name, target);
        
        List<ProjectEnv> projectEnvs = getSubProjects(topProjectEnv, topProjectEnv);
        if (projectEnvs.size() > 0) {
            return buildMultiTargets(projectEnvs, topProjectEnv);
        } else {
            return buildSingleTarget(topProjectEnv);
        }
    }
    
    private List<ProjectEnv> getSubProjects(ProjectEnv projectEnv, ProjectEnv topProjectEnv) {
        List<ProjectEnv> projectEnvs = new ArrayList<>();
        
        List<String> modules = projectEnv.getModules();
        if (modules.size() == 0) {
            if (projectEnv.isProject() &&
                    ModelBuilderImpl.containJavaFile(projectEnv.getSourcePaths())) {
                projectEnvs.add(projectEnv);
            }
            
        } else {
            for (String module : modules) {
                File dir = projectEnv.getBasePath().resolve(module).toFile();
                if (dir.isDirectory()) {
                    String path = dir.getAbsolutePath();
                    String subprojectname = projectEnv.getName() + "#" + module;
                    ProjectEnv env = createProjectEnv(subprojectname, path, projectEnv, topProjectEnv);
                    
                    projectEnvs.addAll(getSubProjects(env, topProjectEnv));
                }
            }
        }
        return projectEnvs;
    }
    
    private ProjectEnv createTopProjectEnv(String name, String target) {
        printMessage("Checking development environment for " + target);
        ProjectEnv env = createProjectEnv(name, target, null, null);
        try {
            env.setUpTopProject();
            printMessage("Found config file: " + env.getConfigFile());
        } catch (Exception e) {
            printError("Fail to collect dependent files.");
        }
        return env;
    }
    
    private ProjectEnv createProjectEnv(String name, String target, ProjectEnv parent, ProjectEnv topProjectEnv) {
        String cdir = new File(".").getAbsoluteFile().getParent();
        Path basePath = Paths.get(getFullPath(target, cdir));
        if (topProjectEnv == null) {
            return ProjectEnv.getProjectEnv(name, basePath, basePath, parent);
        } else {
            return ProjectEnv.getProjectEnv(name, basePath, topProjectEnv.getBasePath(), parent);
        }
    }
    
    private List<JavaProject> buildMultiTargets(List<ProjectEnv> projectEnvs, ProjectEnv topProjectEnv) {
        List<JavaProject> projects = new ArrayList<>();
        for (ProjectEnv env : projectEnvs) {
            printMessage("Checking sub-project " + env.getName());
            
            JavaProject jproject = buildTarget(env);
            if (jproject != null) {
                projects.add(jproject);
            }
        }
        return projects;
    }
    
    private List<JavaProject> buildSingleTarget(ProjectEnv topProjectEnv) {
        List<JavaProject> projects = new ArrayList<>();
        printMessage("Checking project " + topProjectEnv.getName());
        
        JavaProject jproject = buildTarget(topProjectEnv);
        if (jproject != null) {
            projects.add(jproject);
        }
        return projects;
    }
    
    private JavaProject buildTarget(ProjectEnv projectEnv) {
        try {
            projectEnv.setUpEachProject();
        } catch (Exception e) {
            printError("Fail to collect dependent files.");
            return null;
        }
        
        JavaProject jproject = build(projectEnv);
        return jproject;
    }
    
    private JavaProject build(ProjectEnv projectEnv) {
        String[] classpath = getClassPath(projectEnv.getClassPaths());
        String[] srcpath = getPath(projectEnv.getSourcePaths());
        String[] binpath = getPath(projectEnv.getBinaryPaths());
        JavaProject jproject = createProject(projectEnv.getName(),
                projectEnv.getBasePath(), projectEnv.getTopPath(),
                classpath, srcpath, binpath);
        jproject.setCompilerVersions(projectEnv.getCompilerSourceVersion(), projectEnv.getCompilerTargetVersion());
        
        build(jproject, projectEnv.getExcludedSourceFiles());
        
        logger.writeLog();
        return jproject;
    }
}
