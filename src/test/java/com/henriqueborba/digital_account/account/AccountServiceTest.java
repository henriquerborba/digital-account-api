package com.henriqueborba.digital_account.account;

import com.henriqueborba.digital_account.customer.Customer;
import com.henriqueborba.digital_account.customer.CustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@DisplayName("Account Service Test")
class AccountServiceTest {

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private CustomerService customerService;

    @InjectMocks
    private AccountService accountService;

    private Customer mockCustomer;
    private List<Account> mockCustomerAccounts;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Initialize a mock customer and accounts for testing
        mockCustomer = new Customer();
        mockCustomer.setId(1L);
        mockCustomer.setName("John Doe");

        mockCustomerAccounts = new ArrayList<>();
        mockCustomerAccounts.add(new Account(1L, Account.AccountType.SAVINGS_ACCOUNT, new BigDecimal("100.00"), mockCustomer));
        mockCustomerAccounts.add(new Account(2L, Account.AccountType.CURRENT_ACCOUNT, new BigDecimal("500.00"), mockCustomer));

        when(customerService.getCustomer()).thenReturn(mockCustomer);
    }

    @DisplayName("Create Account")
    @Test
    void testCreateAccount() {
        // Arrange
        Account request = Account.builder()
                .accountType(Account.AccountType.CURRENT_ACCOUNT)
                .balance(new BigDecimal("1000.00"))
                .build();

        when(accountRepository.save(any(Account.class))).thenReturn(new Account(3L, Account.AccountType.CURRENT_ACCOUNT, new BigDecimal("1000.00"), mockCustomer));

        // Act
        Account createdAccount = accountService.createAccount(request);

        // Assert
        assertNotNull(createdAccount);
        assertEquals(Account.AccountType.CURRENT_ACCOUNT, createdAccount.getAccountType());
        assertEquals(new BigDecimal("1000.00"), createdAccount.getBalance());
        assertEquals(mockCustomer, createdAccount.getCustomer());
    }

    @Test
    @DisplayName("Update Account")
    void testUpdateAccount() {
        // Arrange
        Account request = Account.builder()
                .accountType(Account.AccountType.SAVINGS_ACCOUNT)
                .balance(new BigDecimal("200.00"))
                .build();
        Long accountId = 1L;

        when(accountRepository.findById(accountId)).thenReturn(Optional.of(mockCustomerAccounts.get(0)));
        when(accountRepository.save(any(Account.class))).thenReturn(new Account(accountId, Account.AccountType.SAVINGS_ACCOUNT, new BigDecimal("200.00"), mockCustomer));
        when(accountRepository.findAccountsByCustomer(any())).thenReturn(mockCustomerAccounts);

        // Act
        Account updatedAccount = accountService.updateAccount(accountId, request);

        // Assert
        assertNotNull(updatedAccount);
        assertEquals(Account.AccountType.SAVINGS_ACCOUNT, updatedAccount.getAccountType());
        assertEquals(new BigDecimal("200.00"), updatedAccount.getBalance());
        assertEquals(mockCustomer, updatedAccount.getCustomer());
    }

    @Test
    void testFindAccountsByCustomer() {
        // Arrange
        when(accountRepository.findAccountsByCustomer(mockCustomer)).thenReturn(mockCustomerAccounts);

        // Act
        List<Account> customerAccounts = accountService.findAccountsByCustomer();

        // Assert
        assertNotNull(customerAccounts);
        assertEquals(2, customerAccounts.size());
        assertEquals(mockCustomerAccounts, customerAccounts);
    }

    @Test
    @DisplayName("Get Account")
    void testGetAccount() {
        // Arrange
        Long accountId = 1L;

        when(accountRepository.findById(accountId)).thenReturn(Optional.of(mockCustomerAccounts.get(0)));
        when(accountRepository.findAccountsByCustomer(mockCustomer)).thenReturn(mockCustomerAccounts);

        // Act
        Account account = accountService.getAccount(accountId);

        // Assert
        assertNotNull(account);
        assertEquals(accountId, account.getId());
        assertEquals(Account.AccountType.SAVINGS_ACCOUNT, account.getAccountType());
        assertEquals(new BigDecimal("100.00"), account.getBalance());
        assertEquals(mockCustomer, account.getCustomer());
    }

    @Test
    @DisplayName("Get Account Not Found")
    void testGetAccountNotFound() {
        // Arrange
        Long nonExistentAccountId = 999L;

        when(accountRepository.findById(nonExistentAccountId)).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(NotFoundException.class, () -> accountService.getAccount(nonExistentAccountId));
    }
}
