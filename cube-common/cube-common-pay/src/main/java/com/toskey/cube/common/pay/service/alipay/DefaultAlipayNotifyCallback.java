package com.toskey.cube.common.pay.service.alipay;

import com.alipay.api.internal.util.AlipaySignature;
import com.toskey.cube.common.pay.config.PayProperties;
import jakarta.servlet.http.HttpServletRequest;
import lombok.SneakyThrows;

import java.util.HashMap;
import java.util.Map;

/**
 * DefaultZxAlipayNotifyCallback
 *
 * @author toskey
 * @version 1.0.0
 */
public class DefaultAlipayNotifyCallback implements AlipayNotifyCallback {

    private final PayProperties.AliPayProperties properties;

    public DefaultAlipayNotifyCallback(PayProperties payProperties) {
        this.properties = payProperties.getAlipay();
    }

    @SneakyThrows
    @Override
    public boolean verify(Map<String, String> body) {
        String sign = body.get("sign");
        String content = AlipaySignature.getSignCheckContentV1(body);
        return AlipaySignature.rsa256CheckContent(content, sign, properties.getPublicKey(), "UTF-8");
    }

    @Override
    public Map<String, String> parseBody(HttpServletRequest request) {
        if (request.getParameter("trade_status").equals("TRADE_SUCCESS")) {
            Map<String, String> params = new HashMap<>();
            Map<String, String[]> requestParams = request.getParameterMap();
            for (String paramName : requestParams.keySet()) {
                params.put(paramName, request.getParameter(paramName));
            }
            return params;
        }
        return null;
    }

    @Override
    public String invoke(Map<String, String> body) {
        return "success";
    }
}
