package com.toskey.cube.common.cache.aspect;

import com.toskey.cube.common.cache.annotation.CacheClearDelay;
import com.toskey.cube.common.cache.event.ClearCacheEvent;
import com.toskey.cube.common.core.util.SpelParser;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.context.ApplicationEventPublisher;

import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

/**
 * redis缓存延迟双删AOP实现
 *
 * @author lis
 * @date 2023/2/23 15:33
 */
@Aspect
@RequiredArgsConstructor
public class CacheClearDelayAspect {

    private final ApplicationEventPublisher applicationEventPublisher;

    @SneakyThrows
    @Around("@annotation(com.toskey.cube.common.cache.annotation.CacheClearDelay)")
    public Object doCacheClearDelay(ProceedingJoinPoint point) {
        Signature signature = point.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method targetMethod = methodSignature.getMethod();
        CacheClearDelay annotation = targetMethod.getAnnotation(CacheClearDelay.class);
        Object ret =  point.proceed();

        String key = annotation.key();
        // parse SPEL
        if (StringUtils.startsWith(key, "#{") && StringUtils.endsWith(key, "}")) {
            key = (String) SpelParser.parse(point, key);
        }

        if (StringUtils.isBlank(key)) {
            return ret;
        }

        long delayTime = annotation.delay();
        TimeUnit timeUnit = annotation.timeUnit();
        String keyPrefix = annotation.prefix();

        final String cacheKey = keyPrefix + key;
        applicationEventPublisher.publishEvent(ClearCacheEvent.of(cacheKey, delayTime, timeUnit));

        return ret;
    }

}
