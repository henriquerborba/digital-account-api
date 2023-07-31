package com.henriqueborba.digital_account.account;

import com.henriqueborba.digital_account.account.dto.AccountRequest;
import com.henriqueborba.digital_account.account.dto.AccountResponse;
import com.henriqueborba.digital_account.transaction.TransactionMapper;
import com.henriqueborba.digital_account.transaction.TransactionService;
import com.henriqueborba.digital_account.transaction.dto.TransactionRequest;
import com.henriqueborba.digital_account.transaction.dto.TransactionResponse;
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

    private final TransactionService transactionService;

    private final TransactionMapper transactionMapper;

    @PostMapping
    @Operation(summary = "Create a customer account")
    @ResponseStatus(HttpStatus.CREATED)
    public AccountResponse createAccount(@Validated @RequestBody AccountRequest request) {
        return mapper.fromEntity(service.createAccount(mapper.toEntity(request)));
    }

    @PostMapping("/{id}/transactions")
    @Operation(summary = "Create a transaction for a customer account")
    @ResponseStatus(HttpStatus.CREATED)
    public TransactionResponse createTransaction(
            @PathVariable(value = "id") Long accountId,
            @Validated @RequestBody TransactionRequest request) {
        return transactionMapper.fromEntity(transactionService.save(accountId, transactionMapper.toEntity(request)));
    }

    @GetMapping("/{id}/transactions")
    @Operation(summary = "Get all transactions from a customer account")
    public List<TransactionResponse> findTransactionsByAccount(@PathVariable(value = "id") Long accountId) {
        return transactionService.findTransactionsByAccount(accountId).stream().map(transactionMapper::fromEntity).toList();
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a customer account")
    public AccountResponse updateAccount(@PathVariable Long id, @Validated @RequestBody AccountRequest request) {
        return mapper.fromEntity(service.updateAccount(id, mapper.toEntity(request)));
    }

    @GetMapping
    @Operation(summary = "Get all accounts from the logged customer")
    public List<AccountResponse> findAccountsByCustomer() {
        return service.findAccountsByCustomer().stream().map(mapper::fromEntity).toList();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get an customer account by id")
    public AccountResponse getAccount(@PathVariable Long id) {
        return mapper.fromEntity(service.getAccount(id));
    }

}
