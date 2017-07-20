package com.ssm.act.core.delegate;

import com.ssm.common.base.util.ActivitiHelper;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;

public class ApproveTaskListener implements TaskListener {

    @Override
    public void notify(DelegateTask delegateTask) {
        delegateTask.setAssignee((String) delegateTask.getVariable(ActivitiHelper.APPROVER_PLACEHOLDER_KEY));
    }

}
