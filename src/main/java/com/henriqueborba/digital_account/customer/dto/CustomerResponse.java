package com.henriqueborba.digital_account.customer.dto;

import com.henriqueborba.digital_account.customer.Customer;

public record CustomerResponse(
        String name,
        String cpf,
        String email
) {
    public static CustomerResponse fromEntity(Customer customer) {
        return new CustomerResponse(
                customer.getName(),
                customer.getCpf(),
                customer.getEmail()
        );
    }
}
