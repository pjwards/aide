package com.pjwards.aide.repository;

import com.pjwards.aide.config.ApplicationConfig;
import com.pjwards.aide.domain.Conference;
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

@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
@ContextConfiguration(classes = {ApplicationConfig.class}, loader = SpringApplicationContextLoader.class)
public class ConferenceRepositoryTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(ConferenceRepositoryTest.class);
    private static final String NAME = "name";
    private static final  String DESCRIPTION = "description";

    private Conference conference;

    @Autowired
    private ConferenceRepository conferenceRepository;

    @Test
    public void testSaveWithMandatory() {
        conference = new Conference.Builder(NAME, DESCRIPTION).build();
        conferenceRepository.save(conference);
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void testSave_EmptyRoom_ShouldOccurNoInteractionsWanted() {
        conference = new Conference();
        conferenceRepository.save(conference);
    }

}
