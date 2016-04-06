package com.pjwards.aide.config;

import com.pjwards.aide.domain.Conference;
import com.pjwards.aide.repository.ConferenceRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;

@Configuration
@ComponentScan("com.pjwards.aide")
@EnableAutoConfiguration
public class ApplicationConfig {

    private static final String MESSAGE_SOURCE_BASE_NAME = "i18n/messages";

    @Bean
    public MessageSource messageSource() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();

        messageSource.setBasename(MESSAGE_SOURCE_BASE_NAME);
        messageSource.setDefaultEncoding("UTF-8");
        messageSource.setUseCodeAsDefaultMessage(true);

        return messageSource;
    }


    @Bean
    public CommandLineRunner conferenceDemo(ConferenceRepository repository) {
        return (args) -> {
            repository.save(new Conference.Builder("DEVIEW 2015", "DEVIEW 2015가 성황리에 끝났습니다.").build());
            repository.save(new Conference.Builder("PYCON KOREA 2014", "한국에서 열리는 첫 번째 파이콘").build());
        };
    }
}
