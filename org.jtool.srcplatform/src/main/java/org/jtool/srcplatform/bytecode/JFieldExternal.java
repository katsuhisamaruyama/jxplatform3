/*
 *  Copyright 2020
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.srcplatform.bytecode;

import org.jtool.srcmodel.QualifiedName;
import java.util.Set;

/**
 * Concise information on a field outside the project.
 * 
 * @author Katsuhisa Maruyama
 */
public class JFieldExternal extends JField {
    
    JFieldExternal(String signature, JClass declaringClass) {
        super(new QualifiedName(declaringClass.getClassName(), signature), declaringClass);
    }
    
    @Override
    protected void findDefUseFields(Set<JMethod> visitedMethods, Set<JField> visitedFields, int count) {
    }
}
