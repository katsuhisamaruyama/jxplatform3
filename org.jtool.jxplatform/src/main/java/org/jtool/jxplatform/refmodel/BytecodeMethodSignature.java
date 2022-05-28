/*
 *  Copyright 2022
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.jxplatform.refmodel;

import java.io.File;

/**
 * Obtains the signature string for a method or a field from its bytecode signature descriptor.
 * 
 * MethodDescriptor    := ( ParameterDescriptor* ) ReturnDescriptor
 * ParameterDescriptor := FieldType
 * ReturnDescriptor    := FieldType | VoidDescriptor
 * VoidDescriptor      := V
 *
 * FieldDescriptor := FieldType
 * FieldType       := BaseType | ObjectType | ArrayType
 * BaseType        := B | C | D | F | I | J | S | Z
 * ObjectType      := L ClassName ;
 * ArrayType       := [ ComponentType
 * ComponentType   := FieldType
 * 
 * @author Katsuhisa Maruyama
 */
public class BytecodeMethodSignature {
    
    private static int comsumedCharsLength = 0;
    
    static final String INVALID_SIGNATURE = "!";
    
    public static String methodSignatureToString(String signature, BytecodeClassStore bcStore) {
        StringBuilder buf = new StringBuilder();
        if (signature.charAt(0) == '(') {
            buf.append("(");
        } else {
            return INVALID_SIGNATURE;
        }
        
        int index = 1;
        while (signature.charAt(index) != ')') {
            String type = variableTypeToString(signature.substring(index), bcStore);
            buf.append(" ");
            buf.append(type);
            index = index + comsumedCharsLength;
        }
        buf.append(" )");
        
        /*
         * Without a return type
         *
        index++;
        if (signature.charAt(index) != 'V') {
            buf.append(": void");
        } else {
            String type = fieldSignatureToString(signature.substring(index), bcStore);
            buf.append(":");
            buf.append(type);
        }
        */
        return buf.toString();
    }
    
    private static String fieldSignatureToString(String signature, BytecodeClassStore bcStore) {
        return variableTypeToString(signature, bcStore);
    }
    
    private static String variableTypeToString(String signature, BytecodeClassStore bcStore) {
        comsumedCharsLength = 1;
        switch (signature.charAt(0)) {
            case 'B':
                return "byte";
            case 'C':
                return "char";
            case 'D':
                return "double";
            case 'F':
                return "float";
            case 'I':
                return "int";
            case 'J':
                return "long";
            case 'S':
                return "short";
            case 'Z':
                return "boolean";
                
            case 'L':
                int index = signature.indexOf(';');
                if (index < 0) {
                    return INVALID_SIGNATURE;
                }
                comsumedCharsLength = index + 1;
                String className = signature.substring(1, index).replace(File.separatorChar, '.');
                String cname = bcStore.getCanonicalClassName(className);
                if (cname != null) {
                    return cname;
                } else {
                    return INVALID_SIGNATURE;
                }
                
            case '[':
                StringBuilder brackets = new StringBuilder();
                int count;
                for (count = 0; signature.charAt(count) == '['; count++) {
                    brackets.append("[]");
                }
                String type = fieldSignatureToString(signature.substring(count), bcStore);
                comsumedCharsLength = comsumedCharsLength + count;
                return type + brackets.toString();
                
            default:
                return INVALID_SIGNATURE;
        }
    }
}
