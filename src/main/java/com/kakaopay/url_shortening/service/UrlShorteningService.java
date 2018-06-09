package com.kakaopay.url_shortening.service;

import com.kakaopay.url_shortening.domain.ShortenedUrlDomain;

import java.util.Optional;

public interface UrlShorteningService {

    Optional<ShortenedUrlDomain> getShortenedUrl(String url);
    Optional<ShortenedUrlDomain> getOriginalUrl(String shortenedUrl);

}
