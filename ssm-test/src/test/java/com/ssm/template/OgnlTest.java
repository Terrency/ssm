package com.ssm.template;

import ognl.Ognl;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OgnlTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(OgnlTest.class);

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void testOgnl() throws Exception {
        User user = new User(101L, "admin");
        Object name = Ognl.getValue("name", user);
        LOGGER.info("{}", name);
    }

    public class User {

        private Long id;
        private String name;

        public User() {
        }

        public User(Long id, String name) {
            this.id = id;
            this.name = name;
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

    }

}
