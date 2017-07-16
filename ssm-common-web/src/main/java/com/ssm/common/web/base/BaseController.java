package com.ssm.common.web.base;

import com.ssm.common.base.enums.StatusCode;

public class BaseController {

    public static final String FORWARD_PREFIX = "forward:";
    public static final String REDIRECT_PREFIX = "redirect:";

    // @InitBinder
    // public void initBinder(WebDataBinder binder, WebRequest request) {
    //     binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat(Constant.DATETIME_FORMAT), Boolean.TRUE));
    // }

    public ResponseData setData(Object data) {
        return setData(true, Integer.toString(StatusCode.SUCCESS.getCode()), StatusCode.SUCCESS.getReasonPhrase(), data);
    }

    public ResponseData setData(boolean success, String code, String message) {
        return setData(success, code, message, null);
    }

    public ResponseData setData(boolean success, String code, String message, Object data) {
        return ResponseData.newInstance()
                .setSuccess(success)
                .setCode(code)
                .setMessage(message)
                .setData(data);
    }

    public String forward(String view) {
        return FORWARD_PREFIX + view;
    }

    public String redirect(String view) {
        return REDIRECT_PREFIX + view;
    }

}
