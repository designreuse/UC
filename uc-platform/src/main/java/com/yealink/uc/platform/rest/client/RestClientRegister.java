package com.yealink.uc.platform.rest.client;

import java.util.List;

import com.google.common.collect.Lists;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;

public class RestClientRegister implements BeanDefinitionRegistryPostProcessor {
    private final Logger logger = LoggerFactory.getLogger(RestClientRegister.class);
    final String serviceURL;
    final List<Class<?>> serviceClasses = Lists.newArrayList();

    public RestClientRegister(String serviceURL) {
        this.serviceURL = serviceURL;
    }

    public RestClientRegister add(Class<?> serviceClass) {
        serviceClasses.add(serviceClass);
        return this;
    }

    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {

    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        for (Class<?> serviceClass : serviceClasses) {
            logger.info("registered api client, class={}, url={}", serviceClass, serviceURL);
            Object serviceClient = new RestClientCreator(serviceURL).build(serviceClass);

            beanFactory.registerSingleton(serviceClass.getName(), serviceClient);
        }
    }
}
