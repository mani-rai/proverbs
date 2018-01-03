/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.manirai.proverbs.services.impl;

import java.util.Collection;
import java.util.List;
import javax.persistence.EntityNotFoundException;
import net.manirai.proverbs.entities.Proverb;
import net.manirai.proverbs.repositories.ProverbRepository;
import net.manirai.proverbs.services.ProverbService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

/**
 * Default service implementation of {@link ProverbService}.
 *
 * @author mani
 */
@Service
public class ProverbServiceImpl implements ProverbService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProverbServiceImpl.class);

    @Autowired
    private ProverbRepository proverbRepository;

    @Override
    public List<Proverb> addProverbs(Collection<Proverb> proverbs) {

        LOGGER.debug("Adding {} proverbs.", proverbs.size());
        return this.proverbRepository.saveAll(proverbs);
    }

    @Override
    public List<Proverb> getAllProverbs() {

        LOGGER.debug("Fetching all proverbs.");
        return this.proverbRepository.findAll(new Sort(Sort.Direction.ASC, "phrase"));
    }

    @Override
    public Proverb getProverb(Long id) {

        LOGGER.debug("Fetching proverb identified by {}.", id);
        Proverb foundProverb = this.proverbRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException());
        return foundProverb;
    }
}
