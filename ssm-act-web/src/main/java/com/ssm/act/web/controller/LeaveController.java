package com.ssm.act.web.controller;

import com.ssm.act.api.model.Leave;
import com.ssm.act.api.service.LeaveService;
import com.ssm.act.api.service.ProcessService;
import com.ssm.common.base.util.ActivitiHelper;
import com.ssm.common.web.base.BaseController;
import com.ssm.common.web.base.ResponseData;
import com.ssm.common.web.util.SecurityHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/leave")
public class LeaveController extends BaseController {

    @Autowired
    private LeaveService leaveService;

    @Autowired
    private ProcessService processService;

    @RequestMapping("/list")
    public ModelAndView list() {
        Map<String, Object> model = new HashMap<>();
        model.put(ActivitiHelper.PROCESS_DEFINITION_KEY_NAME, ActivitiHelper.getDefaultProcessDefinitionKey(Leave.class));
        // 将数据填充到Request域并返回指定的逻辑视图
        return new ModelAndView("leave/list", model);
    }

    @ResponseBody
    @RequestMapping(value = "/getList", method = {RequestMethod.POST})
    public List<Map> getList() {
        return leaveService.getList(SecurityHelper.getActiveUser().getCode());
    }

    @ResponseBody
    @RequestMapping(value = "/addSubmit", method = RequestMethod.POST)
    public ResponseData addSubmit(Leave leave) {
        leave.setApplicant(SecurityHelper.getActiveUser().getCode());
        return setData(leaveService.add(leave));
    }

    @ResponseBody
    @RequestMapping(value = "/editSubmit", method = {RequestMethod.POST})
    public ResponseData editSubmit(Leave leave) {
        return setData(leaveService.update(leave));
    }

    @ResponseBody
    @RequestMapping(value = "/deleteSubmit", method = {RequestMethod.POST})
    public ResponseData deleteSubmit(@RequestParam Long id) {
        return setData(leaveService.delete(id));
    }

    @ResponseBody
    @RequestMapping("/startProcess")
    public ResponseData startProcess(@RequestParam Long id) {
        leaveService.startProcess(id);
        return ResponseData.newInstance();
    }

    @RequestMapping(value = "/task", method = {RequestMethod.GET})
    public ModelAndView task(@RequestParam String taskId, @RequestParam String processInstanceId) {
        String businessKey = processService.getBusinessKey(taskId);
        Leave leave = leaveService.getById(Long.parseLong(businessKey));
        List<String> outgoingList = processService.getOutgoingList(processInstanceId);
        Map<String, Object> model = new HashMap<>();
        model.put("taskId", taskId);
        model.put("processInstanceId", processInstanceId);
        model.put("leave", leave);
        model.put("outgoingList", outgoingList);
        return new ModelAndView("leave/task", model);
    }

    @ResponseBody
    @RequestMapping(value = "/taskSubmit", method = {RequestMethod.POST})
    public ResponseData taskSubmit(@RequestParam String taskId, @RequestParam String comment) {
        Map<String, Object> variables = new HashMap<>();
        variables.put(ActivitiHelper.APPLICANT_PLACEHOLDER_KEY, SecurityHelper.getActiveUser().getManager());
        leaveService.completeTask(SecurityHelper.getActiveUser().getCode(), taskId, comment, variables);
        return ResponseData.newInstance();
    }

}
