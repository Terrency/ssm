package com.ssm.common.core.datasource;

import com.ssm.common.util.Constant;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static com.ssm.common.core.datasource.ProfileConfig.DemoBean;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ProfilesTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProfilesTest.class);

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void testProfile() throws Exception {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.getEnvironment().setActiveProfiles(Constant.ENV_PROD);
        context.register(ProfileConfig.class);
        context.refresh();
        DemoBean demoBean = context.getBean(DemoBean.class);
        LOGGER.info("===== {} =====", demoBean.getValue());
        context.close();
    }

}
