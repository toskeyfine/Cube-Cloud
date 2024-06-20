package com.toskey.cube.common.pay.entity;

import lombok.Getter;
import lombok.Setter;

/**
 * 转账详情
 *
 * @author toskey
 * @version 1.0.0
 */
@Getter
@Setter
public class PayTransferDetails {

    private String outDetailNo;

    private long amount;

    private String remark;

    private String openid;

    private String username;
}
