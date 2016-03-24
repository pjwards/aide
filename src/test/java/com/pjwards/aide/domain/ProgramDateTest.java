package com.pjwards.aide.domain;

import org.junit.Before;
import org.junit.Test;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class ProgramDateTest {
    private ProgramDate programDate;
    private DateFormat formatter;

    @Before
    public void setup() throws ParseException {
        programDate = new ProgramDate();
        formatter = new SimpleDateFormat("yyyy-MM-dd");
        programDate.setDay(formatter.parse("2016-01-01"));
    }

    @Test
    public void testProgramDate() {
        assertThat(formatter.format(programDate.getDay()), is("2016-01-01"));
    }
}
