package com.ssm.sys.core.mapper.extension;

import com.ssm.common.base.page.Page;
import com.ssm.common.base.page.Pageable;

import java.util.List;
import java.util.Map;

public interface Select2Mapper {

    List<Map> selectActor(Map<String, Object> map);

    List<Map> selectFunc(Map<String, Object> map);

    List<Map> selectRole(Map<String, Object> map);

    Page<Map> selectActorPage(Pageable pageable);

    Page<Map> selectFuncPage(Pageable pageable);

}
