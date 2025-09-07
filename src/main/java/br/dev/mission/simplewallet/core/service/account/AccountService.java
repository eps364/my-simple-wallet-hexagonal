package br.dev.mission.simplewallet.core.service.account;

import java.util.List;
import java.util.Objects;

import org.springframework.stereotype.Service;

import br.dev.mission.simplewallet.core.exceptions.InvalidException;
import br.dev.mission.simplewallet.core.exceptions.NotFoundException;
import br.dev.mission.simplewallet.core.model.account.AccountCore;
import br.dev.mission.simplewallet.core.ports.inbounce.account.AccountPort;
import br.dev.mission.simplewallet.core.ports.output.account.AccountRepositoryPort;

@Service
public class AccountService implements AccountPort {

    private final AccountRepositoryPort accountRepositoryPort;

    public AccountService(AccountRepositoryPort accountRepositoryPort) {
        this.accountRepositoryPort = Objects.requireNonNull(accountRepositoryPort,
                "AccountRepositoryPort cannot be null");
    }

    @Override
    public AccountCore createAccount(AccountCore account) {
        validateAccountForCreation(account);

        // Regras de negócio específicas para criação
        if (account.getId() != null) {
            throw new InvalidException("Account ID must be null for creation");
        }

        return accountRepositoryPort.save(account);
    }

    @Override
    public AccountCore getAccountById(Long id) {
        validateId(id);

        return accountRepositoryPort.findById(id)
                .orElseThrow(() -> new NotFoundException("Account not found with id: " + id));
    }

    @Override
    public AccountCore updateAccount(AccountCore account) {
        validateAccountForUpdate(account);
        if (!accountRepositoryPort.existsById(account.getId())) {
            throw new NotFoundException("Account not found with id: " + account.getId());
        }

        return accountRepositoryPort.update(account);
    }

    @Override
    public void deleteAccount(Long id) {
        validateId(id);

        // Verifica se a conta existe antes de deletar
        if (!accountRepositoryPort.existsById(id)) {
            throw new NotFoundException("Account not found with id: " + id);
        }

        // Aqui poderia ter regras de negócio para deletar
        // Ex: verificar se não há transações pendentes

        accountRepositoryPort.deleteById(id);
    }

    @Override
    public List<AccountCore> getAllAccounts() {
        return accountRepositoryPort.findAll();
    }

    // Métodos de validação privados
    private void validateId(Long id) {
        if (id == null || id <= 0) {
            throw new InvalidException("Invalid account ID: " + id);
        }
    }

    private void validateAccountForCreation(AccountCore account) {
        if (account == null) {
            throw new InvalidException("Account cannot be null");
        }

        if (account.getDescription() == null || account.getDescription().trim().isEmpty()) {
            throw new InvalidException("Account description is required");
        }

        if (account.getUserId() == null) {
            throw new InvalidException("User ID is required");
        }

    }

    private void validateAccountForUpdate(AccountCore account) {
        validateAccountForCreation(account);

        if (account.getId() == null || account.getId() <= 0) {
            throw new InvalidException("Valid account ID is required for update");
        }
    }
}