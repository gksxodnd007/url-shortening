package com.kakaopay.url_shortening.service;

import com.kakaopay.url_shortening.dao.UrlRepository;
import com.kakaopay.url_shortening.domain.ShortenedUrlDomain;
import com.kakaopay.url_shortening.util.ShorteningKeyHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UrlShorteningServiceImpl implements UrlShorteningService {

    private final UrlRepository urlRepository;

    @Autowired
    public UrlShorteningServiceImpl(UrlRepository urlRepository) {
        this.urlRepository = urlRepository;
    }

    @Override
    public Optional<ShortenedUrlDomain> getShortenedUrl(String url) {
        Optional<ShortenedUrlDomain> existedUrlDomain = urlRepository.findByOriginalUrl(url);

        if (existedUrlDomain.isPresent()) {
            return existedUrlDomain;
        }

        ShortenedUrlDomain newUrlDomain = new ShortenedUrlDomain();
        String shortenedUrl = ShorteningKeyHelper.generateShortenedUrl(url);

        while (urlRepository.findByShortenedUrl(shortenedUrl).isPresent()) {
            shortenedUrl = ShorteningKeyHelper.generateShortenedUrl(url);
        }

        newUrlDomain.setShortenedUrl(shortenedUrl);
        newUrlDomain.setOriginalUrl(url);

        urlRepository.save(newUrlDomain);

        return Optional.of(newUrlDomain);
    }

    @Override
    public Optional<ShortenedUrlDomain> getOriginalUrl(String shortenedUrl) {
        return urlRepository.findByShortenedUrl(shortenedUrl);
    }
}
