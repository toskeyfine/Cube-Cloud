package com.toskey.cube.service.sequence.business.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.toskey.cube.service.sequence.business.constant.enums.SequenceCodeType;
import com.toskey.cube.service.sequence.business.domain.entity.SequenceSection;
import com.toskey.cube.service.sequence.business.domain.entity.SequenceRule;
import com.toskey.cube.service.sequence.business.domain.mapper.SequenceRuleMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * SequenceRuleService
 *
 * @author toskey
 * @version 1.0.0
 */
@Service
@RequiredArgsConstructor
public class SequenceRuleService extends ServiceImpl<SequenceRuleMapper, SequenceRule> implements IService<SequenceRule> {

    private final SequenceSectionService sequenceSectionService;

    @Transactional(rollbackFor = Exception.class)
    public boolean saveBatchSequenceRule(List<SequenceRule> sequenceRuleList) {
        super.saveBatch(sequenceRuleList);

        Optional<SequenceRule> sectionRule = sequenceRuleList.stream()
                .filter(r -> r.getType() == SequenceCodeType.ID_SECTION)
                .findFirst();

        sectionRule.ifPresent(rule -> {
            SequenceSection sequenceSection = rule.getSequenceSection();
            sequenceSection.setRuleId(rule.getId());
            sequenceSectionService.save(sequenceSection);
        });

        return true;
    }
}
