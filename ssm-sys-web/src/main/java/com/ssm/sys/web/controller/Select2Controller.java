package com.ssm.sys.web.controller;

import com.ssm.common.model.ModelMap;
import com.ssm.common.page.Page;
import com.ssm.sys.api.service.Select2Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/select2")
public class Select2Controller {

    @Autowired
    private Select2Service select2Service;

    @ResponseBody
    @RequestMapping(value = "/queryActor", method = RequestMethod.POST)
    public Page<Map> queryActor(@RequestParam Map<String, String> params,
                                @RequestParam(defaultValue = "1") Integer page,
                                @RequestParam(defaultValue = "10") Integer limit) {
        return select2Service.queryActor(new ModelMap(params), (page - 1) * limit, limit);
    }

    @ResponseBody
    @RequestMapping(value = "/queryFunc", method = RequestMethod.POST)
    public Page<Map> queryFunc(@RequestParam Map<String, String> params,
                               @RequestParam(defaultValue = "1") Integer page,
                               @RequestParam(defaultValue = "10") Integer limit) {
        return select2Service.queryFunc(new ModelMap(params), (page - 1) * limit, limit);
    }

    @ResponseBody
    @RequestMapping(value = "/queryRole", method = RequestMethod.POST)
    public List<Map> queryRole(@RequestParam Map<String, String> params) {
        return select2Service.queryRole(new ModelMap(params));
    }

}
