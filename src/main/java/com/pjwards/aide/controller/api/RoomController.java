package com.pjwards.aide.controller.api;

import com.pjwards.aide.domain.Room;
import com.pjwards.aide.exception.RoomNotFoundException;
import com.pjwards.aide.service.room.RoomService;
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
@RequestMapping("/api/rooms")
public class RoomController {

    private static final Logger LOGGER = LoggerFactory.getLogger(RoomController.class);

    @Autowired
    private RoomService roomService;

    @RequestMapping(method = RequestMethod.GET)
    public List<Room> getAll() {
        LOGGER.debug("[API] Finding all rooms.");
        return roomService.findAll();
    }

    @RequestMapping(method = RequestMethod.POST)
    public Map<String, Object> create(@Valid @RequestBody Room room) {
        LOGGER.debug("[API] Creating a new room with information: {}", room);

        Map<String, Object> response = new LinkedHashMap<>();
        response.put("message", "Room created successfully");
        response.put("room", roomService.add(room));
        return response;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    public Room getDetails(@PathVariable("id") Long id) throws RoomNotFoundException {
        LOGGER.debug("[API] Finding room with id: {}", id);
        return roomService.findById(id);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/{id}")
    public Map<String, Object> update(@PathVariable("id") Long id,
                                      @Valid @RequestBody Room room) throws RoomNotFoundException {
        LOGGER.debug("[API] Updating a room with information: {}", room);

        Map<String, Object> response = new LinkedHashMap<>();
        response.put("message", "Room updated successfully");
        response.put("room", roomService.update(room));
        return response;
    }


    @RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
    public Map<String, Object> delete(@PathVariable("id") Long id) throws RoomNotFoundException {
        LOGGER.debug("[API] Deleting a room with id: {}", id);

        Map<String, Object> response = new HashMap<>();
        response.put("message", "Room deleted successfully");
        response.put("room", roomService.deleteById(id));
        return response;
    }
}
