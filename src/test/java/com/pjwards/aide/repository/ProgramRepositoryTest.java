package com.pjwards.aide.repository;

import com.pjwards.aide.config.ApplicationConfig;
import com.pjwards.aide.domain.Conference;
import com.pjwards.aide.domain.Program;
import com.pjwards.aide.domain.ProgramDate;
import com.pjwards.aide.domain.Room;
import com.pjwards.aide.exception.WrongInputDateException;
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
    private static final String NAME = "name";
    private static final String TITLE = "title";
    private static final String DESCRIPTION = "description";
    private static final String BEGIN = "09:00";
    private static final String END = "10:00";

    private Program program;

    @Autowired
    private ProgramRepository programRepository;

    @Autowired
    private ProgramDateRepository programDateRepository;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private ConferenceRepository conferenceRepository;

    @Test
    public void testSaveWithMandatory() throws WrongInputDateException {
        program = new Program.Builder(TITLE, DESCRIPTION, BEGIN, END).build();
        programRepository.save(program);
    }

    @Test
    public void testSaveWithAll() throws ParseException, WrongInputDateException {
        Conference conference = new Conference.Builder("name", "slogan", "description").build();
        conferenceRepository.save(conference);

        ProgramDate programDate = new ProgramDate.Builder(NAME, "2016-01-01").conference(conference).build();
        programDateRepository.save(programDate);

        Room room = new Room.Builder("room", "101", "description").conference(conference).build();
        roomRepository.save(room);

        program = new Program.Builder(TITLE, DESCRIPTION, BEGIN, END)
                .conference(conference).room(room).date(programDate).build();
        programRepository.save(program);
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void testSave_EmptyRoom_ShouldOccurNoInteractionsWanted() {
        program = new Program();
        programRepository.save(program);
    }
}
