package com.pjwards.aide.domain;


import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.IsNull.nullValue;
import static org.junit.Assert.assertThat;

public class AssetsTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(AssetsTest.class);
    private static final String FILENAME = "a.ppt";
    private static final String REAL_PATH = "file/filename";
    private static final Long FILE_SIZE = 20L;
    private static final Integer DOWNLOAD_COUNT = 122;

    private Assets assets;

    @Before
    public void setup() {
        assets = new Assets.Builder(FILENAME, REAL_PATH, FILE_SIZE, DOWNLOAD_COUNT).build();
    }

    @Test
    public void testBuildWithMandatoryInformation() throws Exception{
        assertThat(assets.getId(), nullValue());
        assertThat(assets.getFileName(), is(FILENAME));
        assertThat(assets.getRealPath(), is(REAL_PATH));
        assertThat(assets.getFileSize(), is(FILE_SIZE));
        assertThat(assets.getDownloadCount(), is(DOWNLOAD_COUNT));
        assertThat(assets.getSponsor(), nullValue());
        assertThat(assets.getUser(), nullValue());
    }

    @Test
    public void testUpdate() {
        String UPDATE_FILENAME = "b.ppt";
        String UPDATE_REAL_PATH = "file/filename/ad";
        Long UPDATE_FILE_SIZE = 30L;
        Integer UPDATE_DOWNLOAD_COUNT = 2;

        Assets updatedAssets = new Assets.Builder(UPDATE_FILENAME, UPDATE_REAL_PATH, UPDATE_FILE_SIZE, UPDATE_DOWNLOAD_COUNT).build();
        assets.update(updatedAssets);

        assertThat(assets.getId(), nullValue());
        assertThat(assets.getFileName(), is(UPDATE_FILENAME));
        assertThat(assets.getRealPath(), is(UPDATE_REAL_PATH));
        assertThat(assets.getFileSize(), is(UPDATE_FILE_SIZE));
        assertThat(assets.getDownloadCount(), is(UPDATE_DOWNLOAD_COUNT));
        assertThat(assets.getSponsor(), nullValue());
        assertThat(assets.getUser(), nullValue());
    }
}
