package com.kakaopay.url_shortening.service;

import com.kakaopay.url_shortening.domain.ShortenedUrlDomain;

import java.util.Optional;

public interface UrlShorteningService {

    @Deprecated
    Optional<ShortenedUrlDomain> getShortenedUrl(String url);
    Optional<ShortenedUrlDomain> getShortenedUrlKey(String url);
    Optional<ShortenedUrlDomain> getOriginalUrl(String shortenedUrl);

}
