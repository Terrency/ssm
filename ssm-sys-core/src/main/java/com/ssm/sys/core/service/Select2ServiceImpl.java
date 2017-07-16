package com.ssm.sys.core.service;

import com.ssm.common.base.model.ModelMap;
import com.ssm.common.base.page.Page;
import com.ssm.common.base.page.PageRequest;
import com.ssm.sys.api.service.Select2Service;
import com.ssm.sys.core.mapper.extension.Select2Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service(Select2Service.BEAN_NAME)
public class Select2ServiceImpl implements Select2Service {

    @Autowired
    private Select2Mapper select2Mapper;

    @Override
    public List<Map> queryActor(ModelMap param) {
        return select2Mapper.selectActor(param);
    }

    @Override
    public List<Map> queryFunc(ModelMap param) {
        return select2Mapper.selectFunc(param);
    }

    @Override
    public List<Map> queryRole(ModelMap param) {
        return select2Mapper.selectRole(param);
    }

    @Override
    public Page<Map> queryActor(ModelMap param, int offset, int length) {
        return select2Mapper.selectActorPage(PageRequest.newInstance(param, offset, length));
    }

    @Override
    public Page<Map> queryFunc(ModelMap param, int offset, int length) {
        return select2Mapper.selectFuncPage(PageRequest.newInstance(param, offset, length));
    }

}
