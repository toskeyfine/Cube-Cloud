package com.toskey.cube.service.sas.business.domain.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.toskey.cube.common.data.entity.DataEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * SysTenant
 *
 * @author lis
 * @version 1.0
 * @description TODO
 * @date 2024/6/7 15:27
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_tenant")
public class SysTenant extends DataEntity {

    private String name;

    private String code;

    private LocalDate regTime;

    private String type;

    private Integer ordered;

    private String status;

    private LocalDateTime authorizationBeginTime;

    private LocalDateTime authorizationEndTime;

}
