package com.pjwards.aide.repository;

import com.pjwards.aide.config.ApplicationConfig;
import com.pjwards.aide.domain.ConferenceRole;
import com.pjwards.aide.domain.User;
import com.pjwards.aide.domain.enums.Role;
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

import java.util.HashSet;

@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
@ContextConfiguration(classes = {ApplicationConfig.class}, loader = SpringApplicationContextLoader.class)
public class UserRepositoryTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserRepositoryTest.class);
    private static final String NAME = "jisung";
    private static final String EMAIL = "a@a.com";
    private static final String PASSWORD = "4194105091094";

    private User user;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ConferenceRoleRepository conferenceRoleRepository;

    @Test
    public void testSaveWithMandatory() {
        user = new User.Builder(NAME, EMAIL, PASSWORD).build();
        userRepository.save(user);
    }

    @Test
    public void testSaveWithAll() {
        ConferenceRole conferenceRole = new ConferenceRole.Builder(Role.ADMIN).build();
        conferenceRoleRepository.save(conferenceRole);

        user = new User.Builder(NAME, EMAIL, PASSWORD).conferenceRole(new HashSet<ConferenceRole>(){{
            add(conferenceRole);
        }}).build();

        userRepository.save(user);
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void testSaveException() {
        user = new User();
        userRepository.save(user);
    }
}
