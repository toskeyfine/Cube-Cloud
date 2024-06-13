package com.toskey.common.tenant.component;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.web.servlet.filter.OrderedFilter;
import org.springframework.core.Ordered;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;

/**
 * TenantContextFilter
 *
 * @author lis
 * @version 1.0
 * @description TODO
 * @date 2024/6/7 16:23
 */
public class TenantContextFilter extends GenericFilterBean implements OrderedFilter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        setTenantContext(servletRequest);

        filterChain.doFilter(servletRequest, servletResponse);
        TenantContextHolder.clear();
    }

    private void setTenantContext(ServletRequest servletRequest) {
        String headerValue = ((HttpServletRequest) servletRequest).getHeader("X-TENANT-ID");
        if (StringUtils.isNotBlank(headerValue)) {
            TenantContextHolder.setContext(TenantContext.of(headerValue));
        } else {
            TenantContextHolder.setContext(TenantContext.of("1"));
        }
    }

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE;
    }
}
