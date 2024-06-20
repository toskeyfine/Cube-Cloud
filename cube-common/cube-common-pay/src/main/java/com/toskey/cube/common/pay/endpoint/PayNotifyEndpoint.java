package com.toskey.cube.common.pay.endpoint;

import com.toskey.cube.common.pay.service.alipay.AlipayNotifyCallback;
import com.toskey.cube.common.pay.service.wechat.WechatPayNotifyCallback;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 支付异步通知端点
 *
 * @author toskey
 * @version 1.0.0
 */
@AllArgsConstructor
@RestController
@RequestMapping("/zx/pay")
public class PayNotifyEndpoint {

    private final WechatPayNotifyCallback wechatPayNotifyCallback;

    private final AlipayNotifyCallback alipayNotifyCallback;

    @PostMapping("/wechat/notify")
    public String wxPayNotification(HttpServletRequest request) {
        Map<String, String> body = wechatPayNotifyCallback.parseBody(request);
        /*if (wechatPayNotifyCallback.verify(body)) {
            return wechatPayNotifyCallback.invoke(body);
        }*/
        return wechatPayNotifyCallback.invoke(body);
    }

    @PostMapping("/alipay/notify")
    public String alipayNotification(HttpServletRequest request) {
        Map<String, String> body = alipayNotifyCallback.parseBody(request);
        if (alipayNotifyCallback.verify(body)) {
            return wechatPayNotifyCallback.invoke(body);
        }
        return "success";
    }

}
