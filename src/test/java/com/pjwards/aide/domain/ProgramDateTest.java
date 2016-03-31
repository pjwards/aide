package com.pjwards.aide.domain;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.IsNull.nullValue;
import static org.junit.Assert.assertThat;

public class ProgramDateTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProgramDateTest.class);
    private ProgramDate programDate;
    private DateFormat formatter;

    @Before
    public void setup() throws ParseException {
        formatter = new SimpleDateFormat("yyyy-MM-dd");
        programDate = new ProgramDate.Builder(formatter.parse("2016-01-01")).build();
    }

    @Test
    public void testBuildWithMandatoryInformation() {
        assertThat(programDate.getId(), nullValue());
        assertThat(formatter.format(programDate.getDay()), is("2016-01-01"));
        assertThat(programDate.getProgramList(), nullValue());
    }

    @Test
    public void testUpdate() throws ParseException {
        ProgramDate updatedProgramDate = new ProgramDate.Builder(formatter.parse("2016-02-01")).build();
        programDate.update(updatedProgramDate);

        assertThat(programDate.getId(), nullValue());
        assertThat(formatter.format(programDate.getDay()), is("2016-02-01"));
        assertThat(programDate.getProgramList(), nullValue());
    }

    @Test
    public void testGetFormattedDay() {
        assertThat(programDate.getFormattedDay(), is("2016-01-01"));
        assertThat(programDate.getFormattedDay("yyyy-MM-dd"), is("2016-01-01"));
    }

    @Test
    public void testSetFormattedDay() throws ParseException {
        programDate.setFormattedDay("2016-02-01");
        assertThat(programDate.getFormattedDay(), is("2016-02-01"));

        programDate.setFormattedDay("2016-02-01", "yyyy-MM-dd");
        assertThat(programDate.getFormattedDay(), is("2016-02-01"));
    }
}
