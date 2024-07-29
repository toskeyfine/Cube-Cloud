package com.toskey.cube.service.sequence.business.domain.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.toskey.cube.service.sequence.business.domain.entity.Sequence;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * SequenceMapper
 *
 * @author toskey
 * @version 1.0.0
 */
@Mapper
public interface SequenceMapper extends BaseMapper<Sequence> {

    Sequence selectSequenceById(@Param("id") String id);

    int insertDbSequence(@Param("key") String key, @Param("begin") long begin, @Param("incrementStepLength") int incrementStepLength, @Param("min") Long min, @Param("max") Long max, @Param("cycle") boolean cycle);

    int restartSequence(@Param("key") String key, @Param("value") long value);

    int dropDbSequence(@Param("key") String key);

}
