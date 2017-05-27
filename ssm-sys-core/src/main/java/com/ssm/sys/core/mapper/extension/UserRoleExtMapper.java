package com.ssm.sys.core.mapper.extension;

import com.ssm.sys.api.model.UserRole;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserRoleExtMapper {

    int deleteByUserId(Long userId);

    int deleteByRoleId(Long roleId);

    List<Long> selectRoleIdsByUserId(Long userId);

    int insertBatch(List<UserRole> userRoles);

    int deleteBatch(@Param("userId") Long userId, @Param("roleIds") List<Long> roleIds);

}