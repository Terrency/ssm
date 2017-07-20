package com.ssm.common.base.util;

public abstract class ActivitiHelper {

    public static final String APPLICANT_PLACEHOLDER_KEY = "applicant";
    public static final String APPROVER_PLACEHOLDER_KEY = "approver";

    public static final String PROCESS_DEFINITION_KEY_NAME = "pdKey";
    public static final String PROCESS_VARIABLE_NAME = "Process";

    public static String getDefaultProcessDefinitionKey(Class clazz) {
        return clazz.getSimpleName() + PROCESS_VARIABLE_NAME;
    }

}
