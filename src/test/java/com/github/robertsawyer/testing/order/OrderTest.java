package com.github.robertsawyer.testing.order;

import com.github.robertsawyer.testing.BeforeAfterExtension;
import com.github.robertsawyer.testing.meal.Meal;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(BeforeAfterExtension.class)
class OrderTest {

    private Order order;

    @BeforeEach
    void initializeNewOrder(){
        order = new Order();
    }

    @AfterEach
    void cleanUp(){
        order.cancel();
    }

    @Test
    void orderShouldBeEmptyAfterCreation(){
        assertThat(order.getOrders(), empty());
        assertThat(order.getOrders().size(), equalTo(0));
        MatcherAssert.assertThat(order.getOrders(), hasSize(0));
    }

    @Test
    void shouldAddMealToOrder() {
        //given
        Meal meal = new Meal(15, "Burger", 1);

        //when
        order.addMealToOrder(meal);

        //then
        assertThat(order.getOrders(), hasSize(1));
        assertThat(order.getOrders(), contains(meal));
    }

    @Test
    void shouldRemoveMealFromOrder() {
        //given
        Meal meal = new Meal(15, "Burger", 1);

        //when
        order.addMealToOrder(meal);
        order.removeMealFromOrder(meal);

        //then
        assertThat(order.getOrders(), hasSize(0));
        assertThat(order.getOrders(), not(contains(meal)));
    }

    @Test
    void mealsShouldBeInCorrectOrder(){
        //given
        Meal meal1 = new Meal(15, "Burger", 1);
        Meal meal2 = new Meal(10, "BigMac", 1);

        //when
        order.addMealToOrder(meal1);
        order.addMealToOrder(meal2);

        //then
        assertThat(order.getOrders(), hasSize(2));
        assertThat(order.getOrders(), contains(meal1, meal2));
        //TO JEŚLI KOLEJNOSC DODAWANIE NIE MA ZNACZENIA
        assertThat(order.getOrders(), containsInAnyOrder(meal1, meal2));
    }

    @Test
    void orderTotalPriceShouldNotExceedMaxValue(){

        //given
        Meal meal1 = new Meal(Integer.MAX_VALUE, "Burger", 1);
        Meal meal2 = new Meal(Integer.MAX_VALUE, "BigMac", 1);

        //when
        order.addMealToOrder(meal1);
        order.addMealToOrder(meal2);

        //then
        assertThrows(IllegalStateException.class, () -> order.totalPrice());
    }

    @Test
    void emptyOrderTotalPriceShouldEqualZero(){
        //given
        //Order powstaje w BeforeEach
        //then
        assertThat(order.totalPrice(), is(0));
    }

    @Test
    void cancellingOrderShouldRemoveAllMealsFromList(){
        //given
        Meal meal1 = new Meal(5, "Burger", 1);
        Meal meal2 = new Meal(9, "BigMac", 1);

        //when
        order.addMealToOrder(meal1);
        order.addMealToOrder(meal2);
        order.cancel();

        //then
        assertThat(order.getOrders().size(), is(0));
    }
}