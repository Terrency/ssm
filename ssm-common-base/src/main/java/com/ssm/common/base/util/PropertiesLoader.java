package com.ssm.common.base.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import java.io.IOException;
import java.util.Properties;
import java.util.Set;

/**
 * @see org.springframework.core.io.support.PropertiesLoaderSupport
 */
public abstract class PropertiesLoader {

    private static final Logger LOGGER = LoggerFactory.getLogger(PropertiesLoader.class);

    private static final Properties properties = new Properties();

    public static void init(String... resources) {
        loadProperties(resources);
    }

    private static void loadProperties(String... resources) {
        ResourceLoader resourceLoader = new DefaultResourceLoader();
        for (String location : resources) {
            try {
                LOGGER.debug("Loading properties file from {}", location);
                PropertiesLoaderUtils.fillProperties(properties, resourceLoader.getResource(location));
            } catch (IOException e) {
                LOGGER.warn("Could not load properties from path {}", location, e);
            }
        }
    }

    public static String getValue(String key) {
        // String value = System.getProperty(key);
        // if (value != null) {
        //     return value;
        // }
        return properties.getProperty(key);
    }

    public static String getValue(String key, String defaultValue) {
        String value = getValue(key);
        return value != null ? value : defaultValue;
    }

    public static Integer getInteger(String key) {
        return Integer.valueOf(getValue(key));
    }

    public static Integer getInteger(String key, Integer defaultValue) {
        Integer value = getInteger(key);
        return value != null ? value : defaultValue;
    }

    public static Boolean getBoolean(String key) {
        return Boolean.valueOf(getValue(key));
    }

    public static Boolean getBoolean(String key, Boolean defaultValue) {
        Boolean value = getBoolean(key);
        return value != null ? value : defaultValue;
    }

    public static Set<String> getKeys() {
        return properties.stringPropertyNames();
    }

    public static Properties getProperties() {
        return properties;
    }

}
