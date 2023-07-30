package com.henriqueborba.digital_account.customer.dto;

import org.springframework.hateoas.Links;
import org.springframework.hateoas.RepresentationModel;

import io.swagger.v3.oas.annotations.Hidden;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
@EqualsAndHashCode(callSuper = false)
public class CustomerResponse extends RepresentationModel<CustomerResponse> {
    private final String name;
    private final String cpf;
    private final String email;

    @Hidden
    @NonNull
    @Override
    public Links getLinks() {
        return super.getLinks();
    }
}
