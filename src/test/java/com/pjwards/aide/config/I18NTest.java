package com.pjwards.aide.config;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationContextLoader;
import org.springframework.context.MessageSource;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.servlet.LocaleResolver;

import java.util.Locale;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestConfig.class, ApplicationConfig.class}, loader = SpringApplicationContextLoader.class)
public class I18NTest {
    @Autowired
    private MessageSource messageSource;

    @Autowired
    private LocaleResolver localeResolver;

    @Test
    public void test() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        String localeMessage = messageSource.getMessage("hello.test",null, "no search", localeResolver.resolveLocale(request));
        String usMessage = messageSource.getMessage("hello.test", null, "no search", Locale.US);
        String korMessage = messageSource.getMessage("hello.test",null, "no search", Locale.KOREA);

        assertThat(localeMessage, is("HELLO"));
        assertThat(usMessage, is("HELLO"));
        assertThat(korMessage, is("안녕"));
    }
}
