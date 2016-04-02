package com.pjwards.aide.service.conferencerole;


import com.pjwards.aide.domain.ConferenceRole;
import com.pjwards.aide.exception.ConferenceRoleNotFoundException;
import com.pjwards.aide.repository.ConferenceRoleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ConferenceRoleServiceImpl implements ConferenceRoleService{
    private static final Logger LOGGER = LoggerFactory.getLogger(ConferenceRoleService.class);

    private ConferenceRoleRepository conferenceRoleRepository;

    @Autowired
    public ConferenceRoleServiceImpl(ConferenceRoleRepository conferenceRoleRepository){
        this.conferenceRoleRepository = conferenceRoleRepository;
    }

    @Transactional(readOnly = true)
    @Override
    public List<ConferenceRole> findAll() {
        LOGGER.debug("Finding all conferenceRoles");

        List<ConferenceRole> conferenceRoles = conferenceRoleRepository.findAll();
        LOGGER.debug("Found {} conferenceRoles", conferenceRoles.size());

        return conferenceRoles;
    }

    @Transactional
    @Override
    public ConferenceRole add(ConferenceRole added) {
        LOGGER.debug("Create a conferenceRole with Info: {}", added);

        added = conferenceRoleRepository.save(added);
        LOGGER.debug("Successfully created");

        return added;
    }

    @Transactional(readOnly = true, rollbackFor = {ConferenceRoleNotFoundException.class})
    @Override
    public ConferenceRole findById(Long id) throws ConferenceRoleNotFoundException {
        LOGGER.debug("Find a conferenceRole by Id: {}", id);

        ConferenceRole found = conferenceRoleRepository.findOne(id);

        if(found == null){
            LOGGER.debug("Not Found conferenceRole by Id: {}", id);
            throw new ConferenceRoleNotFoundException("Not Found conferenceRole by Id: " + id);
        }

        LOGGER.debug("Find the conferenceRole: {}", found);

        return found;
    }

    @Transactional(rollbackFor = {ConferenceRoleNotFoundException.class})
    @Override
    public ConferenceRole update(ConferenceRole updated) throws ConferenceRoleNotFoundException {
        LOGGER.debug("Update the conferenceRole with Info: {}", updated);

        ConferenceRole found = findById(updated.getId());
        found.update(updated.getRole());
        conferenceRoleRepository.save(found);

        LOGGER.debug("Successfully updated");

        return found;
    }

    @Transactional(rollbackFor = {ConferenceRoleNotFoundException.class})
    @Override
    public ConferenceRole deleteById(Long id) throws ConferenceRoleNotFoundException {
        LOGGER.debug("Delete the user by Id: {}", id);

        ConferenceRole deleted = findById(id);
        conferenceRoleRepository.delete(deleted);

        LOGGER.debug("Successfully deleted Info: {}", deleted);

        return deleted;
    }
}
