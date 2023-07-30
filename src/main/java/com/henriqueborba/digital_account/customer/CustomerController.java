package com.henriqueborba.digital_account.customer;

import com.henriqueborba.digital_account.customer.dto.CustomerRequest;
import com.henriqueborba.digital_account.customer.dto.CustomerResponse;
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

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CustomerResponse createCustomer(@Validated @RequestBody CustomerRequest request) {
        return CustomerResponse.fromEntity(service.createCustomer(request.toEntity()));
    }

    @PutMapping("/{id}")
    public CustomerResponse updateCustomer(@PathVariable Long id, @Validated @RequestBody CustomerRequest request) {
        return CustomerResponse.fromEntity(service.updateCustomer(id, request.toEntity()));
    }

    @GetMapping("/{id}")
    public CustomerResponse getCustomer(@PathVariable Long id) {
        return CustomerResponse.fromEntity(service.getCustomer(id));
    }

}
