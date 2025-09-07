package br.dev.mission.simplewallet.api.application.domain.model.category;

import java.util.UUID;

import br.dev.mission.simplewallet.api.application.domain.model.transaction.TransactionType;

public class Category {

    private Long id;
    private String category;
    private TransactionType type;
    private UUID userId;
    private String color;

}