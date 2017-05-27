package com.ssm.sys.core.mapper.extension;

import com.ssm.common.subject.Permission;
import com.ssm.sys.api.model.extension.PermissionExt;

import java.util.List;
import java.util.Map;

public interface PermissionExtMapper {

    List<Permission> selectMenuList(Long userId);

    List<Permission> selectFunctionList(Long userId);

    int deleteByParentId(Long parentId);

    int deleteByPrimaryKey(Long id);

    PermissionExt selectByPrimaryKey(Long id);

    List<Map> selectSelective(Map<String, Object> map);

    List<com.ssm.sys.api.model.Permission> selectByParentId(Long parentId);

}