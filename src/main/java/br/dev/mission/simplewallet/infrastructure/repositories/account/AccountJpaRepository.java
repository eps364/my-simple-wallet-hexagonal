package br.dev.mission.simplewallet.infrastructure.repositories.account;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.dev.mission.simplewallet.infrastructure.entities.account.AccountEntity;

@Repository
public interface AccountJpaRepository extends JpaRepository<AccountEntity, Long> {

}