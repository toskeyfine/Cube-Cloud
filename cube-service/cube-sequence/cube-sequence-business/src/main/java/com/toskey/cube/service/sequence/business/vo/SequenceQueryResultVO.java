package com.toskey.cube.service.sequence.business.vo;

import com.toskey.cube.common.core.annotation.EntityMapper;
import com.toskey.cube.common.core.base.BaseEntityMapper;
import com.toskey.cube.service.sequence.business.constant.enums.RuleType;
import com.toskey.cube.service.sequence.business.domain.entity.Sequence;
import com.toskey.cube.service.sequence.business.domain.entity.SequenceRule;

import java.util.List;

/**
 * SequenceQueryResultVO
 *
 * @author toskey
 * @version 1.0.0
 */
@EntityMapper(entity = Sequence.class)
public class SequenceQueryResultVO extends BaseEntityMapper {

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
    private List<SequenceRule> ruleList;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public RuleType getRuleType() {
        return ruleType;
    }

    public void setRuleType(RuleType ruleType) {
        this.ruleType = ruleType;
    }

    public Integer getTotalLength() {
        return totalLength;
    }

    public void setTotalLength(Integer totalLength) {
        this.totalLength = totalLength;
    }

    public Integer getIncrementStepLength() {
        return incrementStepLength;
    }

    public void setIncrementStepLength(Integer incrementStepLength) {
        this.incrementStepLength = incrementStepLength;
    }

    public Integer getPadding() {
        return padding;
    }

    public void setPadding(Integer padding) {
        this.padding = padding;
    }

    public String getPaddingStr() {
        return paddingStr;
    }

    public void setPaddingStr(String paddingStr) {
        this.paddingStr = paddingStr;
    }

    public Integer getOrdered() {
        return ordered;
    }

    public void setOrdered(Integer ordered) {
        this.ordered = ordered;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public List<SequenceRule> getRuleList() {
        return ruleList;
    }

    public void setRuleList(List<SequenceRule> ruleList) {
        this.ruleList = ruleList;
    }
}
