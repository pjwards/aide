package com.pjwards.aide.controller.api;

import com.pjwards.aide.domain.ProgramDate;
import com.pjwards.aide.exception.ProgramDateNotFoundException;
import com.pjwards.aide.service.programdate.ProgramDateService;
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
@RequestMapping("/api/program-dates")
public class ProgramDateController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProgramDateController.class);

    @Autowired
    private ProgramDateService programDateService;

    @RequestMapping(method = RequestMethod.GET)
    public List<ProgramDate> getAll() {
        LOGGER.debug("[API] Finding all program dates.");
        return programDateService.findAll();
    }

    @RequestMapping(method = RequestMethod.POST)
    public Map<String, Object> create(@Valid @RequestBody ProgramDate programDate) {
        LOGGER.debug("[API] Creating a new program date with information: {}", programDate);

        Map<String, Object> response = new LinkedHashMap<>();
        response.put("message", "Program date created successfully");
        response.put("programDate", programDateService.add(programDate));
        return response;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    public ProgramDate getDetails(@PathVariable("id") Long id) throws ProgramDateNotFoundException {
        LOGGER.debug("[API] Finding program date with id: {}", id);
        return programDateService.findById(id);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/{id}")
    public Map<String, Object> update(@PathVariable("id") Long id,
                                      @Valid @RequestBody ProgramDate programDate) throws ProgramDateNotFoundException {
        LOGGER.debug("[API] Updating a program date with information: {}", programDate);

        Map<String, Object> response = new LinkedHashMap<>();
        response.put("message", "Program date updated successfully");
        response.put("programDate", programDateService.update(programDate));
        return response;
    }


    @RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
    public Map<String, Object> delete(@PathVariable("id") Long id) throws ProgramDateNotFoundException {
        LOGGER.debug("[API] Deleting a program date with id: {}", id);

        Map<String, Object> response = new HashMap<>();
        response.put("message", "Program date deleted successfully");
        response.put("programDate", programDateService.deleteById(id));
        return response;
    }
}
