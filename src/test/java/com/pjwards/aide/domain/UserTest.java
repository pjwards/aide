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
    private Date date;

    @Before
    public void setup() {
        user = new User();
        date = new Date();
        user.setId(1L).setName("jisung").setEmail("a@a.com").setPassword("4194105091094")
                .setCreatedDate(date).setLastDate(date).setCompany("google");
    }

    @Test
    public void testUser() throws Exception {
        assertThat(user.getCompany(), is("google"));
        assertThat(user.getCreatedDate(), is(date));
        assertThat(user.getEmail(), is("a@a.com"));
        assertThat(user.getId(), is(1L));
        assertThat(user.getLastDate(), is(date));
        assertThat(user.getName(), is("jisung"));
        assertThat(user.getPassword(), is("4194105091094"));

    }
}
