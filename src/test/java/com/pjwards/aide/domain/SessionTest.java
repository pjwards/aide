package com.pjwards.aide.domain;

import com.pjwards.aide.exception.WrongInputDateException;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.IsNull.nullValue;
import static org.junit.Assert.assertThat;

public class SessionTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(SessionTest.class);
    private static final String TITLE = "title";
    private static final String DESCRIPTION = "description";

    private Session session;

    @Before
    public void setup() throws ParseException, WrongInputDateException {
        session = new Session.Builder(TITLE, DESCRIPTION).build();
    }

    @Test
    public void testBuildWithMandatoryInformation() {
        assertThat(session.getId(), nullValue());
        assertThat(session.getTitle(), is(TITLE));
        assertThat(session.getDescription(), is(DESCRIPTION));
        assertThat(session.getRoom(), nullValue());
        assertThat(session.getProgram(), nullValue());
    }
}
