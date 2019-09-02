package com.github.robertsawyer.testing;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

class MealTest {

    @Test
    void shouldReturnDiscountedPrice() {
        //given
        Meal meal = new Meal(30, "Pizza");

        //when
        int discountedPrice = meal.getDiscountedPrice(5);

        //then
        assertEquals(25, discountedPrice);
        assertThat(discountedPrice, equalTo(25));
    }

    @Test
    void referenceToTheSameObjectShouldBeEqual(){
        Meal meal = new Meal(15, "Pizza");
        Meal meal1 = meal;

        assertSame(meal, meal1);
        assertThat(meal, sameInstance(meal1));
    }

    @Test
    void referenceToDifferentObjectShouldNotBeEqual(){
        Meal meal = new Meal(15, "Pizza");
        Meal meal1 = new Meal(15, "Frytki");

        assertNotSame(meal, meal1);
        assertThat(meal, not(sameInstance(meal1)));
    }

    @Test
    void twoMealsShouldBeEqualWhenPriceAndNameAreTheSame(){
        Meal meal = new Meal(10, "Pizza");
        Meal meal1 = new Meal(10, "Pizza");

        assertEquals(meal, meal1);
    }

    @Test
    void exceptionShouldBeThrownIfDiscountIsHigherThanThePrice(){
        //given
        Meal meal = new Meal(5, "Frytki");

        //when, then
        assertThrows(IllegalArgumentException.class, () -> meal.getDiscountedPrice(7));
    }

    @ParameterizedTest
    @ValueSource(ints = {5, 10, 15, 18})
    void mealPricesShouldBeLowerThanTwenty(int prices) {
        assertThat(prices, lessThan(20));

    }

    @ParameterizedTest
    @MethodSource("createMealsWithNameAndPrice")
    void burgersShouldHaveCorrectNameAndPrice(String name, int price) {
        assertThat(name, containsString("burger"));
        assertThat(price, greaterThanOrEqualTo(10));
    }

    private static Stream<Arguments> createMealsWithNameAndPrice() {
        return Stream.of(
                Arguments.of("Hamburger", 10),
                Arguments.of("Cheeseburger", 12)
        );
    }

    @ParameterizedTest
    @MethodSource("createCakeNames")
    void cakeNamesShouldEndWithCake(String name) {
        assertThat(name, notNullValue());
        assertThat(name, endsWith("cake"));
    }

    private static Stream<String> createCakeNames() {
        List<String> list = Arrays.asList("Fruitcake", "Cupcake", "Cheesecake");
        return list.stream();
    }


}