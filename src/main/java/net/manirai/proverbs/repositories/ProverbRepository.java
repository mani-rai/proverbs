/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.manirai.proverbs.repositories;

import net.manirai.proverbs.entities.Proverb;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository interface of proverb domain.
 *
 * @author mani
 */
public interface ProverbRepository extends JpaRepository<Proverb, Long> {

    /**
     * Find a proverb by {@link Proverb#phrase}.
     *
     * @param phrase {@link Proverb#phrase}
     * @return {@link Proverb}
     */
    Proverb findByPhrase(String phrase);
}
