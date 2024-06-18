package com.toskey.cube.common.excel.util;

import com.alibaba.excel.EasyExcel;
import com.toskey.cube.common.excel.annotation.ExcelMapper;
import com.toskey.cube.common.excel.converter.*;

import java.io.InputStream;

/**
 * Excel读取器
 *
 * @author toskey
 * @version 1.0.0
 */
public class ExcelReader implements Excel {

    private final ReaderProperties readerProperties;

    public ExcelReader(ReaderProperties readerProperties) {
        this.readerProperties = readerProperties;
    }

    public <T> void read(InputStream inputStream, Integer sheetNo, Class<T> clazz) {
        if (inputStream == null) {
            throw new IllegalArgumentException("文件流为空");
        }
        ExcelMapper annotation = clazz.getAnnotation(ExcelMapper.class);
        if (annotation == null) {
            throw new IllegalArgumentException("未找到@ZxExcelReader注解");
        }

        EasyExcel.read(inputStream, clazz, readerProperties.getReadListener())
                .registerConverter(IntegerConverter.INSTANCE)
                .registerConverter(LocalDateTimeConverter.INSTANCE)
                .registerConverter(LocalDateConverter.INSTANCE)
                .registerConverter(LongConverter.INSTANCE)
                .registerConverter(StringArrayConverter.INSTANCE)
                .headRowNumber(annotation.headRowNumber())
                .ignoreEmptyRow(annotation.ignoreEmptyRow())
                .sheet(sheetNo)
                .doRead();
    }

}
