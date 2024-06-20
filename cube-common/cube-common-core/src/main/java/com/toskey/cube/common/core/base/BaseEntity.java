package com.toskey.cube.common.core.base;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.toskey.cube.common.core.annotation.EntityProperty;
import com.toskey.cube.common.core.annotation.IgnoreProperty;
import com.toskey.cube.common.core.constant.CommonConstants;
import com.toskey.cube.common.core.util.StringUtils;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * BaseEntity
 *
 * @author toskey
 * @version 1.0.0
 */
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
            String targetProperty = entityProperty.target();
            Method entityGetter = this.getClass()
                    .getDeclaredMethod(CommonConstants.GETTER_PREFIX + StringUtils.toUpperCase(targetProperty, 0));
            Method mapperSetter = clazz.getDeclaredMethod(
                    CommonConstants.SETTER_PREFIX + StringUtils.toUpperCase(field.getName(), 0),
                    field.getType()
            );
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
