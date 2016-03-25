package com.pjwards.aide.repository;

import com.pjwards.aide.config.ApplicationConfig;
import com.pjwards.aide.domain.Conference;
import com.pjwards.aide.domain.ProgramDate;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationContextLoader;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;

@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
@ContextConfiguration(classes = {ApplicationConfig.class}, loader = SpringApplicationContextLoader.class)
public class ProgramDateRepositoryTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProgramDateRepositoryTest.class);
    private ProgramDate programDate;
    private Conference conference;

    @Autowired
    private ProgramDateRepository programDateRepository;

    @Autowired
    private ConferenceRepository conferenceRepository;

    @Before
    public void setup() throws ParseException {
        conference = new Conference();
        conference.setName("name").setDescription("description");
        conferenceRepository.save(conference);

        programDate = new ProgramDate();
        programDate.setFormattedDay("2016-01-01").setConference(conference);
        programDateRepository.save(programDate);
    }

    @Test
    public void testSave() {

    }

    @Test(expected = DataIntegrityViolationException.class)
    public void testSaveException() {
        programDate = new ProgramDate();
        programDate.setConference(conference);
        programDateRepository.save(programDate);
    }
}
