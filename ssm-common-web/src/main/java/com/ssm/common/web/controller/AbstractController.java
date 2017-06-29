package com.ssm.common.web.controller;

import com.ssm.common.enums.StatusCode;
import com.ssm.common.web.base.ResponseData;

public abstract class AbstractController {

    // @InitBinder
    // public void initBinder(WebDataBinder binder, WebRequest request) {
    //     binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat(Constant.DATETIME_FORMAT), Boolean.TRUE));
    // }

    // @RequestMapping(value = "/{root}/{page}", method = {RequestMethod.GET})
    // protected String toPage(@PathVariable String root, @PathVariable String page) throws Exception {
    //     return root + "/" + page;
    // }

    protected ResponseData setData(Object data) {
        return setData(true, Integer.toString(StatusCode.SUCCESS.getCode()), StatusCode.SUCCESS.getReasonPhrase(), data);
    }

    protected ResponseData setData(boolean success, String code, String message) {
        return setData(success, code, message, null);
    }

    protected ResponseData setData(boolean success, String code, String message, Object data) {
        return ResponseData.newInstance()
                .setSuccess(success)
                .setCode(code)
                .setMessage(message)
                .setData(data);
    }

}
