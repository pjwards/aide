package com.pjwards.aide.config;

import com.pjwards.aide.service.conference.ConferenceService;
import com.sun.deploy.config.DefaultConfig;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TestConfig extends DefaultConfig {

    @Bean
    public ConferenceService conferenceService() {
        return Mockito.mock(ConferenceService.class);
    }
}