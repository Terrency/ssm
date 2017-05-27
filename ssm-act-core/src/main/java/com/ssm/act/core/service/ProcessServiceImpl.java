package com.ssm.act.core.service;

import com.ssm.act.api.service.ProcessService;
import com.ssm.common.util.SecurityHelper;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.history.HistoricVariableInstance;
import org.activiti.engine.impl.identity.Authentication;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.pvm.PvmTransition;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Comment;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipInputStream;

@Service(ProcessService.BEAN_NAME)
public class ProcessServiceImpl implements ProcessService {

    @Autowired
    private ProcessEngine processEngine;

    /**
     * 流程部署
     *
     * @param zipInputStream zip文件输入流
     * @param name           流程名称
     */
    @Override
    public Deployment deploy(ZipInputStream zipInputStream, String name) {
        return processEngine.getRepositoryService()
                .createDeployment()
                .name(name)
                .addZipInputStream(zipInputStream)
                .deploy();
    }

    /**
     * 根据deploymentId删除一个部署
     *
     * @param deploymentId 部署ID
     */
    @Override
    public void deleteDeployment(String deploymentId) {
        processEngine.getRepositoryService().deleteDeployment(deploymentId, Boolean.TRUE);
    }

    /**
     * 查询所有的流程部署列表
     */
    @Override
    public List<Deployment> getDeploymentList() {
        return processEngine.getRepositoryService()
                .createDeploymentQuery()
                .orderByDeploymenTime()
                .desc()
                .list();
    }

    /**
     * 查询所有的流程定义列表
     */
    @Override
    public List<ProcessDefinition> getProcessDefinitionList() {
        return processEngine.getRepositoryService()
                .createProcessDefinitionQuery()
                .orderByProcessDefinitionVersion()
                .desc()
                .list();
    }

    /**
     * 查看流程图
     */
    @Override
    public InputStream getProcessDiagram(String processDefinitionId) {
        return processEngine.getRepositoryService().getProcessDiagram(processDefinitionId);
    }

    /**
     * 查看流程图
     */
    @Override
    public InputStream getProcessDiagram(String processDefinitionKey, String businessKey) {
        ProcessInstance processInstance = getProcessInstance(processDefinitionKey, businessKey);
        if (processInstance != null) {
            return getProcessDiagram(processInstance.getProcessDefinitionId());
        }
        return null;
    }

    /**
     * 启动流程实例
     *
     * @param processDefinitionKey 流程定义Key
     */
    @Override
    public ProcessInstance startProcessInstanceByKey(String processDefinitionKey) {
        return processEngine.getRuntimeService().startProcessInstanceByKey(processDefinitionKey);
    }

    /**
     * 启动流程实例
     *
     * @param processDefinitionKey 流程定义Key
     * @param businessKey          业务表主键值
     */
    @Override
    public ProcessInstance startProcessInstanceByKey(String processDefinitionKey, String businessKey) {
        return processEngine.getRuntimeService().startProcessInstanceByKey(processDefinitionKey, businessKey);
    }

    /**
     * 启动流程实例
     *
     * @param processDefinitionKey 流程定义Key
     * @param variables            流程变量
     */
    @Override
    public ProcessInstance startProcessInstanceByKey(String processDefinitionKey, Map<String, Object> variables) {
        return processEngine.getRuntimeService().startProcessInstanceByKey(processDefinitionKey, variables);
    }

    /**
     * 启动流程实例
     *
     * @param processDefinitionKey 流程定义Key
     * @param businessKey          业务表主键值
     * @param variables            流程变量
     */
    @Override
    public ProcessInstance startProcessInstanceByKey(String processDefinitionKey, String businessKey, Map<String, Object> variables) {
        return processEngine.getRuntimeService().startProcessInstanceByKey(processDefinitionKey, businessKey, variables);
    }

    /**
     * 完成任务
     *
     * @param taskId 任务ID
     */
    @Override
    public ProcessInstance completeTask(String taskId) {
        return completeTask(taskId, null, null);
    }

    /**
     * 完成任务
     *
     * @param taskId  任务ID
     * @param comment 批注信息
     */
    @Override
    public ProcessInstance completeTask(String taskId, String comment) {
        return completeTask(taskId, comment, null);
    }

    /**
     * 完成任务
     *
     * @param taskId    任务ID
     * @param variables 流程变量
     */
    @Override
    public ProcessInstance completeTask(String taskId, Map<String, Object> variables) {
        return completeTask(taskId, null, variables);
    }

    /**
     * 完成任务
     *
     * @param taskId    任务ID
     * @param comment   批注信息
     * @param variables 流程变量
     * @return ProcessInstance(若返回值为空则流程已结束)
     */
    @Override
    public ProcessInstance completeTask(String taskId, String comment, Map<String, Object> variables) {
        Task task = getTask(taskId);
        TaskService taskService = processEngine.getTaskService();
        if (comment != null) {
            Authentication.setAuthenticatedUserId(SecurityHelper.getActiveUser().getCode());
            taskService.addComment(taskId, task.getProcessInstanceId(), comment);
        }
        if (variables == null || variables.isEmpty()) {
            taskService.complete(taskId);
        } else {
            taskService.complete(taskId, variables);
        }
        return getProcessInstance(task.getProcessInstanceId());
    }

    /**
     * 查询指定用户的任务列表
     *
     * @param assignee 任务执行者
     */
    @Override
    public List<Task> getTaskList(String assignee) {
        return processEngine.getTaskService()
                .createTaskQuery()
                .taskAssignee(assignee)
                .orderByTaskCreateTime()
                .desc()
                .list();
    }

    /**
     * 查询指定用户的历史任务列表
     *
     * @param assignee 任务执行者
     */
    @Override
    public List<HistoricTaskInstance> getHistoryTaskList(String assignee) {
        return processEngine.getHistoryService()
                .createHistoricTaskInstanceQuery()
                .taskAssignee(assignee)
                .finished()
                .orderByHistoricTaskInstanceStartTime()
                .desc()
                .list();
    }

    /**
     * 根据taskId获取businessKey
     *
     * @param taskId 任务ID
     * @return businessKey
     */
    @Override
    public String getBusinessKey(String taskId) {
        return getProcessInstance(getTask(taskId).getProcessInstanceId()).getBusinessKey();
    }

    /**
     * 根据taskId获取当前流程实例正在执行节点的SequenceFlow列表
     *
     * @param taskId 任务ID
     */
    public List<String> getOutgoingList(String taskId) {
        List<String> list = new ArrayList<>();
        List<PvmTransition> pvmTransitionList = getActivityImpl(taskId).getOutgoingTransitions();
        if (!(pvmTransitionList == null || pvmTransitionList.isEmpty())) {
            for (PvmTransition pvmTransition : pvmTransitionList) {
                list.add((String) pvmTransition.getProperty("name"));
            }
        }
        return list;
    }

    /**
     * 根据processInstanceId获取当前流程实例正在执行的节点ActivityImpl
     *
     * @param processInstanceId 流程实例ID
     * @return ActivityImpl
     */
    @Override
    public ActivityImpl getActivityImpl(String processInstanceId) {
        ProcessInstance processInstance = getProcessInstance(processInstanceId);
        if (processInstance != null) {
            return getProcessDefinitionEntity(processInstance.getProcessDefinitionId()).findActivity(processInstance.getActivityId());
        }
        return null;
    }

    /**
     * 根据processDefinitionKey和businessKey获取当前流程实例正在执行的节点ActivityImpl
     *
     * @param processDefinitionKey processDefinitionKey
     * @param businessKey          processInstanceBusinessKey
     * @return ActivityImpl
     */
    @Override
    public ActivityImpl getActivityImpl(String processDefinitionKey, String businessKey) {
        ProcessInstance processInstance = getProcessInstance(processDefinitionKey, businessKey);
        if (processInstance != null) {
            return getActivityImpl(processInstance.getProcessInstanceId());
        }
        return null;
    }

    /**
     * 查询历史批注信息
     *
     * @param processInstanceId 流程实例ID
     */
    public List<Comment> getHistoryCommentList(String processInstanceId) {
        List<Comment> list = new ArrayList<>();
        List<HistoricActivityInstance> haiList = processEngine.getHistoryService()
                .createHistoricActivityInstanceQuery()
                .processInstanceId(processInstanceId)
                .activityType("userTask")
                .list();
        if (haiList != null && !haiList.isEmpty()) {
            TaskService taskService = processEngine.getTaskService();
            for (HistoricActivityInstance hai : haiList) {
                List<Comment> comments = taskService.getTaskComments(hai.getTaskId());
                if (comments != null && !comments.isEmpty()) {
                    list.addAll(comments);
                }
            }
        }
        return list;
    }

    /**
     * 查询历史批注信息
     *
     * @param variableName  流程实例变量的key
     * @param variableValue 流程实例变量的value
     */
    public List<Comment> getHistoryCommentList(String variableName, Object variableValue) {
        return getHistoryCommentList(getHistoricVariableInstance(variableName, variableValue).getProcessInstanceId());
    }

    private ProcessDefinition getProcessDefinition(String processDefinitionId) {
        return processEngine.getRepositoryService().getProcessDefinition(processDefinitionId);
    }

    private ProcessDefinitionEntity getProcessDefinitionEntity(String processDefinitionId) {
        return (ProcessDefinitionEntity) getProcessDefinition(processDefinitionId);
    }

    /**
     * 返回null时代表流程实例已结束
     */
    private ProcessInstance getProcessInstance(String processInstanceId) {
        return processEngine.getRuntimeService()
                .createProcessInstanceQuery()
                .processInstanceId(processInstanceId)
                .singleResult();
    }

    /**
     * 返回null时代表流程实例已结束
     */
    private ProcessInstance getProcessInstance(String processDefinitionKey, String businessKey) {
        return processEngine.getRuntimeService()
                .createProcessInstanceQuery()
                .processInstanceBusinessKey(businessKey, processDefinitionKey)
                .singleResult();
    }

    private Task getTask(String taskId) {
        return processEngine.getTaskService()
                .createTaskQuery()
                .taskId(taskId)
                .singleResult();
    }

    private HistoricVariableInstance getHistoricVariableInstance(String variableName, Object variableValue) {
        return processEngine.getHistoryService()
                .createHistoricVariableInstanceQuery()
                .variableValueEquals(variableName, variableValue)
                .singleResult();
    }

}
