/*
 *  Copyright 2022
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.cfg.builder;

import org.jtool.cfg.CFG;
import org.jtool.srcmodel.JavaProject;
import java.util.Set;
import java.util.HashSet;

/**
 * Collection of resolvers.
 * 
 * @author Katsuhisa Maruyama
 */
class Resolver {
    
    private static Set<String> visited;
    
    static void initialize() {
        visited = new HashSet<>();
    }
    
    static void resolveReferences(JavaProject jproject, CFG cfg) {
        if (!visited.contains(cfg.getQualifiedName().fqn())) {
            visited.add(cfg.getQualifiedName().fqn());
            
            ReceiverTypeResolver receiverTypeResolver = new ReceiverTypeResolver(jproject);
            receiverTypeResolver.findReceiverTypes(cfg);
            
            FieldReferenceResolver referenceResolver = new FieldReferenceResolver();
            referenceResolver.findDefUseFields(cfg);
        }
    }
    
    static void resolveLocalAlias(JavaProject jproject, CFG cfg) {
        LocalAliasResolver localAliasResolver = new LocalAliasResolver(jproject);
        localAliasResolver.resolve(cfg);
    }
}