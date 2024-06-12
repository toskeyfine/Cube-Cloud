package com.toskey.cube.service.sas.business.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.toskey.cube.service.sas.business.domain.entity.SysTenant;
import com.toskey.cube.service.sas.business.domain.mapper.SysTenantMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * SysTenantService
 *
 * @author lis
 * @version 1.0
 * @description TODO
 * @date 2024/6/7 15:52
 */
@Slf4j
@Service
public class SysTenantService extends ServiceImpl<SysTenantMapper, SysTenant> implements IService<SysTenant> {
}
