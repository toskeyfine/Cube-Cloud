package com.toskey.cube.common.pay.service.wechat;

import jakarta.servlet.http.HttpServletRequest;

import java.util.Map;

/**
 * 支付回调处理
 *
 * @author toskey
 * @version 1.0.0
 */
public interface WechatPayNotifyCallback {

    /**
     * 验证
     *
     * @param body 回调请求的参数
     * @return boolean
     */
    boolean verify(Map<String, String> body);

    /**
     * 通过回调的request解析出body，并转为Map<String, Object>
     *
     * @param request 回调请求
     * @return Map<Object>
     */
    Map<String, String> parseBody(HttpServletRequest request);

    /**
     * 可在此实现数据存储等业务逻辑
     *
     * @param body 回调请求的参数
     * @return String
     */
    String invoke(Map<String, String> body);

}
