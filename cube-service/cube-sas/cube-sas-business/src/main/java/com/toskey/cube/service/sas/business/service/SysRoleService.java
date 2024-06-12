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
import com.toskey.cube.common.datascope.enums.DataScopeType;
import com.toskey.cube.service.sas.business.domain.entity.SysMenu;
import com.toskey.cube.service.sas.business.domain.entity.SysRole;
import com.toskey.cube.service.sas.business.domain.mapper.SysRoleMapper;
import com.toskey.cube.service.sas.business.vo.role.PermissionBindVO;
import com.toskey.cube.service.sas.business.vo.role.RoleFormVO;
import com.toskey.cube.service.sas.business.vo.role.RoleQueryResultVO;
import com.toskey.cube.service.sas.business.vo.role.RoleQueryVO;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.management.relation.Role;
import java.util.Arrays;
import java.util.List;

/**
 * SysRoleService
 *
 * @author lis
 * @version 1.0
 * @description TODO
 * @date 2024/6/7 15:51
 */
@Slf4j
@Service
public class SysRoleService extends ServiceImpl<SysRoleMapper, SysRole> implements IService<SysRole> {

    public PageData<RoleQueryResultVO> findPage(RoleQueryVO query) {
        IPage<SysRole> page = PageUtils.buildPage();
        List<SysRole> roleList = baseMapper.selectList(
                page,
                Wrappers.<SysRole>lambdaQuery()
                        .like(StringUtils.isNotBlank(query.getName()), SysRole::getName, query.getName())
                        .like(StringUtils.isNotBlank(query.getCode()), SysRole::getCode, query.getCode())
                        .eq(StringUtils.isNotEmpty(query.getStatus()), SysRole::getStatus, query.getStatus())
        );
        return PageUtils.buildPageData(page, EntityUtils.toMapper(roleList, RoleQueryResultVO.class));
    }

    public RoleQueryResultVO findById(String id) {
        SysRole role = getById(id);
        return role.toMapper(RoleQueryResultVO.class);
    }

    public boolean saveRole(SysRole role) {
        if (StringUtils.isNotBlank(role.getId())) {
            role.setId(null);
        }
        if (checkNameExists(null, role.getName())) {
            throw new BusinessException("");
        }
        if (checkCodeExists(null, role.getCode())) {
            throw new BusinessException("");
        }
        return save(role);
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean saveRole(RoleFormVO form) {
        SysRole role = form.toEntity();
        return saveRole(role);
    }


    public boolean bindPermission(SysRole sysRole) {
        if (StringUtils.isBlank(sysRole.getId())) {
            throw new BusinessException("");
        }
        baseMapper.deleteRoleMenuRel(sysRole.getId());
        baseMapper.insertRoleMenuRel(sysRole.getId(),sysRole.getMenus().stream().map(SysMenu::getId).toList());

        return update(
            Wrappers.<SysRole>lambdaUpdate()
                    .eq(SysRole::getId, sysRole.getId())
                    .set(SysRole::getDataScopeType, sysRole.getDataScopeType())
        );
    }

    public boolean updateRole(SysRole role) {
        if (StringUtils.isNotBlank(role.getId())) {
            throw new BusinessException("");
        }
        if (checkNameExists(role.getId(), role.getName())) {
            throw new BusinessException("");
        }
        if (checkCodeExists(role.getId(), role.getCode())) {
            throw new BusinessException("");
        }
        return updateById(role);
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean updateRole(RoleFormVO form) {
        SysRole role = form.toEntity();
        return updateRole(role);
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean bindPermission(PermissionBindVO bindVO) {
        SysRole role = bindVO.toEntity();
        role.setDataScopeType(DataScopeType.of(bindVO.getDataScopeType()));
        role.setMenus(Arrays.stream(bindVO.getMenuIds()).map(SysMenu::new).toList());
        return bindPermission(role);
    }

    public boolean checkNameExists(@Nullable String id, @NotBlank String name) {
        return exists(
                Wrappers.<SysRole>lambdaQuery()
                        .eq(StringUtils.isNotBlank(id), SysRole::getId, id)
                        .eq(SysRole::getName, name)
        );
    }

    public boolean checkCodeExists(@Nullable String id, @NotBlank String code) {
        return exists(
            Wrappers.<SysRole>lambdaQuery()
                    .eq(StringUtils.isNotBlank(id), SysRole::getId, id)
                    .eq(SysRole::getCode, code)
        );
    }
}
