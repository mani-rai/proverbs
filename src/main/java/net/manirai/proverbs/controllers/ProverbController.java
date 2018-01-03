/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.manirai.proverbs.controllers;

import java.util.List;
import net.manirai.proverbs.entities.Comment;
import net.manirai.proverbs.entities.Proverb;
import net.manirai.proverbs.services.CommentService;
import net.manirai.proverbs.services.ProverbService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Proverb resource controller.
 *
 * @author mani
 */
@Controller
@RequestMapping("/")
public class ProverbController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProverbController.class);

    @Autowired
    private ProverbService proverbService;

    @Autowired
    private CommentService commentService;

    /**
     * Controller for request to get all proverbs.
     *
     * @param model {@link Model}
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    public String getProverbs(Model model) {

        List<Proverb> foundProverbs = this.proverbService.getAllProverbs();

        model.addAttribute("proverbs", foundProverbs);

        return "home";
    }

    /**
     * Controller for request of getting a proverb,
     *
     * @param id {@link Proverb#id}
     * @param model {@link Model}
     * @return
     */
    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public String getProverb(@PathVariable("id") Long id, Model model) {

        Proverb foundProverb = this.proverbService.getProverb(id);
        List<Comment> foundComments = this.commentService.getAllCommentsOfProverb(id);
        foundProverb.setComments(foundComments);

        model.addAttribute("proverb", foundProverb);
        model.addAttribute("newComment", new Comment());
        return "proverb-detail";
    }
}
