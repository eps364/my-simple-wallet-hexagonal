package br.dev.mission.simplewallet.api.application.domain.model.transaction;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class Transaction {

    private Long id;

    private LocalDate dueDate;

    private LocalDate effectiveDate;

    private String description;

    private BigDecimal amount;

    private BigDecimal effectiveAmount;

    private Long category;

    private TransactionType type;

    private LocalDateTime created;

    private LocalDateTime updated;

    private Long accountId;

    public String getStatus() {
        if (this.effectiveDate != null) {
            return "liquidated";
        }
        if (this.dueDate != null && this.dueDate.isBefore(java.time.LocalDate.now())) {
            return "overdue";
        }
        return "pending";
    }

    protected void onCreate() {
        this.created = LocalDateTime.now();
        this.updated = LocalDateTime.now();
    }

    protected void onUpdate() {
        this.updated = LocalDateTime.now();
    }

}
