package com.ssm.common.model;

import java.util.LinkedHashMap;
import java.util.Map;

public class ModelMap extends LinkedHashMap<String, Object> implements Model {

    private static final long serialVersionUID = 20160523L;

    public ModelMap() {
    }

    public ModelMap(Map<String, ?> map) {
        if (map != null) {
            super.putAll(map);
        }
    }

    public ModelMap(String key, Object value) {
        if (key == null) {
            throw new IllegalArgumentException("ParamDTO key name must not be null");
        }
        super.put(key, value);
    }

}
