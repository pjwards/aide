package com.pjwards.aide.repository;

import com.pjwards.aide.config.ApplicationConfig;
import com.pjwards.aide.domain.Assets;
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
    private static final String FILENAME = "a.ppt";
    private static final String REAL_PATH = "file/filename";
    private static final Long FILE_SIZE = 20L;
    private static final Integer DOWNLOAD_COUNT = 122;

    private User user;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ConferenceRoleRepository conferenceRoleRepository;

    @Autowired
    private AssetsRepository assetsRepository;

    @Test
    public void testSaveWithMandatory() {
        user = new User.Builder(NAME, EMAIL, PASSWORD).build();
        userRepository.save(user);
    }

    @Test
    public void testSaveWithAll() {
        ConferenceRole conferenceRole = new ConferenceRole.Builder(Role.ADMIN).build();
        conferenceRoleRepository.save(conferenceRole);
        Assets assets = new Assets.Builder(FILENAME, REAL_PATH, FILE_SIZE, DOWNLOAD_COUNT).build();
        assetsRepository.save(assets);

        user = new User.Builder(NAME, EMAIL, PASSWORD).assets(assets).build();

        userRepository.save(user);
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void testSaveException() {
        user = new User();
        userRepository.save(user);
    }
}
