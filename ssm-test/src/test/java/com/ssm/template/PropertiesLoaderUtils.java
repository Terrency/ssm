package com.ssm.template;

import java.io.IOException;
import java.util.Properties;

public abstract class PropertiesLoaderUtils {

    public static Properties loadProperties(String resource) throws IOException {
        Properties props = new Properties();
        fillProperties(props, resource);
        return props;
    }

    public static void fillProperties(Properties props, String resource) throws IOException {
        props.load(Thread.currentThread().getContextClassLoader().getResourceAsStream(resource));
    }

}
