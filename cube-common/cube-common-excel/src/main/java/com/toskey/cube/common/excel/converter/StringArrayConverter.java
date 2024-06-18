package com.toskey.cube.common.excel.converter;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.data.ReadCellData;
import com.alibaba.excel.metadata.data.WriteCellData;
import com.alibaba.excel.metadata.property.ExcelContentProperty;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * EasyExcel字符串数组格式转换器
 *
 * @author toskey
 * @version 1.0.0
 */
public enum StringArrayConverter implements Converter<String[]> {

    INSTANCE;

    @Override
    public Class<?> supportJavaTypeKey() {
        return String[].class;
    }

    @Override
    public CellDataTypeEnum supportExcelTypeKey() {
        return CellDataTypeEnum.STRING;
    }

    @Override
    public String[] convertToJavaData(ReadCellData<?> cellData, ExcelContentProperty contentProperty, GlobalConfiguration globalConfiguration) throws Exception {
        return cellData.getStringValue().split(",");
    }

    @Override
    public WriteCellData<?> convertToExcelData(String[] value, ExcelContentProperty contentProperty, GlobalConfiguration globalConfiguration) throws Exception {
        return new WriteCellData<>(Arrays.stream(value).collect(Collectors.joining()));
    }

}
