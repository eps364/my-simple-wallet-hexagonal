package br.dev.mission.simplewallet.core.service.account;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import br.dev.mission.simplewallet.core.model.account.AccountCore;
import br.dev.mission.simplewallet.core.ports.inbounce.account.AccountPort;

@Service
public class AccountService implements AccountPort {

    @Override
    public AccountCore createAccount(AccountCore account) {
        return newAccountCore();
    }

    @Override
    public void deleteAccount(Long id) {

    }

    @Override
    public AccountCore getAccountById(Long id) {
        return newAccountCore();
    }

    @Override
    public List<AccountCore> getAllAccounts() {
        return List.of(newAccountCore());
    }

    @Override
    public AccountCore updateAccount(AccountCore account) {
        return newAccountCore();
    }

    private AccountCore newAccountCore() {
        // TODO: Metodo temporario

        AccountCore account = new AccountCore();
        account.setId(50L);
        account.setDescription("Teste Account");
        account.setBalance(BigDecimal.ZERO);
        account.setCredit(BigDecimal.ZERO);
        account.setUserId(UUID.randomUUID());
        return account;
    }

}