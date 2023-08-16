/*
 *  Copyright 2022
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.jxplatform.project;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.FileTime;
import java.time.Instant;
import net.lingala.zip4j.ZipFile;
import net.lingala.zip4j.model.FileHeader;
import java.nio.file.StandardCopyOption;
import java.util.stream.Stream;

/**
 * Extracts a jar file stored to the library from an AAR (Android Studio) file.
 * 
 * @author Katsuhisa Maruyama
 */
class AARFile {
    
    private final static String UNZIP_TMPDIR = "unzip_tmp";
    private final static String CLASSES_FILE = "classes.jar";
    
    static void extract(Path libpath) {
        Path tmppath = libpath.resolve(UNZIP_TMPDIR);
        try {
            Files.createDirectory(tmppath);
        } catch (IOException e) {
            return;
        }
        
        File dir = libpath.toFile();
        if (dir != null && dir.exists()) {
            try (Stream<Path> stream = Files.list(libpath)) {
                stream.forEach(p -> {
                    String filename = p.toString();
                    if (filename.endsWith(".aar")) {
                        String name = filename.substring(0, filename.length() - ".aar".length());
                        Path aarname = libpath.resolve(name + ".aar");
                        Path jarname = libpath.resolve(name + ".jar");
                        if (!jarname.toFile().exists()) {
                            decompress(tmppath, aarname);
                            rename(tmppath, jarname);
                        }
                    }
                });
            } catch(IOException e) { /* empty */ }
        }
        
        try {
        Files.delete(tmppath);
        } catch (IOException ie) { /* empty */ }
    }
    
    private static void decompress(Path tmpdir, Path aarname) {
        try (ZipFile zipfile = new ZipFile(aarname.toString())) {
            FileHeader fheader = 
                    zipfile.getFileHeaders().stream()
                    .filter(fh -> fh.getFileName().equals(CLASSES_FILE)).findFirst().orElse(null);
            if (fheader != null) {
                zipfile.extractFile(fheader, tmpdir.toString(), fheader.getFileName());
            }
        } catch (Exception e) { e.printStackTrace();}
    }
    
    private static void rename(Path tmppath, Path jarname) {
        try {
            Path srcfile = tmppath.resolve(CLASSES_FILE);
            Files.move(srcfile, jarname, StandardCopyOption.REPLACE_EXISTING);
            Files.setLastModifiedTime(jarname, FileTime.from(Instant.now()));
        } catch (IOException e) { /* empty */ }
    }
}
