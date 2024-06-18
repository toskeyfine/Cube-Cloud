package com.toskey.cube.service.sas.business.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.toskey.cube.common.core.base.PageData;
import com.toskey.cube.common.core.constant.CommonConstants;
import com.toskey.cube.common.core.exception.BusinessException;
import com.toskey.cube.common.core.util.EntityUtils;
import com.toskey.cube.common.core.util.PageUtils;
import com.toskey.cube.common.core.util.StringUtils;
import com.toskey.cube.service.sas.business.domain.entity.SysPost;
import com.toskey.cube.service.sas.business.domain.mapper.SysPostMapper;
import com.toskey.cube.service.sas.business.vo.post.PostFormVO;
import com.toskey.cube.service.sas.business.vo.post.PostQueryResultVO;
import com.toskey.cube.service.sas.business.vo.post.PostQueryVO;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

/**
 * SysPostService
 *
 * @author lis
 * @version 1.0
 * @description TODO
 * @date 2024/6/7 15:51
 */
@Slf4j
@Service
public class SysPostService extends ServiceImpl<SysPostMapper, SysPost> implements IService<SysPost> {

    public PageData<PostQueryResultVO> findPage(PostQueryVO query) {
        IPage<SysPost> page = PageUtils.buildPage();
        List<SysPost> postList = baseMapper.selectList(
                page,
                Wrappers.<SysPost>lambdaQuery()
                        .like(StringUtils.isNotBlank(query.getName()), SysPost::getName, query.getName())
                        .like(StringUtils.isNotBlank(query.getCode()), SysPost::getCode, query.getCode())
                        .eq(StringUtils.isNotBlank(query.getDeptId()), SysPost::getDeptId, query.getDeptId())
                        .eq(StringUtils.isNotBlank(query.getStatus()), SysPost::getStatus, query.getStatus())
        );
        return PageUtils.buildPageData(page, EntityUtils.toMapper(postList, PostQueryResultVO.class));
    }

    public PostQueryResultVO findById(String id) {
        SysPost post = getById(id);
        return post.toMapper(PostQueryResultVO.class);
    }

    public boolean savePost(SysPost post) {
        if (StringUtils.isNotBlank(post.getId())) {
            post.setId(null);
        }
        if (checkNameExists(null, post.getName())) {
            throw new BusinessException("");
        }
        if (checkCodeExists(null, post.getCode())) {
            throw new BusinessException("");
        }
        return save(post);
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean savePost(PostFormVO form) {
        SysPost post = form.toEntity();
        return save(post);
    }

    /**
     * 修改岗位信息
     * <p>
     *     修改逻辑处理
     * </p>
     *
     * @param post 待修改的岗位
     * @return
     */
    public boolean updatePost(SysPost post) {
        if (StringUtils.isBlank(post.getId())) {
            throw new BusinessException("岗位选择错误");
        }
        if (checkNameExists(post.getId(), post.getName())) {
            throw new BusinessException("岗位名称已存在");
        }
        if (checkCodeExists(post.getId(), post.getCode())) {
            throw new BusinessException("岗位编码已存在");
        }
        // 用户绑定过的岗位不允许修改状态
        if (StringUtils.equals(CommonConstants.DATA_STATUS_DISABLED, post.getStatus())) {
            if (baseMapper.postBindCount(post.getId()) > 0) {
                throw new BusinessException("该岗位已绑定用户.");
            }
        }
        return updateById(post);
    }

    /**
     * 修改岗位信息
     * <p>vo -> entity转换</p>
     *
     * @param form
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public boolean updatePost(PostFormVO form) {
        SysPost dept = form.toEntity();
        return updatePost(dept);
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean removePost(String[] ids) {
        for (String id : ids) {
            if (baseMapper.postBindCount(id) > 0) {
                throw new BusinessException("该岗位已绑定用户.");
            }
        }
        return removeBatchByIds(Arrays.asList(ids));
    }

    public boolean checkNameExists(@Nullable String id, @Nonnull String name) {
        return exists(
                Wrappers.<SysPost>lambdaQuery()
                        .eq(SysPost::getName, name)
                        .ne(StringUtils.isNotBlank(id), SysPost::getId, id)
        );
    }

    public boolean checkCodeExists(@Nullable String id, @Nonnull String code) {
        return exists(
                Wrappers.<SysPost>lambdaQuery()
                        .eq(SysPost::getCode, code)
                        .ne(StringUtils.isNotBlank(id), SysPost::getId, id)
        );
    }
}
