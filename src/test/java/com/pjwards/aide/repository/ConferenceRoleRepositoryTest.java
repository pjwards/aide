package com.pjwards.aide.repository;

import com.pjwards.aide.config.ApplicationConfig;
import com.pjwards.aide.domain.Conference;
import com.pjwards.aide.domain.ConferenceRole;
import com.pjwards.aide.domain.User;
import com.pjwards.aide.domain.enums.Role;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationContextLoader;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.expression.ParseException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;


@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
@ContextConfiguration(classes = {ApplicationConfig.class}, loader = SpringApplicationContextLoader.class)
public class ConferenceRoleRepositoryTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(ConferenceRoleRepositoryTest.class);
    private static final Role ROLE = Role.ADMIN;
    private static final String NAME = "jisung";
    private static final String DESCRPTION = "hello";
    private static final String EMAIL = "a@a.com";
    private static final String PASSWORD = "4194105091094";

    private ConferenceRole conferenceRole;

    @Autowired
    private ConferenceRoleRepository conferenceRoleRepository;

    @Autowired
    private ConferenceRepository conferenceRepository;

    @Autowired
    private UserRepository userRepository;

    @Before
    public void testSaveWithMandatory() {
        conferenceRole = new ConferenceRole.Builder(ROLE).build();
        conferenceRoleRepository.save(conferenceRole);
    }

    @Test
    public void testSaveWithAll() throws ParseException{
        Conference conference = new Conference.Builder(NAME, DESCRPTION).build();
        conferenceRepository.save(conference);

        User user = new User.Builder(NAME, EMAIL, PASSWORD).build();
        userRepository.save(user);

        conferenceRole = new ConferenceRole.Builder(ROLE).user(new HashSet<User>(){{
            add(user);
        }}).conference(new HashSet<Conference>(){{
            add(conference);
        }}).build();

        conferenceRoleRepository.save(conferenceRole);
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void testSaveException(){
        conferenceRole = new ConferenceRole();
        conferenceRoleRepository.save(conferenceRole);
    }
}
