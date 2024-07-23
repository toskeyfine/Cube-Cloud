package com.toskey.cube.service.sequence.business.domain.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.toskey.cube.service.sequence.business.domain.entity.SequenceSection;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * IdSectionParamMapper
 *
 * @author toskey
 * @version 1.0.0
 */
@Mapper
public interface SequenceSectionMapper extends BaseMapper<SequenceSection> {

    SequenceSection selectByRuleId(@Param("ruleId") String ruleId);

}
