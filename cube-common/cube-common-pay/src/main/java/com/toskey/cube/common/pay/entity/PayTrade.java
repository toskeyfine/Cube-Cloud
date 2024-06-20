package com.toskey.cube.common.pay.entity;

import lombok.Getter;
import lombok.Setter;

/**
 * 交易信息
 *
 * @author toskey
 * @version 1.0.0
 */
@Getter
@Setter
public class PayTrade {

    /**
     * 订单号
     */
    private Long orderNo;

    /**
     * 交易金额
     * <p>
     *     单位为分，必须为大于0的正整数
     * </p>
     */
    private String amount;

    /**
     * 货币代码
     */
    private String currency = "CNY";

    /**
     * 订单标题
     */
    private String subject;

    /**
     * 本次交易描述信息
     * 会在同步、异步回调中原样返回
     */
    private String body;

    /**
     * 本地交易流水号
     * 确保本地系统唯一且长度小于等于64
     */
    private String outTradeNo;

    /**
     * 订单失效时间
     * <p>
     *     格式为RFC3339格式
     * </p>
     * <p>
     *     yyyy-MM-dd'T'HH:mm:ss.SSSXXX
     * </p>
     * <p>
     *     例：2024-04-18T15:15:30.00Z
     * </p>
     *
     */
    private String timeExpires;

}
