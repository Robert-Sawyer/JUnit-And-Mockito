package com.github.robertsawyer.testing;

import org.junit.jupiter.api.Test;

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
}