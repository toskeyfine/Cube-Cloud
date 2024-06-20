package com.toskey.cube.common.core.util;

import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.server.ServletServerHttpResponse;

import java.nio.charset.StandardCharsets;

/**
 * ResponseUtils
 *
 * @author toskey
 * @version 1.0.0
 */
public final class ResponseUtils {

    private static final MappingJackson2HttpMessageConverter httpMessageConverter = new MappingJackson2HttpMessageConverter();

    @SneakyThrows
    public static void writeJson(Object object, HttpServletResponse response) {
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        httpMessageConverter.write(object, MediaType.APPLICATION_JSON, new ServletServerHttpResponse(response));
    }
}
