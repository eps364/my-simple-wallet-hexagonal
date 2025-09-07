package br.dev.mission.simplewallet.entrypoint.rest.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

    @GetMapping("/{id}")
    public ResponseEntity<AccountResponse> getAccountById(@PathVariable Long id) {
        
        AccountCore accountCore = accountPort.getAccountById(id);
        if (accountCore == null) {
            return ResponseEntity.notFound().build();
        }
        AccountResponse response = new AccountResponse(
            accountCore.getId(),
            accountCore.getDescription(),
            accountCore.getBalance(),
            accountCore.getCredit(),
            accountCore.getDueDate(),
            accountCore.getUserId(),
            ""
        );


        return ResponseEntity.ok(response);
    }
}
