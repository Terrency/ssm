package com.ssm.common.base.exception;

import com.ssm.common.base.util.MessageHelper;
import org.junit.Before;
import org.junit.Test;
import org.springframework.util.Assert;

public class ExceptionTest {

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void testGetMessage() throws Exception {
        Assert.hasText(MessageHelper.getMessage("F-9999"));
    }

}
