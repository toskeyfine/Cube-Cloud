package com.toskey.cube.common.sentinel.component;

import com.alibaba.cloud.sentinel.feign.SentinelContractHolder;
import com.alibaba.cloud.sentinel.feign.SentinelFeign;
import com.alibaba.cloud.sentinel.feign.SentinelInvocationHandler;
import feign.Contract;
import feign.Feign;
import feign.InvocationHandlerFactory;
import feign.Target;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.cloud.openfeign.FeignClientFactory;
import org.springframework.cloud.openfeign.FeignClientFactoryBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * SentinelFeignEnhancer
 *
 * @author lis
 * @version 1.0
 * @description TODO
 * @date 2024/6/19 14:35
 */
public class EnhanceSentinelFeign {

    private static final String FEIGN_LAZY_ATTR_RESOLUTION = "spring.cloud.openfeign.lazy-attributes-resolution";

    private EnhanceSentinelFeign() {

    }

    public static EnhanceSentinelFeign.Builder builder() {
        return new EnhanceSentinelFeign.Builder();
    }

    public static final class Builder extends Feign.Builder
            implements ApplicationContextAware {

        private Contract contract = new Contract.Default();

        private ApplicationContext applicationContext;

        private FeignClientFactory feignClientFactory;

        @Override
        public Feign.Builder invocationHandlerFactory(
                InvocationHandlerFactory invocationHandlerFactory) {
            throw new UnsupportedOperationException();
        }

        @Override
        public EnhanceSentinelFeign.Builder contract(Contract contract) {
            this.contract = contract;
            return this;
        }

        @Override
        public Feign internalBuild() {
            super.invocationHandlerFactory(new InvocationHandlerFactory() {
                @Override
                public InvocationHandler create(Target target,
                                                Map<Method, MethodHandler> dispatch) {
                    GenericApplicationContext gctx = (GenericApplicationContext) EnhanceSentinelFeign.Builder.this.applicationContext;
                    BeanDefinition def = gctx.getBeanDefinition(target.type().getName());
                    FeignClientFactoryBean feignClientFactoryBean;

                    // If you need the attributes to be resolved lazily, set the property value to true.
                    Boolean isLazyInit = applicationContext.getEnvironment()
                            .getProperty(FEIGN_LAZY_ATTR_RESOLUTION, Boolean.class, false);
                    if (isLazyInit) {
                        /*
                         * Due to the change of the initialization sequence,
                         * BeanFactory.getBean will cause a circular dependency. So
                         * FeignClientFactoryBean can only be obtained from BeanDefinition
                         */
                        feignClientFactoryBean = (FeignClientFactoryBean) def
                                .getAttribute("feignClientsRegistrarFactoryBean");
                    }
                    else {
                        feignClientFactoryBean = (FeignClientFactoryBean) applicationContext
                                .getBean("&" + target.type().getName());
                    }
                    Class fallback = feignClientFactoryBean.getFallback();
                    Class fallbackFactory = feignClientFactoryBean.getFallbackFactory();
                    String beanName = feignClientFactoryBean.getContextId();
                    if (!StringUtils.hasText(beanName)) {
                        beanName = (String) getFieldValue(feignClientFactoryBean, "name");
                    }

                    Object fallbackInstance;
                    FallbackFactory fallbackFactoryInstance;
                    // check fallback and fallbackFactory properties
                    if (void.class != fallback) {
                        fallbackInstance = getFromContext(beanName, "fallback", fallback,
                                target.type());
                        return new RestSentinelInvocationHandler(target, dispatch,
                                new FallbackFactory.Default(fallbackInstance));
                    }
                    if (void.class != fallbackFactory) {
                        fallbackFactoryInstance = (FallbackFactory) getFromContext(
                                beanName, "fallbackFactory", fallbackFactory,
                                FallbackFactory.class);
                        return new RestSentinelInvocationHandler(target, dispatch,
                                fallbackFactoryInstance);
                    }

                    return new RestSentinelInvocationHandler(target, dispatch);
                }

                private Object getFromContext(String name, String type,
                                              Class fallbackType, Class targetType) {
                    Object fallbackInstance = feignClientFactory.getInstance(name,
                            fallbackType);
                    if (fallbackInstance == null) {
                        throw new IllegalStateException(String.format(
                                "No %s instance of type %s found for feign client %s",
                                type, fallbackType, name));
                    }
                    // when fallback is a FactoryBean, should determine the type of instance
                    if (fallbackInstance instanceof FactoryBean<?> factoryBean) {
                        try {
                            fallbackInstance = factoryBean.getObject();
                        }
                        catch (Exception e) {
                            throw new IllegalStateException(type + " create fail", e);
                        }
                        fallbackType = fallbackInstance.getClass();
                    }

                    if (!targetType.isAssignableFrom(fallbackType)) {
                        throw new IllegalStateException(String.format(
                                "Incompatible %s instance. Fallback/fallbackFactory of type %s is not assignable to %s for feign client %s",
                                type, fallbackType, targetType, name));
                    }
                    return fallbackInstance;
                }
            });

            super.contract(new SentinelContractHolder(contract));
            return super.internalBuild();
        }

        private Object getFieldValue(Object instance, String fieldName) {
            Field field = ReflectionUtils.findField(instance.getClass(), fieldName);
            field.setAccessible(true);
            try {
                return field.get(instance);
            }
            catch (IllegalAccessException e) {
                // ignore
            }
            return null;
        }

        @Override
        public void setApplicationContext(ApplicationContext applicationContext)
                throws BeansException {
            this.applicationContext = applicationContext;
            feignClientFactory = this.applicationContext.getBean(FeignClientFactory.class);
        }

    }

}
