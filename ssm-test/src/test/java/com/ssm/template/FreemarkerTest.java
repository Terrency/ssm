package com.ssm.template;

import freemarker.cache.ClassTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.StringReader;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * @see org.apache.ibatis.scripting.xmltags.TrimSqlNode.FilteredDynamicContext#applyPrefix
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class FreemarkerTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(FreemarkerTest.class);

    private Configuration configuration;

    @Before
    public void setUp() throws Exception {
        configuration = new Configuration(Configuration.DEFAULT_INCOMPATIBLE_IMPROVEMENTS);
        configuration.setTemplateLoader(new ClassTemplateLoader(this.getClass().getClassLoader(), ""));
        configuration.setSharedVariable("where", new WhereDirective());
        configuration.setSettings(PropertiesLoaderUtils.loadProperties("freemarker.properties"));
        Assert.assertTrue("yyyy-MM-dd".equals(configuration.getDateFormat()));
    }

    @Test
    public void test1StringTemplate() throws Exception {
        String stringTemplate = "<#if id?? && name!!=''>${id}:${name}</#if>";
        Template template = new Template("default", new StringReader(stringTemplate), configuration);
        StringWriter result = new StringWriter();
        Map<String, Object> dataModel = new HashMap<>();
        dataModel.put("id", 101L);
        dataModel.put("name", "Freemarker");
        template.process(dataModel, result);
        LOGGER.info(result.toString());
    }

    @Test
    public void test2FileTemplate() throws Exception {
        Template template = configuration.getTemplate("freemarker.ftl");
        StringWriter result = new StringWriter();
        Map<String, Object> dataModel = new HashMap<>();
        dataModel.put("id", 101L);
        dataModel.put("name", "Freemarker");
        template.process(dataModel, result);
        LOGGER.info(result.toString());
    }

    @Test
    public void test3Directive() throws Exception {
        Map<String, Object> dataModel = new HashMap<>();
        dataModel.put("id", 101L);
        dataModel.put("name", "Freemarker");
        String stringTemplate = "SELECT * FROM SYS_USER <@where><#if id??>and id = :id</#if> <#if name!!=''>AND name = :name</#if></@where>";
        StringWriter out = new StringWriter();
        new Template("default", stringTemplate, configuration).process(dataModel, out);
        LOGGER.info(out.toString());
    }

}
