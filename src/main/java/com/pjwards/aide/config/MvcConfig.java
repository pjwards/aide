package com.pjwards.aide.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.context.embedded.ErrorPage;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import java.io.File;
import java.util.Locale;

@Configuration
@PropertySource("classpath:file.properties")
public class MvcConfig extends WebMvcConfigurerAdapter {

    @Autowired
    private Environment env;

    @Value("${file.realPath}")
    private String realPath;

    @Value("${file.filePath}")
    private String filePath;

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        //registry.addViewController("/home").setViewName("home");
        registry.addViewController("/").setViewName("index");
        registry.addViewController("/index").setViewName("index");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        super.addResourceHandlers(registry);

        String extPath = "file:" + filePath + realPath + "/";
        registry.addResourceHandler("/assets/**")
                .addResourceLocations(extPath)
                .setCachePeriod(0);
    }

    @Bean
    public EmbeddedServletContainerCustomizer containerCustomizer() {

        return (container -> {
            ErrorPage error401Page = new ErrorPage(HttpStatus.UNAUTHORIZED, "/401");
            ErrorPage error404Page = new ErrorPage(HttpStatus.NOT_FOUND, "/404");
            ErrorPage error500Page = new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, "/500");

            container.addErrorPages(error401Page, error404Page, error500Page);
        });
    }

    @Bean
    public LocaleChangeInterceptor localeChangeInterceptor() {
        LocaleChangeInterceptor localeChangeInterceptor = new LocaleChangeInterceptor();
        // set locale by language parameter from request
        localeChangeInterceptor.setParamName("lang");
        return localeChangeInterceptor;
    }

    @Bean(name = "localeResolver")
    public LocaleResolver sessionLocaleResolver() {
        // set locale by session
        SessionLocaleResolver localeResolver = new SessionLocaleResolver();
        // set locale by cookie(if session is broken, set by cookie in browser)
//        CookieLocaleResolver localeResolver = new CookieLocaleResolver();

        // set basic locale
        localeResolver.setDefaultLocale(new Locale(env.getProperty("default.locale")));

        return localeResolver;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // add Interceptor
        registry.addInterceptor(localeChangeInterceptor());
    }
}

