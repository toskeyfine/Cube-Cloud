package com.toskey.cube.common.excel.util;

import com.toskey.cube.common.excel.enhancer.DefaultWriterSheetBuilderEnhancer;
import com.toskey.cube.common.excel.enhancer.ExcelHeader;
import com.toskey.cube.common.excel.enhancer.WriterSheetBuilderEnhancer;
import lombok.Getter;

import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;

/**
 * Excel Sheet定义
 *
 * @author toskey
 * @version 1.0.0
 */
@Getter
public class ExcelSheet<T> {

    private final Integer sheetNo;

    private final String sheetName;

    private final WriterSheetBuilderEnhancer writerSheetBuilderEnhancer;

    private final List<T> dataList;

    private final Class<T> dataCls;

    private final ExcelHeader header;

    private ExcelSheet(Integer sheetNo, String sheetName,
                       WriterSheetBuilderEnhancer enhancer,
                       List<T> dataList, Class<T> dataCls,
                       ExcelHeader header) {
        this.sheetNo = sheetNo;
        this.sheetName = sheetName;
        this.writerSheetBuilderEnhancer = enhancer;
        this.dataList = dataList;
        this.dataCls = dataCls;
        this.header = header;
    }

    public static class Builder<E> {
        private Integer sheetNo = 0;

        private String sheetName = null;

        private WriterSheetBuilderEnhancer writerSheetBuilderEnhancer;

        private final DefaultWriterSheetBuilderEnhancer defaultSheetEnhancer = new DefaultWriterSheetBuilderEnhancer();

        private List<E> dataList;

        private Class<E> dataCls;

        private ExcelHeader header;

        public Builder<E> sheetNo(Integer sheetNo) {
            this.sheetNo = sheetNo;
            return this;
        }

        public Builder<E> sheetName(String sheetName) {
            this.sheetName = sheetName;
            return this;
        }

        public Builder<E> defaultSheetEnhancer(Customizer<DefaultWriterSheetBuilderEnhancer> customizer) {
            customizer.customize(this.defaultSheetEnhancer);
            return this;
        }

        public Builder<E> sheetEnhancer(Supplier<WriterSheetBuilderEnhancer> supplier) {
            this.writerSheetBuilderEnhancer = supplier.get();
            return this;
        }

        public Builder<E> data(Supplier<List<E>> supplier) {
            this.dataList = supplier.get();
            return this;
        }

        public Builder<E> dataCls(Class<E> dataCls) {
            this.dataCls = dataCls;
            return this;
        }

        public Builder<E> header(ExcelHeader excelHeader) {
            this.header = excelHeader;
            return this;
        }

        public ExcelSheet<E> build() {
            return new ExcelSheet<E>(
                    sheetNo,
                    sheetName,
                    Objects.requireNonNullElse(this.writerSheetBuilderEnhancer, defaultSheetEnhancer),
                    dataList,
                    dataCls,
                    header
            );
        }

    }

}
