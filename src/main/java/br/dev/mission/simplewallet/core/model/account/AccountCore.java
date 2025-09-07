package br.dev.mission.simplewallet.core.model.account;

import java.math.BigDecimal;
import java.util.UUID;

public class AccountCore {
    private Long id;
    private String description;
    private BigDecimal balance;
    private BigDecimal credit;
    private Integer dueDate;
    private UUID userId;

}
