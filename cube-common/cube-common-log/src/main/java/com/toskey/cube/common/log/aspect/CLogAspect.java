package com.toskey.cube.common.log.aspect;

import com.toskey.cube.common.tenant.component.TenantContextHolder;
import com.toskey.cube.common.core.util.JsonUtils;
import com.toskey.cube.common.core.util.SpelParser;
import com.toskey.cube.common.core.util.StringUtils;
import com.toskey.cube.common.core.util.WebUtils;
import com.toskey.cube.common.log.annotation.CLog;
import com.toskey.cube.common.log.enums.LogResultType;
import com.toskey.cube.common.log.event.SaveOperationLogEvent;
import com.toskey.cube.common.security.util.SecurityUtils;
import com.toskey.cube.service.log.interfaces.dto.OperationLogDTO;
import jakarta.servlet.http.HttpServletRequest;
import lombok.SneakyThrows;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * CLogAspect
 *
 * @author toskey
 * @version 1.0.0
 */
@Aspect
public class CLogAspect {

    private final ApplicationEventPublisher applicationEventPublisher;

    public CLogAspect(final ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @SneakyThrows
    @Around("@annotation(cLog)")
    public Object around(ProceedingJoinPoint point, CLog cLog) {
        ServletRequestAttributes sra = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        RequestContextHolder.setRequestAttributes(sra, true);

        String value = cLog.value();
        String content = cLog.content();

        if (StringUtils.isNotBlank(content)) {
            String patternStr = "#\\{([^{}]+)\\}";
            Pattern pattern = Pattern.compile(patternStr);
            Matcher matcher = pattern.matcher(content);
            while (matcher.find()) {
                try {
                    String expression = matcher.group(1);
                    Object invokeVal = SpelParser.parse(point, expression);
                    content = content.replace("#{" + expression + "}", String.valueOf(invokeVal));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        OperationLogDTO operationLogDTO = buildLog(value, content, cLog.module());
        Long startTime = System.currentTimeMillis();
        Object obj;
        try {
            obj = point.proceed();
        } catch (Exception e) {
            operationLogDTO.setLogType(LogResultType.ERROR.getValue());
            operationLogDTO.setExMsg(e.getMessage());
            throw e;
        } finally {
            Long endTime = System.currentTimeMillis();
            operationLogDTO.setExecuteTime(endTime - startTime);
            operationLogDTO.setTenantId(TenantContextHolder.getContext().getId());
            operationLogDTO.setCreateTime(LocalDateTime.now());
            applicationEventPublisher.publishEvent(new SaveOperationLogEvent(operationLogDTO));
        }
        return obj;
    }

    private OperationLogDTO buildLog(String title, String content, String module) {
        HttpServletRequest request = ((ServletRequestAttributes) Objects
                .requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
        OperationLogDTO log = new OperationLogDTO();
        log.setTitle(title);
        log.setContent(content);
        log.setModule(module);
        log.setCreateBy(StringUtils.isNotBlank(SecurityUtils.getUserId()) ? SecurityUtils.getUserId() : "anonymous");
        log.setLogType(LogResultType.NORMAL.getValue());
        log.setRequestRemoteHost(WebUtils.getIP(request));
        log.setRequestUri(request.getRequestURI());
        log.setRequestMethod(request.getMethod());
        log.setRequestUserAgent(request.getHeader("user-agent"));
        log.setRequestParams(JsonUtils.toJson(request.getParameterMap()));
        return log;
    }

}
