package com.toskey.cube.common.core.util;

import com.toskey.cube.common.core.base.BaseEntity;
import com.toskey.cube.common.core.base.BaseEntityMapper;
import jakarta.annotation.Nonnull;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import org.springframework.beans.BeanUtils;

import java.util.List;

/**
 * @description: 实体类型转换工具
 * @author: zxicet
 * @create: 2023-06-27 17:43
 */
@UtilityClass
public class EntityUtils {

    @SneakyThrows
    public <T, E> E to(@Nonnull T src, @Nonnull final Class<E> dstCls) {
        E dst = dstCls.getDeclaredConstructor().newInstance();
        BeanUtils.copyProperties(src, dst);

        return dst;
    }

    public <T, E> List<E> to(@Nonnull List<T> src, @Nonnull final Class<E> dstCls) {
        return src.stream()
                .map(t -> to(t, dstCls))
                .toList();
    }

    public <E extends BaseEntity, M extends BaseEntityMapper> M toMapper(@Nonnull E entity, @Nonnull final Class<M> mapperCls) {
        return entity.toMapper(mapperCls);
    }

    public <E extends BaseEntity, M extends BaseEntityMapper> List<M> toMapper(@Nonnull List<E> entities, @Nonnull final Class<M> mapperCls) {
        return entities.stream()
                .map(t -> EntityUtils.toMapper(t, mapperCls))
                .toList();
    }

    public <M extends BaseEntityMapper> BaseEntity toEntity(@Nonnull M mapper) {
        return mapper.toEntity();
    }

    public <M extends BaseEntityMapper> List<? extends BaseEntity> toEntity(@Nonnull List<M> mappers) {
        return mappers.stream()
                .map(EntityUtils::toEntity)
                .toList();
    }

}
