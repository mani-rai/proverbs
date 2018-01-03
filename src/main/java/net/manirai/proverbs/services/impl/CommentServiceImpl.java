/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.manirai.proverbs.services.impl;

import java.util.List;
import net.manirai.proverbs.entities.Comment;
import net.manirai.proverbs.repositories.CommentRepository;
import net.manirai.proverbs.services.CommentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Default implementation of {@link CommentService}.
 *
 * @author mani
 */
@Service
public class CommentServiceImpl implements CommentService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CommentServiceImpl.class);

    @Autowired
    private CommentRepository commentRepository;

    @Override
    public Comment addComment(Comment comment) {

        LOGGER.debug("Adding proverb ()." + comment);
        return this.commentRepository.save(comment);
    }

    @Override
    public List<Comment> getAllCommentsOfProverb(Long proverbId) {

        LOGGER.debug("Fetching all comments of proverb {}.", proverbId);
        return this.commentRepository.findByProverbId(proverbId);
    }
}
