package br.dev.mission.simplewallet.entrypoint.rest.dto.account;

import java.math.BigDecimal;

public record AccountResponse(
    Long id,
    String description,
    BigDecimal balance,
    BigDecimal credit,
    Integer dueDate,
    String userId,
    String username
) {}
