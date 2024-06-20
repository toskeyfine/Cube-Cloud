package com.toskey.cube.common.cache.aspect;

import com.toskey.cube.common.cache.annotation.CLock;
import com.toskey.cube.common.cache.component.CubeLock;
import com.toskey.cube.common.cache.component.IDistributedLock;
import com.toskey.cube.common.core.util.SpelParser;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.reflect.Method;

/**
 * 分布式锁注解切片
 *
 * @author toskey
 * @version 1.0.0
 */
@Aspect
public class CLockAspect {

    private final IDistributedLock distributedLock;

    public CLockAspect(IDistributedLock distributedLock) {
        this.distributedLock = distributedLock;
    }

    @Around("@annotation(com.toskey.cube.common.cache.annotation.CLock)")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        Signature signature = point.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method targetMethod = methodSignature.getMethod();
        CLock cLock = targetMethod.getAnnotation(CLock.class);

        String key = cLock.key();
        // 解析SPEL
        if (key.startsWith("#")) {
            Object spelKey = SpelParser.parse(point, key);
            if (spelKey instanceof String keyStr) {
                key = keyStr;
            } else {
                throw new RuntimeException("SPEL表达式类型异常.");
            }
        }
        String prefix = cLock.prefix();
        // 拼接完整Key
        if (StringUtils.isNotBlank(prefix)) {
            key = String.format("%s:%s", prefix, key);
        }
        CubeLock lock = null;
        try {
            if (cLock.tryLock()) {
                if (cLock.tryTime() <= 0) {
                    throw new RuntimeException("@ZxLock tryTime必须大于0");
                }
                lock = this.distributedLock.tryLock(key, cLock.tryTime(), cLock.expires(), cLock.unit(), cLock.fair());
            } else {
                lock = this.distributedLock.lock(key, cLock.expires(), cLock.unit(), cLock.fair());
            }
            if (lock == null) {
                throw new RuntimeException("Duplicate request for method still in process");
            }
            return point.proceed();
        } catch (Exception e) {
            throw e;
        } finally {
            this.distributedLock.unlock(lock);
        }

    }
}
