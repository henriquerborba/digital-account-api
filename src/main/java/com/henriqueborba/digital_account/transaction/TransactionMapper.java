package com.henriqueborba.digital_account.transaction;

import com.henriqueborba.digital_account.transaction.dto.TransactionRequest;
import com.henriqueborba.digital_account.transaction.dto.TransactionResponse;
import org.springframework.stereotype.Service;

@Service
public class TransactionMapper {

    public Transaction toEntity(TransactionRequest transactionRequest) {
        return Transaction.builder()
                .type(transactionRequest.type())
                .amount(transactionRequest.amount())
                .build();
    }

    public TransactionResponse fromEntity(Transaction transaction) {
        return new TransactionResponse(
                transaction.getId(),
                transaction.getType(),
                transaction.getAmount().toString(),
                transaction.getCreatedAt().toString()
        );
    }
}
