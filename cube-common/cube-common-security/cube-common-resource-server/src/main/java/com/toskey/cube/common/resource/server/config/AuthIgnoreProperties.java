package com.toskey.cube.common.resource.server.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.List;

/**
 * AuthIgnoreProperties
 *
 * @author lis
 * @version 1.0
 * @description TODO
 * @date 2024/6/6 10:25
 */
@Setter
@Getter
@ConfigurationProperties(prefix = "cube.security.ignore")
public class AuthIgnoreProperties {

    private List<String> ignoreUrls = new ArrayList<>();
}
