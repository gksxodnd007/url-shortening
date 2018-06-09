package com.kakaopay.url_shortening.dao;

import com.kakaopay.url_shortening.domain.ShortenedUrlDomain;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UrlRepository extends JpaRepository<ShortenedUrlDomain, Long> {

    Optional<ShortenedUrlDomain> findByShortenedUrl(String url);
    Optional<ShortenedUrlDomain> findByOriginalUrl(String url);

}
