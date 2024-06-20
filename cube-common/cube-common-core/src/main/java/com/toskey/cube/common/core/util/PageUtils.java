package com.toskey.cube.common.core.util;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.toskey.cube.common.core.base.BaseEntity;
import com.toskey.cube.common.core.base.BaseEntityMapper;
import com.toskey.cube.common.core.base.PageData;
import com.toskey.cube.common.core.exception.BusinessException;
import com.toskey.cube.common.core.exception.FrameworkException;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

/**
 * PageUtils
 *
 * @author toskey
 * @version 1.0.0
 */
public class PageUtils {

    private static final String PAGE_NUMBER = "pageNumber";

    private static final String PAGE_SIZE = "pageSize";

    private static final String ORDER_BY = "orderBy";

    private static final String SORTED = "sorted";

    private static final String SORTED_ASC = "asc";

    private static final String SORTED_DESC = "desc";

    public static <T> IPage<T> buildPage() {
        HttpServletRequest request = WebUtils.getRequest();
        if (request != null) {
            String pageNumber = request.getParameter(PAGE_NUMBER);
            String pageSize = request.getParameter(PAGE_SIZE);
            if (StringUtils.isNotEmpty(pageNumber) && StringUtils.isNotEmpty(pageSize)) {
                int iNumber = handlePageNumber(pageNumber);
                int iSize = handlePageSize(pageSize);

                Page<T> page = new Page<>(iNumber, iSize);
                String orderBy = request.getParameter(ORDER_BY);
                if (StringUtils.isNotEmpty(orderBy)) {
                    orderBy = StringUtils.toUnderScoreCase(orderBy);

                    String sorted = request.getParameter(SORTED);
                    if (StringUtils.isNotEmpty(sorted)) {
                        if (!StringUtils.equals(SORTED_ASC, sorted) && !StringUtils.equals(SORTED_DESC, sorted)) {
                            page.addOrder(OrderItem.asc(orderBy));
                        } else {
                            if (StringUtils.equals(SORTED_ASC, sorted)) {
                                page.addOrder(OrderItem.asc(orderBy));
                            } else if (StringUtils.equals(SORTED_DESC, sorted)) {
                                page.addOrder(OrderItem.desc(orderBy));
                            }
                        }
                    } else {
                        page.addOrder(OrderItem.asc(orderBy));
                    }
                }
                return page;
            }
        }
        throw new FrameworkException("获取分页参数错误.");
    }

    public static <T> IPage<T> buildPage(int pageNumber, int pageSize) {
        int iNumber = handlePageNumber(pageNumber);
        int iSize = handlePageSize(pageSize);
        return new Page<>(iNumber, iSize);
    }

    @SuppressWarnings("unchecked")
    public static <T> IPage<T> buildPage(int pageNumber, int pageSize, boolean sort, String orderBy, String sorted) {
        Page<T> page = (Page<T>) buildPage(pageNumber, pageSize);
        if (sort) {
            orderBy = StringUtils.toUnderScoreCase(orderBy);
            if (StringUtils.isNotEmpty(sorted)) {
                if (!StringUtils.equals(SORTED_ASC, sorted) && !StringUtils.equals(SORTED_DESC, sorted)) {
                    page.addOrder(OrderItem.asc(orderBy));
                } else {
                    if (StringUtils.equals(SORTED_ASC, sorted)) {
                        page.addOrder(OrderItem.asc(orderBy));
                    } else if (StringUtils.equals(SORTED_DESC, sorted)) {
                        page.addOrder(OrderItem.desc(orderBy));
                    }
                }
            } else {
                page.addOrder(OrderItem.asc(orderBy));
            }
        }
        return page;
    }

    public static <E> PageData<E> buildPageData(IPage<E> page) {
        PageData<E> pageData = new PageData<>();
        pageData.setPageNumber(page.getCurrent());
        pageData.setPageSize(page.getSize());
        pageData.setTotal(page.getTotal());
        pageData.setData(page.getRecords());
        return pageData;
    }

    public static <E> PageData<E> buildPageData(IPage<?> page, List<E> dataList) {
        PageData<E> pageData = new PageData<>();
        pageData.setPageNumber(page.getCurrent());
        pageData.setPageSize(page.getSize());
        pageData.setTotal(page.getTotal());
        pageData.setData(dataList);
        return pageData;
    }

    public static <E extends BaseEntityMapper, T extends BaseEntity> PageData<E> buildPageData(IPage<T> page, List<T> dataList, Class<E> cls) {
        List<E> list = EntityUtils.toMapper(dataList, cls);
        return buildPageData(page, list);
    }

    /**
     * 处理pageNumber参数
     *
     * @param pageNumber
     * @return
     */
    public static int handlePageNumber(String pageNumber) {
        if (StringUtils.isBlank(pageNumber)) {
            pageNumber = "1";
        }
        return Integer.parseInt(pageNumber);
    }

    /**
     * 处理pageNumber参数
     *
     * @param pageNumber
     * @return
     */
    public static int handlePageNumber(Integer pageNumber) {
        if (pageNumber == null || pageNumber <= 0) {
            pageNumber = 1;
        }
        return pageNumber;
    }

    /**
     * 处理pageSize参数
     *
     * @param pageSize
     * @return
     */
    public static int handlePageSize(String pageSize) {
        if (StringUtils.isBlank(pageSize)) {
            pageSize = "10";
        }
        return Integer.parseInt(pageSize);
    }

    /**
     * 处理pageSize参数
     *
     * @param pageSize
     * @return
     */
    public static int handlePageSize(Integer pageSize) {
        if (pageSize == null || pageSize <= 0) {
            pageSize = 10;
        }
        return pageSize;
    }
}
