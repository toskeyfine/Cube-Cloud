package com.toskey.cube.service.config.business.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.toskey.cube.common.core.base.PageData;
import com.toskey.cube.common.core.exception.BusinessException;
import com.toskey.cube.common.core.util.EntityUtils;
import com.toskey.cube.common.core.util.PageUtils;
import com.toskey.cube.common.core.util.StringUtils;
import com.toskey.cube.service.config.business.domain.entity.SysConfigGroup;
import com.toskey.cube.service.config.business.vo.group.ConfigGroupQueryResultVO;
import com.toskey.cube.service.config.business.domain.mapper.SysConfigGroupMapper;
import com.toskey.cube.service.config.business.vo.group.ConfigGroupFormVO;
import com.toskey.cube.service.config.business.vo.group.ConfigGroupQueryVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

/**
 * SysConfigGroupService
 *
 * @author toskey
 * @version 1.0.0
 */
@Service
public class SysConfigGroupService extends ServiceImpl<SysConfigGroupMapper, SysConfigGroup> implements IService<SysConfigGroup> {

    public PageData<ConfigGroupQueryResultVO> findPage(ConfigGroupQueryVO query) {
        IPage<SysConfigGroup> page = PageUtils.buildPage();
        List<SysConfigGroup> list = baseMapper.selectList(
                page,
                Wrappers.<SysConfigGroup>lambdaQuery()
                        .like(StringUtils.isNotBlank(query.getName()), SysConfigGroup::getName, query.getName())
                        .like(StringUtils.isNotBlank(query.getCode()), SysConfigGroup::getCode, query.getCode())
                        .eq(StringUtils.isNotBlank(query.getStatus()), SysConfigGroup::getStatus, query.getStatus())
        );
        return PageUtils.buildPageData(page, EntityUtils.toMapper(list, ConfigGroupQueryResultVO.class));
    }

    public ConfigGroupQueryResultVO findById(String id) {
        SysConfigGroup group = getById(id);
        return group.toMapper(ConfigGroupQueryResultVO.class);
    }

    public boolean saveConfigGroup(SysConfigGroup group) {
        if (StringUtils.isNotBlank(group.getId())) {
            group.setId(null);
        }
        if (checkCodeExists(null, group.getCode())) {
            throw new BusinessException("");
        }
        return save(group);
    }
    /**
     * 新增
     *
     * @param form 客户端信息
     * @return
     */
    public boolean saveConfigGroup(ConfigGroupFormVO form) {
        SysConfigGroup group = form.toEntity();
        return saveConfigGroup(group);
    }

    public boolean updateConfigGroup(SysConfigGroup group) {
        if (StringUtils.isBlank(group.getId())) {
            throw new BusinessException("");
        }
        if (checkCodeExists(group.getId(), group.getCode())) {
            throw new BusinessException("");
        }

        return updateById(group);
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean updateConfigGroup(ConfigGroupFormVO form) {
        SysConfigGroup group = form.toEntity();
        return updateConfigGroup(group);
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean removeConfigGroup(String[] ids) {
        return removeBatchByIds(Arrays.asList(ids));
    }

    public boolean checkCodeExists(String id, String code) {
        return exists(
                Wrappers.<SysConfigGroup>lambdaQuery()
                        .eq(SysConfigGroup::getCode, code)
                        .eq(StringUtils.isNotBlank(id), SysConfigGroup::getId, id)
        );
    }


}
