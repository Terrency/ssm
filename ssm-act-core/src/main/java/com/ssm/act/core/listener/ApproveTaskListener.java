package com.ssm.act.core.listener;

import com.ssm.common.util.SecurityHelper;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;

public class ApproveTaskListener implements TaskListener {

    @Override
    public void notify(DelegateTask delegateTask) {
        delegateTask.setAssignee(SecurityHelper.getActiveUser().getManager());
    }

}
