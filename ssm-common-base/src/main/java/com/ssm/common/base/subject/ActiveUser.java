package com.ssm.common.base.subject;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;
import java.util.*;

public class ActiveUser implements Serializable {

    private final Long id;
    private final String code;
    private final String name;
    private final String pass;
    private final String salt;
    private final String status;
    private final String manager;

    private final List<Permission> menus = new ArrayList<>();
    private final List<Permission> functions = new ArrayList<>();
    private final Map<String, Permission> permissions = new HashMap<>();

    public ActiveUser(Long id, String code, String name, String pass, String salt, String status, String manager) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.pass = pass;
        this.salt = salt;
        this.status = status;
        this.manager = manager;
    }

    public Long getId() {
        return id;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public String getPass() {
        return pass;
    }

    public String getSalt() {
        return salt;
    }

    public String getStatus() {
        return status;
    }

    public String getManager() {
        return manager;
    }

    public List<Permission> getMenus() {
        return menus;
    }

    public List<Permission> getFunctions() {
        return functions;
    }

    public Collection<Permission> getPermissions() {
        return permissions.values();
    }

    public boolean hasPermission(String code) {
        return permissions.containsKey(code);
    }

    public void addMenus(Collection<Permission> menus) {
        this.menus.addAll(menus);
        this.addPermissions(menus);
    }

    public void addFunctions(Collection<Permission> functions) {
        this.functions.addAll(functions);
        this.addPermissions(menus);
    }

    private void addPermissions(Collection<Permission> permissions) {
        for (Permission permission : permissions) {
            this.permissions.put(permission.getCode(), permission);
        }
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }

}
