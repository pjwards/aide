package com.pjwards.aide.service.pass;

import com.pjwards.aide.domain.Pass;
import com.pjwards.aide.exception.PassNotFoundException;
import com.pjwards.aide.repository.PassRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PassServiceImpl implements PassService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PassServiceImpl.class);

    private PassRepository passRepository;

    @Autowired
    public PassServiceImpl(PassRepository passRepository) {
        this.passRepository = passRepository;
    }

    @Transactional(readOnly = true)
    @Override
    public List<Pass> findAll() {
        LOGGER.debug("Finding all passs.");

        List<Pass> passs = passRepository.findAll();
        LOGGER.debug("Found {} passs.", passs.size());

        return passs;
    }

    @Transactional
    @Override
    public Pass add(Pass added) {
        LOGGER.debug("Creating a new pass with information: {}", added);

        added =  passRepository.save(added);
        LOGGER.debug("Added a pass with information: {}", added);

        return added;
    }


    @Transactional(readOnly = true, rollbackFor = {PassNotFoundException.class})
    @Override
    public Pass findById(Long id) throws PassNotFoundException {
        LOGGER.debug("Finding pass with id: {}", id);

        Pass found = passRepository.findOne(id);
        LOGGER.debug("Found pass with information: {}", found);

        if (found == null) {
            LOGGER.debug("No pass found with id: {}", id);
            throw new PassNotFoundException("No pass found with id: " + id);
        }

        return found;
    }

    @Transactional(rollbackFor = {PassNotFoundException.class})
    @Override
    public Pass update(Pass updated) throws PassNotFoundException {
        LOGGER.debug("Updating a pass with information: {}", updated);

        Pass found = findById(updated.getId());
        found.update(updated);
        passRepository.save(found);
        LOGGER.debug("Updated the information of a pass to: {}", found);

        return found;
    }

    @Transactional(rollbackFor = {PassNotFoundException.class})
    @Override
    public Pass deleteById(Long id) throws PassNotFoundException {
        LOGGER.debug("Deleting a pass with id: {}", id);

        Pass deleted = findById(id);
        passRepository.delete(deleted);
        LOGGER.debug("Deleting pass: {}", deleted);

        return deleted;
    }
}
