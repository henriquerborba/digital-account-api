package com.henriqueborba.digital_account.customer.dto;

public record AuthenticationRequest(
        String email,
        String password
) {

}
