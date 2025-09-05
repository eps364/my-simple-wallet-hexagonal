package br.dev.mission.simplewallet.api.application.domain.model.transaction;

public enum TransactionType {
    IN(0, "INCOME"), 
    EX(1, "EXPENSE");

    private final Integer code;
    private final String description;

    TransactionType(Integer code, String description) {
        this.code = code;
        this.description = description;
    }

    public Integer getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    public static TransactionType fromCode(Integer code) {
        for (TransactionType type : values()) {
            if (type.code.equals(code)) return type;
        }
        throw new IllegalArgumentException("Invalid TransactionType code: " + code);
    }
}
