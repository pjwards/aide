package com.pjwards.aide.controller.api;

import com.pjwards.aide.domain.Session;
import com.pjwards.aide.exception.SessionNotFoundException;
import com.pjwards.aide.exception.WrongInputDateException;
import com.pjwards.aide.service.session.SessionService;
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
@RequestMapping("/api/sessions")
public class SessionController {

    private static final Logger LOGGER = LoggerFactory.getLogger(SessionController.class);

    @Autowired
    private SessionService sessionService;

    @RequestMapping(method = RequestMethod.GET)
    public List<Session> getAll() {
        LOGGER.debug("[API] Finding all sessions.");
        return sessionService.findAll();
    }

    @RequestMapping(method = RequestMethod.POST)
    public Map<String, Object> create(@Valid @RequestBody Session session) throws WrongInputDateException {
        LOGGER.debug("[API] Creating a new session with information: {}", session);

        Map<String, Object> response = new LinkedHashMap<>();
        response.put("message", "Session created successfully");
        response.put("session", sessionService.add(session));
        return response;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    public Session getDetails(@PathVariable("id") Long id) throws SessionNotFoundException {
        LOGGER.debug("[API] Finding session with id: {}", id);
        return sessionService.findById(id);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/{id}")
    public Map<String, Object> update(@PathVariable("id") Long id,
                                      @Valid @RequestBody Session session) throws SessionNotFoundException, WrongInputDateException {
        LOGGER.debug("[API] Updating a session with information: {}", session);

        Map<String, Object> response = new LinkedHashMap<>();
        response.put("message", "Session updated successfully");
        response.put("session", sessionService.update(session));
        return response;
    }


    @RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
    public Map<String, Object> delete(@PathVariable("id") Long id) throws SessionNotFoundException {
        LOGGER.debug("[API] Deleting a session with id: {}", id);

        Map<String, Object> response = new HashMap<>();
        response.put("message", "Session deleted successfully");
        response.put("session", sessionService.deleteById(id));
        return response;
    }
}