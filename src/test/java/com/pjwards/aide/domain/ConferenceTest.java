package com.pjwards.aide.domain;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class ConferenceTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(ConferenceTest.class);
    private Conference conference;

    @Before
    public void setup() {
        conference = new Conference();

        conference.setId(1L).setName("name").setDescription("description");
    }

    @Test
    public void testConference() {
        assertThat(conference.getId(), is(1L));
        assertThat(conference.getName(), is("name"));
        assertThat(conference.getDescription(), is("description"));
    }
}
