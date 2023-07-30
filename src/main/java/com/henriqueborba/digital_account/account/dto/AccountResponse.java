package com.henriqueborba.digital_account.account.dto;

import com.henriqueborba.digital_account.account.Account;
import com.henriqueborba.digital_account.account.AccountController;
import com.henriqueborba.digital_account.customer.Customer;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.Links;
import org.springframework.hateoas.RepresentationModel;

import java.math.BigDecimal;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RequiredArgsConstructor
@Getter
@EqualsAndHashCode(callSuper = false)
public class AccountResponse extends RepresentationModel<AccountResponse> {

    private final Account.AccountType accountType;

    private final BigDecimal balance;

    private final Customer customer;

    public static AccountResponse fromEntity(Account account) {
        return new AccountResponse(account.getAccountType(), account.getBalance(), account.getCustomer())
                .add(linkTo(methodOn(AccountController.class).getAccount(account.getId())).withSelfRel());
    }

    @Override
    @Hidden
    public Links getLinks() {
        return super.getLinks();
    }
}
