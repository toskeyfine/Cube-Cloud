package com.toskey.cube.common.sms.model;

import com.toskey.cube.common.core.util.StringUtils;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * MessageResult
 *
 * @author toskey
 * @version 1.0.0
 */
@Getter
@Setter
@AllArgsConstructor(staticName = "of")
public class MessageResult {

    private String code;

    public boolean isSuccess() {
        return StringUtils.equals("OK", code);
    }
}
