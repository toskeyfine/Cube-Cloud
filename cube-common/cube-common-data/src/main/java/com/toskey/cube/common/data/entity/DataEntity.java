package com.toskey.cube.common.data.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.toskey.cube.common.core.base.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * DataEntity
 *
 * @author toskey
 * @version 1.0.0
 */
@Getter
@Setter
public class DataEntity extends BaseEntity {

    @TableField(fill = FieldFill.INSERT)
    protected String createBy;

    @TableField(fill = FieldFill.INSERT)
    protected LocalDateTime createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    protected String updateBy;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    protected LocalDateTime updateTime;

    protected String remark;

    @TableLogic(value = "0", delval = "1")
    protected String delFlag;
}
