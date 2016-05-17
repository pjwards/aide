package com.pjwards.aide.service.programdate;

import com.pjwards.aide.domain.Conference;
import com.pjwards.aide.domain.ProgramDate;
import com.pjwards.aide.domain.forms.ProgramDateForm;
import com.pjwards.aide.exception.ProgramDateNotFoundException;
import com.pjwards.aide.repository.ConferenceRepository;
import com.pjwards.aide.repository.ProgramDateRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class ProgramDateServiceImpl implements ProgramDateService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProgramDateServiceImpl.class);

    private ProgramDateRepository programDateRepository;
    private ConferenceRepository conferenceRepository;

    @Autowired
    public ProgramDateServiceImpl(ProgramDateRepository programDateRepository,
                                  ConferenceRepository conferenceRepository) {
        this.programDateRepository = programDateRepository;
        this.conferenceRepository = conferenceRepository;
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

    @Transactional
    @Override
    public ProgramDate create(ProgramDateForm form) throws ParseException {
        LOGGER.debug("Create a user with Info: {}", form);

        DateFormat formatter = new SimpleDateFormat(ProgramDate.DAY_FORMAT);
        Date date = formatter.parse(form.getDay());

        Conference conference = conferenceRepository.findOne(form.getConferenceId());

        ProgramDate programDate = new ProgramDate.Builder(
                form.getName(),
                date).conference(conference).build();

        programDateRepository.save(programDate);

        LOGGER.debug("Successfully created");
        return programDate;
    }

    @Transactional
    @Override
    public ProgramDate update(ProgramDateForm form, Long id) throws ProgramDateNotFoundException, ParseException {
        LOGGER.debug("Create a user with Info: {}", form);

        ProgramDate programDate = findById(id);

        DateFormat formatter = new SimpleDateFormat(ProgramDate.DAY_FORMAT);
        Date date = formatter.parse(form.getDay());

        programDate.setName(form.getName()).setDay(date);

        programDateRepository.save(programDate);

        LOGGER.debug("Successfully updated");
        return programDate;
    }
}
