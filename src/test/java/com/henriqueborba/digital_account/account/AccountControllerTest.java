package com.henriqueborba.digital_account.account;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.henriqueborba.digital_account.account.dto.AccountRequest;
import com.henriqueborba.digital_account.account.dto.AccountResponse;

@DisplayName("Account Controller Test")
class AccountControllerTest {

        @Mock
        private AccountService accountService;

        @Mock
        private AccountMapper accountMapper;

        @InjectMocks
        private AccountController accountController;

        private MockMvc mockMvc;

        @BeforeEach
        void setUp() {
                MockitoAnnotations.openMocks(this);
                mockMvc = MockMvcBuilders.standaloneSetup(accountController).build();
        }

        @Test
        @DisplayName("Create customer account valid request should return created status")
        void createAccount_ValidRequest_ReturnsCreatedStatus() throws Exception {
                // Arrange
                Account account = Account.builder()
                                .id(1L)
                                .accountType(Account.AccountType.SAVINGS_ACCOUNT)
                                .balance(BigDecimal.valueOf(100))
                                .build();
                AccountResponse response = new AccountResponse(1L, Account.AccountType.SAVINGS_ACCOUNT,
                                BigDecimal.valueOf(100));

                when(accountService.createAccount(any(Account.class))).thenReturn(account);
                when(accountMapper.toEntity(any(AccountRequest.class))).thenReturn(account);
                when(accountMapper.fromEntity(any(Account.class))).thenReturn(response);

                // Act and Assert
                mockMvc.perform(post("/api/accounts")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{ \"accountType\": \"SAVINGS_ACCOUNT\", \"balance\": 100 }"))
                                .andExpect(status().isCreated())
                                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                                .andExpect(jsonPath("$.id").value(1))
                                .andExpect(jsonPath("$.accountType").value("SAVINGS_ACCOUNT"))
                                .andExpect(jsonPath("$.balance").value(100));
        }

        @Test
        @DisplayName("update customer account valid request should return ok status")
        void updateAccount_ValidRequest_ReturnsOkStatus() throws Exception {
                // Arrange
                Account account = Account.builder()
                                .id(1L)
                                .accountType(Account.AccountType.SAVINGS_ACCOUNT)
                                .balance(BigDecimal.valueOf(100))
                                .build();
                Account updatedAccount = Account.builder()
                                .id(1L)
                                .accountType(Account.AccountType.SAVINGS_ACCOUNT)
                                .balance(BigDecimal.valueOf(200))
                                .build();
                AccountResponse response = new AccountResponse(1L, Account.AccountType.SAVINGS_ACCOUNT,
                                BigDecimal.valueOf(200));

                when(accountService.updateAccount(1L, account)).thenReturn(updatedAccount);
                when(accountMapper.toEntity(any(AccountRequest.class))).thenReturn(account);
                when(accountMapper.fromEntity(any(Account.class))).thenReturn(response);

                // Act and Assert
                mockMvc.perform(put("/api/accounts/1")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{ \"accountType\": \"SAVINGS_ACCOUNT\", \"balance\": 200 }"))
                                .andExpect(status().isOk())
                                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                                .andExpect(jsonPath("$.id").value(1))
                                .andExpect(jsonPath("$.accountType").value("SAVINGS_ACCOUNT"))
                                .andExpect(jsonPath("$.balance").value(200));
        }

        @Test
        @DisplayName("find accounts by customer should return ok status")
        void findAccountsByCustomer_ReturnsOkStatus() throws Exception {
                // Arrange
                Account account1 = Account.builder()
                                .id(1L)
                                .accountType(Account.AccountType.SAVINGS_ACCOUNT)
                                .balance(BigDecimal.valueOf(100))
                                .build();
                Account account2 = Account.builder()
                                .id(2L)
                                .accountType(Account.AccountType.CURRENT_ACCOUNT)
                                .balance(BigDecimal.valueOf(200))
                                .build();
                List<Account> accounts = Arrays.asList(account1, account2);

                AccountResponse response1 = new AccountResponse(1L, Account.AccountType.SAVINGS_ACCOUNT,
                                BigDecimal.valueOf(100));
                AccountResponse response2 = new AccountResponse(2L, Account.AccountType.CURRENT_ACCOUNT,
                                BigDecimal.valueOf(200));

                when(accountService.findAccountsByCustomer()).thenReturn(accounts);
                when(accountMapper.fromEntity(account1)).thenReturn(response1);
                when(accountMapper.fromEntity(account2)).thenReturn(response2);

                // Act and Assert
                mockMvc.perform(get("/api/accounts"))
                                .andExpect(status().isOk())
                                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                                .andExpect(jsonPath("$[0].id").value(1))
                                .andExpect(jsonPath("$[0].accountType").value("SAVINGS_ACCOUNT"))
                                .andExpect(jsonPath("$[0].balance").value(100))
                                .andExpect(jsonPath("$[1].id").value(2))
                                .andExpect(jsonPath("$[1].accountType").value("CURRENT_ACCOUNT"))
                                .andExpect(jsonPath("$[1].balance").value(200));
        }

        @Test
        @DisplayName("get account by id should return ok status")
        void getAccount_ValidAccountId_ReturnsOkStatus() throws Exception {
                // Arrange
                Account account = Account.builder()
                                .id(1L)
                                .accountType(Account.AccountType.SAVINGS_ACCOUNT)
                                .balance(BigDecimal.valueOf(100))
                                .build();
                AccountResponse response = new AccountResponse(1L, Account.AccountType.SAVINGS_ACCOUNT,
                                BigDecimal.valueOf(100));

                when(accountService.getAccount(1L)).thenReturn(account);
                when(accountMapper.fromEntity(account)).thenReturn(response);

                // Act and Assert
                mockMvc.perform(get("/api/accounts/1"))
                                .andExpect(status().isOk())
                                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                                .andExpect(jsonPath("$.id").value(1))
                                .andExpect(jsonPath("$.accountType").value("SAVINGS_ACCOUNT"))
                                .andExpect(jsonPath("$.balance").value(100));
        }
}
