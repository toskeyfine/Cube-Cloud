package com.toskey.cube.service.sequence.business.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.toskey.cube.service.sequence.business.domain.entity.SequenceSection;
import com.toskey.cube.service.sequence.business.domain.mapper.SequenceSectionMapper;
import org.springframework.stereotype.Service;

/**
 * SectionParamService
 *
 * @author toskey
 * @version 1.0.0
 */
@Service
public class SequenceSectionService extends ServiceImpl<SequenceSectionMapper, SequenceSection> implements IService<SequenceSection> {

}
