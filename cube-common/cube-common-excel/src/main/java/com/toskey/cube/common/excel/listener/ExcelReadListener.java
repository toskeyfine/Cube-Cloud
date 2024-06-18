package com.toskey.cube.common.excel.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 默认读取监听器
 *
 * @author toskey
 * @version 1.0.0
 */
@Slf4j
public class ExcelReadListener<T> extends AnalysisEventListener<T> {

    private final List<T> data = new ArrayList<>();

    /**
     * 当前行
     */
    private long currentRow = 0;

    /**
     * 总行数
     */
    private long totalRow = -1;

    private ReadAfter<T> readAfter = ReadAfter.withDefault();

    private ReadError readError = ReadError.withDefault();

    private static final Validator validator;

    static {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    public List<T> data() {
        return this.data;
    }

    private void onError(ExcelError error) {
        this.readError.doError(error);
    }

    public ExcelReadListener<T> onError(ReadError readError) {
        this.readError = readError;
        return this;
    }

    public ExcelReadListener<T> onSuccess(ReadAfter<T> readAfter) {
        this.readAfter = readAfter;
        return this;
    }

    @Override
    public void invoke(T data, AnalysisContext context) {
        this.totalRow = context.readSheetHolder().getApproximateTotalRowNumber();
        currentRow++;
        Set<ConstraintViolation<T>> violations = validator.validate(data);
        if (!violations.isEmpty()) {
            Set<String> errorMessages = violations.stream()
                    .map(ConstraintViolation::getMessage)
                    .collect(Collectors.toSet());
            this.onError(new ExcelError(currentRow, errorMessages));
        } else {
            this.data.add(data);
        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        this.readAfter.doAfter(this.totalRow, this.data());
    }
}
