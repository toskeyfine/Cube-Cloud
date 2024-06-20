package com.toskey.cube.common.core.constant;

/**
 * CacheConstants
 *
 * @author toskey
 * @version 1.0.0
 */
public interface CacheConstants {

    String CACHE_GLOBAL_PREFIX = "cube";

    String CACHE_LOGIN_ERRORS = "login_errors";

    long EXPIRE_LOGIN_ERRORS = 20 * 60 * 1000;

    String CACHE_AUTHORIZATION_KEY = "authorization";

    String CACHE_REGISTERED_CLIENT_KEY = "registered_client";

    String CACHE_LOGIN_SMS_CODE_PREFIX = CACHE_GLOBAL_PREFIX + ":login:sms:";

    String CACHE_CONFIG_PREFIX = CACHE_GLOBAL_PREFIX + ":config:";
}
