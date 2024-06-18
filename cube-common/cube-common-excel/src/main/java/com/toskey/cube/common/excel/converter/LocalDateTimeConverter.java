package com.toskey.cube.common.excel.converter;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.data.ReadCellData;
import com.alibaba.excel.metadata.data.WriteCellData;
import com.alibaba.excel.metadata.property.ExcelContentProperty;
import com.alibaba.excel.util.DateUtils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * EasyExcel日期格式转换器
 *
 * @author toskey
 * @version 1.0.0
 */
public enum LocalDateTimeConverter implements Converter<LocalDateTime> {

    INSTANCE;

    @Override
    public Class<?> supportJavaTypeKey() {
        return LocalDateTime.class;
    }

    @Override
    public CellDataTypeEnum supportExcelTypeKey() {
        return CellDataTypeEnum.STRING;
    }

    @Override
    public LocalDateTime convertToJavaData(ReadCellData<?> cellData, ExcelContentProperty contentProperty, GlobalConfiguration globalConfiguration) throws Exception {
        String cellVal = cellData.getStringValue();
        String pattern;
        if (contentProperty == null || contentProperty.getDateTimeFormatProperty() == null) {
            int valLen = cellVal.length();
            switch (valLen) {
                case 10:
                    pattern = DateUtils.DATE_FORMAT_10;
                    break;
                case 14:
                    pattern = DateUtils.DATE_FORMAT_14;
                    break;
                case 16:
                    if (cellVal.contains("-")) {
                        pattern = DateUtils.DATE_FORMAT_16;
                    } else {
                        pattern = DateUtils.DATE_FORMAT_16_FORWARD_SLASH;
                    }
                    break;
                case 17:
                    pattern = DateUtils.DATE_FORMAT_17;
                    break;
                case 19:
                    if (cellVal.contains("-")) {
                        pattern = DateUtils.DATE_FORMAT_19;
                    } else {
                        pattern = DateUtils.DATE_FORMAT_19_FORWARD_SLASH;
                    }
                default:
                    throw new IllegalArgumentException("日期转换错误");
            }
        } else {
            pattern = contentProperty.getDateTimeFormatProperty().getFormat();
        }
        return LocalDateTime.parse(cellVal, DateTimeFormatter.ofPattern(pattern));
    }

    @Override
    public WriteCellData<?> convertToExcelData(LocalDateTime value, ExcelContentProperty contentProperty, GlobalConfiguration globalConfiguration) throws Exception {
        String pattern;
        if (contentProperty == null || contentProperty.getDateTimeFormatProperty() == null) {
            pattern = DateUtils.DATE_FORMAT_19;
        } else {
            pattern = contentProperty.getDateTimeFormatProperty().getFormat();
        }
        return new WriteCellData<>(value.format(DateTimeFormatter.ofPattern(pattern)));
    }
}
