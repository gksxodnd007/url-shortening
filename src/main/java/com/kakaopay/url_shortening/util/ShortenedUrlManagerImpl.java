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

    //TODO 파라미터 이름이 혼동스러움(shortenedUrl인지 originalUrl인지.. 변수명 수정)
    @Override
    public synchronized boolean isExistShortenedUrlKey(String url) {
        final String key = url.substring(0, 2);

        if (shortenedUrlMap.containsKey(key)) {
            loggingShortenedUrlManagerHashMap();
            return shortenedUrlMap.get(key).contains(url);
        }

        List<String> urlKeys = urlRepository.findAllShortenedUrlKeyByPrefix(key);
        if (urlKeys.isEmpty()) {
            return false;
        }
        shortenedUrlMap.put(key, new HashSet<>());
        urlKeys.forEach(urlKey -> {
            logger.info("hashMap Key : {}, value: {}", key, urlKey);
            shortenedUrlMap.get(key).add(urlKey);
        });
        loggingShortenedUrlManagerHashMap();

        return shortenedUrlMap.get(key).contains(url);
    }

    @Override
    public synchronized Optional<ShortenedUrlDomain> addNewShortenedUrlKey(String shortenedUrlKey, String originalUrl) {
        ShortenedUrlDomain domain = new ShortenedUrlDomain();
        domain.setShortenedUrlKey(shortenedUrlKey);
        domain.setOriginalUrl(originalUrl);
        ShortenedUrlDomain result = urlRepository.save(domain);

        final String key = shortenedUrlKey.substring(0, 2);
        logger.info("hashMap Key : {}, value: {}", key, shortenedUrlKey);
        if (shortenedUrlMap.containsKey(key)) {
            shortenedUrlMap.get(key).add(shortenedUrlKey);
        } else {
            shortenedUrlMap.put(key, new HashSet<>());
            shortenedUrlMap.get(key).add(shortenedUrlKey);
        }

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
