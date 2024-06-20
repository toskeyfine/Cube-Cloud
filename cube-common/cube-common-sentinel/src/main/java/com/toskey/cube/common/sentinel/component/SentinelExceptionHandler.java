package com.toskey.cube.common.sentinel.component;

import com.alibaba.csp.sentinel.adapter.spring.webmvc.callback.BlockExceptionHandler;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.authority.AuthorityException;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeException;
import com.alibaba.csp.sentinel.slots.block.flow.FlowException;
import com.alibaba.csp.sentinel.slots.system.SystemBlockException;
import com.toskey.cube.common.core.base.RestResult;
import com.toskey.cube.common.core.util.ResponseUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;

/**
 * Sentinel异常处理
 *
 * @author toskey
 * @version 1.0.0
 */
public class SentinelExceptionHandler implements BlockExceptionHandler {

    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, BlockException e) throws Exception {
        String exMsg = null;
        if (e instanceof FlowException) {
            exMsg = "访问频繁，请稍候再试";
        } else if (e instanceof DegradeException) {
            exMsg = "系统降级";
        } else if (e instanceof SystemBlockException) {
            exMsg = "系统规则限流或降级";
        } else if (e instanceof AuthorityException) {
            exMsg = "授权规则不通过";
        } else {
            exMsg = "未知限流";
        }
        httpServletResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        ResponseUtils.writeJson(RestResult.failure(exMsg), httpServletResponse);
    }

}
