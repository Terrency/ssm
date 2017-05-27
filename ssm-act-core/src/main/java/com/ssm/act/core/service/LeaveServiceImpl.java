package com.ssm.act.core.service;

import com.ssm.act.api.model.Leave;
import com.ssm.act.api.service.LeaveService;
import com.ssm.act.api.service.ProcessService;
import com.ssm.act.core.mapper.LeaveMapper;
import com.ssm.act.core.mapper.extension.LeaveExtMapper;
import com.ssm.common.exception.BusinessException;
import com.ssm.common.model.ModelMap;
import com.ssm.common.page.Page;
import com.ssm.common.page.PageRequest;
import com.ssm.common.util.ActivitiHelper;
import com.ssm.common.util.Constant;
import com.ssm.common.util.SecurityHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service(LeaveService.BEAN_NAME)
public class LeaveServiceImpl implements LeaveService {

    // 状态常量：审批中
    // public static final String STATUS_RUNNING = "审批中";
    // 状态常量：已通过
    // public static final String STATUS_APPROVED = "已通过";
    // 状态常量：未通过
    // public static final String STATUS_REJECTED = "未通过";

    @Autowired
    private LeaveMapper leaveMapper;

    @Autowired
    private LeaveExtMapper leaveExtMapper;

    @Autowired
    private ProcessService processService;

    @Override
    public int add(Leave leave) {
        leave.setStatus(0);
        leave.setApplicant(SecurityHelper.getActiveUser().getCode());
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
    public String startProcess(Long id) {
        checkProcessStatus(id);
        Leave leave = new Leave();
        leave.setId(id);
        leave.setStatus(1);
        // 更新请假状态标志
        int row = leaveMapper.updateByPrimaryKeySelective(leave);
        if (row != 1) {
            return null;
        }
        String pdKey = ActivitiHelper.getDefaultProcessDefinitionKey(leave.getClass());
        String bizKey = Long.toString(id);
        // 业务关联流程
        Map<String, Object> variables = new HashMap<>();
        variables.put(ActivitiHelper.PROCESS_VARIABLE_NAME, pdKey + Constant.PERIOD_SEPARATOR + bizKey);
        // 启动流程实例
        return processService.startProcessInstanceByKey(pdKey, bizKey, variables).getProcessInstanceId();
    }

    @Override
    public void completeTask(String taskId, String comment) {
        String businessKey = processService.getBusinessKey(taskId);
        Leave leave = getById(Long.parseLong(businessKey));
        if (leave.getStatus() == 1) {
            leave.setStatus(2);
        }
        if (processService.completeTask(taskId, comment) == null) {
            leave.setStatus(3);
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
    public Page<Map> getPage(ModelMap modelMap, int offset, int length) {
        return leaveExtMapper.selectPage(PageRequest.newInstance(modelMap, offset, length));
    }

    private void checkProcessStatus(Long id) {
        Leave leave = leaveMapper.selectByPrimaryKey(id);
        if (leave == null) {
            throw new BusinessException("记录不存在！");
        }
        if (leave.getStatus() != 0) {
            throw new BusinessException("只有当请假状态为【初始录入】时方可执行修改、删除和申请请假操作！");
        }
    }

}
