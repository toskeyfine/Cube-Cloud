package com.toskey.cube.common.license;

import com.toskey.cube.common.license.config.LicenseProperties;
import com.toskey.cube.common.license.support.verifier.LicenseVerifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

/**
 * ZxLicenseAutoConfiguration
 *
 * @author toskey
 * @version 1.0.0
 */
@EnableConfigurationProperties(LicenseProperties.class)
public class LicenseAutoConfiguration {

    @ConditionalOnMissingBean
    @Bean(initMethod = "install", destroyMethod = "uninstall")
    public LicenseVerifier licenseVerifier(LicenseProperties properties, ApplicationContext context) {
        return new LicenseVerifier.Builder(context)
                .subject(properties.getSubject())
                .publicAlias(properties.getPublicAlias())
                .storePwd(properties.getPublicPassword())
                .publicKeyPath(properties.getPublicKeyPath())
                .licensePath(properties.getLicensePath())
                .build();
    }

}
