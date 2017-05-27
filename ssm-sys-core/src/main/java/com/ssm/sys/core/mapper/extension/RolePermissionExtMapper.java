package com.ssm.sys.core.mapper.extension;

import com.ssm.sys.api.model.RolePermission;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RolePermissionExtMapper {

    int deleteByPermissionId(Long permissionId);

    int deleteByRoleId(Long roleId);

    List<Long> selectPermissionIdsByRoleId(Long roleId);

    int insertBatch(List<RolePermission> rolePermissions);

    int deleteBatch(@Param("roleId") Long roleId, @Param("permissionIds") List<Long> permissionIds);

}
