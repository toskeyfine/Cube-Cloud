package com.toskey.cube.common.license.support;

import de.schlichtherle.license.AbstractKeyStoreParam;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * ZxLicenseParam
 *
 * @author toskey
 * @version 1.0.0
 */
public class CubeKeyStoreParam extends AbstractKeyStoreParam {

    private String resource;

    private String alias;

    private String storePwd;

    private String keyPwd;

    public CubeKeyStoreParam(Class clazz, String resource, String alias, String storePwd, String keyPwd) {
        super(clazz, resource);
        this.alias = alias;
        this.storePwd = storePwd;
        this.keyPwd = keyPwd;
    }

    @Override
    public String getAlias() {
        return this.alias;
    }

    @Override
    public String getStorePwd() {
        return this.storePwd;
    }

    @Override
    public String getKeyPwd() {
        return this.keyPwd;
    }

    @Override
    public InputStream getStream() throws IOException {
        return new FileInputStream(resource);
    }
}
