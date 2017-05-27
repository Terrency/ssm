package com.ssm.common.subject;

import java.io.Serializable;
import java.util.List;

public class ActiveUser implements Serializable {

    private Long id;
    private String code;
    private String name;
    private String pass;
    private String salt;
    private String status;
    private String manager;
    private List<Permission> menus;
    private List<Permission> functions;

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

    public String getManager() {
        return manager;
    }

    public void setManager(String manager) {
        this.manager = manager;
    }

    public List<Permission> getMenus() {
        return menus;
    }

    public void setMenus(List<Permission> menus) {
        this.menus = menus;
    }

    public List<Permission> getFunctions() {
        return functions;
    }

    public void setFunctions(List<Permission> functions) {
        this.functions = functions;
    }

    @Override
    public String toString() {
        return "ActiveUser [id=" + id + ", code=" + code
                + ", name=" + name + "]";
    }

}
