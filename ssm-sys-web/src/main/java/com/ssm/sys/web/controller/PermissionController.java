package com.ssm.sys.web.controller;

import com.ssm.common.model.ModelMap;
import com.ssm.common.web.base.ResponseData;
import com.ssm.common.web.controller.AbstractController;
import com.ssm.sys.api.model.Permission;
import com.ssm.sys.api.service.PermissionService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/permission")
public class PermissionController extends AbstractController {

    @Autowired
    private PermissionService permissionService;

    @RequiresPermissions("permission:query")
    @RequestMapping(value = "/list", method = {RequestMethod.GET})
    public String list() throws Exception {
        return "permission/list";
    }

    @ResponseBody
    @RequiresPermissions("permission:query")
    @RequestMapping(value = "/getList", method = {RequestMethod.POST})
    public List<Map> getList(Map<String, String> params) {
        return permissionService.getList(new ModelMap(params));
    }

    @RequiresPermissions("permission:create")
    @RequestMapping(value = "/add", method = {RequestMethod.GET})
    public String add() throws Exception {
        return "permission/add";
    }

    @ResponseBody
    @RequiresPermissions("permission:create")
    @RequestMapping(value = "/addSubmit", method = RequestMethod.POST)
    public ResponseData addSubmit(@RequestBody Permission permission) {
        return setData(permissionService.add(permission));
    }

    @RequiresPermissions("permission:update")
    @RequestMapping(value = "/edit", method = {RequestMethod.GET})
    public ModelAndView edit(@RequestParam Long id) {
        // 将数据填充到Request域并返回指定的逻辑视图
        return new ModelAndView("permission/edit", "permission", permissionService.getById(id));
    }

    @ResponseBody
    @RequiresPermissions("permission:update")
    @RequestMapping(value = "/editSubmit", method = {RequestMethod.POST})
    public ResponseData editSubmit(@RequestBody Permission permission) {
        return setData(permissionService.update(permission));
    }

    @ResponseBody
    @RequiresPermissions("permission:delete")
    @RequestMapping(value = "/deleteSubmit", method = {RequestMethod.POST})
    public ResponseData deleteSubmit(@RequestParam Long id) {
        return setData(permissionService.delete(id));
    }

}
