package com.kakaopay.url_shortening.util;

import com.kakaopay.url_shortening.dao.UrlRepository;
import com.kakaopay.url_shortening.domain.ShortenedUrlDomain;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class ShortenedUrlManagerImpl implements ShortenedUrlManager {

    private UrlRepository urlRepository;
    private final Map<String, Set<String>> shortenedUrlMap;
    private static final Logger logger = LoggerFactory.getLogger(ShortenedUrlManagerImpl.class);

    @Autowired
    public ShortenedUrlManagerImpl(UrlRepository urlRepository) {
        this.urlRepository = urlRepository;
        this.shortenedUrlMap = new HashMap<>();
    }

    private boolean isExistShortenedUrlKey(String shortenedUrlKey, String hashKey) {
        if (shortenedUrlMap.containsKey(hashKey)) {
            loggingShortenedUrlManagerHashMap();
            return shortenedUrlMap.get(hashKey).contains(shortenedUrlKey);
        }

        List<String> urlKeys = urlRepository.findAllShortenedUrlKeyByPrefix(hashKey);
        if (urlKeys.isEmpty()) {
            return false;
        }

        shortenedUrlMap.put(hashKey, new HashSet<>());
        urlKeys.forEach(urlKey -> {
            logger.info("hashMap Key : {}, value: {}", hashKey, urlKey);
            shortenedUrlMap.get(hashKey).add(urlKey);
        });
        loggingShortenedUrlManagerHashMap();

        return shortenedUrlMap.get(hashKey).contains(shortenedUrlKey);
    }

    @Override
    public synchronized Optional<ShortenedUrlDomain> addNewShortenedUrlKey(String url) {
        String shortenedUrlKey = ShorteningKeyHelper.generateShortenedUrlKey(url);
        final String hashKey = shortenedUrlKey.substring(0, 2);

        while (isExistShortenedUrlKey(shortenedUrlKey, hashKey)) {
            shortenedUrlKey = ShorteningKeyHelper.generateShortenedUrlKey(url);
        }

        ShortenedUrlDomain domain = new ShortenedUrlDomain();
        domain.setShortenedUrlKey(shortenedUrlKey);
        domain.setOriginalUrl(url);
        ShortenedUrlDomain result = urlRepository.save(domain);

        shortenedUrlMap.put(hashKey, new HashSet<>());
        shortenedUrlMap.get(hashKey).add(shortenedUrlKey);

        logger.info("hashMap Key : {}, value: {}", hashKey, shortenedUrlKey);

        return Optional.of(result);
    }

    @Override
    public void loggingShortenedUrlManagerHashMap() {
        if (shortenedUrlMap.isEmpty()) {
            logger.info("HashMap is empty");
            return;
        }
        Set<String> hashKeys = shortenedUrlMap.keySet();
        logger.info("------------ HashMap data ---------------");
        for(String hashKey : hashKeys) {
            logger.info("-----------------------------------------");
            for (String value: shortenedUrlMap.get(hashKey)) {
                logger.info("hashMap Key : {}, value: {}", hashKey, value);
            }
            logger.info("-----------------------------------------");
        }
    }

    @Override
    public synchronized void clearHashMap() {
        if (shortenedUrlMap.isEmpty()) {
            return;
        }
        shortenedUrlMap.clear();
    }
}
