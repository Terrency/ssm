package com.ssm.act.core.delegate;

import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;

public class ApproveTaskListener implements TaskListener {

    public static final String ACT_RU_VARIABLE_KEY = "approver";

    @Override
    public void notify(DelegateTask delegateTask) {
        Object variable = delegateTask.getVariable(ACT_RU_VARIABLE_KEY);
        if (variable != null) {
            delegateTask.setAssignee(variable.toString());
        }
    }

}
