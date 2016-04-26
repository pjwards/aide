package com.pjwards.aide.service.contact;

import com.pjwards.aide.domain.Contact;
import com.pjwards.aide.exception.ContactNotFoundException;
import com.pjwards.aide.repository.ContactRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ContactServiceImpl implements ContactService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ContactServiceImpl.class);

    private ContactRepository contactRepository;

    @Autowired
    public ContactServiceImpl(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

    @Transactional(readOnly = true)
    @Override
    public List<Contact> findAll() {
        LOGGER.debug("Finding all contacts.");

        List<Contact> contacts = contactRepository.findAll();
        LOGGER.debug("Found {} contacts.", contacts.size());

        return contacts;
    }

    @Transactional
    @Override
    public Contact add(Contact added) {
        LOGGER.debug("Creating a new contact with information: {}", added);

        added =  contactRepository.save(added);
        LOGGER.debug("Added a contact with information: {}", added);

        return added;
    }


    @Transactional(readOnly = true, rollbackFor = {ContactNotFoundException.class})
    @Override
    public Contact findById(Long id) throws ContactNotFoundException {
        LOGGER.debug("Finding contact with id: {}", id);

        Contact found = contactRepository.findOne(id);
        LOGGER.debug("Found contact with information: {}", found);

        if (found == null) {
            LOGGER.debug("No contact found with id: {}", id);
            throw new ContactNotFoundException("No contact found with id: " + id);
        }

        return found;
    }

    @Transactional(rollbackFor = {ContactNotFoundException.class})
    @Override
    public Contact update(Contact updated) throws ContactNotFoundException {
        LOGGER.debug("Updating a contact with information: {}", updated);

        Contact found = findById(updated.getId());
        found.update(updated);
        contactRepository.save(found);
        LOGGER.debug("Updated the information of a contact to: {}", found);

        return found;
    }

    @Transactional(rollbackFor = {ContactNotFoundException.class})
    @Override
    public Contact deleteById(Long id) throws ContactNotFoundException {
        LOGGER.debug("Deleting a contact with id: {}", id);

        Contact deleted = findById(id);
        contactRepository.delete(deleted);
        LOGGER.debug("Deleting contact: {}", deleted);

        return deleted;
    }
}
