package br.dev.mission.simplewallet.core.ports.output.dto.account;

import java.math.BigDecimal;
import java.util.UUID;

import br.dev.mission.simplewallet.core.model.account.AccountCore;

public record AccountResponseCore(
    Long id,
    String description,
    BigDecimal balance,
    BigDecimal credit,
    Integer dueDate,
    UUID userId,
    String username
) {

    public static AccountCore toModel(AccountResponseCore accountResponseCore) {
        return new AccountCore(
            accountResponseCore.id(),
            accountResponseCore.description(),
            accountResponseCore.balance(),
            accountResponseCore.credit(),
            accountResponseCore.dueDate(),
            accountResponseCore.userId()
        );
    }

    public static AccountResponseCore fromModel(AccountCore accountCore) {
        return new AccountResponseCore(
            accountCore.getId(),
            accountCore.getDescription(),
            accountCore.getBalance(),
            accountCore.getCredit(),
            accountCore.getDueDate(),
            accountCore.getUserId(),
            ""
        );
    }
}
