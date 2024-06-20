package com.toskey.cube.service.config.business.vo.config;

import com.toskey.cube.common.core.annotation.EntityMapper;
import com.toskey.cube.common.core.base.BaseEntityMapper;
import com.toskey.cube.service.config.business.domain.entity.SysConfig;

/**
 * ClientQueryVO
 *
 * @author lis
 * @version 1.0
 * @description TODO
 * @date 2024/6/18 16:17
 */
@EntityMapper(entity = SysConfig.class)
public class ConfigQueryVO extends BaseEntityMapper {

    private String groupId;

    private String name;

    private String code;

    private String status;

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
