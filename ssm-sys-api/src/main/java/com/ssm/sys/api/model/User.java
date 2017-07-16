package com.ssm.sys.api.model;

import com.ssm.common.base.model.AbstractModel;

import java.io.Serializable;

public class User extends AbstractModel implements Serializable {
    private Long id;

    private String code;

    private String name;

    private String pass;

    private String salt;

    private String status;

    private Long manager;

    private static final long serialVersionUID = 1L;

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

    public Long getManager() {
        return manager;
    }

    public void setManager(Long manager) {
        this.manager = manager;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", code=").append(code);
        sb.append(", name=").append(name);
        sb.append(", pass=").append(pass);
        sb.append(", salt=").append(salt);
        sb.append(", status=").append(status);
        sb.append(", manager=").append(manager);
        sb.append("]");
        return sb.toString();
    }
}
