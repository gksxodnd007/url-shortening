package com.kakaopay.url_shortening.commons.exception;

public class BaseException extends RuntimeException {

    private int statusCode;

    public BaseException() {
        super();
    }

    public BaseException(int statusCode) {
        this(statusCode, null);
    }

    protected BaseException(int statusCode, String message) {
        this(statusCode, message, null);
    }

    private BaseException(int statusCode, String message, Throwable cause) {
        super(message, cause);
        this.statusCode = statusCode;
    }

    public int getStatusCode() {
        return this.statusCode;
    }

}
