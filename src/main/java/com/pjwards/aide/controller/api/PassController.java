package com.pjwards.aide.controller.api;

import com.pjwards.aide.domain.Pass;
import com.pjwards.aide.exception.PassNotFoundException;
import com.pjwards.aide.service.pass.PassService;
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
@RequestMapping("/api/passes")
public class PassController {

    private static final Logger LOGGER = LoggerFactory.getLogger(PassController.class);

    @Autowired
    private PassService passService;

    @RequestMapping(method = RequestMethod.GET)
    public List<Pass> getAll() {
        LOGGER.debug("[API] Finding all passes.");
        return passService.findAll();
    }

    @RequestMapping(method = RequestMethod.POST)
    public Map<String, Object> create(@Valid @RequestBody Pass pass) {
        LOGGER.debug("[API] Creating a new pass with information: {}", pass);

        Map<String, Object> response = new LinkedHashMap<>();
        response.put("message", "Pass created successfully");
        response.put("pass", passService.add(pass));
        return response;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    public Pass getDetails(@PathVariable("id") Long id) throws PassNotFoundException {
        LOGGER.debug("[API] Finding pass with id: {}", id);
        return passService.findById(id);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/{id}")
    public Map<String, Object> update(@PathVariable("id") Long id,
                                      @Valid @RequestBody Pass pass) throws PassNotFoundException {
        LOGGER.debug("[API] Updating a pass with information: {}", pass);

        Map<String, Object> response = new LinkedHashMap<>();
        response.put("message", "Pass updated successfully");
        response.put("pass", passService.update(pass));
        return response;
    }


    @RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
    public Map<String, Object> delete(@PathVariable("id") Long id) throws PassNotFoundException {
        LOGGER.debug("[API] Deleting a pass with id: {}", id);

        Map<String, Object> response = new HashMap<>();
        response.put("message", "Pass deleted successfully");
        response.put("pass", passService.deleteById(id));
        return response;
    }
}
