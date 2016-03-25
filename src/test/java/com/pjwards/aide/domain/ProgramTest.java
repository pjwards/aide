package com.pjwards.aide.domain;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.util.Date;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

public class ProgramTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProgramTest.class);
    private static final long TWO_HOUR = 2 * 60 * 60 * 1000;
    private Program program;
    private Date begin;
    private Date end;

    @Before
    public void setup() throws ParseException {
        program = new Program();
        begin = new Date(System.currentTimeMillis());
        end = new Date(System.currentTimeMillis() + TWO_HOUR);

        program
                .setId(1L)
                .setTitle("title")
                .setDescription("description")
                .setBegin(begin)
                .setEnd(end);
    }

    @Test
    public void testProgram() throws Exception {
        assertThat(program.getId(), is(1L));
        assertThat(program.getTitle(), is("title"));
        assertThat(program.getDescription(), is("description"));
        assertThat(program.getBegin(), is(begin));
        assertThat(program.getEnd(), is(end));
        assertThat(program.getEnd().getTime() - program.getBegin().getTime(), is(TWO_HOUR));
    }

    @Test
    public void testGetTime() {
        assertThat(program.getEndTime().getTime() - program.getBeginTime().getTime(), is(TWO_HOUR));
        assertThat(program.getBeginTime().toString(), is(program.getBegin().toString().substring(11, 19)));
        assertThat(program.getEndTime().toString(), is(program.getEnd().toString().substring(11, 19)));
    }
}
