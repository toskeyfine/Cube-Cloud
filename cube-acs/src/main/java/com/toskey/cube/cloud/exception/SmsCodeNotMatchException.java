package com.toskey.cube.cloud.exception;

import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2Error;

/**
 * SmsCodeNotMatchException
 *
 * @author lis
 * @version 1.0
 * @description TODO
 * @date 2024/6/18 11:18
 */
public class SmsCodeNotMatchException extends OAuth2AuthenticationException {
    public SmsCodeNotMatchException(String msg) {
        super(msg);
    }
    public SmsCodeNotMatchException(OAuth2Error error, Throwable cause) {
        super(error, cause);
    }

}
