

import com.company.dao.RentCompany;
import com.company.tests.RentCompanyStructure;

import java.lang.reflect.*;
import java.util.Arrays;
import java.util.StringJoiner;

public class About {
    public static void main(String[] args) {
//        about(RentCompany.class);
        about2(RentCompany.class);
    }

//    public static void about(Class aClass){
//        //печатает на консоль всю информацию об этом классе (все поля и методы без тел)
//
//        RentCompanyStructure structure = new RentCompanyStructure(aClass);
//        structure.printRentCompanyFields();
////        structure.printRentCompanyMethods(aClass.getDeclaredMethods());
//
//
//
//
////        for (Field field : aClass.getDeclaredFields()) {
////            String access = field.toString().split("\\s")[0];
////            String type = field.getAnnotatedType().getType().getTypeName().split("\\<")[0].split("\\.")[2];
////            if(field.getAnnotatedType().getType().getTypeName().split("\\<").length > 1) {
////                String typing = field.getAnnotatedType().getType().getTypeName();
////                String key = typing.split("\\<")[1].split("\\,")[0].split("\\.")[2];
////                String value = typing.split("\\,")[1];
////                String value1 = value.split("\\<").length == 1 ?
////                        value.split("\\<")[0].split("\\.")[value.split("\\<")[0].split("\\.").length-1] :
////                        value.split("\\<")[0].split("\\.")[2] + "<" +
////                        value.split("\\<")[value.split("\\<").length-1].split("\\.")[3];
////                System.out.println(access + " " + type + "<" + key + ", " + value1);
////            }
////        }
////        System.out.println();
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
////        Method[] methods = aClass.getDeclaredMethods();
////
////        for (Method method : methods) {
////            if(method.isSynthetic()) continue;
////            String[] methodData = method.getAnnotatedReturnType().getType().getTypeName().split("\\.");
////            String returnType;
////            if(method.getAnnotatedReturnType().getType().getTypeName().split("\\<").length == 1) {
////                returnType = methodData.length > 1 ? methodData[methodData.length-1] : methodData[0];
////            } else {
//////                if(method.getAnnotatedReturnType().getType().getTypeName().split("\\<") )
////            }
////            System.out.println(method.getAnnotatedReturnType().getType().getTypeName());
////
////            String methodName = method.getName();
////            StringBuilder listOfParameters = new StringBuilder();
//////            listOfParameters.append("(");
////            Parameter[] parameters = method.getParameters();
////            for (int i = 0; i < parameters.length; i++) {
////                String[] parameterData = parameters[i].getType().getTypeName().split("\\.");
////                String typeOfParameter = parameterData.length > 1 ? parameterData[2] : parameterData[0];
////                listOfParameters.append(typeOfParameter).append(" ").append(parameters[i].getName());
////                if(i < parameters.length - 1) listOfParameters.append(", ");
////            }
//////            listOfParameters.append(");");
//////            System.out.println(returnType + " " + methodName + listOfParameters);
////        }
//    }


    public static void about2(Class aClass){
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

    public static String typeToString(String type){
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
