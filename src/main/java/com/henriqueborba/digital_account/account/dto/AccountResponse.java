package com.henriqueborba.digital_account.account.dto;

import com.henriqueborba.digital_account.account.Account;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.Links;
import org.springframework.hateoas.RepresentationModel;

import java.math.BigDecimal;

@RequiredArgsConstructor
@Getter
@EqualsAndHashCode(callSuper = false)
public class AccountResponse extends RepresentationModel<AccountResponse> {

    private final Long id;

    private final Account.AccountType accountType;

    private final BigDecimal balance;

    @Override
    @Hidden
    @NonNull
    public Links getLinks() {
        return super.getLinks();
    }
}
