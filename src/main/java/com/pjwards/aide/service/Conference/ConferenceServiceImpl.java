package com.pjwards.aide.service.conference;

import com.pjwards.aide.domain.Conference;
import com.pjwards.aide.exception.ConferenceNotFoundException;
import com.pjwards.aide.repository.ConferenceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ConferenceServiceImpl implements ConferenceService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ConferenceServiceImpl.class);

    private ConferenceRepository conferenceRepository;

    @Autowired
    public ConferenceServiceImpl(ConferenceRepository conferenceRepository) {
        this.conferenceRepository = conferenceRepository;
    }

    @Transactional(readOnly = true)
    @Override
    public List<Conference> findAll() {
        LOGGER.debug("Finding all conferences.");

        List<Conference> conferences = conferenceRepository.findAll();
        LOGGER.debug("Found {} conferences.", conferences.size());

        return conferences;
    }

    @Transactional
    @Override
    public Conference add(Conference created) {
        LOGGER.debug("Creating a new conference with information: {}", created);

        created =  conferenceRepository.save(created);
        LOGGER.debug("Added a conference with information: {}", created);

        return created;
    }


    @Transactional(readOnly = true, rollbackFor = {ConferenceNotFoundException.class})
    @Override
    public Conference findById(Long id) throws ConferenceNotFoundException {
        LOGGER.debug("Finding conference with id: {}", id);

        Conference found = conferenceRepository.findOne(id);
        LOGGER.debug("Found conference with information: {}", found);

        if (found == null) {
            LOGGER.debug("No conference found with id: {}", id);
            throw new ConferenceNotFoundException("No conference found with id: " + id);
        }

        return found;
    }

    @Transactional(rollbackFor = {ConferenceNotFoundException.class})
    @Override
    public Conference update(Conference updated) throws ConferenceNotFoundException {
        LOGGER.debug("Updating a conference with information: {}", updated);

        Conference found = findById(updated.getId());
        found.update(updated);
        conferenceRepository.save(found);
        LOGGER.debug("Updated the information of a conference to: {}", found);

        return found;
    }

    @Transactional(rollbackFor = {ConferenceNotFoundException.class})
    @Override
    public Conference deleteById(Long id) throws ConferenceNotFoundException {
        LOGGER.debug("Deleting a conference with id: {}", id);

        Conference deleted = findById(id);
        conferenceRepository.delete(deleted);
        LOGGER.debug("Deleting conference: {}", deleted);

        return deleted;
    }
}
