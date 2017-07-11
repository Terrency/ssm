package com.ssm.sys.core.service;

import com.ssm.common.core.mapper.BaseMapper;
import com.ssm.common.core.service.AbstractBaseService;
import com.ssm.common.model.ModelMap;
import com.ssm.sys.api.model.Role;
import com.ssm.sys.api.model.RolePermission;
import com.ssm.sys.api.service.RoleService;
import com.ssm.sys.core.enums.RoleStatus;
import com.ssm.sys.core.mapper.RoleMapper;
import com.ssm.sys.core.mapper.extension.RoleExtMapper;
import com.ssm.sys.core.mapper.extension.RolePermissionExtMapper;
import com.ssm.sys.core.mapper.extension.UserRoleExtMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Service(RoleService.BEAN_NAME)
public class RoleServiceImpl extends AbstractBaseService<Role> implements RoleService {

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private RoleExtMapper roleExtMapper;

    @Autowired
    private UserRoleExtMapper userRoleExtMapper;

    @Autowired
    private RolePermissionExtMapper rolePermissionExtMapper;

    @Override
    protected BaseMapper<Role> getBaseMapper() {
        return roleMapper;
    }

    @Override
    public int add(Role role) {
        role.setStatus(RoleStatus.AVAILABLE.getValue());
        return super.add(role);
    }

    @Override
    public int delete(Long id) {
        userRoleExtMapper.deleteByRoleId(id);
        rolePermissionExtMapper.deleteByRoleId(id);
        return super.delete(id);
    }

    @Override
    public List<Map> getList(ModelMap param) {
        return roleExtMapper.selectSelective(param);
    }

    @Override
    public List<Map> getRoleListByUserId(Long userId) {
        return roleExtMapper.selectByUserId(userId);
    }

    @Override
    public int assignPermissions(Long roleId, Long[] permissionIds) {
        int count = 0;
        if (permissionIds != null && permissionIds.length > 0) {
            List<Long> list = Arrays.asList(permissionIds);
            List<Long> deleteList = rolePermissionExtMapper.selectPermissionIdsByRoleId(roleId);
            List<Long> containList = new ArrayList<>(deleteList);
            deleteList.removeAll(list);
            if (deleteList.size() > 0) {
                count += rolePermissionExtMapper.deleteBatch(roleId, deleteList);
            }
            List<Long> insertList = new ArrayList<>(list);
            containList.retainAll(insertList);
            insertList.removeAll(containList);
            List<RolePermission> rolePermissionList = new ArrayList<>();
            for (Long permissionId : insertList) {
                RolePermission rolePermission = new RolePermission();
                rolePermission.setRoleId(roleId);
                rolePermission.setPermissionId(permissionId);
                rolePermissionList.add(rolePermission);
            }
            if (rolePermissionList.size() > 0) {
                count += rolePermissionExtMapper.insertBatch(rolePermissionList);
            }
        } else {
            count += rolePermissionExtMapper.deleteByRoleId(roleId);
        }
        return count;
    }

}
