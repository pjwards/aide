package com.pjwards.aide.domain;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.IsNull.nullValue;
import static org.junit.Assert.assertThat;

public class RoomTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(RoomTest.class);
    private static final String NAME = "name";
    private static final String LOCATION = "location";
    private static final String DESCRIPTION = "description";

    private Room room;

    @Before
    public void setup() {
        room = new Room.Builder(NAME, LOCATION, DESCRIPTION).build();
    }

    @Test
    public void testBuildWithMandatoryInformation() {

        assertThat(room.getId(), nullValue());
        assertThat(room.getName(), is(NAME));
        assertThat(room.getLocation(), is(LOCATION));
        assertThat(room.getDescription(), is(DESCRIPTION));
        assertThat(room.getConference(), nullValue());
        assertThat(room.getProgramList(), nullValue());
    }
}
