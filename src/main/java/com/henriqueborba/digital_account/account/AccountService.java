package com.henriqueborba.digital_account.account;

import com.henriqueborba.digital_account.customer.CustomerService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository repository;

    private final CustomerService customerService;

    public Account createAccount(Account account) {
        final var customer = customerService.getCustomer();
        account.setCustomer(customer);
        return repository.save(account);
    }

    @SneakyThrows
    public Account updateAccount(Long accountId, Account account) {
        final var accountSaved = getAccount(accountId);

        accountSaved.setAccountType(account.getAccountType());
        accountSaved.setBalance(account.getBalance());

        return repository.save(accountSaved);
    }

    public List<Account> findAccountsByCustomer() {
        final var customer = customerService.getCustomer();
        return repository.findAccountsByCustomer(customer);
    }

    @SneakyThrows
    public Account getAccount(Long id) {
        final var account = repository.findById(id).orElseThrow(NotFoundException::new);
        final var customerAccounts = findAccountsByCustomer();
        if (!customerAccounts.contains(account)) {
            throw new NotFoundException();
        }
        return account;
    }
}
