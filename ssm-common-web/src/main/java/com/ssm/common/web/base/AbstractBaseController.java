package com.ssm.common.web.base;

import com.ssm.common.model.Model;
import com.ssm.common.service.BaseService;
import org.springframework.web.bind.annotation.*;

public abstract class AbstractBaseController<T extends Model> extends BaseController {

    protected abstract BaseService<T> getBaseService();

    @RequestMapping(value = "/{root}/{page}", method = {RequestMethod.GET})
    public String toPage(@PathVariable String root, @PathVariable String page) throws Exception {
        return root + "/" + page;
    }

    @ResponseBody
    @RequestMapping(value = "/addSubmit", method = RequestMethod.POST)
    public ResponseData addSubmit(T model) {
        return setData(getBaseService().add(model));
    }

    @ResponseBody
    @RequestMapping(value = "/editSubmit", method = {RequestMethod.POST})
    public ResponseData editSubmit(T model) {
        return setData(getBaseService().update(model));
    }

    @ResponseBody
    @RequestMapping(value = "/deleteSubmit", method = {RequestMethod.POST})
    public ResponseData deleteSubmit(@RequestParam Long[] ids) {
        return setData(getBaseService().delete(ids));
    }

    @ResponseBody
    @RequestMapping(value = "/getById", method = {RequestMethod.GET})
    public T getById(@RequestParam Long id) {
        return getBaseService().getById(id);
    }

}
