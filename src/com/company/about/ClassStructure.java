package com.company.about;

import java.lang.reflect.*;
import java.util.StringJoiner;

public class ClassStructure {

    public static void about(Class aClass){
        Field[] declaredFields = aClass.getDeclaredFields();
        Method[] declaredMethods = aClass.getDeclaredMethods();

        System.out.println();
        System.out.println("///////////////////////////");
        System.out.println("/////////FIELDS////////////");
        System.out.println("///////////////////////////");
        for (Field declaredField : declaredFields) {
            Type genericType = declaredField.getGenericType();

            String type = typeToString(genericType.toString());
            String modifiers = Modifier.toString(declaredField.getModifiers());
            String name = declaredField.getName();

            System.out.println(modifiers + " " + type + " " + name + ";");
        }

        System.out.println();
        System.out.println("///////////////////////////");
        System.out.println("/////////METHODS///////////");
        System.out.println("///////////////////////////");
        System.out.println();

        for (Method method : declaredMethods) {
            if (method.isSynthetic()) {continue;}
            Type returnType = method.getGenericReturnType();

            String type = typeToString(returnType.toString());
            String modifiers = Modifier.toString(method.getModifiers());
            String name = method.getName();

            StringJoiner stringJoiner = new StringJoiner(", ");
            for (Parameter parameter : method.getParameters()) {
                String typePar = typeToString(parameter.getAnnotatedType().toString());
                String typeName = parameter.getName();
                stringJoiner.add(typePar + " " + typeName);
            }
            String parameterList = stringJoiner.toString();

            System.out.println(modifiers + " " + type + " " + name + "(" + parameterList + ");");
        }
    }

    private static String typeToString(String type){
        int indexBracket = type.indexOf("<");
        int indexComma = type.indexOf(",");
        //первая база рекурсии (если нет других типов и дженериков)
        if(indexBracket == -1 && indexComma == -1){
            String[] split = type.split("\\.");
            return split[split.length-1];
        }

        if(indexBracket > 0 && (indexComma == -1 || indexBracket < indexComma)){
            //угольная скобка
            String type1 = type.substring(0, indexBracket);
            String type2 = type.substring(indexBracket + 1);

            return typeToString(type1) + "<" + typeToString(type2);
        }else{
            //запятая
            String type1 = type.substring(0, indexComma);
            String type2 = type.substring(indexComma + 1);

            return typeToString(type1) + ", " + typeToString(type2);
        }
    }
}
