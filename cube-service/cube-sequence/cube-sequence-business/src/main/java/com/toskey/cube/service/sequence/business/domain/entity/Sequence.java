package com.toskey.cube.service.sequence.business.domain.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.toskey.cube.common.core.annotation.EntityProperty;
import com.toskey.cube.common.tenant.entity.TenantEntity;
import com.toskey.cube.service.sequence.business.constant.enums.RuleType;
import com.toskey.cube.service.sequence.business.vo.SequenceRuleQueryResultVO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * 序列
 *
 * @author toskey
 * @version 1.0.0
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("sequence_info")
public class Sequence extends TenantEntity {

    /**
     * 名称
     */
    private String name;

    /**
     * 编码
     */
    private String code;

    /**
     * 类型
     */
    private RuleType ruleType;

    /**
     * 总长度
     */
    private Integer totalLength;

    /**
     * 自增步长
     */
    private Integer incrementStepLength;

    /**
     * 是否填充
     */
    private Integer padding;

    /**
     * 填充字符
     */
    private String paddingStr;

    /**
     * 排序序号
     */
    private Integer ordered;

    /**
     * 状态
     */
    private Integer status;

    /**
     * 编码规则
     */
    @TableField(exist = false)
    @EntityProperty(target = SequenceRuleQueryResultVO.class, strategy = EntityProperty.Strategy.RESULT)
    private List<SequenceRule> ruleList;

}
