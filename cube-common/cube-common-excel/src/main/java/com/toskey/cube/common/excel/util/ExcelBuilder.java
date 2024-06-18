package com.toskey.cube.common.excel.util;

import com.alibaba.excel.event.AnalysisEventListener;
import com.toskey.cube.common.excel.listener.ExcelReadListener;
import jakarta.servlet.http.HttpServletResponse;

import java.io.OutputStream;
import java.util.List;
import java.util.function.Supplier;

/**
 * Excel工具构造器
 *
 * @author toskey
 * @version 1.0.0
 */
public class ExcelBuilder {

    public static final String MODE_READ = "read";
    public static final String MODE_WRITE = "write";

    private String mode = MODE_WRITE;

    private WriterProperties writerProperties;

    private ReaderProperties readerProperties;

    public ExcelBuilder() {
        this.writerProperties = new WriterProperties();
        this.readerProperties = new ReaderProperties();
    }

    public ExcelBuilder write() {
        this.mode = MODE_WRITE;
        return this;
    }

    public ExcelBuilder read() {
        this.mode = MODE_READ;
        return this;
    }

    public ExcelBuilder template(Supplier<String> supplier) {
        this.writerProperties.setTemplate(supplier.get());
        return this;
    }

    public ExcelBuilder fillMode() {
        this.writerProperties.setFillMode(true);
        return this;
    }

    public ExcelBuilder single(Supplier<ExcelSheet> supplier) {
        writerProperties.addSheet(supplier.get());
        return this;
    }

    public ExcelBuilder single(ExcelSheet sheet) {
        writerProperties.addSheet(sheet);
        return this;
    }

    public ExcelBuilder many(Customizer<List<ExcelSheet>> customizer) {
        customizer.customize(this.writerProperties.getSheets());
        return this;
    }

    public ExcelBuilder many(List<ExcelSheet> sheets) {
        this.writerProperties.setSheets(sheets);
        return this;
    }

    public ExcelBuilder outputName(Supplier<String> supplier) {
        this.writerProperties.setOutputName(supplier.get());
        return this;
    }

    public ExcelBuilder outputStream(Supplier<OutputStream> supplier) {
        this.writerProperties.setOutputStream(supplier.get());
        return this;
    }

    public ExcelBuilder response(Supplier<HttpServletResponse> supplier) {
        this.writerProperties.setResponse(supplier.get());
        return this;
    }

    public ExcelBuilder defaultReadListener(Customizer<ExcelReadListener> customizer) {
        customizer.customize((ExcelReadListener) readerProperties.getReadListener());
        return this;
    }

    public ExcelBuilder readListener(Supplier<AnalysisEventListener> supplier) {
        this.readerProperties.setReadListener(supplier.get());
        return this;
    }

    public Excel build() {
        if (this.mode.equals(MODE_READ)) {
            return new ExcelReader(this.readerProperties);
        } else if (this.mode.equals(MODE_WRITE)) {
            return new ExcelWriter(this.writerProperties);
        } else {
            return null;
        }
    }

}
