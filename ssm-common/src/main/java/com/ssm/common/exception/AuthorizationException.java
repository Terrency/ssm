package com.ssm.common.exception;

import java.io.Serializable;

/**
 * 授权出错
 */
public class AuthorizationException extends AbstractException {

    private static final long serialVersionUID = 1L;

    private static final String ERROR_CODE = "F-0102";

    public AuthorizationException(Serializable detailMessage) {
        super(ERROR_CODE, detailMessage);
    }

    public AuthorizationException(Serializable detailMessage, Throwable cause) {
        super(ERROR_CODE, detailMessage, cause);
    }

}
