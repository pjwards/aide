package com.pjwards.aide.controller.api;

import com.pjwards.aide.domain.Contact;
import com.pjwards.aide.exception.ContactNotFoundException;
import com.pjwards.aide.service.contact.ContactService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/contacts")
public class ContactController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ContactController.class);

    @Autowired
    private ContactService contactService;

    @RequestMapping(method = RequestMethod.GET)
    public List<Contact> getAll() {
        LOGGER.debug("[API] Finding all contacts.");
        return contactService.findAll();
    }

    @RequestMapping(method = RequestMethod.POST)
    public Map<String, Object> create(@Valid @RequestBody Contact contact) {
        LOGGER.debug("[API] Creating a new contact with information: {}", contact);

        Map<String, Object> response = new LinkedHashMap<>();
        response.put("message", "Contact created successfully");
        response.put("contact", contactService.add(contact));
        return response;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    public Contact getDetails(@PathVariable("id") Long id) throws ContactNotFoundException {
        LOGGER.debug("[API] Finding contact with id: {}", id);
        return contactService.findById(id);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/{id}")
    public Map<String, Object> update(@PathVariable("id") Long id,
                                      @Valid @RequestBody Contact contact) throws ContactNotFoundException {
        LOGGER.debug("[API] Updating a contact with information: {}", contact);

        Map<String, Object> response = new LinkedHashMap<>();
        response.put("message", "Contact updated successfully");
        response.put("contact", contactService.update(contact));
        return response;
    }


    @RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
    public Map<String, Object> delete(@PathVariable("id") Long id) throws ContactNotFoundException {
        LOGGER.debug("[API] Deleting a contact with id: {}", id);

        Map<String, Object> response = new HashMap<>();
        response.put("message", "Contact deleted successfully");
        response.put("contact", contactService.deleteById(id));
        return response;
    }
}
