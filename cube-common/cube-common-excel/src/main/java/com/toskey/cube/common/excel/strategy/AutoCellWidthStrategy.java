package com.toskey.cube.common.excel.strategy;

import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.Head;
import com.alibaba.excel.metadata.data.WriteCellData;
import com.alibaba.excel.write.metadata.holder.WriteSheetHolder;
import com.alibaba.excel.write.style.column.AbstractColumnWidthStyleStrategy;
import org.apache.poi.ss.usermodel.Cell;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 单元格自实行宽度策略
 *
 * @author toskey
 * @version 1.0.0
 */
public class AutoCellWidthStrategy extends AbstractColumnWidthStyleStrategy {

    private Map<Integer, Map<Integer, Integer>> CACHE = new HashMap<>();

    @Override
    protected void setColumnWidth(WriteSheetHolder writeSheetHolder, List<WriteCellData<?>> cellDataList, Cell cell, Head head, Integer relativeRowIndex, Boolean isHead) {
        boolean needSetWidth = isHead || !CollectionUtils.isEmpty(cellDataList);
        if (needSetWidth) {
            Map<Integer, Integer> maxColumnWidthMap = CACHE.computeIfAbsent(writeSheetHolder.getSheetNo(), k -> new HashMap<>());
            int columnWidth = this.dataLength(cellDataList, cell, isHead) + 5;
            if (columnWidth >= 0) {
                if (columnWidth > 254) {
                    columnWidth = 254;
                }
                Integer maxColumnWidth = maxColumnWidthMap.get(cell.getColumnIndex());
                if (maxColumnWidth == null || columnWidth > maxColumnWidth) {
                    maxColumnWidthMap.put(cell.getColumnIndex(), columnWidth);
                    writeSheetHolder.getSheet().setColumnWidth(cell.getColumnIndex(), columnWidth * 256);
                }
            }
        }
    }

    private int dataLength(List<WriteCellData<?>> cellDataList, Cell cell, Boolean isHead) {
        if (isHead) {
            return cell.getStringCellValue().getBytes().length;
        } else {
            WriteCellData<?> cellData = cellDataList.get(0);
            CellDataTypeEnum typeEnum = cellData.getType();
            if (typeEnum == null) {
                return -1;
            } else {
                return switch (typeEnum) {
                    case STRING -> cellData.getStringValue().getBytes().length + 1;
                    case BOOLEAN -> cellData.getBooleanValue().toString().getBytes().length;
                    case NUMBER -> cellData.getNumberValue().toString().getBytes().length * 2;
                    default -> -1;
                };
            }
        }
    }
}
