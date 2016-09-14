package com.yealink.imweb;


import java.util.Properties;
import java.util.ResourceBundle;
import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import com.opensymphony.sitemesh.webapp.SiteMeshFilter;
import com.yealink.imweb.modules.common.interceptor.AuthInterceptor;
import com.yealink.uc.platform.exception.resolver.ExceptionResolver;
import com.yealink.uc.platform.filter.TrackLogFilter;
import com.yealink.uc.platform.interceptor.RequestContextInterceptor;

import com.yealink.uc.platform.rest.client.RestClientRegister;
import com.yealink.uc.service.modules.account.api.AccountRESTService;
import com.yealink.uc.service.modules.login.api.LoginRESTService;
import com.yealink.uc.service.modules.muc.api.MucRESTService;
import com.yealink.uc.service.modules.org.api.OrgRESTService;
import com.yealink.uc.service.modules.staff.api.StaffRESTService;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.transaction.annotation.EnableTransactionManagement;
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
@ComponentScan(basePackages = "com.yealink.imweb")
@EnableTransactionManagement(proxyTargetClass = true)
@EnableAspectJAutoProxy
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
        multipartResolver.setMaxUploadSize(10 * 1024 * 1024);//最大大小1M
        return multipartResolver;
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

    @Bean
    public ViewResolver viewResolver() {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setViewClass(JstlView.class);
//        viewResolver.setPrefix("/WEB-INF/jsp/");
        viewResolver.setPrefix("/html/");
        viewResolver.setSuffix(".html");
        return viewResolver;
    }

    @Bean
    public ExceptionResolver exceptionResolver() {
        ExceptionResolver exceptionResolver = new ExceptionResolver();
        Properties properties = new Properties();
        properties.setProperty(Exception.class.getName(), "common/error/500");
        exceptionResolver.setExceptionMappings(properties);
        return exceptionResolver;
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
        registry.addResourceHandler("/html/**").addResourceLocations("/html/");
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new RequestContextInterceptor());
        registry.addInterceptor(new AuthInterceptor()).addPathPatterns("/**");
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

    @Bean
    public RestClientRegister restClientsBuilder() {
        ResourceBundle bundle = ResourceBundle.getBundle("config.service");
        return new RestClientRegister(bundle.getString("uc.service.url"))
                .add(OrgRESTService.class)
                .add(StaffRESTService.class)
                .add(MucRESTService.class);
    }

}
