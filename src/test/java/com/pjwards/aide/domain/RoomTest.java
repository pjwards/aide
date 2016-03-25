package com.pjwards.aide.domain;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class RoomTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(RoomTest.class);
    private Room room;

    @Before
    public void setup() {
        room = new Room();
        room.setId(1L).setName("room").setLocation("101").setDescription("description");
    }

    @Test
    public void testRoom() {
        assertThat(room.getId(), is(1L));
        assertThat(room.getName(), is("room"));
        assertThat(room.getLocation(), is("101"));
        assertThat(room.getDescription(), is("description"));
    }
}
