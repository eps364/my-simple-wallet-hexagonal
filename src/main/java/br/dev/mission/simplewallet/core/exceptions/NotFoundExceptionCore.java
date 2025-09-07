package br.dev.mission.simplewallet.core.exceptions;

public class NotFoundExceptionCore extends RuntimeException {
    public NotFoundExceptionCore(String message) {
        super(message);
    }
    
    public NotFoundExceptionCore(String message, Throwable cause) {
        super(message, cause);
    }
}