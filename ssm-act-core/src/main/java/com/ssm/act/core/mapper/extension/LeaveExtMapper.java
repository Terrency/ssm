package com.ssm.act.core.mapper.extension;

import com.ssm.common.base.model.ModelMap;
import com.ssm.common.base.page.Page;
import com.ssm.common.base.page.Pageable;

import java.util.List;
import java.util.Map;

public interface LeaveExtMapper {

    List<Map> selectByApplicant(String applicant);

    List<Map> selectSelective(ModelMap modelMap);

    Page<Map> selectPage(Pageable<ModelMap> pageable);

}
