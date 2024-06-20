package com.toskey.cube.common.sms;

import com.aliyun.auth.credentials.Credential;
import com.aliyun.auth.credentials.provider.StaticCredentialProvider;
import com.aliyun.sdk.service.dysmsapi20170525.AsyncClient;
import com.toskey.cube.common.sms.component.AliSmsSender;
import com.toskey.cube.common.sms.component.SmsSender;
import com.toskey.cube.common.sms.properties.SmsProperties;
import darabonba.core.client.ClientOverrideConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

/**
 * SmsAutoConfiguration
 *
 * @author toskey
 * @version 1.0.0
 */
@EnableConfigurationProperties(SmsProperties.class)
public class SmsAutoConfiguration {

    private final SmsProperties smsProperties;

    public SmsAutoConfiguration(SmsProperties smsProperties) {
        this.smsProperties = smsProperties;
    }

    @Bean
    public AsyncClient aliSmsClient() {
        SmsProperties.AliSmsProperties aliProperties = smsProperties.getAli();
        StaticCredentialProvider provider = StaticCredentialProvider.create(
                Credential.builder()
                        .accessKeyId(aliProperties.getAccessKey())
                        .accessKeySecret(aliProperties.getSecretKey())
                        .build()
        );
        return AsyncClient.builder()
                .region(aliProperties.getRegionId())
                .credentialsProvider(provider)
                .overrideConfiguration(
                        ClientOverrideConfiguration.create()
                                .setEndpointOverride("dysmsapi.aliyuncs.com")
                )
                .build();
    }

    @Bean("aliSmsSender")
    public SmsSender aliSmsSender(SmsProperties smsProperties) {
        return new AliSmsSender(smsProperties, aliSmsClient());
    }

}
