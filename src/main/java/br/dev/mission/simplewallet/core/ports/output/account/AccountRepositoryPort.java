package br.dev.mission.simplewallet.core.ports.output.account;

import java.util.List;
import java.util.Optional;

import br.dev.mission.simplewallet.core.model.account.AccountCore;

public interface AccountRepositoryPort {
    AccountCore save(AccountCore account);

    AccountCore update(AccountCore account);

    Optional<AccountCore> findById(Long id);

    List<AccountCore> findAll();

    void deleteById(Long id);

    boolean existsById(Long id);

}
