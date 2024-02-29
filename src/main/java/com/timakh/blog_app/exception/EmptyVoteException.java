package com.timakh.blog_app.exception;

public class EmptyVoteException extends RuntimeException {
    public EmptyVoteException(String message) {
        super(message);
    }
}
