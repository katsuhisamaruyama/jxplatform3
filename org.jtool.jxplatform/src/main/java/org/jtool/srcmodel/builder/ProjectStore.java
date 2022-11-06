/*
 *  Copyright 2020
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.srcmodel.builder;

import org.jtool.srcmodel.JavaProject;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

/**
 * An object holds a collection of all projects.
 * 
 * @author Katsuhisa Maruyama
 */
public class ProjectStore {
    
    private static ProjectStore instance = new ProjectStore();
    
    private Map<String, JavaProject> projectStore = new HashMap<>();
    
    protected JavaProject currentProject;
    
    public static ProjectStore getInstance() {
        return instance;
    }
    
    public void clear() {
        projectStore.values().forEach(jproject -> jproject.clear());
        projectStore.clear();
    }
    
    public void clear(String path) {
        JavaProject jproject = projectStore.get(path);
        if (jproject != null) {
            jproject.clear();
        }
    }
    
    public void addProject(JavaProject jproject) {
        projectStore.put(jproject.getPath(), jproject);
    }
    
    public void removeProject(String path) {
        clear(path);
        projectStore.remove(path);
    }
    
    public JavaProject getProject(String path) {
        return projectStore.get(path);
    }
    
    public List<JavaProject> getProjects() {
        return new ArrayList<>(projectStore.values());
    }
}
