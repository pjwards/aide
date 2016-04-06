package com.pjwards.aide.repository;

import com.pjwards.aide.config.ApplicationConfig;
import com.pjwards.aide.domain.Assets;
import com.pjwards.aide.domain.Sponsor;
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
public class AssetsRepositoryTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(AssetsRepositoryTest.class);
    private static final String NAME = "jisung";
    private static final String EMAIL = "a@a.com";
    private static final String PASSWORD = "4194105091094";
    private static final String SLUG = "peter";
    private static final String FILENAME = "a.ppt";
    private static final String REAL_PATH = "file/filename";
    private static final Long FILE_SIZE = 20L;
    private static final Integer DOWNLOAD_COUNT = 122;

    private Assets assets;

    @Autowired
    private AssetsRepository assetsRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SponsorRepository sponsorRepository;

    @Test
    public void testSaveWithMandatory() {
        assets = new Assets.Builder(FILENAME, REAL_PATH, FILE_SIZE, DOWNLOAD_COUNT).build();
        assetsRepository.save(assets);
    }

    @Test
    public void testSaveWithAll() {
        User user = new User.Builder(NAME, EMAIL, PASSWORD).build();
        userRepository.save(user);
        Sponsor sponsor = new Sponsor.Builder(SLUG, NAME).build();
        sponsorRepository.save(sponsor);

        assets = new Assets.Builder(FILENAME, REAL_PATH, FILE_SIZE, DOWNLOAD_COUNT).user(user).sponsor(sponsor).build();
        assetsRepository.save(assets);
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void testSaveException() {
        assets = new Assets();
        assetsRepository.save(assets);
    }
}
