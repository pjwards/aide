package com.pjwards.aide.service.program;

import com.pjwards.aide.domain.Program;
import com.pjwards.aide.domain.User;
import com.pjwards.aide.domain.forms.ProgramForm;
import com.pjwards.aide.exception.ProgramNotFoundException;
import com.pjwards.aide.exception.WrongInputDateException;
import com.pjwards.aide.repository.ProgramDateRepository;
import com.pjwards.aide.repository.ProgramRepository;
import com.pjwards.aide.repository.RoomRepository;
import com.pjwards.aide.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ProgramServiceImpl implements ProgramService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProgramServiceImpl.class);

    private ProgramRepository programRepository;
    private ProgramDateRepository programDateRepository;
    private RoomRepository roomRepository;
    private UserRepository userRepository;

    @Autowired
    public ProgramServiceImpl(ProgramRepository programRepository,
                              ProgramDateRepository programDateRepository,
                              RoomRepository roomRepository,
                              UserRepository userRepository) {
        this.programRepository = programRepository;
        this.programDateRepository = programDateRepository;
        this.roomRepository = roomRepository;
        this.userRepository = userRepository;
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

        added = programRepository.save(added);
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

    @Transactional
    @Override
    public Program create(ProgramForm form) {
        LOGGER.debug("Create a user with Info: {}", form);

        Program program = new Program.Builder(
                form.getTitle(),
                form.getDescription(),
                form.getBegin(),
                form.getEnd())
                .programType(form.getProgramType())
                .slideUrl(form.getSlideUrl())
                .slideEmbed(form.getSlideEmbed())
                .videoUrl(form.getVideoUrl())
                .videoEmbed(form.getVideoEmbed())
                .build();

        if (form.getProgramDateId() != null) {
            program.setDate(programDateRepository.findOne(form.getProgramDateId()));
        }

        if (form.getRoomId() != null) {
            program.setRoom(roomRepository.findOne(form.getRoomId()));
        }

        if (form.getSpeakers() != null) {
            Set<User> speakers = form.getSpeakers().stream().map(userId -> userRepository.findOne(userId)).collect(Collectors.toSet());
            program.setSpeakerSet(speakers);
        }

        programRepository.save(program);

        LOGGER.debug("Successfully created");
        return program;
    }

    @Transactional
    @Override
    public Program update(ProgramForm form, Long id) throws ProgramNotFoundException {
        LOGGER.debug("Create a user with Info: {}", form);

        Program program = findById(id);

        program
                .setTitle(form.getTitle())
                .setDescription(form.getDescription())
                .setBegin(form.getBegin())
                .setEnd(form.getEnd())
                .setProgramType(form.getProgramType())
                .setSlideUrl(form.getSlideUrl())
                .setSlideEmbed(form.getSlideEmbed())
                .setVideoUrl(form.getVideoUrl())
                .setVideoEmbed(form.getVideoEmbed());

        if (form.getProgramDateId() != null) {
            program.setDate(programDateRepository.findOne(form.getProgramDateId()));
        }

        if (form.getRoomId() != null) {
            program.setRoom(roomRepository.findOne(form.getRoomId()));
        }

        if (form.getSpeakers() != null) {
            Set<User> speakers = form.getSpeakers().stream().map(userId -> userRepository.findOne(userId)).collect(Collectors.toSet());
            program.setSpeakerSet(speakers);
        }

        programRepository.save(program);

        LOGGER.debug("Successfully updated");
        return program;
    }
}
