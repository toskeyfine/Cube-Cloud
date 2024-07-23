package com.toskey.cube.service.sequence.business.vo;

import com.toskey.cube.common.core.annotation.EntityMapper;
import com.toskey.cube.common.core.annotation.MapperProperty;
import com.toskey.cube.common.core.base.BaseEntityMapper;
import com.toskey.cube.service.sequence.business.constant.enums.RuleType;
import com.toskey.cube.service.sequence.business.domain.entity.Sequence;
import com.toskey.cube.service.sequence.business.domain.entity.SequenceRule;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * SequenceFormVO
 *
 * @author toskey
 * @version 1.0.0
 */
@Getter
@Setter
@EntityMapper(entity = Sequence.class)
public class SequenceFormVO extends BaseEntityMapper {

    private String id;

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
    @MapperProperty(target = SequenceRule.class)
    private List<SequenceRuleFormVO> ruleList;

}
