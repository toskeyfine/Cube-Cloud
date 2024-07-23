package com.toskey.cube.common.core.util;

import com.toskey.cube.common.core.base.BaseEntity;
import com.toskey.cube.common.core.base.BaseEntityMapper;
import jakarta.annotation.Nonnull;
import lombok.SneakyThrows;
import org.springframework.beans.BeanUtils;

import java.util.List;

/**
 * EntityUtils
 *
 * @author toskey
 * @version 1.0.0
 */
public class EntityUtils {

    @SneakyThrows
    public static <T, E> E to(@Nonnull T src, @Nonnull final Class<E> dstCls) {
        E dst = dstCls.getDeclaredConstructor().newInstance();
        BeanUtils.copyProperties(src, dst);

        return dst;
    }

    public static <T, E> List<E> to(@Nonnull List<T> src, @Nonnull final Class<E> dstCls) {
        return src.stream()
                .map(t -> to(t, dstCls))
                .toList();
    }

    public static <E extends BaseEntity, M extends BaseEntityMapper> M toMapper(@Nonnull E entity, @Nonnull final Class<M> mapperCls) {
        return entity.toMapper(mapperCls);
    }

    public static <E extends BaseEntity, M extends BaseEntityMapper> M toMapper(@Nonnull Object entity, @Nonnull final Class<M> mapperCls) {
        return ((E) entity).toMapper(mapperCls);
    }

    public static <E extends BaseEntity, M extends BaseEntityMapper> List<M> toMapper(@Nonnull List<E> entities, @Nonnull final Class<M> mapperCls) {
        return entities.stream()
                .map(t -> EntityUtils.toMapper(t, mapperCls))
                .toList();
    }

    public static <M extends BaseEntityMapper> BaseEntity toEntity(@Nonnull M mapper) {
        return mapper.toEntity();
    }

    public static <M extends BaseEntityMapper> List<? extends BaseEntity> toEntity(@Nonnull List<M> mappers) {
        return mappers.stream()
                .map(EntityUtils::toEntity)
                .toList();
    }

    public static <M extends BaseEntityMapper> BaseEntity toEntity(@Nonnull Object mapper, Class<? extends BaseEntity> entityCls) {
        return ((M) mapper).toEntity(entityCls);
    }

    public static <M extends BaseEntityMapper> List<? extends BaseEntity> toEntity(@Nonnull List<M> mappers, Class<? extends BaseEntity> entityCls) {
        return mappers.stream()
                .map(mapper -> EntityUtils.toEntity(mapper, entityCls))
                .toList();
    }

}
