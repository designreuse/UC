package com.yealink.uc.api;


import java.util.ResourceBundle;
import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import com.yealink.uc.platform.filter.TrackLogFilter;
import com.yealink.uc.platform.interceptor.RequestContextInterceptor;
import com.yealink.uc.platform.rest.client.RestClientRegister;
import com.yealink.uc.service.modules.account.api.AccountRESTService;
import com.yealink.uc.service.modules.businessaccount.api.BusinessAccountRESTService;
import com.yealink.uc.service.modules.login.api.LoginRESTService;
import com.yealink.uc.service.modules.org.api.OrgRESTService;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import org.springframework.web.servlet.support.AbstractDispatcherServletInitializer;

/**
 * @author ChNan
 */
@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "com.yealink.uc.api")
@EnableAspectJAutoProxy
public class AppContextInitializer extends WebMvcConfigurerAdapter implements WebApplicationInitializer {
    @Override
    public void onStartup(final ServletContext servletContext) throws ServletException {
        AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
        context.register(AppContextInitializer.class);
        registerDispatcherServlet(servletContext, context);
        registerEncodingFilter(servletContext);
        registerRequestLogFilter(servletContext);
    }

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertyLoader() {
        PropertySourcesPlaceholderConfigurer placeholderConfigurer = new PropertySourcesPlaceholderConfigurer();
        placeholderConfigurer.setLocations(
            new ClassPathResource("/config/site.properties"),
            new ClassPathResource("/config/mail.properties"));
        return placeholderConfigurer;
    }

    @Bean
    public SessionLocaleResolver localeResolver() { // Set locale
        return new SessionLocaleResolver();
    }

    @Bean
    public MessageSource messageSource() {  // International config
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename("classpath:/i18n/message");
        return messageSource;
    }

    @Override
    public Validator getValidator() {
        LocalValidatorFactoryBean validator = new LocalValidatorFactoryBean();
        validator.setValidationMessageSource(messageSource());
        validator.afterPropertiesSet();
        return validator;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new RequestContextInterceptor());
    }

    private void registerDispatcherServlet(final ServletContext servletContext, final AnnotationConfigWebApplicationContext context) {
        ServletRegistration.Dynamic registration = servletContext.addServlet(AbstractDispatcherServletInitializer.DEFAULT_SERVLET_NAME, new DispatcherServlet(context));
        registration.addMapping("/");
        registration.setLoadOnStartup(1);
    }

    private void registerEncodingFilter(final ServletContext servletContext) {
        FilterRegistration.Dynamic encodingFilter = servletContext.addFilter("encodingFilter", new CharacterEncodingFilter());
        encodingFilter.setInitParameter("encoding", "UTF-8");
        encodingFilter.setInitParameter("forceEncoding", "true");
        encodingFilter.addMappingForUrlPatterns(null, true, "/*");
    }

    private void registerRequestLogFilter(final ServletContext servletContext) {
        FilterRegistration.Dynamic requestLogFilter = servletContext.addFilter("trackLogFilter", new TrackLogFilter());
        requestLogFilter.addMappingForUrlPatterns(null, true, "/*");
    }

    @Bean
    public RestClientRegister restClientsBuilder() {
        ResourceBundle bundle = ResourceBundle.getBundle("config.service");
        return new RestClientRegister(bundle.getString("service.url"))
            .add(LoginRESTService.class)
            .add(OrgRESTService.class)
            .add(AccountRESTService.class)
            .add(BusinessAccountRESTService.class);
    }


}
