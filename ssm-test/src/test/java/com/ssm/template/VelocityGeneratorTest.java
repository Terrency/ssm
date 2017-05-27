package com.ssm.template;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.PrintWriter;
import java.util.Properties;

public class VelocityGeneratorTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(VelocityGeneratorTest.class);

    private VelocityEngine engine;

    @Before
    public void setUp() throws Exception {
        engine = new VelocityEngine();
        engine.init(PropertiesLoaderUtils.loadProperties("velocity.properties"));
    }

    @Test
    public void testVelocityGenerator() throws Exception {

        Properties props = PropertiesLoaderUtils.loadProperties("velocity-generator.properties");

        String modulePrefix = props.getProperty("modulePrefix");
        String modelPrefix = props.getProperty("modelPrefix");

        String rootPath = new File("").getAbsolutePath() + "/src/test/java/";

        String xml = rootPath + String.format("com/ssm/%s/core/extension/%sExtMapper.xml", modulePrefix, modelPrefix);
        String mapper = rootPath + String.format("com/ssm/%s/core/extension/%sExtMapper.java", modulePrefix, modelPrefix);
        String service = rootPath + String.format("com/ssm/%s/api/service/%sService.java", modulePrefix, modelPrefix);
        String serviceImpl = rootPath + String.format("com/ssm/%s/core/service/%sServiceImpl.java", modulePrefix, modelPrefix);
        String controller = rootPath + String.format("com/ssm/%s/web/controller/%sController.java", modulePrefix, modelPrefix);

        VelocityContext context = new VelocityContext(props);

        merge(engine.getTemplate("Xml.vm"), context, xml);
        merge(engine.getTemplate("Mapper.vm"), context, mapper);
        merge(engine.getTemplate("Service.vm"), context, service);
        merge(engine.getTemplate("ServiceImpl.vm"), context, serviceImpl);
        merge(engine.getTemplate("Controller.vm"), context, controller);

    }

    private static void merge(Template template, VelocityContext context, String path) throws Exception {

        File file = new File(path);
        if (!file.getParentFile().exists()) {
            boolean flag = file.getParentFile().mkdirs();
            LOGGER.info("Create Directory {} => {}", file.getParentFile().getPath(), flag);
        }

        PrintWriter writer = null;
        try {
            writer = new PrintWriter(path);
            template.merge(context, writer);
            writer.flush();
        } finally {
            if (writer != null) {
                writer.close();
            }
        }

    }

}
