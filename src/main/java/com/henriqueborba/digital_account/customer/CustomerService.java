package com.henriqueborba.digital_account.customer;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository repository;

    public Customer createCustomer(Customer customer) {
        return repository.save(customer);
    }

    @SneakyThrows
    public Customer updateCustomer(Long customerId, Customer customerUpdated) {
        final var customer = repository.findById(customerId)
                .orElseThrow(NotFoundException::new);

        customer.setName(customerUpdated.getName());
        customer.setCpf(customerUpdated.getCpf());
        customer.setEmail(customerUpdated.getEmail());
        customer.setPassword(customerUpdated.getPassword());

        return repository.save(customer);
    }

    @SneakyThrows
    public Customer getCustomer(Long id) {
        return repository.findById(id)
                .orElseThrow(NotFoundException::new);
    }
}
