package com.toskey.cube.common.core.constant;

/**
 * SecurityConstants
 *
 * @author toskey
 * @version 1.0
 */
public interface SecurityConstants {

    String USER_ID = "user-id";

    String USERNAME = "username";

    String TENANT_ID = "tenant-id";

    String AUTHORITY_ROLE_PREFIX = "ROLE_";

    String HEADER_SERVICE = "S-SERVICE";

    String HEADER_SERVICE_CUBE = "cube-cloud";

    String HEADER_TENANT = "X-TENANT";

    long LOGIN_ERROR_MAX_TIMES = 5;

    String GRANT_TYPE_SMS = "sms";

    String SMS_PARAMETER_MOBILE = "mobile";

    String SMS_PARAMETER_CODE = "code";

}
