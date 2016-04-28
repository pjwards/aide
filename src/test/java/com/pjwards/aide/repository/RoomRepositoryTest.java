package com.pjwards.aide.repository;

import com.pjwards.aide.config.ApplicationConfig;
import com.pjwards.aide.domain.Room;
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
    private static final String NAME = "name";
    private static final String SLOGAN = "slogan";
    private static final String LOCATION = "location";
    private static final String DESCRIPTION = "description";

    private Room room;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private ConferenceRepository conferenceRepository;

    @Test
    public void testSaveWithMandatory() {
        room = new Room.Builder(NAME, LOCATION, DESCRIPTION).build();
        roomRepository.save(room);
    }

    @Test
    public void testSaveWithAll() {
        room = new Room.Builder(NAME, LOCATION, DESCRIPTION).build();
        roomRepository.save(room);
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void testSave_EmptyRoom_ShouldOccurNoInteractionsWanted() {
        room = new Room();
        roomRepository.save(room);
    }
}
