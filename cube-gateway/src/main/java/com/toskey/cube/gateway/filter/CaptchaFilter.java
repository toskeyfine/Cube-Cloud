package com.toskey.cube.gateway.filter;

import com.anji.captcha.model.vo.CaptchaVO;
import com.anji.captcha.service.CaptchaService;
import com.toskey.cube.common.core.constant.CommonConstants;
import com.toskey.cube.common.core.exception.FrameworkException;
import com.toskey.cube.common.core.util.SpringContextHolder;
import com.toskey.cube.gateway.config.CaptchaProperties;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 公共验证码过滤器
 *
 * @author toskey
 * @version 1.0.0
 */
@Component
@EnableConfigurationProperties(CaptchaProperties.class)
public class CaptchaFilter extends AbstractGatewayFilterFactory {

    private final CaptchaProperties captchaProperties;

    public CaptchaFilter(CaptchaProperties captchaProperties) {
        this.captchaProperties = captchaProperties;
    }

    @Override
    public GatewayFilter apply(Object config) {
        return (exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();

            List<CaptchaProperties.CaptchaUrl> urls = captchaProperties.getExcludeUrls();
            if (urls != null) {
                urls.stream()
                        .filter(u -> StringUtils.equals(u.getUrl(), request.getURI().getPath()))
                        .findFirst()
                        .ifPresent(url -> {
                            if (url.isEnabled()) {
                                String code = request.getQueryParams().getFirst("code");
                                if (StringUtils.isBlank(code)) {
                                    throw new FrameworkException("验证码不能为空");
                                }
                                CaptchaService captchaService = SpringContextHolder.getBean(CaptchaService.class);
                                CaptchaVO vo = new CaptchaVO();
                                vo.setCaptchaVerification(code);
                                String captchaType = request.getQueryParams().getFirst("captchaType");
                                vo.setCaptchaType(StringUtils.isNotBlank(captchaType) ? captchaType : CommonConstants.CAPTCHA_DEFAULT_TYPE);
                                if (!captchaService.verification(vo).isSuccess()) {
                                    throw new FrameworkException("验证码错误");
                                }
                            }
                        });
            }
            return chain.filter(exchange);
        };
    }
}
