package com.toskey.cube.common.sms.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * CubeSmsProperties
 *
 * @author toskey
 * @version 1.0.0
 */
@Getter
@Setter
@ConfigurationProperties("cube.message.sms")
public class SmsProperties {

    private AliSmsProperties ali;

    @Getter
    @Setter
    public static class AliSmsProperties {

        private String regionId;

        private String accessKey;

        private String secretKey;

        private String signName;

        private String regTemplateCode;

        private String loginTemplateCode;

    }

}
