package com.toskey.cube.service.sas.business.service;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.toskey.cube.common.core.constant.CommonConstants;
import com.toskey.cube.common.core.exception.BusinessException;
import com.toskey.cube.common.core.util.EntityUtils;
import com.toskey.cube.common.core.util.StringUtils;
import com.toskey.cube.common.core.util.TreeUtils;
import com.toskey.cube.service.sas.business.domain.entity.SysDept;
import com.toskey.cube.service.sas.business.domain.mapper.SysDeptMapper;
import com.toskey.cube.service.sas.business.vo.dept.DeptFormVO;
import com.toskey.cube.service.sas.business.vo.dept.DeptQueryResultVO;
import com.toskey.cube.service.sas.business.vo.dept.DeptQueryVO;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

/**
 * SysDeptService
 *
 * @author lis
 * @version 1.0
 * @description TODO
 * @date 2024/6/7 15:51
 */
@Slf4j
@Service
public class SysDeptService extends ServiceImpl<SysDeptMapper, SysDept> implements IService<SysDept> {

    public List<DeptQueryResultVO> findTreeList(DeptQueryVO query) {
        List<SysDept> deptList = baseMapper.selectList(
                Wrappers.<SysDept>lambdaQuery()
                        .eq(StringUtils.isNotBlank(query.getParentId()), SysDept::getParentId, query.getParentId())
                        .like(StringUtils.isNotBlank(query.getName()), SysDept::getName, query.getName())
                        .like(StringUtils.isNotBlank(query.getCode()), SysDept::getCode, query.getCode())
                        .eq(StringUtils.isNotBlank(query.getStatus()), SysDept::getStatus, query.getStatus())
        );

        List<DeptQueryResultVO> resultList = EntityUtils.toMapper(deptList, DeptQueryResultVO.class);
        // 构建树结构
        return TreeUtils.buildTree(
                resultList,
                DeptQueryResultVO::getId,
                DeptQueryResultVO::getParentId,
                CommonConstants.TREE_ROOT_DEFAULT_PARENT_KEY,
                DeptQueryResultVO::getOrdered,
                DeptQueryResultVO::setChildren
        );
    }

    public DeptQueryResultVO findById(String id) {
        SysDept dept = getById(id);
        return dept.toMapper(DeptQueryResultVO.class);
    }

    public boolean saveDept(SysDept dept) {
        if (StringUtils.isNotBlank(dept.getId())) {
            dept.setId(null);
        }
        if (checkNameExists(null, dept.getName())) {
            throw new BusinessException("");
        }
        if (checkCodeExists(null, dept.getCode())) {
            throw new BusinessException("");
        }
        return save(dept);
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean saveDept(DeptFormVO form) {
        SysDept dept = form.toEntity();
        return save(dept);
    }

    /**
     * 修改部门信息
     * <p>
     *     修改逻辑处理
     * </p>
     *
     * @param dept 待修改的部门
     * @return
     */
    public boolean updateDept(SysDept dept) {
        if (StringUtils.isBlank(dept.getId())) {
            throw new BusinessException("");
        }
        if (checkNameExists(dept.getId(), dept.getName())) {
            throw new BusinessException("");
        }
        if (checkCodeExists(dept.getId(), dept.getCode())) {
            throw new BusinessException("");
        }
        // 用户绑定过的部门不允许修改状态
        if (StringUtils.equals(CommonConstants.DATA_STATUS_DISABLED, dept.getStatus())) {
            if (baseMapper.deptBindCount(dept.getId()) > 0) {
                throw new BusinessException("");
            }
        }
        return updateById(dept);
    }

    /**
     * 修改部门信息
     * <p>vo -> entity转换</p>
     *
     * @param form
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public boolean updateDept(DeptFormVO form) {
        SysDept dept = form.toEntity();
        return updateDept(dept);
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean removeDept(String[] ids) {
        for (String id : ids) {
            if (baseMapper.deptBindCount(id) > 0) {
                throw new BusinessException("");
            }
        }
        return removeBatchByIds(Arrays.asList(ids));
    }

    public boolean checkNameExists(@Nullable String id, @Nonnull String name) {
        return exists(
                Wrappers.<SysDept>lambdaQuery()
                        .eq(SysDept::getName, name)
                        .eq(StringUtils.isNotBlank(id), SysDept::getId, id)
        );
    }

    public boolean checkCodeExists(@Nullable String id, @Nonnull String code) {
        return exists(
                Wrappers.<SysDept>lambdaQuery()
                        .eq(SysDept::getCode, code)
                        .eq(StringUtils.isNotBlank(id), SysDept::getId, id)
        );
    }

}
