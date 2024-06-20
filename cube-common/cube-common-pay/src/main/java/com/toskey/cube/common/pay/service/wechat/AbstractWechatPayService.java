package com.toskey.cube.common.pay.service.wechat;

import com.toskey.cube.common.pay.config.PayProperties;
import com.toskey.cube.common.pay.entity.PayTransfer;
import com.toskey.cube.common.pay.entity.TransferResult;
import com.wechat.pay.java.core.Config;
import com.wechat.pay.java.service.transferbatch.TransferBatchService;
import com.wechat.pay.java.service.transferbatch.model.InitiateBatchTransferRequest;
import com.wechat.pay.java.service.transferbatch.model.InitiateBatchTransferResponse;
import com.wechat.pay.java.service.transferbatch.model.TransferDetailInput;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * ZxAbstractPayService
 *
 * @author toskey
 * @version 1.0.0
 */
@Slf4j
public abstract class AbstractWechatPayService implements WechatPayService {

    protected final PayProperties.WechatPayProperties properties;

    protected final Config config;

    protected AbstractWechatPayService(PayProperties properties, Config config) {
        this.properties = properties.getWechat();
        this.config = config;
    }

    @Override
    public TransferResult transfer(PayTransfer transfer) {
        TransferBatchService service = new TransferBatchService.Builder()
                .config(config)
                .build();

        InitiateBatchTransferRequest request = buildTransferBatchRequest(transfer);

        InitiateBatchTransferResponse response = service.initiateBatchTransfer(request);
        return TransferResult.of(response);
    }

    private InitiateBatchTransferRequest buildTransferBatchRequest(PayTransfer transfer) {
        InitiateBatchTransferRequest request = new InitiateBatchTransferRequest();
        request.setAppid(properties.getAppId());
        request.setBatchName(transfer.getBatchName());
        request.setBatchRemark(transfer.getBatchRemark());
        request.setTransferSceneId(transfer.getTransferSceneId());
        request.setOutBatchNo(transfer.getOutBatchNo());
        request.setTotalAmount(transfer.getTotalAmount());
        request.setTotalNum(transfer.getTotalNum());
        List<TransferDetailInput> details = transfer.getDetails().stream()
                .map(detail -> {
                    TransferDetailInput entity = new TransferDetailInput();
                    entity.setOutDetailNo(detail.getOutDetailNo());
                    entity.setTransferAmount(detail.getAmount());
                    entity.setTransferRemark(detail.getRemark());
                    entity.setOpenid(detail.getOpenid());
                    entity.setUserName(detail.getUsername());
                    return entity;
                })
                .toList();
        request.setTransferDetailList(details);
        return request;
    }

}
