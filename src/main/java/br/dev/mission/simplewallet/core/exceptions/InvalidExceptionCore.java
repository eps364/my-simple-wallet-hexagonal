package br.dev.mission.simplewallet.core.exceptions;

public class InvalidExceptionCore extends RuntimeException {
    public InvalidExceptionCore(String message) {
        super(message);
    }
    
    public InvalidExceptionCore(String message, Throwable cause) {
        super(message, cause);
    }
}