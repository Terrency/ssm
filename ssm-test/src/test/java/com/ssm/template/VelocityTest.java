package com.ssm.template;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.util.StringUtils;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class VelocityTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(VelocityTest.class);

    private VelocityEngine engine;

    @Before
    public void setUp() throws Exception {
        engine = new VelocityEngine();
        engine.init(PropertiesLoaderUtils.loadProperties("velocity.properties"));
    }

    @Test
    public void test1StringTemplate() throws Exception {

        String stringTemplate = "$id : $StringUtils.firstLetterCaps(${name})";

        Map<String, Object> context = new HashMap<>();
        context.put("id", 101);
        context.put("name", "king");
        context.put("StringUtils", new StringUtils());

        StringWriter result = new StringWriter();
        engine.evaluate(new VelocityContext(context), result, this.getClass().getName(), stringTemplate);
        // Velocity.evaluate(new VelocityContext(context), result, this.getClass().getName(), stringTemplate);

        LOGGER.info(result.toString());

    }

    @Test
    public void test2FileTemplate() throws Exception {

        Map<String, Object> context = new HashMap<>();
        context.put("name", "King");
        context.put("date", Calendar.getInstance().getTime());

        List<Object> temp = new ArrayList<>();
        temp.add("Hello");
        temp.add("Velocity");
        context.put("list", temp);

        StringWriter writer = new StringWriter();

        Template template = engine.getTemplate("velocity.vm");
        template.merge(new VelocityContext(context), writer);

        LOGGER.info(writer.toString());

    }

}
