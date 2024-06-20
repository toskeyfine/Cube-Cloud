package com.toskey.cube.service.config.business.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.toskey.cube.service.config.business.domain.entity.SysConfig;
import com.toskey.cube.service.config.business.domain.mapper.SysConfigMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * SysConfigService
 *
 * @author toskey
 * @version 1.0.0
 */
@Slf4j
@Service
public class SysConfigService extends ServiceImpl<SysConfigMapper, SysConfig> implements IService<SysConfig> {
}
