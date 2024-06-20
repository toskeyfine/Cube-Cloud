package com.toskey.cube.common.pay.util;

import org.apache.commons.lang3.math.NumberUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;

/**
 * ZxPayUtils
 *
 * @author toskey
 * @version 1.0.0
 */
public final class PayUtils {

    public static String wechatSuccess() {
        Map<String, String> result = new HashMap<>();
        result.put("return_code", "SUCCESS");
        result.put("return_msg", "OK");
        return toXml(result);
    }

    public static String toXml(Map<String, String> body) {
        StringBuilder xml = new StringBuilder("<xml>");
        for (Map.Entry<String, String> entry : body.entrySet()) {
            xml.append("<").append(entry.getKey()).append(">");
            xml.append(entry.getValue());
            xml.append("</").append(entry.getKey()).append(">");
        }
        xml.append("</xml>");
        return xml.toString();
    }

    /**
     * 分转元
     *
     * @param value
     * @return
     */
    public static String toYuan(String value, int scale) {
        BigDecimal src = NumberUtils.toScaledBigDecimal(value);
        return src.divide(new BigDecimal(100), scale, RoundingMode.HALF_UP).toString();
    }
}
