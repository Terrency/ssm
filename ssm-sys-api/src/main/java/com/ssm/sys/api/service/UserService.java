package com.ssm.sys.api.service;

import com.ssm.common.model.ModelMap;
import com.ssm.common.page.Page;
import com.ssm.common.service.BaseService;
import com.ssm.sys.api.model.User;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Size;
import java.util.List;
import java.util.Map;

public interface UserService extends BaseService<User> {

    String BEAN_NAME = "userService";

    User getByCode(@NotEmpty String code);

    boolean checkPass(@NotEmpty String pass);

    int changePass(@NotEmpty String oldPass, @NotEmpty String newPass);

    int resetPass(@Size(min = 1) Long[] ids);

    int assignRoles(Long userId, Long[] roleIds);

    List<Map> getList(ModelMap param);

    Page<Map> getPage(ModelMap param, int offset, int length);

}
