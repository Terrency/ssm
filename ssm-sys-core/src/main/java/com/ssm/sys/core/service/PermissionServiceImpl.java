package com.ssm.sys.core.service;

import com.ssm.common.core.mapper.BaseMapper;
import com.ssm.common.core.service.AbstractBaseService;
import com.ssm.common.base.exception.BusinessException;
import com.ssm.common.base.model.ModelMap;
import com.ssm.common.base.util.Constant;
import com.ssm.sys.api.model.Permission;
import com.ssm.sys.api.model.extension.PermissionExt;
import com.ssm.sys.api.service.PermissionService;
import com.ssm.sys.core.enums.PermissionStatus;
import com.ssm.sys.core.enums.ResourceType;
import com.ssm.sys.core.mapper.PermissionMapper;
import com.ssm.sys.core.mapper.extension.PermissionExtMapper;
import com.ssm.sys.core.mapper.extension.RolePermissionExtMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service(PermissionService.BEAN_NAME)
public class PermissionServiceImpl extends AbstractBaseService<Permission> implements PermissionService {

    @Autowired
    private PermissionMapper permissionMapper;

    @Autowired
    private PermissionExtMapper permissionExtMapper;

    @Autowired
    private RolePermissionExtMapper rolePermissionExtMapper;

    @Override
    protected BaseMapper<Permission> getBaseMapper() {
        return permissionMapper;
    }

    @Override
    public int add(Permission permission) {
        Long parentId = permission.getParentId();
        if (parentId != null) {
            Permission parent = permissionMapper.selectByPrimaryKey(parentId);
            if (parent == null) {
                throw new BusinessException("The record which id = " + parentId + " is not found!");
            }
            if (ResourceType.valueOf(parent.getType()) != ResourceType.MENU) {
                throw new BusinessException("The node which you select is not a menu node");
            }
            permission.setParentId(parentId);
            if (StringUtils.isNotBlank(parent.getParentIds())) {
                permission.setParentIds(parent.getParentIds() + Long.toString(parentId) + Constant.SLASHES_SEPARATOR);
            } else {
                permission.setParentIds(Long.toString(parentId) + Constant.SLASHES_SEPARATOR);
            }
        } else {
            permission.setParentId(null);
            permission.setParentIds(null);
        }
        permission.setStatus(PermissionStatus.AVAILABLE.getValue());
        return super.add(permission);
    }

    @Override
    public int update(Permission permission) {
        Long parentId = permission.getParentId();
        if (parentId != null) {
            Permission parent = permissionMapper.selectByPrimaryKey(parentId);
            if (parent == null) {
                throw new BusinessException("The record which id = " + parentId + " is not found!");
            }
            if (ResourceType.valueOf(parent.getType()) != ResourceType.MENU) {
                throw new BusinessException("The node which you select is not a menu node");
            }
            permission.setParentId(parentId);
            permission.setParentIds(StringUtils.trimToEmpty(parent.getParentIds()) + Long.toString(parentId) + Constant.SLASHES_SEPARATOR);
        } else {
            permission.setParentId(null);
            permission.setParentIds(null);
        }
        updateSubParentIds(permission);
        return permissionMapper.updateByPrimaryKey(permission);
    }

    /**
     * 树形菜单的递归更新ParentIds值
     */
    private void updateSubParentIds(Permission parent) {
        List<Permission> children = permissionExtMapper.selectByParentId(parent.getId());
        if (children != null && !children.isEmpty()) {
            for (Permission child : children) {
                child.setParentIds(StringUtils.trimToEmpty(parent.getParentIds()) + Long.toString(parent.getId()) + Constant.SLASHES_SEPARATOR);
                permissionMapper.updateByPrimaryKeySelective(child);
                updateSubParentIds(child);
            }
        }
    }

    @Override
    public int delete(Long id) {
        rolePermissionExtMapper.deleteByPermissionId(id);
        permissionExtMapper.deleteByParentId(id);
        return super.delete(id);
    }

    @Override
    public PermissionExt getById(Long id) {
        return permissionExtMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<Map> getList(ModelMap param) {
        return permissionExtMapper.selectSelective(param);
    }

    @Override
    public List<Long> getPermissionIdsByRoleId(Long roleId) {
        return rolePermissionExtMapper.selectPermissionIdsByRoleId(roleId);
    }

}
