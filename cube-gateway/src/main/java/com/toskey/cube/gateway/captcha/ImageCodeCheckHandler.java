package com.toskey.cube.gateway.captcha;

import com.anji.captcha.model.common.ResponseModel;
import com.anji.captcha.model.vo.CaptchaVO;
import com.anji.captcha.service.CaptchaService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.toskey.cube.common.core.base.RestResult;
import com.toskey.cube.common.core.constant.CommonConstants;
import com.toskey.cube.common.core.exception.BusinessException;
import com.toskey.cube.common.core.util.SpringContextHolder;
import com.toskey.cube.common.core.util.StringUtils;
import com.toskey.cube.service.config.interfaces.dto.ConfigDTO;
import com.toskey.cube.service.config.interfaces.service.RemoteConfigService;
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
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * 验证码校验处理器
 *
 * @author toskey
 * @version 1.0.0
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class ImageCodeCheckHandler implements HandlerFunction<ServerResponse> {

    private final ObjectMapper objectMapper;

    private final RemoteConfigService remoteConfigService;

    @SneakyThrows
    @Override
    public Mono<ServerResponse> handle(@Nonnull ServerRequest request) {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Future<RestResult<ConfigDTO>> future = executorService.submit(() -> remoteConfigService.get("sys:login:captcha_type"));
        RestResult<ConfigDTO> result = future.get();
        executorService.shutdown();
        if (!Objects.nonNull(result) || result.getCode() != 0) {
            throw new RuntimeException("获取配置信息失败");
        }
        return request.bodyToMono(HashMap.class)
                .flatMap(map -> {
                    CaptchaVO vo = new CaptchaVO();
                    vo.setPointJson((String) map.get("pointJson"));
                    vo.setToken((String) map.get("token"));
                    vo.setCaptchaType(StringUtils.isNotEmpty(result.getData().getValue()) ? result.getData().getValue() : CommonConstants.CAPTCHA_DEFAULT_TYPE);
                    return Mono.just(vo);
                }).flatMap(captcha -> {
                    CaptchaService captchaService = SpringContextHolder.getBean(CaptchaService.class);
                    ResponseModel responseModel = captchaService.check(captcha);
                    return Mono.just(responseModel);
                }).flatMap(response -> {
                    try {
                        return ServerResponse.status(HttpStatus.OK)
                                .contentType(MediaType.APPLICATION_JSON)
                                .body(BodyInserters.fromValue(objectMapper.writeValueAsString(RestResult.success(response))));
                    } catch (JsonProcessingException e) {
                        throw new BusinessException(e.getMessage());
                    }
                });

    }

}
