package com.github.robertsawyer.testing.account;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AccountServiceTest {

    @Test
    void getAllActiveAccounts() {

        //given
        List<Account> accounts = prepareAccountData();
        AccountRepository accountRepository = mock(AccountRepository.class);
        AccountService accountService = new AccountService(accountRepository);
        when(accountRepository.getAllAccounts()).thenReturn(accounts);
        //alternatywnie:
//        given(accountRepository.getAllAccounts()).willReturn(accounts);

        //when
        List<Account> accountList = accountService.getAllActiveAccounts();

        //then
        assertThat(accountList, hasSize(2));

    }

    @Test
    void getNoActiveAccounts() {

        //given
        AccountRepository accountRepository = mock(AccountRepository.class);
        AccountService accountService = new AccountService(accountRepository);
        when(accountRepository.getAllAccounts()).thenReturn(Arrays.asList());
        //alternatywnie:
//        given(accountRepository.getAllAccounts()).willReturn(accounts);

        //when
        List<Account> accountList = accountService.getAllActiveAccounts();

        //then
        assertThat(accountList, hasSize(0));
    }

    @Test
    void getAccountByName() {

        //given
        AccountRepository accountRepository = mock(AccountRepository.class);
        AccountService accountService = new AccountService(accountRepository);
        given(accountRepository.getByName("Robert")).willReturn(Collections.singletonList("Sawyer"));
        //alternatywnie:
//        given(accountRepository.getAllAccounts()).willReturn(accounts);

        //when
        List<String> accountList = accountService.findByName("Robert");

        //then
        assertThat(accountList, contains("Sawyer"));
    }

    private static List<Account> prepareAccountData(){
        Address address1 = new Address("Leszka Czarnego", "16");
        Account account1 = new Account(address1);

        Account account2 = new Account();

        Address address2 = new Address("Jagiellońska", "13");
        Account account3 = new Account(address2);

        return Arrays.asList(account1, account2, account3);
    }


}
