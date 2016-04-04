package com.pjwards.aide.domain;

import com.pjwards.aide.domain.enums.Role;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.nullValue;

import static org.junit.Assert.assertThat;

public class ConferenceRoleTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(ConferenceRoleTest.class);
    private static final Role CONFERENCE_ROLE = Role.USER;

    private ConferenceRole conferenceRole;

    @Before
    public void setup() {
        conferenceRole = new ConferenceRole.Builder(Role.ADMIN).build();
    }

    @Test
    public void testBuildWithMandatoryInformation() {
        assertThat(conferenceRole.getId(), nullValue());
        assertThat(conferenceRole.getRole(), is(Role.ADMIN));
        assertThat(conferenceRole.getUserSet(), nullValue());
        assertThat(conferenceRole.getConferenceSet(), nullValue());
    }

    @Test
    public void testUpdate(){
        Role UPDATE_ROLE = Role.USER;
        conferenceRole.update(UPDATE_ROLE);

        assertThat(conferenceRole.getId(), nullValue());
        assertThat(conferenceRole.getRole(), is(CONFERENCE_ROLE));
        assertThat(conferenceRole.getUserSet(), nullValue());
        assertThat(conferenceRole.getConferenceSet(), nullValue());
    }

}
