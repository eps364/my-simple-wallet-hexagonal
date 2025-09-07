package br.dev.mission.simplewallet.infrastructure.adapters.outbound.account;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import br.dev.mission.simplewallet.core.exceptions.NotFoundExceptionCore;
import br.dev.mission.simplewallet.core.model.account.AccountCore;
import br.dev.mission.simplewallet.core.ports.output.repository.account.AccountRepositoryPort;
import br.dev.mission.simplewallet.infrastructure.entities.account.AccountEntity;
import br.dev.mission.simplewallet.infrastructure.mappers.account.AccountMapper;
import br.dev.mission.simplewallet.infrastructure.repositories.account.AccountJpaRepository;

@Component
public class AccountJpaAdapter implements AccountRepositoryPort {

    private final AccountJpaRepository jpaRepository;
    private final AccountMapper mapper;

    public AccountJpaAdapter(AccountJpaRepository jpaRepository, AccountMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    @Override
    public AccountCore save(AccountCore account) {
        AccountEntity entity = mapper.toEntity(account);
        AccountEntity savedEntity = jpaRepository.save(entity);
        return mapper.toCore(savedEntity);
    }

    @Override
    public Optional<AccountCore> findById(Long id) {
        return jpaRepository.findById(id).map(mapper::toCore);
    }

    @Override
    public List<AccountCore> findAll() {
        return jpaRepository.findAll().stream().map(mapper::toCore).toList();
    }

    @Override
    public void deleteById(Long id) {
        jpaRepository.deleteById(id);
    }

    @Override
    public boolean existsById(Long id) {
        return jpaRepository.existsById(id);
    }

    @Override
    public AccountCore update(AccountCore account) {
        if (!jpaRepository.existsById(account.getId())) {
            throw new NotFoundExceptionCore("Account with id " + account.getId() + " not found.");
        }
        AccountEntity entity = mapper.toEntity(account);
        AccountEntity updatedEntity = jpaRepository.save(entity);
        return mapper.toCore(updatedEntity);
    }
}