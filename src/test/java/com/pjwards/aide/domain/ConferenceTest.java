package com.pjwards.aide.domain;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class ConferenceTest {
    private Conference conference;

    @Before
    public void setup() {
        conference = new Conference();

        conference.setName("name").setDescription("description");
    }

    @Test
    public void testConference() {
        assertThat(conference.getName(), is("name"));
        assertThat(conference.getDescription(), is("description"));
    }
}
