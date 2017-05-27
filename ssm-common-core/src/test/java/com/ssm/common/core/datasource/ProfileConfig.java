package com.ssm.common.core.datasource;

import com.ssm.common.util.Constant;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
public class ProfileConfig {

    @Bean
    @Profile(Constant.ENV_DEV)
    public DemoBean devDemo() {
        return new DemoBean("Active development profile");
    }

    @Bean
    @Profile(Constant.ENV_PROD)
    public DemoBean prodDemo() {
        return new DemoBean("Active production profile");
    }

    static class DemoBean {

        private final String value;

        public DemoBean(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

}
