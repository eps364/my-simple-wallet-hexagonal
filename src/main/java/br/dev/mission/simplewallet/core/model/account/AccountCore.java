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

    public AccountCore() {
    }

    public AccountCore(Long id, String description, BigDecimal balance, BigDecimal credit, Integer dueDate,
            UUID userId) {
        this.id = id;
        this.description = description;
        this.balance = balance;
        this.credit = credit;
        this.dueDate = dueDate;
        this.userId = userId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public BigDecimal getCredit() {
        return credit;
    }

    public void setCredit(BigDecimal credit) {
        this.credit = credit;
    }

    public Integer getDueDate() {
        return dueDate;
    }

    public void setDueDate(Integer dueDate) {
        this.dueDate = dueDate;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

}
