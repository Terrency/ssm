package com.ssm.act.api.service;

import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Comment;
import org.activiti.engine.task.Task;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipInputStream;

public interface ProcessService {

    String BEAN_NAME = "processService";

    Deployment deploy(@NotNull ZipInputStream zipInputStream, @NotEmpty String name);

    void deleteDeployment(@NotEmpty String deploymentId);

    List<Deployment> getDeploymentList();

    List<ProcessDefinition> getProcessDefinitionList();

    InputStream getProcessDiagram(@NotEmpty String processDefinitionId);

    InputStream getProcessDiagram(@NotEmpty String processDefinitionKey, @NotEmpty String businessKey);

    ProcessInstance startProcessInstanceByKey(@NotEmpty String processDefinitionKey);

    ProcessInstance startProcessInstanceByKey(@NotEmpty String processDefinitionKey, @NotEmpty String businessKey);

    ProcessInstance startProcessInstanceByKey(@NotEmpty String processDefinitionKey, @NotNull Map<String, Object> variables);

    ProcessInstance startProcessInstanceByKey(@NotEmpty String processDefinitionKey, @NotEmpty String businessKey, @NotNull Map<String, Object> variables);

    ProcessInstance completeTask(@NotEmpty String taskId);

    ProcessInstance completeTask(@NotEmpty String taskId, @NotEmpty String comment);

    ProcessInstance completeTask(@NotEmpty String taskId, @NotNull Map<String, Object> variables);

    ProcessInstance completeTask(@NotEmpty String taskId, @NotEmpty String comment, @NotNull Map<String, Object> variables);

    List<Task> getTaskList(@NotEmpty String assignee);

    List<HistoricTaskInstance> getHistoryTaskList(@NotEmpty String assignee);

    String getBusinessKey(@NotEmpty String taskId);

    List<String> getOutgoingList(@NotEmpty String taskId);

    ActivityImpl getActivityImpl(@NotEmpty String processInstanceId);

    ActivityImpl getActivityImpl(@NotEmpty String processDefinitionKey, @NotEmpty String businessKey);

    List<Comment> getHistoryCommentList(@NotEmpty String processInstanceId);

    List<Comment> getHistoryCommentList(@NotEmpty String variableName, @NotNull Object variableValue);

}
