package com.kakaopay.url_shortening.util;

import com.kakaopay.url_shortening.dao.UrlRepository;
import com.kakaopay.url_shortening.domain.ShortenedUrlDomain;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

//TODO shortenedUrlMap 컨테이너에 데이터들은 무한증가 데이터이다. OutOfMemoryException이 발생할 가능성이 있으므로 주기적으로 데이터를 지워줘야함.
@Component
public class ShortenedUrlManagerImpl implements ShortenedUrlManager {

    private UrlRepository urlRepository;
    private final Map<String, Set<String>> shortenedUrlMap;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public ShortenedUrlManagerImpl(UrlRepository urlRepository) {
        this.urlRepository = urlRepository;
        this.shortenedUrlMap = new HashMap<>();
    }

    //TODO shortenedUrlMap는 여러스레드 사이에서 공유되는 객체 -> 동기화 작업 해야함(StampLock이용 해보자).
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

}
