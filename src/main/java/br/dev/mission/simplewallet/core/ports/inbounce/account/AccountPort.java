package br.dev.mission.simplewallet.core.ports.inbounce.account;

import java.util.List;

import br.dev.mission.simplewallet.core.model.account.AccountCore;
import br.dev.mission.simplewallet.core.ports.output.dto.account.AccountResponseCore;

public interface AccountPort {
    AccountResponseCore createAccount(AccountCore account);

    AccountResponseCore getAccountById(Long id);

    AccountResponseCore updateAccount(Long id, AccountCore account);

    void deleteAccount(Long id);

    List<AccountResponseCore> getAllAccounts();
}
