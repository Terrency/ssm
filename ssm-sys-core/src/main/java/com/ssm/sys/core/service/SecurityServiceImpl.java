package com.ssm.sys.core.service;

import com.ssm.common.base.subject.ActiveUser;
import com.ssm.common.base.subject.Permission;
import com.ssm.common.base.subject.SecurityService;
import com.ssm.sys.api.model.extension.UserExt;
import com.ssm.sys.core.mapper.extension.PermissionExtMapper;
import com.ssm.sys.core.mapper.extension.UserExtMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service(SecurityService.BEAN_NAME)
public class SecurityServiceImpl implements SecurityService {

    @Autowired
    private UserExtMapper userExtMapper;

    @Autowired
    private PermissionExtMapper permissionExtMapper;

    @Override
    public boolean checkPassword(String userCode, String password) {
        UserExt user = userExtMapper.selectByCode(userCode);
        return user != null && user.getPass().equals(password);
    }

    @Override
    public ActiveUser getActiveUser(String userCode) {
        UserExt user = userExtMapper.selectByCode(userCode);
        return user != null ? new ActiveUser(
                user.getId(),
                user.getCode(),
                user.getName(),
                user.getPass(),
                user.getSalt(),
                user.getStatus(),
                user.getManagerCode()
        ) : null;
    }

    @Override
    public List<Permission> getMenuList(Long userId) {
        return permissionExtMapper.selectMenuList(userId);
    }

    @Override
    public List<Permission> getFunctionList(Long userId) {
        return permissionExtMapper.selectFunctionList(userId);
    }

}
