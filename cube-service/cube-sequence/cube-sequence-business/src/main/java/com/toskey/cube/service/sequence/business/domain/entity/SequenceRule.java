package com.toskey.cube.service.sequence.business.domain.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.toskey.cube.common.core.annotation.EntityProperty;
import com.toskey.cube.common.core.base.BaseEntity;
import com.toskey.cube.service.sequence.business.constant.enums.SequenceCodeType;
import com.toskey.cube.service.sequence.business.vo.SequenceSectionQueryResultVO;
import lombok.Data;

import java.util.Objects;

/**
 * 序列规则
 *
 * @author toskey
 * @version 1.0.0
 */
@Data
@TableName("sequence_rule")
public class SequenceRule extends BaseEntity {

    /**
     * 编码规则类型
     */
    private SequenceCodeType type;

    /**
     * 内容
     */
    private String content;

    /**
     * 格式化
     */
    private String format;

    /**
     * 总长度
     */
    private Integer totalLength;

    /**
     * 排序序号
     */
    private Integer ordered;

    /**
     * 所属序列ID
     */
    private String sequenceId;

    /**
     * 号段规则
     */
    @TableField(exist = false)
    @EntityProperty(target = SequenceSectionQueryResultVO.class, strategy = EntityProperty.Strategy.RESULT)
    private SequenceSection sequenceSection;

    @Override
    public boolean equals(Object obj) {
        if (Objects.nonNull(obj) && obj instanceof SequenceRule sequenceRule) {
            if (sequenceRule.getType() != null && sequenceRule.getType() == this.getType()) {
                return Boolean.TRUE;
            }
        }
        return Boolean.FALSE;
    }
}
