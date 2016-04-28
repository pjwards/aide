package com.pjwards.aide.domain;

import com.pjwards.aide.domain.enums.ContactType;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.IsNull.nullValue;
import static org.junit.Assert.assertThat;

public class ContactTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(ContactTest.class);
    private static final ContactType TYPE = ContactType.EMAIL;
    private static final String URL = "contact url";

    private Contact contact;

    @Before
    public void setup() {
        contact = new Contact.Builder(TYPE, URL).build();
    }

    @Test
    public void testBuildWithMandatoryInformation() {
        assertThat(contact.getId(), nullValue());
        assertThat(contact.getType(), is(TYPE));
        assertThat(contact.getUrl(), is(URL));
        assertThat(contact.getConference(), nullValue());
    }
}
