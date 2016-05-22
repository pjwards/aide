package com.pjwards.aide.controller.api;

import com.pjwards.aide.domain.Presence;
import com.pjwards.aide.exception.PresenceNotFoundException;
import com.pjwards.aide.service.presence.PresenceService;
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
@RequestMapping("/api/presence")
public class PresenceController {
    private static final Logger LOGGER = LoggerFactory.getLogger(ConferenceRoleController.class);

    @Autowired
    private PresenceService presenceService;

    @RequestMapping(method = RequestMethod.GET)
    public List<Presence> getAll() {
        LOGGER.debug("[API] Finding all presences.");
        return presenceService.findAll();
    }

    @RequestMapping(method = RequestMethod.POST)
    public Map<String, Object> create(@Valid @RequestBody Presence conferenceRole) {
        LOGGER.debug("[API] Creating a new presence with information: {}", conferenceRole);

        Map<String, Object> response = new LinkedHashMap<>();
        response.put("message", "Presence created successfully");
        response.put("conferenceRole", presenceService.add(conferenceRole));
        return response;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    public Presence getDetails(@PathVariable("id") Long id) throws PresenceNotFoundException {
        LOGGER.debug("[API] Finding presence with id: {}", id);
        return presenceService.findById(id);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/{id}")
    public Map<String, Object> update(@PathVariable("id") Long id,
                                      @Valid @RequestBody Presence presence) throws PresenceNotFoundException {
        LOGGER.debug("[API] Updating a presence with information: {}", presence);

        Map<String, Object> response = new LinkedHashMap<>();
        response.put("message", "Presence updated successfully");
        response.put("presence", presenceService.update(presence));
        return response;
    }


    @RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
    public Map<String, Object> delete(@PathVariable("id") Long id) throws PresenceNotFoundException {
        LOGGER.debug("[API] Deleting a presence with id: {}", id);

        Map<String, Object> response = new HashMap<>();
        response.put("message", "Presence deleted successfully");
        response.put("presence", presenceService.deleteById(id));
        return response;
    }
}
