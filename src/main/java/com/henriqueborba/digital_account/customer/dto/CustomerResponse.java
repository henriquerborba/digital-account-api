package com.henriqueborba.digital_account.customer.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.Links;
import org.springframework.hateoas.RepresentationModel;

@RequiredArgsConstructor
@Getter
@EqualsAndHashCode(callSuper = false)
@JsonIgnoreProperties({"_link"})
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
