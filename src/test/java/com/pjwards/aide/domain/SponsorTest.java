package com.pjwards.aide.domain;


import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.IsNull.nullValue;
import static org.junit.Assert.assertThat;

public class SponsorTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(SponsorTest.class);
    private static final String SLUG = "peter";
    private static final String NAME = "jisung";
    private static final String URL = "www.google.com";
    private static final String DESCRIPTION = "hellomynameisjisungjeon";

    private Sponsor sponsor;

    @Before
    public void setup() {
        sponsor = new Sponsor.Builder(SLUG, NAME).url(URL).description(DESCRIPTION).build();
    }

    @Test
    public void testBuildWithMandatoryInformation() throws Exception{
        assertThat(sponsor.getId(), nullValue());
        assertThat(sponsor.getName(), is(NAME));
        assertThat(sponsor.getSlug(), is(SLUG));
        assertThat(sponsor.getUrl(), is(URL));
        assertThat(sponsor.getDescription(), is(DESCRIPTION));
        assertThat(sponsor.getAssets(), nullValue());
    }

    @Test
    public void testUpdate() {
        String UPDATE_NAME = "seodong";
        String UPDATE_SLUG = "sam";
        String UPDATE_URL= "www.facebook.com";
        String UPDATE_DESCRIPTION = "hellomynameisseodong";

        Sponsor updatedSponsor = new Sponsor.Builder(UPDATE_SLUG, UPDATE_NAME).url(UPDATE_URL).description(UPDATE_DESCRIPTION).build();
        sponsor.update(updatedSponsor);

        assertThat(sponsor.getId(), nullValue());
        assertThat(sponsor.getName(), is(UPDATE_NAME));
        assertThat(sponsor.getSlug(), is(UPDATE_SLUG));
        assertThat(sponsor.getUrl(), is(UPDATE_URL));
        assertThat(sponsor.getDescription(), is(UPDATE_DESCRIPTION));
        assertThat(sponsor.getAssets(), nullValue());
    }
}
