package com.ssm.common.base.subject;

import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import java.util.List;

public interface SecurityService {

    String BEAN_NAME = "securityService";

    boolean checkPassword(@NotEmpty String userCode, @NotEmpty String password);

    ActiveUser getActiveUser(@NotEmpty String userCode);

    List<Permission> getMenuList(@NotNull Long userId);

    List<Permission> getFunctionList(@NotNull Long userId);

}
