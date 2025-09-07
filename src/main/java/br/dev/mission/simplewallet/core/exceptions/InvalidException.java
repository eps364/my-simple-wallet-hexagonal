package br.dev.mission.simplewallet.core.exceptions;

public class InvalidException extends RuntimeException {
    public InvalidException(String message) {
        super(message);
    }
    
    public InvalidException(String message, Throwable cause) {
        super(message, cause);
    }
}