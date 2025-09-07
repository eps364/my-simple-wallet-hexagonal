package br.dev.mission.simplewallet.core.model.transaction;

public enum TransactionTypeCore {
    IN(0, "INCOME"), EX(1, "EXPENSE");

    private final Integer code;
    private final String description;

    TransactionTypeCore(Integer code, String description) {
        this.code = code;
        this.description = description;
    }

    public Integer getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    public static TransactionTypeCore fromCode(Integer code) {
        for (TransactionTypeCore type : values()) {
            if (type.code.equals(code))
                return type;
        }
        throw new IllegalArgumentException("Invalid TransactionType code: " + code);
    }
}
