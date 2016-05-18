package com.pjwards.aide.service.message;

import com.pjwards.aide.domain.Message;
import com.pjwards.aide.domain.Room;
import com.pjwards.aide.domain.enums.Status;
import com.pjwards.aide.domain.validators.ImageValidator;
import com.pjwards.aide.exception.MessageNotFoundException;
import com.pjwards.aide.repository.AssetsRepository;
import com.pjwards.aide.repository.ContactRepository;
import com.pjwards.aide.repository.MessageRepository;
import com.pjwards.aide.repository.UserRepository;
import com.pjwards.aide.util.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MessageServiceImpl implements MessageService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MessageServiceImpl.class);

    private MessageRepository messageRepository;
    private ContactRepository contactRepository;
    private AssetsRepository assetsRepository;
    private UserRepository userRepository;
    private ImageValidator imageValidator;
    private Utils utils;

    @Autowired
    public MessageServiceImpl(MessageRepository messageRepository,
                                 ContactRepository contactRepository,
                                 AssetsRepository assetsRepository,
                                 UserRepository userRepository,
                                 ImageValidator imageValidator,
                                 Utils utils) {
        this.messageRepository = messageRepository;
        this.contactRepository = contactRepository;
        this.assetsRepository = assetsRepository;
        this.userRepository = userRepository;
        this.imageValidator = imageValidator;
        this.utils = utils;
    }

    @Transactional(readOnly = true)
    @Override
    public List<Message> findAll() {
        LOGGER.debug("Finding all messages.");

        List<Message> messages = messageRepository.findAll();
        LOGGER.debug("Found {} messages.", messages.size());

        return messages;
    }

    @Transactional(readOnly = true)
    @Override
    public List<Message> findAllByRoom(Room room) {
        LOGGER.debug("Finding all messages by room.");

        List<Message> messages = messageRepository.findAllByRoom(room);
        LOGGER.debug("Found {} messages.", messages.size());

        return messages;
    }

    @Transactional
    @Override
    public Message add(Message added) {
        LOGGER.debug("Creating a new message with information: {}", added);

        added = messageRepository.save(added);
        LOGGER.debug("Added a message with information: {}", added);

        return added;
    }


    @Transactional(readOnly = true, rollbackFor = {MessageNotFoundException.class})
    @Override
    public Message findById(Long id) throws MessageNotFoundException {
        LOGGER.debug("Finding message with id: {}", id);

        Message found = messageRepository.findOne(id);
        LOGGER.debug("Found message with information: {}", found);

        if (found == null) {
            LOGGER.debug("No message found with id: {}", id);
            throw new MessageNotFoundException("No message found with id: " + id);
        }

        return found;
    }

    @Transactional(rollbackFor = {MessageNotFoundException.class})
    @Override
    public Message update(Message updated) throws MessageNotFoundException {
        LOGGER.debug("Updating a message with information: {}", updated);

        Message found = findById(updated.getId());
        found.update(updated);
        messageRepository.save(found);
        LOGGER.debug("Updated the information of a message to: {}", found);

        return found;
    }

    @Transactional(rollbackFor = {MessageNotFoundException.class})
    @Override
    public Message deleteById(Long id) throws MessageNotFoundException {
        LOGGER.debug("Deleting a message with id: {}", id);

        Message deleted = findById(id);
        messageRepository.delete(deleted);
        LOGGER.debug("Deleting message: {}", deleted);

        return deleted;
    }
}
