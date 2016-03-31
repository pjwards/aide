package com.pjwards.aide.repository;

import com.pjwards.aide.config.ApplicationConfig;
import com.pjwards.aide.domain.Conference;
import com.pjwards.aide.domain.ConferenceRole;
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

import java.util.Set;


@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
@ContextConfiguration(classes = {ApplicationConfig.class}, loader = SpringApplicationContextLoader.class)
public class ConferenceRoleRepositoryTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(ConferenceRoleRepositoryTest.class);
    private ConferenceRole conferenceRole;

    @Autowired
    private ConferenceRoleRepository conferenceRoleRepository;

    @Autowired
    private ConferenceRepository conferenceRepository;

    @Before
    public void setup() throws ParseException{
        conferenceRole = new ConferenceRole.Builder(Role.ADMIN).build();
        conferenceRoleRepository.save(conferenceRole);
    }

    @Test
    public void testSave(){

    }

    @Test(expected = DataIntegrityViolationException.class)
    public void testSaveException(){
        conferenceRole = new ConferenceRole();
        conferenceRoleRepository.save(conferenceRole);
    }
}
