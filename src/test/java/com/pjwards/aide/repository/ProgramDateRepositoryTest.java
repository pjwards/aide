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
    private static final String DATE = "2016-01-01";

    private ProgramDate programDate;

    @Autowired
    private ProgramDateRepository programDateRepository;

    @Autowired
    private ConferenceRepository conferenceRepository;

    @Test
    public void testSaveWithMandatory() throws ParseException {
        programDate = new ProgramDate.Builder(DATE).build();
        programDateRepository.save(programDate);
    }

    @Test
    public void testSaveWithAll() throws ParseException {
        Conference conference = new Conference.Builder("name", "description").build();
        conferenceRepository.save(conference);

        programDate = new ProgramDate.Builder(DATE).conference(conference).build();
        programDateRepository.save(programDate);
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void testSave_EmptyRoom_ShouldOccurNoInteractionsWanted() {
        programDate = new ProgramDate();
        programDateRepository.save(programDate);
    }
}
