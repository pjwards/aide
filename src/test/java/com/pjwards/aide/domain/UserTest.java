package com.pjwards.aide.domain;

import com.pjwards.aide.domain.enums.Role;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

import static java.lang.Math.abs;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.IsNull.nullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class UserTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserTest.class);
    private static final String NAME = "jisung";
    private static final String EMAIL = "a@a.com";
    private static final String PASSWORD = "4194105091094";
    private static final String COMPANY = "google";
    private static final Date DAY = new Date();
    private static final Date NEXT_DAY = new Date();
    private static final Role ROLE = Role.USER;

    private User user;

    @Before
    public void setup() {
        user = new User.Builder(NAME, EMAIL, PASSWORD).company(COMPANY).build();
    }

    @Test
    public void testBuildWithMandatoryInformation() throws Exception{
        assertThat(user.getId(), nullValue());
        assertThat(user.getName(), is(NAME));
        assertThat(user.getEmail(), is(EMAIL));
        assertThat(user.getPassword(), is(PASSWORD));
        assertThat(user.getCompany(), is(COMPANY));
        assertTrue("Day dates aren't close enough to each other!",
                abs(user.getCreatedDate().getTime() - DAY.getTime()) < 1000);
        assertTrue("Next day dates aren't close enough to each other!",
                abs(user.getLastDate().getTime() - NEXT_DAY.getTime()) < 1000);
        assertThat(user.getRole(), is(ROLE));
        assertThat(user.getConferenceRoleSet(), nullValue());
    }

    @Test
    public void testUpdate() {
        String UPDATE_NAME = "seodong";
        String UPDATE_EMAIL = "a@b.com";
        String UPDATE_PASS_WOARD = "1234567";
        String UPDATE_COMPANY = "facebook";
        Date UPDATE_LAST_DAY = new Date();
        Role UPDATE_ROLE = Role.USER;

        User updatedUser = new User.Builder(UPDATE_NAME,UPDATE_EMAIL,UPDATE_PASS_WOARD).company(UPDATE_COMPANY)
                .lastDate(UPDATE_LAST_DAY).role(UPDATE_ROLE).build();
        user.update(updatedUser);

        assertThat(user.getId(), nullValue());
        assertThat(user.getName(), is(UPDATE_NAME));
        assertThat(user.getEmail(), is(UPDATE_EMAIL));
        assertThat(user.getPassword(), is(UPDATE_PASS_WOARD));
        assertThat(user.getCompany(), is(UPDATE_COMPANY));
        assertThat(user.getLastDate(), is(UPDATE_LAST_DAY));
        assertThat(user.getRole(), is(UPDATE_ROLE));
        assertThat(user.getConferenceRoleSet(), nullValue());
    }
}
