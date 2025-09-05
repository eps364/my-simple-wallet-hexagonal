package br.dev.mission.simplewallet.api.application.domain.model.account;

import java.math.BigDecimal;

public class Account {
    private Long id;
    private String description;
    private BigDecimal balance;
    private BigDecimal credit;
    private Integer dueDate;
    private String userId;

}
