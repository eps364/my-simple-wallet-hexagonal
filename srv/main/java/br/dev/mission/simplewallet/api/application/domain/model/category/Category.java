package br.dev.mission.simplewallet.api.application.domain.model.category;

import br.dev.mission.simplewallet.api.application.domain.model.transaction.TransactionType;

public class Category {

    private Long id;
    private String category;
    private TransactionType type;
    private String userId;
    private String color;

}