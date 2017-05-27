package com.ssm.act.api.service;

import com.ssm.act.api.model.Leave;
import com.ssm.common.model.ModelMap;
import com.ssm.common.page.Page;
import com.ssm.common.service.BaseService;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;

public interface LeaveService extends BaseService<Leave> {

    String BEAN_NAME = "leaveService";

    String startProcess(@NotNull Long id);

    void completeTask(@NotEmpty String taskId, @NotEmpty String comment);

    List<Map> getList(@NotEmpty String applicant);

    List<Map> getList(ModelMap modelMap);

    Page<Map> getPage(ModelMap modelMap, int offset, int length);

}
