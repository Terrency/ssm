package com.ssm.sys.api.service;

import com.ssm.common.model.ModelMap;
import com.ssm.common.page.Page;

import java.util.List;
import java.util.Map;

public interface Select2Service {

    String BEAN_NAME = "select2Service";

    List<Map> queryActor(ModelMap param);

    List<Map> queryFunc(ModelMap param);

    List<Map> queryRole(ModelMap param);

    Page<Map> queryActor(ModelMap param, int offset, int length);

    Page<Map> queryFunc(ModelMap param, int offset, int length);

}
