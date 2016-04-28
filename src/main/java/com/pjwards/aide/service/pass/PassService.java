package com.pjwards.aide.service.pass;

import com.pjwards.aide.domain.Pass;
import com.pjwards.aide.exception.PassNotFoundException;

import java.util.List;

public interface PassService {

    /**
     * Returns a list of passs.
     *
     * @return The list of passs
     */
    public List<Pass> findAll();

    /**
     * Adds a new pass.
     *
     * @param added The information of the added pass.
     * @return The added pass.
     */
    public Pass add(Pass added);

    /**
     * Finds a pass.
     *
     * @param id The id of the wanted pass.
     * @return The found pass.
     * @throws PassNotFoundException if no pass is found with the given id.
     */
    public Pass findById(Long id) throws PassNotFoundException;

    /**
     * Updates the information of pass.
     *
     * @param updated The information of the updated pass.
     * @return The updated pass.
     * @throws PassNotFoundException If no pass is found with the given id.
     */
    public Pass update(Pass updated) throws PassNotFoundException;

    /**
     * Deletes a pass.
     *
     * @param id The id of the deleted pass.
     * @return The deleted pass.
     * @throws PassNotFoundException if no pass is found with the given id.
     */
    public Pass deleteById(Long id) throws PassNotFoundException;
}
