package com.toskey.cube.common.pay.entity;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 转账
 *
 * @author toskey
 * @version 1.0.0
 */
@Getter
@Setter
public class PayTransfer {

    private String outBatchNo;

    private String batchName;

    private String batchRemark;

    private String transferSceneId;

    private List<PayTransferDetails> details;

    public int getTotalNum() {
        if (CollectionUtils.isNotEmpty(details)) {
            return details.size();
        }
        return -1;
    }

    public long getTotalAmount() {
        if (CollectionUtils.isNotEmpty(details)) {
            return details.stream().map(PayTransferDetails::getAmount).reduce(0L, Long::sum);
        }
        return -1;
    }
}
