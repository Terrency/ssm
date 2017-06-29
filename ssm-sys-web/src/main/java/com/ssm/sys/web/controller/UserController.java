package com.ssm.sys.web.controller;

import com.ssm.common.model.ModelMap;
import com.ssm.common.page.Page;
import com.ssm.common.util.Constant;
import com.ssm.common.web.base.ResponseData;
import com.ssm.common.web.controller.AbstractController;
import com.ssm.common.web.datatable.DataTableRequest;
import com.ssm.common.web.datatable.DataTableResponse;
import com.ssm.common.web.datatable.DataTableUtility;
import com.ssm.common.web.wrapper.BaseWrapper;
import com.ssm.sys.api.model.User;
import com.ssm.sys.api.service.UserService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

@Controller
@RequestMapping("/user")
public class UserController extends AbstractController {

    @Autowired
    private UserService userService;

    @RequestMapping("/list")
    @RequiresPermissions("user:query")
    public String list() {
        return "user/list";
    }

    @ResponseBody
    @RequiresPermissions("user:query")
    @RequestMapping(value = "/getList", method = {RequestMethod.POST})
    public DataTableResponse<Map> getList(@RequestBody BaseWrapper<ModelMap> wrapper) {
        DataTableRequest dtArgs = wrapper.getDtArgs();
        Page<Map> page = userService.getPage(wrapper.getModel(), dtArgs.getStart() + 1, dtArgs.getLength());
        return DataTableUtility.buildDataTable(dtArgs, page);
    }

    @RequiresPermissions("user:create")
    @RequestMapping(value = "/add", method = {RequestMethod.GET})
    public String add() {
        return "user/add";
    }

    @ResponseBody
    @RequiresPermissions("user:create")
    @RequestMapping(value = "/addSubmit", method = RequestMethod.POST)
    public ResponseData addSubmit(User user) {
        return setData(userService.add(user));
    }

    @RequiresPermissions("user:update")
    @RequestMapping(value = "/edit", method = {RequestMethod.GET})
    public ModelAndView edit(@RequestParam Long id) {
        // 将数据填充到Request域并返回指定的逻辑视图
        return new ModelAndView("user/edit", "user", userService.getById(id));
    }

    @ResponseBody
    @RequiresPermissions("user:update")
    @RequestMapping(value = "/editSubmit", method = {RequestMethod.POST})
    public ResponseData editSubmit(User user) {
        return setData(userService.update(user));
    }

    @ResponseBody
    @RequiresPermissions("user:delete")
    @RequestMapping(value = "/deleteSubmit", method = {RequestMethod.POST})
    public ResponseData deleteSubmit(@RequestParam Long[] ids) {
        return setData(userService.delete(ids));
    }

    @ResponseBody
    @RequestMapping("/checkCode")
    public ModelMap checkCode(@RequestParam String code) {
        return new ModelMap(Constant.REMOTE_VALIDATION_KEY, userService.getByCode(code) == null);
    }

    @ResponseBody
    @RequestMapping("/checkPass")
    public ModelMap checkPass(@RequestParam String oldPass) {
        return new ModelMap(Constant.REMOTE_VALIDATION_KEY, userService.checkPass(oldPass));
    }

    @ResponseBody
    @RequestMapping("/changePass")
    public ResponseData changePass(@RequestParam String oldPass, @RequestParam String newPass) {
        return setData(userService.changePass(oldPass, newPass));
    }

    @ResponseBody
    @RequestMapping("/resetPass")
    public ResponseData resetPass(@RequestParam Long[] ids) {
        return setData(userService.resetPass(ids));
    }

    @ResponseBody
    @RequestMapping(value = "/authSubmit", method = {RequestMethod.GET, RequestMethod.POST})
    public ResponseData authSubmit(@RequestParam Long userId, @RequestParam Long[] roleIds) {
        return setData(userService.assignRoles(userId, roleIds));
    }

}
