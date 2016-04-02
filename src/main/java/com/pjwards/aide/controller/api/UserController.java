package com.pjwards.aide.controller.api;


import com.pjwards.aide.domain.User;
import com.pjwards.aide.exception.UserNotFoundException;
import com.pjwards.aide.service.user.UserService;
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
@RequestMapping("/api/users")
public class UserController {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @RequestMapping(method = RequestMethod.GET)
    public List<User> getAll() {
        LOGGER.debug("[API] Finding all users.");
        return userService.findAll();
    }

    @RequestMapping(method = RequestMethod.POST)
    public Map<String, Object> create(@Valid @RequestBody User user) {
        LOGGER.debug("[API] Creating a new user with information: {}", user);

        Map<String, Object> response = new LinkedHashMap<>();
        response.put("message", "User created successfully");
        response.put("user", userService.add(user));
        return response;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    public User getDetails(@PathVariable("id") Long id) throws UserNotFoundException {
        LOGGER.debug("[API] Finding user with id: {}", id);
        return userService.findById(id);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/{id}")
    public Map<String, Object> update(@PathVariable("id") Long id,
                                      @Valid @RequestBody User user) throws UserNotFoundException {
        LOGGER.debug("[API] Updating a user with information: {}", user);

        Map<String, Object> response = new LinkedHashMap<>();
        response.put("message", "User updated successfully");
        response.put("user", userService.update(user));
        return response;
    }


    @RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
    public Map<String, Object> delete(@PathVariable("id") Long id) throws UserNotFoundException {
        LOGGER.debug("[API] Deleting a user with id: {}", id);

        Map<String, Object> response = new HashMap<>();
        response.put("message", "User deleted successfully");
        response.put("user", userService.deleteById(id));
        return response;
    }
}
