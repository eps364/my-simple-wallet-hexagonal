package br.dev.mission.simplewallet.core.ports.inbounce.transaction;

import java.util.List;

import br.dev.mission.simplewallet.core.model.transaction.TransactionCore;

public interface TransactionPort {
    void createTransaction(TransactionCore transaction);
    TransactionCore getTransactionById(Long id);
    void updateTransaction(TransactionCore transaction);
    void deleteTransaction(Long id);
    List<TransactionCore> getAllTransactions();
}
