/*
 *  Copyright 2022
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.jxplatform.plugin.internal;

import org.jtool.jxplatform.plugin.Activator;
import org.jtool.jxplatform.project.ConsoleProgressMonitor;
import org.jtool.srcmodel.JavaProject;
import org.jtool.cfg.internal.refmodel.BytecodeClass;
import org.jtool.cfg.internal.refmodel.BytecodeName;
import org.jtool.cfg.internal.refmodel.BytecodeClassStore;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.ui.IWorkbenchWindow;
import java.util.Set;
import java.lang.reflect.InvocationTargetException;

/**
 * An object that stores information on byte-code classes for a plug-in.
 * 
 * @author Katsuhisa Maruyama
 */
class BytecodeClassStoreForPlugin extends BytecodeClassStore {
    
    public BytecodeClassStoreForPlugin(JavaProject jproject) {
        super(jproject);
    }
    
    @Override
    protected void parseBytecodeClass(ConsoleProgressMonitor monitor, Set<BytecodeName> names) {
        try {
            IWorkbenchWindow workbenchWindow = Activator.getPlugin().getWorkbenchWindow();
            workbenchWindow.run(true, true, new IRunnableWithProgress() {
                
                @Override
                public void run(IProgressMonitor pm) throws InvocationTargetException, InterruptedException {
                    monitor.begin(names.size());
                    pm.beginTask("Parsing bytecode classes... ", names.size());
                    int count = 1;
                    
                    for (BytecodeName bytecodeName : names) {
                        pm.subTask(count + "/" + names.size() + " - " + bytecodeName.getClassName());
                        
                        parseBytecodeClass(bytecodeName);
                        
                        if (pm.isCanceled()) {
                            monitor.done();
                            pm.done();
                            throw new InterruptedException();
                        }
                        
                        monitor.work(1);
                        pm.worked(1);
                        count++;
                    }
                    
                    monitor.done();
                    pm.done();
                }
            });
        } catch (InvocationTargetException | InterruptedException e) { /* empty */ }
    }
    
    @Override
    protected void collectBytecodeClassInfo(Set<BytecodeClass> bclasses, ConsoleProgressMonitor monitor) {
        try {
            IWorkbenchWindow workbenchWindow = Activator.getPlugin().getWorkbenchWindow();
            workbenchWindow.run(true, true, new IRunnableWithProgress() {
                
                @Override
                public void run(IProgressMonitor pm) throws InvocationTargetException, InterruptedException {
                    
                    monitor.begin(bclasses.size());
                    pm.beginTask("Collecting bytecode classes information... ", bclasses.size());
                    int count = 1;
                    
                    for (BytecodeClass bclass : bclasses) {
                        pm.subTask(count + "/" + bclasses.size() + " - " + bclass.getName());
                        
                        collectBytecodeClassInfo(bclass);
                        
                        if (pm.isCanceled()) {
                            monitor.done();
                            pm.done();
                            throw new InterruptedException();
                        }
                        
                        monitor.work(1);
                        pm.worked(1);
                        count++;
                    }
                    monitor.done();
                    pm.done();
                }
            });
        } catch (InvocationTargetException | InterruptedException e) { /* empty */ }
    }
}
