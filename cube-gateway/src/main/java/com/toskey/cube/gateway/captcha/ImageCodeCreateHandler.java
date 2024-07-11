package com.toskey.cube.gateway.captcha;

import com.anji.captcha.model.vo.CaptchaVO;
import com.anji.captcha.service.CaptchaService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.toskey.cube.common.core.base.RestResult;
import com.toskey.cube.common.core.constant.CommonConstants;
import com.toskey.cube.common.core.util.SpringContextHolder;
import com.toskey.cube.common.core.util.StringUtils;
import jakarta.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.HandlerFunction;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.HashMap;

/**
 * 验证码生成处理器
 *
 * @author toskey
 * @version 1.0.0
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class ImageCodeCreateHandler implements HandlerFunction<ServerResponse> {

    private final ObjectMapper objectMapper;

    @Override
    @SneakyThrows
    public Mono<ServerResponse> handle(@Nonnull ServerRequest serverRequest) {
        return serverRequest.bodyToMono(HashMap.class)
                .flatMap(body -> {
                    CaptchaVO vo = new CaptchaVO();
                    String captchaType = (String) body.get("captchaType");
                    vo.setCaptchaType(StringUtils.isNotBlank(captchaType) ? captchaType : CommonConstants.CAPTCHA_DEFAULT_TYPE);
                    CaptchaService captchaService = SpringContextHolder.getBean(CaptchaService.class);
                    return Mono.just(captchaService.get(vo));
                })
                .flatMap(response ->
                        {
                            try {
                                return ServerResponse.status(HttpStatus.OK)
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .body(BodyInserters.fromValue(objectMapper.writeValueAsString(RestResult.success(response))));
                            } catch (JsonProcessingException e) {
                                throw new RuntimeException(e);
                            }
                        }
                );

    }

}
