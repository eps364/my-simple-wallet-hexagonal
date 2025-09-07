package br.dev.mission.simplewallet.core.ports.inbounce.account;

import java.util.List;

import br.dev.mission.simplewallet.core.model.account.AccountCore;

public interface AccountPort {
    void createAccount(AccountCore account);
    AccountCore getAccountById(Long id);
    void updateAccount(AccountCore account);
    void deleteAccount(Long id);
    List<AccountCore> getAllAccounts();
}
