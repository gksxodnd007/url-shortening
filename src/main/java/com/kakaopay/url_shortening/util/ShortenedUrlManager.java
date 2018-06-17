package com.kakaopay.url_shortening.util;

import com.kakaopay.url_shortening.domain.ShortenedUrlDomain;

import java.util.Optional;

public interface ShortenedUrlManager {

    Optional<ShortenedUrlDomain> addNewShortenedUrlKey(String url);
    //로깅용 메서드
    void loggingShortenedUrlManagerHashMap();
    void clearHashMap();

}
