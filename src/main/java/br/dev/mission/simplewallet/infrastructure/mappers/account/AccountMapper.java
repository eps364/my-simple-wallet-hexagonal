package br.dev.mission.simplewallet.infrastructure.mappers.account;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import br.dev.mission.simplewallet.core.model.account.AccountCore;
import br.dev.mission.simplewallet.infrastructure.entities.account.AccountEntity;

@Mapper(componentModel = "spring")
public interface AccountMapper {

    AccountMapper INSTANCE = Mappers.getMapper(AccountMapper.class);

    AccountEntity toEntity(AccountCore core);

    AccountCore toCore(AccountEntity entity);

    void updateEntityFromCore(AccountCore core, @MappingTarget AccountEntity entity);
}