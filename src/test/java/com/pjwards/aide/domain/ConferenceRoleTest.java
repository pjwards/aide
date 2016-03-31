package com.pjwards.aide.domain;

import com.pjwards.aide.domain.enums.Role;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class ConferenceRoleTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(ConferenceRoleTest.class);
    private ConferenceRole conferenceRole;

    @Before
    public void setup() {
        conferenceRole = new ConferenceRole.Builder(Role.ADMIN).build();
    }

    @Test
    public void testUser() throws Exception{
        assertThat(conferenceRole.getRole(),is(Role.ADMIN));
    }

}
