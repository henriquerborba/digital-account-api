package com.henriqueborba.digital_account.account.dto;

import com.henriqueborba.digital_account.account.Account;
import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record AccountRequest(
        Account.AccountType accountType,
        BigDecimal balance

) {
    public Account toEntity() {
        return Account.builder()
                .accountType(accountType)
                .balance(balance)
                .build();
    }
}
