package com.toskey.cube.common.pay.service.alipay;

import com.alipay.api.AlipayClient;
import com.alipay.api.domain.AlipayTradeRefundModel;
import com.alipay.api.request.AlipayTradeRefundRequest;
import com.alipay.api.response.AlipayTradeRefundResponse;
import com.toskey.cube.common.pay.config.PayProperties;
import com.toskey.cube.common.pay.entity.PayRefund;
import com.toskey.cube.common.pay.entity.RefundResult;
import com.toskey.cube.common.pay.util.PayUtils;
import lombok.SneakyThrows;

/**
 * ZxAlipayAbstractService
 *
 * @author toskey
 * @version 1.0.0
 */
public abstract class AbstractAlipayService implements AlipayService {

    protected final PayProperties.AliPayProperties properties;

    protected final AlipayClient alipayClient;

    protected AbstractAlipayService(PayProperties payProperties,
                                    AlipayClient alipayClient) {
        this.properties = payProperties.getAlipay();
        this.alipayClient = alipayClient;
    }

    /**
     * 支付宝退款
     *
     * @param refund 退款对象模型
     * @return RefundResult 瑞款结果
     */
    @SneakyThrows
    public RefundResult RefundResult(PayRefund refund) {
        AlipayTradeRefundRequest request = new AlipayTradeRefundRequest();
        request.setNotifyUrl(properties.getNotifyUrl());

        AlipayTradeRefundModel model = new AlipayTradeRefundModel();
        model.setTradeNo(refund.getTradeNo());
        model.setOutTradeNo(refund.getOutTradeNo());
        model.setRefundAmount(PayUtils.toYuan(refund.getRefundAmount(), 2));
        request.setBizModel(model);

        AlipayTradeRefundResponse response = alipayClient.execute(request);
        RefundResult result = new RefundResult();
        if (response.isSuccess()) {
            result.success();
            result.setTradeNo(response.getTradeNo());
            result.setOutTradeNo(result.getOutTradeNo());
            result.setRefundFee(result.getRefundFee());
            result.setRefundCurrency(result.getRefundCurrency());
            return result;
        }
        result.fail();
        return result;
    }


}
