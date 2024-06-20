package com.toskey.cube.common.license.support;

import de.schlichtherle.license.LicenseContentException;

/**
 * LicenseException
 *
 * @author toskey
 * @version 1.0.0
 */
public class LicenseException extends LicenseContentException {

    public LicenseException(String msg) {
        super(msg);
    }

    @Override
    public String getLocalizedMessage() {
        return getMessage();
    }
}
