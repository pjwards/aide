package com.pjwards.aide.service.conference;

import com.pjwards.aide.domain.Conference;
import com.pjwards.aide.domain.enums.Status;
import com.pjwards.aide.domain.forms.ConferenceForm;
import com.pjwards.aide.exception.ConferenceNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ConferenceService {

    /**
     * Returns a list of conferences.
     *
     * @return The list of conferences
     */
    public List<Conference> findAll();

    /**
     * Returns a list of conferences by status.
     *
     * @param status conference status
     * @return The list of conferences
     */
    public List<Conference> findAll(Status status);

    /**
     * Returns size of conferences.
     *
     * @return The size of conferences
     */
    public long count();

    /**
     * Returns a list of conferences by page.
     *
     * @param pageable request page
     * @return The list of conferences
     */
    public Page<Conference> findAll(Pageable pageable);

    /**
     * Returns a list of conferences by page and keyword.
     *
     * @param pageable request page
     * @param keyword     keyword
     * @return The list of conferences
     */
    public Page<Conference> findAll(Pageable pageable, String keyword);

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

    /**
     * Create conference by created form
     *
     * @param form conference created form
     * @return The created conference.
     */
    public Conference create(ConferenceForm form);

    /**
     * Update conference by updated form
     *
     * @param form conference updated form
     * @param id   conference id
     * @return The updated conference.
     */
    public Conference update(ConferenceForm form, Long id) throws ConferenceNotFoundException;
}
