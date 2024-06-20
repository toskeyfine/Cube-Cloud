package com.toskey.cube.service.sas.business.service;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.toskey.cube.common.core.constant.CommonConstants;
import com.toskey.cube.common.core.exception.BusinessException;
import com.toskey.cube.common.core.util.EntityUtils;
import com.toskey.cube.common.core.util.StringUtils;
import com.toskey.cube.common.core.util.TreeUtils;
import com.toskey.cube.service.sas.business.domain.entity.SysMenu;
import com.toskey.cube.service.sas.business.domain.mapper.SysMenuMapper;
import com.toskey.cube.service.sas.business.vo.menu.MenuFormVO;
import com.toskey.cube.service.sas.business.vo.menu.MenuQueryResultVO;
import com.toskey.cube.service.sas.business.vo.menu.MenuQueryVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

/**
 * SysMenuService
 *
 * @author toskey
 * @version 1.0.0
 */
@Slf4j
@Service
public class SysMenuService extends ServiceImpl<SysMenuMapper, SysMenu> implements IService<SysMenu> {

    public List<MenuQueryResultVO> findTreeList(MenuQueryVO query) {
        List<SysMenu> deptList = baseMapper.selectList(
                Wrappers.<SysMenu>lambdaQuery()
                        .eq(StringUtils.isNotBlank(query.getParentId()), SysMenu::getParentId, query.getParentId())
                        .like(StringUtils.isNotBlank(query.getName()), SysMenu::getName, query.getName())
                        .eq(StringUtils.isNotBlank(query.getType()), SysMenu::getType, query.getType())
                        .eq(StringUtils.isNotBlank(query.getStatus()), SysMenu::getStatus, query.getStatus())
        );

        List<MenuQueryResultVO> resultList = EntityUtils.toMapper(deptList, MenuQueryResultVO.class);
        // 构建树结构
        return TreeUtils.buildTree(
                resultList,
                MenuQueryResultVO::getId,
                MenuQueryResultVO::getParentId,
                MenuQueryResultVO::getChildren,
                MenuQueryResultVO::setChildren
        );
    }

    public MenuQueryResultVO findById(String id) {
        SysMenu menu = getById(id);
        return menu.toMapper(MenuQueryResultVO.class);
    }

    public boolean saveMenu(SysMenu menu) {
        if (StringUtils.isNotBlank(menu.getId())) {
            menu.setId(null);
        }
        if (StringUtils.isBlank(menu.getParentId())) {
            menu.setParentId(CommonConstants.TREE_ROOT_DEFAULT_PARENT_KEY);
        }
        return save(menu);
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean saveMenu(MenuFormVO form) {
        SysMenu menu = form.toEntity();
        return save(menu);
    }

    /**
     * 修改菜单信息
     * <p>
     *     修改逻辑处理
     * </p>
     *
     * @param menu 待修改的菜单
     * @return
     */
    public boolean updateMenu(SysMenu menu) {
        if (StringUtils.isBlank(menu.getId())) {
            throw new BusinessException("");
        }
        if (StringUtils.isBlank(menu.getParentId())) {
            menu.setParentId(CommonConstants.TREE_ROOT_DEFAULT_PARENT_KEY);
        } else if (StringUtils.equals(CommonConstants.TREE_ROOT_DEFAULT_PARENT_KEY, menu.getParentId())) {
            // do nothing...
        } else {
            // 父级校验
            if (exists(Wrappers.<SysMenu>lambdaQuery().eq(SysMenu::getId, menu.getParentId()))) {
                throw new BusinessException("");
            }
        }
        return updateById(menu);
    }

    /**
     * 修改菜单信息
     * <p>vo -> entity转换</p>
     *
     * @param form
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public boolean updateMenu(MenuFormVO form) {
        SysMenu menu = form.toEntity();
        return updateMenu(menu);
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean removeMenu(String[] ids) {
        // 删除与角色的绑定关系
        Arrays.stream(ids).forEach(baseMapper::deleteMenuRoleRel);
        // 删除菜单
        return removeBatchByIds(Arrays.asList(ids));
    }

}
