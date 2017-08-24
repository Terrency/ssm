package com.ssm.sys.core.service;

import com.ssm.common.base.exception.BusinessException;
import com.ssm.common.base.model.ModelMap;
import com.ssm.common.base.page.Page;
import com.ssm.common.base.page.Pageable;
import com.ssm.common.base.util.SecurityUtils;
import com.ssm.common.core.mapper.BaseMapper;
import com.ssm.common.core.service.AbstractBaseService;
import com.ssm.sys.api.model.User;
import com.ssm.sys.api.model.UserRole;
import com.ssm.sys.api.service.UserService;
import com.ssm.sys.core.enums.UserStatus;
import com.ssm.sys.core.mapper.UserMapper;
import com.ssm.sys.core.mapper.extension.UserExtMapper;
import com.ssm.sys.core.mapper.extension.UserRoleExtMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Service(UserService.BEAN_NAME)
public class UserServiceImpl extends AbstractBaseService<User> implements UserService {

    private static final String DEFAULT_PASS = "111111";

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserExtMapper userExtMapper;

    @Autowired
    private UserRoleExtMapper userRoleExtMapper;

    @Override
    protected BaseMapper<User> getBaseMapper() {
        return userMapper;
    }

    @Override
    public int add(User user) {
        String salt = SecurityUtils.generateRandomNumber();
        user.setStatus(UserStatus.UNLOCKED.getValue());
        user.setSalt(salt);
        user.setPass(SecurityUtils.generateMd5Hash(DEFAULT_PASS, salt));
        return super.add(user);
    }

    @Override
    public int update(User user) {
        user.setSalt(null);
        user.setPass(null);
        return userMapper.updateByPrimaryKeySelective(user);
    }

    @Override
    public int delete(Long id) {
        userRoleExtMapper.deleteByUserId(id);
        return userMapper.deleteByPrimaryKey(id);
    }

    @Override
    public User getByCode(String code) {
        return userExtMapper.selectByCode(code);
    }

    @Override
    public boolean checkPass(Long id, String pass) {
        User user = userMapper.selectByPrimaryKey(id);
        if (user == null) {
            throw new BusinessException(String.format("The user which id = %d does not exist！", id));
        }
        return user.getPass().equals(SecurityUtils.generateMd5Hash(pass, user.getSalt()));
    }

    @Override
    public int changePass(Long id, String oldPass, String newPass) {
        if (!checkPass(id, oldPass)) {
            throw new BusinessException("The password is incorrect！");
        }
        User user = new User();
        user.setId(id);
        String salt = SecurityUtils.generateRandomNumber();
        user.setSalt(salt);
        user.setPass(SecurityUtils.generateMd5Hash(newPass, salt));
        return userMapper.updateByPrimaryKeySelective(user);
    }

    @Override
    public int resetPass(Long[] ids) {
        int count = 0;
        for (Long id : ids) {
            String salt = SecurityUtils.generateRandomNumber();
            User user = new User();
            user.setId(id);
            user.setSalt(salt);
            user.setPass(SecurityUtils.generateMd5Hash(DEFAULT_PASS, salt));
            count += userMapper.updateByPrimaryKeySelective(user);
        }
        return count;
    }

    @Override
    public int assignRoles(Long userId, Long[] roleIds) {
        int count = 0;
        if (roleIds != null && roleIds.length > 0) {
            List<Long> list = Arrays.asList(roleIds);
            List<Long> deleteList = userRoleExtMapper.selectRoleIdsByUserId(userId);
            List<Long> containList = new ArrayList<>(deleteList);
            deleteList.removeAll(list);
            if (deleteList.size() > 0) {
                count += userRoleExtMapper.deleteBatch(userId, deleteList);
            }
            List<Long> insertList = new ArrayList<>(list);
            containList.retainAll(insertList);
            insertList.removeAll(containList);
            List<UserRole> userRoleList = new ArrayList<>();
            for (Long roleId : insertList) {
                UserRole userRole = new UserRole();
                userRole.setUserId(userId);
                userRole.setRoleId(roleId);
                userRoleList.add(userRole);
            }
            if (userRoleList.size() > 0) {
                count += userRoleExtMapper.insertBatch(userRoleList);
            }
        } else {
            count += userRoleExtMapper.deleteByUserId(userId);
        }
        return count;
    }

    @Override
    public List<Map> getList(ModelMap param) {
        return userExtMapper.selectSelective(param);
    }

    @Override
    public Page<Map> getPage(Pageable<ModelMap> pageable) {
        return userExtMapper.selectPage(pageable);
    }

}
