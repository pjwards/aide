package com.pjwards.aide.repository;

import com.pjwards.aide.config.ApplicationConfig;
import com.pjwards.aide.domain.Conference;
import com.pjwards.aide.domain.Program;
import com.pjwards.aide.domain.ProgramDate;
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

import java.text.ParseException;
import java.util.Date;

@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
@ContextConfiguration(classes = {ApplicationConfig.class}, loader = SpringApplicationContextLoader.class)
public class ProgramRepositoryTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProgramRepositoryTest.class);
    private ProgramDate programDate;
    private Room room;
    private Conference conference;
    private Program program;
    private Date begin;
    private Date end;

    @Autowired
    private ProgramRepository programRepository;

    @Autowired
    private ProgramDateRepository programDateRepository;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private ConferenceRepository conferenceRepository;

    @Before
    public void setup() throws ParseException {
        conference = new Conference.Builder("name", "description").build();
        conferenceRepository.save(conference);

        programDate = new ProgramDate();
        programDate = new ProgramDate.Builder("2016-01-01").conference(conference).build();
        programDateRepository.save(programDate);

        room = new Room.Builder("room", "101", "description").conference(conference).build();
        roomRepository.save(room);

        begin = new Date(System.currentTimeMillis());
        end = new Date(System.currentTimeMillis() + 2 * 60 * 60 * 1000);
        program = new Program.Builder("title", "description", begin, end)
                .conference(conference).room(room).date(programDate).build();
        programRepository.save(program);
    }

    @Test
    public void testSave() {

    }

    @Test(expected = DataIntegrityViolationException.class)
    public void testSaveException() {
        program = new Program();
        programRepository.save(program);
    }
}
