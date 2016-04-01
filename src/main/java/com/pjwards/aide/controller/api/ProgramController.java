package com.pjwards.aide.controller.api;

import com.pjwards.aide.domain.Program;
import com.pjwards.aide.exception.ProgramNotFoundException;
import com.pjwards.aide.exception.WrongInputDateException;
import com.pjwards.aide.service.program.ProgramService;
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
@RequestMapping("/api/programs")
public class ProgramController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProgramController.class);

    @Autowired
    private ProgramService programService;

    @RequestMapping(method = RequestMethod.GET)
    public List<Program> getAll() {
        LOGGER.debug("[API] Finding all programs.");
        return programService.findAll();
    }

    @RequestMapping(method = RequestMethod.POST)
    public Map<String, Object> create(@Valid @RequestBody Program program) throws WrongInputDateException {
        LOGGER.debug("[API] Creating a new program with information: {}", program);

        Map<String, Object> response = new LinkedHashMap<>();
        response.put("message", "Program created successfully");
        response.put("program", programService.add(program));
        return response;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    public Program getDetails(@PathVariable("id") Long id) throws ProgramNotFoundException {
        LOGGER.debug("[API] Finding program with id: {}", id);
        return programService.findById(id);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/{id}")
    public Map<String, Object> update(@PathVariable("id") Long id,
                                      @Valid @RequestBody Program program) throws ProgramNotFoundException, WrongInputDateException {
        LOGGER.debug("[API] Updating a program with information: {}", program);

        Map<String, Object> response = new LinkedHashMap<>();
        response.put("message", "Program updated successfully");
        response.put("program", programService.update(program));
        return response;
    }


    @RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
    public Map<String, Object> delete(@PathVariable("id") Long id) throws ProgramNotFoundException {
        LOGGER.debug("[API] Deleting a program with id: {}", id);

        Map<String, Object> response = new HashMap<>();
        response.put("message", "Program deleted successfully");
        response.put("program", programService.deleteById(id));
        return response;
    }
}