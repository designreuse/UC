package com.yealink.uc.web.platform;

import javax.inject.Inject;

import com.yealink.uc.web.SpringTestInitializer;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * @author neo
 */
public class ConfigurationTest extends SpringTestInitializer {
    @Before
    public void prepareRequestScope() {
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(new MockHttpServletRequest()));
    }

    @Inject
    ApplicationContext applicationContext;

    @Test
    public void contextShouldBeInitialized() {
        Assert.assertNotNull(applicationContext);
    }

    @Test
    public void verifyBeanConfiguration() {
        String[] beanNames = applicationContext.getBeanDefinitionNames();
        for (String beanName : beanNames) {
            try {
                applicationContext.getBean(beanName);
            } catch (Exception e) {
                throw new RuntimeException("Failed bean name " + beanName, e);
            }
        }
    }

}
