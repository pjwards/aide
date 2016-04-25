package com.pjwards.aide.domain;

import com.pjwards.aide.exception.WrongInputDateException;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.util.Date;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.IsNull.nullValue;
import static org.junit.Assert.assertThat;

public class ProgramTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProgramTest.class);
    private static final String TITLE = "title";
    private static final String DESCRIPTION = "description";
    private static final String BEGIN = "09:00";
    private static final String END = "10:00";

    private Program program;

    @Before
    public void setup() throws ParseException, WrongInputDateException {
        program = new Program.Builder(TITLE, DESCRIPTION, BEGIN, END).build();
    }

    @Test
    public void testBuildWithMandatoryInformation() {
        assertThat(program.getId(), nullValue());
        assertThat(program.getTitle(), is(TITLE));
        assertThat(program.getDescription(), is(DESCRIPTION));
        assertThat(program.getRoom(), nullValue());
        assertThat(program.getDate(), nullValue());
        assertThat(program.getBegin(), is(BEGIN));
        assertThat(program.getEnd(), is(END));
    }

    @Test(expected = WrongInputDateException.class)
    public void testDateChecker() throws WrongInputDateException {
        program.dateChecker(END, BEGIN);
    }
}
