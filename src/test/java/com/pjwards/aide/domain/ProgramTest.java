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
    private static final long TWO_HOUR = 2 * 60 * 60 * 1000;
    private static final String TITLE = "title";
    private static final String DESCRIPTION = "description";
    private static final Date BEGIN = new Date(System.currentTimeMillis());
    private static final Date END = new Date(System.currentTimeMillis() + TWO_HOUR);

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
        assertThat(program.getConference(), nullValue());
        assertThat(program.getRoom(), nullValue());
        assertThat(program.getDate(), nullValue());
        assertThat(program.getBegin(), is(BEGIN));
        assertThat(program.getEnd(), is(END));
        assertThat(program.getEnd().getTime() - program.getBegin().getTime(), is(TWO_HOUR));
    }

    @Test
    public void testUpdate() throws WrongInputDateException {
        String UPDATED_TITLE = "updated title";
        String UPDATED_DESCRIPTION = "updated description";
        Date UPDATED_BEGIN = new Date(System.currentTimeMillis());
        Date UPDATED_END = new Date(System.currentTimeMillis() + TWO_HOUR);
        Program updatedProgram = new Program.Builder(UPDATED_TITLE, UPDATED_DESCRIPTION, UPDATED_BEGIN, UPDATED_END).build();
        program.update(updatedProgram);

        assertThat(program.getId(), nullValue());
        assertThat(program.getTitle(), is(UPDATED_TITLE));
        assertThat(program.getDescription(), is(UPDATED_DESCRIPTION));
        assertThat(program.getConference(), nullValue());
        assertThat(program.getRoom(), nullValue());
        assertThat(program.getDate(), nullValue());
        assertThat(program.getBegin(), is(UPDATED_BEGIN));
        assertThat(program.getEnd(), is(UPDATED_END));
        assertThat(program.getEnd().getTime() - program.getBegin().getTime(), is(TWO_HOUR));

        program.update(TITLE, DESCRIPTION, BEGIN, END);
        assertThat(program.getTitle(), is(TITLE));
        assertThat(program.getDescription(), is(DESCRIPTION));
        assertThat(program.getBegin(), is(BEGIN));
        assertThat(program.getEnd(), is(END));
        assertThat(program.getEnd().getTime() - program.getBegin().getTime(), is(TWO_HOUR));
    }

    @Test
    public void testGetTime() {
        assertThat(program.getEndTime().getTime() - program.getBeginTime().getTime(), is(TWO_HOUR));
        assertThat(program.getBeginTime().toString(), is(program.getBegin().toString().substring(11, 19)));
        assertThat(program.getEndTime().toString(), is(program.getEnd().toString().substring(11, 19)));
    }

    @Test(expected = WrongInputDateException.class)
    public void testDateChecker() throws WrongInputDateException {
        program.dateChecker(END, BEGIN);
    }
}
