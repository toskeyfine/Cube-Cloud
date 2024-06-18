package com.toskey.cube.common.excel.strategy;

import com.alibaba.excel.write.handler.context.CellWriteHandlerContext;
import com.alibaba.excel.write.metadata.style.WriteCellStyle;
import com.alibaba.excel.write.style.AbstractCellStyleStrategy;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;

/**
 * 单元格自动居中策略
 *
 * @author toskey
 * @version 1.0.0
 */
public class AutoCellCenterStrategy extends AbstractCellStyleStrategy {

    private final boolean headerCenter;
    private final boolean contentCenter;

    public AutoCellCenterStrategy(boolean headerCenter, boolean contentCenter) {
        this.headerCenter = headerCenter;
        this.contentCenter = contentCenter;
    }

    @Override
    protected void setHeadCellStyle(CellWriteHandlerContext context) {
        if (stopProcessing(context) || this.headerCenter) {
            WriteCellStyle headerStyle = new WriteCellStyle();
            headerStyle.setHorizontalAlignment(HorizontalAlignment.CENTER);
            headerStyle.setVerticalAlignment(VerticalAlignment.CENTER);
            WriteCellStyle.merge(headerStyle, context.getFirstCellData().getOrCreateStyle());
        }
    }

    @Override
    protected void setContentCellStyle(CellWriteHandlerContext context) {
        if (stopProcessing(context) || this.contentCenter) {
            WriteCellStyle contentStyle = new WriteCellStyle();
            contentStyle.setHorizontalAlignment(HorizontalAlignment.CENTER);
            contentStyle.setVerticalAlignment(VerticalAlignment.CENTER);
            WriteCellStyle.merge(contentStyle, context.getFirstCellData().getOrCreateStyle());
        }
    }

    protected boolean stopProcessing(CellWriteHandlerContext context) {
        return context.getFirstCellData() == null;
    }
}
