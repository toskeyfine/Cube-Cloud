package com.toskey.cube.common.excel.enhancer;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Set;

/**
 * Excel标题头
 *
 * @author toskey
 * @version 1.0.0
 */
@Getter
@Setter
public class ExcelHeader {

    private List<List<String>> header;

    private Set<String> ignores;

}
