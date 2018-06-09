package com.kakaopay.url_shortening.controller;

import com.kakaopay.url_shortening.domain.ShortenedUrlDomain;
import com.kakaopay.url_shortening.service.UrlShorteningService;
import org.apache.commons.validator.routines.UrlValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.Optional;

@Controller
public class UrlShorteningController {

    private final UrlShorteningService urlShorteningService;
    private final UrlValidator urlValidator;

    @Autowired
    public UrlShorteningController(@Qualifier("urlShorteningServiceImpl") UrlShorteningService urlShorteningService) {
        this.urlShorteningService = urlShorteningService;
        this.urlValidator = new UrlValidator();
    }

    @PostMapping(value = "/generate/shortening/url")
    public String shorteningUrl(@RequestParam("targetUrl") String url,
                                Map<String, String> model) {
        if (!urlValidator.isValid(url)) {
            model.put("isValidUrl", "잘못된 URL입니다. 다시 입력해주세요");
            return "index";
        }

        Optional<ShortenedUrlDomain> domain = urlShorteningService.getShortenedUrl(url);
        domain.ifPresent(elem -> {
            model.put("shortenedUrl", elem.getShortenedUrl());
            model.put("originalUrl", elem.getOriginalUrl());
        });
        return "index";
    }

    @GetMapping(value = "/**")
    public String redirectServcie(HttpServletRequest request) {
        System.out.println(request.getRequestURI());
        final Optional<ShortenedUrlDomain> url = urlShorteningService.getOriginalUrl("http://localhost:8080" + request.getRequestURI());

        //TODO error page로 리다이렉트 안되는 버그 해결하기
        //오류 메시지 : org.springframework.expression.spel.SpelEvaluationException: EL1008E: Property or field 'timestamp' cannot be found on object of type 'java.util.HashMap' - maybe not public?
        return url.map(elem -> "redirect:" + elem.getOriginalUrl()).orElse("error");
    }
}
