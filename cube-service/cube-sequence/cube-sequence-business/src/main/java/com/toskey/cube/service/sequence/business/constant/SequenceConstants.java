package com.toskey.cube.service.sequence.business.constant;

/**
 * SequenceConstants
 *
 * @author toskey
 * @version 1.0.0
 */
public class SequenceConstants {

    public static final String REDIS_INCREMENT_ID_PREFIX = "cr:sequence:increment:rds";

    public static final String REDIS_INCREMENT_ID_VALUE_SUFFIX = "current";

    public static final String REDIS_INCREMENT_ID_PARAM_SUFFIX = "param";

    public static final String DB_ID_PREFIX = "cr_sequence_";

    public static final String DB_ID_PARAM_CACHE_PREFIX = "cr:sequence:increment:db";

    public static final String DB_ID_PARAM_CACHE_SUFFIX = "param";

    public static final String REDIS_SECTION_KEY_PREFIX = "cr:sequence:combined";

    public static final String REDIS_SECTION_PARAM_KEY_SUFFIX = "section:param";

    public static final String REDIS_SECTION_CURRENT_KEY_SUFFIX = "section:current";

    public static final String REDIS_COMBINED_KEY_PREFIX = "cr:sequence:combined";

    public static final String REDIS_COMBINED_KEY_SUFFIX = "combined_id";

    public static final String REDIS_SNOWFLAKE_PREFIX = "cr:sequence:snowflake";

    public static final String REDIS_SNOWFLAKE_SEQ_SUFFIX = "sequence";

    public static final String REDIS_SNOWFLAKE_TIME_SUFFIX = "timestamps";

    public static String buildRedisIncrementValueKey(String code) {
        return String.format("%s:%s:%s", REDIS_INCREMENT_ID_PREFIX, code, REDIS_INCREMENT_ID_VALUE_SUFFIX);
    }

    public static String buildRedisIncrementParamKey(String code) {
        return String.format("%s:%s:%s", REDIS_INCREMENT_ID_PREFIX, code, REDIS_INCREMENT_ID_PARAM_SUFFIX);
    }

    public static String buildDBIncrementParamKey(String code) {
        return String.format("%s:%s:%s", DB_ID_PARAM_CACHE_PREFIX, code, DB_ID_PARAM_CACHE_SUFFIX);
    }

    public static  String buildSectionParamKey(String code) {
        return String.format("%s:%s:%s", REDIS_SECTION_KEY_PREFIX, code, REDIS_SECTION_PARAM_KEY_SUFFIX);
    }

    public static String buildSectionCurrentKey(String code) {
        return String.format("%s:%s:%s", REDIS_SECTION_KEY_PREFIX, code, REDIS_SECTION_CURRENT_KEY_SUFFIX);
    }

    public static String buildCombinedKey(String code) {
        return String.format("%s:%s:%s", REDIS_COMBINED_KEY_PREFIX, code, REDIS_COMBINED_KEY_SUFFIX);
    }

    public static String buildSnowflakeSequenceKey(String code) {
        return String.format("%s:%s:%s", REDIS_SNOWFLAKE_PREFIX, code, REDIS_SNOWFLAKE_SEQ_SUFFIX);
    }

    public static String buildSnowflakeTimeKey(String code) {
        return String.format("%s:%s:%s", REDIS_SNOWFLAKE_PREFIX, code, REDIS_SNOWFLAKE_TIME_SUFFIX);
    }

}
