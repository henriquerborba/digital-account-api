package com.henriqueborba.digital_account.account;

import com.henriqueborba.digital_account.account.dto.AccountRequest;
import com.henriqueborba.digital_account.account.dto.AccountResponse;
import org.springframework.stereotype.Service;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


@Service
public class AccountMapper {

    public AccountResponse fromEntity(Account account) {
        return new AccountResponse(account.getId(), account.getAccountType(), account.getBalance())
                .add(linkTo(methodOn(AccountController.class).getAccount(account.getId())).withSelfRel());
    }

    public Account toEntity(AccountRequest request) {
        return Account.builder()
                .accountType(request.accountType())
                .balance(request.balance())
                .build();
    }

}
