package com.toskey.cube.common.pay.entity;

import lombok.Getter;
import lombok.Setter;

/**
 * 支付退款
 *
 * @author toskey
 * @version 1.0.0
 */
@Getter
@Setter
public class PayRefund {

    private String tradeNo;

    private String outTradeNo;

    private String refundAmount;

}
