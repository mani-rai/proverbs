/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.manirai.proverbs.crawlers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import net.manirai.proverbs.entities.Proverb;
import net.manirai.proverbs.entities.Tag;
import net.manirai.proverbs.events.ProverbsAddEvent;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

/**
 * Crawler of http://www.atasozleri.gen.tr/.
 *
 * @author mani
 */
@Component
public class AtasozleriDotGenDotTrCrawler implements ApplicationEventPublisherAware {

    private static final Logger LOGGER = LoggerFactory.getLogger(AtasozleriDotGenDotTrCrawler.class);

    @Value("${atasozleri.gen.tr.website.url}")
    private String websiteUrl;
    @Value("${atasozleri.gen.tr.thread.core.pool.size}")
    private int corePoolSize;
    @Value("${atasozleri.gen.tr.thread.max.pool.size}")
    private int maxPoolSize;
    @Value("${atasozleri.gen.tr.thread.queue.capacity}")
    private int queueCapacity;

    // Crawler task executor with own thread pool.
    @Autowired
    @Qualifier(value = "crawlerTaskExecutor")
    private ThreadPoolTaskExecutor taskExecutor;
    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {

        this.applicationEventPublisher = applicationEventPublisher;
    }

    @PostConstruct
    public void init() {

        this.taskExecutor.setCorePoolSize(this.corePoolSize);
        this.taskExecutor.setMaxPoolSize(this.maxPoolSize);
        this.taskExecutor.setQueueCapacity(this.queueCapacity);
    }

    /**
     * Scheduled task to crawl http://www.atasozleri.gen.tr/ for proverbs every 2 days.
     */
    @Scheduled(initialDelay = 10000, fixedDelay = 172800000)
    @Async(value = "taskScheduler")
    public void crawl() {

        try {
            LOGGER.info("Initiating crawling " + this.websiteUrl);
            Future<Set<String>> categoriesCrawlerFuture = this.taskExecutor.submit(
                    new CategoriesCrawler(this.websiteUrl));
            Set<String> categoriesLinks = categoriesCrawlerFuture.get();

            if (categoriesCrawlerFuture.isDone()) {

                LOGGER.debug("Found {} categories links.", categoriesLinks);
                LOGGER.trace("Crawler through {} categories for proverbs.", categoriesLinks.size());
                for (String categoryLink : categoriesLinks) {

                    Future<Set<String>> proverbsCrawlerFuture = this.taskExecutor.submit(
                            new ProverbsCrawler(categoryLink));
                    Set<String> proverbsLinks = proverbsCrawlerFuture.get();

                    if (proverbsCrawlerFuture.isDone()) {

                        // Declaring and if there are links intitializing a proverbs holder.
                        LOGGER.debug("Found {} proverb links.", proverbsLinks.size());
                        List<Proverb> proverbs = new ArrayList<>();
                        LOGGER.trace("Crawler through {} proverb details.", proverbsCrawlerFuture
                                .get()
                                .size());
                        for (String proverbDetailLink : proverbsLinks) {

                            Future<Proverb> proverbDetailCrawlerFuture = this.taskExecutor.submit(
                                    new ProverbDetailCrawler(proverbDetailLink));
                            Proverb proverb = proverbDetailCrawlerFuture.get();
                            if (proverbDetailCrawlerFuture.isDone()) {
                                proverbs.add(proverb);
                            }
                        }

                        this.applicationEventPublisher.publishEvent(new ProverbsAddEvent(proverbs));
                    }
                }
            }
        } catch (InterruptedException | ExecutionException e) {
            LOGGER.warn(e.getMessage());
        }
    }

    public class CategoriesCrawler implements Callable<Set<String>> {

        private String webpageUrl;

        public CategoriesCrawler(String webpageUrl) {
            this.webpageUrl = webpageUrl;
        }

        @Override
        public Set<String> call() {

            Document document;
            try {
                document = Jsoup.connect(this.webpageUrl).get();
            } catch (IOException ex) {
                LOGGER.error(ex.getMessage());
                return new HashSet<>();
            }

            LOGGER.trace("Retreiving categories links.");
            return document.child(0).child(1).child(1).child(0)
                    .child(0).child(0).select(
                    "a").stream().map((element) -> element.absUrl("href")).collect(Collectors
                    .toSet());
        }
    }

    public class ProverbsCrawler implements Callable<Set<String>> {

        private String webpageUrl;

        public ProverbsCrawler(String webpageUrl) {
            this.webpageUrl = webpageUrl;
        }

        @Override
        public Set<String> call() throws Exception {

            Document document;
            try {
                document = Jsoup.connect(this.webpageUrl).get();
            } catch (IOException ex) {
                LOGGER.error(ex.getMessage());
                return new HashSet<>();
            }

            LOGGER.trace("Retreiving proverbs links.");
            return document.select(".okul-baslik").stream().map((element) -> element.absUrl("href"))
                    .collect(Collectors.toSet());
        }
    }

    public class ProverbDetailCrawler implements Callable<Proverb> {

        private String webpageUrl;

        public ProverbDetailCrawler(String webpageUrl) {
            this.webpageUrl = webpageUrl;
        }

        @Override
        public Proverb call() throws Exception {

            Document document;
            try {
                document = Jsoup.connect(this.webpageUrl).get();
            } catch (IOException ex) {
                LOGGER.error(ex.getMessage());
                return null;
            }

            LOGGER.trace("Creating proverb.");
            String title = document.child(0).child(1).child(1).child(5).child(1).child(0).child(0)
                    .text();
            String meaning = document.child(0).child(1).child(1).child(5).child(1).child(0).child(1)
                    .child(3).text();

            Proverb proverb = new Proverb();
            proverb.setLanguage("tr");
            proverb.setMeaning(meaning);
            proverb.setPhrase(title);

            List<Tag> tags = new ArrayList<>();
            Elements tagElements = document.child(0).child(1).child(1).child(5).child(1).child(0)
                    .child(1).select(".glyphicon-tags a");
            for (Element element : tagElements) {
                Tag tag = new Tag();
                tag.setName(element.text());
                tags.add(tag);
            }
            proverb.setTags(tags);
            return proverb;
        }
    }

    /**
     * Handler method on {@link ContextClosedEvent} to shutdown crawlers own threads and its pool
     * gracefully.
     *
     * @param event {@link ContextClosedEvent}
     */
    @EventListener
    public void handleContextClosedEvent(ContextClosedEvent event) {

        LOGGER.debug("Shutting down crawler task executor.");
        this.taskExecutor.shutdown();
        try {
            // Wait a while for existing tasks to terminate
            if (!this.taskExecutor.getThreadPoolExecutor().awaitTermination(30,
                    TimeUnit.SECONDS)) {
                LOGGER.debug("Forcefully shutting down task executor.");
                this.taskExecutor.getThreadPoolExecutor().shutdownNow();
                // Wait a while for tasks to respond to being cancelled
                if (!this.taskExecutor.getThreadPoolExecutor().awaitTermination(30,
                        TimeUnit.SECONDS)) {
                    LOGGER.error("Failed to shutdown task executor.");
                }
            }
        } catch (InterruptedException ex) {
            LOGGER.error(ex.getMessage());

            // (Re-)Cancel if current thread also interrupted
            this.taskExecutor.getThreadPoolExecutor().shutdownNow();

            // Preserve interrupt status
            Thread.currentThread().interrupt();

        }
    }
}
