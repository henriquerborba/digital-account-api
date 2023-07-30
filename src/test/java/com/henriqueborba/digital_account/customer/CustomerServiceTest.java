package com.henriqueborba.digital_account.customer;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("Tests for Customer Service")
class CustomerServiceTest {

    @InjectMocks
    private CustomerService customerService;

    @Mock
    private CustomerRepository repository;

    @Test
    @DisplayName("Create customer when a new email is provided")
    void testCreateCustomer() {
        // Mock the behavior of repository.findCustomerByEmail
        when(repository.findCustomerByEmail("existing@example.com")).thenReturn(Optional.of(new Customer()));

        // Test when an existing email is provided
        Customer customer = Customer.builder().email("existing@example.com").build();
        assertThrows(IllegalArgumentException.class, () -> customerService.createCustomer(customer));

        // Test when a new email is provided
        Customer newCustomer = Customer.builder().email("new@example.com").build();
        when(repository.save(newCustomer)).thenReturn(newCustomer);
        Customer createdCustomer = customerService.createCustomer(newCustomer);

        assertNotNull(createdCustomer);
        assertEquals("new@example.com", createdCustomer.getEmail());
    }

    @Test
    @DisplayName("Create customer when an existing email is provided should throw IllegalArgumentException")
    void testCreateCustomerWithExistingEmail() {
        // Mock the behavior of repository.findCustomerByEmail
        final var email = "existing@email.com";
        when(repository.findCustomerByEmail(email)).thenReturn(Optional.of(new Customer()));

        // Test when an existing email is provided
        Customer customer = Customer.builder().email(email).build();
        assertThrows(IllegalArgumentException.class, () -> customerService.createCustomer(customer));
    }


    @Test
    @DisplayName("Update customer when a new email is provided")
    void testUpdateCustomer() {
        Long customerId = 1L;

        Customer existingCustomer = Customer.builder()
                .id(customerId)
                .name("John Doe")
                .cpf("123456789")
                .email("john.doe@example.com")
                .password("password")
                .build();

        Customer updatedCustomer = Customer.builder()
                .name("Jane Doe")
                .cpf("987654321")
                .email("jane.doe@example.com")
                .password("newpassword")
                .build();

        // Mock the behavior of repository.findById
        when(repository.findById(customerId)).thenReturn(Optional.of(existingCustomer));

        // Mock the behavior of repository.save
        when(repository.save(existingCustomer)).thenReturn(existingCustomer);

        Customer result = customerService.updateCustomer(customerId, updatedCustomer);

        assertNotNull(result);
        assertEquals("Jane Doe", result.getName());
        assertEquals("987654321", result.getCpf());
        assertEquals("jane.doe@example.com", result.getEmail());
    }
}
