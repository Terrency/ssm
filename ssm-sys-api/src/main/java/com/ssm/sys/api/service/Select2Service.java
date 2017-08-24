package com.ssm.sys.api.service;

import com.ssm.common.base.model.ModelMap;
import com.ssm.common.base.page.Page;
import com.ssm.common.base.page.Pageable;

import java.util.List;
import java.util.Map;

public interface Select2Service {

    String BEAN_NAME = "select2Service";

    List<Map> queryActor(ModelMap param);

    List<Map> queryFunc(ModelMap param);

    List<Map> queryRole(ModelMap param);

    Page<Map> queryActor(Pageable<ModelMap> pageable);

    Page<Map> queryFunc(Pageable<ModelMap> pageable);

}
