package com.toskey.cube.common.excel.util;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.excel.write.builder.ExcelWriterBuilder;
import com.alibaba.excel.write.builder.ExcelWriterSheetBuilder;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.toskey.cube.common.core.util.StringUtils;
import com.toskey.cube.common.excel.converter.*;
import com.toskey.cube.common.excel.enhancer.WriterSheetBuilderEnhancer;
import com.toskey.cube.common.excel.exception.ExcelWriteException;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

/**
 * Excel写入器
 *
 * @author toskey
 * @version 1.0.0
 */
public class ExcelWriter implements Excel {

    private final WriterProperties writerProperties;

    public ExcelWriter(WriterProperties writerProperties) {
        this.writerProperties = writerProperties;
    }

    public void write() throws IOException {
        OutputStream outputStream = this.writerProperties.getOutputStream();
        if (outputStream == null) {
            HttpServletResponse response = this.writerProperties.getResponse();
            if (response != null) {
                response.setContentType("application/vnd.ms-excel");
                response.setCharacterEncoding("utf-8");
                response.setHeader("Content-Disposition", "attachment;filename*=utf-8''" + writerProperties.getOutputName());

                outputStream = response.getOutputStream();
            } else {
                throw new ExcelWriteException("未找到输出流.请检查代码.");
            }
        }
        ExcelWriterBuilder writerBuilder = EasyExcel.write(outputStream)
                .registerConverter(IntegerConverter.INSTANCE)
                .registerConverter(LocalDateConverter.INSTANCE)
                .registerConverter(LocalDateTimeConverter.INSTANCE)
                .registerConverter(LongConverter.INSTANCE)
                .registerConverter(StringArrayConverter.INSTANCE)
                .autoCloseStream(true)
                .excelType(ExcelTypeEnum.XLSX)
                .inMemory(false);

        String template = this.writerProperties.getTemplate();
        if (StringUtils.isNotBlank(template)) {
            ClassPathResource classPathResource = new ClassPathResource(template);
            writerBuilder.withTemplate(classPathResource.getInputStream());
        }

        com.alibaba.excel.ExcelWriter writer = writerBuilder.build();

        ExcelWriterSheetBuilder sheetBuilder = null;
        List<ExcelSheet> sheetList = this.writerProperties.getSheets();
        for (ExcelSheet sheet : sheetList) {
            if (StringUtils.isNotBlank(sheet.getSheetName())) {
                sheetBuilder = EasyExcel.writerSheet(sheet.getSheetNo(), sheet.getSheetName());
            } else {
                sheetBuilder = EasyExcel.writerSheet(sheet.getSheetNo());
            }
            // 设置标题头
            if (sheet.getHeader() != null) {
                sheetBuilder.head(sheet.getHeader().getHeader());
            } else if (sheet.getDataCls() != null) {
                sheetBuilder.head(sheet.getDataCls());
            } else if (CollectionUtils.isNotEmpty(sheet.getDataList())) {
                sheetBuilder.head(sheet.getDataList().get(0).getClass());
            } else {
                throw new IllegalArgumentException("未找到标题配置. 请检查代码.");
            }

            WriterSheetBuilderEnhancer sheetEnhancer = sheet.getWriterSheetBuilderEnhancer();
            if (sheetEnhancer != null) {
                sheetBuilder = sheetEnhancer.enhance(sheetBuilder);
            }

            WriteSheet writeSheet = sheetBuilder.build();
            if (this.writerProperties.isFillMode()) {
                writer.fill(sheet.getDataList(), writeSheet);
            } else {
                writer.write(sheet.getDataList(), writeSheet);
            }
        }

        writer.finish();
    }

}
