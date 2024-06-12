package com.toskey.cube.service.sas.business.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.toskey.cube.service.sas.business.domain.entity.SysDept;
import com.toskey.cube.service.sas.business.domain.mapper.SysDeptMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * SysDeptService
 *
 * @author lis
 * @version 1.0
 * @description TODO
 * @date 2024/6/7 15:51
 */
@Slf4j
@Service
public class SysDeptService extends ServiceImpl<SysDeptMapper, SysDept> implements IService<SysDept> {
}
