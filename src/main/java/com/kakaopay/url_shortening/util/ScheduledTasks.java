package com.kakaopay.url_shortening.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class ScheduledTasks {

    private static final Logger logger = LoggerFactory.getLogger(ScheduledTasks.class);
    private ShortenedUrlManager shortenedUrlManager;

    @Autowired
    public ScheduledTasks(@Qualifier("shortenedUrlManagerImpl") ShortenedUrlManager shortenedUrlManager) {
        this.shortenedUrlManager = shortenedUrlManager;
    }

    //한 시간마다 HashMap 컨테이너를 비움
    @Scheduled(cron = "0 0 * * * *", zone = "GMT+9:00")
    public void clearShortenedUrlKeyHashMap() {
        logger.info("execute time : {}", LocalDateTime.now().toString());
        logger.info("before clear");
        shortenedUrlManager.loggingShortenedUrlManagerHashMap();

        shortenedUrlManager.clearHashMap();

        logger.info("after clear");
        shortenedUrlManager.loggingShortenedUrlManagerHashMap();
    }
}
