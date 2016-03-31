package com.pjwards.aide.service.room;

import com.pjwards.aide.domain.Room;
import com.pjwards.aide.exception.RoomNotFoundException;
import com.pjwards.aide.repository.RoomRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public class RoomServiceImpl implements RoomService{

    private static final Logger LOGGER = LoggerFactory.getLogger(RoomServiceImpl.class);

    private RoomRepository roomRepository;

    @Autowired
    public RoomServiceImpl(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
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

        added =  roomRepository.save(added);
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
}
