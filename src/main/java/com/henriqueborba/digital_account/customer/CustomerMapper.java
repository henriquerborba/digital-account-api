package com.henriqueborba.digital_account.customer;

import com.henriqueborba.digital_account.customer.dto.AuthenticationRequest;
import com.henriqueborba.digital_account.customer.dto.CustomerRequest;
import com.henriqueborba.digital_account.customer.dto.CustomerResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
@RequiredArgsConstructor
public class CustomerMapper {

    private final PasswordEncoder passwordEncoder;

    public Customer toEntity(AuthenticationRequest request) {
        return Customer.builder()
                .email(request.email())
                .password(request.password())
                .build();
    }

    public Customer toEntity(CustomerRequest request) {
        return Customer.builder()
                .name(request.name())
                .cpf(request.cpf())
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .build();
    }

    public CustomerResponse fromEntity(Customer customer) {
        return new CustomerResponse(customer.getName(), customer.getCpf(), customer.getEmail())
                .add(linkTo(methodOn(CustomerController.class).getCustomer()).withSelfRel());
    }
}
