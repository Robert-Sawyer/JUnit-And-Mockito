package com.github.robertsawyer.testing;

import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.DynamicTest.dynamicTest;

class MealTest {

    @Test
    void shouldReturnDiscountedPrice() {
        //given
        Meal meal = new Meal(30, "Pizza", 1);

        //when
        int discountedPrice = meal.getDiscountedPrice(5);

        //then
        assertEquals(25, discountedPrice);
        assertThat(discountedPrice, equalTo(25));
    }

    @Test
    void referenceToTheSameObjectShouldBeEqual(){
        Meal meal = new Meal(15, "Pizza", 1);
        Meal meal1 = meal;

        assertSame(meal, meal1);
        assertThat(meal, sameInstance(meal1));
    }

    @Tag("frytki")
    @Test
    void referenceToDifferentObjectShouldNotBeEqual(){
        Meal meal = new Meal(15, "Pizza", 1);
        Meal meal1 = new Meal(15, "Frytki", 1);

        assertNotSame(meal, meal1);
        assertThat(meal, not(sameInstance(meal1)));
    }

    @Test
    void twoMealsShouldBeEqualWhenPriceAndNameAreTheSame(){
        Meal meal = new Meal(10, "Pizza", 1);
        Meal meal1 = new Meal(10, "Pizza", 1);

        assertEquals(meal, meal1);
    }

    @Tag("frytki")
    @Test
    void exceptionShouldBeThrownIfDiscountIsHigherThanThePrice(){
        //given
        Meal meal = new Meal(5, "Frytki", 1);

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

    @TestFactory
    Collection<DynamicTest> dynamicTestCollection(){
        return Arrays.asList(
                dynamicTest("Dynamic Test 1", () -> assertThat(5, lessThan(6))),
                dynamicTest("Dynamic Test 2", () -> assertEquals(4, 2*2))
        );
    }

    @Tag("frytki")
    @TestFactory
    Collection<DynamicTest> calculateMealPrice(){

        Order order = new Order();
        order.addMealToOrder(new Meal(12, "Hamburger", 3));
        order.addMealToOrder(new Meal(7, "Pizza", 2));
        order.addMealToOrder(new Meal(8, "Frytki", 5));

        Collection<DynamicTest> dynamicTests = new ArrayList<>();

        for (int i = 0; i < order.getOrders().size(); i++) {

            int price = order.getOrders().get(i).getPrice();
            int quantity = order.getOrders().get(i).getQuantity();

            Executable executable = () -> {
                assertThat(calculatePrice(price, quantity), lessThan(41));
            };
            String name = "Dynamic Test: " + i;
            DynamicTest dynamicTest = DynamicTest.dynamicTest(name, executable);
            dynamicTests.add(dynamicTest);
        }
        return dynamicTests;
    }

    private int calculatePrice(int price, int quantity) {
        return price * quantity;
    }
}