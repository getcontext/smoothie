package com.cs.smoothie.service;

import com.cs.smoothie.model.Smoothie;

import java.util.List;

public interface SmoothieService {
    /**
     * Retrieves all Smoothies currently existing
     *
     * @return
     */
    List<Smoothie> getAllSmoothies();

    /**
     * @param id The id of the Smoothie
     * @return The Smoothie with the matching id
     */
    Smoothie getById(Long id);

    /**
     * @param id      The id of the Smoothie to be updated
     * @param request The Smoothie object to be updated
     * @return The updated Smoothie
     */
    Smoothie update(Long id, Smoothie request);

    /**
     * @param smoothie The Smoothie object to be created
     * @return The Smoothie object created
     */
    Smoothie create(Smoothie smoothie);

    /**
     * @param id The id of the Smoothie to be deleted
     */
    void delete(Long id);
}
