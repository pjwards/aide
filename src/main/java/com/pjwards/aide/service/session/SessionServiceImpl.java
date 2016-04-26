package com.pjwards.aide.service.session;

import com.pjwards.aide.domain.Session;
import com.pjwards.aide.exception.SessionNotFoundException;
import com.pjwards.aide.exception.WrongInputDateException;
import com.pjwards.aide.repository.SessionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SessionServiceImpl implements SessionService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SessionServiceImpl.class);

    private SessionRepository sessionRepository;

    @Autowired
    public SessionServiceImpl(SessionRepository sessionRepository) {
        this.sessionRepository = sessionRepository;
    }

    @Transactional(readOnly = true)
    @Override
    public List<Session> findAll() {
        LOGGER.debug("Finding all sessions.");

        List<Session> sessions = sessionRepository.findAll();
        LOGGER.debug("Found {} sessions.", sessions.size());

        return sessions;
    }

    @Transactional
    @Override
    public Session add(Session added) throws WrongInputDateException {
        LOGGER.debug("Creating a new session with information: {}", added);

        added =  sessionRepository.save(added);
        LOGGER.debug("Added a session with information: {}", added);

        return added;
    }


    @Transactional(readOnly = true, rollbackFor = {SessionNotFoundException.class})
    @Override
    public Session findById(Long id) throws SessionNotFoundException {
        LOGGER.debug("Finding session with id: {}", id);

        Session found = sessionRepository.findOne(id);
        LOGGER.debug("Found session with information: {}", found);

        if (found == null) {
            LOGGER.debug("No session found with id: {}", id);
            throw new SessionNotFoundException("No session found with id: " + id);
        }

        return found;
    }

    @Transactional(rollbackFor = {SessionNotFoundException.class})
    @Override
    public Session update(Session updated) throws SessionNotFoundException, WrongInputDateException {
        LOGGER.debug("Updating a session with information: {}", updated);

        Session found = findById(updated.getId());
        found.update(updated);
        sessionRepository.save(found);
        LOGGER.debug("Updated the information of a session to: {}", found);

        return found;
    }

    @Transactional(rollbackFor = {SessionNotFoundException.class})
    @Override
    public Session deleteById(Long id) throws SessionNotFoundException {
        LOGGER.debug("Deleting a session with id: {}", id);

        Session deleted = findById(id);
        sessionRepository.delete(deleted);
        LOGGER.debug("Deleting session: {}", deleted);

        return deleted;
    }
}
