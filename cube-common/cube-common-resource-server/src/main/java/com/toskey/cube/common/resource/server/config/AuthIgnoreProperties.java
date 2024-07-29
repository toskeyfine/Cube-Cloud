package com.toskey.cube.common.resource.server.config;

import com.toskey.cube.common.core.util.SpringContextHolder;
import com.toskey.cube.common.core.util.StringUtils;
import com.toskey.cube.common.resource.server.annotation.AuthIgnore;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.condition.PathPatternsRequestCondition;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * AuthIgnoreProperties
 *
 * @author toskey
 * @version 1.0.0
 */
@Setter
@Getter
@ConfigurationProperties(prefix = "cube.resource-server.ignore")
public class AuthIgnoreProperties implements InitializingBean {

    private static final Pattern URL_PATTERN = Pattern.compile("\\{(.*?)\\}");

    private static final Pattern VAR_PATTERN = Pattern.compile("\\$(\\d+)");

    private List<String> urls = new ArrayList<>();

    @Override
    public void afterPropertiesSet() throws Exception {
        // 处理FeignApi方法注解
        RequestMappingHandlerMapping handlerMapping = SpringContextHolder.getBean("requestMappingHandlerMapping");
        Map<RequestMappingInfo, HandlerMethod> handlerMethodMap = handlerMapping.getHandlerMethods();
        handlerMethodMap.forEach((k, v) -> {
            AuthIgnore methodAnno = AnnotationUtils.findAnnotation(v.getMethod(), AuthIgnore.class);
            if (methodAnno != null) {
                PathPatternsRequestCondition condition = k.getPathPatternsCondition();
                if (condition != null) {
                    condition.getPatternValues().forEach(url -> {
                        urls.add(handlePathPattern(url));
                    });
                }
            }
            AuthIgnore clazzAnno = AnnotationUtils.findAnnotation(v.getBeanType(), AuthIgnore.class);
            if (clazzAnno != null) {
                PathPatternsRequestCondition condition = k.getPathPatternsCondition();
                if (condition != null) {
                    condition.getPatternValues().forEach(url -> {
                        urls.add(handlePathPattern(url));
                    });
                }
            }
        });
    }

    private String handlePathPattern(String path) {
        if (StringUtils.isEmpty(path)) {
            return path;
        }
        final Matcher matcher = URL_PATTERN.matcher(path);
        boolean result = matcher.find();
        if (result) {
            final StringBuilder sb = new StringBuilder();
            do {
                matcher.appendReplacement(sb, "*");
                result = matcher.find();
            } while (result);
            matcher.appendTail(sb);
            return sb.toString();
        }
        return path;
    }
}
