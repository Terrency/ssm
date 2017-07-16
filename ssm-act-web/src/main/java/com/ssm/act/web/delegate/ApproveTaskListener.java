package com.ssm.act.web.delegate;

import com.ssm.common.web.util.SecurityHelper;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;

/**
 * @see <a>https://community.alfresco.com/thread/216953-how-to-verify-user-task-assignment-at-runtime</a>
 */
public class ApproveTaskListener implements TaskListener {

    @Override
    public void notify(DelegateTask delegateTask) {
        delegateTask.setAssignee(SecurityHelper.getActiveUser().getManager());
    }

}
