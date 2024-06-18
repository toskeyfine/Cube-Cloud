package com.toskey.cube.common.excel.enhancer;

import com.alibaba.excel.write.builder.ExcelWriterSheetBuilder;
import com.alibaba.excel.write.handler.CellWriteHandler;
import com.alibaba.excel.write.style.column.SimpleColumnWidthStyleStrategy;
import com.alibaba.excel.write.style.row.SimpleRowHeightStyleStrategy;
import com.toskey.cube.common.excel.strategy.AutoCellHeightStrategy;
import com.toskey.cube.common.excel.strategy.AutoCellWidthStrategy;
import com.toskey.cube.common.excel.strategy.DefaultStyleStrategy;
import com.toskey.cube.common.excel.util.Customizer;
import org.apache.commons.collections4.CollectionUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Supplier;

/**
 * 默认写入增强器
 *
 * @author toskey
 * @version 1.0.0
 */
public class DefaultWriterSheetBuilderEnhancer implements WriterSheetBuilderEnhancer {

    private boolean autoCellWidth = Boolean.FALSE;
    private Integer cellWidth = -1;
    private boolean autoCellHeight = Boolean.FALSE;
    private short headerHeight = -1;
    private short contentHeight = -1;
    private boolean autoTrim = Boolean.FALSE;
    private final Collection<String> excludeColumnFieldNames = new ArrayList<>();
    private final DefaultStyleStrategy defaultStyleStrategy = new DefaultStyleStrategy();
    private final List<CellWriteHandler> handlers = new ArrayList<>();

    public ExcelWriterSheetBuilder enhance(ExcelWriterSheetBuilder sheetBuilder) {
        // 设置宽度
        if (this.autoCellWidth) {
            sheetBuilder = sheetBuilder.registerWriteHandler(new AutoCellWidthStrategy());
        } else {
            if (this.cellWidth != null && this.cellWidth > 0) {
                sheetBuilder = sheetBuilder.registerWriteHandler(new SimpleColumnWidthStyleStrategy(this.cellWidth));
            }
        }
        // 设置高度
        if (this.autoCellHeight) {
            sheetBuilder = sheetBuilder.registerWriteHandler(new AutoCellHeightStrategy());
        } else {
            if (this.headerHeight > 0 && this.contentHeight > 0) {
                sheetBuilder = sheetBuilder.registerWriteHandler(new SimpleRowHeightStyleStrategy(this.headerHeight, this.contentHeight));
            }
        }
        if (CollectionUtils.isNotEmpty(this.excludeColumnFieldNames)) {
            sheetBuilder.excludeColumnFieldNames(this.excludeColumnFieldNames);
        }
        if (this.autoTrim) {
            sheetBuilder.autoTrim(true);
        }
        sheetBuilder.registerWriteHandler(defaultStyleStrategy);
        // 扩展样式策略
        if (CollectionUtils.isNotEmpty(this.handlers)) {
            for (CellWriteHandler handler : this.handlers) {
                sheetBuilder = sheetBuilder.registerWriteHandler(handler);
            }
        }
        return sheetBuilder;
    }

    public DefaultWriterSheetBuilderEnhancer autoCellWidth() {
        this.autoCellWidth = true;
        return this;
    }

    public DefaultWriterSheetBuilderEnhancer autoCellHeight() {
        this.autoCellHeight = true;
        return this;
    }

    public DefaultWriterSheetBuilderEnhancer cellWidth(Supplier<Integer> supplier) {
        this.cellWidth = supplier.get();
        return this;
    }

    public DefaultWriterSheetBuilderEnhancer headerHeight(Supplier<Short> supplier) {
        this.headerHeight = supplier.get();
        return this;
    }

    public DefaultWriterSheetBuilderEnhancer contentHeight(Supplier<Short> supplier) {
        this.contentHeight = supplier.get();
        return this;
    }

    public DefaultWriterSheetBuilderEnhancer autoTrim() {
        this.autoTrim = true;
        return this;
    }

    public DefaultWriterSheetBuilderEnhancer excludeColumnFieldNames(Customizer<Collection<String>> customizer) {
        customizer.customize(this.excludeColumnFieldNames);
        return this;
    }

    public DefaultWriterSheetBuilderEnhancer handlers(Customizer<List<CellWriteHandler>> customizer) {
        customizer.customize(this.handlers);
        return this;
    }

    public DefaultWriterSheetBuilderEnhancer defaultStyleStrategy(Customizer<DefaultStyleStrategy> customizer) {
        customizer.customize(this.defaultStyleStrategy);
        return this;
    }
}
