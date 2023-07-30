package com.henriqueborba.digital_account.customer.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.henriqueborba.digital_account.customer.Customer;
import com.henriqueborba.digital_account.customer.CustomerController;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.Links;
import org.springframework.hateoas.RepresentationModel;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RequiredArgsConstructor
@Getter
@EqualsAndHashCode(callSuper = false)
@JsonIgnoreProperties({"_link"})
public class CustomerResponse extends RepresentationModel<CustomerResponse> {
    private final String name;
    private final String cpf;
    private final String email;

    public static CustomerResponse fromEntity(Customer customer) {
        return new CustomerResponse(customer.getName(), customer.getCpf(), customer.getEmail())
                .add(linkTo(methodOn(CustomerController.class).getCustomer(customer.getId())).withSelfRel());
    }

    @Override
    @Hidden
    public Links getLinks() {
        return super.getLinks();
    }
}
