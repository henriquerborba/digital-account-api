package com.henriqueborba.digital_account.account;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository repository;

    public Account createAccount(Account account) {
        return repository.save(account);
    }

    public Account updateAccount(Long accountId, Account account) {
        return repository.save(account);
    }

    @SneakyThrows
    public Account getAccount(Long id) {
        return repository.findById(id).orElseThrow(ChangeSetPersister.NotFoundException::new);
    }
}
