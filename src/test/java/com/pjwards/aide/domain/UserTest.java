package com.pjwards.aide.domain;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class UserTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserTest.class);
    private User user;

    @Before
    public void setup() {
        user = new User.Builder("jisung", "a@a.com", "4194105091094").company("google").build();
    }

    @Test
    public void testUser() throws Exception {
        assertThat(user.getCompany(), is("google"));
        assertThat(user.getEmail(), is("a@a.com"));
        assertThat(user.getName(), is("jisung"));
        assertThat(user.getPassword(), is("4194105091094"));
    }
}
