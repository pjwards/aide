package com.pjwards.aide.service.session;

import com.pjwards.aide.domain.Session;
import com.pjwards.aide.exception.SessionNotFoundException;
import com.pjwards.aide.exception.WrongInputDateException;

import java.util.List;

public interface SessionService {

    /**
     * Returns a list of sessions.
     *
     * @return The list of sessions
     */
    public List<Session> findAll();

    /**
     * Adds a new session.
     *
     * @param added The information of the added session.
     * @return The added session.
     */
    public Session add(Session added) throws WrongInputDateException;

    /**
     * Finds a session.
     *
     * @param id The id of the wanted session.
     * @return The found session.
     * @throws SessionNotFoundException if no session is found with the given id.
     */
    public Session findById(Long id) throws SessionNotFoundException;

    /**
     * Updates the information of session.
     *
     * @param updated The information of the updated session.
     * @return The updated session.
     * @throws SessionNotFoundException If no session is found with the given id.
     */
    public Session update(Session updated) throws SessionNotFoundException, WrongInputDateException;

    /**
     * Deletes a session.
     *
     * @param id The id of the deleted session.
     * @return The deleted session.
     * @throws SessionNotFoundException if no session is found with the given id.
     */
    public Session deleteById(Long id) throws SessionNotFoundException;
}

