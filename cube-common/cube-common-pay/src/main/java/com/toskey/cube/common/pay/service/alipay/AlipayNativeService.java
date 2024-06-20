package com.toskey.cube.common.pay.service.alipay;

import com.alipay.api.AlipayClient;
import com.alipay.api.domain.AlipayTradePrecreateModel;
import com.alipay.api.request.AlipayTradePrecreateRequest;
import com.alipay.api.response.AlipayTradePrecreateResponse;
import com.toskey.cube.common.pay.config.PayProperties;
import com.toskey.cube.common.pay.entity.PayTrade;
import com.toskey.cube.common.pay.entity.PrepayResult;
import com.toskey.cube.common.pay.util.PayUtils;
import lombok.SneakyThrows;

import java.util.HashMap;
import java.util.Map;

/**
 * ZxAlipayNativeService
 *
 * @author toskey
 * @version 1.0.0
 */
public class AlipayNativeService extends AbstractAlipayService {

    public AlipayNativeService(PayProperties payProperties,
                               AlipayClient alipayClient) {
        super(payProperties, alipayClient);
    }

    @SneakyThrows
    @Override
    public PrepayResult<Map<String, String>> pay(PayTrade payTrade) {
        AlipayTradePrecreateRequest request = new AlipayTradePrecreateRequest();
        request.setNotifyUrl(properties.getNotifyUrl());

        AlipayTradePrecreateModel model = new AlipayTradePrecreateModel();
        model.setOutTradeNo(payTrade.getOutTradeNo());
        // 分转元，保留两位
        model.setTotalAmount(PayUtils.toYuan(payTrade.getAmount(), 2));
        model.setSubject(payTrade.getSubject());
        model.setBody(payTrade.getBody());

        request.setBizModel(model);

        AlipayTradePrecreateResponse response = alipayClient.execute(request);
        if (response.isSuccess()) {
            String url = response.getQrCode();
            Map<String, String> result = new HashMap<>();
            result.put("codeUrl", url);
            return PrepayResult.success(result);
        }
        return PrepayResult.error();
    }
}
