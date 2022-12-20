/*
 *  Copyright 2022
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.cfg.internal.refmodel;

import org.jtool.srcmodel.JavaProject;
import org.jtool.jxplatform.builder.TimeInfo;
import java.util.Collection;
import java.util.Map;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.List;
import java.io.File;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.nio.file.Files;
import javax.json.Json;
import javax.json.JsonValue;
import javax.json.JsonString;
import javax.json.JsonWriterFactory;
import javax.json.stream.JsonGenerator;
import javax.json.JsonObject;
import javax.json.JsonArray;
import javax.json.JsonStructure;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import javax.json.JsonReader;
import javax.json.JsonWriter;

/**
 * Reads and writes the cache of byte-code classes in the JSON format.
 * 
 * @author Katsuhisa Maruyama
 */
class BytecodeCacheManagerJSON extends BytecodeCacheManager {
    
    private static final String CACHE_FILE_EXTION = "cache.json";
    
    BytecodeCacheManagerJSON() {
        super(CACHE_FILE_EXTION);
    }
    
    @Override
    boolean readCache(JavaProject jproject, String rootPath, String cacheName, boolean bootModule) {
        Path path = Paths.get(rootPath, BYTECODE_CACHE_DIR, cacheName + "." + cacheExtension);
        
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
        
        try (BufferedReader reader = Files.newBufferedReader(path)) {
            parseJSONProject(jproject, cacheName, bootModule, reader);
            return true;
        } catch (IOException e) {
            System.err.println("IO error2 " + e.getMessage());
            return false;
        }
    }
    
    private boolean parseJSONProject(JavaProject jproject, String cacheName, boolean bootModule,
            BufferedReader reader) throws ClassCastException {
        JsonReader jsonReader = Json.createReader(reader);
        JsonObject jsonProject = jsonReader.readObject();
        
        String format = jsonProject.getString(FormatVersionAttr);
        if (!format.equals(FORMAT_VERSION)) {
            return false;
        }
        
        parseJSONClass(jproject, cacheName, bootModule, jsonProject);
        return true;
    }
    
    private void parseJSONClass(JavaProject jproject, String cacheName, boolean bootModule,
            JsonObject jsonProject) throws ClassCastException {
        JsonArray classArray = jsonProject.getJsonArray(ClassElem);
        for (int i = 0; i < classArray.size(); i++) {
            BytecodeClassProxy clazz = new BytecodeClassProxy(cacheName, bootModule, jproject.getCFGStore().getBCStore());
            JsonObject jsonClass = classArray.getJsonObject(i);
            
            Map<String, String> attrs = new HashMap<>();
            for (Entry<String, JsonValue> entry : jsonClass.entrySet()) {
                String key = entry.getKey();
                if (key.equals(MethodElem)) {
                    parseJSONMethod(clazz, (JsonArray)entry.getValue());
                } else if (key.equals(FieldElem)) {
                    parseJSONField(clazz, (JsonArray)entry.getValue());
                } else {
                    attrs.put(entry.getKey(), ((JsonString)entry.getValue()).getString());
                }
            }
            clazz.addClass(attrs);
            clazz.collectInfo();
        }
    }
    
    private void parseJSONMethod(BytecodeClassProxy clazz, JsonArray methodArray) {
        for (int i = 0; i < methodArray.size(); i++) {
            JsonObject jsonMethod = methodArray.getJsonObject(i);
            
            Map<String, String> attrs = new HashMap<>();
            for (Entry<String, JsonValue> entry : jsonMethod.entrySet()) {
                attrs.put(entry.getKey(), ((JsonString)entry.getValue()).getString());
            }
            clazz.addMethod(attrs);
        }
    }
    
    private void parseJSONField(BytecodeClassProxy clazz, JsonArray fieldArray) {
        for (int i = 0; i < fieldArray.size(); i++) {
            JsonObject jsonField = fieldArray.getJsonObject(i);
            
            Map<String, String> attrs = new HashMap<>();
            for (Entry<String, JsonValue> entry : jsonField.entrySet()) {
                attrs.put(entry.getKey(), ((JsonString)entry.getValue()).getString());
            }
            clazz.addField(attrs);
        }
    }
    
    @Override
    boolean writeCache(String rootPath, List<? extends BytecodeClassCache>classes, String cacheName) {
        Path path = Paths.get(rootPath, BYTECODE_CACHE_DIR, cacheName + "." + cacheExtension);
        
        File file = path.toFile();
        if (file.exists()) {
            file.delete();
        }
        
        try (BufferedWriter writer = Files.newBufferedWriter(path)) {
            JsonObject jsonObject = getJSONProjectBuilder(cacheName, rootPath, classes).build();
            
            writer.write(stringify(jsonObject));
            writer.flush();
            return true;
        } catch (IOException e) {
            System.err.println("IO error " + e.getMessage());
            return false;
        }
    }
    
    private static String stringify(JsonStructure json) {
        StringWriter stringWriter = new StringWriter();
        Map<String, Object> config = new HashMap<>();
        config.put(JsonGenerator.PRETTY_PRINTING, Boolean.TRUE);
        JsonWriterFactory factory = Json.createWriterFactory(config);
        JsonWriter jsonWriter = factory.createWriter(stringWriter);
        jsonWriter.write(json);
        jsonWriter.close();
        return stringWriter.toString();
    }
    
    private JsonObjectBuilder getJSONProjectBuilder(String cacheName, String path,
            Collection<? extends BytecodeClassCache> classes) {
        JsonObjectBuilder builder = Json.createObjectBuilder();
        builder.add(PathAttr, path);
        builder.add(TimeAttr, TimeInfo.getTimeAsISOString(TimeInfo.getCurrentTime()));
        builder.add(FormatVersionAttr, FORMAT_VERSION);
        
        builder.add(ClassElem, getJSONClassBuilder(classes));
        return builder;
    }
    
    private JsonArrayBuilder getJSONClassBuilder(Collection<? extends BytecodeClassCache> classes) {
        JsonArrayBuilder classBuilder = Json.createArrayBuilder();
        for (BytecodeClassCache clazz : classes) {
            JsonObjectBuilder builder = Json.createObjectBuilder();
            for (Entry<String, String> entry : clazz.getClassCacheData().entrySet()) {
                builder.add(entry.getKey(), entry.getValue());
            }
            
            builder.add(MethodElem, getJSONMethodBuilder(clazz));
            builder.add(FieldElem, getJSONFieldBuilder(clazz));
            classBuilder.add(builder);
        }
        return classBuilder;
    }
    
    private JsonArrayBuilder getJSONMethodBuilder(BytecodeClassCache clazz) {
        JsonArrayBuilder methodBuilder = Json.createArrayBuilder();
        for (Map<String, String> method : clazz.getMethodCacheData()) {
            JsonObjectBuilder builder = Json.createObjectBuilder();
            for (Entry<String, String> entry : method.entrySet()) {
                builder.add(entry.getKey(), entry.getValue());
            }
            methodBuilder.add(builder);
        }
        return methodBuilder;
    }
    
    private JsonArrayBuilder getJSONFieldBuilder(BytecodeClassCache clazz) {
        JsonArrayBuilder fieldBuilder = Json.createArrayBuilder();
        for (Map<String, String> field : clazz.getFieldCacheData()) {
            JsonObjectBuilder builder = Json.createObjectBuilder();
            for (Entry<String, String> entry : field.entrySet()) {
                builder.add(entry.getKey(), entry.getValue());
            }
            fieldBuilder.add(builder);
        }
        return fieldBuilder;
    }
}
