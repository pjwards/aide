package com.pjwards.aide.service.programdate;

import com.pjwards.aide.domain.ProgramDate;
import com.pjwards.aide.exception.ProgramDateNotFoundException;
import com.pjwards.aide.repository.ProgramDateRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProgramDateServiceImpl implements ProgramDateService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProgramDateServiceImpl.class);

    private ProgramDateRepository programDateRepository;

    @Autowired
    public ProgramDateServiceImpl(ProgramDateRepository programDateRepository) {
        this.programDateRepository = programDateRepository;
    }

    @Transactional(readOnly = true)
    @Override
    public List<ProgramDate> findAll() {
        LOGGER.debug("Finding all programDates.");

        List<ProgramDate> programDates = programDateRepository.findAll();
        LOGGER.debug("Found {} programDates.", programDates.size());

        return programDates;
    }

    @Transactional
    @Override
    public ProgramDate add(ProgramDate added) {
        LOGGER.debug("Creating a new programDate with information: {}", added);

        added =  programDateRepository.save(added);
        LOGGER.debug("Added a programDate with information: {}", added);

        return added;
    }


    @Transactional(readOnly = true, rollbackFor = {ProgramDateNotFoundException.class})
    @Override
    public ProgramDate findById(Long id) throws ProgramDateNotFoundException {
        LOGGER.debug("Finding programDate with id: {}", id);

        ProgramDate found = programDateRepository.findOne(id);
        LOGGER.debug("Found programDate with information: {}", found);

        if (found == null) {
            LOGGER.debug("No programDate found with id: {}", id);
            throw new ProgramDateNotFoundException("No programDate found with id: " + id);
        }

        return found;
    }

    @Transactional(rollbackFor = {ProgramDateNotFoundException.class})
    @Override
    public ProgramDate update(ProgramDate updated) throws ProgramDateNotFoundException {
        LOGGER.debug("Updating a programDate with information: {}", updated);

        ProgramDate found = findById(updated.getId());
        found.update(updated);
        programDateRepository.save(found);
        LOGGER.debug("Updated the information of a programDate to: {}", found);

        return found;
    }

    @Transactional(rollbackFor = {ProgramDateNotFoundException.class})
    @Override
    public ProgramDate deleteById(Long id) throws ProgramDateNotFoundException {
        LOGGER.debug("Deleting a programDate with id: {}", id);

        ProgramDate deleted = findById(id);
        programDateRepository.delete(deleted);
        LOGGER.debug("Deleting programDate: {}", deleted);

        return deleted;
    }
}
