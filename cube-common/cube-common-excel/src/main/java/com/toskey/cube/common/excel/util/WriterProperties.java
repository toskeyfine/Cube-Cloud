package com.toskey.cube.common.excel.util;

import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;
import lombok.Setter;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Excel写入参数
 *
 * @author toskey
 * @version 1.0.0
 */
@Getter
@Setter
public class WriterProperties {

    private String template = null;
    private boolean fillMode = Boolean.FALSE;
    private OutputStream outputStream;
    private HttpServletResponse response;
    private List<ExcelSheet> sheets = new ArrayList<>();
    private String outputName;

    public void addSheet(ExcelSheet sheet) {
        sheets.add(sheet);
    }

}
