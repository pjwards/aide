package com.pjwards.aide.controller.api;

import com.pjwards.aide.domain.Message;
import com.pjwards.aide.exception.MessageNotFoundException;
import com.pjwards.aide.service.message.MessageService;
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
@RequestMapping("/api/messages")
public class MessageController {

    private static final Logger LOGGER = LoggerFactory.getLogger(MessageController.class);

    @Autowired
    private MessageService messageService;

    @RequestMapping(method = RequestMethod.GET)
    public List<Message> getAll() {
        LOGGER.debug("[API] Finding all messages.");
        return messageService.findAll();
    }

    @RequestMapping(method = RequestMethod.POST)
    public Map<String, Object> create(@Valid @RequestBody Message message) {
        LOGGER.debug("[API] Creating a new message with information: {}", message);

        Map<String, Object> response = new LinkedHashMap<>();
        response.put("message", "Message created successfully");
        response.put("message", messageService.add(message));
        return response;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    public Message getDetails(@PathVariable("id") Long id) throws MessageNotFoundException {
        LOGGER.debug("[API] Finding message with id: {}", id);
        return messageService.findById(id);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/{id}")
    public Map<String, Object> update(@PathVariable("id") Long id,
                                      @Valid @RequestBody Message message) throws MessageNotFoundException {
        LOGGER.debug("[API] Updating a message with information: {}", message);

        Map<String, Object> response = new LinkedHashMap<>();
        response.put("message", "Message updated successfully");
        response.put("message", messageService.update(message));
        return response;
    }


    @RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
    public Map<String, Object> delete(@PathVariable("id") Long id) throws MessageNotFoundException {
        LOGGER.debug("[API] Deleting a message with id: {}", id);

        Map<String, Object> response = new HashMap<>();
        response.put("message", "Message deleted successfully");
        response.put("message", messageService.deleteById(id));
        return response;
    }
}
