package com.kakaopay.url_shortening.commons.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({ BaseException.class, RuntimeException.class })
    public String handleException(BaseException e) {
        return "error";
    }

}
