package com.henriqueborba.digital_account.transaction;

import com.henriqueborba.digital_account.account.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final TransactionRepository transactionRepository;

    private final AccountService accountService;

    public Transaction save(Long accountId, Transaction transaction) {
        final var account = accountService.getAccount(accountId);
        switch (transaction.getType()) {
            case DEPOSIT -> account.setBalance(account.getBalance().add(transaction.getAmount()));
            case WITHDRAWAL -> account.setBalance(account.getBalance().subtract(transaction.getAmount()));
            default -> throw new IllegalStateException("Unexpected value: " + transaction.getType());
        }
        transaction.setAccount(account);
        return transactionRepository.save(transaction);
    }

    public List<Transaction> findTransactionsByAccount(Long accountId) {
        final var account = accountService.getAccount(accountId);
        return transactionRepository.findTransactionsByAccount(account);
    }

}
