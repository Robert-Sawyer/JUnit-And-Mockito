package com.github.robertsawyer.testing;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.Duration;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

class CartTest {

    //@Disabled -> adnotacja dla testu, który chcemy, żeby był pomijany (może być także nad całą klasą testową
    @DisplayName("Cart is able to process 1000 orders in 100 ms.") //adnotacja do opisów testów lub całych klas testowych
    @Test
    void simulateLargeOrder() {
        //given
        Cart cart = new Cart();

        //when, then
        assertTimeout(Duration.ofMillis(100), cart::simulateLargeOrder);
    }

    @Test
    void cartShouldNotBeEmptyAfterAddingOrder(){
        //given
        Cart cart = new Cart();
        Order order = new Order();

        //when
        cart.addOrderToCart(order);

        //then
//        assertThat(cart.getOrders(), anyOf(     //metoda do łąćzenia asercji, przejdzie gdy PPRZYNAJNIEJ JEDNA z nich zostanie spełniona
//                notNullValue(),
//                hasSize(1),
//                is(not(empty())),
//                is(not(emptyCollectionOf(Order.class)))
//
//        ));
        assertThat(cart.getOrders(), allOf(     //metoda do łąćzenia asercji, przejdzie gdy KAŻDA ASERCJA zostanie spełniona
                notNullValue(),
                hasSize(1),
                is(not(empty())),
                is(not(emptyCollectionOf(Order.class)))

        ));
        assertAll(
                () -> assertThat(cart.getOrders(), notNullValue()),     //alternatywa, dla tych, co lubią lambdy ;)
                () -> assertThat(cart.getOrders(), hasSize(1)),
                () -> assertThat(cart.getOrders(), is(not(empty())))
        );
    }
}