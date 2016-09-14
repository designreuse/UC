package com.yealink.uc.web;


import java.util.Properties;
import java.util.ResourceBundle;
import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import com.opensymphony.sitemesh.webapp.SiteMeshFilter;
import com.yealink.uc.platform.config.cache.CacheConfig;
import com.yealink.uc.platform.exception.BusinessHandleException;
import com.yealink.uc.platform.exception.PermissionForbiddenException;
import com.yealink.uc.platform.exception.SessionTimeoutException;
import com.yealink.uc.platform.exception.resolver.ExceptionResolver;
import com.yealink.uc.platform.filter.TrackLogFilter;
import com.yealink.uc.platform.interceptor.RequestContextInterceptor;
import com.yealink.uc.platform.rest.client.RestClientRegister;
import com.yealink.uc.platform.web.permission.PermissionInitializer;
import com.yealink.uc.service.modules.account.api.AccountRESTService;
import com.yealink.uc.service.modules.login.api.LoginRESTService;
import com.yealink.uc.service.modules.org.api.OrgRESTService;
import com.yealink.uc.web.modules.common.interceptor.CSRFTokenRequiredInterceptor;
import com.yealink.uc.web.modules.common.interceptor.CaptchaRequiredInterceptor;
import com.yealink.uc.platform.interceptor.LoginRequiredInterceptor;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import org.springframework.web.servlet.support.AbstractDispatcherServletInitializer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

/**
 * @author ChNan
 */
@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "com.yealink.uc.web")
@EnableTransactionManagement(proxyTargetClass = true)
@EnableAspectJAutoProxy
@Import(CacheConfig.class)
public class AppContextInitializer extends WebMvcConfigurerAdapter implements WebApplicationInitializer {
    @Override
    public void onStartup(final ServletContext servletContext) throws ServletException {
        AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
        context.register(AppContextInitializer.class);
        registerDispatcherServlet(servletContext, context);
        registerEncodingFilter(servletContext);
        registerSiteMeshFilter(servletContext);
        registerRequestLogFilter(servletContext);
    }

    @Bean
    public CommonsMultipartResolver multipartResolver() {
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
        multipartResolver.setMaxUploadSize(10 * 1024 * 1024);
        return multipartResolver;
    }

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertyLoader() {
        PropertySourcesPlaceholderConfigurer placeholderConfigurer = new PropertySourcesPlaceholderConfigurer();
        placeholderConfigurer.setLocations(
            new ClassPathResource("/config/site.properties"),
            new ClassPathResource("/config/mail.properties"),
            new ClassPathResource("/config/service.properties"));
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

    @Bean
    public ViewResolver viewResolver() {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setViewClass(JstlView.class);
        viewResolver.setPrefix("/WEB-INF/jsp/");
        viewResolver.setSuffix(".jsp");
        return viewResolver;
    }

    @Bean
    public ExceptionResolver exceptionResolver() {
        ExceptionResolver exceptionResolver = new ExceptionResolver();
        Properties properties = new Properties();
        properties.setProperty(BusinessHandleException.class.getName(), "common/message");
        properties.setProperty(SessionTimeoutException.class.getName(), "login");
        properties.setProperty(PermissionForbiddenException.class.getName(), "common/message");
        properties.setProperty(Exception.class.getName(), "common/error/500");
        exceptionResolver.setExceptionMappings(properties);
        return exceptionResolver;
    }

    @Override
    public Validator getValidator() {
        LocalValidatorFactoryBean validator = new LocalValidatorFactoryBean();
        validator.setValidationMessageSource(messageSource());
        validator.afterPropertiesSet();
        return validator;
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) { // Default Redirect
        super.addViewControllers(registry);
        registry.addViewController("login").setViewName("login");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/3rdLibrary/**").addResourceLocations("/3rdLibrary/");
        registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
        registry.addResourceHandler("/styles/**").addResourceLocations("/styles/");
        registry.addResourceHandler("/js/**").addResourceLocations("/js/");
        registry.addResourceHandler("/images/**").addResourceLocations("/images/");
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new RequestContextInterceptor());
        registry.addInterceptor(new LoginRequiredInterceptor());
        registry.addInterceptor(new CSRFTokenRequiredInterceptor());
        registry.addInterceptor(new CaptchaRequiredInterceptor());
//        registry.addInterceptor(new PermissionInterceptor()); aplha does not need.
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

    private void registerSiteMeshFilter(final ServletContext servletContext) {

        FilterRegistration.Dynamic sitemeshFilter = servletContext.addFilter("sitemesh", new SiteMeshFilter());
        sitemeshFilter.addMappingForUrlPatterns(null, true, "/*");
    }

    private void registerRequestLogFilter(final ServletContext servletContext) {
        FilterRegistration.Dynamic requestLogFilter = servletContext.addFilter("trackLogFilter", new TrackLogFilter());
        requestLogFilter.addMappingForUrlPatterns(null, true, "/*");
    }

    //todo modify config
    @Bean
    public RestClientRegister restClientsBuilder() {
        ResourceBundle bundle = ResourceBundle.getBundle("config.service");
        return new RestClientRegister(bundle.getString("service.url"))
            .add(LoginRESTService.class)
            .add(OrgRESTService.class)
            .add(AccountRESTService.class);
    }


    @Bean
    public PermissionInitializer permissionInitializer() {
        return new PermissionInitializer();
    }
}
