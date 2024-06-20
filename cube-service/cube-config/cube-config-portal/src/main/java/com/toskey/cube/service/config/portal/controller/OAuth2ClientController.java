package com.toskey.cube.service.config.portal.controller;

import com.toskey.cube.service.config.business.service.OAuth2ClientService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * OAuth2ClientController
 *
 * @author toskey
 * @version 1.0.0
 */
@RestController
@RequestMapping("/config/client")
public class OAuth2ClientController {

    private final OAuth2ClientService clientService;

    public OAuth2ClientController(OAuth2ClientService clientService) {
        this.clientService = clientService;
    }

}
