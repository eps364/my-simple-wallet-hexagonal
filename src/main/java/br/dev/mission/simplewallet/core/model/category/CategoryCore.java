package br.dev.mission.simplewallet.core.model.category;

import java.util.UUID;

import br.dev.mission.simplewallet.core.model.transaction.TransactionTypeCore;

public class CategoryCore {
    private Long id;
    private String category;
    private TransactionTypeCore type;
    private UUID userId;
    private String color;
}