/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.manirai.proverbs.events;

import java.util.Collection;
import net.manirai.proverbs.entities.Proverb;

/**
 * An event to add proverbs.
 *
 * @author mani
 */
public class ProverbsAddEvent {

    private Collection<Proverb> proverbs;

    public ProverbsAddEvent(Collection<Proverb> proverbs) {
        this.proverbs = proverbs;
    }

    public Collection<Proverb> getProverbs() {
        return proverbs;
    }

    public void setProverbs(Collection<Proverb> proverbs) {
        this.proverbs = proverbs;
    }
}
