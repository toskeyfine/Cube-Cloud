package com.toskey.cube.common.core.base;

import java.io.Serializable;
import java.util.List;

/**
 * PageData
 *
 * @author lis
 * @version 1.0
 * @description TODO
 * @date 2024/6/12 15:42
 */
public class PageData<T> implements Serializable {

    private long pageNumber;

    private long pageSize;

    private long total;

    private List<T> data;

    public long getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(long pageNumber) {
        this.pageNumber = pageNumber;
    }

    public long getPageSize() {
        return pageSize;
    }

    public void setPageSize(long pageSize) {
        this.pageSize = pageSize;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }
}
