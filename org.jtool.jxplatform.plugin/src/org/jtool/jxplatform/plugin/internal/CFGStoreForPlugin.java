/*
 *  Copyright 2022
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.jxplatform.plugin.internal;

import org.jtool.cfg.internal.CFGStore;

/**
 * A repository that stores information on CFGs in the project for a plug-in.
 * 
 * @author Katsuhisa Maruyama
 */
public class CFGStoreForPlugin extends CFGStore {
    
    public CFGStoreForPlugin() {
        super();
    }
    
    @Override
    protected void createBCStore() {
        this.bcStore = new BytecodeClassStoreForPlugin(jproject);
        bcStore.create();
    }
}
