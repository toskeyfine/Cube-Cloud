package com.toskey.cube.common.license.annotation;

import com.toskey.cube.common.license.LicenseAutoConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * EnableLicense
 *
 * @author toskey
 * @version 1.0.0
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Import({LicenseAutoConfiguration.class})
public @interface EnableLicense {

}
