package com.pjwards.aide.domain;

import org.junit.Before;
import org.junit.Test;

import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

public class ProgramTest {
    private static final long TWO_HOUR = 2 * 60 * 60 * 1000;
    private Program program;
    private Time begin;
    private Time end;
    private DateFormat formatter;

    @Before
    public void setup() throws ParseException {
        program = new Program();
        begin = new Time(System.currentTimeMillis());
        end = new Time(System.currentTimeMillis() + TWO_HOUR);

        ProgramDate programDate = new ProgramDate();
        formatter = new SimpleDateFormat("yyyy-MM-dd");
        programDate.setDay(formatter.parse("2016-01-01"));

        program.setTitle("title").setDescription("description").setBegin(begin).setEnd(end).setDate(programDate);
    }

    @Test
    public void testProgram() throws Exception {
        assertThat(program.getTitle(), is("title"));
        assertThat(program.getDescription(), is("description"));
        assertThat(program.getBegin(), is(begin));
        assertThat(program.getEnd(), is(end));
        assertThat(program.getEnd().getTime() - program.getBegin().getTime(), is(TWO_HOUR));
        assertThat(formatter.format(program.getDate().getDay()), is("2016-01-01"));
    }
}
