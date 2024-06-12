package com.toskey.cube.service.sas.business.vo.role;

import com.toskey.cube.common.core.annotation.EntityMapper;
import com.toskey.cube.common.core.annotation.IgnoreProperty;
import com.toskey.cube.common.core.base.BaseEntityMapper;
import com.toskey.cube.service.sas.business.domain.entity.SysRole;
import jakarta.validation.constraints.NotBlank;

/**
 * PermissionBindVO
 *
 * @author lis
 * @version 1.0
 * @description TODO
 * @date 2024/6/12 17:37
 */
@EntityMapper(entity = SysRole.class)
public class PermissionBindVO extends BaseEntityMapper {

    @NotBlank
    private String id;

    @IgnoreProperty
    @NotBlank
    private String dataScopeType;

    @IgnoreProperty
    private String[] menuIds;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDataScopeType() {
        return dataScopeType;
    }

    public void setDataScopeType(String dataScopeType) {
        this.dataScopeType = dataScopeType;
    }

    public String[] getMenuIds() {
        return menuIds;
    }

    public void setMenuIds(String[] menuIds) {
        this.menuIds = menuIds;
    }
}
