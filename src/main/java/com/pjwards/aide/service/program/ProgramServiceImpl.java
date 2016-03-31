package com.pjwards.aide.service.program;

import com.pjwards.aide.domain.Program;
import com.pjwards.aide.exception.ProgramNotFoundException;
import com.pjwards.aide.exception.WrongInputDateException;
import com.pjwards.aide.repository.ProgramRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProgramServiceImpl implements ProgramService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProgramServiceImpl.class);

    private ProgramRepository programRepository;

    @Autowired
    public ProgramServiceImpl(ProgramRepository programRepository) {
        this.programRepository = programRepository;
    }

    @Transactional(readOnly = true)
    @Override
    public List<Program> findAll() {
        LOGGER.debug("Finding all programs.");

        List<Program> programs = programRepository.findAll();
        LOGGER.debug("Found {} programs.", programs.size());

        return programs;
    }

    @Transactional
    @Override
    public Program add(Program added) throws WrongInputDateException {
        LOGGER.debug("Creating a new program with information: {}", added);

        added.dateChecker();

        added =  programRepository.save(added);
        LOGGER.debug("Added a program with information: {}", added);

        return added;
    }


    @Transactional(readOnly = true, rollbackFor = {ProgramNotFoundException.class})
    @Override
    public Program findById(Long id) throws ProgramNotFoundException {
        LOGGER.debug("Finding program with id: {}", id);

        Program found = programRepository.findOne(id);
        LOGGER.debug("Found program with information: {}", found);

        if (found == null) {
            LOGGER.debug("No program found with id: {}", id);
            throw new ProgramNotFoundException("No program found with id: " + id);
        }

        return found;
    }

    @Transactional(rollbackFor = {ProgramNotFoundException.class})
    @Override
    public Program update(Program updated) throws ProgramNotFoundException, WrongInputDateException {
        LOGGER.debug("Updating a program with information: {}", updated);

        Program found = findById(updated.getId());
        found.update(updated);
        found.dateChecker();
        programRepository.save(found);
        LOGGER.debug("Updated the information of a program to: {}", found);

        return found;
    }

    @Transactional(rollbackFor = {ProgramNotFoundException.class})
    @Override
    public Program deleteById(Long id) throws ProgramNotFoundException {
        LOGGER.debug("Deleting a program with id: {}", id);

        Program deleted = findById(id);
        programRepository.delete(deleted);
        LOGGER.debug("Deleting program: {}", deleted);

        return deleted;
    }
}
