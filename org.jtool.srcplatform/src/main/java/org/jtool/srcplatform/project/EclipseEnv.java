/*
 *  Copyright 2022
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.srcplatform.project;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.File;
import java.util.List;
import java.util.Set;
import java.util.HashSet;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

/**
 * Obtains path information from the Eclipse setting.
 * 
 * @author Katsuhisa Maruyama
 */
class EclipseEnv extends ProjectEnv {
    
    private final static String configName = ".classpath";
    
    EclipseEnv(String name, Path basePath) {
        super(name, basePath);
        configFile = basePath.resolve(Paths.get(EclipseEnv.configName));
    }
    
    @Override
    ProjectEnv createProjectEnv(String name, Path basePath) {
        return new EclipseEnv(name, basePath);
    }
    
    @Override
    boolean isApplicable() {
        try {
            Path config = basePath.resolve(Paths.get(EclipseEnv.configName));
            if (config.toFile().exists()) {
                setPaths(config.toString());
                return true;
            }
        } catch (Exception e) { /* empty */ }
        return false;
    }
    
    private void setPaths(String configFile) throws Exception {
        SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
        ConfigParser parser = new ConfigParser();
        SAXParser saxParser = saxParserFactory.newSAXParser();
        saxParser.parse(configFile, parser);
        
        sourcePaths = parser.srcpaths;
        binaryPaths = parser.binpaths;
        classPaths = parser.classpaths;
    }
    
    private class ConfigParser extends DefaultHandler {
        Set<String> srcpaths = new HashSet<>();
        Set<String> binpaths = new HashSet<>();
        Set<String> classpaths = new HashSet<>();
        
        @Override
        public void startElement(String uri, String lname, String qname, Attributes attr) {
            if (qname.equals("classpathentry") && attr != null) {
                if (attr.getValue("kind").equals("src")) {
                    Path path = Paths.get(attr.getValue("path"));
                    String srcPath = basePath.resolve(path).toString();
                    List<File> files = ModelBuilderBatchImpl.collectAllJavaFiles(srcPath.toString());
                    if (files.size() > 0) {
                        srcpaths.add(srcPath);
                    }
                    
                } else if (attr.getValue("kind").equals("output")) {
                    Path path = Paths.get(attr.getValue("path"));
                    binpaths.add(basePath.resolve(path).toString());
                    
                } else if (attr.getValue("kind").equals("lib")) {
                    Path path = Paths.get(attr.getValue("path"));
                    if (path.isAbsolute()) {
                        classpaths.add(path.toString());
                    } else {
                        classpaths.add(basePath.resolve(path).toString());
                    }
                }
            }
        }
    }
    
    @Override
    public String toString() {
        return "Eclipse Env " + basePath.toString();
    }
}
