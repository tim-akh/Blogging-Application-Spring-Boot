package com.timakh.blog_app.exception;

public class EmptyAddressException extends RuntimeException {
    public EmptyAddressException(String message) {
        super(message);
    }
}
