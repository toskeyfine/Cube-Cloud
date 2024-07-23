package com.toskey.cube.common.tenant.component;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.util.ClassUtils;

/**
 * TenantEntityMetaObjectHandler
 *
 * @author toskey
 * @version 1.0.0
 */
public class TenantEntityMetaObjectHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        fillValIfNullByName("tenantId", TenantContextHolder.getContext().getId(), metaObject, true);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        fillValIfNullByName("tenantId", TenantContextHolder.getContext().getId(), metaObject, true);
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
}
