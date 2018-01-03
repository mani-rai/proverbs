/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.manirai.proverbs.repositories;

import java.util.List;
import net.manirai.proverbs.entities.Comment;
import net.manirai.proverbs.entities.Proverb;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository interface of comment domain.
 *
 * @author mani
 */
public interface CommentRepository extends JpaRepository<Comment, Long> {

    /**
     * Finds {@link List} of {@link Comment} by {@link Proverb#id}.
     *
     * @param proverbId {@link Proverb#id}
     * @return {@link List} of {@link Comment}
     */
    List<Comment> findByProverbId(Long proverbId);
}
