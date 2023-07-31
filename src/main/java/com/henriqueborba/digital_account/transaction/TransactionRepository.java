package com.henriqueborba.digital_account.transaction;

import com.henriqueborba.digital_account.account.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    List<Transaction> findTransactionsByAccount(Account account);
}
