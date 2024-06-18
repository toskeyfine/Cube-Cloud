package com.toskey.cube.common.data.component;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.toskey.cube.common.security.util.SecurityUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.util.ClassUtils;

import java.time.LocalDateTime;

/**
 * MybatisPlusMetaObjectHandler
 *
 * @author lis
 * @version 1.0
 * @description TODO
 * @date 2024/6/7 14:42
 */
public class MybatisPlusMetaObjectHandler implements MetaObjectHandler {
    @Override
    public void insertFill(MetaObject metaObject) {
        LocalDateTime now = LocalDateTime.now();

        // 审计字段自动填充
        fillValIfNullByName("createTime", now, metaObject, false);
        fillValIfNullByName("createBy", getUserId(), metaObject, false);

        fillValIfNullByName("updateTime", LocalDateTime.now(), metaObject, true);
        fillValIfNullByName("updateBy", getUserId(), metaObject, true);

        // 删除标记自动填充
        fillValIfNullByName("delFlag", 0, metaObject, false);

    }

    @Override
    public void updateFill(MetaObject metaObject) {
        fillValIfNullByName("updateTime", LocalDateTime.now(), metaObject, true);
        fillValIfNullByName("updateBy", getUserId(), metaObject, true);
    }

    private static void fillValIfNullByName(String fieldName, Object fieldVal, MetaObject metaObject, boolean isCover) {
        // 0. 如果填充值为空
        if (fieldVal == null) {
            return;
        }
        // 1. 没有 set 方法
        if (!metaObject.hasSetter(fieldName)) {
            return;
        }
        // 2. 如果用户有手动设置的值
        Object userSetValue = metaObject.getValue(fieldName);
        String setValueStr = String.valueOf(userSetValue);
        if (StringUtils.isNotBlank(setValueStr) && !isCover) {
            return;
        }
        // 3. field 类型相同时设置
        Class<?> getterType = metaObject.getGetterType(fieldName);
        if (ClassUtils.isAssignableValue(getterType, fieldVal)) {
            metaObject.setValue(fieldName, fieldVal);
        }
    }

    /**
     * 获取当前用户id
     *
     * @return
     */
    private String getUserId() {
        try {
            SecurityUtils.getUserId();
        } catch (Exception e) {
            return null;
        }
        return SecurityUtils.getUserId();
    }
}
