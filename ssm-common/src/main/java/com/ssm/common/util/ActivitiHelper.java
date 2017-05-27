package com.ssm.common.util;

public abstract class ActivitiHelper {

    public static final String PROCESS_DEFINITION_KEY_NAME = "pdKey";
    public static final String PROCESS_VARIABLE_NAME = "Process";

    public static String getDefaultProcessDefinitionKey(Class clazz) {
        return clazz.getSimpleName() + PROCESS_VARIABLE_NAME;
    }

}
