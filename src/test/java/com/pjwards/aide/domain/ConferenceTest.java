package com.pjwards.aide.domain;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.IsNull.nullValue;
import static org.junit.Assert.assertThat;

public class ConferenceTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(ConferenceTest.class);

    private static final String NAME = "name";
    private static final  String DESCRIPTION = "description";
    private Conference conference;

    @Before
    public void setup() {
        conference = new Conference.Builder(NAME, DESCRIPTION).build();
    }

    @Test
    public void testBuildWithMandatoryInformation() {
        assertThat(conference.getId(), nullValue());
        assertThat(conference.getName(), is(NAME));
        assertThat(conference.getDescription(), is(DESCRIPTION));
        assertThat(conference.getRooms(), nullValue());
        assertThat(conference.getProgramDateList(), nullValue());
        assertThat(conference.getProgramList(), nullValue());
    }

    @Test
    public void testUpdate() {
        String UPDATED_NAME = "updated name";
        String UPDATED_DESCRIPTION = "updated description";
        Conference updatedConference = new Conference.Builder(UPDATED_NAME, UPDATED_DESCRIPTION).build();
        conference.update(updatedConference);

        assertThat(conference.getId(), nullValue());
        assertThat(conference.getName(), is(UPDATED_NAME));
        assertThat(conference.getDescription(), is(UPDATED_DESCRIPTION));
        assertThat(conference.getRooms(), nullValue());
        assertThat(conference.getProgramDateList(), nullValue());
        assertThat(conference.getProgramList(), nullValue());
    }
}
