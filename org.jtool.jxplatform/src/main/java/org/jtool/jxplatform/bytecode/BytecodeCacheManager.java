/*
 *  Copyright 2020
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.jxplatform.bytecode;

import org.jtool.jxplatform.util.Logger;
import org.jtool.jxplatform.util.TimeInfo;
import org.jtool.srcmodel.JavaProject;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import java.util.Collection;
import java.util.Map;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.List;
import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.StringWriter;

/**
 * Manages (plus reads and writes) the cache of byte-code classes.
 * 
 * @author Katsuhisa Maruyama
 */
public class BytecodeCacheManager {
    
    static final String FORMAT_VERSION = "0.1";
    
    static final String BYTECODE_CACHE_DIR = ".srcplatform";
    static final String CACHE_FILE_EXTION = ".cache";
    
    static final String ProjectElem = "project";
    static final String PathAttr = "path";
    static final String TimeAttr = "time";
    static final String JavaVMAttr = "jvm";
    
    static final String FormatVersionAttr = "version";
    
    static final String ClassElem = "class";
    static final String MethodElem = "method";
    static final String FieldElem = "field";
    
    public static final String NameAttr = "name";
    public static final String ModifierAttr = "mod";
    public static final String isInterfaceAttr = "intf";
    public static final String isInProjectAttr = "inproj";
    public static final String SuperClassAttr = "sclass";
    public static final String SuperInterfaceAttr = "sintf";
    
    public static final String SignatureAttr = "sig";
    public static final String DefAttr = "def";
    public static final String UseAttr = "use";
    public static final String CallAttr = "call";
    
    static String readBootModuleVersion(JavaProject jproject, String name) {
        if (jproject.makeDir(BYTECODE_CACHE_DIR)) {
            return "0";
        }
        
        String filename = jproject.getPath() + File.separator +
                BYTECODE_CACHE_DIR + File.separator + name;
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            return reader.readLine();
        } catch (IOException e) {
            return "0";
        }
    }
    
    static boolean writeBootModuleVersion(JavaProject jproject, String name, String version) {
        String filename = jproject.getPath() + File.separator +
                BYTECODE_CACHE_DIR + File.separator + name;
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            writer.write(version);
            writer.newLine();
            return true;
        } catch (IOException e) {
            return false;
        }
    }
    
    static long getLastModifiedTimeJarsCacheFile(JavaProject jproject, String cacheName) {
        String filename = jproject.getPath() + File.separator + BYTECODE_CACHE_DIR +
                File.separator + cacheName + CACHE_FILE_EXTION;
        File file = new File(filename);
        if (file.exists()) {
            return file.lastModified();
        }
        return -1;
    }
    
    static boolean canRead(JavaProject jproject, String cacheName) {
        String filename = jproject.getPath() + File.separator + BYTECODE_CACHE_DIR +
                File.separator + cacheName + CACHE_FILE_EXTION;
        File file = new File(filename);
        return file.canRead();
    }
    
    static boolean writeCache(JavaProject jproject, List<? extends BytecodeClassCache>classes, String cacheName) {
        String filename = jproject.getPath() + File.separator + BYTECODE_CACHE_DIR +
                File.separator + cacheName + CACHE_FILE_EXTION;
        
        try {
            CacheExporter exporter = new CacheExporter();
            Document doc = exporter.createDocument(jproject.getPath(), classes);
            
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            DOMSource src = new DOMSource(doc);
            
            StringWriter writer = new StringWriter();
            transformer.transform(src, new StreamResult(writer));
            
            File file = new File(filename);
            if (file.exists()) {
                file.delete();
            }
            
            BufferedWriter bwriter = new BufferedWriter(new FileWriter(file));
            bwriter.write(writer.toString());
            bwriter.flush();
            bwriter.close();
            return true;
            
        } catch (TransformerException  | ParserConfigurationException e) {
            System.err.println("DOM: Export error occurred: " + e.getMessage() + ".");
            return false;
        } catch (IOException e) {
            System.err.println("IO error " + e.getMessage());
            return false;
        }
    }
    
    static boolean readCache(JavaProject jproject, String cacheName) {
        String filename = jproject.getPath() + File.separator + BYTECODE_CACHE_DIR +
                File.separator + cacheName + CACHE_FILE_EXTION;
        File file = new File(filename);
        if (!file.canRead()) {
            return false;
        }
        
        Logger.getInstance().recordLog("-Read cache " + filename);
        
        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            factory.setNamespaceAware(true);
            
            SAXParser parser = factory.newSAXParser();
            DefaultHandler handler = new ProxyImporter(jproject, cacheName);
            parser.parse(file, handler);
            return true;
        } catch (SAXException e) {
            System.err.println(e.getMessage());
            return false;
        } catch (ParserConfigurationException e) {
            System.err.println("DOM: Export error occurred: " + e.getMessage() + ".");
            return false;
        } catch (IOException e) {
            System.err.println("IO error " + e.getMessage());
            return false;
        }
    }
}

class CacheExporter {
    
    Document createDocument(String path, Collection<? extends BytecodeClassCache> classes) throws ParserConfigurationException {
        DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        Document doc = builder.newDocument();
        Element projectElem = doc.createElement(BytecodeCacheManager.ProjectElem);
        projectElem.setAttribute(BytecodeCacheManager.PathAttr, path);
        projectElem.setAttribute(BytecodeCacheManager.TimeAttr, TimeInfo.getTimeAsISOString(TimeInfo.getCurrentTime()));
        projectElem.setAttribute(BytecodeCacheManager.FormatVersionAttr, BytecodeCacheManager.FORMAT_VERSION);
        doc.appendChild(projectElem);
        
        for (BytecodeClassCache clazz : classes) {
            exportClass(doc, projectElem, clazz);
        }
        return doc;
    }
    
    private void exportClass(Document doc, Element parent, BytecodeClassCache clazz) {
        Element classElem = doc.createElement(BytecodeCacheManager.ClassElem);
        parent.appendChild(classElem);
        
        for (Entry<String, String> entry : clazz.getClassCacheData().entrySet()) {
            classElem.setAttribute(entry.getKey(), entry.getValue());
        }
        exportMethod(doc, classElem, clazz);
    }
    
    private void exportMethod(Document doc, Element parent, BytecodeClassCache clazz) {
        for (Map<String, String> method : clazz.getMethodCacheData()) {
            Element methodElem = doc.createElement(BytecodeCacheManager.MethodElem);
            for (Entry<String, String> entry : method.entrySet()) {
                methodElem.setAttribute(entry.getKey(), entry.getValue());
            }
            parent.appendChild(methodElem);
        }
        
        for (Map<String, String> field : clazz.getFieldCacheData()) {
            Element fieldElem = doc.createElement(BytecodeCacheManager.FieldElem);
            for (Entry<String, String> entry : field.entrySet()) {
                fieldElem.setAttribute(entry.getKey(), entry.getValue());
            }
            parent.appendChild(fieldElem);
        }
    }
}

class ProxyImporter extends DefaultHandler {
    
    private JavaProject jproject;
    private String cacheName;
    
    private BytecodeClassProxy clazz = null;
    
    ProxyImporter(JavaProject jproject, String cacheName) {
        this.jproject = jproject;
        this.cacheName = cacheName;
    }
    
    @Override
    public void startElement(String uri, String name, String qname, Attributes attrs) throws SAXException {
        if (qname.equals(BytecodeCacheManager.ProjectElem)) {
            Map<String, String> attr = attributes(attrs);
            String format = attr.get(BytecodeCacheManager.FormatVersionAttr);
            if (!format.equals(BytecodeCacheManager.FORMAT_VERSION)) {
                throw new SAXException("Cache format was changed");
            }
            return;
        }
        
        if (qname.equals(BytecodeCacheManager.ClassElem)) {
            clazz = new BytecodeClassProxy(cacheName, jproject.getCFGStore().getBCStore());
            clazz.addClass(attributes(attrs));
            return;
        }
        
        if (qname.equals(BytecodeCacheManager.MethodElem)) {
            if (clazz != null) {
                clazz.addMethod(attributes(attrs));
            }
            return;
        }
        
        if (qname.equals(BytecodeCacheManager.FieldElem)) {
            if (clazz != null) {
                clazz.addField(attributes(attrs));
            }
            return;
        }
    }
    
    @Override
    public void endElement(String uri, String name, String qname) throws SAXException {
        if (qname.equals(BytecodeCacheManager.ClassElem)) {
            clazz.collectInfo();
            clazz = null;
        }
    }
    
    private Map<String, String> attributes(Attributes attrs) {
        Map<String, String> map = new HashMap<>();
        for (int i = 0; i < attrs.getLength(); i++) {
            map.put(attrs.getQName(i), attrs.getValue(i));
        }
        return map;
    }
}
