package com.henriqueborba.digital_account.account;

import com.henriqueborba.digital_account.customer.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AccountRepository extends JpaRepository<Account, Long> {

    List<Account> findAccountsByCustomer(Customer customer);

}
