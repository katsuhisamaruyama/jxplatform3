/*
 *  Copyright 2022
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.cfg.internal.refmodel;

import org.jtool.srcmodel.JavaProject;
import org.jtool.jxplatform.project.TimeInfo;
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
import java.util.Collection;
import java.util.Map;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.List;
import java.io.File;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.nio.file.Paths;
import java.nio.file.Path;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * Reads and writes the cache of byte-code classes in the XML format.
 * 
 * @author Katsuhisa Maruyama
 */
class BytecodeCacheManagerXML extends BytecodeCacheManager {
    
    private static final String CACHE_FILE_EXTION = "cache.xml";
    
    BytecodeCacheManagerXML() {
        super(CACHE_FILE_EXTION);
    }
    
    @Override
    boolean readCache(JavaProject jproject, String rootPath, String cacheName, boolean bootModule) {
        Path path = Paths.get(rootPath, BYTECODE_CACHE_DIR, cacheName + "." + cacheExtension);
        
        try {
            File file = path.toFile();
            if (!file.exists()) {
                return false;
            } else {
                if (!file.canRead()) {
                    file.delete();
                    return false;
                }
            }
            
            jproject.getModelBuilderImpl().getLogger().recordLog("** Read cache " + file.getName());
            
            SAXParserFactory factory = SAXParserFactory.newInstance();
            factory.setNamespaceAware(true);
            
            SAXParser parser = factory.newSAXParser();
            DefaultHandler handler = new ProxyImporter(jproject, path.toString(), bootModule);
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
    
    private class ProxyImporter extends DefaultHandler {
        
        private JavaProject jproject;
        private boolean bootModule;
        private String cacheName;
        
        private BytecodeClassProxy clazz = null;
        
        ProxyImporter(JavaProject jproject, String cacheName, boolean bootModule) {
            this.jproject = jproject;
            this.bootModule = bootModule;
            this.cacheName = cacheName;
        }
        
        @Override
        public void startElement(String uri, String name, String qname, Attributes attrs) throws SAXException {
            if (qname.equals(ProjectElem)) {
                Map<String, String> attr = attributes(attrs);
                String format = attr.get(FormatVersionAttr);
                if (!format.equals(FORMAT_VERSION)) {
                    throw new SAXException("Cache format was changed");
                }
                return;
            }
            
            if (qname.equals(ClassElem)) {
                clazz = new BytecodeClassProxy(cacheName, bootModule, jproject.getCFGStore().getBCStore());
                clazz.addClass(attributes(attrs));
                return;
            }
            
            if (qname.equals(MethodElem)) {
                if (clazz != null) {
                    clazz.addMethod(attributes(attrs));
                }
                return;
            }
            
            if (qname.equals(FieldElem)) {
                if (clazz != null) {
                    clazz.addField(attributes(attrs));
                }
                return;
            }
        }
        
        @Override
        public void endElement(String uri, String name, String qname) throws SAXException {
            if (qname.equals(ClassElem)) {
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
    
    @Override
    boolean writeCache(String rootPath, List<? extends BytecodeClassCache>classes, String cacheName) {
        Path path = Paths.get(rootPath, BYTECODE_CACHE_DIR, cacheName + "." + cacheExtension);
        
        try {
            CacheExporter exporter = new CacheExporter();
            Document doc = exporter.createDocument(rootPath, classes);
            
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            DOMSource src = new DOMSource(doc);
            
            StringWriter writer = new StringWriter();
            transformer.transform(src, new StreamResult(writer));
            
            File file = path.toFile();
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
    
    private class CacheExporter {
        
        Document createDocument(String path, Collection<? extends BytecodeClassCache> classes) throws ParserConfigurationException {
            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document doc = builder.newDocument();
            Element projectElem = doc.createElement(ProjectElem);
            projectElem.setAttribute(PathAttr, path);
            projectElem.setAttribute(TimeAttr, TimeInfo.getTimeAsISOString(TimeInfo.getCurrentTime()));
            projectElem.setAttribute(FormatVersionAttr, FORMAT_VERSION);
            doc.appendChild(projectElem);
            
            for (BytecodeClassCache clazz : classes) {
                exportClass(doc, projectElem, clazz);
            }
            return doc;
        }
        
        private void exportClass(Document doc, Element parent, BytecodeClassCache clazz) {
            Element classElem = doc.createElement(ClassElem);
            parent.appendChild(classElem);
            
            for (Entry<String, String> entry : clazz.getClassCacheData().entrySet()) {
                classElem.setAttribute(entry.getKey(), entry.getValue());
            }
            exportMethod(doc, classElem, clazz);
        }
        
        private void exportMethod(Document doc, Element parent, BytecodeClassCache clazz) {
            for (Map<String, String> method : clazz.getMethodCacheData()) {
                Element methodElem = doc.createElement(MethodElem);
                for (Entry<String, String> entry : method.entrySet()) {
                    methodElem.setAttribute(entry.getKey(), entry.getValue());
                }
                parent.appendChild(methodElem);
            }
            
            for (Map<String, String> field : clazz.getFieldCacheData()) {
                Element fieldElem = doc.createElement(FieldElem);
                for (Entry<String, String> entry : field.entrySet()) {
                    fieldElem.setAttribute(entry.getKey(), entry.getValue());
                }
                parent.appendChild(fieldElem);
            }
        }
    }
}
