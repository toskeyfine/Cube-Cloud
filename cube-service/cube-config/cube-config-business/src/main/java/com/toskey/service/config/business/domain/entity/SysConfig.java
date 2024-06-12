package com.toskey.service.config.business.domain.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.toskey.cube.common.data.entity.DataEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * SysConfig
 *
 * @author lis
 * @version 1.0
 * @description TODO
 * @date 2024/6/7 17:35
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_config")
public class SysConfig extends DataEntity {
}
