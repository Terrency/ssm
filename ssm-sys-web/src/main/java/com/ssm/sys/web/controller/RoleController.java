package com.ssm.sys.web.controller;

import com.ssm.common.model.ModelMap;
import com.ssm.common.web.base.ResponseData;
import com.ssm.sys.api.model.Role;
import com.ssm.sys.api.service.PermissionService;
import com.ssm.sys.api.service.RoleService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @Autowired
    private PermissionService permissionService;

    @RequiresPermissions("role:query")
    @RequestMapping(value = "/list", method = {RequestMethod.GET})
    public String list() {
        return "role/list";
    }

    @ResponseBody
    @RequiresPermissions("role:query")
    @RequestMapping(value = "/getList", method = {RequestMethod.POST})
    public List<Map> getList(@RequestParam Map<String, String> params) {
        return roleService.getList(new ModelMap(params));
    }

    @ResponseBody
    @RequiresPermissions("role:create")
    @RequestMapping(value = "/addSubmit", method = RequestMethod.POST)
    public ResponseData addSubmit(Role role) {
        int row = roleService.add(role);
        return new ResponseData().setData(row);
    }

    @ResponseBody
    @RequiresPermissions("role:update")
    @RequestMapping(value = "/editSubmit", method = {RequestMethod.POST})
    public ResponseData editSubmit(Role role) {
        int row = roleService.update(role);
        return new ResponseData().setData(row);
    }

    @ResponseBody
    @RequiresPermissions("role:delete")
    @RequestMapping(value = "/deleteSubmit", method = {RequestMethod.POST})
    public ResponseData deleteSubmit(@RequestParam Long[] ids) {
        int rows = roleService.delete(ids);
        return new ResponseData().setData(rows);
    }

    @ResponseBody
    @RequestMapping(value = "/getRoleListByUserId", method = {RequestMethod.GET, RequestMethod.POST})
    public List<Map> getRoleListByUserId(@RequestParam Long userId) {
        return roleService.getRoleListByUserId(userId);
    }

    @ResponseBody
    @RequestMapping(value = "/getPermissionList", method = {RequestMethod.GET, RequestMethod.POST})
    public Map<String, Object> getPermissionList(@RequestParam Long id) {
        Map<String, Object> map = new HashMap<>();
        map.put("permissionIds", permissionService.getPermissionIdsByRoleId(id));
        map.put("permissionList", permissionService.getList(new ModelMap()));
        return map;
    }

    @ResponseBody
    @RequestMapping(value = "/authSubmit", method = RequestMethod.POST)
    public ResponseData authSubmit(@RequestParam Long id, @RequestParam Long[] permissionIds) {
        int rows = roleService.assignPermissions(id, permissionIds);
        return new ResponseData().setData(rows);
    }

}
