package com.toskey.cube.common.pay.service.alipay;

import jakarta.servlet.http.HttpServletRequest;

import java.util.Map;

/**
 * 支付回调处理
 *
 * @author toskey
 * @version 1.0.0
 */
public interface AlipayNotifyCallback {

    /**
     * 验证
     *
     * @param body 回调请求的参数
     * @return boolean
     */
    boolean verify(Map<String, String> body);

    /**
     * 解析请求体
     *
     * @param request 回调请求
     * @return Map<Object>
     */
    Map<String, String> parseBody(HttpServletRequest request);

    /**
     * 回调处理
     *
     * @param body 回调请求的参数
     * @return String
     */
    String invoke(Map<String, String> body);

}
