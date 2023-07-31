package com.henriqueborba.digital_account.customer;

import com.henriqueborba.digital_account.core.config.JwtService;
import com.henriqueborba.digital_account.core.utils.UserUtils;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository repository;

    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;

    public String createCustomer(Customer customer) {
        repository.findCustomerByEmail(customer.getEmail())
                .ifPresent(c -> {
                    throw new IllegalArgumentException("Email already exists");
                });
        customer = repository.save(customer);
        return jwtService.generateToken(customer);
    }

    @SneakyThrows
    public Customer updateCustomer(Customer customerUpdated) {
        final var loggedUser = UserUtils.getLoggedUser();
        final var customer = repository.findCustomerByEmail(loggedUser.getUsername())
                .orElseThrow(NotFoundException::new);

        customer.setName(customerUpdated.getName());
        customer.setCpf(customerUpdated.getCpf());
        customer.setEmail(customerUpdated.getEmail());
        customer.setPassword(customerUpdated.getPassword());

        return repository.save(customer);
    }

    @SneakyThrows
    public Customer getCustomer() {
        final var user = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return repository.findCustomerByEmail(user.getUsername())
                .orElseThrow(NotFoundException::new);
    }

    public String loginCustomer(Customer user) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword()));

        final var customer = repository.findCustomerByEmail(user.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("Invalid email/password"));
        return jwtService.generateToken(customer);
    }
}
