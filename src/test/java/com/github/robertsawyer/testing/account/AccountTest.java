package com.github.robertsawyer.testing.account;

import com.github.robertsawyer.testing.account.Account;
import com.github.robertsawyer.testing.account.Address;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.assumingThat;

class AccountTest {

    @Test
    void newAccontShouldNotBeActiveAfterCreation(){
        //given -> sekcja w metodzie testowej, która przygotowuje warunki do testu
        Account account = new Account();

        //then -> sekcja w któej znajduja sie asercje - główna sekcja testowania
        assertFalse(account.isActive());
        //alternatywnie matchery z biblioteki hamcrest
        assertThat(account.isActive(), is(false));
        assertThat(account.isActive(), equalTo(false));
    }

    @Test
    void accountShouldBeActiveAfterActivation(){
        //given
        Account account = new Account();

        //when -> sekcja, w której wykonuje się czynności prowadzące do przetestowania danej funkcjonalności (nie zawsze występuje, bo
        //czasami wystarczy jedynie sekcja given).
        account.activate();

        //then
        assertTrue(account.isActive());
        assertThat(account.isActive(), is(true));
    }

    @Test
    void newlyCreatedAccontShouldNotHaveAddressSet(){
        //given
        Account account = new Account();

        //when
        Address address = account.getAddress();

        //then
        assertNull(address);
        assertThat(address, nullValue());
    }

    @Test
    void addressShouldNotBeNullAfterSet(){
        //given
        Address address = new Address("Jagiellońska", "13");
        Account account = new Account();
        account.setAddress(address);

        //when
        account.getAddress();

        //then
        assertNotNull(address);
        assertThat(account, notNullValue());
    }

//    @RepeatedTest(10)  -> adnotacja używana gdy chcemy aby jakiś test wykonał się konkretną ilośc razy
    @Test
    void newAccountWithNotNullAdressShouldBeActive(){
        //given
        Address address = new Address("Leszka Czarnego", "16");

        //when
        Account account = new Account(address);

        //then
        assumingThat(address != null, () -> assertTrue(account.isActive()));
    }

    @Test
    void invalidEmailShouldThrowException(){
        //given
        Account account = new Account();

        //when, then
        assertThrows(IllegalArgumentException.class, () -> account.setEmail("Niepoprawny email"));
    }

    @Test
    void validEmailShouldBeSet(){
        Account account = new Account();

        account.setEmail("robert@sawyer.pl");

        assertThat(account.getEmail(), is("robert@sawyer.pl"));
    }

}