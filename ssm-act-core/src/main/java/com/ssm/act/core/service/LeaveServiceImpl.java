package com.ssm.act.core.service;

import com.ssm.act.api.model.Leave;
import com.ssm.act.api.service.LeaveService;
import com.ssm.act.api.service.ProcessService;
import com.ssm.act.core.delegate.ApplyTaskListener;
import com.ssm.act.core.delegate.ApproveTaskListener;
import com.ssm.act.core.enums.LeaveStatus;
import com.ssm.act.core.mapper.LeaveMapper;
import com.ssm.act.core.mapper.extension.LeaveExtMapper;
import com.ssm.common.base.exception.BusinessException;
import com.ssm.common.base.model.ModelMap;
import com.ssm.common.base.page.Page;
import com.ssm.common.base.page.PageRequest;
import com.ssm.common.base.page.Pageable;
import com.ssm.common.base.util.ActivitiHelper;
import com.ssm.common.base.util.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service(LeaveService.BEAN_NAME)
public class LeaveServiceImpl implements LeaveService {

    @Autowired
    private LeaveMapper leaveMapper;

    @Autowired
    private LeaveExtMapper leaveExtMapper;

    @Autowired
    private ProcessService processService;

    @Override
    public int add(Leave leave) {
        leave.setStatus(LeaveStatus.INITIAL.getValue());
        return leaveMapper.insertSelective(leave);
    }

    @Override
    public int add(Collection<Leave> collection) {
        int count = 0;
        for (Leave leave : collection) {
            count += add(leave);
        }
        return count;
    }

    @Override
    public int update(Leave leave) {
        checkProcessStatus(leave.getId());
        return leaveMapper.updateByPrimaryKeySelective(leave);
    }

    @Override
    public int delete(Long id) {
        checkProcessStatus(id);
        return leaveMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int delete(Long[] ids) {
        int count = 0;
        for (Long id : ids) {
            count += delete(id);
        }
        return count;
    }

    /**
     * 启动流程实例并关联业务
     *
     * @param id BusinessKey
     * @return ProcessInstanceId
     */
    @Override
    public String startProcess(Long id, String applicant) {
        checkProcessStatus(id);
        Leave leave = new Leave();
        leave.setId(id);
        leave.setStatus(LeaveStatus.APPLYING.getValue());
        // 更新请假单状态标志
        int row = leaveMapper.updateByPrimaryKeySelective(leave);
        if (row != 1) {
            return null;
        }
        String pdKey = ActivitiHelper.getDefaultProcessDefinitionKey(leave.getClass());
        String bizKey = Long.toString(id);
        // 业务关联流程
        Map<String, Object> variables = new HashMap<>();
        variables.put(ActivitiHelper.PROCESS_VARIABLE_NAME, pdKey + Constant.PERIOD_SEPARATOR + bizKey);
        variables.put(ApplyTaskListener.ACT_RU_VARIABLE_KEY, applicant);
        // 启动流程实例
        return processService.startProcessInstanceByKey(pdKey, bizKey, variables).getProcessInstanceId();
    }

    @Override
    public void completeTask(String userId, String taskId, String comment, String approver) {
        Map<String, Object> variables = new HashMap<>();
        variables.put(ApproveTaskListener.ACT_RU_VARIABLE_KEY, approver);
        String businessKey = processService.getBusinessKey(taskId);
        Leave leave = getById(Long.parseLong(businessKey));
        if (leave.getStatus() == LeaveStatus.APPLYING.getValue()) {
            leave.setStatus(LeaveStatus.PENDING.getValue());
        }
        if (processService.completeTask(userId, taskId, comment, variables) == null) {
            leave.setStatus(LeaveStatus.COMPLETE.getValue());
        }
        // 更新请假状态标志
        leaveMapper.updateByPrimaryKeySelective(leave);
    }

    @Override
    public Leave getById(Long id) {
        return leaveMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<Map> getList(String applicant) {
        return leaveExtMapper.selectByApplicant(applicant);
    }

    @Override
    public List<Map> getList(ModelMap modelMap) {
        return leaveExtMapper.selectSelective(modelMap);
    }

    @Override
    public Page<Map> getPage(Pageable<ModelMap> pageable) {
        return leaveExtMapper.selectPage(pageable);
    }

    private void checkProcessStatus(Long id) {
        Leave leave = leaveMapper.selectByPrimaryKey(id);
        if (leave == null) {
            throw new BusinessException("记录不存在！");
        }
        if (leave.getStatus() != LeaveStatus.INITIAL.getValue()) {
            throw new BusinessException("只有当请假状态为【初始录入】时方可执行修改、删除和申请请假操作！");
        }
    }

}
