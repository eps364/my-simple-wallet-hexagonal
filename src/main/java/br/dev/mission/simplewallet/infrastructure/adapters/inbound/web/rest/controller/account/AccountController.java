package br.dev.mission.simplewallet.infrastructure.adapters.inbound.web.rest.controller.account;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.dev.mission.simplewallet.core.ports.inbounce.account.AccountPort;
import br.dev.mission.simplewallet.core.ports.output.dto.account.AccountResponseCore;
import br.dev.mission.simplewallet.infrastructure.adapters.inbound.web.rest.dto.account.AccountRequest;
import br.dev.mission.simplewallet.infrastructure.adapters.inbound.web.rest.dto.account.AccountResponse;
import jakarta.validation.Valid;


@RestController
@RequestMapping("/api/accounts")
public class AccountController {
    private final AccountPort accountPort;

    public AccountController(AccountPort accountPort) {
        this.accountPort = accountPort;
    }

    @PostMapping
    public ResponseEntity<AccountResponse> createAccount(@Valid @RequestBody AccountRequest request) {
        var createdAccount = accountPort.createAccount(request.toCore());
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(AccountResponse.fromAccountResponseCore(createdAccount));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AccountResponse> updateAccount(
            @PathVariable Long id, 
            @Valid @RequestBody AccountRequest request) {
        var updatedAccount = accountPort.updateAccount(id, request.toCore(id));
        return ResponseEntity.ok(AccountResponse.fromAccountResponseCore(updatedAccount));
    }

    @GetMapping
    public ResponseEntity<List<AccountResponse>> getAllAccounts() {
        List<AccountResponseCore> accounts = accountPort.getAllAccounts();
        List<AccountResponse> responses = accounts.stream().map(AccountResponse::fromAccountResponseCore).toList();
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AccountResponse> getAccountById(@PathVariable Long id) {
        AccountResponseCore accountResponseCore = accountPort.getAccountById(id);
        return ResponseEntity.ok(AccountResponse.fromAccountResponseCore(accountResponseCore));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAccount(@PathVariable Long id) {
        accountPort.deleteAccount(id);
        return ResponseEntity.noContent().build();
    }
}