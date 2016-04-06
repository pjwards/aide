package com.pjwards.aide.controller.api;


import com.pjwards.aide.domain.Sponsor;
import com.pjwards.aide.exception.SponsorNotFoundException;
import com.pjwards.aide.service.sponsor.SponsorService;
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
@RequestMapping("/api/sponsors")
public class SponsorController {
    private static final Logger LOGGER = LoggerFactory.getLogger(SponsorController.class);

    @Autowired
    private SponsorService sponsorService;

    @RequestMapping(method = RequestMethod.GET)
    public List<Sponsor> getAll() {
        LOGGER.debug("[API] Finding all sponsors.");
        return sponsorService.findAll();
    }

    @RequestMapping(method = RequestMethod.POST)
    public Map<String, Object> create(@Valid @RequestBody Sponsor sponsor) {
        LOGGER.debug("[API] Creating a new sponsor with information: {}", sponsor);

        Map<String, Object> response = new LinkedHashMap<>();
        response.put("message", "Sponsor created successfully");
        response.put("sponsor", sponsorService.add(sponsor));
        return response;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    public Sponsor getDetails(@PathVariable("id") Long id) throws SponsorNotFoundException {
        LOGGER.debug("[API] Finding user with id: {}", id);
        return sponsorService.findById(id);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/{id}")
    public Map<String, Object> update(@PathVariable("id") Long id,
                                      @Valid @RequestBody Sponsor sponsor) throws SponsorNotFoundException {
        LOGGER.debug("[API] Updating a sponsor with information: {}", sponsor);

        Map<String, Object> response = new LinkedHashMap<>();
        response.put("message", "Sponsor updated successfully");
        response.put("sponsor", sponsorService.update(sponsor));
        return response;
    }


    @RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
    public Map<String, Object> delete(@PathVariable("id") Long id) throws SponsorNotFoundException {
        LOGGER.debug("[API] Deleting a sponsor with id: {}", id);

        Map<String, Object> response = new HashMap<>();
        response.put("message", "Sponsor deleted successfully");
        response.put("sponsor", sponsorService.deleteById(id));
        return response;
    }
}
