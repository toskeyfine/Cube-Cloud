package com.toskey.cube.common.core.base;

import com.toskey.cube.common.core.annotation.EntityMapper;
import com.toskey.cube.common.core.annotation.EntityProperty;
import com.toskey.cube.common.core.annotation.IgnoreProperty;
import com.toskey.cube.common.core.constant.CommonConstants;
import com.toskey.cube.common.core.util.StringUtils;
import lombok.SneakyThrows;
import org.springframework.beans.BeanUtils;
import org.springframework.core.annotation.AnnotationUtils;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * BaseEntityMapper
 *
 * @author toskey
 * @version 1.0.0
 */
@SuppressWarnings("unchecked")
public class BaseEntityMapper implements Serializable {

    private <E extends BaseEntity> Class<E> getEntityClass() {
        EntityMapper annotation = AnnotationUtils.findAnnotation(this.getClass(), EntityMapper.class);
        if (annotation != null) {
            Class<? extends BaseEntity> entityClass = annotation.entity();
            return (Class<E>) entityClass;
        }
        return null;
    }

    @SneakyThrows
    public <E extends BaseEntity> E toEntity() {
        Class<E> entityCls = getEntityClass();
        if (entityCls == null) {
            throw new IllegalArgumentException(this.getClass().getName() + "未找到@EntityMapper定义.");
        }
        return toEntity(entityCls);
    }

    @SneakyThrows
    public <E extends BaseEntity> E toEntity(Class<E> entityCls) {
        String[] ignoreFileNames = Arrays.stream(this.getClass().getDeclaredFields())
                .filter(field -> field.isAnnotationPresent(IgnoreProperty.class))
                .filter(field -> {
                    IgnoreProperty ignoreProperty = field.getAnnotation(IgnoreProperty.class);
                    return Arrays.stream(ignoreProperty.ignore())
                            .anyMatch(strategy -> strategy == IgnoreProperty.Strategy.ALL
                                    || strategy == IgnoreProperty.Strategy.TO_ENTITY);
                })
                .map(Field::getName)
                .toArray(String[]::new);

        E entity = entityCls.getDeclaredConstructor().newInstance();
        BeanUtils.copyProperties(this, entity, ignoreFileNames);

        Field[] entityProperties = Arrays.stream(this.getClass().getDeclaredFields())
                .filter(field -> field.isAnnotationPresent(EntityProperty.class)
                        && !field.isAnnotationPresent(IgnoreProperty.class))
                .toArray(Field[]::new);
        for (Field field : entityProperties) {
            EntityProperty entityProperty = field.getAnnotation(EntityProperty.class);
            String targetProperty = entityProperty.target();
            Method mapperGetter = this.getClass().getDeclaredMethod(CommonConstants.GETTER_PREFIX + StringUtils.toUpperCase(field.getName(), 0));
            Method entitySetter = entityCls.getDeclaredMethod(CommonConstants.SETTER_PREFIX + StringUtils.toUpperCase(targetProperty, 0), field.getType());
            entitySetter.invoke(entity, mapperGetter.invoke(this));
        }
        return entity;
    }

}
