package com.pjwards.aide.repository;

import com.pjwards.aide.config.ApplicationConfig;
import com.pjwards.aide.domain.Assets;
import com.pjwards.aide.domain.Sponsor;
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
public class SponsorRepositoryTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(SponsorRepositoryTest.class);
    private static final String SLUG = "peter";
    private static final String NAME = "jisung";
    private static final String URL = "www.google.com";
    private static final String DESCRIPTION = "hellomynameisjisungjeon";
    private static final String FILENAME = "a.ppt";
    private static final String REAL_PATH = "file/filename";
    private static final Long FILE_SIZE = 20L;
    private static final Integer DOWNLOAD_COUNT = 122;

    private Sponsor sponsor;

    @Autowired
    private SponsorRepository sponsorRepository;

    @Autowired
    private AssetsRepository assetsRepository;

    @Test
    public void testSaveWithMandatory() {
        sponsor = new Sponsor.Builder(SLUG, NAME).url(URL).description(DESCRIPTION).build();
        sponsorRepository.save(sponsor);
    }

    @Test
    public void testSaveWithAll() {
        Assets assets = new Assets.Builder(FILENAME, REAL_PATH, FILE_SIZE, DOWNLOAD_COUNT).build();
        assetsRepository.save(assets);

        sponsor = new Sponsor.Builder(SLUG, NAME).url(URL).description(DESCRIPTION).assets(assets).build();
        sponsorRepository.save(sponsor);
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void testSaveException() {
        sponsor = new Sponsor();
        sponsorRepository.save(sponsor);
    }
}
