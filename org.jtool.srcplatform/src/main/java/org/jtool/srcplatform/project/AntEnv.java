/*
 *  Copyright 2022
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.srcplatform.project;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Set;
import java.util.HashSet;
import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

/**
 * Obtains path information from the Ant setting.
 * 
 * @author Katsuhisa Maruyama
 */
class AntEnv extends ProjectEnv {
    
    private final static String configName = "build.xml";
    
    AntEnv(String name, Path basePath) {
        super(name, basePath);
        configFile = basePath.resolve(Paths.get(AntEnv.configName));
    }
    
    @Override
    ProjectEnv createProjectEnv(String name, Path basePath) {
        return new AntEnv(name, basePath);
    }
    
    @Override
    boolean isApplicable() {
        try {
            Path config = basePath.resolve(Paths.get(AntEnv.configName));
            if (config.toFile().exists()) {
                setPaths(config.toString());
                return true;
            }
        } catch (Exception e) { /* empty */ }
        return false;
    }
    
    @Override
    boolean isProject() {
        return sourcePath.size() > 0;
    }
    
    private void setPaths(String configFile) throws Exception {
        SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
        ConfigParser parser = new ConfigParser();
        SAXParser saxParser = saxParserFactory.newSAXParser();
        saxParser.parse(configFile.toString(), parser);
        parser.postProcess();
        
        sourcePath = parser.srcpath;
        binaryPath = parser.binpath;
        classPath = parser.classpath;
    }
    
    private class ConfigParser extends DefaultHandler {
        private Map<String, String> properties = new HashMap<>();
        private boolean isClasspathElem = false;
        private boolean isJavacElem = false;
        
        private Set<String> srcpath = new HashSet<String>();
        private Set<String> binpath = new HashSet<String>();
        private Set<String> classpath = new HashSet<String>();
        
        @Override
        public void startElement(String uri, String lname, String qname, Attributes attr) {
            if (qname.equals("property") && attr != null) {
                String name = attr.getValue("name");
                String value = attr.getValue("value");
                if (name != null && value != null) {
                    String cvalue = replace(value);
                    if (cvalue != null) {
                        properties.put(name, cvalue);
                    }
                }
                
            } else if (qname.equals("javac") && attr != null) {
                isJavacElem = true;
                String srcdir = attr.getValue("srcdir");
                if (srcdir != null) {
                    Path path = basePath.resolve(replace(srcdir));
                    if (path.toFile().exists()) {
                        srcpath.add(path.toString());
                    }
                }
                String destdir = attr.getValue("destdir");
                if (destdir != null) {
                    Path path = basePath.resolve(replace(destdir));
                    if (path.toFile().exists()) {
                        binpath.add(path.toString());
                    }
                }
                
            } else if (isJavacElem && qname.contentEquals("path") && attr != null) {
                String id = attr.getValue("id");
                if (id != null && id.equals("classpath")) {
                    isClasspathElem = true;
                }
                
            } else if (isClasspathElem && qname.contentEquals("fileset")) {
                String classpathdir = attr.getValue("dir");
                if (classpathdir != null) {
                    Path path = basePath.resolve(replace(classpathdir));
                    if (path.toFile().exists()) {
                        classpath.add(path.toString());
                    }
                    classpath.add(basePath.resolve("lib").toString());
                }
                
            } else if (qname.equals("src") && attr != null) {
                String srcdir = attr.getValue("path");
                Path path = basePath.resolve(replace(srcdir));
                if (path.toFile().exists()) {
                    srcpath.add(path.toString());
                }
            }
        }
        
        @Override
        public void endElement(String uri, String lname, String qname) {
            if (isClasspathElem && qname.equals("path")) {
                isClasspathElem = false;
            } else if (isJavacElem && qname.equals("javac")) {
                isJavacElem = false;
            }
        }
        
        private String replace(String value) {
            int beginIndex = value.indexOf("${", 0);
            while (beginIndex != -1) {
                int endIndex = value.indexOf("}", beginIndex + 1);
                if (endIndex == -1) {
                    return null;
                }
                
                String key = value.substring(beginIndex + 2, endIndex);
                String cvalue = properties.get(key);
                if (cvalue != null) {
                    value = value.substring(0, beginIndex) + cvalue + value.substring(endIndex + 1);
                } else {
                    return null;
                }
                beginIndex = value.indexOf("${", 0);
            }
            return value;
        }
        
        private void postProcess() {
            if (properties.get("lib.dir") != null) {
                classpath.add(basePath.resolve(properties.get("lib.dir")).toString());
            }
            if (properties.get("build.lib") != null) {
                classpath.add(basePath.resolve(properties.get("build.lib")).toString());
            }
        }
    }
    
    @Override
    public String toString() {
        return "Ant Env " + basePath.toString();
    }
}
