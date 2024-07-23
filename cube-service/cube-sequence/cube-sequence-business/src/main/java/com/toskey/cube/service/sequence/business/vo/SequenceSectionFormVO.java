package com.toskey.cube.service.sequence.business.vo;

import com.toskey.cube.common.core.annotation.EntityMapper;
import com.toskey.cube.common.core.base.BaseEntityMapper;
import com.toskey.cube.service.sequence.business.domain.entity.SequenceSection;

/**
 * SequenceSectionFormVO
 *
 * @author toskey
 * @version 1.0.0
 */
@EntityMapper(entity = SequenceSection.class)
public class SequenceSectionFormVO extends BaseEntityMapper {

    private String id;

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


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRuleId() {
        return ruleId;
    }

    public void setRuleId(String ruleId) {
        this.ruleId = ruleId;
    }

    public Long getBegin() {
        return begin;
    }

    public void setBegin(Long begin) {
        this.begin = begin;
    }

    public Long getFinish() {
        return finish;
    }

    public void setFinish(Long finish) {
        this.finish = finish;
    }

    public Integer getStep() {
        return step;
    }

    public void setStep(Integer step) {
        this.step = step;
    }
}
