package com.toskey.cube.common.pay.constant;

/**
 * 常量定义
 *
 * @author toskey
 * @version 1.0.0
 */
public interface PayConstants {

    String WECHAT_REQUEST_HEADER_SERIAL = "Wechatpay-Serial";

    String WECHAT_REQUEST_HEADER_NONCE = "Wechatpay-Nonce";

    String WECHAT_REQUEST_HEADER_SIGNATURE = "Wechatpay-Signature";

    String WECHAT_REQUEST_HEADER_TIMESTAMP = "Wechatpay-Timestamp";

    String ALIPAY_GATEWAY_PROD = "https://openapi.alipay.com/gateway.do";

    String ALIPAY_GATEWAY_DEV = "https://openapi-sandbox.dl.alipaydev.com/gateway.do";
}
