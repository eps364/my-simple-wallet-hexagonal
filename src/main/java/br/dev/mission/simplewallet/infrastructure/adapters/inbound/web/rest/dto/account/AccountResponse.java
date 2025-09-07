package br.dev.mission.simplewallet.infrastructure.adapters.inbound.web.rest.dto.account;

import java.math.BigDecimal;
import java.util.UUID;

import br.dev.mission.simplewallet.core.ports.output.dto.account.AccountResponseCore;

public record AccountResponse(
    Long id,
    String description,
    BigDecimal balance,
    BigDecimal credit,
    Integer dueDate,
    UUID userId,
    String username
) {
    public static AccountResponse fromAccountResponseCore(AccountResponseCore accountResponseCore) {
        return new AccountResponse(
            accountResponseCore.id(),
            accountResponseCore.description(),
            accountResponseCore.balance(),
            accountResponseCore.credit(),
            accountResponseCore.dueDate(),
            accountResponseCore.userId(),
            ""
        );
    }

}
