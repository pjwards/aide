package com.pjwards.aide.service.room;

import com.pjwards.aide.domain.Room;
import com.pjwards.aide.exception.RoomNotFoundException;

import java.util.List;

public interface RoomService {
    /**
     * Returns a list of rooms.
     *
     * @return The list of rooms
     */
    public List<Room> findAll();

    /**
     * Adds a new room.
     *
     * @param added The information of the added room.
     * @return The added room.
     */
    public Room add(Room added);

    /**
     * Finds a room.
     *
     * @param id The id of the wanted room.
     * @return The found room.
     * @throws RoomNotFoundException if no room is found with the given id.
     */
    public Room findById(Long id) throws RoomNotFoundException;

    /**
     * Updates the information of room.
     *
     * @param updated The information of the updated room.
     * @return The updated room.
     * @throws RoomNotFoundException If no room is found with the given id.
     */
    public Room update(Room updated) throws RoomNotFoundException;

    /**
     * Deletes a room.
     *
     * @param id The id of the deleted room.
     * @return The deleted room.
     * @throws RoomNotFoundException if no room is found with the given id.
     */
    public Room deleteById(Long id) throws RoomNotFoundException;
}
