package com.pjwards.aide.domain;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.IsNull.nullValue;
import static org.junit.Assert.assertThat;

public class ProgramDateTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProgramDateTest.class);
    private static final String NAME = "name";
    private static final String DAY = "2016-01-01";
    private static final String UPDATED_DAY = "2016-01-02";

    private ProgramDate programDate;
    private DateFormat formatter;

    @Before
    public void setup() throws ParseException {
        formatter = new SimpleDateFormat("yyyy-MM-dd");
        programDate = new ProgramDate.Builder(NAME, formatter.parse(DAY)).build();
    }

    @Test
    public void testBuildWithMandatoryInformation() {
        assertThat(programDate.getId(), nullValue());
        assertThat(formatter.format(programDate.getDay()), is(DAY));
        assertThat(programDate.getPrograms(), nullValue());
    }

    @Test
    public void testGetFormattedDay() {
        assertThat(programDate.getFormattedDay(), is(DAY));
        assertThat(programDate.getFormattedDay("yyyy-MM-dd"), is(DAY));
    }

    @Test
    public void testSetFormattedDay() throws ParseException {
        programDate.setFormattedDay(UPDATED_DAY);
        assertThat(programDate.getFormattedDay(), is(UPDATED_DAY));

        programDate.setFormattedDay(UPDATED_DAY, "yyyy-MM-dd");
        assertThat(programDate.getFormattedDay(), is(UPDATED_DAY));
    }

    @Test
    public void testTruncateDate() {
        Date dateObject = new Date();

        Calendar cal = Calendar.getInstance(); // locale-specific
        cal.setTime(dateObject);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);

        assertThat(programDate.truncateDate(dateObject).getTime(), is(cal.getTimeInMillis()));
    }

    @Test(expected = ParseException.class)
    public void testBuild_WrongFormattedDay_ShouldParseException() throws ParseException {
        String WRONG_FORMATTED_DAY = "2016/01/01";
        programDate = new ProgramDate.Builder(NAME, WRONG_FORMATTED_DAY).build();
    }
}
