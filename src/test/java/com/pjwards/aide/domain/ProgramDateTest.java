package com.pjwards.aide.domain;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class ProgramDateTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProgramDateTest.class);
    private ProgramDate programDate;
    private DateFormat formatter;

    @Before
    public void setup() throws ParseException {
        programDate = new ProgramDate();
        formatter = new SimpleDateFormat("yyyy-MM-dd");
        programDate.setId(1L).setDay(formatter.parse("2016-01-01"));
    }

    @Test
    public void testProgramDate() {
        assertThat(programDate.getId(), is(1L));
        assertThat(formatter.format(programDate.getDay()), is("2016-01-01"));
    }

    @Test
    public void testGetFormattedDay() {
        assertThat(programDate.getFormattedDay(), is("2016-01-01"));
        assertThat(programDate.getFormattedDay("yyyy-MM-dd"), is("2016-01-01"));
    }

    @Test
    public void testSetFormattedDay() throws ParseException {
        programDate.setFormattedDay("2016-01-01");
        assertThat(programDate.getFormattedDay(), is("2016-01-01"));

        programDate.setFormattedDay("2016-01-01", "yyyy-MM-dd");
        assertThat(programDate.getFormattedDay(), is("2016-01-01"));
    }
}
