package com.ssm.sys.api.service;

import com.ssm.common.model.ModelMap;
import com.ssm.common.service.BaseService;
import com.ssm.sys.api.model.Permission;
import com.ssm.sys.api.model.extension.PermissionExt;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;

public interface PermissionService extends BaseService<Permission> {

    String BEAN_NAME = "permissionService";

    PermissionExt getById(@NotNull Long id);

    List<Map> getList(ModelMap param);

    List<Long> getPermissionIdsByRoleId(@NotNull Long roleId);

}
