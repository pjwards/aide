package com.pjwards.aide.repository;

import com.pjwards.aide.config.ApplicationConfig;
import com.pjwards.aide.domain.Pass;
import com.pjwards.aide.domain.User;
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
public class PassRepositoryTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(PassRepositoryTest.class);
    private static final String TAG = "tag";
    private static final String NAME = "jisung";
    private static final String EMAIL = "a@a.com";
    private static final String PASSWORD = "4194105091094";

    private Pass pass;

    @Autowired
    private PassRepository passRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void testSaveWithMandatory() {
        pass = new Pass.Builder(TAG).build();
        passRepository.save(pass);
    }

    @Test
    public void testSaveWithAll() {
        User user = new User.Builder(NAME, EMAIL, PASSWORD).build();
        userRepository.save(user);

        pass = new Pass.Builder(TAG).user(user).build();
        passRepository.save(pass);
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void testSave_EmptyRoom_ShouldOccurNoInteractionsWanted() {
        pass = new Pass();
        passRepository.save(pass);
    }

}
