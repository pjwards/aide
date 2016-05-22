package com.pjwards.aide.service.user;

import com.pjwards.aide.domain.Conference;
import com.pjwards.aide.domain.User;
import com.pjwards.aide.domain.forms.SignUpForm;
import com.pjwards.aide.domain.forms.UserUpdatePasswordForm;
import com.pjwards.aide.exception.UserNotFoundException;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<User> findAll();

    User add(User added);

    User findById(Long id) throws UserNotFoundException;

    User update(User updated) throws UserNotFoundException;

    User deleteById(Long id) throws UserNotFoundException;

    Optional<User> findByEmail(String email);

    User create(SignUpForm form);

    User updatePassword(UserUpdatePasswordForm form);

    User createDummy(SignUpForm form, Conference conference);
}
