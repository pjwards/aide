package com.pjwards.aide.service.user;

import com.pjwards.aide.domain.User;
import com.pjwards.aide.exception.UserNotFoundException;
import com.pjwards.aide.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserServiceImpl implements UserService{
    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

    private UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Transactional(readOnly = true)
    @Override
    public List<User> findAll() {
        LOGGER.debug("Finding all users");

        List<User> users = userRepository.findAll();
        LOGGER.debug("Found {} users", users.size());

        return users;
    }

    @Transactional
    @Override
    public User add(User added) {
        LOGGER.debug("Create a user with Info: {}", added);

        added = userRepository.save(added);
        LOGGER.debug("Successfully created");

        return added;
    }

    @Transactional(readOnly = true, rollbackFor = {UserNotFoundException.class})
    @Override
    public User findById(Long id) throws UserNotFoundException {
        LOGGER.debug("Find a user by Id: {}", id);

        User found = userRepository.findOne(id);

        if(found == null){
            LOGGER.debug("Not Found User by Id: {}", id);
            throw new UserNotFoundException("Not Found User by Id: " + id);
        }

        LOGGER.debug("Find the user: {}", found);

        return found;
    }

    @Transactional(rollbackFor = {UserNotFoundException.class})
    @Override
    public User update(User updated) throws UserNotFoundException {
        LOGGER.debug("Update the user with Info: {}", updated);

        User found = findById(updated.getId());
        found.update(updated);
        userRepository.save(found);

        LOGGER.debug("Successfully updated");

        return found;
    }

    @Transactional(rollbackFor = {UserNotFoundException.class})
    @Override
    public User deleteById(Long id) throws UserNotFoundException {
        LOGGER.debug("Delete the user by Id: {}", id);

        User deleted = findById(id);
        userRepository.delete(deleted);

        LOGGER.debug("Successfully deleted Info: {}", deleted);

        return deleted;
    }
}
