package com.toskey.cube.common.pay.service.wechat;

import com.toskey.cube.common.pay.entity.PayTrade;
import com.toskey.cube.common.pay.entity.PayTransfer;
import com.toskey.cube.common.pay.entity.PrepayResult;
import com.toskey.cube.common.pay.entity.TransferResult;
import com.wechat.pay.java.service.payments.model.Transaction;
import java.util.Map;

/**
 * 微信支付处理接口
 *
 * @author toskey
 * @version 1.0.0
 */
public interface WechatPayService {

    /**
     * 微信支付
     *
     * @param payTrade 交易信息
     * @return String 支付二维码地址
     */
    PrepayResult<Map<String, String>> pay(PayTrade payTrade);

    /**
     * 发起商家转账
     *
     * @param transfer 转账参数
     * @return TransferResult 转账结果（非实时），建议存储转账结果中的batchId，用于查询转账结果
     */
    TransferResult transfer(PayTransfer transfer);

    /**
     * 订单查询
     *
     * @param transactionId 交易事务ID
     * @return Transaction 订单信息
     */
    Transaction queryOrder(String transactionId);

    /**
     * 关闭订单
     *
     * @param outTradeNo 外部交易号
     * @return 无返回值，若抛出异常则表示处理失败
     */
    void closeOrder(String outTradeNo);


}
