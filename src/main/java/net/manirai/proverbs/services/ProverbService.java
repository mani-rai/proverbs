/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.manirai.proverbs.services;

import java.util.Collection;
import java.util.List;
import javax.persistence.EntityNotFoundException;
import net.manirai.proverbs.entities.Proverb;

/**
 * Service interface of proverb domain.
 *
 * @author mani
 */
public interface ProverbService {

    /**
     * Adds collection of proverbs.
     *
     * @param proverbs {@link Collection} of {@link Proverb}
     * @return {@link List} of {@link Proverb}
     */
    List<Proverb> addProverbs(Collection<Proverb> proverbs);

    /**
     * Gets all proverbs sorted in alphabetical case sensitive order.
     *
     * @return {@link List} of {@link Proverb}
     */
    List<Proverb> getAllProverbs();

    /**
     * Gets proverb identified by id. If not found throws {@link EntityNotFoundException}.
     *
     * @param id {@link Proverb#id}
     * @return {@link Proverb}
     */
    Proverb getProverb(Long id);
}
