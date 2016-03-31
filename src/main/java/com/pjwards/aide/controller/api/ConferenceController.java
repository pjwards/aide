package com.pjwards.aide.controller.api;

import com.pjwards.aide.domain.Conference;
import com.pjwards.aide.exception.ConferenceNotFoundException;
import com.pjwards.aide.service.conference.ConferenceService;
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
@RequestMapping("/api/conferences")
public class ConferenceController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ConferenceController.class);

    @Autowired
    private ConferenceService conferenceService;

    @RequestMapping(method = RequestMethod.GET)
    public List<Conference> getAll() {
        LOGGER.debug("Finding all conferences.");
        return conferenceService.findAll();
    }

    @RequestMapping(method = RequestMethod.POST)
    public Map<String, Object> create(@Valid @RequestBody Conference conference) {
        LOGGER.debug("Creating a new conference with information: {}", conference);

        Map<String, Object> response = new LinkedHashMap<>();
        response.put("message", "Conference created successfully");
        response.put("conference", conferenceService.add(conference));
        return response;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    public Conference getDetails(@PathVariable("id") Long id) throws ConferenceNotFoundException {
        LOGGER.debug("Finding conference with id: {}", id);
        return conferenceService.findById(id);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/{id}")
    public Map<String, Object> update(@PathVariable("id") Long id,
                                      @Valid @RequestBody Conference conference) throws ConferenceNotFoundException {
        LOGGER.debug("Updating a conference with information: {}", conference);

        Map<String, Object> response = new LinkedHashMap<>();
        response.put("message", "Conference updated successfully");
        response.put("conference", conferenceService.update(conference));
        return response;
    }


    @RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
    public Map<String, Object> delete(@PathVariable("id") Long id) throws ConferenceNotFoundException {
        LOGGER.debug("Deleting a conference with id: {}", id);

        Map<String, Object> response = new HashMap<>();
        response.put("message", "Conference deleted successfully");
        response.put("conference", conferenceService.deleteById(id));
        return response;
    }
}
