package com.toskey.cube.service.sequence.business.vo;

import com.toskey.cube.common.core.base.BaseEntityMapper;
import com.toskey.cube.service.sequence.business.constant.enums.SequenceCodeType;

/**
 * SequenceRuleQueryResultVO
 *
 * @author toskey
 * @version 1.0.0
 */
public class SequenceRuleQueryResultVO extends BaseEntityMapper {

    private String id;

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
    private SequenceSectionQueryResultVO sequenceSection;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public SequenceCodeType getType() {
        return type;
    }

    public void setType(SequenceCodeType type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public Integer getTotalLength() {
        return totalLength;
    }

    public void setTotalLength(Integer totalLength) {
        this.totalLength = totalLength;
    }

    public Integer getOrdered() {
        return ordered;
    }

    public void setOrdered(Integer ordered) {
        this.ordered = ordered;
    }

    public String getSequenceId() {
        return sequenceId;
    }

    public void setSequenceId(String sequenceId) {
        this.sequenceId = sequenceId;
    }

    public SequenceSectionQueryResultVO getSequenceSection() {
        return sequenceSection;
    }

    public void setSequenceSection(SequenceSectionQueryResultVO sequenceSection) {
        this.sequenceSection = sequenceSection;
    }
}
