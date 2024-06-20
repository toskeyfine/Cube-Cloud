package com.toskey.cube.common.license.support.creator;

import com.toskey.cube.common.license.support.CubeKeyStoreParam;
import com.toskey.cube.common.license.support.CubeLicenseManager;
import com.toskey.cube.common.license.support.LicenseExtraInfo;
import de.schlichtherle.license.*;

import javax.security.auth.x500.X500Principal;
import java.io.File;
import java.util.Date;
import java.util.prefs.Preferences;

/**
 * ZxLicenseCreator
 *
 * @author toskey
 * @version 1.0.0
 */
public class LicenseCreator {

    private final static X500Principal DEFAULT_HOLDER_AND_ISSUER = new X500Principal("CN=zxicet, OU=zxicet, O=localhost, L=JN, ST=SD, C=CN");

    /**
     * 应用名
     */
    private String subject;

    /**
     * 私钥别名
     */
    private String privateKeyAlias;

    /**
     * 私钥密码
     */
    private String keyPwd;

    /**
     * 仓储密码
     */
    private String storePwd;

    /**
     * 私钥文件存放路径
     */
    private String privateKeyPath;

    /**
     * 发布日期（授权生效日期）
     */
    private Date issuedTime = new Date();

    /**
     * 授权失效日期
     * yyyy-MM-dd
     * 约定为设置日期的23:59:59
     */
    private Date expiryTime;

    /**
     * 授权文件生成路径
     */
    private String licensePath;

    /**
     * 授权扩展信息对象
     */
    private LicenseExtraInfo extraInfo;

    /**
     * 不允许直接创建对象
     * 统一使用builder创建
     */
    private LicenseCreator() {
    }

    public String getSubject() {
        return subject;
    }

    public String getPrivateKeyAlias() {
        return privateKeyAlias;
    }

    public String getKeyPwd() {
        return keyPwd;
    }

    public String getStorePwd() {
        return storePwd;
    }

    public String getPrivateKeyPath() {
        return privateKeyPath;
    }

    public Date getIssuedTime() {
        return issuedTime;
    }

    public Date getExpiryTime() {
        return expiryTime;
    }

    public String getLicensePath() {
        return licensePath;
    }

    public LicenseExtraInfo getExtraInfo() {
        return extraInfo;
    }

    /**
     * 生成授权文件
     *
     * @return 成功/失败
     */
    public boolean generateLicense() {
        try {
            LicenseManager licenseManager = new CubeLicenseManager(initLicenseParam());
            LicenseContent content = initLicenseContent();
            licenseManager.store(content, new File(this.getLicensePath()));
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 初始化授权参数
     *
     * @return
     */
    private LicenseParam initLicenseParam() {
        Preferences preferences = Preferences.userNodeForPackage(LicenseCreator.class);

        CipherParam cipherParam = new DefaultCipherParam(this.getStorePwd());
        KeyStoreParam keyStoreParam = new CubeKeyStoreParam(
                LicenseCreator.class,
                this.getPrivateKeyPath(),
                this.getPrivateKeyAlias(),
                this.getStorePwd(),
                this.getKeyPwd()
        );

        return new DefaultLicenseParam(this.getSubject(), preferences, keyStoreParam, cipherParam);
    }

    /**
     * 初始化授权文件内容
     * 此内容相当于预设，最终授权文件内容是密文
     * @return
     */
    private LicenseContent initLicenseContent() {
        LicenseContent content = new LicenseContent();
        content.setHolder(DEFAULT_HOLDER_AND_ISSUER);
        content.setIssuer(DEFAULT_HOLDER_AND_ISSUER);

        content.setSubject(this.subject);
        content.setIssued(this.issuedTime);
        content.setNotBefore(this.issuedTime);
        if (this.expiryTime != null) {
            content.setNotAfter(this.expiryTime);
        }
        if (this.extraInfo != null) {
            content.setExtra(this.extraInfo);
        }

        return content;
    }

    public static class Builder {

        private LicenseCreator creator;

        public Builder() {
            creator = new LicenseCreator();
        }

        public Builder subject(String subject) {
            creator.subject = subject;
            return this;
        }

        public Builder privateKeyAlias(String privateKeyAlias) {
            creator.privateKeyAlias = privateKeyAlias;
            return this;
        }

        public Builder keyPwd(String keyPwd) {
            creator.keyPwd = keyPwd;
            return this;
        }

        public Builder storePwd(String storePwd) {
            creator.storePwd = storePwd;
            return this;
        }

        public Builder privateKeyPath(String privateKeyPath) {
            creator.privateKeyPath = privateKeyPath;
            return this;
        }

        public Builder issuedTime(Date issuedTime) {
            creator.issuedTime = issuedTime;
            return this;
        }

        public Builder expiryTime(Date expiryTime) {
            creator.expiryTime = expiryTime;
            return this;
        }

        public Builder extra(LicenseExtraInfo extraInfo) {
            creator.extraInfo = extraInfo;
            return this;
        }

        public Builder licensePath(String licensePath) {
            creator.licensePath = licensePath;
            return this;
        }

        public LicenseCreator build() {
            return creator;
        }
    }

}
