package com.toskey.cube.common.pay.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 支付配置
 *
 * @author toskey
 * @version 1.0.0
 */
@Getter
@Setter
@ConfigurationProperties("cube.pay")
public final class PayProperties {

    private AliPayProperties alipay;

    private WechatPayProperties wechat;

    @Getter
    @Setter
    public static class AliPayProperties {

        private String env = "dev";

        private String appId;

        private String privateKey;

        private String publicKey;

        private String notifyUrl;
    }

    @Getter
    @Setter
    public static class WechatPayProperties {

        /**
         * 应用ID
         */
        private String appId;

        /**
         * 商户ID
         */
        private String merchantId;

        /**
         * 私钥地址
         */
        private String privateKeyPath;

        /**
         * 商户SN
         */
        private String merchantSn;

        /**
         * 应用Key
         */
        private String v3Key;

        /**
         * 合作商Key
         */
        private String partnerKey;

        /**
         * 支付完成回调地址
         */
        private String notifyUrl;

    }


}
