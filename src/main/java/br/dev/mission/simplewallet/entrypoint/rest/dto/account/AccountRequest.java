package br.dev.mission.simplewallet.entrypoint.rest.dto.account;

import java.math.BigDecimal;

public record AccountRequest(
    String description,
    BigDecimal balance,
    BigDecimal credit,
    Integer dueDate
) {}
