package com.pjwards.aide.repository;

import com.pjwards.aide.config.ApplicationConfig;
import com.pjwards.aide.domain.Conference;
import com.pjwards.aide.domain.Room;
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

@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
@ContextConfiguration(classes = {ApplicationConfig.class}, loader = SpringApplicationContextLoader.class)
public class RoomRepositoryTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(RoomRepositoryTest.class);

    private Room room;
    private Conference conference;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private ConferenceRepository conferenceRepository;

    @Before
    public void setup() {
        conference = new Conference();
        conference.setName("name").setDescription("description");
        conferenceRepository.save(conference);

        room = new Room();
        room.setName("room").setLocation("101").setDescription("description").setConference(conference);
        roomRepository.save(room);
    }

    @Test
    public void testSave() {

    }

    @Test(expected = DataIntegrityViolationException.class)
    public void testSaveException() {
        room = new Room();
        room.setLocation("101").setDescription("description").setConference(conference);
        roomRepository.save(room);
    }
}
