package com.pjwards.aide.config;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("com.pjwards.aide")
@EnableAutoConfiguration
public class ApplicationConfig {
}
