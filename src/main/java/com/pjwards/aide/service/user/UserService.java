package com.pjwards.aide.service.user;

import com.pjwards.aide.domain.User;
import com.pjwards.aide.exception.UserNotFoundException;

import java.util.List;

public interface UserService {
    public List<User> findAll();

    public User add(User added);

    public User findById(Long id) throws UserNotFoundException;

    public User update(User updated) throws UserNotFoundException;

    public User deleteById(Long id) throws UserNotFoundException;

}
