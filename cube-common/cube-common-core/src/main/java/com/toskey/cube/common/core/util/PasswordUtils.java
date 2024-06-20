package com.toskey.cube.common.core.util;

import com.toskey.cube.common.core.constant.enums.PasswordStrength;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.regex.Pattern;

/**
 * PasswordUtils
 *
 * @author toskey
 * @version 1.0.0
 */
public class PasswordUtils {

    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public static String encode(String originPassword) {
        return passwordEncoder.encode(originPassword);
    }

    public static boolean matches(String raw, String encodedPassword) {
        return passwordEncoder.matches(raw, encodedPassword);
    }

    public static PasswordStrength strength(String password) {
        Pattern lowercasePattern = Pattern.compile("[a-z]+");
        Pattern uppecasePattern = Pattern.compile("[A-Z]+");
        Pattern numberPattern = Pattern.compile("[0-9]+");
        Pattern specialCharRegex = Pattern.compile("[!@#$%^&*()_;:|,.<>/?]+");
        int matchCount = 0;
        if (lowercasePattern.matcher(password).find()) {
            matchCount++;
        }
        if (uppecasePattern.matcher(password).find()) {
            matchCount++;
        }
        if (numberPattern.matcher(password).find()) {
            matchCount++;
        }
        if (specialCharRegex.matcher(password).find()) {
            matchCount++;
        }
        if (matchCount > 2) {
            return PasswordStrength.HIGH;
        } else if (matchCount == 2) {
            return PasswordStrength.MIDDLE;
        } else {
            return PasswordStrength.LOWER;
        }
    }
}
