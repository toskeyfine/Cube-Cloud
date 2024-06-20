package com.toskey.cube.common.pay.entity;

import lombok.Getter;
import lombok.Setter;

/**
 * 退款结果
 *
 * @author toskey
 * @version 1.0.0
 */
@Getter
@Setter
public class RefundResult {

    private int code;

    private String tradeNo;

    private String outTradeNo;

    private String refundFee;

    private String refundCurrency;

    public boolean isSuccess() {
        return code == 0;
    }

    public void success() {
        this.code = 0;
    }

    public void fail() {
        this.code = -1;
    }

}
