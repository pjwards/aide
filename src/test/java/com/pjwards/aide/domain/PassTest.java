package com.pjwards.aide.domain;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.IsNull.nullValue;
import static org.junit.Assert.assertThat;

public class PassTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(PassTest.class);
    private static final String TAG = "tag";

    private Pass pass;

    @Before
    public void setup() {
        pass = new Pass.Builder(TAG).build();
    }

    @Test
    public void testBuildWithMandatoryInformation() {
        assertThat(pass.getId(), nullValue());
        assertThat(pass.getTag(), is(TAG));
        assertThat(pass.getUser(), nullValue());
    }
}
