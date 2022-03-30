package com.company.tests;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RentCompanyStructure {
    private List<StringBuilder> stringsForPrinting;
    private int index = 0;
    private Field[] fields;
    private StringBuilder substringOfTheField;

    public RentCompanyStructure(Class aClass) {
        this.fields = aClass.getDeclaredFields();
        this.stringsForPrinting = new ArrayList<>();
        this.substringOfTheField = new StringBuilder();
    }

    public String printRentCompanyFields() {
        if(index == stringsForPrinting.size()) {
            stringsForPrinting.add(new StringBuilder(fields[index].toString().split("\\s")[0] + " "));
            substringOfTheField.append(fields[index].getAnnotatedType());
        }
        if (substringOfTheField.indexOf("<") > 0) {
            String str = substringOfTheField.toString().split("\\<")[0].split("\\.")
                    [substringOfTheField.toString().split("\\<")[0].split("\\.").length-1];
            stringsForPrinting.get(index).append(str).append("<");
            substringOfTheField.delete(0, (substringOfTheField.indexOf("<") + 1));
            if (substringOfTheField.indexOf(",") > 0) {
                String[] strings = substringOfTheField.toString().split("\\,");
                stringsForPrinting.get(index).append(strings[0].split("\\.")
                        [strings[0].split("\\.").length - 1]).append(", ");
                substringOfTheField.delete(0, (substringOfTheField.indexOf(",") + 1));
                printRentCompanyFields();
            }
        }
        stringsForPrinting.get(index).append(substringOfTheField.toString().split("\\.")
                [substringOfTheField.toString().split("\\.").length - 1]);
        stringsForPrinting.get(index).append(" ").append(fields[index].getName());
        substringOfTheField = new StringBuilder();

        if(index < (fields.length - 1)) {
            index++;
            printRentCompanyFields();
        }
//        if(index == (fields.length - 1)) print(stringsForPrinting);

        return "";
    }

    public void printRentCompanyMethods(Method[] methods) {
//        Method[] methods = aClass.getDeclaredMethods();
//
//        for (Method method : methods) {
//            if (method.isSynthetic()) continue;
//            String[] methodData = method.getAnnotatedReturnType().getType().getTypeName().split("\\.");
//            String returnType;
//            if (method.getAnnotatedReturnType().getType().getTypeName().split("\\<").length == 1) {
//                returnType = methodData.length > 1 ? methodData[methodData.length - 1] : methodData[0];
//            } else {
////                if(method.getAnnotatedReturnType().getType().getTypeName().split("\\<") )
//            }
//            System.out.println(method.getAnnotatedReturnType().getType().getTypeName());
//
//            String methodName = method.getName();
//            StringBuilder listOfParameters = new StringBuilder();
////            listOfParameters.append("(");
//            Parameter[] parameters = method.getParameters();
//            for (int i = 0; i < parameters.length; i++) {
//                String[] parameterData = parameters[i].getType().getTypeName().split("\\.");
//                String typeOfParameter = parameterData.length > 1 ? parameterData[2] : parameterData[0];
//                listOfParameters.append(typeOfParameter).append(" ").append(parameters[i].getName());
//                if (i < parameters.length - 1) listOfParameters.append(", ");
//            }
////            listOfParameters.append(");");
////            System.out.println(returnType + " " + methodName + listOfParameters);
//        }
    }

    public void print(List<StringBuilder> strings) {for (StringBuilder string : strings) System.out.println(string);}
}
