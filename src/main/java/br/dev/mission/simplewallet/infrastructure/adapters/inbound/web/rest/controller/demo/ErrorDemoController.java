package br.dev.mission.simplewallet.infrastructure.adapters.inbound.web.rest.controller.demo;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.dev.mission.simplewallet.core.exceptions.InvalidExceptionCore;
import br.dev.mission.simplewallet.core.exceptions.NotFoundExceptionCore;

/**
 * Controller de demonstração para testar o ProblemDetail (RFC 7807)
 * Este controller pode ser removido em produção
 */
@RestController
@RequestMapping("/api/demo/errors")
public class ErrorDemoController {

    /**
     * Endpoint para testar erro 404 - Not Found
     * GET /api/demo/errors/not-found
     */
    @GetMapping("/not-found")
    public ResponseEntity<String> triggerNotFound() {
        throw new NotFoundExceptionCore("This is a demo of NotFound exception using ProblemDetail");
    }

    /**
     * Endpoint para testar erro 400 - Bad Request
     * GET /api/demo/errors/invalid-data
     */
    @GetMapping("/invalid-data")
    public ResponseEntity<String> triggerInvalidData() {
        throw new InvalidExceptionCore("This is a demo of Invalid data exception using ProblemDetail");
    }

    /**
     * Endpoint para testar erro 500 - Internal Server Error
     * GET /api/demo/errors/server-error
     */
    @GetMapping("/server-error")
    public ResponseEntity<String> triggerServerError() {
        throw new RuntimeException("This is a demo of unexpected exception using ProblemDetail");
    }

    /**
     * Endpoint para testar erro 400 - Type Mismatch
     * GET /api/demo/errors/type-mismatch/abc
     * (onde 'abc' deveria ser um número)
     */
    @GetMapping("/type-mismatch/{id}")
    public ResponseEntity<String> triggerTypeMismatch(@PathVariable Long id) {
        return ResponseEntity.ok("ID received: " + id);
    }
}
