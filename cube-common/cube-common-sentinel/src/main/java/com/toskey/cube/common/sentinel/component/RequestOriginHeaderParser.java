package com.toskey.cube.common.sentinel.component;

import com.alibaba.csp.sentinel.adapter.spring.webmvc.callback.RequestOriginParser;
import com.toskey.cube.common.core.util.StringUtils;
import jakarta.servlet.http.HttpServletRequest;

/**
 * 从请求头中解析源
 *
 * @author toskey
 * @version 1.0.0
 */
public class RequestOriginHeaderParser implements RequestOriginParser  {

    @Override
    public String parseOrigin(HttpServletRequest request) {
        String origin = request.getHeader("Origin");
        if (StringUtils.isBlank(origin)) {
            origin = "undefined";
        }
        return origin;
    }

}
