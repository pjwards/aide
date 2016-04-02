package com.pjwards.aide.config;

import org.apache.velocity.exception.VelocityException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.ui.velocity.VelocityEngineFactoryBean;

import java.io.IOException;
import java.util.Properties;

@Configuration
public class VelocityConfig {

//    @Bean
//    public VelocityConfigurer velocityConfigurer() {
//        VelocityConfigurer configurer = new VelocityConfigurer();
//        Properties properties = new Properties();
//        properties.setProperty("input.encoding", "UTF-8");
//        properties.setProperty("output.encoding", "UTF-8");
//        configurer.setVelocityProperties(properties);
//        return configurer;
//    }

    /*
    Velocity Engine Configuration for using velocity
     */
    @Bean
    public VelocityEngineFactoryBean velocityEngine() throws VelocityException, IOException {
        VelocityEngineFactoryBean factory = new VelocityEngineFactoryBean();
        Properties props = new Properties();
        props.put("resource.loader", "class");
        props.put("class.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
        props.setProperty("input.encoding", "UTF-8");
        props.setProperty("output.encoding", "UTF-8");
        factory.setVelocityProperties(props);

        return factory;
    }
}