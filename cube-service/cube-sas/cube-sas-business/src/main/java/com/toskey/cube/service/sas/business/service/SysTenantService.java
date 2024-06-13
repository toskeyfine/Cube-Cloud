package com.toskey.cube.service.sas.business.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.toskey.cube.common.core.base.PageData;
import com.toskey.cube.common.core.exception.BusinessException;
import com.toskey.cube.common.core.util.EntityUtils;
import com.toskey.cube.common.core.util.PageUtils;
import com.toskey.cube.common.core.util.StringUtils;
import com.toskey.cube.service.sas.business.domain.entity.SysTenant;
import com.toskey.cube.service.sas.business.domain.mapper.SysTenantMapper;
import com.toskey.cube.service.sas.business.vo.tenant.TenantFormVO;
import com.toskey.cube.service.sas.business.vo.tenant.TenantQueryResultVO;
import com.toskey.cube.service.sas.business.vo.tenant.TenantQueryVO;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

/**
 * SysTenantService
 *
 * @author lis
 * @version 1.0
 * @description TODO
 * @date 2024/6/7 15:52
 */
@Slf4j
@Service
public class SysTenantService extends ServiceImpl<SysTenantMapper, SysTenant> implements IService<SysTenant> {

    public PageData<TenantQueryResultVO> findPage(TenantQueryVO query) {
        IPage<SysTenant> page = PageUtils.buildPage();
        List<SysTenant> postList = baseMapper.selectList(
                page,
                Wrappers.<SysTenant>lambdaQuery()
                        .like(StringUtils.isNotBlank(query.getName()), SysTenant::getName, query.getName())
                        .like(StringUtils.isNotBlank(query.getCode()), SysTenant::getCode, query.getCode())
                        .eq(StringUtils.isNotBlank(query.getType()), SysTenant::getType, query.getType())
                        .eq(StringUtils.isNotBlank(query.getStatus()), SysTenant::getStatus, query.getStatus())
        );
        return PageUtils.buildPageData(page, EntityUtils.toMapper(postList, TenantQueryResultVO.class));
    }

    public TenantQueryResultVO findById(String id) {
        SysTenant tenant = getById(id);
        return tenant.toMapper(TenantQueryResultVO.class);
    }

    public boolean saveTenant(SysTenant tenant) {
        if (StringUtils.isNotBlank(tenant.getId())) {
            tenant.setId(null);
        }
        if (checkNameExists(null, tenant.getName())) {
            throw new BusinessException("");
        }
        if (checkCodeExists(null, tenant.getCode())) {
            throw new BusinessException("");
        }
        return save(tenant);
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean saveTenant(TenantFormVO form) {
        SysTenant tenant = form.toEntity();
        return save(tenant);
    }

    /**
     * 修改岗位信息
     * <p>
     *     修改逻辑处理
     * </p>
     *
     * @param tenant 待修改的租户
     * @return
     */
    public boolean updateTenant(SysTenant tenant) {
        if (StringUtils.isBlank(tenant.getId())) {
            throw new BusinessException("");
        }
        if (checkNameExists(tenant.getId(), tenant.getName())) {
            throw new BusinessException("");
        }
        if (checkCodeExists(tenant.getId(), tenant.getCode())) {
            throw new BusinessException("");
        }
        return updateById(tenant);
    }

    /**
     * 修改岗位信息
     * <p>vo -> entity转换</p>
     *
     * @param form
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public boolean updateTenant(TenantFormVO form) {
        SysTenant tenant = form.toEntity();
        return updateTenant(tenant);
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean removeTenant(String[] ids) {
        // TODO 删除租户下所有业务数据（逻辑删除，便于恢复）
        return removeBatchByIds(Arrays.asList(ids));
    }

    public boolean checkNameExists(@Nullable String id, @Nonnull String name) {
        return exists(
                Wrappers.<SysTenant>lambdaQuery()
                        .eq(SysTenant::getName, name)
                        .eq(StringUtils.isNotBlank(id), SysTenant::getId, id)
        );
    }

    public boolean checkCodeExists(@Nullable String id, @Nonnull String code) {
        return exists(
                Wrappers.<SysTenant>lambdaQuery()
                        .eq(SysTenant::getCode, code)
                        .eq(StringUtils.isNotBlank(id), SysTenant::getId, id)
        );
    }

}
