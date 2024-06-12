package com.toskey.service.config.business.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.toskey.service.config.business.domain.entity.OAuth2Client;
import com.toskey.service.config.business.domain.mapper.OAuth2ClientMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * OAuth2ClientService
 *
 * @author lis
 * @version 1.0
 * @description TODO
 * @date 2024/6/7 17:44
 */
@Slf4j
@Service
public class OAuth2ClientService extends ServiceImpl<OAuth2ClientMapper, OAuth2Client> implements IService<OAuth2Client> {
}
