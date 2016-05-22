package com.pjwards.aide.service.room;

import com.pjwards.aide.domain.Conference;
import com.pjwards.aide.domain.Room;
import com.pjwards.aide.domain.User;
import com.pjwards.aide.domain.forms.RoomForm;
import com.pjwards.aide.exception.RoomNotFoundException;
import com.pjwards.aide.repository.ConferenceRepository;
import com.pjwards.aide.repository.RoomRepository;
import com.pjwards.aide.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class RoomServiceImpl implements RoomService {

    private static final Logger LOGGER = LoggerFactory.getLogger(RoomServiceImpl.class);

    private RoomRepository roomRepository;
    private ConferenceRepository conferenceRepository;
    private UserRepository userRepository;

    @Autowired
    public RoomServiceImpl(RoomRepository roomRepository,
                           ConferenceRepository conferenceRepository,
                           UserRepository userRepository) {
        this.roomRepository = roomRepository;
        this.conferenceRepository = conferenceRepository;
        this.userRepository = userRepository;
    }

    @Transactional(readOnly = true)
    @Override
    public List<Room> findAll() {
        LOGGER.debug("Finding all rooms.");

        List<Room> rooms = roomRepository.findAll();
        LOGGER.debug("Found {} rooms.", rooms.size());

        return rooms;
    }

    @Transactional
    @Override
    public Room add(Room added) {
        LOGGER.debug("Creating a new room with information: {}", added);

        added = roomRepository.save(added);
        LOGGER.debug("Added a room with information: {}", added);

        return added;
    }


    @Transactional(readOnly = true, rollbackFor = {RoomNotFoundException.class})
    @Override
    public Room findById(Long id) throws RoomNotFoundException {
        LOGGER.debug("Finding room with id: {}", id);

        Room found = roomRepository.findOne(id);
        LOGGER.debug("Found room with information: {}", found);

        if (found == null) {
            LOGGER.debug("No room found with id: {}", id);
            throw new RoomNotFoundException("No room found with id: " + id);
        }

        return found;
    }

    @Transactional(rollbackFor = {RoomNotFoundException.class})
    @Override
    public Room update(Room updated) throws RoomNotFoundException {
        LOGGER.debug("Updating a room with information: {}", updated);

        Room found = findById(updated.getId());
        found.update(updated);
        roomRepository.save(found);
        LOGGER.debug("Updated the information of a room to: {}", found);

        return found;
    }

    @Transactional(rollbackFor = {RoomNotFoundException.class})
    @Override
    public Room deleteById(Long id) throws RoomNotFoundException {
        LOGGER.debug("Deleting a room with id: {}", id);

        Room deleted = findById(id);
        roomRepository.delete(deleted);
        LOGGER.debug("Deleting room: {}", deleted);

        return deleted;
    }

    @Transactional
    @Override
    public Room create(RoomForm form) {
        LOGGER.debug("Create a user with Info: {}", form);

        Conference conference = conferenceRepository.findOne(form.getConferenceId());

        Room room = new Room.Builder(
                form.getName(),
                form.getLocation(),
                form.getDescription()).conference(conference).build();

        Set<User> managers = null;
        if (form.getManagers() != null) {
            managers = form.getManagers().stream().map(id -> userRepository.findOne(id)).collect(Collectors.toSet());
            room.setManagerSet(managers);
        }

        roomRepository.save(room);

        if (managers != null) {
            for (User manager : managers) {
                Set<Room> rooms = manager.getRoomSet();
                rooms.add(room);
                manager.setRoomSet(rooms);
                userRepository.save(manager);
            }
        }

        LOGGER.debug("Successfully created");
        return room;
    }

    @Transactional
    @Override
    public Room update(RoomForm form, Long id) throws RoomNotFoundException {
        LOGGER.debug("Create a user with Info: {}", form);

        Room room = findById(id);

        room
                .setName(form.getName())
                .setLocation(form.getLocation())
                .setDescription(form.getDescription());

        Set<User> managers = null;
        if (form.getManagers() != null) {
            managers = form.getManagers().stream().map(userId -> userRepository.findOne(userId)).collect(Collectors.toSet());
            room.setManagerSet(managers);
        }

        roomRepository.save(room);

        if (managers != null) {
            for (User manager : managers) {
                Set<Room> rooms = manager.getRoomSet();
                rooms.add(room);
                manager.setRoomSet(rooms);
                userRepository.save(manager);
            }
        }

        LOGGER.debug("Successfully updated");
        return room;
    }
}
