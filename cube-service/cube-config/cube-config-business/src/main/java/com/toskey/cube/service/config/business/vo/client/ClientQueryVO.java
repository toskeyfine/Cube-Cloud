package com.toskey.cube.service.config.business.vo.client;

import com.toskey.cube.common.core.annotation.EntityMapper;
import com.toskey.cube.common.core.base.BaseEntityMapper;
import com.toskey.cube.service.config.business.domain.entity.OAuth2Client;

/**
 * OAuth2ClientQueryVO
 *
 * @author toskey
 * @version 1.0.0
 */
@EntityMapper(entity = OAuth2Client.class)
public class ClientQueryVO extends BaseEntityMapper {

    private String clientName;

    /**
     * 客户端id
     */
    private String clientId;

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }
}
