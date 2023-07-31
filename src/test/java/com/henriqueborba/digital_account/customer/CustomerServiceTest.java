package com.henriqueborba.digital_account.customer;

import com.henriqueborba.digital_account.core.config.JwtService;
import com.henriqueborba.digital_account.util.CustomerCreator;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("Tests for Customer Service")
class CustomerServiceTest {
    @InjectMocks
    private CustomerService customerService;
    @Mock
    private CustomerRepository repository;

    @Mock
    private JwtService jwtService;

    @BeforeAll
    static void setUp() {
        SecurityContext securityContext = mock(SecurityContext.class);
        Authentication authentication = mock(Authentication.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        when(authentication.getPrincipal()).thenReturn(CustomerCreator.createValidCustomer());
    }

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
        when(jwtService.generateToken(newCustomer)).thenReturn("token");
        String token = customerService.createCustomer(newCustomer);

        assertNotNull(token);
        assertEquals("token", token);
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

        Customer existingCustomer = CustomerCreator.createValidCustomer();

        Customer updatedCustomer = Customer.builder()
                .name("Jane Doe")
                .cpf("987654321")
                .email("jane.doe@example.com")
                .password("newpassword")
                .build();

        // Mock the behavior of repository.findById
        when(repository.findCustomerByEmail(any())).thenReturn(Optional.of(existingCustomer));

        // Mock the behavior of repository.save
        when(repository.save(existingCustomer)).thenReturn(existingCustomer);

        Customer result = customerService.updateCustomer(updatedCustomer);

        assertNotNull(result);
        assertEquals("Jane Doe", result.getName());
        assertEquals("987654321", result.getCpf());
        assertEquals("jane.doe@example.com", result.getEmail());
    }
}
