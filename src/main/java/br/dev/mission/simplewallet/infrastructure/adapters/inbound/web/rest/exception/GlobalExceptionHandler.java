package br.dev.mission.simplewallet.infrastructure.adapters.inbound.web.rest.exception;

import java.net.URI;
import java.time.Instant;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import br.dev.mission.simplewallet.core.exceptions.InvalidExceptionCore;
import br.dev.mission.simplewallet.core.exceptions.NotFoundExceptionCore;

/**
 * Global exception handler para converter exceções em ProblemDetail (RFC 7807)
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    @Autowired
    private MessageSource messageSource;

    /**
     * Trata exceções de entidade não encontrada
     */
    @ExceptionHandler(NotFoundExceptionCore.class)
    public ResponseEntity<ProblemDetail> handleNotFound(NotFoundExceptionCore ex, WebRequest request) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(
            HttpStatus.NOT_FOUND, 
            ex.getMessage()
        );
        
        problemDetail.setType(URI.create("/problems/not-found"));
        problemDetail.setTitle("Resource Not Found");
        problemDetail.setProperty("timestamp", Instant.now());
        problemDetail.setProperty("path", extractPath(request));
        
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(problemDetail);
    }

    /**
     * Trata exceções de validação/dados inválidos
     */
    @ExceptionHandler(InvalidExceptionCore.class)
    public ResponseEntity<ProblemDetail> handleInvalidData(InvalidExceptionCore ex, WebRequest request) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(
            HttpStatus.BAD_REQUEST, 
            ex.getMessage()
        );
        
        problemDetail.setType(URI.create("/problems/invalid-data"));
        problemDetail.setTitle("Invalid Data");
        problemDetail.setProperty("timestamp", Instant.now());
        problemDetail.setProperty("path", extractPath(request));
        
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(problemDetail);
    }

    /**
     * Trata exceções de validação do Bean Validation (@Valid)
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ProblemDetail> handleValidationErrors(MethodArgumentNotValidException ex, WebRequest request) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(
            HttpStatus.BAD_REQUEST,
            "Validation failed for one or more fields"
        );
        
        problemDetail.setType(URI.create("/problems/validation-error"));
        problemDetail.setTitle("Validation Error");
        problemDetail.setProperty("timestamp", Instant.now());
        problemDetail.setProperty("path", extractPath(request));
        
        // Adiciona detalhes dos campos inválidos
        var fieldErrors = ex.getBindingResult().getFieldErrors().stream()
            .map(error -> new ValidationError(
                error.getField(), 
                error.getDefaultMessage(),
                error.getRejectedValue()
            ))
            .toList();
            
        problemDetail.setProperty("fieldErrors", fieldErrors);
        
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(problemDetail);
    }

    /**
     * Trata exceções de tipo de argumento inválido (ex: string em campo numérico)
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ProblemDetail> handleTypeMismatch(MethodArgumentTypeMismatchException ex, WebRequest request) {
        Class<?> requiredType = ex.getRequiredType();
        String expectedType = requiredType != null ? requiredType.getSimpleName() : "unknown";
        
        String message = String.format(
            "Invalid value '%s' for parameter '%s'. Expected type: %s",
            ex.getValue(),
            ex.getName(),
            expectedType
        );
        
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(
            HttpStatus.BAD_REQUEST,
            message
        );
        
        problemDetail.setType(URI.create("/problems/type-mismatch"));
        problemDetail.setTitle("Type Mismatch");
        problemDetail.setProperty("timestamp", Instant.now());
        problemDetail.setProperty("path", extractPath(request));
        problemDetail.setProperty("parameter", ex.getName());
        problemDetail.setProperty("expectedType", expectedType);
        
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(problemDetail);
    }

    /**
     * Trata exceções genéricas não mapeadas
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ProblemDetail> handleGenericException(Exception ex, WebRequest request) {
        // Log do erro para monitoramento
        System.err.println("Unexpected error: " + ex.getMessage());
        ex.printStackTrace();
        
        String message = getMessage("error.internal", "An unexpected error occurred. Please try again later.");
        
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(
            HttpStatus.INTERNAL_SERVER_ERROR,
            message
        );
        
        problemDetail.setType(URI.create("/problems/internal-error"));
        problemDetail.setTitle("Internal Server Error");
        problemDetail.setProperty("timestamp", Instant.now());
        problemDetail.setProperty("path", extractPath(request));
        
        // Em produção, não exponha detalhes internos
        // Em desenvolvimento, pode incluir mais informações
        if (isDevMode()) {
            problemDetail.setProperty("exceptionType", ex.getClass().getSimpleName());
            problemDetail.setProperty("stackTrace", Arrays.toString(ex.getStackTrace()));
        }
        
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(problemDetail);
    }

    /**
     * Extrai o path da requisição
     */
    private String extractPath(WebRequest request) {
        return request.getDescription(false).replace("uri=", "");
    }

    /**
     * Busca mensagem internacionalizada
     */
    private String getMessage(String key, String defaultMessage) {
        try {
            return messageSource.getMessage(key, null, LocaleContextHolder.getLocale());
        } catch (Exception e) {
            return defaultMessage;
        }
    }

    /**
     * Verifica se está em modo de desenvolvimento
     */
    private boolean isDevMode() {
        String[] activeProfiles = System.getProperty("spring.profiles.active", "").split(",");
        return Arrays.asList(activeProfiles).contains("dev");
    }

    /**
     * Record para representar erros de validação de campo
     */
    public record ValidationError(
        String field,
        String message,
        Object rejectedValue
    ) {}
}
