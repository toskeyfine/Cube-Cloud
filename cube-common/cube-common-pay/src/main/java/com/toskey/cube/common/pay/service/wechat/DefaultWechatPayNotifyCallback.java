package com.toskey.cube.common.pay.service.wechat;

import com.toskey.cube.common.pay.constant.PayConstants;
import com.toskey.cube.common.pay.util.PayUtils;
import com.wechat.pay.java.core.Config;
import com.wechat.pay.java.core.exception.ValidationException;
import com.wechat.pay.java.core.notification.NotificationConfig;
import com.wechat.pay.java.core.notification.NotificationParser;
import com.wechat.pay.java.core.notification.RequestParam;
import jakarta.servlet.http.HttpServletRequest;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Map;

/**
 * DefaultWxPayNotifyCallback
 *
 * @author toskey
 * @version 1.0.0
 */
public class DefaultWechatPayNotifyCallback implements WechatPayNotifyCallback {

    private final Config config;

    public DefaultWechatPayNotifyCallback(Config config) {
        this.config = config;
    }

    @Override
    public boolean verify(Map<String, String> body) {
        return true;
    }

    @Override
    public Map<String, String> parseBody(HttpServletRequest request) {
        String sn = request.getHeader(PayConstants.WECHAT_REQUEST_HEADER_SERIAL);
        String nonce = request.getHeader(PayConstants.WECHAT_REQUEST_HEADER_NONCE);
        String signature = request.getHeader(PayConstants.WECHAT_REQUEST_HEADER_SIGNATURE);
        String timestamp = request.getHeader(PayConstants.WECHAT_REQUEST_HEADER_TIMESTAMP);

        StringBuilder body = new StringBuilder();
        try (BufferedReader br = request.getReader()) {
            String slice;
            while ((slice = br.readLine()) != null) {
                body.append(slice);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        RequestParam requestParam = new RequestParam.Builder()
                .serialNumber(sn)
                .nonce(nonce)
                .signature(signature)
                .timestamp(timestamp)
                .body(body.toString())
                .build();

        NotificationParser parser = new NotificationParser((NotificationConfig) config);
        try {
            return parser.parse(requestParam, Map.class);
        } catch (ValidationException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String invoke(Map<String, String> body) {
        return PayUtils.wechatSuccess();
    }
}
