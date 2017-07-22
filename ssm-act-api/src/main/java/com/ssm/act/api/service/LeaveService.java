package com.ssm.act.api.service;

import com.ssm.act.api.model.Leave;
import com.ssm.common.base.model.ModelMap;
import com.ssm.common.base.page.Page;
import com.ssm.common.base.service.BaseService;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;

public interface LeaveService extends BaseService<Leave> {

    String BEAN_NAME = "leaveService";

    /**
     * 请假申请
     *
     * @param id        请假单ID
     * @param applicant 请假人
     * @return 流程实例ID
     */
    String startProcess(@NotNull Long id, @NotEmpty String applicant);

    /**
     * 完成请假申请任务
     *
     * @param userId   任务执行者
     * @param taskId   任务ID
     * @param comment  批注
     * @param approver 任务审批人
     */
    void completeTask(@NotEmpty String userId, @NotEmpty String taskId, @NotEmpty String comment, @NotEmpty String approver);

    /**
     * 获取请假单列表
     *
     * @param applicant 申请人
     * @return 请假单列表
     */
    List<Map> getList(@NotEmpty String applicant);

    /**
     * 获取请假单列表
     */
    List<Map> getList(ModelMap modelMap);

    /**
     * 获取请假单列表
     */
    Page<Map> getPage(ModelMap modelMap, int offset, int length);

}
