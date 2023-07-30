package com.henriqueborba.digital_account.customer.dto;


import com.henriqueborba.digital_account.customer.Customer;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.br.CPF;

public record CustomerRequest(

        @NotBlank(message = "Name is mandatory")
        String name,

        @NotBlank(message = "CPF is mandatory")
        @CPF(message = "CPF is invalid")
        String cpf,

        @NotBlank(message = "Email is mandatory")
        @Email(message = "Email is invalid")
        String email,

        @NotBlank(message = "Password is mandatory")
        @Size(min = 6, message = "Password must be at least 6 characters")
        String password
) {
    public Customer toEntity() {
        return Customer.builder()
                .name(name)
                .cpf(cpf)
                .email(email)
                .password(password)
                .build();
    }
}
