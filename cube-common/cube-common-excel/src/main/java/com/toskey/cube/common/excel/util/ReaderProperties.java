package com.toskey.cube.common.excel.util;

import com.alibaba.excel.event.AnalysisEventListener;
import com.toskey.cube.common.excel.listener.ExcelReadListener;
import lombok.Getter;
import lombok.Setter;

/**
 * Excel读取参数
 *
 * @author toskey
 * @version 1.0.0
 */
@Getter
@Setter
public class ReaderProperties {

    private AnalysisEventListener readListener = new ExcelReadListener();

}
