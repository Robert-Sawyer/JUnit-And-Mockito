package com.github.robertsawyer.testing;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

class AddressTest {

    @ParameterizedTest
    @CsvSource({"Jagielońska, 13", "Leszka Czarnego, 16"})
    void givenAddressesShouldNotBeEmptyAndProperNames(String street, String houseNumber) {
        assertThat(street, notNullValue());
        assertThat(street, containsString("a"));
        assertThat(houseNumber, notNullValue());
        assertThat(houseNumber.length(), lessThan(3));

    }

    @ParameterizedTest
    @CsvFileSource(resources = "/addresses.csv")
    void addressesFromFileShouldNotBeEmptyAndProperNames(String street, String houseNumber) {
        assertThat(street, notNullValue());
        assertThat(street, containsString("a"));
        assertThat(houseNumber, notNullValue());
        assertThat(houseNumber.length(), lessThan(10));

    }
}