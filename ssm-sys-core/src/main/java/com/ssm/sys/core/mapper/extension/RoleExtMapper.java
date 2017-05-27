package com.ssm.sys.core.mapper.extension;

import java.util.List;
import java.util.Map;

public interface RoleExtMapper {

    List<Map> selectByUserId(Long userId);

    List<Map> selectSelective(Map<String, Object> map);

}