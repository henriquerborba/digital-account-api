package com.henriqueborba.digital_account.transaction.dto;

import com.henriqueborba.digital_account.transaction.Transaction;

import java.math.BigDecimal;

public record TransactionRequest(
        Transaction.TransactionType type,
        BigDecimal amount
) {
}
