package com.ssm.sys.core.mapper.extension;

import com.ssm.common.base.model.ModelMap;
import com.ssm.common.base.page.Page;
import com.ssm.common.base.page.Pageable;
import com.ssm.sys.api.model.User;
import com.ssm.sys.api.model.extension.UserExt;

import java.util.List;
import java.util.Map;

public interface UserExtMapper {

    int insertBatch(List<User> users);

    int deleteBatch(Long[] ids);

    UserExt selectByCode(String code);

    List<Map> selectSelective(Map<String, Object> map);

    Page<Map> selectPage(Pageable<ModelMap> pageable);

}
