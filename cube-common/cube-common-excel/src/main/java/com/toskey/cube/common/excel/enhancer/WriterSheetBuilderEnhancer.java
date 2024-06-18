package com.toskey.cube.common.excel.enhancer;

import com.alibaba.excel.write.builder.ExcelWriterSheetBuilder;

/**
 * Sheet写入器增强接口
 *
 * @author toskey
 * @version 1.0.0
 */
public interface WriterSheetBuilderEnhancer {

    ExcelWriterSheetBuilder enhance(ExcelWriterSheetBuilder sheetBuilder);

}
