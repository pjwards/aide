package com.pjwards.aide.service.contact;

import com.pjwards.aide.domain.Contact;
import com.pjwards.aide.exception.ContactNotFoundException;

import java.util.List;

public interface ContactService {

    /**
     * Returns a list of contacts.
     *
     * @return The list of contacts
     */
    public List<Contact> findAll();

    /**
     * Adds a new contact.
     *
     * @param added The information of the added contact.
     * @return The added contact.
     */
    public Contact add(Contact added);

    /**
     * Finds a contact.
     *
     * @param id The id of the wanted contact.
     * @return The found contact.
     * @throws ContactNotFoundException if no contact is found with the given id.
     */
    public Contact findById(Long id) throws ContactNotFoundException;

    /**
     * Updates the information of contact.
     *
     * @param updated The information of the updated contact.
     * @return The updated contact.
     * @throws ContactNotFoundException If no contact is found with the given id.
     */
    public Contact update(Contact updated) throws ContactNotFoundException;

    /**
     * Deletes a contact.
     *
     * @param id The id of the deleted contact.
     * @return The deleted contact.
     * @throws ContactNotFoundException if no contact is found with the given id.
     */
    public Contact deleteById(Long id) throws ContactNotFoundException;
}
