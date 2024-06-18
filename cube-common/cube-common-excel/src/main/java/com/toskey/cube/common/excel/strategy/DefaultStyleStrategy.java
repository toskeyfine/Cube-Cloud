package com.toskey.cube.common.excel.strategy;

import com.alibaba.excel.write.handler.context.CellWriteHandlerContext;
import com.alibaba.excel.write.metadata.style.WriteCellStyle;
import com.alibaba.excel.write.metadata.style.WriteFont;
import com.alibaba.excel.write.style.AbstractCellStyleStrategy;
import com.toskey.cube.common.core.util.StringUtils;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.IndexedColors;

import java.util.function.Supplier;

/**
 * 默认样式
 *
 * @author toskey
 * @version 1.0.0
 */
public class DefaultStyleStrategy extends AbstractCellStyleStrategy {

    private boolean border = Boolean.TRUE;

    private IndexedColors headerBgColor = IndexedColors.GREY_40_PERCENT;

    private IndexedColors headerFontColor = IndexedColors.BLACK;

    private IndexedColors contentFontColor = IndexedColors.BLACK;

    private String headerFontName = "微软雅黑";

    private String contentFontName = "微软雅黑";

    private short headerFontSize = 12;

    private short contentFontSize = 10;

    private boolean wrapped = Boolean.FALSE;

    private boolean headerBold = Boolean.TRUE;

    private boolean contentBold = Boolean.FALSE;

    @Override
    protected void setHeadCellStyle(CellWriteHandlerContext context) {
        WriteCellStyle headerStyle = new WriteCellStyle();
        WriteFont headerFont = new WriteFont();
        headerFont.setBold(this.headerBold);
        headerFont.setColor(this.headerFontColor.getIndex());
        if (StringUtils.isNotBlank(this.headerFontName)) {
            headerFont.setFontName(this.headerFontName);
        }
        if (this.headerFontSize > 0) {
            headerFont.setFontHeightInPoints(this.headerFontSize);
        }
        headerStyle.setWriteFont(headerFont);
        headerStyle.setFillBackgroundColor(this.headerBgColor.getIndex());
        if (this.border) {
            headerStyle.setBorderBottom(BorderStyle.THIN);
            headerStyle.setBorderTop(BorderStyle.THIN);
            headerStyle.setBorderLeft(BorderStyle.THIN);
            headerStyle.setBorderRight(BorderStyle.THIN);
        }
        WriteCellStyle.merge(headerStyle, context.getFirstCellData().getOrCreateStyle());
    }

    @Override
    protected void setContentCellStyle(CellWriteHandlerContext context) {
        WriteCellStyle contentStyle = new WriteCellStyle();
        WriteFont contentFont = new WriteFont();
        contentFont.setBold(this.contentBold);
        contentFont.setColor(contentFontColor.getIndex());
        if (StringUtils.isNotBlank(this.contentFontName)) {
            contentFont.setFontName(this.contentFontName);
        }
        if (this.contentFontSize > 0) {
            contentFont.setFontHeightInPoints(contentFontSize);
        }
        contentStyle.setWriteFont(contentFont);
        contentStyle.setWrapped(this.wrapped);
        if (this.border) {
            contentStyle.setBorderBottom(BorderStyle.THIN);
            contentStyle.setBorderTop(BorderStyle.THIN);
            contentStyle.setBorderLeft(BorderStyle.THIN);
            contentStyle.setBorderRight(BorderStyle.THIN);
        }
        WriteCellStyle.merge(contentStyle, context.getFirstCellData().getOrCreateStyle());
    }

    public DefaultStyleStrategy bold() {
        this.border = true;
        return this;
    }

    public DefaultStyleStrategy headerBgColor(Supplier<IndexedColors> supplier) {
        this.headerBgColor = supplier.get();
        return this;
    }

    public DefaultStyleStrategy headerFontColor(Supplier<IndexedColors> supplier) {
        this.headerFontColor = supplier.get();
        return this;
    }

    public DefaultStyleStrategy contentFontColor(Supplier<IndexedColors> supplier) {
        this.contentFontColor = supplier.get();
        return this;
    }

    public DefaultStyleStrategy headerFontName(Supplier<String> supplier) {
        this.headerFontName = supplier.get();
        return this;
    }

    public DefaultStyleStrategy contentFontName(Supplier<String> supplier) {
        this.contentFontName = supplier.get();
        return this;
    }

    public DefaultStyleStrategy headerFontSize(Supplier<Short> supplier) {
        this.headerFontSize = supplier.get();
        return this;
    }

    public DefaultStyleStrategy contentFontSize(Supplier<Short> supplier) {
        this.contentFontSize = supplier.get();
        return this;
    }

    public DefaultStyleStrategy wrapped() {
        this.wrapped = true;
        return this;
    }

    public DefaultStyleStrategy headerBold() {
        this.headerBold = true;
        return this;
    }

    public DefaultStyleStrategy contentBold() {
        this.contentBold = true;
        return this;
    }
}
