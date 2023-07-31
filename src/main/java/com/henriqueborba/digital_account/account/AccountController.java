package com.henriqueborba.digital_account.account;

import com.henriqueborba.digital_account.account.dto.AccountRequest;
import com.henriqueborba.digital_account.account.dto.AccountResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Tag(name = "Account")
@RequestMapping("/api/accounts")
@SecurityRequirement(name = "bearerAuth")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService service;

    private final AccountMapper mapper;

    @PostMapping
    @Operation(summary = "Create a customer account")
    @ResponseStatus(HttpStatus.CREATED)
    public AccountResponse createAccount(@Validated @RequestBody AccountRequest request) {
        return AccountMapper.fromEntity(service.createAccount(mapper.toEntity(request)));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a customer account")
    public AccountResponse updateAccount(@PathVariable Long id, @Validated @RequestBody AccountRequest request) {
        return AccountMapper.fromEntity(service.updateAccount(id, mapper.toEntity(request)));
    }

    @GetMapping
    @Operation(summary = "Get all accounts from the logged customer")
    public List<AccountResponse> findAccountsByCustomer() {
        return service.findAccountsByCustomer().stream().map(AccountMapper::fromEntity).toList();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get an customer account by id")
    public AccountResponse getAccount(@PathVariable Long id) {
        return AccountMapper.fromEntity(service.getAccount(id));
    }

}
