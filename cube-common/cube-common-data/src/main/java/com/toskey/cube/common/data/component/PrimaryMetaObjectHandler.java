package com.toskey.cube.common.data.component;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * PrimaryMetaObjectHandler
 *
 * @author lis
 * @version 1.0
 * @description TODO
 * @date 2024/7/22 15:58
 */
@RequiredArgsConstructor
public class PrimaryMetaObjectHandler implements MetaObjectHandler {

    private final List<MetaObjectHandler> handlers;

    @Override
    public void insertFill(MetaObject metaObject) {
        handlers.stream()
                .filter(handler -> !handler.equals(this))
                .forEach(handler -> handler.insertFill(metaObject));
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        handlers.stream()
                .filter(handler -> !handler.equals(this))
                .forEach(handler -> handler.updateFill(metaObject));
    }

}
