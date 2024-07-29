package com.toskey.cube.common.core.base;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.toskey.cube.common.core.annotation.EntityProperties;
import com.toskey.cube.common.core.annotation.EntityProperty;
import com.toskey.cube.common.core.annotation.IgnoreProperty;
import com.toskey.cube.common.core.constant.CommonConstants;
import com.toskey.cube.common.core.util.EntityUtils;
import com.toskey.cube.common.core.util.StringUtils;
import lombok.SneakyThrows;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * BaseEntity
 *
 * @author toskey
 * @version 1.0.0
 */
@SuppressWarnings("unchecked")
public class BaseEntity implements Serializable {

    @TableId(type = IdType.ASSIGN_ID)
    protected String id;

    @SneakyThrows
    public <M extends BaseEntityMapper> M toMapper(final Class<M> clazz) {
        String[] ignoreFieldNames = Arrays.stream(clazz.getDeclaredFields())
                .filter(field -> field.isAnnotationPresent(IgnoreProperty.class))
                .filter(field -> {
                    IgnoreProperty ignore = field.getAnnotation(IgnoreProperty.class);
                    return Arrays.stream(ignore.ignore())
                            .anyMatch(strategy -> strategy == IgnoreProperty.Strategy.ALL
                                    || strategy == IgnoreProperty.Strategy.TO_MAPPER);
                })
                .map(Field::getName)
                .toArray(String[]::new);
        M mapper = clazz.getDeclaredConstructor().newInstance();
        BeanUtils.copyProperties(this, mapper, ignoreFieldNames);

        Field[] entityProperties = Arrays.stream(clazz.getDeclaredFields())
                .filter(field -> field.isAnnotationPresent(EntityProperty.class))
                .toArray(Field[]::new);
        for (Field field : entityProperties) {
            EntityProperty entityProperty = field.getAnnotation(EntityProperty.class);
            String targetName = entityProperty.targetName();
            Method entityGetter = this.getClass()
                    .getDeclaredMethod(CommonConstants.GETTER_PREFIX + StringUtils.toUpperCase(targetName, 0));
            Method mapperSetter = clazz.getDeclaredMethod(
                    CommonConstants.SETTER_PREFIX + StringUtils.toUpperCase(field.getName(), 0),
                    field.getType()
            );
            mapperSetter.invoke(mapper, entityGetter.invoke(this));
        }
        return mapper;
    }

    @SneakyThrows
    public <M extends BaseEntityMapper> M toMapper(final Class<M> clazz, final int mappingStrategy) {
        String[] ignoreFieldNames = Arrays.stream(clazz.getDeclaredFields())
                .filter(field -> field.isAnnotationPresent(IgnoreProperty.class))
                .filter(field -> {
                    IgnoreProperty ignore = field.getAnnotation(IgnoreProperty.class);
                    return Arrays.stream(ignore.ignore())
                            .anyMatch(strategy -> strategy == IgnoreProperty.Strategy.ALL
                                    || strategy == IgnoreProperty.Strategy.TO_MAPPER);
                })
                .map(Field::getName)
                .toArray(String[]::new);
        M mapper = clazz.getDeclaredConstructor().newInstance();
        BeanUtils.copyProperties(this, mapper, ignoreFieldNames);

        Field[] entityProperties = Arrays.stream(this.getClass().getDeclaredFields())
                .filter(field -> field.isAnnotationPresent(EntityProperty.class) || field.isAnnotationPresent(EntityProperties.class))
                .toArray(Field[]::new);

        for (Field field : entityProperties) {
            EntityProperty entityProperty = field.getAnnotation(EntityProperty.class);
            if (entityProperty == null) {
                EntityProperties propertiesAnno = field.getAnnotation(EntityProperties.class);
                Optional<EntityProperty> entityPropertyOptional = Arrays.stream(propertiesAnno.properties())
                        .filter(e -> e.strategy() == mappingStrategy)
                        .findFirst();
                if (entityPropertyOptional.isPresent()) {
                    entityProperty = entityPropertyOptional.get();
                } else {
                    continue;
                }
            }
            String targetName = entityProperty.targetName();
            Class<? extends BaseEntityMapper> targetClass = entityProperty.target();
            Method entityGetter = this.getClass()
                    .getDeclaredMethod(CommonConstants.GETTER_PREFIX + StringUtils.toUpperCase(targetName, 0));
            String mapperSetterName = CommonConstants.SETTER_PREFIX + StringUtils.toUpperCase(field.getName(), 0);
            if (targetClass != null && targetClass != BaseEntityMapper.class) {
                if (field.getType() == List.class) {
                    List<? extends BaseEntity> fieldValue = (List<? extends BaseEntity>) entityGetter.invoke(this);
                    List<? extends BaseEntityMapper> mapperValue = EntityUtils.toMapper(fieldValue, targetClass);
                    Method mapperSetter = clazz.getDeclaredMethod(mapperSetterName, field.getType());
                    mapperSetter.invoke(mapper, mapperValue);
                } else {
                    Object fieldValue = entityGetter.invoke(this);
                    Method mapperSetter = clazz.getDeclaredMethod(mapperSetterName, targetClass);
                    mapperSetter.invoke(mapper, EntityUtils.toMapper(fieldValue, targetClass));
                }
            }

            Method mapperSetter = clazz.getDeclaredMethod(mapperSetterName, field.getType());
            mapperSetter.invoke(mapper, entityGetter.invoke(this));
        }
        return mapper;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
