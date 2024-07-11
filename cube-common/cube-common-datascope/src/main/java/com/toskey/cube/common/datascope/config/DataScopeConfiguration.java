package com.toskey.cube.common.datascope.config;

import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.toskey.cube.common.datascope.component.DataScopeInterceptor;
import com.toskey.cube.service.sas.interfaces.service.RemoteRoleService;
import org.springframework.context.annotation.Bean;

/**
 * MybatisPlusConfiguration
 *
 * @author toskey
 * @version 1.0.0
 */
public class DataScopeConfiguration {

    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor(DataScopeInterceptor dataScopeInterceptor) {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();

        interceptor.addInnerInterceptor(dataScopeInterceptor);

        return interceptor;
    }

    @Bean
    public DataScopeInterceptor dataScopeInterceptor(RemoteRoleService remoteRoleService) {
        return new DataScopeInterceptor(remoteRoleService);
    }

}
