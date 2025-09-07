package br.dev.mission.simplewallet.core.model.transaction;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public class TransactionCore {

    private Long id;
    private LocalDate dueDate;
    private LocalDate effectiveDate;
    private String description;
    private BigDecimal amount;
    private BigDecimal effectiveAmount;
    private Long category;
    private TransactionTypeCore type;
    private LocalDateTime created;
    private LocalDateTime updated;
    private Long accountId;
    private UUID userId;

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
