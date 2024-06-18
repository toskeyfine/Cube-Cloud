package com.toskey.cube.service.config.portal.controller;

import com.toskey.service.config.business.service.OAuth2ClientService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * OAuth2ClientController
 *
 * @author lis
 * @version 1.0
 * @description TODO
 * @date 2024/6/18 15:59
 */
@RestController
@RequestMapping("/config/client")
public class OAuth2ClientController {

    private final OAuth2ClientService clientService;

    public OAuth2ClientController(OAuth2ClientService clientService) {
        this.clientService = clientService;
    }

}
