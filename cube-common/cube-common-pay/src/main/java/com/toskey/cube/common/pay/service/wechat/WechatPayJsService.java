package com.toskey.cube.common.pay.service.wechat;

import com.toskey.cube.common.pay.config.PayProperties;
import com.toskey.cube.common.pay.entity.PayTrade;
import com.toskey.cube.common.pay.entity.PrepayResult;
import com.wechat.pay.java.core.Config;
import com.wechat.pay.java.service.payments.jsapi.JsapiServiceExtension;
import com.wechat.pay.java.service.payments.jsapi.model.*;
import com.wechat.pay.java.service.payments.model.Transaction;

import java.util.HashMap;
import java.util.Map;

/**
 * 微信支付 - JSAPI
 * <p>
 *     流程：根据pay方法的返回值发起支付API请求
 * </p>
 *
 * @author toskey
 * @since 1.0.0
 */
public class WechatPayJsService extends AbstractWechatPayService {

    public WechatPayJsService(PayProperties properties, Config config) {
        super(properties, config);
    }

    @Override
    public PrepayResult<Map<String, String>> pay(PayTrade payTrade) {
        JsapiServiceExtension serviceExtension = new JsapiServiceExtension.Builder()
                .config(config)
                .build();
        PrepayRequest request = buildJsRequest(payTrade);
        PrepayWithRequestPaymentResponse response = serviceExtension.prepayWithRequestPayment(request);
        Map<String, String> resp = new HashMap<>();
        resp.put("timeStamp", response.getTimeStamp());
        resp.put("nonceStr", response.getNonceStr());
        resp.put("package", response.getPackageVal());
        resp.put("paySign", response.getPaySign());

        return PrepayResult.success(resp);
    }

    @Override
    public Transaction queryOrder(String transactionId) {
        JsapiServiceExtension serviceExtension = new JsapiServiceExtension.Builder()
                .config(config)
                .build();
        QueryOrderByIdRequest request = new QueryOrderByIdRequest();
        request.setMchid(properties.getMerchantId());
        request.setTransactionId(transactionId);

        return serviceExtension.queryOrderById(request);
    }

    @Override
    public void closeOrder(String outTradeNo) {
        JsapiServiceExtension serviceExtension = new JsapiServiceExtension.Builder()
                .config(config)
                .build();

        CloseOrderRequest request = new CloseOrderRequest();
        request.setMchid(properties.getMerchantId());
        request.setOutTradeNo(outTradeNo);

        serviceExtension.closeOrder(request);
    }

    private PrepayRequest buildJsRequest(PayTrade payTrade) {
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
