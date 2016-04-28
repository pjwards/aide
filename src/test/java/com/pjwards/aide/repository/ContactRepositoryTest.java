package com.pjwards.aide.repository;

import com.pjwards.aide.config.ApplicationConfig;
import com.pjwards.aide.domain.Contact;
import com.pjwards.aide.domain.enums.ContactType;
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
public class ContactRepositoryTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(ContactRepositoryTest.class);
    private static final ContactType TYPE = ContactType.EMAIL;
    private static final String URL = "contact url";

    private Contact contact;

    @Autowired
    private ContactRepository contactRepository;

    @Test
    public void testSaveWithMandatory() {
        contact = new Contact.Builder(TYPE, URL).build();
        contactRepository.save(contact);
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void testSave_EmptyRoom_ShouldOccurNoInteractionsWanted() {
        contact = new Contact();
        contactRepository.save(contact);
    }

}
