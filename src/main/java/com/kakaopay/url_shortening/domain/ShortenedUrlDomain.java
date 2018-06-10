package com.kakaopay.url_shortening.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "TB_SHORTENING_URL")
@Getter
@Setter
@EqualsAndHashCode
public class ShortenedUrlDomain {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "shortened_url", unique = true, nullable = false)
    private String shortenedUrl;

    @Column(name = "original_url", nullable = false)
    private String originalUrl;

}
