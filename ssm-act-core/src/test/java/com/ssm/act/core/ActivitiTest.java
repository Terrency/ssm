package com.ssm.act.core;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.ProcessEngines;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.util.Assert;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.ZipInputStream;

/**
 * Activiti的表都以ACT_开头:
 * ACT_RE_*: RE表示repository, 这个前缀的表包含了流程定义和流程静态资源(图片,规则,等等);
 * ACT_RU_*: RU表示runtime, 这些运行时的表, 包含流程实例, 任务, 变量, 异步任务, 等运行中的数据. Activiti只在流程实例执行过程中保存这些数据, 在流程结束时就会删除这些记录, 这样运行时表可以一直很小, 速度很快;
 * ACT_ID_*: ID表示identity, 这些表包含身份信息, 比如用户, 组等等;
 * ACT_HI_*: HI表示history, 这些表包含历史数据, 比如历史流程实例, 变量, 任务等等;
 * ACT_GE_*: GE表示general, 通用数据, 用于不同场景下.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ActivitiTest {

    @Before
    public void setUp() throws Exception {
        ProcessEngineConfiguration processEngineConfiguration = ProcessEngineConfiguration.createProcessEngineConfigurationFromResource("activiti.cfg.xml");
        ProcessEngine processEngine = processEngineConfiguration.buildProcessEngine();
        Assert.notNull(processEngine);
    }

    /**
     * 流程部署
     * 涉及到的表:
     * (1)act_ge_bytearray
     * (2)act_re_deployment
     * (3)act_re_procdef
     */
    @Test
    public void test1Deploy() throws Exception {
        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("LeaveProcess.zip");
        ProcessEngines.getDefaultProcessEngine()
                .getRepositoryService()
                .createDeployment()
                .name("请假流程")
                .addZipInputStream(new ZipInputStream(inputStream))
                .deploy();
    }

    /**
     * 启动流程实例
     * 涉及到的表:
     * (1)act_hi_actinst
     * (2)act_hi_procinst
     * (3)act_hi_taskinst
     * (4)act_ru_execution: 存放当前正在执行的流程实例(临时表)
     * (5)act_ru_task: 存放当前正在执行的任务(临时表)
     */
    @Test
    public void test2StartProcessInstance() throws Exception {
        Map<String, Object> variables = new HashMap<>();
        variables.put("applicant", "Alan");
        ProcessEngines.getDefaultProcessEngine()
                .getRuntimeService()
                .startProcessInstanceById("LeaveProcess:1:4", "_businessKey", variables);
    }

    /**
     * 请假申请
     */
    @Test
    public void test3CompleteTask() throws Exception {
        Map<String, Object> variables = new HashMap<>();
        variables.put("approver", "Ann");
        ProcessEngines.getDefaultProcessEngine()
                .getTaskService()
                .complete("107", variables);
    }

    /**
     * 部门经理审批
     */
    @Test
    public void test4CompleteTask() throws Exception {
        Map<String, Object> variables = new HashMap<>();
        variables.put("approver", "Gavin");
        ProcessEngines.getDefaultProcessEngine()
                .getTaskService()
                .complete("202", variables);
    }

    /**
     * 总经理审批
     */
    @Test
    public void test5CompleteTask() throws Exception {
        ProcessEngines.getDefaultProcessEngine()
                .getTaskService()
                .complete("302");
    }

}
