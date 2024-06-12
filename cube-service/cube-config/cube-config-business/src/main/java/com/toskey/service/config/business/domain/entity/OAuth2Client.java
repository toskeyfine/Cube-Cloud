package com.toskey.service.config.business.domain.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.toskey.cube.common.data.entity.DataEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * OAuth2Client
 *
 * @author lis
 * @version 1.0
 * @description TODO
 * @date 2024/6/7 17:33
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_oauth2_client")
public class OAuth2Client extends DataEntity {
}
