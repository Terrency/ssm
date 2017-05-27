package com.ssm.core.model;

import java.io.Serializable;

public class User implements Serializable {

    private static final long serialVersionUID = 20161229L;

    private Long id;
    private String code;
    private String name;
    private String pass;
    private String salt;
    private String status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "User{"
                + "id=" + id
                + ", code='" + code
                + ", name='" + name
                + ", pass='" + pass
                + ", salt='" + salt
                + ", status='" + status
                + '}';
    }
}
