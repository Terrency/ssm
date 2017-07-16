package com.ssm.sys.api.service;

import com.ssm.common.base.model.ModelMap;
import com.ssm.common.base.service.BaseService;
import com.ssm.sys.api.model.Role;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;

public interface RoleService extends BaseService<Role> {

    String BEAN_NAME = "roleService";

    List<Map> getList(ModelMap param);

    List<Map> getRoleListByUserId(@NotNull Long userId);

    int assignPermissions(@NotNull Long roleId, Long[] permissionIds);

}
