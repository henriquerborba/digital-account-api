package com.henriqueborba.digital_account.util;

import com.henriqueborba.digital_account.customer.Customer;
import com.henriqueborba.digital_account.customer.dto.CustomerRequest;

public class CustomerCreator {

    public static CustomerRequest createValidCustomerRequest() {
        return CustomerRequest.builder()
                .name("John Doe")
                .cpf("106.712.620-17")
                .email("email@email.com")
                .password("123456")
                .build();
    }

    public static Customer createValidCustomer() {
        return Customer.builder()
                .id(1L)
                .name("John Doe")
                .cpf("106.712.620-17")
                .email("email@email.com")
                .password("123456")
                .build();
    }

}
