/*
 *  Copyright 2022
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.jxplatform.project;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Set;
import java.util.HashSet;
import java.util.Map;
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
    
    AntEnv(String name, Path basePath, Path topPath) {
        super(name, basePath, topPath);
        configFile = basePath.resolve(Paths.get(AntEnv.configName));
    }
    
    @Override
    ProjectEnv createProjectEnv(String name, Path basePath, Path topPath) {
        return new AntEnv(name, basePath, topPath);
    }
    
    @Override
    boolean isApplicable() {
        try {
            if (configFile != null && configFile.toFile().exists()) {
                setConfigParameters(configFile);
                return true;
            }
        } catch (Exception e) { /* empty */ }
        return false;
    }
    
    private void setConfigParameters(Path configPath) throws Exception {
        ConfigParser parser = new ConfigParser();
        SAXParser saxParser = SAXParserFactory.newInstance().newSAXParser();
        saxParser.parse(configPath.toString(), parser);
        parser.postProcess();
        
        sourcePaths = parser.srcpath;
        binaryPaths = parser.binpath;
        classPaths = parser.classpath;
        
        compilerSourceVersion = parser.properties.get("javac.source");
        compilerTargetVersion = parser.properties.get("javac.target");
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
                    String cvalue = replace(value, value);
                    if (cvalue != null) {
                        properties.put(name, cvalue);
                    }
                }
                
            } else if (qname.equals("javac") && attr != null) {
                isJavacElem = true;
                String srcdir = attr.getValue("srcdir");
                if (srcdir != null) {
                    Path path = basePath.resolve(replace(srcdir, "src"));
                    if (path.toFile().exists()) {
                        srcpath.add(path.toString());
                    }
                }
                String destdir = attr.getValue("destdir");
                if (destdir != null) {
                    Path path = basePath.resolve(replace(destdir, "bin"));
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
                    Path path = basePath.resolve(replace(classpathdir, "lib"));
                    if (path.toFile().exists()) {
                        classpath.add(path.toString());
                    }
                    classpath.add(basePath.resolve("lib").toString());
                }
                
            } else if (qname.equals("src") && attr != null) {
                String srcdir = attr.getValue("path");
                Path path = basePath.resolve(replace(srcdir.concat(qname), "src"));
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
        
        private String replace(String value, String defaultValue) {
            int beginIndex = value.indexOf("${", 0);
            while (beginIndex != -1) {
                int endIndex = value.indexOf("}", beginIndex + 1);
                if (endIndex == -1) {
                    return defaultValue;
                }
                
                String key = value.substring(beginIndex + 2, endIndex);
                String cvalue = properties.get(key);
                if (cvalue != null) {
                    value = value.substring(0, beginIndex) + cvalue + value.substring(endIndex + 1);
                } else {
                    return defaultValue;
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
