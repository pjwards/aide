package com.pjwards.aide.service.programdate;

import com.pjwards.aide.domain.ProgramDate;
import com.pjwards.aide.domain.forms.ProgramDateForm;
import com.pjwards.aide.exception.ProgramDateNotFoundException;

import java.text.ParseException;
import java.util.List;

public interface ProgramDateService {

    /**
     * Returns a list of programDates.
     *
     * @return The list of programDates
     */
    public List<ProgramDate> findAll();

    /**
     * Adds a new programDate.
     *
     * @param added The information of the added programDate.
     * @return The added programDate.
     */
    public ProgramDate add(ProgramDate added);

    /**
     * Finds a programDate.
     *
     * @param id The id of the wanted programDate.
     * @return The found programDate.
     * @throws ProgramDateNotFoundException if no programDate is found with the given id.
     */
    public ProgramDate findById(Long id) throws ProgramDateNotFoundException;

    /**
     * Updates the information of programDate.
     *
     * @param updated The information of the updated programDate.
     * @return The updated programDate.
     * @throws ProgramDateNotFoundException If no programDate is found with the given id.
     */
    public ProgramDate update(ProgramDate updated) throws ProgramDateNotFoundException;

    /**
     * Deletes a programDate.
     *
     * @param id The id of the deleted programDate.
     * @return The deleted programDate.
     * @throws ProgramDateNotFoundException if no programDate is found with the given id.
     */
    public ProgramDate deleteById(Long id) throws ProgramDateNotFoundException;

    /**
     * Create programDate by created form
     *
     * @param form programDate created form
     * @return The created programDate.
     */
    public ProgramDate create(ProgramDateForm form) throws ParseException;

    /**
     * Update programDate by updated form
     *
     * @param form programDate updated form
     * @param id programDate id
     * @return The updated programDate.
     */
    public ProgramDate update(ProgramDateForm form, Long id) throws ProgramDateNotFoundException, ParseException;
}
