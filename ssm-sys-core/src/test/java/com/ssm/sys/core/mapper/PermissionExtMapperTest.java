package com.ssm.sys.core.mapper;

import com.ssm.common.model.ModelMap;
import com.ssm.common.util.Constant;
import com.ssm.sys.core.mapper.extension.PermissionExtMapper;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Map;

@ActiveProfiles(Constant.ENV_DEV)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
        "classpath:spring-core-context.xml",
        "classpath:sys-core-context.xml"
})
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class PermissionExtMapperTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(PermissionExtMapperTest.class);

    @Autowired
    private PermissionExtMapper permissionExtMapper;

    @Before
    public void setUp() throws Exception {
        Assert.notNull(permissionExtMapper);
    }

    @Test
    public void test1Select() throws Exception {
        List<Map> list = permissionExtMapper.selectSelective(new ModelMap());
        LOGGER.info("{}", list);
    }

}
