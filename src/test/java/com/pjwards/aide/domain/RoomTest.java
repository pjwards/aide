package com.pjwards.aide.domain;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class RoomTest {
    private Room room;

    @Before
    public void setup() {
        room = new Room();
        room.setName("room").setLocation("101").setDescription("description");
    }

    @Test
    public void testRoom() {
        assertThat(room.getName(), is("room"));
        assertThat(room.getLocation(), is("101"));
        assertThat(room.getDescription(), is("description"));
    }
}
