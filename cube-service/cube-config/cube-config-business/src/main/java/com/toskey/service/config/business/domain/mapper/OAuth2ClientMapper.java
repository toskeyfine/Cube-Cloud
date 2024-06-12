package com.toskey.service.config.business.domain.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.toskey.service.config.business.domain.entity.OAuth2Client;
import org.apache.ibatis.annotations.Mapper;

/**
 * OAuth2ClientMapper
 *
 * @author lis
 * @version 1.0
 * @description TODO
 * @date 2024/6/7 17:34
 */
@Mapper
public interface OAuth2ClientMapper extends BaseMapper<OAuth2Client> {
}
