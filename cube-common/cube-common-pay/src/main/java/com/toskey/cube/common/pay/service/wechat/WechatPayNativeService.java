package com.toskey.cube.common.pay.service.wechat;

import com.toskey.cube.common.pay.config.PayProperties;
import com.toskey.cube.common.pay.entity.PayTrade;
import com.toskey.cube.common.pay.entity.PrepayResult;
import com.wechat.pay.java.core.Config;
import com.wechat.pay.java.service.payments.model.Transaction;
import com.wechat.pay.java.service.payments.nativepay.NativePayService;
import com.wechat.pay.java.service.payments.nativepay.model.*;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

/**
 * 微信支付 - Native API
 * <p>
 *     流程：根据pay方法返回值构建支付二维码，用于前端展示。用户自行扫码支付
 * </p>
 *
 * @author toskey
 * @since 1.0.0
 */
@Slf4j
public class WechatPayNativeService extends AbstractWechatPayService {

    public WechatPayNativeService(PayProperties properties, Config config) {
        super(properties, config);
    }

    /**
     * 微信支付
     *
     * @description 微信支付逻辑实现
     * @param payTrade 交易信息
     * @return String 支付二维码地址
     */
    @Override
    public PrepayResult<Map<String, String>> pay(PayTrade payTrade) {
        NativePayService service = new NativePayService.Builder()
                .config(config)
                .build();

        PrepayRequest request = buildNativeRequest(payTrade);
        PrepayResponse response = service.prepay(request);
        Map<String, String> resp = new HashMap<>();
        resp.put("codeUrl", response.getCodeUrl());
        return PrepayResult.success(resp);
    }

    @Override
    public Transaction queryOrder(String transactionId) {
        NativePayService service = new NativePayService.Builder()
                .config(config)
                .build();

        QueryOrderByIdRequest request = new QueryOrderByIdRequest();
        request.setMchid(properties.getMerchantId());
        request.setTransactionId(transactionId);

        return service.queryOrderById(request);
    }

    @Override
    public void closeOrder(String outTradeNo) {
        NativePayService service = new NativePayService.Builder()
                .config(config)
                .build();

        CloseOrderRequest request = new CloseOrderRequest();
        request.setMchid(properties.getMerchantId());
        request.setOutTradeNo(outTradeNo);

        service.closeOrder(request);
    }

    private PrepayRequest buildNativeRequest(PayTrade payTrade) {
        PrepayRequest request = new PrepayRequest();
        Amount amount = new Amount();
        amount.setTotal(Integer.getInteger(payTrade.getAmount()));
        amount.setCurrency(payTrade.getCurrency());
        request.setAmount(amount);
        request.setAppid(properties.getAppId());
        request.setMchid(properties.getMerchantId());
        request.setDescription(payTrade.getSubject());
        request.setAttach(payTrade.getBody());
        request.setOutTradeNo(payTrade.getOutTradeNo());
        request.setNotifyUrl(properties.getNotifyUrl());
        return request;
    }

}
