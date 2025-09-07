package br.dev.mission.simplewallet.entrypoint.rest.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.dev.mission.simplewallet.core.model.account.AccountCore;
import br.dev.mission.simplewallet.core.ports.inbounce.account.AccountPort;
import br.dev.mission.simplewallet.entrypoint.rest.dto.account.AccountResponse;

@RestController
@RequestMapping("/accounts")
public class AccountController {
    private final AccountPort accountPort;

    public AccountController(AccountPort accountPort) {
        this.accountPort = accountPort;
    }

    @PostMapping
    public ResponseEntity<AccountResponse> createAccount(@RequestBody AccountCore accountCore) {
        AccountCore createdAccount = accountPort.createAccount(accountCore);
        AccountResponse response = toAccountResponse(createdAccount);
        return ResponseEntity.ok(response);
    }

    // updateAccount
    @PutMapping("/{id}")
    public ResponseEntity<AccountResponse> updateAccount(@PathVariable Long id, @RequestBody AccountCore accountCore) {
        AccountCore updatedAccount = accountPort.updateAccount(id, accountCore);
        if (updatedAccount == null) {
            return ResponseEntity.notFound().build();
        }
        AccountResponse response = toAccountResponse(updatedAccount);
        return ResponseEntity.ok(response);
    }

    // getAccounts
    @GetMapping
    public ResponseEntity<List<AccountResponse>> getAllAccounts() {
        List<AccountCore> accounts = accountPort.getAllAccounts();
        List<AccountResponse> responses = accounts
                .stream().map(this::toAccountResponse)
                .toList();
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AccountResponse> getAccountById(@PathVariable Long id) {

        AccountCore accountCore = accountPort.getAccountById(id);
        if (accountCore == null) {
            return ResponseEntity.notFound().build();
        }
        AccountResponse response = toAccountResponse(accountCore);
        return ResponseEntity.ok(response);
    }

    private AccountResponse toAccountResponse(AccountCore accountCore) {
        return new AccountResponse(
            accountCore.getId(),
            accountCore.getDescription(),
            accountCore.getBalance(),
            accountCore.getCredit(),
            accountCore.getDueDate(),
            accountCore.getUserId(),
            ""
        );
    }
}
