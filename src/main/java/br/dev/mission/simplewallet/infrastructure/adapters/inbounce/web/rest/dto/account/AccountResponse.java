package br.dev.mission.simplewallet.infrastructure.adapters.inbounce.web.rest.dto.account;

import java.math.BigDecimal;
import java.util.UUID;

public record AccountResponse(
    Long id,
    String description,
    BigDecimal balance,
    BigDecimal credit,
    Integer dueDate,
    UUID userId,
    String username
) {}
