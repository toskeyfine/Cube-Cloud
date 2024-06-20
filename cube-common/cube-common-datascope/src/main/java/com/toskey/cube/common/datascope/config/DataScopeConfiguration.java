package com.toskey.cube.common.datascope.config;

import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.toskey.cube.common.datascope.component.DataScopeInterceptor;
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
    public DataScopeInterceptor dataScopeInterceptor() {
        return new DataScopeInterceptor();
    }

}
