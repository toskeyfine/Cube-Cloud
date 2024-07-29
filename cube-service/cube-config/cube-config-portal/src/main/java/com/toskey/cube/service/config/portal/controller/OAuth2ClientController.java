package com.toskey.cube.service.config.portal.controller;

import com.toskey.cube.common.core.base.PageData;
import com.toskey.cube.common.core.base.RestResult;
import com.toskey.cube.service.config.business.service.OAuth2ClientService;
import com.toskey.cube.service.config.business.vo.client.ClientFormVO;
import com.toskey.cube.service.config.business.vo.client.ClientQueryResultVO;
import com.toskey.cube.service.config.business.vo.client.ClientQueryVO;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/page")
    public RestResult<PageData<ClientQueryResultVO>> page(ClientQueryVO query) {
        return RestResult.success(clientService.findPage(query));
    }

    @GetMapping("/{id}")
    public RestResult<ClientQueryResultVO> info(@PathVariable("id") String id) {
        return RestResult.success(clientService.findById(id));
    }

    @PostMapping
    public RestResult<Boolean> save(@RequestBody ClientFormVO form) {
        return RestResult.success(clientService.saveClient(form));
    }

    @PutMapping
    public RestResult<Boolean> update(@RequestBody ClientFormVO form) {
        return RestResult.success(clientService.updateClient(form));
    }

    @DeleteMapping
    public RestResult<Boolean> remove(@RequestParam("ids") String[] ids) {
        return RestResult.success(clientService.removeClient(ids));
    }

}
