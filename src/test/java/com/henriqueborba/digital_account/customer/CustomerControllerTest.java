package com.henriqueborba.digital_account.customer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.henriqueborba.digital_account.customer.dto.CustomerRequest;
import com.henriqueborba.digital_account.util.CustomerCreator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@DisplayName("Tests for Customer Controller")
class CustomerControllerTest {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private MockMvc mockMvc;
    @Mock
    private CustomerService customerService;
    @Mock
    private CustomerMapper customerMapper;
    @InjectMocks
    private CustomerController customerController;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(customerController).build();
    }

    @Test
    @DisplayName("Create customer when a valid customer request is provided should return 201 Created")
    void testCreateCustomer() throws Exception {
        // Test creating a new customer
        CustomerRequest request = CustomerCreator.createValidCustomerRequest();

        when(customerService.createCustomer(any())).thenReturn("token");
        when(customerMapper.toEntity(any(CustomerRequest.class))).thenReturn(CustomerCreator.createValidCustomer());

        mockMvc.perform(MockMvcRequestBuilders.post("/api/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.token").value("token"));
    }

    @Test
    @DisplayName("Create customer when an existing email is provided should return 400 Bad Request")
    void testCreateCustomerWithExistingEmail() throws Exception {
        CustomerRequest request = new CustomerRequest("John Doe", "106.712.620-17", "invalid_email", "password");

        mockMvc.perform(MockMvcRequestBuilders.post("/api/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Create customer when an invalid email is provided should return 400 Bad Request")
    void testCreateCustomerWithInvalidEmail() throws Exception {
        var request = CustomerRequest.builder()
                .name("John Doe")
                .cpf("106.712.620-17")
                .password("123456")
                .build();

        mockMvc.perform(MockMvcRequestBuilders.post("/api/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());

        request = CustomerRequest.builder()
                .name("John Doe")
                .cpf("106.712.620-17")
                .email("invalid_email")
                .password("123456")
                .build();

        mockMvc.perform(MockMvcRequestBuilders.post("/api/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());

    }

    @Test
    @DisplayName("Create customer when cpf is invalid should return 400 Bad Request")
    void testCreateCustomerWithInvalidCPF() throws Exception {
        CustomerRequest request = new CustomerRequest("John Doe", "invalid_cpf", "john@example.com", "password");

        mockMvc.perform(MockMvcRequestBuilders.post("/api/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());

        request = CustomerRequest.builder()
                .name("John Doe")
                .email("john@example.com")
                .password("password")
                .build();

        mockMvc.perform(MockMvcRequestBuilders.post("/api/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Create customer when an invalid password is provided should return 400 Bad Request")
    void testCreateCustomerWithInvalidPassword() throws Exception {
        CustomerRequest request = new CustomerRequest("John Doe", "106.712.620-17", "john@example.com", "123");

        mockMvc.perform(MockMvcRequestBuilders.post("/api/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());

        request = CustomerRequest.builder()
                .name("John Doe")
                .cpf("106.712.620-17")
                .email("john@example.com")
                .build();

        mockMvc.perform(MockMvcRequestBuilders.post("/api/customers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)));
    }

    @Test
    @DisplayName("Create customer when an invalid name is provided should return 400 Bad Request")
    void testCreateCustomerWithInvalidName() throws Exception {
        final var request = CustomerRequest.builder()
                .cpf("106.712.620-17")
                .email("john@example.com")
                .password("123456")
                .build();

        mockMvc.perform(MockMvcRequestBuilders.post("/api/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Update customer when a valid customer request is provided should return 200 OK")
    void testUpdateCustomer() throws Exception {
        final var request = CustomerCreator.createValidCustomerRequest();

        final var updatedCustomer = CustomerCreator.createValidCustomer();
        when(customerService.updateCustomer(any())).thenReturn(updatedCustomer);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/customers/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Get customer when a valid id is provided should return 200 OK")
    void testGetCustomer() throws Exception {
        final var customer = CustomerCreator.createValidCustomer();
        when(customerService.getCustomer()).thenReturn(customer);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/customers/"))
                .andExpect(status().isOk());
    }

}
