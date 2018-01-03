/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.manirai.proverbs.events.listeners;

import java.util.ArrayList;
import java.util.List;
import net.manirai.proverbs.entities.Proverb;
import net.manirai.proverbs.entities.Tag;
import net.manirai.proverbs.events.ProverbsAddEvent;
import net.manirai.proverbs.repositories.ProverbRepository;
import net.manirai.proverbs.repositories.TagRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * A listener to add proverbs when crawlers throws {@link ProverbsAddEvent} event.
 *
 * @author mani
 */
@Component
public class CrawlersEventListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(CrawlersEventListener.class);

    @Autowired
    private ProverbRepository proverbRepository;

    @Autowired
    private TagRepository tagRepository;

    /**
     * Adds proverbs when crawlers throws {@link ProverbsAddEvent} event.
     *
     * @param event {@link ProverbsAddEvent}
     */
    @EventListener
    public void handleProverbsAddEvent(ProverbsAddEvent event) {

        LOGGER.debug("Received an event to add {} proverbs.", event.getProverbs().size());
        List<Proverb> proverbs = new ArrayList<>();
        for (Proverb proverb : event.getProverbs()) {
            LOGGER.trace("Finding proverb of phrase \"{}\"." + proverb.getPhrase());
            Proverb foundProverb = this.proverbRepository.findByPhrase(proverb.getPhrase());
            if (foundProverb != null) {
                continue;
            }
            LOGGER.trace("Adding primary key on tags to merge.");
            for (Tag tag : proverb.getTags()) {
                Tag foundTag = this.tagRepository.findByName(tag.getName());
                if (foundTag != null) {
                    tag.setId(foundTag.getId());
                    continue;
                }
                Tag savedTag = this.tagRepository.save(tag);
                tag.setId(savedTag.getId());
            }
            proverbs.add(proverb);
        }
        if (!proverbs.isEmpty()) {
            this.proverbRepository.saveAll(event.getProverbs());
        }
    }
}
