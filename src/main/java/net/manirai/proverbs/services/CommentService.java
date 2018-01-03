/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.manirai.proverbs.services;

import java.util.List;
import net.manirai.proverbs.entities.Comment;
import net.manirai.proverbs.entities.Proverb;

/**
 * Service interface of comment domain.
 *
 * @author mani
 */
public interface CommentService {

    /**
     * Adds a {@link Comment}.
     *
     * @param comment {@link Comment}
     * @return {@link Comment}
     */
    Comment addComment(Comment comment);

    /**
     * Gets all {@link Comment) of {@link Proverb}.
     *
     * @param proverbId {@link Proverb#id}
     * @return {@link List} of {@link Comment}
     */
    List<Comment> getAllCommentsOfProverb(Long proverbId);
}
