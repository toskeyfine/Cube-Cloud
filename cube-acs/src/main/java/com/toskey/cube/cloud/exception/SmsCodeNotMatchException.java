package com.toskey.cube.cloud.exception;

import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2Error;

/**
 * SmsCodeNotMatchException
 *
 * @author toskey
 * @version 1.0.0
 */
public class SmsCodeNotMatchException extends OAuth2AuthenticationException {
    public SmsCodeNotMatchException(String msg) {
        super(msg);
    }
    public SmsCodeNotMatchException(OAuth2Error error, Throwable cause) {
        super(error, cause);
    }

}
