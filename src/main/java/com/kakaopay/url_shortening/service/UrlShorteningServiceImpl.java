package com.kakaopay.url_shortening.service;

import com.kakaopay.url_shortening.dao.UrlRepository;
import com.kakaopay.url_shortening.domain.ShortenedUrlDomain;
import com.kakaopay.url_shortening.util.ShortenedUrlManager;
import com.kakaopay.url_shortening.util.ShorteningKeyHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UrlShorteningServiceImpl implements UrlShorteningService {

    private final UrlRepository urlRepository;
    private final ShortenedUrlManager shortenedUrlManager;

    @Autowired
    public UrlShorteningServiceImpl(UrlRepository urlRepository,
                                    @Qualifier("shortenedUrlManagerImpl") ShortenedUrlManager shortenedUrlManager) {
        this.urlRepository = urlRepository;
        this.shortenedUrlManager = shortenedUrlManager;
    }

    @Deprecated
    @Override
    public Optional<ShortenedUrlDomain> getShortenedUrl(String url) {
        Optional<ShortenedUrlDomain> existedUrlDomain = urlRepository.findByOriginalUrl(url);

        if (existedUrlDomain.isPresent()) {
            return existedUrlDomain;
        }

        ShortenedUrlDomain newUrlDomain = new ShortenedUrlDomain();
        String shortenedUrlKey = ShorteningKeyHelper.generateShortenedUrlKey(url);

        while (urlRepository.findByShortenedUrlKey(shortenedUrlKey).isPresent()) {
            shortenedUrlKey = ShorteningKeyHelper.generateShortenedUrlKey(url);
        }

        newUrlDomain.setShortenedUrlKey(shortenedUrlKey);
        newUrlDomain.setOriginalUrl(url);

        urlRepository.save(newUrlDomain);

        return Optional.of(newUrlDomain);
    }

    @Override
    public Optional<ShortenedUrlDomain> getShortenedUrlKey(String url) {
        Optional<ShortenedUrlDomain> existedUrlDomain = urlRepository.findByOriginalUrl(url);
        shortenedUrlManager.loggingShortenedUrlManagerHashMap();

        if (existedUrlDomain.isPresent()) {
            return existedUrlDomain;
        }

        return shortenedUrlManager.addNewShortenedUrlKey(url);
    }

    @Override
    public Optional<ShortenedUrlDomain> getOriginalUrl(String shortenedUrl) {
        return urlRepository.findByShortenedUrlKey(shortenedUrl);
    }

}
