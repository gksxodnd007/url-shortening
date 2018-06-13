package com.kakaopay.url_shortening.commons.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @ExceptionHandler(RuntimeException.class)
    public String handleException(RuntimeException e) {
        logger.debug("msg : {}, cause : {}", e.getMessage(), e.getCause());
        return "error";
    }

}
