package com.ssm.act.web.controller;

import com.ssm.act.api.service.ProcessService;
import com.ssm.common.base.util.ActivitiHelper;
import com.ssm.common.base.util.Constant;
import com.ssm.common.web.base.ResponseData;
import com.ssm.common.web.util.SecurityHelper;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.task.Comment;
import org.activiti.engine.task.Task;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipInputStream;

@Controller
@RequestMapping("/process")
public class ProcessController {

    @Autowired
    private ProcessService processService;

    @RequestMapping("/deploy")
    public String deploy() {
        return "process/deploy";
    }

    @RequestMapping("/task")
    public String task() {
        return "process/task";
    }

    @RequestMapping("/historyTask")
    public String historyTask() {
        return "process/historyTask";
    }

    @ResponseBody
    @RequestMapping("/deploySubmit")
    public ResponseData deploySubmit(@RequestParam Part file, @RequestParam String name) throws Exception {
        ZipInputStream zipInputStream = null;
        try {
            zipInputStream = new ZipInputStream(file.getInputStream());
            processService.deploy(zipInputStream, name);
        } finally {
            IOUtils.closeQuietly(zipInputStream);
        }
        return ResponseData.newInstance();
    }

    @ResponseBody
    @RequestMapping("/deleteDeployment")
    public ResponseData deleteDeployment(@RequestParam String deployId) {
        processService.deleteDeployment(deployId);
        return ResponseData.newInstance();
    }

    @ResponseBody
    @RequestMapping("/getDeploymentList")
    public List<Map<String, Object>> getDeploymentList() {
        List<Map<String, Object>> result = new ArrayList<>();
        List<Deployment> deploymentList = processService.getDeploymentList();
        for (Deployment deployment : deploymentList) {
            Map<String, Object> model = new HashMap<>();
            model.put("id", deployment.getId());
            model.put("name", deployment.getName());
            model.put("deploymentTime", deployment.getDeploymentTime());
            result.add(model);
        }
        return result;
    }

    @ResponseBody
    @RequestMapping("/getProcessDefinitionList")
    public List<Map<String, Object>> getProcessDefinitionList() {
        List<Map<String, Object>> result = new ArrayList<>();
        List<ProcessDefinition> processDefinitionList = processService.getProcessDefinitionList();
        for (ProcessDefinition processDefinition : processDefinitionList) {
            Map<String, Object> model = new HashMap<>();
            model.put("id", processDefinition.getId());
            model.put("name", processDefinition.getName());
            model.put("key", processDefinition.getKey());
            model.put("version", processDefinition.getVersion());
            model.put("resourceName", processDefinition.getResourceName());
            model.put("diagramResourceName", processDefinition.getDiagramResourceName());
            model.put("deploymentId", processDefinition.getDeploymentId());
            result.add(model);
        }
        return result;
    }

    @RequestMapping("/getDiagram")
    public void getDiagram(@RequestParam String pdKey, @RequestParam String bizKey, HttpServletResponse response) throws Exception {
        writeDiagramToOutputStream(response, processService.getProcessDiagram(pdKey, bizKey));
    }

    @RequestMapping("/getProcessDiagram")
    public void getProcessDiagram(@RequestParam String pdId, HttpServletResponse response) throws Exception {
        writeDiagramToOutputStream(response, processService.getProcessDiagram(pdId));
    }

    @ResponseBody
    @RequestMapping("/startProcessInstance")
    public ResponseData startProcessInstance(@RequestParam String pdKey, @RequestParam String bizKey) {
        // 业务关联流程
        Map<String, Object> variables = new HashMap<>();
        variables.put(ActivitiHelper.PROCESS_VARIABLE_NAME, pdKey + Constant.PERIOD_SEPARATOR + bizKey);
        // 启动流程实例
        processService.startProcessInstanceByKey(pdKey, bizKey, variables);
        return ResponseData.newInstance();
    }

    @ResponseBody
    @RequestMapping("/getActivity")
    public Map<String, Object> getActivity(@RequestParam String processInstanceId) {
        return getActivity(processService.getActivityImpl(processInstanceId));
    }

    @ResponseBody
    @RequestMapping("/ActivityImpl")
    public Map<String, Object> getActivity(@RequestParam String pdKey, @RequestParam String bizKey) {
        return getActivity(processService.getActivityImpl(pdKey, bizKey));
    }

    @ResponseBody
    @RequestMapping("/getTaskList")
    public List<Map<String, Object>> getTaskList() {
        List<Task> taskList = processService.getTaskList(SecurityHelper.getActiveUser().getCode());
        List<Map<String, Object>> result = new ArrayList<>();
        for (Task task : taskList) {
            Map<String, Object> model = new HashMap<>();
            model.put("id", task.getId());
            model.put("name", task.getName());
            model.put("assignee", task.getAssignee());
            model.put("createTime", task.getCreateTime());
            model.put("processDefinitionId", task.getProcessDefinitionId());
            model.put("processInstanceId", task.getProcessInstanceId());
            result.add(model);
        }
        return result;
    }

    @ResponseBody
    @RequestMapping("/getHistoryTaskList")
    public List<Map<String, Object>> getHistoryTaskList() {
        List<Map<String, Object>> result = new ArrayList<>();
        List<HistoricTaskInstance> historicTaskInstanceList = processService.getHistoryTaskList(SecurityHelper.getActiveUser().getCode());
        for (HistoricTaskInstance historicTaskInstance : historicTaskInstanceList) {
            Map<String, Object> model = new HashMap<>();
            model.put("id", historicTaskInstance.getId());
            model.put("name", historicTaskInstance.getName());
            model.put("assignee", historicTaskInstance.getAssignee());
            model.put("startTime", historicTaskInstance.getStartTime());
            model.put("endTime", historicTaskInstance.getEndTime());
            model.put("deleteReason", historicTaskInstance.getDeleteReason());
            model.put("processDefinitionId", historicTaskInstance.getProcessDefinitionId());
            model.put("processInstanceId", historicTaskInstance.getProcessInstanceId());
            result.add(model);
        }
        return result;
    }

    @ResponseBody
    @RequestMapping("/getHistoryComments")
    public List<Map<String, Object>> getHistoryCommentList(@RequestParam String processInstanceId) {
        List<Comment> historyCommentList = processService.getHistoryCommentList(processInstanceId);
        return getHistoryCommentList(historyCommentList);
    }

    @ResponseBody
    @RequestMapping("/getHistoryCommentList")
    public List<Map<String, Object>> getHistoryCommentList(@RequestParam String pdKey, @RequestParam String bizKey) {
        List<Comment> historyCommentList = processService.getHistoryCommentList(ActivitiHelper.PROCESS_VARIABLE_NAME, pdKey + Constant.PERIOD_SEPARATOR + bizKey);
        return getHistoryCommentList(historyCommentList);
    }

    private void writeDiagramToOutputStream(HttpServletResponse response, InputStream in) throws Exception {
        if (in != null) {
            OutputStream out = null;
            try {
                out = response.getOutputStream();
                int b = 0;
                while ((b = in.read()) != -1) {
                    out.write(b);
                }
            } finally {
                IOUtils.closeQuietly(out, in);
            }
        }
    }

    private List<Map<String, Object>> getHistoryCommentList(List<Comment> commentList) {
        List<Map<String, Object>> result = new ArrayList<>();
        if (commentList != null && !commentList.isEmpty()) {
            for (Comment comment : commentList) {
                Map<String, Object> model = new HashMap<>();
                model.put("userId", comment.getUserId());
                model.put("time", comment.getTime());
                model.put("fullMessage", comment.getFullMessage());
                result.add(model);
            }
        }
        return result;
    }

    private Map<String, Object> getActivity(ActivityImpl activityImpl) {
        Map<String, Object> model = new HashMap<>();
        if (activityImpl != null) {
            model.put("x", activityImpl.getX());
            model.put("y", activityImpl.getY());
            model.put("width", activityImpl.getWidth());
            model.put("height", activityImpl.getHeight());
        }
        return model;
    }

}
