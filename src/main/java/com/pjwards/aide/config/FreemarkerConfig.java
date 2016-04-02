package com.pjwards.aide.config;

import freemarker.template.TemplateModelException;
import kr.pe.kwonnam.freemarker.inheritance.BlockDirective;
import kr.pe.kwonnam.freemarker.inheritance.ExtendsDirective;
import kr.pe.kwonnam.freemarker.inheritance.PutDirective;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;
import org.springframework.web.servlet.view.freemarker.FreeMarkerViewResolver;

import java.util.HashMap;
import java.util.Map;

@Configuration
@PropertySource("classpath:application.properties")
public class FreemarkerConfig {

    @Value("${spring.application.name}")
    private String name;

    @Value("${spring.application.address}")
    private String address;

    @Value("${multipart.max-request-size}")
    private String maxFileSize;

    @Bean
    public FreeMarkerConfigurer freeMarkerConfigurer() throws TemplateModelException {
        FreeMarkerConfigurer freeMarkerConfigurer = new FreeMarkerConfigurer();

        Map<String, Object> freemarkerLayoutDirectives = new HashMap<>();
        freemarkerLayoutDirectives.put("extends", new ExtendsDirective());
        freemarkerLayoutDirectives.put("block", new BlockDirective());
        freemarkerLayoutDirectives.put("put", new PutDirective());

        Map<String, Object> myApp = new HashMap<>();
        myApp.put("name", name);
        myApp.put("address", address);
        myApp.put("maxFileSize", Integer.parseInt(maxFileSize.replace("MB",""))*1024*1024);

        Map<String, Object> freemarkerVariable = new HashMap<>();
        freemarkerVariable.put("layout", freemarkerLayoutDirectives);
        freemarkerVariable.put("myApp", myApp);

        freeMarkerConfigurer.setFreemarkerVariables(freemarkerVariable);
        freeMarkerConfigurer.setDefaultEncoding("UTF-8");
        freeMarkerConfigurer.setTemplateLoaderPath("classpath:templates");

        return freeMarkerConfigurer;
    }

    @Bean
    public ViewResolver freeMarkerViewResolver() {
        FreeMarkerViewResolver freeMarkerViewResolver = new FreeMarkerViewResolver();

        freeMarkerViewResolver.setCache(true);
        freeMarkerViewResolver.setPrefix("");
        freeMarkerViewResolver.setSuffix(".ftl");
        freeMarkerViewResolver.setContentType("text/html;charset=UTF-8");
        freeMarkerViewResolver.setExposeSpringMacroHelpers(true);
        freeMarkerViewResolver.setExposeRequestAttributes(true);
        freeMarkerViewResolver.setExposeSessionAttributes(true);
        freeMarkerViewResolver.setRequestContextAttribute("rc");
        return freeMarkerViewResolver;
    }
}