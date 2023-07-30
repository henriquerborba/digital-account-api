package com.henriqueborba.digital_account.account;

import com.henriqueborba.digital_account.account.dto.AccountRequest;
import com.henriqueborba.digital_account.account.dto.AccountResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@Tag(name = "Account")
@RequestMapping("/api/accounts")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService service;

    @PostMapping
    @Operation(summary = "Create a new account")
    @ResponseStatus(HttpStatus.CREATED)
    public AccountResponse createAccount(@Validated @RequestBody AccountRequest request) {
        return AccountResponse.fromEntity(service.createAccount(request.toEntity()));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an account")
    public AccountResponse updateAccount(@PathVariable Long id, @Validated @RequestBody AccountRequest request) {
        return AccountResponse.fromEntity(service.updateAccount(id, request.toEntity()));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get an account by id")
    public AccountResponse getAccount(@PathVariable Long id) {
        return AccountResponse.fromEntity(service.getAccount(id));
    }

}
