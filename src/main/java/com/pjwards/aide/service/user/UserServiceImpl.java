package com.pjwards.aide.service.user;

import com.pjwards.aide.domain.Assets;
import com.pjwards.aide.domain.User;
import com.pjwards.aide.domain.forms.SignUpForm;
import com.pjwards.aide.domain.forms.UserUpdatePasswordForm;
import com.pjwards.aide.exception.UserNotFoundException;
import com.pjwards.aide.repository.AssetsRepository;
import com.pjwards.aide.repository.UserRepository;
import com.pjwards.aide.util.identicon.IdenticonGeneratorUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@PropertySource("classpath:file.properties")
public class UserServiceImpl implements UserService{
    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

    @Value("${file.identicon.filePath}")
    private String filePath;

    private final UserRepository userRepository;

    private final AssetsRepository assetsRepository;

    private final IdenticonGeneratorUtil identiconGeneratorUtil;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, AssetsRepository assetsRepository,
                           IdenticonGeneratorUtil identiconGeneratorUtil){
        this.userRepository = userRepository;
        this.assetsRepository = assetsRepository;
        this.identiconGeneratorUtil = identiconGeneratorUtil;
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

    @Transactional(readOnly = true)
    @Override
    public Optional<User> findByEmail(String email) {
        LOGGER.debug("Getting user by email={}", email.replaceFirst("@.*", "@***"));
        return userRepository.findOneByEmail(email);
    }

    @Transactional
    @Override
    public User create(SignUpForm form) {
        LOGGER.debug("Create a user with Info: {}", form);

        User user = new User.Builder(
                form.getName(),
                form.getEmail(),
                new BCryptPasswordEncoder().encode(form.getPassword())
        ).company("").description("").build();

        Map<String, String> fileMap;
        try {
            fileMap = identiconGeneratorUtil.generator(form.getEmail());
        } catch (IOException e) {
            return userRepository.save(user);
        }

        File avatar = new File(filePath + fileMap.get("realPath"));

        Assets assets = new Assets.Builder(fileMap.get("fileName"), fileMap.get("realPath"), avatar.length(), 0).build();
        assets = assetsRepository.save(assets);

        user.setAssets(assets);
        user=userRepository.save(user);

        assets.setUser(user);
        assetsRepository.save(assets);

        LOGGER.debug("Successfully created");

        return user;
    }

    @Transactional(rollbackFor = {UserNotFoundException.class})
    @Override
    public User updatePassword(UserUpdatePasswordForm form) {
        User user = userRepository.getOne(form.getId());
        user.update(user.getName(), user.getEmail(), new BCryptPasswordEncoder().encode(form.getPassword()),
                user.getCreatedDate(), user.getLastDate(), user.getCompany(), user.getRole(), user.getDescription());
        return userRepository.save(user);
    }


}
