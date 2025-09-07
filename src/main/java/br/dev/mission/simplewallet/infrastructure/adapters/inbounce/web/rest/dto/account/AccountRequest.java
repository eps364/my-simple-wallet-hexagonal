package br.dev.mission.simplewallet.infrastructure.adapters.inbounce.web.rest.dto.account;

import java.math.BigDecimal;

public record AccountRequest(
    String description,
    BigDecimal balance,
    BigDecimal credit,
    Integer dueDate
) {}
