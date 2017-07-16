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
    public ActiveUser getActiveUser(String userCode) {
        UserExt user = userExtMapper.selectByCode(userCode);
        if (user == null) {
            return null;
        }
        ActiveUser activeUser = new ActiveUser();
        activeUser.setId(user.getId());
        activeUser.setCode(user.getCode());
        activeUser.setName(user.getName());
        activeUser.setPass(user.getPass());
        activeUser.setSalt(user.getSalt());
        activeUser.setStatus(user.getStatus());
        activeUser.setManager(user.getManagerCode());
        return activeUser;
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
