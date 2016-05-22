package com.pjwards.aide.service.presence;

import com.pjwards.aide.domain.Conference;
import com.pjwards.aide.domain.Presence;
import com.pjwards.aide.exception.PresenceNotFoundException;
import com.pjwards.aide.repository.PresenceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PresenceServiceImpl implements PresenceService{
    private static final Logger LOGGER = LoggerFactory.getLogger(PresenceService.class);

    private PresenceRepository presenceRepository;

    @Autowired
    public PresenceServiceImpl(PresenceRepository presenceRepository){
        this.presenceRepository = presenceRepository;
    }

    @Transactional(readOnly = true)
    @Override
    public List<Presence> findAll() {
        LOGGER.debug("Finding all conferenceRoles");

        List<Presence> presences = presenceRepository.findAll();
        LOGGER.debug("Found {} conferenceRoles", presences.size());

        return presences;
    }

    @Transactional
    @Override
    public Presence add(Presence added) {
        LOGGER.debug("Create a conferenceRole with Info: {}", added);

        added = presenceRepository.save(added);

        LOGGER.debug("Successfully created");

        return added;
    }

    @Transactional(readOnly = true, rollbackFor = {PresenceNotFoundException.class})
    @Override
    public Presence findById(Long id) throws PresenceNotFoundException {
        LOGGER.debug("Find a conferenceRole by Id: {}", id);

        Presence found = presenceRepository.findOne(id);

        if(found == null){
            LOGGER.debug("Not Found conferenceRole by Id: {}", id);
            throw new PresenceNotFoundException("Not Found conferenceRole by Id: " + id);
        }

        LOGGER.debug("Find the conferenceRole: {}", found);

        return found;
    }

    @Transactional(rollbackFor = {PresenceNotFoundException.class})
    @Override
    public Presence update(Presence updated) throws PresenceNotFoundException {
        LOGGER.debug("Update the conferenceRole with Info: {}", updated);

        Presence found = findById(updated.getId());
        found.update(updated);
        presenceRepository.save(found);

        LOGGER.debug("Successfully updated");

        return found;
    }

    @Transactional(rollbackFor = {PresenceNotFoundException.class})
    @Override
    public Presence deleteById(Long id) throws PresenceNotFoundException {
        LOGGER.debug("Delete the user by Id: {}", id);

        Presence deleted = findById(id);
        presenceRepository.delete(deleted);

        LOGGER.debug("Successfully deleted Info: {}", deleted);

        return deleted;
    }

    @Transactional(readOnly = true, rollbackFor = {PresenceNotFoundException.class})
    @Override
    public List<Presence> findByConference(Conference conference) {
        LOGGER.debug("Find a conferenceRole by ConferenceId: {}", conference);

        List<Presence> found = presenceRepository.findAllByConferenceSet(conference);

        LOGGER.debug("Find the conferenceRole size: {}", found.size());

        return found;
    }

}
