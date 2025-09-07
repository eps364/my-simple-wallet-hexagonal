package br.dev.mission.simplewallet.core.service.account;

import java.util.List;
import java.util.Objects;

import org.springframework.stereotype.Service;

import br.dev.mission.simplewallet.core.exceptions.InvalidExceptionCore;
import br.dev.mission.simplewallet.core.exceptions.NotFoundExceptionCore;
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
            throw new InvalidExceptionCore("Account ID must be null for creation");
        }

        return accountRepositoryPort.save(account);
    }

    @Override
    public AccountCore getAccountById(Long id) {
        validateId(id);

        return accountRepositoryPort.findById(id)
                .orElseThrow(() -> new NotFoundExceptionCore("Account not found with id: " + id));
    }

    @Override
    public AccountCore updateAccount(Long id, AccountCore account) {
        validateAccountForUpdate(account);

        if (!Objects.equals(id, account.getId())) {
            throw new InvalidExceptionCore("Path ID and Account ID must match for update");
        }

        if (!accountRepositoryPort.existsById(id)) {
            throw new NotFoundExceptionCore("Account not found with id: " + id);
        }

        return accountRepositoryPort.update(account);
    }

    @Override
    public void deleteAccount(Long id) {
        validateId(id);

        // Verifica se a conta existe antes de deletar
        if (!accountRepositoryPort.existsById(id)) {
            throw new NotFoundExceptionCore("Account not found with id: " + id);
        }

        accountRepositoryPort.deleteById(id);
    }

    @Override
    public List<AccountCore> getAllAccounts() {
        return accountRepositoryPort.findAll();
    }

    private void validateId(Long id) {
        if (id == null || id <= 0) {
            throw new InvalidExceptionCore("Invalid account ID: " + id);
        }
    }

    private void validateAccountForCreation(AccountCore account) {
        if (account == null) {
            throw new InvalidExceptionCore("Account cannot be null");
        }

        if (account.getDescription() == null || account.getDescription().trim().isEmpty()) {
            throw new InvalidExceptionCore("Account description is required");
        }

        if (account.getUserId() == null) {
            throw new InvalidExceptionCore("User ID is required");
        }

    }

    private void validateAccountForUpdate(AccountCore account) {
        validateAccountForCreation(account);

        if (account.getId() == null || account.getId() <= 0) {
            throw new InvalidExceptionCore("Valid account ID is required for update");
        }
    }
}