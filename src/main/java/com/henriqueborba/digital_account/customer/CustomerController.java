package com.henriqueborba.digital_account.customer;

import com.henriqueborba.digital_account.customer.dto.AuthenticationRequest;
import com.henriqueborba.digital_account.customer.dto.AuthenticationResponse;
import com.henriqueborba.digital_account.customer.dto.CustomerRequest;
import com.henriqueborba.digital_account.customer.dto.CustomerResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@Tag(name = "Customer")
@RequestMapping("/api/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService service;

    private final CustomerMapper mapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create a new customer")
    public AuthenticationResponse createCustomer(@Validated @RequestBody CustomerRequest request) {
        final String token = service.createCustomer(mapper.toEntity(request));
        return new AuthenticationResponse(token);
    }

    @PostMapping("/login")
    @Operation(summary = "Login")
    public AuthenticationResponse loginCustomer(@Validated @RequestBody AuthenticationRequest request) {
        final String token = service.loginCustomer(mapper.toEntity(request));
        return new AuthenticationResponse(token);
    }

    @PutMapping
    @Operation(summary = "Update logged customer")
    @SecurityRequirement(name = "bearerAuth")
    public CustomerResponse updateCustomer(@Validated @RequestBody CustomerRequest request) {
        return mapper.fromEntity(service.updateCustomer(mapper.toEntity(request)));
    }

    @GetMapping
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Get logged customer")
    public CustomerResponse getCustomer() {
        return mapper.fromEntity(service.getCustomer());
    }

}
