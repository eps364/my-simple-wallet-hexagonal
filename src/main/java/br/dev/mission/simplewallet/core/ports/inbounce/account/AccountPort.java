package br.dev.mission.simplewallet.core.ports.inbounce.account;

import java.util.List;

import br.dev.mission.simplewallet.core.model.account.AccountCore;

public interface AccountPort {
    AccountCore createAccount(AccountCore account);

    AccountCore getAccountById(Long id);

    AccountCore updateAccount(Long id, AccountCore account);

    void deleteAccount(Long id);

    List<AccountCore> getAllAccounts();
}
