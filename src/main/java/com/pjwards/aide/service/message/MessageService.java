package com.pjwards.aide.service.message;

import com.pjwards.aide.domain.Message;
import com.pjwards.aide.domain.Room;
import com.pjwards.aide.domain.enums.Status;
import com.pjwards.aide.exception.MessageNotFoundException;

import java.util.List;

public interface MessageService {

    /**
     * Returns a list of messages.
     *
     * @return The list of messages
     */
    public List<Message> findAll();

    /**
     * Returns a list of messages by room.
     *
     * @return The list of messages
     */
    public List<Message> findAllByRoom(Room room);

    /**
     * Adds a new message.
     *
     * @param added The information of the added message.
     * @return The added message.
     */
    public Message add(Message added);

    /**
     * Finds a message.
     *
     * @param id The id of the wanted message.
     * @return The found message.
     * @throws MessageNotFoundException if no message is found with the given id.
     */
    public Message findById(Long id) throws MessageNotFoundException;

    /**
     * Updates the information of message.
     *
     * @param updated The information of the updated message.
     * @return The updated message.
     * @throws MessageNotFoundException If no message is found with the given id.
     */
    public Message update(Message updated) throws MessageNotFoundException;

    /**
     * Deletes a message.
     *
     * @param id The id of the deleted message.
     * @return The deleted message.
     * @throws MessageNotFoundException if no message is found with the given id.
     */
    public Message deleteById(Long id) throws MessageNotFoundException;
}
