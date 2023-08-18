/*
 *  Copyright 2022-2023
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.cfg.internal;

import org.jtool.cfg.CFG;
import org.jtool.srcmodel.JavaProject;
import java.util.concurrent.TimeoutException;

/**
 * Collection of resolvers.
 * 
 * @author Katsuhisa Maruyama
 */
class Resolver {
    
    private static final int TIMEOUT_SEC = 60;
    
    void resolveReferences(final JavaProject jproject, final CFG cfg) {
        Runnable task = new Runnable() {
            
            @Override
            public void run() {
                resolveReferences0(jproject, cfg);
            }
        };
        try {
            jproject.getModelBuilderImpl().performTaskWithTimeout(task, TIMEOUT_SEC);
        } catch (TimeoutException e) {
            jproject.getModelBuilderImpl().printErrorOnMonitor(
                    "**Timeout occurred in resoving references: " + cfg.getQualifiedName().fqn());
            jproject.getModelBuilderImpl().getLogger().recordTimeoutError(cfg.getQualifiedName().fqn());
        }
    }
    
    private void resolveReferences0(JavaProject jproject, CFG cfg) {
        try {
            ReceiverTypeResolver receiverTypeResolver = new ReceiverTypeResolver(jproject);
            receiverTypeResolver.findReceiverTypes(cfg);
            
            FieldReferenceResolver referenceResolver = new FieldReferenceResolver();
            referenceResolver.findDefUseFields(cfg);
        } catch (InterruptedException e) {
            // Executes workaround resolution if needed
        }
    }
    
    void resolveLocalAlias(JavaProject jproject, CFG cfg) {
        Runnable task = new Runnable() {
            
            @Override
            public void run() {
                resolveLocalAlias0(jproject, cfg);
            }
        };
        
        try {
            jproject.getModelBuilderImpl().performTaskWithTimeout(task, TIMEOUT_SEC);
        } catch (TimeoutException e) {
            jproject.getModelBuilderImpl().printErrorOnMonitor(
                    "**Timeout occurred in resoving alias relations: " + cfg.getQualifiedName().fqn());
            jproject.getModelBuilderImpl().getLogger().recordTimeoutError(cfg.getQualifiedName().fqn());
        }
    }
    
    private void resolveLocalAlias0(JavaProject jproject, CFG cfg) {
        try {
            LocalAliasResolver localAliasResolver = new LocalAliasResolver(jproject);
            localAliasResolver.resolve(cfg);
        } catch (InterruptedException e) {
            // Executes workaround resolution if needed
        }
    }
}