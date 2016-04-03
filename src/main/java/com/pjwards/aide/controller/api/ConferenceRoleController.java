package com.pjwards.aide.controller.api;

import com.pjwards.aide.domain.ConferenceRole;
import com.pjwards.aide.exception.ConferenceRoleNotFoundException;
import com.pjwards.aide.service.conferencerole.ConferenceRoleService;
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
@RequestMapping("/api/conference-roles")
public class ConferenceRoleController {
    private static final Logger LOGGER = LoggerFactory.getLogger(ConferenceRoleController.class);

    @Autowired
    private ConferenceRoleService conferenceRoleService;

    @RequestMapping(method = RequestMethod.GET)
    public List<ConferenceRole> getAll() {
        LOGGER.debug("[API] Finding all conferenceRoles.");
        return conferenceRoleService.findAll();
    }

    @RequestMapping(method = RequestMethod.POST)
    public Map<String, Object> create(@Valid @RequestBody ConferenceRole conferenceRole) {
        LOGGER.debug("[API] Creating a new conferenceRole with information: {}", conferenceRole);

        Map<String, Object> response = new LinkedHashMap<>();
        response.put("message", "ConferenceRole created successfully");
        response.put("conferenceRole", conferenceRoleService.add(conferenceRole));
        return response;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    public ConferenceRole getDetails(@PathVariable("id") Long id) throws ConferenceRoleNotFoundException {
        LOGGER.debug("[API] Finding conferenceRole with id: {}", id);
        return conferenceRoleService.findById(id);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/{id}")
    public Map<String, Object> update(@PathVariable("id") Long id,
                                      @Valid @RequestBody ConferenceRole conferenceRole) throws ConferenceRoleNotFoundException {
        LOGGER.debug("[API] Updating a conferenceRole with information: {}", conferenceRole);

        Map<String, Object> response = new LinkedHashMap<>();
        response.put("message", "ConferenceRole updated successfully");
        response.put("conferenceRole", conferenceRoleService.update(conferenceRole));
        return response;
    }


    @RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
    public Map<String, Object> delete(@PathVariable("id") Long id) throws ConferenceRoleNotFoundException {
        LOGGER.debug("[API] Deleting a conferenceRole with id: {}", id);

        Map<String, Object> response = new HashMap<>();
        response.put("message", "ConferenceRole deleted successfully");
        response.put("conferenceRole", conferenceRoleService.deleteById(id));
        return response;
    }
}
