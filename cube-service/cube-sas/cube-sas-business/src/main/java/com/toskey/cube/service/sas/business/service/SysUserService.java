package com.toskey.cube.service.sas.business.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.toskey.cube.common.core.base.PageData;
import com.toskey.cube.common.core.constant.CommonConstants;
import com.toskey.cube.common.core.constant.enums.PasswordStrength;
import com.toskey.cube.common.core.exception.BusinessException;
import com.toskey.cube.common.core.util.EntityUtils;
import com.toskey.cube.common.core.util.PageUtils;
import com.toskey.cube.common.core.util.StringUtils;
import com.toskey.cube.common.core.util.PasswordUtils;
import com.toskey.cube.common.resource.server.util.SecurityUtils;
import com.toskey.cube.service.sas.interfaces.dto.UserDTO;
import com.toskey.cube.service.sas.business.domain.entity.SysRole;
import com.toskey.cube.service.sas.business.domain.entity.SysUser;
import com.toskey.cube.service.sas.business.domain.mapper.SysUserMapper;
import com.toskey.cube.service.sas.business.vo.user.UserFormVO;
import com.toskey.cube.service.sas.business.vo.user.UserQueryResultVO;
import com.toskey.cube.service.sas.business.vo.user.UserQueryVO;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

/**
 * SysUserService
 *
 * @author lis
 * @version 1.0
 * @description TODO
 * @date 2024/6/7 15:50
 */
@Slf4j
@Service
public class SysUserService extends ServiceImpl<SysUserMapper, SysUser> implements IService<SysUser> {

    /**
     * 分页查询
     *
     * @param query 查询条件
     * @return 分页结果
     */
    public PageData<UserQueryResultVO> findPage(UserQueryVO query) {
        IPage<SysUser> page = PageUtils.buildPage();
        List<SysUser> userList = baseMapper.selectList(
                page,
                Wrappers.<SysUser>lambdaQuery()
                        .like(StringUtils.isNotBlank(query.getUsername()), SysUser::getUsername, query.getUsername())
                        .like(StringUtils.isNotBlank(query.getName()), SysUser::getName, query.getName())
                        .like(StringUtils.isNotBlank(query.getMobile()), SysUser::getMobile, query.getMobile())
                        .like(StringUtils.isNotBlank(query.getEmail()), SysUser::getEmail, query.getEmail())
                        .eq(StringUtils.isNotBlank(query.getStatus()), SysUser::getStatus, query.getStatus())
                        .eq(StringUtils.isNotBlank(query.getDeptId()), SysUser::getDeptId, query.getDeptId())
                        .eq(StringUtils.isNotBlank(query.getPostId()), SysUser::getPostId, query.getPostId())
                        .eq(StringUtils.isNotBlank(query.getUserType()), SysUser::getUserType, query.getUserType())
        );
        return PageUtils.buildPageData(page, EntityUtils.toMapper(userList, UserQueryResultVO.class));
    }

    public UserDTO findDTOByUsername(@NotBlank String username) {
        SysUser user = getOne(Wrappers.<SysUser>lambdaQuery().eq(SysUser::getUsername, username));
        return user.toMapper(UserDTO.class);
    }

    public UserDTO findDTOByMobile(@NotBlank String mobile) {
        SysUser user = getOne(Wrappers.<SysUser>lambdaQuery().eq(SysUser::getMobile, mobile));
        return user.toMapper(UserDTO.class);
    }

    /**
     * 根据ID查询用户
     *
     * @param id 查询条件
     * @return 查询结果
     */
    public UserQueryResultVO findById(String id) {
        SysUser user = getById(id);
        return user.toMapper(UserQueryResultVO.class);
    }

    public boolean saveUser(SysUser user) {
        if (StringUtils.isNotBlank(user.getId())) {
            user.setId(null);
        }
        if (checkUsernameExists(null, user.getUsername())) {
            throw new BusinessException("");
        }
        if (StringUtils.isNotBlank(user.getMobile())
                && checkUMobileExists(null, user.getMobile())) {
            throw new BusinessException("");
        }
        if (StringUtils.isNotBlank(user.getEmail())
                && checkEmailExists(null, user.getEmail())) {
            throw new BusinessException("");
        }

        PasswordStrength passwordStrength = PasswordUtils.strength(user.getPassword());
        user.setPasswordStrength(passwordStrength.getValue());
        user.setPassword(PasswordUtils.encode(user.getPassword()));

        if (save(user)) {
            return baseMapper.insertUserRoleRel(user.getId(), user.getRoles().stream().map(SysRole::getId).toList()) > 0;
        }
        return false;
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean saveUser(UserFormVO form) {
        SysUser user = form.toEntity();
        user.setRoles(form.getRoleIds().stream().map(SysRole::new).toList());
        user.setUserType(CommonConstants.USER_TYPE_NORMAL);
        return saveUser(user);
    }

    public boolean updateUser(SysUser user) {
        if (StringUtils.isNotBlank(user.getId())) {
            throw new BusinessException("");
        }
        // 任何用户只能在个人中心修改自己的信息
        if (StringUtils.equals(SecurityUtils.getUserId(), user.getId())) {
            throw new BusinessException("");
        }
        // 唯一性校验
        if (checkUsernameExists(user.getId(), user.getUsername())) {
            throw new BusinessException("");
        }
        if (StringUtils.isNotBlank(user.getMobile())
                && checkUMobileExists(user.getId(), user.getMobile())) {
            throw new BusinessException("");
        }
        if (StringUtils.isNotBlank(user.getEmail())
                && checkEmailExists(user.getId(), user.getEmail())) {
            throw new BusinessException("");
        }
        if (StringUtils.isNotBlank(user.getPassword())) {
            PasswordStrength passwordStrength = PasswordUtils.strength(user.getPassword());
            user.setPasswordStrength(passwordStrength.getValue());
            user.setPassword(PasswordUtils.encode(user.getPassword()));
        }
        if (baseMapper.deleteUserRoleRel(user.getId()) > 0) {
            baseMapper.insertUserRoleRel(user.getId(), user.getRoles().stream().map(SysRole::getId).toList());
        }
        return updateById(user);
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean updateUser(UserFormVO form) {
        SysUser user = form.toEntity();
        user.setRoles(form.getRoleIds().stream().map(SysRole::new).toList());
        return updateUser(user);
    }

    public boolean lock(@NotBlank String username) {
        return update(
                Wrappers.<SysUser>lambdaUpdate()
                        .eq(SysUser::getUsername, username)
                        .set(SysUser::getStatus, CommonConstants.USER_STATUS_LOCKED)
        );
    }

    public boolean unlock(@NotBlank String username) {
        return update(
                Wrappers.<SysUser>lambdaUpdate()
                        .eq(SysUser::getUsername, username)
                        .set(SysUser::getStatus, CommonConstants.USER_STATUS_NORMAL)
        );
    }

    @Transactional
    public boolean removeUser(String[] ids) {
        // 不允许删除自己，可使用注销账户功能
        if (Arrays.asList(ids).contains(SecurityUtils.getUserId())) {
            throw new BusinessException("");
        }
        // 删用户角色关系
        Arrays.stream(ids).forEach(baseMapper::deleteUserRoleRel);
        return removeBatchByIds(Arrays.asList(ids));
    }

    public boolean checkUsernameExists(@Nullable String id, @Nonnull String username) {
        return exists(
                Wrappers.<SysUser>lambdaQuery()
                        .eq(SysUser::getUsername, username)
                        .eq(StringUtils.isNotBlank(id), SysUser::getId, id)
        );
    }

    public boolean checkUMobileExists(@Nullable String id, @Nonnull String mobile) {
        return exists(
                Wrappers.<SysUser>lambdaQuery()
                        .eq(SysUser::getMobile, mobile)
                        .eq(StringUtils.isNotBlank(id), SysUser::getId, id)
        );
    }


    public boolean checkEmailExists(@Nullable String id, @Nonnull String email) {
        return exists(
                Wrappers.<SysUser>lambdaQuery()
                        .eq(SysUser::getEmail, email)
                        .eq(StringUtils.isNotBlank(id), SysUser::getId, id)
        );
    }

}
