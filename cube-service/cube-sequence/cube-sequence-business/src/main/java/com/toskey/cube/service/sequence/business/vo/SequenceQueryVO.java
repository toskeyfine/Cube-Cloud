package com.toskey.cube.service.sequence.business.vo;

import com.toskey.cube.common.core.annotation.EntityMapper;
import com.toskey.cube.common.core.base.BaseEntityMapper;
import com.toskey.cube.service.sequence.business.domain.entity.Sequence;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * SequenceQueryVo
 *
 * @author toskey
 * @version 1.0.0
 */
@Getter
@Setter
@EntityMapper(entity = Sequence.class)
public class SequenceQueryVO extends BaseEntityMapper {

    /**
     * 名称
     */
    private String name;

    /**
     * 编码
     */
    private String code;

    /**
     * 编码类型
     */
    private Integer ruleType;

}
