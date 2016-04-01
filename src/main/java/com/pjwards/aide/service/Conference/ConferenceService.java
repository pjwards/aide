package com.pjwards.aide.service.conference;

import com.pjwards.aide.domain.Conference;
import com.pjwards.aide.exception.ConferenceNotFoundException;

import java.util.List;

public interface ConferenceService {

    /**
     * Returns a list of conferences.
     *
     * @return The list of conferences
     */
    public List<Conference> findAll();

    /**
     * Adds a new conference.
     *
     * @param added The information of the added conference.
     * @return The added conference.
     */
    public Conference add(Conference added);

    /**
     * Finds a conference.
     *
     * @param id The id of the wanted conference.
     * @return The found conference.
     * @throws ConferenceNotFoundException if no conference is found with the given id.
     */
    public Conference findById(Long id) throws ConferenceNotFoundException;

    /**
     * Updates the information of conference.
     *
     * @param updated The information of the updated conference.
     * @return The updated conference.
     * @throws ConferenceNotFoundException If no conference is found with the given id.
     */
    public Conference update(Conference updated) throws ConferenceNotFoundException;

    /**
     * Deletes a conference.
     *
     * @param id The id of the deleted conference.
     * @return The deleted conference.
     * @throws ConferenceNotFoundException if no conference is found with the given id.
     */
    public Conference deleteById(Long id) throws ConferenceNotFoundException;
}
