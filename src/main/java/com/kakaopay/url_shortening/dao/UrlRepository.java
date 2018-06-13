package com.kakaopay.url_shortening.dao;

import com.kakaopay.url_shortening.domain.ShortenedUrlDomain;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UrlRepository extends JpaRepository<ShortenedUrlDomain, Long> {

    Optional<ShortenedUrlDomain> findByShortenedUrlKey(String url);
    Optional<ShortenedUrlDomain> findByOriginalUrl(String url);

    @Query(value = "SELECT `shortened_url_key` FROM `TB_SHORTENING_URL` WHERE `shortened_url_key` like :key%", nativeQuery = true)
    List<String> findAllShortenedUrlKeyByPrefix(@Param("key") String key);

}
