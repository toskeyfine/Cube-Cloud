package com.toskey.cube.common.license.support.verifier;

import com.toskey.cube.common.license.support.CubeKeyStoreParam;
import com.toskey.cube.common.license.support.CubeLicenseManager;
import de.schlichtherle.license.*;
import org.springframework.context.ApplicationContext;

import java.io.File;
import java.util.prefs.Preferences;

/**
 * ZxLicenseVerifier
 *
 * @author toskey
 * @version 1.0.0
 */
public class LicenseVerifier {

    private final ApplicationContext applicationContext;

    public LicenseVerifier(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    private String subject;

    private String publicAlias;

    private String storePwd;

    private String licensePath;

    private String publicKeyPath;

    private LicenseManager licenseManager;

    public void install() {
        try {
            Preferences preferences = Preferences.userNodeForPackage(LicenseVerifier.class);
            CipherParam cipherParam = new DefaultCipherParam(this.storePwd);
            KeyStoreParam keyStoreParam = new CubeKeyStoreParam(LicenseVerifier.class, publicKeyPath, publicAlias, storePwd, null);
            LicenseParam licenseParam = new DefaultLicenseParam(subject, preferences, keyStoreParam, cipherParam);

            licenseManager = new CubeLicenseManager(licenseParam);
            licenseManager.uninstall();

            licenseManager.install(new File(licensePath));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void uninstall() {
        try {
            licenseManager.uninstall();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public boolean verify() {
        try {
            if (licenseManager.verify() != null) {
                return Boolean.TRUE;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Boolean.FALSE;
    }

    public static class Builder {
        private LicenseVerifier verifier;

        public Builder(ApplicationContext applicationContext) {
            verifier = new LicenseVerifier(applicationContext);
        }

        public Builder subject(String subject) {
            verifier.subject = subject;
            return this;
        }

        public Builder publicAlias(String publicAlias) {
            verifier.publicAlias = publicAlias;
            return this;
        }

        public Builder storePwd(String storePwd) {
            verifier.storePwd = storePwd;
            return this;
        }

        public Builder publicKeyPath(String publicKeyPath) {
            if (publicKeyPath != null && !publicKeyPath.equals("")) {
                if (publicKeyPath.startsWith("classpath")) {
                    verifier.publicKeyPath = getClass().getClassLoader().getResource(publicKeyPath.substring(10)).getPath();
                } else {
                    verifier.publicKeyPath = publicKeyPath;
                }
            }
            return this;
        }

        public Builder licensePath(String licensePath) {
            if (licensePath != null && !licensePath.equals("")) {
                if (licensePath.startsWith("classpath")) {
                    verifier.licensePath = getClass().getClassLoader().getResource(licensePath.substring(10)).getPath();
                } else {
                    verifier.licensePath = licensePath;
                }
            }
            return this;
        }

        public LicenseVerifier build() {
            return verifier;
        }

    }
}
