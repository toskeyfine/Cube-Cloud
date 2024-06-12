package com.toskey.cube.service.sas.business.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.toskey.cube.service.sas.business.domain.entity.SysPost;
import com.toskey.cube.service.sas.business.domain.mapper.SysPostMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * SysPostService
 *
 * @author lis
 * @version 1.0
 * @description TODO
 * @date 2024/6/7 15:51
 */
@Slf4j
@Service
public class SysPostService extends ServiceImpl<SysPostMapper, SysPost> implements IService<SysPost> {
}
