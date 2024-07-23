package com.toskey.cube.service.sequence.business.domain.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.toskey.cube.common.core.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 号段参数
 *
 * @author toskey
 * @version 1.0.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sequence_section")
public class SequenceSection extends BaseEntity {

    /**
     * 序列规则ID
     */
    private String ruleId;

    /**
     * 号段起始
     */
    private Long begin;

    /**
     * 号段终止
     */
    private Long finish;

    /**
     * 递增步长
     */
    private Integer step;

}
