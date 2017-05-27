package com.ssm.common.web.datatable;

import java.io.Serializable;

public class DataTableSearch implements Serializable {

    private static final long serialVersionUID = 20161202L;

    private String value;
    private Boolean regex;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Boolean getRegex() {
        return regex;
    }

    public void setRegex(Boolean regex) {
        this.regex = regex;
    }
}
