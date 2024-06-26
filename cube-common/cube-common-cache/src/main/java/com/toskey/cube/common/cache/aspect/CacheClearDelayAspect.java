package com.toskey.cube.common.cache.aspect;

import com.toskey.cube.common.cache.annotation.CacheClearDelay;
import com.toskey.cube.common.cache.event.ClearCacheEvent;
import com.toskey.cube.common.core.util.SpelParser;
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
 * @author toskey
 * @version 1.0.0
 */
@Aspect
public class CacheClearDelayAspect {

    private final ApplicationEventPublisher applicationEventPublisher;

    public CacheClearDelayAspect(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @Around("@annotation(com.toskey.cube.common.cache.annotation.CacheClearDelay)")
    public Object doCacheClearDelay(ProceedingJoinPoint point) throws Throwable {
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
        applicationEventPublisher.publishEvent(new ClearCacheEvent(cacheKey, delayTime, timeUnit));

        return ret;
    }

}
