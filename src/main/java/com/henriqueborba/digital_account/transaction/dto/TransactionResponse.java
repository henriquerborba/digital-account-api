package com.henriqueborba.digital_account.transaction.dto;

import com.henriqueborba.digital_account.transaction.Transaction;

public record TransactionResponse(
        Long id,
        Transaction.TransactionType type,
        String amount,
        String createdAt
) {
}
