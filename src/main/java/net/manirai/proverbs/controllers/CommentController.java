/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.manirai.proverbs.controllers;

import java.util.ArrayList;
import java.util.List;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import net.manirai.proverbs.entities.Comment;
import net.manirai.proverbs.entities.Proverb;
import net.manirai.proverbs.services.CommentService;
import net.manirai.proverbs.services.ProverbService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Comment resource controller.
 *
 * @author mani
 */
@Controller
@RequestMapping("/{proverbId}/comments")
public class CommentController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CommentController.class);

    @Autowired
    private CommentService commentService;

    @Autowired
    private ProverbService proverbService;

    /**
     * Adds a comment.
     *
     * @param proverbId {@link Proverb#id}
     * @param comment {@link Comment} to persist
     * @return Persisted {@link Comment}
     */
    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public Comment addComment(@PathVariable("proverbId") Long proverbId,
            @RequestBody @Valid Comment comment) {

        // Finds the proverb first. If not found throw EntityNotFoundException resulting in 500 http status.
        Proverb foundProverb = this.proverbService.getProverb(proverbId);

        // Setting proverb incomment for merge.
        comment.setProverb(foundProverb);

        // Adding comment.
        return this.commentService.addComment(comment);
    }

    /**
     * Handles {@link ConstraintViolationException} for this controller.
     *
     * @param ex {@link  ConstraintViolationException}
     * @return {@link List} of errors.
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    public List<String> handleConstraintViolationException(ConstraintViolationException ex) {

        LOGGER.debug("Caught constraints violation exception.");
        List<String> errors = new ArrayList<>();
        for (ConstraintViolation<?> violation : ex.getConstraintViolations()) {
            errors.add(violation.getRootBeanClass().getName() + " "
                    + violation.getPropertyPath() + ": " + violation.getMessage());
        }
        return errors;
    }
}
