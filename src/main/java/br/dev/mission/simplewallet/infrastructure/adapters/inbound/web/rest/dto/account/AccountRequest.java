package br.dev.mission.simplewallet.infrastructure.adapters.inbound.web.rest.dto.account;

import java.math.BigDecimal;
import java.util.UUID;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import br.dev.mission.simplewallet.core.model.account.AccountCore;

public record AccountRequest(
    @NotBlank(message = "{account.description.notblank}")
    @Size(min = 1, max = 100, message = "{account.description.size}")
    String description,
    
    @NotNull(message = "{account.balance.notnull}")
    @DecimalMin(value = "0.0", inclusive = true, message = "{account.balance.positive}")
    BigDecimal balance,
    
    @DecimalMin(value = "0.0", inclusive = true, message = "{account.credit.positive}")
    BigDecimal credit,
    
    @Min(value = 1, message = "{account.duedate.range}")
    @Max(value = 31, message = "{account.duedate.range}")
    Integer dueDate,
    
    @NotNull(message = "{account.userid.notnull}")
    UUID userId
) {
    
    /**
     * Converte o DTO para o modelo de domínio
     */
    public AccountCore toCore() {
        return new AccountCore(
            null, // ID será gerado pelo banco
            this.description,
            this.balance,
            this.credit,
            this.dueDate,
            this.userId
        );
    }
    
    /**
     * Converte o DTO para o modelo de domínio com ID específico (para updates)
     */
    public AccountCore toCore(Long id) {
        return new AccountCore(
            id,
            this.description,
            this.balance,
            this.credit,
            this.dueDate,
            this.userId
        );
    }
}
