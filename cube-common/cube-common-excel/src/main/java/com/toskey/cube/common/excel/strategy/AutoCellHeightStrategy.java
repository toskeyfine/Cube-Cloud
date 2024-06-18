package com.toskey.cube.common.excel.strategy;

import com.alibaba.excel.write.style.row.AbstractRowHeightStyleStrategy;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;

import java.util.Iterator;

/**
 * 单元格自适应高度策略
 *
 * @author toskey
 * @version 1.0.0
 */
public class AutoCellHeightStrategy extends AbstractRowHeightStyleStrategy {

    private static final Integer DEFAULT_ROW_HEIGHT = 300;

    @Override
    protected void setHeadColumnHeight(Row row, int relativeRowIndex) {

    }

    @Override
    protected void setContentColumnHeight(Row row, int relativeRowIndex) {
        Iterator<Cell> cellIterator = row.cellIterator();
        if (!cellIterator.hasNext()) {
            return;
        }
        int maxHeight = 1;
        while (cellIterator.hasNext()) {
            Cell cell = cellIterator.next();
            if (cell.getCellType() == CellType.STRING) {
                String value = cell.getStringCellValue();
                int len = value.length();
                int num = 0;
                if (len > 50) {
                    num = len % 50 > 0 ? len / 50 : len / 2 - 1;
                }
                if (num > 0) {
                    for (int i = 0; i < num; i++) {
                        value = value.substring(0, (i + 1) * 50 + i) + "\n" + value.substring((i + 1) * 50 + i, len + i);
                    }
                }
                if (value.contains("\n")) {
                    int length = value.split("\n").length;
                    maxHeight = Math.max(maxHeight, length) + 1;
                }
            }
            row.setHeight((short) ((maxHeight) * DEFAULT_ROW_HEIGHT));
        }
    }
}
