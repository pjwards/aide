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
    private static final String SLOGAN = "slogan";
    private static final String DESCRIPTION = "description";
    private static final String LOCATION = "location";
    private static final String LOCATION_URL = "location url";

    private Conference conference;

    @Before
    public void setup() {
        conference = new Conference.Builder(NAME, SLOGAN, DESCRIPTION)
                .location(LOCATION).locationUrl(LOCATION_URL).build();
    }

    @Test
    public void testBuildWithMandatoryInformation() {
        assertThat(conference.getId(), nullValue());
        assertThat(conference.getName(), is(NAME));
        assertThat(conference.getSlogan(), is(SLOGAN));
        assertThat(conference.getDescription(), is(DESCRIPTION));
        assertThat(conference.getLocation(), is(LOCATION));
        assertThat(conference.getLocationUrl(), is(LOCATION_URL));
        assertThat(conference.getProgramDates(), nullValue());
        assertThat(conference.getHost(), nullValue());
        assertThat(conference.getAssetsSet(), nullValue());
        assertThat(conference.getContacts(), nullValue());
    }
}
