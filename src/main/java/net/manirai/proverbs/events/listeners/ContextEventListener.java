/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.manirai.proverbs.events.listeners;

import java.util.concurrent.TimeUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Component;

/**
 * Context event listener.
 *
 * @author mani
 */
@Component
public class ContextEventListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(ContextEventListener.class);

    @Autowired
    private ThreadPoolTaskScheduler taskScheduler;

    /**
     * On context closed, closes threads and its pool of task scheduler.
     *
     * @param event {@link ContextClosedEvent}
     */
    @EventListener
    public void handleContextClosedEvent(ContextClosedEvent event) {

        LOGGER.debug("Shutting down task scheduler.");
        this.taskScheduler.shutdown();
        try {
            // Wait a while for existing tasks to terminate
            if (!this.taskScheduler.getScheduledThreadPoolExecutor().awaitTermination(30,
                    TimeUnit.SECONDS)) {
                LOGGER.debug("Forcefully shutting down task scheduler.");
                this.taskScheduler.getScheduledThreadPoolExecutor().shutdownNow();
                // Wait a while for tasks to respond to being cancelled
                if (!this.taskScheduler.getScheduledThreadPoolExecutor().awaitTermination(30,
                        TimeUnit.SECONDS)) {
                    LOGGER.error("Failed to shutdown task scheduler.");
                }
            }
        } catch (InterruptedException ex) {
            LOGGER.error(ex.getMessage());

            // (Re-)Cancel if current thread also interrupted
            this.taskScheduler.getScheduledThreadPoolExecutor().shutdownNow();

            // Preserve interrupt status
            Thread.currentThread().interrupt();

        }
    }
}
