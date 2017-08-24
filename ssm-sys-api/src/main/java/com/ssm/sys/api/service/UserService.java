package com.ssm.sys.api.service;

import com.ssm.common.base.model.ModelMap;
import com.ssm.common.base.page.Page;
import com.ssm.common.base.page.Pageable;
import com.ssm.common.base.service.BaseService;
import com.ssm.sys.api.model.User;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Map;

public interface UserService extends BaseService<User> {

    String BEAN_NAME = "userService";

    User getByCode(@NotEmpty String code);

    boolean checkPass(@NotNull Long id, @NotEmpty String pass);

    int changePass(@NotNull Long id, @NotEmpty String oldPass, @NotEmpty String newPass);

    int resetPass(@Size(min = 1) Long[] ids);

    int assignRoles(Long userId, Long[] roleIds);

    List<Map> getList(ModelMap param);

    Page<Map> getPage(Pageable<ModelMap> pageable);

}
