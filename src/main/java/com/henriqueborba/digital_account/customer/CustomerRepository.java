package com.henriqueborba.digital_account.customer;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    Optional<Customer> findCustomerByEmail(String email);

    Optional<Customer> findCustomerByEmailAndPassword(String email, String password);
}
