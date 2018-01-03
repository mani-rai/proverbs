/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.manirai.proverbs.repositories;

import net.manirai.proverbs.entities.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository interface of Tag domain.
 *
 * @author mani
 */
public interface TagRepository extends JpaRepository<Tag, Long> {

    /**
     * Find a {@link Tag} by {@link Tag#name}.
     *
     * @param name {@link Tag#name}
     * @return {@link Tag}
     */
    Tag findByName(String name);
}
