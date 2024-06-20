package com.toskey.cube.common.pay;

import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.toskey.cube.common.core.util.StringUtils;
import com.toskey.cube.common.pay.config.PayProperties;
import com.toskey.cube.common.pay.constant.PayConstants;
import com.toskey.cube.common.pay.service.alipay.AlipayNativeService;
import com.toskey.cube.common.pay.service.alipay.AlipayNotifyCallback;
import com.toskey.cube.common.pay.service.alipay.AlipayService;
import com.toskey.cube.common.pay.service.alipay.DefaultAlipayNotifyCallback;
import com.toskey.cube.common.pay.service.wechat.*;
import com.wechat.pay.java.core.Config;
import com.wechat.pay.java.core.RSAAutoCertificateConfig;
import lombok.SneakyThrows;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.util.ResourceUtils;

/**
 * PayAutoConfiguration
 *
 * @author toskey
 * @version 1.0.0
 */
@AutoConfiguration
@EnableConfigurationProperties(PayProperties.class)
public class PayAutoConfiguration {

    private final PayProperties payProperties;

    public PayAutoConfiguration(PayProperties payProperties) {
        this.payProperties = payProperties;
    }

    @SneakyThrows
    @Bean
    public Config config() {
        PayProperties.WechatPayProperties wechatPayProperties = payProperties.getWechat();
        String privateKeyPath = ResourceUtils.getURL(wechatPayProperties.getPrivateKeyPath()).getPath();
        return new RSAAutoCertificateConfig.Builder()
                .merchantId(wechatPayProperties.getMerchantId())
                .privateKeyFromPath(privateKeyPath)
                .merchantSerialNumber(wechatPayProperties.getMerchantSn())
                .apiV3Key(wechatPayProperties.getV3Key())
                .build();
    }

    @Bean
    public AlipayClient alipayClient() {
        PayProperties.AliPayProperties aliPayProperties = payProperties.getAlipay();

        String gateway = PayConstants.ALIPAY_GATEWAY_DEV;
        if (StringUtils.equals("prod", aliPayProperties.getEnv())) {
            gateway = PayConstants.ALIPAY_GATEWAY_PROD;
        }
        return new DefaultAlipayClient(gateway,
                aliPayProperties.getAppId(),
                aliPayProperties.getPrivateKey(),
                "json",
                "GBK",
                aliPayProperties.getPublicKey(),
                "RSA2");
    }

    @Bean
    public AlipayService alipayNativeService() {
        return new AlipayNativeService(payProperties, alipayClient());
    }

    @Bean("wechatNativePayService")
    public WechatPayService wechatNativePayService() {
        return new WechatPayNativeService(payProperties, config());
    }

    @Bean("wechatJsPayService")
    public WechatPayService wechatJsPayService() {
        return new WechatPayJsService(payProperties, config());
    }

    @Bean
    @ConditionalOnMissingBean(WechatPayNotifyCallback.class)
    public WechatPayNotifyCallback wxPayNotifyCallback() {
        return new DefaultWechatPayNotifyCallback(config());
    }

    @Bean
    @ConditionalOnMissingBean(AlipayNotifyCallback.class)
    public AlipayNotifyCallback alipayNotifyCallback() {
        return new DefaultAlipayNotifyCallback(payProperties);
    }

}