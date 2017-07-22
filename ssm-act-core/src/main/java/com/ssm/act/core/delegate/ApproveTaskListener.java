package com.ssm.act.core.delegate;

import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;

public class ApproveTaskListener implements TaskListener {

    public static final String ACT_RU_VARIABLE_KEY = "approver";

    @Override
    public void notify(DelegateTask delegateTask) {
        delegateTask.setAssignee(delegateTask.getVariable(ACT_RU_VARIABLE_KEY).toString());
    }

}
