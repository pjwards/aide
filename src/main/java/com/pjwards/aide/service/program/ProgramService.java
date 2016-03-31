package com.pjwards.aide.service.program;

import com.pjwards.aide.domain.Program;
import com.pjwards.aide.exception.ProgramNotFoundException;
import com.pjwards.aide.exception.WrongInputDateException;

import java.util.List;

public interface ProgramService {

    /**
     * Returns a list of programs.
     *
     * @return The list of programs
     */
    public List<Program> findAll();

    /**
     * Adds a new program.
     *
     * @param added The information of the added program.
     * @return The added program.
     */
    public Program add(Program added) throws WrongInputDateException;

    /**
     * Finds a program.
     *
     * @param id The id of the wanted program.
     * @return The found program.
     * @throws ProgramNotFoundException if no program is found with the given id.
     */
    public Program findById(Long id) throws ProgramNotFoundException;

    /**
     * Updates the information of program.
     *
     * @param updated The information of the updated program.
     * @return The updated program.
     * @throws ProgramNotFoundException If no program is found with the given id.
     */
    public Program update(Program updated) throws ProgramNotFoundException, WrongInputDateException;

    /**
     * Deletes a program.
     *
     * @param id The id of the deleted program.
     * @return The deleted program.
     * @throws ProgramNotFoundException if no program is found with the given id.
     */
    public Program deleteById(Long id) throws ProgramNotFoundException;
}

