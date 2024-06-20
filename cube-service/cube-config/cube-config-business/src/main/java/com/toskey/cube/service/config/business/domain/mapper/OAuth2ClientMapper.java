package com.toskey.cube.service.config.business.domain.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.toskey.cube.service.config.business.domain.entity.OAuth2Client;
import org.apache.ibatis.annotations.Mapper;

/**
 * OAuth2ClientMapper
 *
 * @author toskey
 * @version 1.0.0
 */
@Mapper
public interface OAuth2ClientMapper extends BaseMapper<OAuth2Client> {
}
