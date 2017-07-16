package com.ssm.common.base.exception;

import java.io.Serializable;

/**
 * 认证出错
 */
public class AuthenticateException extends AbstractException {

    private static final long serialVersionUID = 1L;

    private static final String ERROR_CODE = "F-0101";

    public AuthenticateException(Serializable detailMessage) {
        super(ERROR_CODE, detailMessage);
    }

    public AuthenticateException(Serializable detailMessage, Throwable cause) {
        super(ERROR_CODE, detailMessage, cause);
    }

}
