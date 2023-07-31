package com.henriqueborba.digital_account.account.dto;

import com.henriqueborba.digital_account.account.Account;
import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record AccountRequest(
        Account.AccountType accountType,
        BigDecimal balance

) {

}
