/*
 *  Copyright 2022
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.cfg.builder;

import org.jtool.cfg.CFG;
import org.jtool.srcmodel.JavaProject;

/**
 * Collection of resolvers.
 * 
 * @author Katsuhisa Maruyama
 */
class Resolver {
    
    static void resolveReferences(JavaProject jproject, CFG cfg) {
        ReceiverTypeResolver receiverTypeResolver = new ReceiverTypeResolver(jproject);
        receiverTypeResolver.findReceiverTypes(cfg);
        
        FieldReferenceResolver referenceResolver = new FieldReferenceResolver();
        referenceResolver.findDefUseFields(cfg);
    }
    
    static void resolveLocalAlias(JavaProject jproject, CFG cfg) {
        LocalAliasResolver localAliasResolver = new LocalAliasResolver(jproject);
        localAliasResolver.resolve(cfg);
    }
}