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

    /**
     * 流程部署
     *
     * @param deployName     部署名称
     * @param zipInputStream Zip文件输入流
     * @return Deployment
     * @see <a href="http://hessian.caucho.com/doc/hessian-overview.xtp#Hessian">Hessian with large binary data</a>
     */
    Deployment deploy(@NotEmpty String deployName, @NotNull ZipInputStream zipInputStream);

    /**
     * 删除部署
     *
     * @param deploymentId 部署ID
     */
    void deleteDeployment(@NotEmpty String deploymentId);

    /**
     * 获取流程部署列表
     *
     * @return 流程部署列表
     */
    List<Deployment> getDeploymentList();

    /**
     * 获取流程定义列表
     *
     * @return 流程定义列表
     */
    List<ProcessDefinition> getProcessDefinitionList();

    /**
     * 获取流程定义图片
     *
     * @param processDefinitionId 流程定义ID
     * @return 流程定义图片
     */
    InputStream getProcessDiagram(@NotEmpty String processDefinitionId);

    /**
     * 获取流程定义图片
     *
     * @param processDefinitionKey 流程定义ID
     * @param businessKey          业务表主键值
     * @return 流程定义图片
     */
    InputStream getProcessDiagram(@NotEmpty String processDefinitionKey, @NotEmpty String businessKey);

    /**
     * 启动流程实例
     *
     * @param processDefinitionKey 流程定义KEY
     * @return 流程实例
     */
    ProcessInstance startProcessInstanceByKey(@NotEmpty String processDefinitionKey);

    /**
     * 启动流程实例
     *
     * @param processDefinitionKey 流程定义KEY
     * @param businessKey          业务表主键值
     * @return 流程实例
     */
    ProcessInstance startProcessInstanceByKey(@NotEmpty String processDefinitionKey, @NotEmpty String businessKey);

    /**
     * 启动流程实例
     *
     * @param processDefinitionKey 流程定义KEY
     * @param variables            流程变量(用于在运行时动态设置申请人或审批人)
     * @return 流程实例
     */
    ProcessInstance startProcessInstanceByKey(@NotEmpty String processDefinitionKey, @NotNull Map<String, Object> variables);

    /**
     * 启动流程实例
     *
     * @param processDefinitionKey 流程定义KEY
     * @param businessKey          业务表主键值
     * @param variables            流程变量(用于在运行时动态设置申请人或审批人)
     * @return 流程实例
     */
    ProcessInstance startProcessInstanceByKey(@NotEmpty String processDefinitionKey, @NotEmpty String businessKey, @NotNull Map<String, Object> variables);

    /**
     * 完成任务
     *
     * @param userId 任务执行者
     * @param taskId 任务ID
     * @return 流程实例
     */
    ProcessInstance completeTask(@NotEmpty String userId, @NotEmpty String taskId);

    /**
     * 完成任务
     *
     * @param userId  任务执行者
     * @param taskId  任务ID
     * @param comment 批注
     * @return 流程实例
     */
    ProcessInstance completeTask(@NotEmpty String userId, @NotEmpty String taskId, @NotEmpty String comment);

    /**
     * 完成任务
     *
     * @param userId    任务执行者
     * @param taskId    任务ID
     * @param variables 流程变量
     * @return 流程实例
     */
    ProcessInstance completeTask(@NotEmpty String userId, @NotEmpty String taskId, @NotNull Map<String, Object> variables);

    /**
     * 完成任务
     *
     * @param userId    任务执行者
     * @param taskId    任务ID
     * @param comment   批注
     * @param variables 流程变量
     * @return 流程实例
     */
    ProcessInstance completeTask(@NotEmpty String userId, @NotEmpty String taskId, @NotEmpty String comment, @NotNull Map<String, Object> variables);

    /**
     * 获取任务列表
     *
     * @param assignee 任务执行者
     * @return 任务列表
     */
    List<Task> getTaskList(@NotEmpty String assignee);

    /**
     * 获取历史任务列表
     *
     * @param assignee 任务执行者
     * @return 历史任务列表
     */
    List<HistoricTaskInstance> getHistoryTaskList(@NotEmpty String assignee);

    /**
     * 获取业务表主键值
     *
     * @param taskId 任务ID
     * @return 业务表主键值
     */
    String getBusinessKey(@NotEmpty String taskId);

    List<String> getOutgoingList(@NotEmpty String taskId);

    ActivityImpl getActivityImpl(@NotEmpty String processInstanceId);

    ActivityImpl getActivityImpl(@NotEmpty String processDefinitionKey, @NotEmpty String businessKey);

    List<Comment> getHistoryCommentList(@NotEmpty String processInstanceId);

    List<Comment> getHistoryCommentList(@NotEmpty String variableName, @NotNull Object variableValue);

    ProcessInstance getProcessInstance(@NotEmpty String processInstanceId);

    ProcessInstance getProcessInstance(@NotEmpty String processDefinitionKey, @NotEmpty String businessKey);

}
