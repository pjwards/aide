package com.pjwards.aide.controller.message;

import com.pjwards.aide.domain.CurrentUser;
import com.pjwards.aide.domain.Message;
import com.pjwards.aide.domain.Room;
import com.pjwards.aide.domain.User;
import com.pjwards.aide.exception.RoomNotFoundException;
import com.pjwards.aide.exception.UserNotFoundException;
import com.pjwards.aide.service.message.MessageService;
import com.pjwards.aide.service.room.RoomService;
import com.pjwards.aide.service.user.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/messages")
public class MessageDetailController {

    private static final Logger LOGGER = LoggerFactory.getLogger(com.pjwards.aide.controller.api.MessageController.class);

    @Autowired
    private MessageService messageService;

    @Autowired
    private RoomService roomService;

    @RequestMapping(method = RequestMethod.GET)
    public List<Message> getAll() {
        LOGGER.debug("Finding all messages.");
        return messageService.findAll();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/rooms/{id}")
    public List<Message> getAllByRoom(@PathVariable("id") Long id) throws RoomNotFoundException {
        LOGGER.debug("Finding all messages.");
        Room room = roomService.findById(id);
        return messageService.findAllByRoom(room);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/rooms/{id}")
    public Map<String, Object> create(@ModelAttribute("currentUser") CurrentUser currentUser,
                                      @PathVariable("id") Long id,
                                      @RequestParam(value = "message") String messageContent) {
        LOGGER.debug("Creating a new message with information: {}", messageContent);

        Map<String, Object> response = new LinkedHashMap<>();

        User sender = currentUser.getUser();
        Room room;
        try {
            room = roomService.findById(id);
        } catch (RoomNotFoundException e) {
            response.put("message", "Message create failed.");
            return response;
        }
        Message message = new Message.Builder(messageContent, sender, room).build();
        message = messageService.add(message);

        response.put("message", "Message created successfully");
        response.put("chat", messageService.add(message));
        return response;
    }
}