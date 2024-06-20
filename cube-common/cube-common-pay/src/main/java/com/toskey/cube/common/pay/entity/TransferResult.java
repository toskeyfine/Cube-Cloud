package com.toskey.cube.common.pay.entity;

import com.wechat.pay.java.service.transferbatch.model.InitiateBatchTransferResponse;
import lombok.Getter;
import lombok.Setter;

/**
 * 转账结果
 *
 * @author toskey
 * @version 1.0.0
 */
@Getter
@Setter
public class TransferResult {

    private String outBatchNo;

    private String batchId;

    private String batchStatus;

    private String createTime;

    public static TransferResult of(InitiateBatchTransferResponse response) {
        TransferResult transferResult = new TransferResult();
        transferResult.setBatchId(response.getBatchId());
        transferResult.setOutBatchNo(response.getOutBatchNo());
        transferResult.setBatchStatus(response.getBatchStatus());
        transferResult.setCreateTime(response.getCreateTime());
        return transferResult;
    }
}
