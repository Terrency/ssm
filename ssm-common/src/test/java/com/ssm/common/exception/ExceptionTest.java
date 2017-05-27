package com.ssm.common.exception;

import com.ssm.common.util.MessageHelper;
import org.junit.Before;
import org.junit.Test;
import org.springframework.util.Assert;

import java.util.Locale;
import java.util.ResourceBundle;

public class ExceptionTest {

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void testGetMessage() throws Exception {
        Assert.hasText(MessageHelper.getMessage("F-9999"));
    }

}
