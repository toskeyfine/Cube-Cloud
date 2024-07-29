package com.toskey.cube.common.license.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * LicenseProperties
 *
 * @author toskey
 * @version 1.0.0
 */
@ConfigurationProperties(prefix = "cube.license")
public class LicenseProperties {

    private String subject;

    private String publicKeyPath;

    private String publicAlias;

    private String publicPassword;

    private String licensePath;

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getPublicKeyPath() {
        return publicKeyPath;
    }

    public void setPublicKeyPath(String publicKeyPath) {
        this.publicKeyPath = publicKeyPath;
    }

    public String getPublicAlias() {
        return publicAlias;
    }

    public void setPublicAlias(String publicAlias) {
        this.publicAlias = publicAlias;
    }

    public String getPublicPassword() {
        return publicPassword;
    }

    public void setPublicPassword(String publicPassword) {
        this.publicPassword = publicPassword;
    }

    public String getLicensePath() {
        return licensePath;
    }

    public void setLicensePath(String licensePath) {
        this.licensePath = licensePath;
    }
}
