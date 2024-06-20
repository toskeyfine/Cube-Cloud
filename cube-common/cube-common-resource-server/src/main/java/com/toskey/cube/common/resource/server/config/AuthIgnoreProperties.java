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
        // 处理FeignApi类注解
        ClassPathScanningCandidateComponentProvider provider = new ClassPathScanningCandidateComponentProvider(false);
        provider.addIncludeFilter(new AnnotationTypeFilter(AuthIgnore.class));
        provider.setResourceLoader(null);
        provider.setResourcePattern("**/*.class");

        Set<BeanDefinition> components = provider.findCandidateComponents("com.toskey.cube");
        for (BeanDefinition bean : components) {
            Class<?> clazz = Class.forName(bean.getBeanClassName());
            RequestMapping requestMapping = AnnotationUtils.findAnnotation(clazz, RequestMapping.class);
            if (requestMapping != null) {
                String[] paths = requestMapping.value();
                Arrays.stream(paths).forEach(path -> urls.add(handlePathPattern(path) + "/**"));
            }
        }
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
        });
    }

    private String handlePathPattern(String path) {
        if (StringUtils.isEmpty(path)) {
            return path;
        }
        final Matcher matcher = URL_PATTERN.matcher(path);
        boolean result = matcher.find();
        if (result) {
            final Set<String> varNums = new TreeSet<>();
            final Matcher matcher1 = VAR_PATTERN.matcher("*");
            while (matcher1.find()) {
                varNums.add(matcher1.group(1));
            }
            final StringBuilder sb = new StringBuilder();
            do {
                String replacement = "*";
                for (final String var : varNums) {
                    final int group = Integer.parseInt(var);
                    replacement = replacement.replace("$" + var, matcher.group(group));
                }
                matcher.appendReplacement(sb, StringUtils.escape(replacement));
                result = matcher.find();
            } while (result);
            matcher.appendTail(sb);
            return sb.toString();
        }
        return path;
    }
}
